package cn.luanshiliunian.utils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DnsUtil {

    public static InetSocketAddress getInetAddress(String domainName) throws UnknownHostException {

        String host = domainName;
        if (domainName.contains(":")){
            host = parseHost(domainName);
            if (host == null) throw new RuntimeException("host parse error!");
        }

        if (host.contains(":")) {
            String[] hostArr = host.split(":");
            return new InetSocketAddress(InetAddress.getByName(hostArr[0]), Integer.parseInt(hostArr[1]));
        }

        return new InetSocketAddress(InetAddress.getByName(host), 80);
    }

    public static String parseHost(String url) {
        String regex = "://([^/]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String host = matcher.group(1);
            if ("".equals(host)) return null;
            return host;
        } else {
            return url;
        }
    }

}
