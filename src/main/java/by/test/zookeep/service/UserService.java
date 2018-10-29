package by.test.zookeep.service;

import by.test.zookeep.pojo.User;

public interface UserService {
    void saveUser(final User user);

    boolean checkUser(final User user);

    void checkUserAndSave();
}
