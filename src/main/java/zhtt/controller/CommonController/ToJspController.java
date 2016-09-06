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

    @RequestMapping(value = "/{child}/index",method = RequestMethod.GET)
    public ModelAndView toTemplateIndexJsp(@PathVariable("module")String module,@PathVariable("child")String child){
        ModelAndView mav = new ModelAndView("/"+module+"/"+child+"/index");
        return mav;
    }

    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String toCreateJsp(@PathVariable("module")String module){
        return "/"+module+"/create";
    }

    @RequestMapping(value = "/{child}/create",method = RequestMethod.GET)
    public String toTempleateCreateJsp(@PathVariable("module")String module,@PathVariable("child")String child){
        return "/"+module+"/"+child+"/create";
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ModelAndView toUpdateJsp(@PathVariable("module")String module){
        ModelAndView mav = new ModelAndView("/"+module+"/update");
        return mav;
    }

    @RequestMapping(value = "/{child}/update",method = RequestMethod.GET)
    public String toTempleateUpdateJsp(@PathVariable("module")String module,@PathVariable("child")String child){
        return "/"+module+"/"+child+"/update";
    }

}
