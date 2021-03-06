package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import zhtt.dao.templeate.DataStatisticsTemplateManager;
import zhtt.entity.templeate.DataStatisticsTemplate;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhtt on 2016/8/7.
 */
@Service
public class DataStatisticsTemplateService {

    @Autowired
    private MongoOperations mongoTemplate;

    @Autowired
    private DataStatisticsTemplateManager dataStatisticsTemplateManager;

    public DBObject getByUuid(String uuid){
        return dataStatisticsTemplateManager.findOne(new BasicDBObject("uuid",uuid));
    }

    /**
     * 返回统计树
     * @param orgId
     * @return
     */
    public JsonResponse getDataStatisticsTree(String orgId){
        BasicDBObject docTreeObj=getDocTreeNode(orgId);
        if(docTreeObj==null){
            return JsonResponse.error("节点树未初始化，请创建。");
        }else{
            List<String> uuidList=DataStatisticsTemplateQueryUtil.getUuidListByDocTree(docTreeObj);
            BasicDBObject query=new BasicDBObject("uuid", new BasicDBObject("$in", uuidList));
            DBObject filter=new BasicDBObject("uuid",true);
            filter.put("name",true);
            filter.put("parentId",true);
            filter.put("type",true);
            List<BasicDBObject> dbList=dataStatisticsTemplateManager.queryDBObjectList(query,filter);
            Map<String,BasicDBObject> dbMap=new HashMap<String,BasicDBObject>();
            for(BasicDBObject basicDBObject:dbList){
                String uuid=basicDBObject.getString("uuid");
                if(basicDBObject.containsField("name")){
                    dbMap.put(uuid, basicDBObject);
                }
            }
            Map<String, Object> map=DataStatisticsTemplateQueryUtil.buildTree(docTreeObj,dbMap);
            return JsonResponse.success(map);
        }
    }

    /**
     * 返回统计树
     * @param orgId
     * @return
     */
    public JsonResponse getDataStatisticsTable(String orgId){
        BasicDBObject docTreeObj=getDocTreeNode(orgId);
        if(docTreeObj==null){
            return JsonResponse.error("节点树未初始化，请创建。");
        }else{
            List<String> uuidList=DataStatisticsTemplateQueryUtil.getUuidListByDocTree(docTreeObj);
            BasicDBObject query=new BasicDBObject(DataStatisticsTemplate.FieldKey.uuid, new BasicDBObject("$in", uuidList));
            DBObject filter=new BasicDBObject(DataStatisticsTemplate.FieldKey.uuid,true);
            filter.put(DataStatisticsTemplate.FieldKey.name,true);
            filter.put(DataStatisticsTemplate.FieldKey.parentId,true);
            filter.put(DataStatisticsTemplate.FieldKey.type,true);
            filter.put(DataStatisticsTemplate.FieldKey.colspan,true);
            filter.put(DataStatisticsTemplate.FieldKey.maxNumber,true);
            filter.put(DataStatisticsTemplate.FieldKey.minNumber,true);
            filter.put(DataStatisticsTemplate.FieldKey.decimalDigits,true);
            filter.put(DataStatisticsTemplate.FieldKey.unit,true);
            filter.put(DataStatisticsTemplate.FieldKey.beyondRemind,true);
            List<BasicDBObject> dbList=dataStatisticsTemplateManager.queryDBObjectList(query,filter);
            Map<String,BasicDBObject> dbMap=new HashMap<String,BasicDBObject>();
            for(BasicDBObject basicDBObject:dbList){
                String uuid=basicDBObject.getString(DataStatisticsTemplate.FieldKey.uuid);
                if(basicDBObject.containsField(DataStatisticsTemplate.FieldKey.name)){
                    dbMap.put(uuid, basicDBObject);
                }
            }
            Map<String, Object> map = DataStatisticsTemplateQueryUtil.buildTable(docTreeObj, dbMap, DataStatisticsTemplateQueryUtil.getAllInputList(dbList));
            return JsonResponse.success(map);
        }
    }

    /**
     * 查询指定机构下的doc_tree文档记录
     * @param orgId
     * @return
     */
    public BasicDBObject getDocTreeNode(String orgId){
        DBObject query = DataStatisticsTemplateQueryUtil.getTreeDocQuery(orgId);
        return (BasicDBObject)dataStatisticsTemplateManager.findOne(query);
    }

    /**
     * 根据查询条件返回  List<BasicDBObject>
     * @param query
     * @return
     */
    public List<BasicDBObject> queryDBObjectList(DBObject query){
        return dataStatisticsTemplateManager.queryDBObjectList(query);
    }

    /**
     * 获取本单位所有显示状态的字段
     * @param orgId
     * @return
     */
    public  DBObject getAllTotalField(String orgId){
        BasicDBObject treeDoc=getDocTreeNode(orgId);
        if(treeDoc==null){
            return null;
        }
        List<String> uuidList=DataStatisticsTemplateQueryUtil.getInputShowListByChildrenTree((List<BasicDBObject>)treeDoc.get("children"),getAllFieldDoc());
        DBObject object=new BasicDBObject();
        for(String  uuid:uuidList){
            object.put(uuid, 0);
        }
        return object;
    }

    /**
     * 获取所有文档类型为Field的记录
     */
    public List<String> getAllFieldDoc(){
        BasicDBObject query=new BasicDBObject(DataStatisticsTemplate.FieldKey.type, DataStatisticsTemplate.Type.FIELD);
        DBObject filter=new BasicDBObject(DataStatisticsTemplate.FieldKey.uuid,true);
        filter.put("_id",false);
        List<BasicDBObject> dbList=dataStatisticsTemplateManager.queryDBObjectList(query,filter);
        List<String> uuidList=new ArrayList<>();
        for(BasicDBObject obj:dbList){
            uuidList.add(obj.getString(DataStatisticsTemplate.FieldKey.uuid));
        }
        return uuidList;
    }
}
