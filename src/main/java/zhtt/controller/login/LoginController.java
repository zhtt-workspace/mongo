package zhtt.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhtt on 2016/8/9.
 */
@Controller
public class LoginController {

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
}
