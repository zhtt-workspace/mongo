package zhtt.controller.dataStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zhtt.entity.user.Organization;
import zhtt.service.dataStatistics.DataStatisticsService;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhtt on 2016/9/19.
 */
@Controller
@RequestMapping("/dataStatistics")
public class DataStatisticsController {

    @Autowired
    private DataStatisticsService service;

    @RequestMapping("/tableForm")
    @ResponseBody
    public JsonResponse table(HttpServletRequest request){
        try{
            Organization loginRootOrganization=(Organization)request.getSession().getAttribute("loginRootOrganization");
            if(loginRootOrganization==null){
                return JsonResponse.error("登录信息已过期！");
            }
            String date= (String) request.getParameter("date");
            Map<String, Object> mapListMap=service.buildCreateTableForm(loginRootOrganization.getUuid(),date);
            return JsonResponse.success(mapListMap);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResponse.error(e.getMessage());
        }
    }
}
