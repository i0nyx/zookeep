package by.test.zookeep.repositories;

import by.test.zookeep.pojo.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserRepositories extends CassandraRepository<User, Integer> {
}
