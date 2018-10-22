package by.test.zookeep;

import by.test.zookeep.config.ProjectConfig;
import by.test.zookeep.pojo.User;
import by.test.zookeep.service.UserService;
import by.test.zookeep.service.ZkManager;
import by.test.zookeep.service.ZkManagerImpl;
import org.apache.zookeeper.KeeperException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

import static by.test.zookeep.constant.Constants.USER_ZNODE;

public class Main {

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZkManager zkManager = new ZkManagerImpl();
        String[] arrStr = ((String) zkManager.getZNodeData(USER_ZNODE, true)).split(";");
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ProjectConfig.class);
        UserService userService = appContext.getBean(UserService.class);
        userService.saveUser(buildUser(arrStr));
        ((ZkManagerImpl) zkManager).closeConnection();
    }

    private static User buildUser(String[] arr) {
        return User.builder().uuid(UUID.fromString(arr[0])).name(arr[1]).email(arr[2]).build();
    }
}
