package by.test.zookeep.service;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public interface ZkManager {
    Stat getZNodeStat(final String path) throws KeeperException, InterruptedException;

    String getZNodeData(final String path) throws KeeperException, InterruptedException;

    List<String> getZNodeChildren(final String path) throws KeeperException, InterruptedException;

    void closeConnection();
}
