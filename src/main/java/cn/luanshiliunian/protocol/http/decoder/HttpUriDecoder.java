package cn.luanshiliunian.protocol.http.decoder;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.Decoder;
import cn.luanshiliunian.protocol.http.enums.HttpMethodEnum;
import cn.luanshiliunian.protocol.http.enums.HttpProtocolEnum;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.protocol.http.utils.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Locale;

public class HttpUriDecoder extends AbstractDecoder {

    private final HttpVersionDecoder versionDecoder = new HttpVersionDecoder();

    private final DynamicByte dynamicByte = new DynamicByte();

    @Override
    protected Decoder decode0(ByteBuf byteBuf, Request request, ChannelHandlerContext ctx) {


        byte b;
        while (true) {
            if (!byteBuf.isReadable()) return this;
            if ((b = byteBuf.readByte()) != Constant.SP) {
                dynamicByte.append(b);
            } else break;
        }

        String uap = dynamicByte.getString();

        request.setOrgUrl(uap);

        String uapLower = uap.toLowerCase(Locale.ROOT);
        if (uapLower.startsWith("http") || uapLower.startsWith("https")) {
            int colonIndex = uap.indexOf(":");
            if (colonIndex > 0) {
                String protocol = uap.substring(0, colonIndex);
                HttpProtocolEnum protocolEnum = HttpProtocolEnum.valueOf(protocol.toUpperCase(Locale.ROOT));
                request.setProtocol(protocolEnum);

                colonIndex += 3;
                String hostUrl = uap.substring(colonIndex);
                int slashIndex = hostUrl.indexOf("/");
                if (slashIndex == -1)
                    request.setPath("/");
                else request.setPath(hostUrl.substring(slashIndex));

            } else {
                request.setPath(uap);
            }
        }else {
            if (request.getMethod() == HttpMethodEnum.CONNECT){
                request.setProtocol(HttpProtocolEnum.HTTPS);
            }
        }




        return versionDecoder.decode0(byteBuf, request, ctx);
    }
}
