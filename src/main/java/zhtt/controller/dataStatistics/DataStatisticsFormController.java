package zhtt.controller.dataStatistics;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhtt.entity.templeate.DataStatisticsTemplate;
import zhtt.entity.user.Organization;
import zhtt.service.dataStatistics.DataStatisticsFormService;
import zhtt.service.dataStatistics.DataStatisticsService;
import zhtt.util.CalendarHelp;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhtt on 2016/9/19.
 */
@Controller
@RequestMapping("/dataStatistics")
public class DataStatisticsFormController {

    @Autowired
    private DataStatisticsFormService service;

    /**
     * 说明：
     *      1、后台自动添加创建时间，前台无需添加时间
     *      2、后台将数据日期添加：时：分：秒
     * @param jsonStr
     * @param request
     * @return
     */
    @RequestMapping("/create-form")
    @ResponseBody
    public JsonResponse create(String jsonStr,HttpServletRequest request){
        try{
            Organization loginRootOrganization=(Organization)request.getSession().getAttribute("loginRootOrganization");
            if(loginRootOrganization==null){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"登录信息已过期！");
            }
            BasicDBObject data= (BasicDBObject) JSON.parse(jsonStr);
            if(checkData(data)){
                data.put(DataStatisticsTemplate.DataKey.datetime,new Date());
                String dateStr=data.getString(DataStatisticsTemplate.DataKey.date);
                if("".equals(dateStr)){
                    dateStr=CalendarHelp.getTodayDateStr();
                }
                dateStr+=" 15:30:30";
                data.put(DataStatisticsTemplate.DataKey.date, CalendarHelp.format(dateStr, CalendarHelp.EN_YMDHMS));
                service.save(data);
                return new JsonResponse(data);
            }else{
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"数据项缺失！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }

    private boolean checkData(BasicDBObject data){
        String[] keys={DataStatisticsTemplate.DataKey.createOrgId,DataStatisticsTemplate.DataKey.dataType,DataStatisticsTemplate.DataKey.date,DataStatisticsTemplate.DataKey.orgId,DataStatisticsTemplate.DataKey.orgName,DataStatisticsTemplate.DataKey.receiveOrgId};
        for(String key:keys){
            if(data.containsField(key)){
                continue;
            }else {
                return false;
            }
        }
        return true;
    }
}//{"content":"6250117d497e446285f954a2f3af3497:99,8ca633518d6d489a99ab04a67227ea69:441,d9dce5f324f6412391848158069a337c:441,16566a9775354345944614bcbcddea8c:441"}
