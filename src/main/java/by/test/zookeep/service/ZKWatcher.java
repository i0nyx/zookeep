package by.test.zookeep.service;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

public class ZKWatcher implements Watcher {
    private final CountDownLatch latch;

    ZKWatcher() {
        latch = new CountDownLatch(0);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Watcher fired on path: " + event.getPath() + " state: " +
                event.getState() + " type " + event.getType());
        latch.countDown();
    }

    void await() throws InterruptedException {
        latch.await();
    }
}
