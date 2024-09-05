package cn.luanshiliunian.protocol.http.utils;

import cn.luanshiliunian.protocol.http.common.HttpHeader;
import cn.luanshiliunian.protocol.http.object.Request;
import cn.luanshiliunian.protocol.http.object.Response;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpProtocolUtil {

    private static final char SP_CHAR = (char) Constant.SP;

    public static byte[] buildHttpRequestHeader(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getMethod().getMethod()).append(SP_CHAR);
        sb.append(request.getPath()).append(SP_CHAR);
        sb.append(request.getVersion().getVersion()).append(Constant.CRLF);

        HttpHeader header = request.getHeader();
        for (Map.Entry<String, String> item : header.entrySet()) {
            sb.append(item.getKey()).append(Constant.COLON_CHAR).append(item.getValue()).append(Constant.CRLF);
        }

        sb.append(Constant.CRLF);
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] buildHttpResponseHeader(Response response) {
        StringBuilder sb = new StringBuilder();

        sb.append(response.getHttpVersion().getVersion()).append(SP_CHAR);
        sb.append(response.getStatusCode()).append(SP_CHAR).append(response.getStatusMessage()).append(Constant.CRLF);

        for (Map.Entry<String, String> entry : response.getHttpHeader().entrySet()) {
            sb.append(entry.getKey()).append(Constant.COLON_CHAR).append(entry.getValue()).append(Constant.CRLF);
        }

        sb.append(Constant.CRLF);
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
