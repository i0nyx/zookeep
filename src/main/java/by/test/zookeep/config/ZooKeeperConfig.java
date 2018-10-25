package by.test.zookeep.config;

import by.test.zookeep.service.ZkManagerImpl;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Configuration
@PropertySource("classpath:zookeeper.properties")
@ComponentScan("by.test.zookeep")
public class ZooKeeperConfig {
    @Value("${zk.host}")
    private String zkHost;
    @Value("${zk.timeout}")
    private int zkTimeOut;
    @Value("${zk.count.down.latch}")
    private int countDownLatch;

    @Bean
    public ZooKeeper zooKeeper() throws IOException {
        CountDownLatch downLatch = new CountDownLatch(countDownLatch);
        return new ZooKeeper(zkHost, zkTimeOut, watchedEvent -> {
            if (watchedEvent.getState().equals(Watcher.Event.KeeperState.SyncConnected)) {
                downLatch.countDown();
            }
        });
    }

    @Bean
    public ZkManagerImpl zkManager() throws IOException {
        return new ZkManagerImpl(zooKeeper());
    }

}
