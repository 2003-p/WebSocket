package com.objetdirect.gwt.umldrawer.client.saito;
import java.util.concurrent.*;

public class Hello {
    private final ScheduledExecutorService executor;

    public Hello() {
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startPrinting() {
        Runnable task = () -> System.out.println("hello");
        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }
}