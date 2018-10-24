package by.test.zookeep.repositories;

import by.test.zookeep.pojo.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositories extends CassandraRepository<User, Integer> {
}
