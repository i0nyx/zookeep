package by.test.zookeep;

import by.test.zookeep.service.ZkManager;
import by.test.zookeep.service.ZkManagerImpl;
import org.apache.zookeeper.KeeperException;

public class Main {
    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZkManager zkm = new ZkManagerImpl();
//        zkm.create("ZNode", "test".getBytes());
     zkm.getZNodeData("/Znode", false);
    }
}
