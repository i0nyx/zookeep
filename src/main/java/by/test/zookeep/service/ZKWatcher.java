package by.test.zookeep.service;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class ZKWatcher implements Watcher, AsyncCallback.StatCallback {
    CountDownLatch latch;

    public ZKWatcher() {
        latch = new CountDownLatch(1);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Watcher fired on path: " + event.getPath() + " state: " +
                event.getState() + " type " + event.getType());
        latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }
}
