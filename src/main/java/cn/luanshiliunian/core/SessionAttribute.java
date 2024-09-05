package cn.luanshiliunian.core;

import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.socket.SocketChannel;

public class SessionAttribute {

    private Decoder decoder;

    private Request request;

    private boolean tunnel = false;

    private SocketChannel socketChannel = null;

    public boolean isTunnel() {
        return tunnel;
    }

    public void setTunnel(boolean tunnel) {
        this.tunnel = tunnel;
    }

    public Decoder getDecoder() {
        return decoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
