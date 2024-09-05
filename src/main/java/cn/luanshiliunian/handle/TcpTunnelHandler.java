package cn.luanshiliunian.handle;

import cn.luanshiliunian.core.SessionAttribute;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.socket.BioSocketChannelPool;

import cn.luanshiliunian.socket.NettySocketChannelPool;
import cn.luanshiliunian.socket.SocketChannel;
import cn.luanshiliunian.utils.DnsUtil;
import cn.luanshiliunian.utils.FlowUtil;
import cn.luanshiliunian.utils.ServerUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.nio.charset.StandardCharsets;

import static cn.luanshiliunian.core.GlobalContext.*;

public class TcpTunnelHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpTunnelHandler.class);

    public static void createTcpTunnel(Request r, Channel channel) throws Exception {
        SessionAttribute sessionAttribute = SESSION.GetSession(channel);
        LOGGER.info("TCP TUNNEL:{}",r.getHost());
        SocketChannel socketChannel = NettySocketChannelPool.open(DnsUtil.getInetAddress(r.getHost()));
        sessionAttribute.setSocketChannel(socketChannel);

        String replyClient = "HTTP/1.0 200 Connection established\r\n\r\n";

        ServerUtil.ReplyWaitComplete(channel, replyClient.getBytes(StandardCharsets.UTF_8));

        new Thread(() -> {
            socketChannel.read(bytes -> {
                // System.out.println(bytes.length);
                LOGGER.info("TCP TUNNEL ↓ ->{}", FlowUtil.humanFileSize(bytes.length));
                ServerUtil.Reply(channel, bytes);
            });
        }).start();

    }

    public static void exec(Request r, Channel channel) throws Exception {
        SessionAttribute sessionAttribute = SESSION.GetSession(channel);
        SocketChannel socketChannel = sessionAttribute.getSocketChannel();
        if (socketChannel == null) throw new RuntimeException("The tunnel has been disconnected");

        LOGGER.info("TCP TUNNEL ↑ ->{}", FlowUtil.humanFileSize(r.getContext().length));
        if (!socketChannel.isConnected()){
            createTcpTunnel(r,channel);
            exec(r,channel);
        }
        socketChannel.write(r.getContext());



    }
}
