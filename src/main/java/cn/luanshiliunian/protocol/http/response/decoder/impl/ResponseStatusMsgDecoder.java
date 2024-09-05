package cn.luanshiliunian.protocol.http.response.decoder.impl;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.object.Response;
import cn.luanshiliunian.protocol.http.response.decoder.AbstractResponseDecoder;
import cn.luanshiliunian.protocol.http.response.decoder.ResponseDecoder;
import cn.luanshiliunian.protocol.http.utils.Constant;

public class ResponseStatusMsgDecoder extends AbstractResponseDecoder {

    private final DynamicByte dynamicByte = new DynamicByte();
    private final ResponseHeaderDecoder headerDecoder = new ResponseHeaderDecoder();

    @Override
    protected ResponseDecoder decoder0(byte[] buffer, int cursor, Response response) {

        int len = buffer.length;
        int c = cursor;

        while (c < len) {
            byte b = buffer[c];

            if (b == Constant.CR && ++c < len) {
                if ((b = buffer[c]) != Constant.LF) {
                    return this;
                } else {
                    c++;
                    break;
                }

            } else dynamicByte.append(b);
            c++;
        }

        response.setStatusMessage(dynamicByte.getString());

        return headerDecoder.decoder0(buffer, c, response);
    }
}
