package by.test.zookeep.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @PrimaryKey
    private UUID uuid;
    private String name;
    private String email;
}
