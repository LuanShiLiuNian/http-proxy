package cn.luanshiliunian.socket;

import java.net.Socket;
import java.net.SocketAddress;

public class BioSocketChannelPool {
    public static BioSocketChannel open(SocketAddress address) throws Exception {

        return new BioSocketChannel(buildSocket(address, false));
    }

    public static BioSocketChannel openKeepAlive(SocketAddress address) throws Exception {
        return new BioSocketChannel(buildSocket(address, true));
    }

    private static Socket buildSocket(SocketAddress address, boolean keepAlive) throws Exception {
        Socket socket = new Socket();

        if (keepAlive) {
            socket.setKeepAlive(true);
        } else {
            socket.setSoTimeout(BioSocketChannel.SO_TIMEOUT);
        }

        socket.setTcpNoDelay(true);
        socket.setReuseAddress(true);
        socket.setSoTimeout(4000);
        socket.connect(address, BioSocketChannel.DEFAULT_CONNECT_TIMEOUT);
        return socket;
    }
}
