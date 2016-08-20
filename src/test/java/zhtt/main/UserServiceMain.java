package zhtt.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zhtt.entity.user.User;
import zhtt.service.user.UserService;

import java.util.List;
import java.util.Random;

/**
 * Created by zhtt on 2016/8/7.
 */
public class UserServiceMain {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                new String[] { "applicationContext-test.xml", "applicationContext-mongo-config-test.xml", "applicationContext-servlet-test.xml"});
        UserService userService = (UserService) ac.getBean("userService");
        count(userService);
	}

    public static void count(UserService userService){
        String name="an";
        String username="username";
        long total=userService.count(name,username);
        System.out.println("total:\t"+total);
    }

    public static void query(UserService userService){
        String name="123";
        String username="admin";
        int limit=10;
        int skip=0;
        List<User> userList=userService.query(name, username, limit, skip);
        System.out.println(userList);
    }
    public static void save(UserService userService){
        User user=new User();
        user.setUsername("username"+Math.random());
        user.setPassword("password"+Math.random());
        Random rand = new Random();
        int randNum = rand.nextInt(50)+50;
        user.setAge(randNum);
        user.setName("zhang");
        userService.saveUser(user);
    }
}
