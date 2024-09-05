package cn.luanshiliunian.protocol.http.decoder;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.enums.HttpVersionEnum;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.protocol.http.utils.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HttpVersionDecoder extends AbstractDecoder {

    private final HttpHeaderDecode headerDecode = new HttpHeaderDecode();

    private final DynamicByte dynamicByte = new DynamicByte(8);

    @Override
    protected Decoder decode0(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx) {

        while (true) {
            if (!byteBuf.isReadable()) return this;

            byte b;
            if ((b = byteBuf.readByte()) == Constant.CR && byteBuf.isReadable() && (b = byteBuf.readByte()) == Constant.LF) {
                break;
            } else {
                dynamicByte.append(b);
            }
        }
        String httpVersion = dynamicByte.getString();

        if (httpVersion.equalsIgnoreCase("HTTP/1.1"))
            request.setVersion(HttpVersionEnum.HTTP_1_1);
        else if (httpVersion.equalsIgnoreCase("HTTP/1.0"))
            request.setVersion(HttpVersionEnum.HTTP_1_0);
        else throw new RuntimeException("httpVersion err;");
        return headerDecode.decode0(byteBuf, request, ctx);
    }
}
