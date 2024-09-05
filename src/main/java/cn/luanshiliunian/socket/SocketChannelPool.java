package cn.luanshiliunian.socket;

import java.net.SocketAddress;

public class SocketChannelPool {
    public static SocketChannel open(SocketAddress address) throws Exception {
        String type = chooseSocketChannel();
        if ("netty".equalsIgnoreCase(type)) {
            return NettySocketChannelPool.open(address);
        } else {
            return BioSocketChannelPool.open(address);
        }

    }

    private static String chooseSocketChannel() {
        return "bio"; // or netty
    }

}
