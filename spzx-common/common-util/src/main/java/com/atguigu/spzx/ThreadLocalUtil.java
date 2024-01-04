package com.atguigu.spzx;

import com.atguigu.spzx.model.entity.system.SysUser;

public class ThreadLocalUtil {
    // 任意一个线程中，使用的是相同的threadLocal
    private final static ThreadLocal<SysUser> threadLocal = new ThreadLocal<SysUser>();
    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }
    public static SysUser get(){
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
