package com.open.api.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 高并发场景下System.currentTimeMillis()的性能问题的优化
 * 时间戳打印建议使用
 * 原因
 * 1、System.currentTimeMillis()属于native方法，频繁用户态和内核态切换耗费系统资源及时间
 * 2、操作系统通常仅维护一个全局时钟源（如HPET），高并发时多线程竞争访问会引发严重延迟，单次调用偏差可达150ms
 * 解决
 * 1、使用缓存，定时更新时间戳
 * 2、使用spring的StopWatch工具更适合耗时统计
 * 3、使用本方法，定时更新时间，类似缓存
 */
public class SystemClock {
    private static final String THREAD_NAME = "system.clock";
    private static final SystemClock MILLIS_CLOCK = new SystemClock(1);//1毫秒更新一次
    private final long precision;
    private final AtomicLong now;

    private SystemClock(long precision) {
        this.precision = precision;
        now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    public static SystemClock millisClock() {
        return MILLIS_CLOCK;
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, THREAD_NAME);
            thread.setDaemon(true);
            return thread;
        });
        scheduler.scheduleAtFixedRate(() -> now.set(System.currentTimeMillis()), precision, precision, TimeUnit.MILLISECONDS);
    }

    public long now() {
        return now.get();
    }
}
