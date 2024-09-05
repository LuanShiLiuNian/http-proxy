package cn.luanshiliunian.protocol.http.response.decoder.impl;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.enums.HttpVersionEnum;
import cn.luanshiliunian.protocol.http.object.Response;
import cn.luanshiliunian.protocol.http.response.decoder.AbstractResponseDecoder;
import cn.luanshiliunian.protocol.http.response.decoder.ResponseDecoder;
import cn.luanshiliunian.protocol.http.utils.Constant;

public class ResponseHttpVersionDecoder extends AbstractResponseDecoder {

    private final DynamicByte dynamicByte = new DynamicByte(8);

    private final ResponseHttpStatusDecoder statusDecoder = new ResponseHttpStatusDecoder();

    @Override
    protected ResponseDecoder decoder0(byte[] buffer, int cursor, Response response) {
        int c = cursor;

        int len = buffer.length;
        byte b;
        while (c < len && (b = buffer[c]) != Constant.SP) {
            dynamicByte.append(b);
            c++;
        }

        c++;

        String httpVersion = dynamicByte.getString();

        if (httpVersion.equalsIgnoreCase("HTTP/1.1"))
            response.setHttpVersion(HttpVersionEnum.HTTP_1_1);
        else if (httpVersion.equalsIgnoreCase("HTTP/1.0"))
            response.setHttpVersion(HttpVersionEnum.HTTP_1_0);
        else throw new RuntimeException("httpVersion err;");
        return statusDecoder.decoder0(buffer, c, response);
    }
}
