package zhtt.dao.dataStatistics;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.MongoCollectionsManager;
import zhtt.dao.MongoQueryUtil;

import java.util.List;

/**
 * Created by zhtt on 2016/9/18.
 */
@Service
public class DataStatisticsManager {

    @Autowired
    private MongoCollectionsManager mongoCollectionsManager;

    /**
     * 保存
     * @param obj
     * @return
     */
    public WriteResult save(DBObject obj){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsCollection();
        WriteResult result=collection.save(obj);
        return result;
    }

    /**
     * 保存
     * @param obj
     * @return
     */
    public WriteResult saveOrUpdate(DBObject query,DBObject obj){
        WriteResult result=update(query,obj,true,false);
        return result;
    }

    /**
     *
     * @param find:查询条件
     * @param update：更新后新值
     * @return
     */
    public WriteResult update(DBObject find,DBObject update){
        WriteResult result=update(find, update, false, false);
        return result;
    }

    /**
     *
     * @param find:查询条件
     * @param update：更新后新值
     * @param insert：无此记录时是否插入
     * @param multi：如果多条记录是否全部更新
     * @return
     */
    public WriteResult update(DBObject find,DBObject update,boolean insert,boolean multi){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsCollection();
        WriteResult result=collection.update(find, update, insert, multi, WriteConcern.SAFE);
        return result;
    }

    /**
     * 分组
     * @param groupField
     * @param filterCond
     * @param initialField
     * @param finalizer
     * @return
     */
    public List<BasicDBObject> group(DBObject groupField, DBObject filterCond, DBObject initialField,  String finalizer){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsCollection();
        return (List<BasicDBObject>)collection.group(groupField, filterCond, initialField,  finalizer);
    }

    /**
     *
     * @param query：查询条件
     * @param filterField：指定返回字段
     * @return
     */
    public List<BasicDBObject> query(DBObject query,DBObject filterField){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsCollection();
        return MongoQueryUtil.queryDBObjectList(query, filterField, collection);
    }

    public List<BasicDBObject> query(DBObject query){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsCollection();
        return MongoQueryUtil.queryDBObjectList(query,collection);
    }
}
