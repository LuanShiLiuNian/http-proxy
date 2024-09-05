package cn.luanshiliunian.protocol.http.utils;



import cn.luanshiliunian.protocol.http.enums.HeaderNameEnum;
import cn.luanshiliunian.protocol.http.enums.HeaderValueEnum;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HeaderUtil {

    private static final Map<String, HeaderNameEnum> headerKeyCached = new HashMap<>();

    private static final Map<String, HeaderValueEnum> headerValCached = new HashMap<>();

    static {
        for (HeaderNameEnum name : HeaderNameEnum.values()) {
            headerKeyCached.put(name.getName().toLowerCase(Locale.ROOT), name);
        }

        for (HeaderValueEnum value : HeaderValueEnum.values()) {
            headerValCached.put(value.getName().toLowerCase(Locale.ROOT), value);
        }
    }

    public static void init() {
    }

    public static Map<String, HeaderNameEnum> getHeaderKeyCached() {
        return headerKeyCached;
    }

    public static Map<String, HeaderValueEnum> getHeaderValCached() {
        return headerValCached;
    }
}
