package zhtt.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import zhtt.entity.user.User;
import zhtt.service.user.UserService;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhtt on 2016/8/9.
 */
@Controller
public class LoginController {

    @Autowired private UserService userService;

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView("/login");
        return mav;
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("/index");
        return mav;
    }

    @RequestMapping("/login/{username}/{password}")
    @ResponseBody
    private JsonResponse loginCheck(@PathVariable("username")String username,@PathVariable("password")String password,HttpServletRequest request){
        try{
            User user=userService.login(username,password);
            request.getSession().setAttribute("loginUser",user);
            return new JsonResponse(user);
        }catch (Exception e){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }
}
