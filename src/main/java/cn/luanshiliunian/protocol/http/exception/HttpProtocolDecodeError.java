package cn.luanshiliunian.protocol.http.exception;

public class HttpProtocolDecodeError extends RuntimeException{

    public HttpProtocolDecodeError(String message) {
        super(message);
    }
}
