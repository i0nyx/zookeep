package by.test.zookeep;

import by.test.zookeep.config.ProjectConfig;
import by.test.zookeep.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args)  {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ProjectConfig.class);
        UserService userService = appContext.getBean(UserService.class);
        userService.checkUserAndSave();
    }
}
