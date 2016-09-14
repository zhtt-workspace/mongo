package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /** 构造更新树 **/
    public  static String buildAddChildrenUpdateSql(List<BasicDBObject> tree,final String parent,List<String> sqlList){
        int i=0;
        int j=tree.size();
        for(BasicDBObject child:tree){
            if(child!=null){
                String uuid=child.getString("uuid");
                if(parent.equals(uuid)){
                    sqlList.add("."+i+".children");
                    StringBuffer sqlBuffer=new StringBuffer("children");
                    for(String string:sqlList){
                        sqlBuffer.append(string);
                    }
                    return sqlBuffer.toString();
                }else{
                    List<BasicDBObject> childtree=(List<BasicDBObject>) child.get("children");
                    if(childtree==null){
                    }else{
                        sqlList.add("."+i+".children");
                        buildAddChildrenUpdateSql(childtree,parent,sqlList);
                    }
                }
            }
            i++;
            if(i==j){
                sqlList.remove(sqlList.size()-1);
            }
        }
        return null;
    }



    /**
     * 摘取uuid
     * @param docTreeObj
     * @return
     */
    public static List<String> getUuidListByDocTree(BasicDBObject docTreeObj){
        List<String> uuidList=new ArrayList<String>();
        if(docTreeObj!=null) {
            uuidList.add(docTreeObj.getString("uuid"));
            if(docTreeObj.containsField("children")){
                uuidList.addAll(getUuidListByDocTree((List<BasicDBObject> )docTreeObj.get("children")));
            }
        }
        return uuidList;
    }

    /**
     * 摘取uuid
     * @param childList
     * @return
     */
    private static List<String> getUuidListByDocTree(List<BasicDBObject> childList){
        List<String> uuidList=new ArrayList<String>();
        if(childList==null){
            return uuidList;
        }
        for(BasicDBObject child:childList) {
            if (child == null) {
                continue;
            }
            uuidList.add(child.getString("uuid"));
            if (!(child.get("children") == null || "null".equals(child.get("children")))) {
                uuidList.addAll(getUuidListByDocTree((List<BasicDBObject>) child.get("children")));
            }
        }
        return uuidList;
    }



    /** 构造树 **/
    public static  Map<String, Object> buildTree(BasicDBObject treeObj,Map<String,BasicDBObject> dbMap){
        List<Map<String, Object>> mapList = buildTree((List<BasicDBObject>)treeObj.get("children"),dbMap);
        Map<String, Object> map=new HashMap<>();
        map.put("name",dbMap.get("doc_tree").get("name"));
        map.put("uuid",dbMap.get("doc_tree").get("uuid"));
        map.put("children",mapList);
        return map;
    }

    /** 构造树 **/
    private static  List<Map<String, Object>> buildTree(List<BasicDBObject> tree,Map<String,BasicDBObject> DCTMap){
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for(BasicDBObject child:tree){
            Map<String, Object> map = new HashMap<String, Object>();
            if(child==null){
                continue;
            }
            String uuid=child.getString("uuid");
            if(uuid==null){
                continue;
            }
            map.put("uuid", uuid);
            map.put("show", child.containsField("show")&&child.getBoolean("show")==true);
            BasicDBObject DCT=DCTMap.get(uuid);
            if(DCT==null){
                //本节点对应模板已被删除
            }else{
                map.put("uuid", DCT.getString("uuid"));
                map.put("name", DCT.get("name"));
                map.put("parent", DCT.get("parentId"));
                map.put("type", DCT.get("type"));
                List<BasicDBObject> childtree=(List<BasicDBObject>) child.get("children");
                if(childtree==null){
                    //本节点无子节点
                }else{
                    map.put("children",buildTree(childtree, DCTMap));
                }
                mapList.add(map);
            }
        }
        return mapList;
    }
}
