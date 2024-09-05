package cn.luanshiliunian.protocol.http.decoder;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.exception.HttpProtocolDecodeError;
import cn.luanshiliunian.protocol.http.object.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HttpContextLengthDecoder extends AbstractDecoder {

    private int readLen = 0;

    private final DynamicByte dynamicByte = new DynamicByte();

    @Override
    protected Decoder decode0(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx) {

        Integer contentLength = request.getContentLength();
        if (contentLength == null) throw new HttpProtocolDecodeError("contentLength is null!");

        int readableLen = byteBuf.readableBytes();
        if (readableLen >= contentLength) {
            byte[] buffer = new byte[contentLength];
            byteBuf.readBytes(buffer);
            request.setContext(buffer);
            return null;
        } else {
            dynamicByte.appendPayload(byteBuf.readBytes(readableLen));
            readLen += readableLen;
        }

        if (readLen == contentLength) {
            request.setContext(dynamicByte.getValidBody());
            return null;
        } else return this;

    }
}
