package by.test.zookeep.pojo;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Data
public class User {
    @PrimaryKey
    private UUID uuid;
    private String name;
    private String email;
}
