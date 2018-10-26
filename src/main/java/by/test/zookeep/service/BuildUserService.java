package by.test.zookeep.service;

import by.test.zookeep.pojo.User;
import lombok.AllArgsConstructor;
import org.json.JSONObject;

import java.util.UUID;

import static by.test.zookeep.constant.Constants.*;

@AllArgsConstructor
public class BuildUserService {
    private ZkManagerImpl zkManager;

    User buildUserFromNodes(){
        return User.builder()
                .uuid(checkAndConvertUuid(zkManager.getZNodeData(ZK_USER_UUID)))
                .name(zkManager.getZNodeData(ZK_USER_NAME))
                .email(zkManager.getZNodeData(ZK_USER_EMAIL))
                .build();
    }

    User buildUserFromJsonNode(){
        JSONObject jsonObject = new JSONObject(zkManager.getZNodeData(ZK_USER_DATA_JSON));
        return User.builder()
                .uuid(checkAndConvertUuid(jsonObject.getString("uuid")))
                .name(jsonObject.getString("name"))
                .email(jsonObject.getString("email"))
                .build();
    }

    private UUID checkAndConvertUuid(final String uuidStr){
        if(uuidStr.isEmpty()){
            return UUID.randomUUID();
        }
        return UUID.fromString(uuidStr);
    }


}
