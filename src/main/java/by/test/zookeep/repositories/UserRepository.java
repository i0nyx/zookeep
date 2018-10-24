package by.test.zookeep.repositories;

import by.test.zookeep.pojo.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<User, Integer> {
    User findByUuid(UUID uuid);
}
