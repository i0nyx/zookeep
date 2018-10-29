package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import lombok.AllArgsConstructor;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

import static by.test.zookeep.constant.Constants.*;
import static com.google.common.collect.Lists.newArrayList;

@AllArgsConstructor
public class BuildUserServiceImpl implements BuildUserService {
    private ZkManagerImpl zkManager;

    @Override
    public User buildUserFromNodes() {
        return User.builder()
                .uuid(checkAndConvertUuid(zkManager.getZNodeData(ZK_USER_UUID)))
                .name(zkManager.getZNodeData(ZK_USER_NAME))
                .email(zkManager.getZNodeData(ZK_USER_EMAIL))
                .build();
    }

    @Override
    public User buildUserFromJsonNode() {
        JSONObject jsonObject = new JSONObject(zkManager.getZNodeData(ZK_USER_DATA_JSON));
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
