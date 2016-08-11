package zhtt.controller.template;

import com.beust.jcommander.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import zhtt.service.template.DataStatisticsTemplateService;
import zhtt.util.JsonResponse;

/**
 * Created by zhtt on 2016/8/7.
 */

@Controller
@RequestMapping(DataStatisticsTemplateController.path)
public class DataStatisticsTemplateController {

    public static final String path="/template/data-statistics";

    @Autowired private DataStatisticsTemplateService dataStatisticsTemplateService;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView(path+"/index");
        return mav;
    }

    @RequestMapping("/tree")
    @ResponseBody
    public Object tree(@RequestParam(value = "orgId",required = false)String orgId,@RequestParam(value = "tree",defaultValue = "true",required = false)String tree){
        if("true".equals(tree)){
            return dataStatisticsTemplateService.getDataStatisticsTree(orgId).getData();
        }else{
            return dataStatisticsTemplateService.getDataStatisticsTree(orgId);
        }
    }

}
