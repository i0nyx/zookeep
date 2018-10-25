package by.test.zookeep.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class ZkManagerImpl implements ZkManager {
    private ZooKeeper zooKeeper;

    @Override
    public void create(final String path, final byte[] data) throws KeeperException, InterruptedException {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    @Override
    public String[] getZNodeData(final String path) {
        String result = null;
        try {
            final byte[] byteArr;
            byteArr = zooKeeper.getData(path, null, null);
            result = new String(byteArr);
        } catch (Exception e) {
            log.error("can't read object " + e);
        }
        return Objects.requireNonNull(result).split(";");
    }

    @Override
    public void closeConnection() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            log.error("Can't close zookeeper!");
        }
    }
}
