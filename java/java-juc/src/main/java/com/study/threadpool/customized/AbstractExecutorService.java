package com.study.threadpool.customized;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public abstract class AbstractExecutorService implements ExecutorService {

    @Override
    public FutureTask submit(Runnable runnable) {
        FutureTask futureTask = new FutureTask(runnable, null);
        execute(futureTask);
        return null;
    }

    @Override
    public FutureTask submit(Callable callable) {
        FutureTask futureTask = new FutureTask(callable);
        execute(futureTask);
        return futureTask;
    }
}
