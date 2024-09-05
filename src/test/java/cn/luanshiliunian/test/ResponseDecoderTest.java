package cn.luanshiliunian.test;

import cn.luanshiliunian.protocol.http.object.Response;
import cn.luanshiliunian.protocol.http.response.decoder.impl.ResponseHttpVersionDecoder;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class ResponseDecoderTest {

    @Test
    public void run() {
        String responseHtml = "HTTP/1.1 200 OK\r\n" +
                "Server: nginx/1.20.1\r\n" +
                "Date: Thu, 02 May 2024 02:42:05 GMT\r\n" +
                "Content-Type: text/html;charset=utf-8\r\n" +
                "Transfer-Encoding: chunked\r\n" +
                "Connection: keep-alive\r\n" +
                "Access-Control-Allow-Headers: Content-Type,ADMIN-Authorization,API-Authorization\r\n" +
                "Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS\r\n" +
                "Access-Control-Allow-Credentials: true\r\n" +
                "Access-Control-Max-Age: 3600\r\n" +
                "Set-Cookie: JSESSIONID=node01omyz5t2jdbrrno2x9y1myilp37050.node0; Path=/\r\n" +
                "Expires: Thu, 01 Jan 1970 00:00:00 GMT\r\n" +
                "Content-Language: en-US\r\n\r\n";


        byte[] bytes = responseHtml.getBytes(StandardCharsets.UTF_8);

        ResponseHttpVersionDecoder responseHttpVersionDecoder = new ResponseHttpVersionDecoder();
        Response response = new Response();
        responseHttpVersionDecoder.decode(bytes, 0, response);

    }

}
