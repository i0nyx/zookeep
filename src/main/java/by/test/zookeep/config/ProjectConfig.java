package by.test.zookeep.config;

import by.test.zookeep.repositories.UserRepository;
import by.test.zookeep.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("by.test.zookeep")
@AllArgsConstructor
public class ProjectConfig {
    private UserRepository userRepository;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userRepository);
    }
}
