package zhtt.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import zhtt.entity.user.User;
import zhtt.service.user.UserService;

/**
 * Created by zhtt on 2016/8/5.
 */
@Controller
@RequestMapping("/users") 
public class UserController {
	
	
	@Autowired  
    private UserService userService;
      
    @RequestMapping("/index")  
    public ModelAndView index(){  
        ModelAndView mav = new ModelAndView("/users/index");
        return mav;  
    }  
    @RequestMapping("/addpage")  
    public String addUser(User user){
        //ModelAndView mav = new ModelAndView("/add");            
        return "add";  
    } 
    @RequestMapping(value ="/save")  
    public ModelAndView saveUser(User user){
        ModelAndView mav = new ModelAndView("/result");            
        System.out.println("save:"+ user);
        mav.addObject("message","添加成功");
        userService.saveUser(user);
        return mav;  
    }  
    
    @RequestMapping("/findpage")  
    public ModelAndView findPage(User user){
        ModelAndView mav = new ModelAndView("/get");
        return mav;  
    }  
      
    @RequestMapping("/find")  
    public ModelAndView findUser(User user){
        user = userService.findUserByName(user.getName());
        System.out.println("find:"+ user);
        ModelAndView mav = new ModelAndView("/user");
        mav.addObject("user", user);
        return mav;  
    }  
    @RequestMapping("/findAll")  
    public ModelAndView findAllUser(User user){
        List<User> allUser = userService.listUser();
        ModelAndView mav = new ModelAndView("/findAll");
        mav.addObject("allUser", allUser);
        return mav;  
    }  
    @RequestMapping("/deletepage")  
    public ModelAndView deletePage(User user){
        ModelAndView mav = new ModelAndView("/delete");
        return mav;  
    } 
    @RequestMapping("/delete")  
    public ModelAndView delete(User user){
        ModelAndView mav = new ModelAndView("/index");
        userService.removeUser(user.getName());
        return mav;  
    } 
    
    @RequestMapping("/modfigPage")  
    public ModelAndView modfigPage(User user){
        ModelAndView mav = new ModelAndView("/modfig");
        return mav;  
    } 
    
    @RequestMapping("/modfig")  
    public ModelAndView modfig(User user,String key,String value){
        ModelAndView mav = new ModelAndView("/index");
        userService.updateUser(user.getName(),key,value);
        return mav;  
    }
    
//    public static void main(String[] args) {
//    
//    	ApplicationContext ac = new ClassPathXmlApplicationContext("WebContent/WEB-INF/applicationContext.xml");
//    	UserService userService = (UserService) ac.getBean("UserService");
//    	Users user=new Users();
//    	user.setAge(1000);
//    	user.setName("zhang");
//    	userService.saveUser(user);
//	}

}
