package cn.luanshiliunian.protocol.http.object;

import cn.luanshiliunian.protocol.http.common.HttpHeader;
import cn.luanshiliunian.protocol.http.enums.HttpVersionEnum;

public class Response {
    private HttpVersionEnum httpVersion;

    private int statusCode;

    private String statusMessage;

    private String server;

    private String date;

    private boolean keepAlive = false;

    private final HttpHeader httpHeader = new HttpHeader();

    public HttpVersionEnum getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(HttpVersionEnum httpVersion) {
        this.httpVersion = httpVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    @Override
    public String toString() {
        return "Response{" +
                "httpVersion=" + httpVersion +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                ", server='" + server + '\'' +
                ", date='" + date + '\'' +
                ", keepAlive=" + keepAlive +
                ", httpHeader=" + httpHeader +
                '}';
    }
}
