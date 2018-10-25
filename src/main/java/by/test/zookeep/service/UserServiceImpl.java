package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import by.test.zookeep.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

import static by.test.zookeep.constant.Constants.*;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ZkManagerImpl zkManager;

    @Override
    public void saveUser(final User user) {
        userRepository.save(user);
        log.info("Save user");
    }

    @Override
    public boolean checkUser(final User user) {
        User oldUser = userRepository.findByUuid(user.getUuid());
        return user.equals(oldUser);
    }

    @Override
    @Scheduled(fixedRate = SCHEDULED_TIMEOUT)
    public void checkUserAndSave() {
        User user = buildUser();
        if(!checkUser(user)){
            saveUser(user);
        }
    }

    private User buildUser(){
        UUID uuid = UUID.fromString(zkManager.getZNodeData(ZK_USER_UUID));
        return User.builder()
                .uuid(uuid)
                .name(zkManager.getZNodeData(ZK_USER_NAME))
                .email(zkManager.getZNodeData(ZK_USER_EMAIL))
                .build();
    }
}
