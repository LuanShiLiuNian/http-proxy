package cn.luanshiliunian.core;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager extends ConcurrentHashMap<Object, Object> {


    public <T> T GetSession(Object key) {
        return (T) super.get(key);
    }


    public Object SetSession(Object key, Object value) {
        return super.put(key, value);
    }
}
