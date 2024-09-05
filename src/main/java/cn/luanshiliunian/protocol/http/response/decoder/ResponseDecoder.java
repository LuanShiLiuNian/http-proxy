package cn.luanshiliunian.protocol.http.response.decoder;

import cn.luanshiliunian.protocol.http.object.Response;

public interface ResponseDecoder {
    ResponseDecoder decode(byte[] buffer, int cursor, Response response);

    int getCursor();
}
