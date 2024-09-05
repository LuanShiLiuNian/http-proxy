package cn.luanshiliunian.protocol.http.decoder;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.enums.HttpMethodEnum;
import cn.luanshiliunian.protocol.http.exception.HttpProtocolDecodeError;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.protocol.http.utils.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HttpMethodDecoder extends AbstractDecoder {

    private final HttpUriDecoder httpUriDecoder = new HttpUriDecoder();

    private final DynamicByte dynamicByte = new DynamicByte(5);

    @Override
    protected Decoder decode0(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx) {

        int i = 0;
        while (true) {
            if (!byteBuf.isReadable()) return this;

            byte b = byteBuf.readByte();
            if (b != Constant.SP) {
                dynamicByte.append(b);
                i++;
                if (i > 8)
                    throw new HttpProtocolDecodeError("http method decoder error!");
            } else break;
        }

        HttpMethodEnum method = HttpMethodEnum.valueOf(dynamicByte.getString());
        request.setMethod(method);

        return httpUriDecoder.decode0(byteBuf, request, ctx);
    }
}
