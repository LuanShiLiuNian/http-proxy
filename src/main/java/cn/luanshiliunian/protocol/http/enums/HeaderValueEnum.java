
package cn.luanshiliunian.protocol.http.enums;


public enum HeaderValueEnum {
    CHUNKED("chunked"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    APPLICATION_JSON("application/json"),
    UPGRADE("Upgrade"),
    WEBSOCKET("websocket"),
    KEEPALIVE("Keep-Alive"),
    // keepalive("keep-alive"),
    CLOSE("close"),
    DEFAULT_CONTENT_TYPE("text/html; charset=utf-8"),
    CONTINUE("100-continue"),
    GZIP("gzip"),
    H2("h2"),
    H2C("h2c");

    private final String name;

    HeaderValueEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
