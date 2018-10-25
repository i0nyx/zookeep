package by.test.zookeep.service;

import org.apache.zookeeper.KeeperException;

public interface ZkManager {
    void create(final String path, final byte[] data) throws KeeperException,
            InterruptedException;

    Object getZNodeData(final String path) throws KeeperException,
            InterruptedException;

    void closeConnection();
}
