package by.test.zookeep.service;

import by.test.zookeep.pojo.User;

public interface UserService {
    void saveUser(User user);
    boolean checkUser(User user);
}
