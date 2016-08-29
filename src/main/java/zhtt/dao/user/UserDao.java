package zhtt.dao.user;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import zhtt.entity.user.Organization;
import zhtt.entity.user.User;
import zhtt.service.util.TableConfig;

import java.util.List;

/**
 * Created by zhtt on 2016/8/13.
 */
@Service
public class UserDao {

    private final Class<User> $class=User.class;

    private final String  table= TableConfig.USER;

    @Autowired
    MongoOperations mongoTemplate;

    /**
     * 保存
     * @param user
     */
    public void saveUser(User user){
        mongoTemplate.save(user, TableConfig.USER);
    }

    /**
     * 更新
     * @param query
     * @param update
     * @return
     */
    public WriteResult update(Query query,Update update){
        WriteResult writeResult=mongoTemplate.updateFirst(query, update,$class, TableConfig.USER);
        return writeResult;
    }

    /**
     * 删除
     * @param query
     */
    public void delete(Query query){
        mongoTemplate.remove(query,$class, TableConfig.USER);
    }

    /**
     * 查询
     * @param query
     * @return
     */
    public User findOne(Query query){
        return mongoTemplate.findOne(query,$class, table);
    }

    /**
     * 分页查询
     * @param query
     * @param limit
     * @param skip
     * @return
     */
    public List<User> query(Query query,int limit,int skip){
        List<User> userList= mongoTemplate.find(query, $class, TableConfig.USER);
        return userList;
    }

    /**
     * 查询总数
     * @param query
     * @return
     */
    public long count(Query query){
        return mongoTemplate.count(query,TableConfig.USER);
    }
}
