package cn.luanshiliunian.protocol.http.response.decoder;

import cn.luanshiliunian.protocol.http.object.Response;

public abstract class AbstractResponseDecoder implements ResponseDecoder {

    protected int cor;

    @Override
    public ResponseDecoder decode(byte[] buffer, int cursor, Response response) {
        return decoder0(buffer, cursor, response);
    }

    protected abstract ResponseDecoder decoder0(byte[] buffer, int cursor, Response response);

    @Override
    public int getCursor() {
        return cor;
    }
}
