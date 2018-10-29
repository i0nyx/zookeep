package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import by.test.zookeep.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static by.test.zookeep.constant.Constants.SCHEDULED_TIMEOUT;
import static java.util.Objects.isNull;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BuildUserService buildUser;

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
        buildUser.buildListUsers().stream()
                .filter(user -> !isNull(user))
                .filter(user -> !checkUser(user))
                .forEach(this::saveUser);
    }
}