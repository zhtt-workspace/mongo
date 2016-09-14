package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import zhtt.dao.templeate.DataStatisticsTemplateManager;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import java.util.List;

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
            return new JsonResponse(docTreeObj);
        }
    }

    /**
     * 查询指定机构下的doc_tree文档记录
     * @param orgId
     * @return
     */
    public BasicDBObject getDocTreeNode(String orgId){
        BasicDBObject query = new BasicDBObject();
        if(orgId==null){
            query.put("orgId", new BasicDBObject("$exists", false));
        }else{
            query.put("orgId",orgId);
        }
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
