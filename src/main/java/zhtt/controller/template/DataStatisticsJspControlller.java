package zhtt.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhtt on 2016/9/10.
 */
@Controller
@RequestMapping("/{module}")
public class DataStatisticsJspControlller {

    @RequestMapping(value = "/data-statistics/create-root",method = RequestMethod.GET)
    public String toCreateRootJsp(@PathVariable("module")String module){
        return "/"+module+"/data-statistics/create-root";
    }

    @RequestMapping(value = "/data-statistics/create-group",method = RequestMethod.GET)
    public String toCreateGroupJsp(@PathVariable("module")String module){
        return "/"+module+"/data-statistics/create-group";
    }

    @RequestMapping(value = "/data-statistics/create-field",method = RequestMethod.GET)
    public String toCreatefieldJsp(@PathVariable("module")String module){
        return "/"+module+"/data-statistics/create-field";
    }
}
