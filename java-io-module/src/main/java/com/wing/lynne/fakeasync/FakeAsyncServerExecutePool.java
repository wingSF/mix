package com.wing.lynne.fakeasync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FakeAsyncServerExecutePool {

    private ExecutorService executorService;

    public FakeAsyncServerExecutePool(int maxPoolSize, int queueSize) {
        this.executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize,
                120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }
}
