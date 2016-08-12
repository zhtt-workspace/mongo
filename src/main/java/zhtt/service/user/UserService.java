package zhtt.service.user;

import java.util.List;

import zhtt.entity.user.User;

/**
 * Created by zhtt on 2016/8/5.
 */
public interface UserService {
	
	public void saveUser(User user);
	public void update(User user);
	public User findUserByName(String name);
	public void removeUser(String name);
	public void updateUser(String name, String key, String value);
	public List<User> listUser();
	public List<User> query(String name,String useranme,int limit,int skip);
	public long count(String name,String username);

}
