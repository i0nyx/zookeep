package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import by.test.zookeep.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import static by.test.zookeep.constant.Constants.SCHEDULED_TIMEOUT;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BuildUserService buildUser;

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
        User user = buildUser.buildUserFromJsonNode();
        if(!checkUser(user)){
            saveUser(user);
        }
    }

}
