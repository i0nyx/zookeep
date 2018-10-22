package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import by.test.zookeep.repositories.UserRepositories;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepositories repositories;

    @Override
    public void saveUser(User user) {
        repositories.save(user);
        log.info("Save new user");
    }
}
