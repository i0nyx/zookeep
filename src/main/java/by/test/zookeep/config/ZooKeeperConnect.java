package by.test.zookeep.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static by.test.zookeep.constant.Constants.ZK_HOST;

public class ZooKeeperConnect implements AutoCloseable {
    private final CountDownLatch downLatch = new CountDownLatch(0);
    private ZooKeeper zooKeeper;

    public ZooKeeper connect() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(ZK_HOST, 10000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    downLatch.countDown();
                }
            }
        });
        downLatch.await();
        return zooKeeper;
    }

    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
