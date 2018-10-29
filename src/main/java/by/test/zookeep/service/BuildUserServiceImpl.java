package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class BuildUserServiceImpl implements BuildUserService {
    @Value("${zk.user.uuid}")
    private String pathUserUuid;
    @Value("${zk.user.name}")
    private String pathUserName;
    @Value("${zk.user.email}")
    private String pathUserEmail;
    @Value("${zk.user.data.json}")
    private String pathUserDataJson;
    private final ZkManagerImpl zkManager;

    public BuildUserServiceImpl(final ZkManagerImpl zkManager) {
        this.zkManager = zkManager;
    }

    @Override
    public User buildUserFromNodes() {
        return User.builder()
                .uuid(checkAndConvertUuid(zkManager.getZNodeData(pathUserUuid)))
                .name(zkManager.getZNodeData(pathUserName))
                .email(zkManager.getZNodeData(pathUserEmail))
                .build();
    }

    @Override
    public User buildUserFromJsonNode() {
        JSONObject jsonObject = new JSONObject(zkManager.getZNodeData(pathUserDataJson));
        return User.builder()
                .uuid(checkAndConvertUuid(jsonObject.getString("uuid")))
                .name(jsonObject.getString("name"))
                .email(jsonObject.getString("email"))
                .build();
    }

    @Override
    public List<User> buildListUsers() {
        List<User> users = newArrayList();
        users.add(buildUserFromNodes());
        users.add(buildUserFromJsonNode());
        return users;
    }

    private UUID checkAndConvertUuid(final String uuidStr) {
        if (uuidStr.isEmpty()) {
            return UUID.randomUUID();
        }
        return UUID.fromString(uuidStr);
    }
}