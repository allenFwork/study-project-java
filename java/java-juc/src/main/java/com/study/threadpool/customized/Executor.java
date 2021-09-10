package com.study.threadpool.customized;

public interface Executor {

    // 暴露接口
    void execute(Runnable task);

}
