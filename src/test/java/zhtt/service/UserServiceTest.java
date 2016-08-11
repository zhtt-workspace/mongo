package zhtt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zhtt.entity.user.User;
import zhtt.service.user.UserService;

/**
 * Created by zhtt on 2016/8/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext*-test.xml"})
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired private UserService service;

    @Test
    public void save(){
        User user=new User();
        user.setAge(1000);
        user.setName("zhang");
        service.saveUser(user);
    }
}
