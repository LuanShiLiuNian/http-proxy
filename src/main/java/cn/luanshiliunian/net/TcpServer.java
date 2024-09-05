package cn.luanshiliunian.net;

import cn.luanshiliunian.net.adapter.TcpServerAdapter;
import cn.luanshiliunian.net.codes.HttpProxyDecode;
import cn.luanshiliunian.protocol.http.utils.HeaderUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpServer.class);

    public TcpServer() {
        HeaderUtil.init();
    }

    public void starter() {
         int threadSize = Runtime.getRuntime().availableProcessors() * 3;
        EventLoopGroup bossGroup = new NioEventLoopGroup(4);
        EventLoopGroup workerGroup = new NioEventLoopGroup(threadSize);

        ServerBootstrap httpProxyBootstrap = new ServerBootstrap();
        httpProxyBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpProxyDecode());
                        ch.pipeline().addLast(new TcpServerAdapter());
                        ch.pipeline().addLast(new IdleStateHandler(0, 0, 4));
                    }
                });

        ChannelFuture future = httpProxyBootstrap.bind(8080);

        try {
            future.sync();

            LOGGER.info("server at starter!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {

                    future.channel().close();
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            LOGGER.info("O0O0O0httpServerStarter0O0O0O");
        }

    }
}
