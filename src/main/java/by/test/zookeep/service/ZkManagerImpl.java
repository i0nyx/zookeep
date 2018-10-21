package by.test.zookeep.service;

import by.test.zookeep.config.ZooKeeperConnect;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import java.util.List;

public class ZkManagerImpl implements ZkManager {
    private static ZooKeeper zkeeper;

    public ZkManagerImpl(){
        initialize();
    }

    private void initialize() {
        ZooKeeperConnect zkConnection = new ZooKeeperConnect();
        try {
            zkeeper = zkConnection.connect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        zkeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);

    }

    @Override
    public Stat getZNodeStats(String path) throws KeeperException, InterruptedException {
        Stat stat = zkeeper.exists(path, true);
        if (stat != null) {
            System.out.println("Node exists and the node version is " + stat.getVersion());
        } else {
            System.out.println("Node does not exists");
        }
        return stat;
    }

    @Override
    public Object getZNodeData(String path, boolean watchFlag) {
        String result = null;
        try {
            Stat stat = getZNodeStats(path);
            byte[] byteArr;
            if (stat != null) {
                if(watchFlag){
                    ZKWatcher watch = new ZKWatcher();
                    byteArr = zkeeper.getData(path, watch,null);
                    watch.await();
                }else{
                    byteArr = zkeeper.getData(path, null,null);
                }
                result = new String(byteArr);
            } else {
                System.out.println("Node does not exists");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void update(String path, byte[] data) {

    }

    @Override
    public List<String> getZNodeChildren(String path) {
        return null;
    }

    @Override
    public void delete(String path) {

    }
}
