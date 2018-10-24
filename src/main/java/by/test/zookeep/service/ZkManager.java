package by.test.zookeep.service;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

public interface ZkManager {
    void create(String path, byte[] data) throws KeeperException,
            InterruptedException;

    Stat getZNodeStats(String path) throws KeeperException,
            InterruptedException;

    Object getZNodeData(final String path) throws KeeperException,
            InterruptedException;

    void closeConnection();
}
