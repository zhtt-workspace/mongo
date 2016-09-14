package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import zhtt.dao.templeate.DataStatisticsTemplateManager;
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

    /**
     * 返回统计树
     * @param orgId
     * @return
     */
    public JsonResponse getDataStatisticsTree(String orgId){
        BasicDBObject docTreeObj=getDocTreeNode(orgId);
        if(docTreeObj==null){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,"节点树未初始化，请创建。");
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
            return new JsonResponse(map);
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

}
