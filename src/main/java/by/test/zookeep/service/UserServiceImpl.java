package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import by.test.zookeep.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

import static by.test.zookeep.constant.Constants.SCHEDULED_TIMEOUT;
import static by.test.zookeep.constant.Constants.ZK_USER_PATH;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ZkManagerImpl zkManager;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        log.info("Save user");
    }

    @Override
    public boolean checkUser(User user) {
        User oldUser = userRepository.findByUuid(user.getUuid());
        return user.equals(oldUser);
    }

    @Override
    @Scheduled(fixedRate = SCHEDULED_TIMEOUT)
    public void checkUserAndSave() {
        User user = buildUser(getArrStr());
        if (!checkUser(user)) {
            saveUser(user);
        }
    }

    private String[] getArrStr() {
        return ((String) zkManager.getZNodeData(ZK_USER_PATH)).split(";");
    }

    private User buildUser(String[] arr) {
        return User.builder().uuid(UUID.fromString(arr[0])).name(arr[1].trim()).email(arr[2].trim()).build();
    }
}
