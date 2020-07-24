package com.macky.springbootshardingjdbc.concurrent;

/**
 * Created by IntelliJ IDEA.
 * User:zhaozhihui
 * Date: 2020/5/23
 * Time: 17:49
 */


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程获取类
 */
public class VoucherExecutor {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(20);

    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
