package zhtt.dao.templeate;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.MongoCollectionsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhtt on 2016/8/7.
 */
@Service
public class DataStatisticsTemplateManager {

    @Autowired
    private MongoCollectionsManager mongoCollectionsManager;

    public WriteResult save(DBObject obj){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        WriteResult result=collection.save(obj);
        return result;
    }

    public WriteResult updateOrInsert(DBObject find,DBObject update){
        WriteResult result=update(find,new BasicDBObject("$set", update),true,true);
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

    public DBObject findOne(DBObject query){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        return collection.findOne(query);
    }

    /**
     *
     * @param query：查询条件
     * @return
     */
    public DBCursor queryDBCursor(DBObject query){
        try{
            return queryDBCursor("datetime", query);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param sort：排序字段
     * @param query：查询条件
     * @return
     */
    public DBCursor queryDBCursor(String sort,DBObject query){
        try{
            DBCollection coll = mongoCollectionsManager.getDataStatisticsTemplateCollection();
            DBCursor dBCursor=coll.find(query).sort(new BasicDBObject(sort, -1));
            return dBCursor;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据查询条件返回  List<BasicDBObject>
     * @param query
     * @return
     */
    public List<BasicDBObject> queryDBObjectList(DBObject query){
        DBCursor cusor=this.queryDBCursor(query);
        List<BasicDBObject> dblist=new ArrayList<BasicDBObject>();
        while (cusor.hasNext()) {
            dblist.add((BasicDBObject) cusor.next());
        }
        return dblist;
    }

    public List<BasicDBObject> group(DBObject key, DBObject filterCond, DBObject initialCode,  String finalizer){
        DBCollection collection=mongoCollectionsManager.getDataStatisticsTemplateCollection();
        return (List<BasicDBObject>)collection.group(key, filterCond, initialCode,  finalizer);
    }
}
