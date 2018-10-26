package by.test.zookeep.config;

import by.test.zookeep.repositories.UserRepository;
import by.test.zookeep.service.BuildUserService;
import by.test.zookeep.service.UserServiceImpl;
import by.test.zookeep.service.ZkManagerImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("by.test.zookeep")
@AllArgsConstructor
@EnableScheduling
public class ProjectConfig {
    private UserRepository userRepository;
    private ZkManagerImpl zkManager;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userRepository, buildUserService());
    }

    @Bean
    public BuildUserService buildUserService(){
        return new BuildUserService(zkManager);
    }
}
