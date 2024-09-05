package cn.luanshiliunian.protocol.http.enums;

public enum HttpProtocolEnum {

    HTTP("http"),
    HTTPS("https");


    private final String protocol;

    HttpProtocolEnum(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }
}
