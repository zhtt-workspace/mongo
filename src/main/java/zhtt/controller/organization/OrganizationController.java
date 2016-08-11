package zhtt.controller.organization;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhtt on 2016/8/11.
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("/organization/index");
        return mav;
    }
}
