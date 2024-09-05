package cn.luanshiliunian.handle;

import cn.luanshiliunian.core.SessionAttribute;
import cn.luanshiliunian.protocol.http.common.HttpHeader;
import cn.luanshiliunian.protocol.http.enums.HeaderNameEnum;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.protocol.http.object.Response;
import cn.luanshiliunian.protocol.http.response.decoder.ResponseDecoder;
import cn.luanshiliunian.protocol.http.response.decoder.impl.NoImplDecoder;
import cn.luanshiliunian.protocol.http.response.decoder.impl.ResponseHttpVersionDecoder;
import cn.luanshiliunian.protocol.http.utils.HttpProtocolUtil;
import cn.luanshiliunian.socket.BioSocketChannelPool;
import cn.luanshiliunian.socket.SocketChannel;
import cn.luanshiliunian.utils.DnsUtil;
import cn.luanshiliunian.utils.FlowUtil;
import cn.luanshiliunian.utils.ServerUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static cn.luanshiliunian.core.GlobalContext.SESSION;

public class HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHandler.class);

    public static void exec(Request r, Channel channel) throws Exception {
        SessionAttribute sa = SESSION.GetSession(channel);
        if (sa.isTunnel()) {
            throw new RuntimeException("the request is Tunnel httpHandler not handler!");
        }

        SocketChannel socket = sa.getSocketChannel();
        while (true) {
            if (socket == null) {
                LOGGER.info("httpConnectHost:{}", r.getHost());
                socket = BioSocketChannelPool.open(DnsUtil.getInetAddress(r.getHost()));
                socket.setAttribute(r.getHost());
                sa.setSocketChannel(socket);
            }

            if (socket.getAttribute().equals(r.getHost())) break;
            else {
                socket.close();
                socket = null;
                sa.setSocketChannel(socket);
            }
        }


        byte[] httpHeader = HttpProtocolUtil.buildHttpRequestHeader(r);

        socket.write(httpHeader);
        if (r.getContext() != null) {
            socket.write(r.getContext());
        }

        Response response = new Response();

        final boolean keepAlive = r.isKeepAlive();


        socket.read(new Consumer<byte[]>() {
            private ResponseDecoder responseDecoder = new ResponseHttpVersionDecoder();

            @Override
            public void accept(byte[] bytes) {
                LOGGER.info("http ↓ ->{}", FlowUtil.humanFileSize(bytes.length));
                if (!(responseDecoder instanceof NoImplDecoder)) {
                    responseDecoder = responseDecoder.decode(bytes, 0, response);
                    if (responseDecoder instanceof NoImplDecoder) {
                        // build response httpHeader

                        HttpHeader header = response.getHttpHeader();
                        header.put(HeaderNameEnum.SERVER.getName(), "JLinkServer");
                        header.put("Keep-Alive", "timeout=4");
                        byte[] responseHeaderBytes = HttpProtocolUtil.buildHttpResponseHeader(response);
                        ServerUtil.ReplyWaitComplete(channel, responseHeaderBytes);

                        int cursor = responseDecoder.getCursor();
//                        if (keepAlive)
//                            ServerUtil.Reply(channel, bytes, cursor, bytes.length - cursor);
//                        else
                        ServerUtil.ReplyWaitComplete(channel, bytes, cursor, bytes.length - cursor);
                    }
                } else {
                    // real client
//                    if (keepAlive)
//                        ServerUtil.Reply(channel, bytes);
//                    else
                    ServerUtil.ReplyWaitComplete(channel, bytes);
                }
            }
        });


        if (!keepAlive) {
            channel.close();
            socket.close();
            sa.setSocketChannel(null);
        }


    }

}
