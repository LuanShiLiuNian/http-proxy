package cn.luanshiliunian.protocol.http.decoder;

import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.object.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;


public abstract class AbstractDecoder implements Decoder {
    @Override
    public Decoder decode(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx) {
        return decode0(byteBuf, request, ctx);
    }

    protected abstract Decoder decode0(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx);
}
