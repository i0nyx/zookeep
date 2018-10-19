package by.test.zookeep.service;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public interface ZkManager {
    void create(String path, byte[] data) throws KeeperException,
            InterruptedException;

    Stat getZNodeStats(String path) throws KeeperException,
            InterruptedException;

    Object getZNodeData(String path, boolean watchFlag) throws KeeperException,
            InterruptedException;

    void update(String path, byte[] data) throws KeeperException,
            InterruptedException;


    List<String> getZNodeChildren(String path) throws KeeperException,
            InterruptedException;

    void delete(String path) throws KeeperException,
            InterruptedException;
}
