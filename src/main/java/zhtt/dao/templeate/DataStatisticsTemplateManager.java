package zhtt.dao.templeate;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.MongoCollectionsManager;
import zhtt.dao.MongoQueryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhtt on 2016/8/7.
 */
@Service
public class DataStatisticsTemplateManager {

    @Autowired
    private MongoCollectionsManager mongoCollectionsManager;

    /**
     * 保存
     * @param obj
     * @return
     */
    public WriteResult save(DBObject obj){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        WriteResult result=collection.save(obj);
        return result;
    }

    /**
     * 保存
     * @param obj
     * @return
     */
    public WriteResult save(DBObject query,DBObject obj){
        return update(query,obj,true,false);
    }

    /**
     * 有则更新
     * @param find
     * @param update
     * @return
     */
    public WriteResult update(DBObject find,DBObject update){
        WriteResult result=update(find, new BasicDBObject("$set", update), false, true);
        return result;
    }

    /**
     * 有则更新，无则插入
     * @param find
     * @param update
     * @return
     */
    public WriteResult updateOrInsert(DBObject find,DBObject update){
        WriteResult result=update(find, new BasicDBObject("$set", update), true, true);
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
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        WriteResult result=collection.update(find, update, insert, multi, WriteConcern.SAFE);
        return result;
    }

    /**
     * 删除模板字段
     */
    public boolean delete(DBObject query){
        try{
            DBCollection coll = mongoCollectionsManager.getDataStatisticsTemplateCollection();
            coll.remove(query, WriteConcern.SAFE);
            return true;
        }catch(Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * 仅返回一条
     * @param query
     * @return
     */
    public DBObject findOne(DBObject query){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        return collection.findOne(query);
    }

    /**
     *
     * @param query：查询条件
     * @param filterField：指定返回字段
     * @return
     */
    public List<BasicDBObject> queryDBObjectList(DBObject query,DBObject filterField){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        return MongoQueryUtil.queryDBObjectList(query,filterField,collection);
    }

    public List<BasicDBObject> queryDBObjectList(DBObject query){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        return MongoQueryUtil.queryDBObjectList(query,collection);
    }
}
