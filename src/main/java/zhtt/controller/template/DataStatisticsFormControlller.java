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
import java.util.HashMap;
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

    @RequestMapping(value="/data-statistics/show/{uuid}/{state}")
    @ResponseBody
    private Object changeState(@PathVariable("uuid")String uuid,@PathVariable("state")boolean state,HttpServletRequest request){
        try{
            Organization loginRootOrganization=(Organization)request.getSession().getAttribute("loginRootOrganization");
            if(loginRootOrganization==null){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"登录信息已过期！");
            }
            if(uuid==null&&uuid.length()==0){
                return new JsonResponse(JsonResponseStatusEnum.ERROR,"请示路径无效");
            }else{
                dataStatisticsTemplateFormService.updateTreeState(loginRootOrganization.getUuid(),uuid, state);
                Map<String,String> map=new HashMap<String,String>();
                map.put("uuid",uuid);
                return new JsonResponse(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }

    @RequestMapping(value = "/data-statistics/delete/{uuid}/{parentId}",method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable("uuid")String uuid,@PathVariable("parentId")String parentId,HttpServletRequest request){
        Organization loginRootOrganization=(Organization)request.getSession().getAttribute("loginRootOrganization");
        if(loginRootOrganization==null){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,"登录信息已过期！");
        }
        if(uuid==null&&uuid.length()==0){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,"请示路径无效");
        }else{
            dataStatisticsTemplateFormService.deleteAndModifyDocTree(uuid, parentId,loginRootOrganization.getUuid(), false);
            Map<String,String> map=new HashMap<String,String>();
            map.put("uuid",uuid);
            map.put("parentId",parentId);
            return new JsonResponse(map);
        }
    }

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

            DBObject dbObj=createFieldDoc(map);
            dbObj.put(DataStatisticsTemplate.FieldKey.orgId, loginRootOrganization.getUuid());
            if(map.containsKey("uuid")&&map.get("uuid")!=null&&map.get("uuid").length()>0){
                DBObject dbObjQuery=createFieldDocQuery(map);
                dbObj.put("uuid",map.get("uuid"));
                dataStatisticsTemplateFormService.update(dbObjQuery, dbObj);
            }else{
                dataStatisticsTemplateFormService.saveAndModifyTreeDoc(dbObj);
            }
            return new JsonResponse(dbObj);
        }catch (Exception e){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
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
            if(map.containsKey("uuid")&&map.get("uuid")!=null&&map.get("uuid").length()>0){
                DBObject dbObjQuery=createGroupDocQuery(map);
                dbObj.put("uuid",map.get("uuid"));
                dataStatisticsTemplateFormService.update(dbObjQuery, dbObj);
            }else{
                dataStatisticsTemplateFormService.saveAndModifyTreeDoc(dbObj);
            }
            return new JsonResponse(dbObj);
        }catch (Exception e){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
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
            if(map.containsKey("uuid")&&map.get("uuid")!=null&&map.get("uuid").length()>0){

            }else{
                DBObject treeObj=createRootDocTreeDoc(map);
                treeObj.put(DataStatisticsTemplate.RootKey.orgId,loginRootOrganization.getUuid());
                DBObject treeObjQuery=createRootDocTreeDocQuery(map);
                treeObjQuery.put(DataStatisticsTemplate.RootKey.orgId,loginRootOrganization.getUuid());
                dataStatisticsTemplateFormService.updateOrInsert(treeObjQuery, treeObj);
            }
            dbObj.put(DataStatisticsTemplate.RootKey.orgId,loginRootOrganization.getUuid());
            DBObject dbObjQuery=createRootDocQuery(map);
            dbObjQuery.put(DataStatisticsTemplate.RootKey.orgId,loginRootOrganization.getUuid());
            dataStatisticsTemplateFormService.updateOrInsert(dbObjQuery, dbObj);
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
     * 创建树文档
     * @param map
     * @return
     */
    private DBObject createRootDocTreeDoc(Map<String,String> map){
        DBObject treeObj=new BasicDBObject();
        treeObj.put(DataStatisticsTemplate.RootKey.datetime, new Date());
        return treeObj;
    }

    /**
     * 创建树文档查询条件
     * @param map
     * @return
     */
    private DBObject createRootDocTreeDocQuery(Map<String,String> map){
        DBObject treeObjQuery=new BasicDBObject();
        treeObjQuery.put("node_id", "doc_tree");
        return treeObjQuery;
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
        /*dbObjQuery.put(DataStatisticsTemplate.GroupKey.parentId,map.get(DataStatisticsTemplate.GroupKey.parentId));
        dbObjQuery.put(DataStatisticsTemplate.GroupKey.name,map.get(DataStatisticsTemplate.GroupKey.name));
        dbObjQuery.put(DataStatisticsTemplate.GroupKey.type,DataStatisticsTemplate.Type.GROUP);*/
        dbObjQuery.put(DataStatisticsTemplate.GroupKey.uuid,map.get(DataStatisticsTemplate.GroupKey.uuid));
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
        dbObjQuery.put(DataStatisticsTemplate.FieldKey.uuid,map.get(DataStatisticsTemplate.FieldKey.uuid));
        //dbObjQuery.put(DataStatisticsTemplate.FieldKey.parentId,map.get(DataStatisticsTemplate.FieldKey.parentId));
        //dbObjQuery.put(DataStatisticsTemplate.FieldKey.name,map.get(DataStatisticsTemplate.FieldKey.name));
        //dbObjQuery.put(DataStatisticsTemplate.FieldKey.type,DataStatisticsTemplate.Type.FIELD);
        return dbObjQuery;
    }
}
