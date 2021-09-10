package com.study.threadpool.customized;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public interface ExecutorService extends Executor{

    public void shutdown();

    public FutureTask submit(Runnable runnable);

    public FutureTask submit(Callable callable);

}
