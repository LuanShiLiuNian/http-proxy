package cn.luanshiliunian.test;

import cn.luanshiliunian.socket.BioSocketChannel;
import cn.luanshiliunian.socket.BioSocketChannelPool;
import cn.luanshiliunian.socket.NettySocketChannelPool;
import cn.luanshiliunian.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class TestHome {

    @Test
    public void run() throws Exception {

        String sendBody = "POST /api/admin/login HTTP/1.1\r\n" +
                "Accept: application/json, text/plain, */*\r\n" +
                "Accept-Language: zh-CN,zh;q=0.9\r\n" +
                "Content-Length: 63\r\n" +
                "Content-Type: application/json\r\n" +
                "Connection: keep-alive\r\n" +
                "Host: www.luanshiliunian.cn\r\n\r\n" +
                "{\"username\":\"admin\",\"password\":\"thanos123.com\",\"authcode\":null}";


        SocketChannel socketChannel = NettySocketChannelPool.open(new InetSocketAddress("124.222.203.74", 80));

        socketChannel.write(sendBody.getBytes(StandardCharsets.UTF_8));

        byte[] b = new byte[0];
        socketChannel.read(b);

        System.out.println(new String(b));

        //System.out.println(new String(socketChannel.read(500)));
        //System.out.println(new String(socketChannel.read(500)));

//        Socket socket = new Socket();
//        socket.connect(new InetSocketAddress("124.222.203.74", 80));
//
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write(sendBody.getBytes(StandardCharsets.UTF_8));
//        InputStream inputStream = socket.getInputStream();
//        byte[] buf = new byte[1024];
//        inputStream.read(buf);
//
//        System.out.println(new String(buf));

    }


    @Test
    public void testPos() throws Exception{
        String sendBody = "POST /api/admin/login HTTP/1.1                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          \r\n" +
                "Accept: application/json, text/plain, */*\r\n" +
                "Accept-Language: zh-CN,zh;q=0.9\r\n" +
                "Content-Length: 63\r\n" +
                "Content-Type: application/json\r\n" +
                "Connection: close\r\n" +
                "Host: www.luanshiliunian.cn\r\n\r\n";





    }
}
