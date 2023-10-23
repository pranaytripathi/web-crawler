package services;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ExecutorProvider {
    private final ExecutorService executorService;
    private static ExecutorProvider instance;

    private ExecutorProvider() {
        this.executorService = new ThreadPoolExecutor(
                100,
                1000,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }

    public static ExecutorProvider getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ExecutorProvider();
        }
        return instance;
    }

    public ExecutorService getExecutors() {
        return instance.executorService;
    }
}
