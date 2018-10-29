package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class BuildUserServiceImpl implements BuildUserService {
    private final ZkManagerImpl zkManager;
    @Value("${zk.user.uuid}")
    private String pathUserUuid;
    @Value("${zk.user.name}")
    private String pathUserName;
    @Value("${zk.user.email}")
    private String pathUserEmail;
    @Value("${zk.user.data.json}")
    private String pathUserDataJson;

    public BuildUserServiceImpl(final ZkManagerImpl zkManager) {
        this.zkManager = zkManager;
    }

    @Override
    public User buildUserFromNodes() {
        User result = null;
        if (!zkManager.getZNodeData(pathUserName).isEmpty() && !zkManager.getZNodeData(pathUserEmail).isEmpty()) {
            result = User.builder()
                    .uuid(checkAndConvertUuid(zkManager.getZNodeData(pathUserUuid)))
                    .name(zkManager.getZNodeData(pathUserName))
                    .email(zkManager.getZNodeData(pathUserEmail))
                    .build();
        }
        return result;
    }

    @Override
    public User buildUserFromJsonNode() {
        JSONObject jsonObject = checkJson(zkManager.getZNodeData(pathUserDataJson));
        ofNullable(jsonObject).orElseThrow(NullPointerException::new);
        User result = null;
        if (!jsonObject.getString("name").isEmpty() && !jsonObject.getString("email").isEmpty()) {
            result = User.builder()
                    .uuid(checkAndConvertUuid(jsonObject.getString("uuid")))
                    .name(jsonObject.getString("name"))
                    .email(jsonObject.getString("email"))
                    .build();
        }
        return result;
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

    private JSONObject checkJson(final String str) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
        } catch (JSONException e) {
            log.error("json parse error " + e);
        }
        return jsonObject;
    }
}