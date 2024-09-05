package cn.luanshiliunian.protocol.http.response.decoder.impl;

import cn.luanshiliunian.container.DynamicByte;
import cn.luanshiliunian.protocol.http.object.Response;
import cn.luanshiliunian.protocol.http.response.decoder.AbstractResponseDecoder;
import cn.luanshiliunian.protocol.http.response.decoder.ResponseDecoder;
import cn.luanshiliunian.protocol.http.utils.Constant;

public class ResponseHeaderDecoder extends AbstractResponseDecoder {

    private final NoImplDecoder noImplDecoder = new NoImplDecoder();
    private final DynamicByte line = new DynamicByte();

    @Override
    protected ResponseDecoder decoder0(byte[] buffer, int cursor, Response response) {

        int len = buffer.length;
        int c = cursor;

        while (true) {
            byte b = buffer[c];

            if (b == Constant.CR && (c + 1) < len) {
                b = buffer[++c];
                if (b == Constant.LF) {
                    String linStr = line.getString();
                    int colonIdx = linStr.indexOf(":");
                    if (colonIdx == -1) throw new RuntimeException("http protocol is error!");
                    String key = linStr.substring(0, colonIdx).trim();
                    colonIdx++;
                    String val = linStr.substring(colonIdx).trim();
                    response.getHttpHeader().put(key, val);
                    line.clear();


                    if ((c + 1) < len && (b = buffer[++c]) == Constant.CR) {
                        if ((c + 1) < len && (b = buffer[++c]) == Constant.LF)
                            break;
                        else throw new RuntimeException("Header err!");
                    } else {
                        line.append(b);
                        c++;
                    }

                }
            } else {
                line.append(b);
                c++;
            }

            if (c >= len) return this;

        }
        c++;
        noImplDecoder.decoder0(null, c, response);
        return noImplDecoder;
    }
}
