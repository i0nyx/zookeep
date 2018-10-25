package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import org.apache.zookeeper.KeeperException;

public interface UserService {
    void saveUser(final User user);

    boolean checkUser(final User user);

    void checkUserAndSave();
}
