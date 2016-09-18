package zhtt.dao;

import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import zhtt.dao.TableConfig;

/**
 * Created by zhtt on 2016/8/7.
 */
@Service
@Scope("singleton")
public class MongoCollectionsManager {

    @Autowired
    MongoOperations mongoTemplate;

    /**
     * 数据统计模板
     * @return
     */
    public DBCollection getDataStatisticsTemplateCollection(){
        return getDBCollection(TableConfig.DATA_STATISTICS_TEMPLATE);
    }

    /**
     * 数据统计模板
     * @return
     */
    public DBCollection getDataStatisticsCollection(){
        return getDBCollection(TableConfig.DATA_STATISTICS);
    }

    public DBCollection getDBCollection(String collectionName){
        return mongoTemplate.getCollection(collectionName);
    }
}
