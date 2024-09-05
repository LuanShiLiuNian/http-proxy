package cn.luanshiliunian.net.codes;

import cn.luanshiliunian.core.SessionAttribute;
import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.decoder.HttpMethodDecoder;
import cn.luanshiliunian.protocol.http.object.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static cn.luanshiliunian.core.GlobalContext.SESSION;

public class HttpProxyDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        SessionAttribute sa = SESSION.GetSession(ctx.channel());
        Request request = sa.getRequest();


        if (sa.isTunnel()) {
            if (request == null) request = new Request();
            byte[] buffer = new byte[in.readableBytes()];
            in.readBytes(buffer);
            request.setContext(buffer);
            out.add(request);
            return;
        }

        Decoder decoder = sa.getDecoder();
        if (decoder instanceof HttpMethodDecoder) {
            request = new Request();
            sa.setRequest(request);
        }


        Decoder resultDecoder = decoder.decode(in, request, ctx);
        if (resultDecoder == null) {
            sa.setDecoder(new HttpMethodDecoder());
            out.add(request);
        } else sa.setDecoder(resultDecoder);


    }
}
