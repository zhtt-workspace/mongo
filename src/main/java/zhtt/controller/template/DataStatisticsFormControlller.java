package zhtt.controller.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhtt.entity.templeate.DataStatisticsTemplate;
import zhtt.entity.user.Organization;
import zhtt.entity.user.User;
import zhtt.service.template.DataStatisticsTemplateFormService;
import zhtt.service.user.OrganizationService;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;
import zhtt.util.ProjectUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhtt on 2016/9/10.
 */
@Controller
@RequestMapping("/template")
public class DataStatisticsFormControlller {

    @Autowired
    private DataStatisticsTemplateFormService dataStatisticsTemplateFormService;

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/data-statistics/field-form",method = RequestMethod.POST)
    @ResponseBody
    private JsonResponse fieldform(@RequestParam Map<String,String> map,HttpServletRequest request){
        try{
            if(!map.containsKey("name")){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请输入名称！");
            }
            if(!map.containsKey("colspan")){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请输入总列数！");
            }
            Organization loginRootOrganization=(Organization)request.getSession().getAttribute("loginRootOrganization");
            if(loginRootOrganization==null){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"登录信息已过期！");
            }

            DBObject dbObj=createGroupDoc(map);
            dbObj.put(DataStatisticsTemplate.FieldKey.orgId,loginRootOrganization.getUuid());

            DBObject dbObjQuery=createGroupDocQuery(map);
            dbObjQuery.put(DataStatisticsTemplate.FieldKey.orgId,loginRootOrganization.getUuid());
            dataStatisticsTemplateFormService.save(dbObjQuery, dbObj);

            return new JsonResponse(map);
        }catch (Exception e){
            return new JsonResponse();
        }
    }

    @RequestMapping(value = "/data-statistics/group-form",method = RequestMethod.POST)
    @ResponseBody
    private JsonResponse groupform(@RequestParam Map<String,String> map,HttpServletRequest request){
        try{
            if(!map.containsKey("name")){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请输入名称！");
            }
            if(!map.containsKey("colspan")){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请输入总列数！");
            }
            Organization loginRootOrganization=(Organization)request.getSession().getAttribute("loginRootOrganization");
            if(loginRootOrganization==null){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"登录信息已过期！");
            }
            DBObject dbObj=createGroupDoc(map);
            dbObj.put(DataStatisticsTemplate.GroupKey.orgId,loginRootOrganization.getUuid());

            DBObject dbObjQuery=createGroupDocQuery(map);
            dbObjQuery.put(DataStatisticsTemplate.GroupKey.orgId,loginRootOrganization.getUuid());
            dataStatisticsTemplateFormService.save(dbObjQuery, dbObj);
            return new JsonResponse(map);
        }catch (Exception e){
            return new JsonResponse();
        }
    }

    @RequestMapping(value = "/data-statistics/root-form",method = RequestMethod.POST)
    @ResponseBody
    private JsonResponse rootform(@RequestParam Map<String,String> map,HttpServletRequest request){
        try{
            if(!map.containsKey("name")){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请输入名称！");
            }
            if(!map.containsKey("colspan")){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请输入列数！");
            }
            Organization loginRootOrganization=(Organization)request.getSession().getAttribute("loginRootOrganization");
            if(loginRootOrganization==null){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"登录信息已过期！");
            }

            DBObject dbObj=createRootDoc(map);
            dbObj.put(DataStatisticsTemplate.RootKey.orgId,loginRootOrganization.getUuid());
            DBObject dbObjQuery=createRootDocQuery(map);
            dbObjQuery.put(DataStatisticsTemplate.RootKey.orgId,loginRootOrganization.getUuid());
            dataStatisticsTemplateFormService.updateOrInsert(dbObjQuery, dbObj);

            DBObject treeObj=new BasicDBObject();
            treeObj.put(DataStatisticsTemplate.RootKey.datetime, new Date());
            treeObj.put("node_id", "doc_tree");
            treeObj.put(DataStatisticsTemplate.RootKey.uuid, "doc_tree");
            treeObj.put(DataStatisticsTemplate.RootKey.orgId,loginRootOrganization.getUuid());

            DBObject treeObjQuery=new BasicDBObject();
            treeObjQuery.put("node_id", "doc_tree");
            treeObjQuery.put(DataStatisticsTemplate.RootKey.uuid, "doc_tree");
            treeObjQuery.put(DataStatisticsTemplate.RootKey.orgId,loginRootOrganization.getUuid());
            dataStatisticsTemplateFormService.updateOrInsert(treeObjQuery, treeObj);
            return new JsonResponse(dbObj);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }

    /**
     * 创建根节点的文档节点
     * @param map
     * @return
     */
    private DBObject createRootDoc(Map<String,String> map){
        DBObject dbObj=new BasicDBObject();
        dbObj.put(DataStatisticsTemplate.RootKey.datetime, new Date());
        dbObj.put(DataStatisticsTemplate.RootKey.type,DataStatisticsTemplate.Type.ROOT);
        dbObj.put(DataStatisticsTemplate.RootKey.colspan,map.get(DataStatisticsTemplate.RootKey.colspan));
        dbObj.put(DataStatisticsTemplate.RootKey.name,map.get(DataStatisticsTemplate.RootKey.name));
        dbObj.put(DataStatisticsTemplate.RootKey.uuid, "doc_tree");
        return dbObj;
    }

    /**
     * 创建根节点的文档节点查询条件
     * @param map
     * @return
     */
    private DBObject createRootDocQuery(Map<String,String> map){
        DBObject dbObjQuery=new BasicDBObject();
        dbObjQuery.put(DataStatisticsTemplate.RootKey.type,DataStatisticsTemplate.Type.ROOT);
        dbObjQuery.put(DataStatisticsTemplate.RootKey.uuid, "doc_tree");
        return dbObjQuery;
    }

    /**
     * 创建分组节点文档
     * @param map
     * @return
     */
    private DBObject createGroupDoc(Map<String,String> map){
        DBObject dbObj=new BasicDBObject();
        dbObj.put(DataStatisticsTemplate.GroupKey.datetime, new Date());
        dbObj.put(DataStatisticsTemplate.GroupKey.type,DataStatisticsTemplate.Type.GROUP);
        dbObj.put(DataStatisticsTemplate.GroupKey.colspan,map.get(DataStatisticsTemplate.GroupKey.colspan));
        dbObj.put(DataStatisticsTemplate.GroupKey.name,map.get(DataStatisticsTemplate.GroupKey.name));
        dbObj.put(DataStatisticsTemplate.GroupKey.parentId,map.get(DataStatisticsTemplate.GroupKey.parentId));
        dbObj.put(DataStatisticsTemplate.GroupKey.uuid, ProjectUtil.randomUUID());
        return dbObj;
    }

    /**
     * 创建分组节点文档
     * @param map
     * @return
     */
    private DBObject createGroupDocQuery(Map<String,String> map){
        DBObject dbObjQuery=new BasicDBObject();
        dbObjQuery.put(DataStatisticsTemplate.GroupKey.parentId,map.get(DataStatisticsTemplate.GroupKey.parentId));
        dbObjQuery.put(DataStatisticsTemplate.GroupKey.name,map.get(DataStatisticsTemplate.GroupKey.name));
        dbObjQuery.put(DataStatisticsTemplate.GroupKey.type,DataStatisticsTemplate.Type.GROUP);
        return dbObjQuery;
    }

    /**
     * 创建字段节点文档
     * @param map
     * @return
     */
    private DBObject createFieldDoc(Map<String,String> map){
        DBObject dbObj=new BasicDBObject();
        dbObj.put(DataStatisticsTemplate.FieldKey.datetime, new Date());
        dbObj.put(DataStatisticsTemplate.FieldKey.type,DataStatisticsTemplate.Type.FIELD);
        dbObj.put(DataStatisticsTemplate.FieldKey.colspan,map.get(DataStatisticsTemplate.FieldKey.colspan));
        dbObj.put(DataStatisticsTemplate.FieldKey.name,map.get(DataStatisticsTemplate.FieldKey.name));
        dbObj.put(DataStatisticsTemplate.FieldKey.parentId,map.get(DataStatisticsTemplate.FieldKey.parentId));
        dbObj.put(DataStatisticsTemplate.FieldKey.uuid, ProjectUtil.randomUUID());
        dbObj.put(DataStatisticsTemplate.FieldKey.unit,map.get(DataStatisticsTemplate.FieldKey.unit));
        dbObj.put(DataStatisticsTemplate.FieldKey.decimalDigits,map.get(DataStatisticsTemplate.FieldKey.decimalDigits));
        dbObj.put(DataStatisticsTemplate.FieldKey.maxNumber,map.get(DataStatisticsTemplate.FieldKey.maxNumber));
        dbObj.put(DataStatisticsTemplate.FieldKey.minNumber,map.get(DataStatisticsTemplate.FieldKey.minNumber));
        dbObj.put(DataStatisticsTemplate.FieldKey.beyondRemind,map.get(DataStatisticsTemplate.FieldKey.beyondRemind));
        return dbObj;
    }

    /**
     * 创建字段节点文档
     * @param map
     * @return
     */
    private DBObject createFieldDocQuery(Map<String,String> map){
        DBObject dbObjQuery=new BasicDBObject();
        dbObjQuery.put(DataStatisticsTemplate.FieldKey.parentId,map.get(DataStatisticsTemplate.FieldKey.parentId));
        dbObjQuery.put(DataStatisticsTemplate.FieldKey.name,map.get(DataStatisticsTemplate.FieldKey.name));
        dbObjQuery.put(DataStatisticsTemplate.FieldKey.type,DataStatisticsTemplate.Type.FIELD);
        return dbObjQuery;
    }
}
