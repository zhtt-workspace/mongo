package zhtt.service.util;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhtt on 2016/8/7.
 */
@Service
public class DataStatisticsTemplateManager {

    @Autowired
    MongoCollectionsManager mongoCollectionsManager;

    public WriteResult save(DBObject obj){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        WriteResult result=collection.save(obj);
        return result;
    }

    public DBObject query(DBObject query){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        return collection.findOne(query);
    }
}
