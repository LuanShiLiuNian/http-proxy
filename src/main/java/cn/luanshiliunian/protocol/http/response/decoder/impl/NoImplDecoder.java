package cn.luanshiliunian.protocol.http.response.decoder.impl;

import cn.luanshiliunian.protocol.http.object.Response;
import cn.luanshiliunian.protocol.http.response.decoder.AbstractResponseDecoder;
import cn.luanshiliunian.protocol.http.response.decoder.ResponseDecoder;

public class NoImplDecoder extends AbstractResponseDecoder {
    @Override
    protected ResponseDecoder decoder0(byte[] buffer, int cursor, Response response) {
        this.cor = cursor;
        return null;
    }
}
