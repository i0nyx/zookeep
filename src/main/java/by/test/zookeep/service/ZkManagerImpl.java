package by.test.zookeep.service;

import by.test.zookeep.config.ZooKeeperConnect;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

@Slf4j
public class ZkManagerImpl implements ZkManager {
    private static ZooKeeper zkeeper;
    private ZooKeeperConnect zkConnection;

    public ZkManagerImpl() {
        initialize();
    }

    private void initialize() {
        zkConnection = new ZooKeeperConnect();
        try {
            zkeeper = zkConnection.connect();
        } catch (Exception e) {
            log.error("connected false " + e);
        }
    }

    public void closeConnection() {
        zkConnection.close();
    }

    @Override
    public void create(final String path, final byte[] data) throws KeeperException, InterruptedException {
        zkeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    @Override
    public Stat getZNodeStats(final String path) throws KeeperException, InterruptedException {
        Stat stat = zkeeper.exists(path, true);
        if (stat != null) {
            log.info("Node exists and the node version is " + stat.getVersion());
        } else {
            log.info("Node does not exists");
        }
        return stat;
    }

    @Override
    public Object getZNodeData(final String path, boolean watchFlag) {
        String result = null;
        try {
            Stat stat = getZNodeStats(path);
            byte[] byteArr;
            if (stat != null) {
                if (watchFlag) {
                    ZKWatcher watch = new ZKWatcher();
                    byteArr = zkeeper.getData(path, watch, null);
                    watch.await();
                } else {
                    byteArr = zkeeper.getData(path, null, null);
                }
                result = new String(byteArr);
            } else {
                log.info("Node does not exists");
            }
        } catch (Exception e) {
            log.error("can't read object " + e);
        }
        return result;
    }
}
