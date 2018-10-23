package by.test.zookeep.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static by.test.zookeep.constant.Constants.ZK_HOST;
import static by.test.zookeep.constant.Constants.ZK_TIMEOUT;

@Slf4j
public class ZooKeeperConnect {
    private final CountDownLatch downLatch = new CountDownLatch(1);
    private ZooKeeper zooKeeper;

    public ZooKeeper connect() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(ZK_HOST, ZK_TIMEOUT, watchedEvent -> {
            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                downLatch.countDown();
            }
        });
        downLatch.await();
        return zooKeeper;
    }

    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            log.error("can't close zookeeper " + e);
        }
    }
}
