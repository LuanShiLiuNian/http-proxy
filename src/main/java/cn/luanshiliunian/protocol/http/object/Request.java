package cn.luanshiliunian.protocol.http.object;


import cn.luanshiliunian.protocol.http.common.HttpHeader;
import cn.luanshiliunian.protocol.http.enums.HeaderValueEnum;
import cn.luanshiliunian.protocol.http.enums.HttpMethodEnum;
import cn.luanshiliunian.protocol.http.enums.HttpProtocolEnum;
import cn.luanshiliunian.protocol.http.enums.HttpVersionEnum;

public class Request {

    private HttpMethodEnum method;

    private HttpProtocolEnum protocol;
    private String path;
    private String orgUrl;
    private String host;

    private HttpVersionEnum version;
    private final HttpHeader header = new HttpHeader();
    private HeaderValueEnum connect;
    private Integer contentLength = null;

    private byte[] context;

    private boolean keepAlive = false;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public byte[] getContext() {
        return context;
    }

    public void setContext(byte[] context) {
        this.context = context;
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }

    public HttpProtocolEnum getProtocol() {
        return protocol;
    }

    public void setProtocol(HttpProtocolEnum protocol) {
        this.protocol = protocol;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }

    public HttpVersionEnum getVersion() {
        return version;
    }

    public void setVersion(HttpVersionEnum version) {
        this.version = version;
    }

    public HttpHeader getHeader() {
        return header;
    }

    public HeaderValueEnum getConnect() {
        return connect;
    }

    public void setConnect(HeaderValueEnum connect) {
        this.connect = connect;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }
}
