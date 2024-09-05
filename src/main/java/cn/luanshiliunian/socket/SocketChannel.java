package cn.luanshiliunian.socket;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.function.Consumer;

public interface SocketChannel {
    void write(byte[]... buf) throws IOException;

    byte[] read(int readSize) throws IOException;

    byte[] read(int readSize, int timeout) throws IOException;

    void read(byte[] data, int off, int len, int timeout) throws IOException;

    int read(byte[] data) throws IOException;

    void read(Consumer<byte[]> consumer);

    void readAsync(Consumer<byte[]> consumer);

    boolean isConnected();

    SocketAddress getRemoteSocketAddress();

    SocketAddress getLocalSocketAddress();

    void close();

    String getAttribute();

    void setAttribute(String at);
}
