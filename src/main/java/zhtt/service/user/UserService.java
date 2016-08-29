package zhtt.service.user;

import java.util.List;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import zhtt.dao.user.UserDao;
import zhtt.entity.user.User;
import zhtt.service.util.TableConfig;

/**
 * Created by zhtt on 2016/8/5.
 */
@Service(value = "userService")
public class UserService {

	@Autowired	UserDao dao;

	/**
	 * 保存
	 * @param user
	 */
	public void saveUser(User user){
		dao.saveUser(user);
	}

	/**
	 * 更新
	 * @param user
	 * @return
	 */
	public WriteResult update(User user){
		Query query=new Query();
		query.addCriteria(Criteria.where("uuid").is(user.getUuid()));
		WriteResult writeResult=dao.update(query, user.toUpdate());
		return writeResult;
	}

	/**
	 * 删除
	 * @param uuid
	 */
	public void delete(List<String> uuid){
		dao.delete(new Query(Criteria.where("uuid").in(uuid)));
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
		/** 排序 **/
		Sort.Direction direction=true? Sort.Direction.ASC: Sort.Direction.DESC;
		query.with(new Sort(direction,"name"));
		List<User> userList= dao.query(query, limit,skip );
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
		return dao.count(query);
	}

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username,String password){
		Query query=new Query();
		query.addCriteria(Criteria.where("username").regex(username));
		query.addCriteria(Criteria.where("password").regex(password));
		return dao.findOne(query);
	}


	/**
	 *

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

	 */
}
