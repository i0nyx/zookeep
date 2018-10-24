package by.test.zookeep;

import by.test.zookeep.config.ProjectConfig;
import by.test.zookeep.config.ZooKeeperConfig;
import by.test.zookeep.pojo.User;
import by.test.zookeep.service.UserService;
import by.test.zookeep.service.ZkManager;
import by.test.zookeep.service.ZkManagerImpl;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

import static by.test.zookeep.constant.Constants.ZK_USER_PATH;

public class Main {

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ProjectConfig.class, ZooKeeperConfig.class);
        UserService userService = appContext.getBean(UserService.class);
        ZooKeeper zooKeeper = appContext.getBean(ZooKeeper.class);
        ZkManagerImpl zkManager = new ZkManagerImpl(zooKeeper);
        userService.saveUser(buildUser(getArrStr(zkManager)));
        zkManager.closeConnection();
    }

    private static String[] getArrStr(final ZkManager zkManager) throws KeeperException, InterruptedException {
        return ((String) zkManager.getZNodeData(ZK_USER_PATH)).split(";");
    }
    private static User buildUser(final String[] arr) {
        return User.builder().uuid(UUID.fromString(arr[0])).name(arr[1]).email(arr[2]).build();
    }
}
