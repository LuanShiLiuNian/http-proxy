package cn.luanshiliunian.protocol.http.common;



import cn.luanshiliunian.protocol.http.enums.HeaderNameEnum;
import cn.luanshiliunian.protocol.http.enums.HeaderValueEnum;
import cn.luanshiliunian.protocol.http.utils.HeaderUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HttpHeader extends HashMap<String, String> {

    @Override
    public String put(String key, String value) {

        // 标准化header协议头
        Map<String, HeaderNameEnum> headerKeyCached = HeaderUtil.getHeaderKeyCached();
        Map<String, HeaderValueEnum> headerValCached = HeaderUtil.getHeaderValCached();

        String k = key;
        String v = value;

        String kLocale = key.toLowerCase(Locale.ROOT);
        String vLocale = value.toLowerCase(Locale.ROOT);

        if (headerKeyCached.containsKey(kLocale)) {
            HeaderNameEnum headerName = headerKeyCached.get(kLocale);
            if (!headerName.getName().equals(key))
                k = headerName.getName();
        }

        if (headerValCached.containsKey(vLocale)) {
            HeaderValueEnum headerValue = headerValCached.get(vLocale);
            if (!headerValue.getName().equals(value))
                v = headerValue.getName();
        }

        return super.put(k, v);
    }

    @Override
    public String get(Object key) {
        return super.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }
}
