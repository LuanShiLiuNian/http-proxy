package cn.luanshiliunian.protocol.http.response.decoder.impl;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.object.Response;
import cn.luanshiliunian.protocol.http.response.decoder.AbstractResponseDecoder;
import cn.luanshiliunian.protocol.http.response.decoder.ResponseDecoder;
import cn.luanshiliunian.protocol.http.utils.Constant;

public class ResponseHttpStatusDecoder extends AbstractResponseDecoder {

    private final DynamicByte dynamicByte = new DynamicByte(3);
    private final ResponseStatusMsgDecoder statusMsgDecoder = new ResponseStatusMsgDecoder();

    @Override
    protected ResponseDecoder decoder0(byte[] buffer, int cursor, Response response) {
        int len = buffer.length;
        int c = cursor;
        byte b;
        while (c < len && (b = buffer[c]) != Constant.SP) {
            dynamicByte.append(b);
            c++;
        }

        c++;

        String codeStr = dynamicByte.getString();
        try {
            int status = Integer.parseInt(codeStr);
            response.setStatusCode(status);
        } catch (NumberFormatException e) {
            throw new RuntimeException("http response decoder: status parse error!");
        }

        return statusMsgDecoder.decoder0(buffer, c, response);
    }
}
