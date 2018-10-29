package by.test.zookeep.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@Slf4j
@AllArgsConstructor
public class ZkManagerImpl implements ZkManager {
    private ZooKeeper zooKeeper;

    @Override
    public Stat getZNodeStat(final String path) {
        Stat stat = null;
        try {
            stat = zooKeeper.exists(path, false);
        } catch (KeeperException | InterruptedException e) {
            log.error("zookeeper exception " + e);
        }
        return stat;
    }

    @Override
    public String getZNodeData(final String path) {
        String result = null;
        try {
            final byte[] byteArr;
            byteArr = zooKeeper.getData(path, null, null);
            result = new String(byteArr);
        } catch (Exception e) {
            log.error("can't read object " + e);
        }
        return result;
    }

    @Override
    public List<String> getZNodeChildren(final String path) throws KeeperException, InterruptedException {
        Stat status = getZNodeStat(path);
        List<String> result = null;
        if(!isNull(status)){
            result = zooKeeper.getChildren(path, false);
        }
        return result;
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
