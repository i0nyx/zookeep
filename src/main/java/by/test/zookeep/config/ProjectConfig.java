package by.test.zookeep.config;

import by.test.zookeep.repositories.UserRepositories;
import by.test.zookeep.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("by.test.zookeep")
@AllArgsConstructor
public class ProjectConfig {
    private UserRepositories repositories;

    @Bean
    public UserServiceImpl userService(){
        return new UserServiceImpl(repositories);
    }
}
