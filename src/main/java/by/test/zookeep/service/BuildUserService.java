package by.test.zookeep.service;

import by.test.zookeep.pojo.User;

import java.util.List;

public interface BuildUserService {
    User buildUserFromNodes();

    User buildUserFromJsonNode();

    List<User> buildListUsers();
}
