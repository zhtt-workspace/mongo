package zhtt.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import zhtt.entity.user.User;
import zhtt.service.user.UserService;
import zhtt.util.JsonTableResponse;

/**
 * Created by zhtt on 2016/8/5.
 */
@Controller
@RequestMapping("/user")
public class UserController {
	

	@Autowired
    private UserService userService;
      
    @RequestMapping("/index")  
    public ModelAndView index(){  
        ModelAndView mav = new ModelAndView("/user/index");
        return mav;  
    }

    @RequestMapping("/query")
    @ResponseBody
    public JsonTableResponse query(String name,String username,int limit,int skip){
        List<User> userList=userService.query(name,username,limit,skip);
        long total=userService.count(name,username);
        return new JsonTableResponse(total,userList);
    }
    @RequestMapping("/addpage")  
    public String addUser(User user){
        //ModelAndView mav = new ModelAndView("/add");            
        return "/user/add";
    } 
    @RequestMapping(value ="/save")  
    public ModelAndView saveUser(User user){
        ModelAndView mav = new ModelAndView("/user/result");
        System.out.println("save:"+ user);
        mav.addObject("message","添加成功");
        userService.saveUser(user);
        return mav;  
    }  
    
    @RequestMapping("/findpage")  
    public ModelAndView findPage(User user){
        ModelAndView mav = new ModelAndView("/user/get");
        return mav;  
    }  
      
    @RequestMapping("/find")  
    public ModelAndView findUser(User user){
        user = userService.findUserByName(user.getName());
        System.out.println("find:"+ user);
        ModelAndView mav = new ModelAndView("/user/user");
        mav.addObject("user", user);
        return mav;  
    }  
    @RequestMapping("/findAll")  
    public ModelAndView findAllUser(User user){
        List<User> allUser = userService.listUser();
        ModelAndView mav = new ModelAndView("/user/findAll");
        System.out.println("allUser:"+allUser);
        mav.addObject("allUser", allUser);
        return mav;  
    }  
    @RequestMapping("/deletepage")  
    public ModelAndView deletePage(User user){
        ModelAndView mav = new ModelAndView("/user/delete");
        return mav;  
    } 
    @RequestMapping("/delete")  
    public ModelAndView delete(User user){
        ModelAndView mav = new ModelAndView("/user/index");
        userService.removeUser(user.getName());
        return mav;  
    } 
    
    @RequestMapping("/modfigPage")  
    public ModelAndView modfigPage(User user){
        ModelAndView mav = new ModelAndView("/user/modfig");
        return mav;  
    } 
    
    @RequestMapping("/modfig")  
    public ModelAndView modfig(User user,String key,String value){
        ModelAndView mav = new ModelAndView("/user/index");
        userService.updateUser(user.getName(),key,value);
        return mav;  
    }
    


}
