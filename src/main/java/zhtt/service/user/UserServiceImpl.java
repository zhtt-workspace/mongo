package zhtt.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import zhtt.entity.user.User;

/**
 * Created by zhtt on 2016/8/5.
 */
@Service(value = "userService")   
public class UserServiceImpl implements UserService{  
      
    private static String USER_COLLECTION = "users";  
  
    @Autowired  
    MongoOperations mongoTemplate;  
      
   
    public void saveUser(User user){
        mongoTemplate.save(user, USER_COLLECTION);
    }        
    public User findUserByName(String name){
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), User.class, USER_COLLECTION);
    }
	public void removeUser(String name) {
		mongoTemplate.remove(new Query(Criteria.where("name").is(name)),User.class,USER_COLLECTION);
	}
	public void updateUser(String name,String key,String value) {
		mongoTemplate.updateFirst(new Query(Criteria.where("name").is(name)), Update.update(key, value), User.class);
		
	}
	public List<User> listUser() {
		return mongoTemplate.findAll(User.class);
	}
}
