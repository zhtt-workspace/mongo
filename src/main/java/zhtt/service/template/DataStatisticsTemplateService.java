package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import zhtt.service.util.DataStatisticsTemplateManager;
import zhtt.service.util.MongoCollectionsManager;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by zhtt on 2016/8/7.
 */
@Service
public class DataStatisticsTemplateService {

    @Autowired
    MongoOperations mongoTemplate;

    @Autowired
    DataStatisticsTemplateManager dataStatisticsTemplateManager;

    /**
     * 初始化 ：doc_tree节点
     * @param name
     * @return
     */
    public WriteResult init(String name,int cols){
        DBObject obj=new BasicDBObject();
        obj.put("name",name);//树结构名称
        obj.put("children",null);//子节点标识
        obj.put("cols",cols);//形成table表格时的总列数
        obj.put("code","doc_tree");//code标识
        return dataStatisticsTemplateManager.save(obj);
    }

    /**
     * 返回统计树
     * @param orgId
     * @return
     */
    public JsonResponse getDataStatisticsTree(String orgId){
        BasicDBObject docTreeObj=getDocTreeNode(orgId);
        if(docTreeObj==null){
            return new JsonResponse(JsonResponseStatusEnum.SUCCESS,"节点树未初始化，请创建。");
        }
        return new JsonResponse(JsonResponseStatusEnum.ERROR,"构建树遇到错误！");
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
        return (BasicDBObject)dataStatisticsTemplateManager.query(query);
    }


}
