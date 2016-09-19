package zhtt.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhtt on 2016/9/18.
 */
public class MongoQueryUtil {

    /**
     *
     * @param query：查询条件
     * @return
     */
    public static DBCursor queryDBCursor(DBObject query,DBCollection coll){
        try{
            return queryDBCursor("datetime", query,coll);
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
    public static DBCursor queryDBCursor(String sort,DBObject query,DBCollection coll){
        try{
            DBCursor dBCursor=coll.find(query).sort(new BasicDBObject(sort, -1));
            return dBCursor;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据查询条件返回  DBCursor
     * @param query：查询条件
     * @param filterField：指定需要返回的字段
     * @return
     */
    public static DBCursor queryDBCursor(DBObject query,DBObject filterField,DBCollection coll){
        DBCursor cusor = coll.find(query,filterField);
        return cusor;
    }

    /**
     * 根据查询条件返回  List<BasicDBObject>
     * @param query
     * @return
     */
    public static List<BasicDBObject> queryDBObjectList(DBObject query,DBCollection coll){
        DBCursor cusor=queryDBCursor(query,coll);
        List<BasicDBObject> dblist=new ArrayList<BasicDBObject>();
        while (cusor.hasNext()) {
            dblist.add((BasicDBObject) cusor.next());
        }
        return dblist;
    }

    /**
     * 根据查询条件返回  List<BasicDBObject>
     * @param query
     * @return
     */
    public static List<BasicDBObject> queryDBObjectList(DBObject query,DBObject filterField,DBCollection coll){
        DBCursor cusor=queryDBCursor(query,filterField,coll);
        List<BasicDBObject> dblist=new ArrayList<BasicDBObject>();
        while (cusor.hasNext()) {
            dblist.add((BasicDBObject) cusor.next());
        }
        return dblist;
    }
}
