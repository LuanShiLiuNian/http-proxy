package cn.luanshiliunian.protocol.http.decoder;

import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.object.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HttpTransferEncodingDecoder extends AbstractDecoder{
    @Override
    protected Decoder decode0(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx) {
        return null;
    }
}
