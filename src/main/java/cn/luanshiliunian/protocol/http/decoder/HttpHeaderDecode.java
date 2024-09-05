package cn.luanshiliunian.protocol.http.decoder;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.common.HttpHeader;
import cn.luanshiliunian.protocol.http.enums.HeaderNameEnum;
import cn.luanshiliunian.protocol.http.exception.HttpProtocolDecodeError;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.protocol.http.utils.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HttpHeaderDecode extends AbstractDecoder {

    private final HttpContextLengthDecoder contextLengthDecoder = new HttpContextLengthDecoder();
    private final HttpTransferEncodingDecoder transferEncodingDecoder = new HttpTransferEncodingDecoder();

    private final DynamicByte line = new DynamicByte();

    @Override
    protected Decoder decode0(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx) {


        while (true) {

            byte b = byteBuf.readByte();
            if (b == Constant.CR && byteBuf.isReadable()) {
                byte c = byteBuf.readByte();
                if (c == Constant.LF) {
                    String linStr = line.getString();
                    int colonIdx = linStr.indexOf(":");
                    if (colonIdx == -1) throw new RuntimeException("http protocol is error!");
                    String key = linStr.substring(0, colonIdx).trim();
                    colonIdx++;
                    String val = linStr.substring(colonIdx).trim();
                    request.getHeader().put(key, val);
                    line.clear();

                    if (byteBuf.isReadable() && (b = byteBuf.readByte()) == Constant.CR) {
                        if (byteBuf.isReadable() && (b = byteBuf.readByte()) == Constant.LF)
                            break;
                        else throw new RuntimeException("Header err!");
                    } else line.append(b);

                }
            } else line.append(b);

            if (!byteBuf.isReadable()) return this;
        }
        HttpHeader header = request.getHeader();

        // host
        String hostStr = HeaderNameEnum.HOST.getName();
        if (header.containsKey(hostStr)){
            String hostVal = header.get(hostStr);
            request.setHost(hostVal);
        }

        // keepAlive
        String connectionStr = HeaderNameEnum.CONNECTION.getName();
        if (header.containsKey(connectionStr)) {
            if (header.get(connectionStr).equalsIgnoreCase("keep-alive"))
                request.setKeepAlive(true);
        } else {
            connectionStr = HeaderNameEnum.PROXY_CONNECTION.getName();

            if (header.containsKey(connectionStr)){
                if (header.get(connectionStr).equalsIgnoreCase("keep-alive"))
                    request.setKeepAlive(true);
                else request.setKeepAlive(false);

                header.remove(HeaderNameEnum.PROXY_CONNECTION.getName());
            }


        }



        String contextLength = HeaderNameEnum.CONTENT_LENGTH.getName();
        String transferEncoding = HeaderNameEnum.TRANSFER_ENCODING.getName();

        if (header.containsKey(contextLength)) {
            String contextLengthStr = header.get(contextLength);
            request.setContentLength(Integer.parseInt(contextLengthStr));

            return contextLengthDecoder.decode0(byteBuf, request, ctx);
        } else if (header.containsKey(transferEncoding)) {

            String encodingVal = "chunked";
            if (header.get(transferEncoding).equals(encodingVal))
                return transferEncodingDecoder.decode0(byteBuf, request, ctx);
            else throw new HttpProtocolDecodeError("http header Transfer-Encoding not implement!");
        }
        return null;
    }
}
