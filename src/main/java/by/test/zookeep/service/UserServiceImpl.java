package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import by.test.zookeep.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;
import java.util.UUID;

import static by.test.zookeep.constant.Constants.SCHEDULED_TIMEOUT;
import static by.test.zookeep.constant.Constants.ZK_USER_PATH;

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
        User user = buildUser(getArrStr());
        if (user != null && !checkUser(user)) {
            saveUser(user);
        }
    }

    private String[] getArrStr() {
        return zkManager.getZNodeData(ZK_USER_PATH);
    }

    private User buildUser(final String[] arr) {
        return Optional.ofNullable(arr)
                .filter(a -> a.length == 3)
                .map(a -> User.builder()
                        .uuid(UUID.fromString(a[0]))
                        .name(a[1].trim())
                        .email(a[2].trim()).build())
                .orElse(null);
    }
}
