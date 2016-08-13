package zhtt.controller.CommonController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhtt on 2016/8/13.
 */
@Controller
@RequestMapping("/{module}")
public class ToJspController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView toIndexJsp(@PathVariable("module")String module){
        ModelAndView mav = new ModelAndView("/"+module+"/index");
        return mav;
    }

    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String toCreateJsp(@PathVariable("module")String module){
        return "/"+module+"/create";
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ModelAndView toUpdateJsp(@PathVariable("module")String module){
        ModelAndView mav = new ModelAndView("/"+module+"/update");
        return mav;
    }

}
