package zhtt.controller.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhtt.service.template.DataStatisticsTemplateFormService;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhtt on 2016/9/10.
 */
@Controller
@RequestMapping("/template")
public class DataStatisticsFormControlller {

    @Autowired
    private DataStatisticsTemplateFormService dataStatisticsTemplateFormService;

    @RequestMapping(value = "/data-statistics/field-form",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse fieldform(@RequestParam Map<String,String> map){
        try{
            DBObject dbObj=new BasicDBObject();
            dbObj.put("datetime", new Date());
            dbObj.put("type","field");

            return new JsonResponse(map);
        }catch (Exception e){
            return new JsonResponse();
        }
    }

    @RequestMapping(value = "/data-statistics/group-form",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse groupform(@RequestParam Map<String,String> map){
        try{
            return new JsonResponse(map);
        }catch (Exception e){
            return new JsonResponse();
        }
    }

    @RequestMapping(value = "/data-statistics/root-form",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse rootform(@RequestParam Map<String,String> map){
        try{
            if(!map.containsKey("name")){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请输入名称！");
            }
            if(!map.containsKey("colspan")){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请输入总列数！");
            }
            DBObject dbObj=new BasicDBObject();
            dbObj.put("datetime", new Date());
            dbObj.put("type","root");
            dbObj.put("colspan",map.get("colspan"));
            dbObj.put("name",map.get("name"));
            dbObj.put("parentId","doc_tree");
            dbObj.put("uuid", UUID.randomUUID().toString().replace("-",""));
            return new JsonResponse(dbObj);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }
}
