package cn.luanshiliunian.net.adapter;

import cn.luanshiliunian.core.SessionAttribute;
import cn.luanshiliunian.handle.HttpHandler;
import cn.luanshiliunian.handle.TcpTunnelHandler;
import cn.luanshiliunian.protocol.http.decoder.HttpMethodDecoder;
import cn.luanshiliunian.protocol.http.enums.HttpMethodEnum;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.socket.SocketChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.luanshiliunian.core.GlobalContext.*;

public class TcpServerAdapter extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpServerAdapter.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Request)) {
            LOGGER.warn("receive not request!");
            return;
        }

        Request request = (Request) msg;

        SessionAttribute sessionAttribute = SESSION.GetSession(ctx.channel());

        if (sessionAttribute.isTunnel()) {
            TcpTunnelHandler.exec(request, ctx.channel());
        } else {
            HttpMethodEnum method = request.getMethod();
            if (method != HttpMethodEnum.CONNECT) {
                HttpHandler.exec(request, ctx.channel());
            } else {
                sessionAttribute.setTunnel(true);
                // 开启隧道
                TcpTunnelHandler.createTcpTunnel(request, ctx.channel());
            }
        }


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SessionAttribute sa = new SessionAttribute();
        sa.setDecoder(new HttpMethodDecoder());
        SESSION.SetSession(ctx.channel(), sa);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause.getMessage().contains("NullPointerException") ) {
            LOGGER.error("exceptionCaught->", cause);
        } else
            LOGGER.error("exceptionCaught->{}", cause.getMessage());
        ctx.channel().close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("client close");
        clear(ctx.channel());

        SESSION.remove(ctx.channel());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state() == IdleState.ALL_IDLE) {
                clear(ctx.channel());
            }
        }
    }


    private void clear(Channel channel) {
        SessionAttribute sessionAttribute = SESSION.GetSession(channel);
        if (sessionAttribute != null) {
            SocketChannel socketChannel = sessionAttribute.getSocketChannel();
            try {
                LOGGER.info("IDLE CLOSE");
                socketChannel.close();
            } catch (Exception e) {

            }
        }
        channel.close();
    }

}

