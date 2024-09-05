package cn.luanshiliunian.protocol.http;

import cn.luanshiliunian.protocol.http.object.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;


public interface Decoder {
    Decoder decode(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx);
}
