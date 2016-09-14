package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Created by zhtt on 2016/9/14.
 */
public class DataStatisticsTemplateQueryUtil {

    /**
     * 得到查询树文档的查询条件
     * @return
     */
    public static DBObject getTreeDocQuery(String orgId){
        DBObject query = new BasicDBObject();
        query.put("node_id", "doc_tree");
        query.put("orgId", orgId);
        return query;
    }
}
