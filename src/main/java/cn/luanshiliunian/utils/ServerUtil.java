package cn.luanshiliunian.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUtil {

    private final static Logger logger = LoggerFactory.getLogger(ServerUtil.class);

    public static void Reply(Channel channel, byte[] message) {
        Reply(channel, message, 0, message.length);
    }

    public static void Reply(Channel channel, byte message) {
        ByteBuf byteBuf = channel.alloc().buffer(1);
        byteBuf.writeByte(message);
        channel.writeAndFlush(byteBuf);
    }

    public static void Reply(Channel channel, byte[] message, int index, int len) {
        ByteBuf byteBuf = channel.alloc().buffer(len);
        byteBuf.writeBytes(message, index, len);
        channel.writeAndFlush(byteBuf);
        // byteBuf.clear();
    }

    public static void ReplyWaitComplete(Channel channel, byte[] message) {
        ReplyWaitComplete(channel, message, 0, message.length);
    }

    public synchronized static void ReplyWaitComplete(Channel channel, byte[] message, int index, int len) {
        ByteBuf byteBuf = channel.alloc().buffer(len);
        byteBuf.writeBytes(message, index, len);
        flush(channel, byteBuf);

    }

    public static void asyncReply(Channel channel, byte[] message) {
        ByteBuf requestBuffer = Unpooled.copiedBuffer(message);
        channel.writeAndFlush(requestBuffer);
    }

    /**
     * 往channel里输出消息
     */
    public static void flush(Channel channel, ByteBuf byteBuf) {
        if (channel.isWritable()) {
            channel.writeAndFlush(byteBuf).addListener(future -> {
                if (!future.isSuccess()) {
                    logger.warn("flush error " + future.cause().getMessage());
                }
            });
        } else {
            try {
                //同步发送
                channel.writeAndFlush(byteBuf).sync();
            } catch (InterruptedException e) {
                logger.error("flush error " + e.getMessage());
            }
        }
    }

}
