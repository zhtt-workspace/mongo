package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import zhtt.entity.templeate.DataStatisticsTemplate;

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
    public  static String buildAddChildrenUpdateSql(List<BasicDBObject> tree,final String parent,List<String> sqlList,String sql){
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
                    sql= sqlBuffer.toString();
                }else{
                    List<BasicDBObject> childtree=(List<BasicDBObject>) child.get("children");
                    if(childtree==null){
                    }else{
                        sqlList.add("."+i+".children");
                        sql=buildAddChildrenUpdateSql(childtree,parent,sqlList,sql);
                    }
                }
            }
            i++;
            if(i==j){
                sqlList.remove(sqlList.size()-1);
            }
        }
        return sql;
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
        uuidList.add("doc_tree");
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

    /** 得到子树上的所有显示状态的 input code **/
    public static  List<String> getInputShowListByChildrenTree(List<BasicDBObject> tree,List<String> inputUuidList){
        List<String> uuidList=new ArrayList<String>();
        for(BasicDBObject child:tree){
            if(child==null){continue;}
            List<BasicDBObject> childtree=(List<BasicDBObject>) child.get("children");
            if(child.containsField("show")&&child.getBoolean("show")){
                String uuid=child.getString("uuid");
                if(inputUuidList.contains(uuid)){
                    uuidList.add(uuid);
                }
                if(childtree!=null){
                    uuidList.addAll(getInputShowListByChildrenTree(childtree, inputUuidList));
                }
            }
        }
        return uuidList;
    }

    /** 构造树 **/
    public static  Map<String, Object> buildTree(BasicDBObject treeObj,Map<String,BasicDBObject> dbMap){
        List<Map<String, Object>> mapList = buildTree((List<BasicDBObject>)treeObj.get("children"),dbMap);
        Map<String, Object> map=new HashMap<>();
        map.put(DataStatisticsTemplate.RootKey.name,dbMap.get("doc_tree").get(DataStatisticsTemplate.RootKey.name));
        map.put(DataStatisticsTemplate.RootKey.uuid,dbMap.get("doc_tree").get(DataStatisticsTemplate.RootKey.uuid));
        map.put(DataStatisticsTemplate.RootKey.type,dbMap.get("doc_tree").get(DataStatisticsTemplate.RootKey.type));
        map.put("children",mapList);
        return map;
    }

    /** 构造树 **/
    private static  List<Map<String, Object>> buildTree(List<BasicDBObject> childrens,Map<String,BasicDBObject> DCTMap){
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(childrens==null||childrens.size()==0){
            return mapList;
        }
        for(BasicDBObject child:childrens){
            Map<String, Object> map = new HashMap<String, Object>();
            if(child==null){
                continue;
            }
            String uuid=child.getString("uuid");
            if(uuid==null){
                continue;
            }
            map.put("uuid", uuid);
            map.put("parentId", child.get("parentId"));
            map.put("show", child.containsField("show")&&child.getBoolean("show")==true);
            BasicDBObject DCT=DCTMap.get(uuid);
            if(DCT==null){
                //本节点对应模板已被删除
            }else{
                map.put("uuid", DCT.getString("uuid"));
                map.put("name", DCT.get("name"));
                map.put("parentId", DCT.get("parentId"));
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

    /** 得到所有input **/
    public static List<String> getAllInputList(List<BasicDBObject> dctList){
        List<String> codeList=new ArrayList<String>();
        for(BasicDBObject child:dctList){
            if(("field".equals(child.get("type")))){
                codeList.add(child.getString("uuid"));
            }
        }
        return codeList;
    }

    /** 构造表单 **/
    public static  Map<String, Object> buildTable(BasicDBObject treeObj,Map<String,BasicDBObject> dbMap,List<String> inputCodeList){
        List<Map<String, Object>> mapList = DataStatisticsTemplateQueryUtil.buildTable((List<BasicDBObject>) treeObj.get("children"), dbMap, inputCodeList);
        Map<String, Object> map=new HashMap<>();
        BasicDBObject treeDoc=dbMap.get("doc_tree");
        map.put(DataStatisticsTemplate.RootKey.name, treeDoc.get(DataStatisticsTemplate.RootKey.name));
        map.put(DataStatisticsTemplate.RootKey.uuid,treeDoc.get(DataStatisticsTemplate.RootKey.uuid));
        map.put(DataStatisticsTemplate.RootKey.type,treeDoc.get(DataStatisticsTemplate.RootKey.type));
        map.put(DataStatisticsTemplate.RootKey.colspan,treeDoc.get(DataStatisticsTemplate.RootKey.colspan));
        map.put("children",mapList);
        return map;
    }

    /** 构造表单 **/
    public static  List<Map<String, Object>> buildTable(List<BasicDBObject> tree,Map<String,BasicDBObject> DCTMap,List<String> inputCodeList){
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(tree==null){
            return mapList;
        }
        try{
            for(BasicDBObject child:tree){
                if(child==null){continue;}
                if(child.containsField("show")&&child.getBoolean("show")){
                    Map<String, Object> map = new HashMap<String, Object>();
                    String uuid=child.getString(DataStatisticsTemplate.FieldKey.uuid);
                    map.put("uuid", uuid);
                    BasicDBObject DCT=DCTMap.get(uuid);
                    if(DCT==null){
                        //本节点对应模板已被删除
                    }else{
                        map.put(DataStatisticsTemplate.FieldKey.name, DCT.get(DataStatisticsTemplate.FieldKey.name));
                        map.put(DataStatisticsTemplate.FieldKey.parentId, DCT.get(DataStatisticsTemplate.FieldKey.parentId));
                        String type=(String) DCT.get(DataStatisticsTemplate.FieldKey.type);
                        map.put(DataStatisticsTemplate.FieldKey.type, type);
                        List<BasicDBObject> childtree=(List<BasicDBObject>) child.get("children");
                        if(childtree==null){
                            //本节点无子节点
                            map.put(DataStatisticsTemplate.FieldKey.colspan, DCT.getString(DataStatisticsTemplate.FieldKey.colspan));//1 为占一列、0 为占半列
                            if(DataStatisticsTemplate.Type.FIELD.equals(type)){
                                map.put(DataStatisticsTemplate.FieldKey.unit, DCT.get(DataStatisticsTemplate.FieldKey.unit));
                                map.put(DataStatisticsTemplate.FieldKey.type, DCT.get(DataStatisticsTemplate.FieldKey.type));
                                map.put(DataStatisticsTemplate.FieldKey.maxNumber, DCT.get(DataStatisticsTemplate.FieldKey.maxNumber));
                                map.put(DataStatisticsTemplate.FieldKey.minNumber, DCT.get(DataStatisticsTemplate.FieldKey.minNumber));
                                map.put(DataStatisticsTemplate.FieldKey.beyondRemind, DCT.get(DataStatisticsTemplate.FieldKey.beyondRemind));
                            }
                        }else{
                            map.put(DataStatisticsTemplate.FieldKey.colspan, DCT.getString(DataStatisticsTemplate.FieldKey.colspan));
                            map.put("children",buildTable(childtree,DCTMap,inputCodeList));
                            map.put("childrenSize",getInputShowListByChildrenTree(childtree, inputCodeList).size());
                        }
                    }
                    mapList.add(map);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mapList;
    }
}
