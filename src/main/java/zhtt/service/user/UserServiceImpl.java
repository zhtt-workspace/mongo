package zhtt.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import zhtt.entity.user.User;
import zhtt.service.util.TableConfig;

/**
 * Created by zhtt on 2016/8/5.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    private final Class<User> $class=User.class;
  
    @Autowired  MongoOperations mongoTemplate;

    public void saveUser(User user){
        mongoTemplate.save(user, TableConfig.USER);
    }

    public void update(User user){
        Query query=new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        mongoTemplate.updateFirst(query,user.toUpdate(),TableConfig.USER);
        //mongoTemplate.updateFirst(query,Update.update("name", user.getName()),TableConfig.USER);
    }

    public User findUserByName(String name){
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), $class, TableConfig.USER);
    }

	public void removeUser(String name) {
		mongoTemplate.remove(new Query(Criteria.where("name").is(name)),$class,TableConfig.USER);
	}

	public void updateUser(String name,String key,String value) {
		mongoTemplate.updateFirst(new Query(Criteria.where("name").is(name)), Update.update(key, value), $class);
	}

	public List<User> listUser() {
        return mongoTemplate.findAll($class, TableConfig.USER);
	}

    /**
     * 分页查询用户
     * @param name
     * @param username
     * @param limit
     * @param skip
     * @return
     */
    public List<User> query(String name,String username,int limit,int skip){
        Query query=new Query();
        query.addCriteria(Criteria.where("name").regex(name));
        query.addCriteria(Criteria.where("username").regex(username));
        query.limit(limit);
        query.skip(skip);
        Sort sort=new Sort("name");

        /** 排序 **/
        Sort.Direction direction=true? Sort.Direction.ASC: Sort.Direction.DESC;
        query.with(new Sort(direction,"name"));
        List<User> userList= mongoTemplate.find(query, $class, TableConfig.USER);
        return userList;
    }

    /**
     * 查询用户总数
     * @param name
     * @param username
     * @return
     */
    public long count(String name,String username){
        Query query=new Query();
        query.addCriteria(Criteria.where("name").regex(name));
        query.addCriteria(Criteria.where("username").regex(username));
        return mongoTemplate.count(query,TableConfig.USER);
    }
}
