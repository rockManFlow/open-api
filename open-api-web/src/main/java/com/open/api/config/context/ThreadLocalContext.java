package com.open.api.config.context;

/**
 * @author rock
 * @detail
 * @date 2025/8/26 10:12
 */
public class ThreadLocalContext {
    private static final ThreadLocal local=new ThreadLocal();

    public static void setLocal(Object value){
        local.set(value);
    }

    public static Object getLocal(){
        return local.get();
    }

    public static void removeLocal(){
        local.remove();
    }
}
