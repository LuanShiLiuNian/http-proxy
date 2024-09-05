package cn.luanshiliunian.protocol.http.enums;

public enum HttpVersionEnum {
    HTTP_1_1("HTTP/1.1"),
    HTTP_1_0("HTTP/1.0");

    final String version;

    HttpVersionEnum(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
