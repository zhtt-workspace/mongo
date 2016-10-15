package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.MongoCollectionsManager;
import zhtt.dao.templeate.DataStatisticsTemplateManager;
import zhtt.entity.templeate.DataStatisticsTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhtt on 2016/9/10.
 * 本表单负责增删改
 */
@Service
public class DataStatisticsTemplateFormService {

    @Autowired
    private DataStatisticsTemplateManager dataStatisticsTemplateManager;

    @Autowired
    private DataStatisticsTemplateService dataStatisticsTemplateService;

    public WriteResult save(DBObject obj){
        return dataStatisticsTemplateManager.save(obj);
    }

    public boolean deleteAndModifyDocTree(String uuid,String parentId,String orgId,boolean realyDelete){
        return delete(uuid,parentId,realyDelete)&&removeOrAddChildToTree(orgId,parentId,uuid,false);
    }

    /**
     * 删除节点，假删除，删除后同步到下级
     * @param uuid
     * @param parentId
     * @param realyDelete：是否真删除
     * @return
     */
    public boolean delete(String uuid,String parentId,boolean realyDelete) {
        DBObject query=new BasicDBObject("parentId",parentId);
        query.put("uuid", uuid);
        if(realyDelete){
            return dataStatisticsTemplateManager.delete(query);
        }else{
            BasicDBObject newValue = new BasicDBObject(DataStatisticsTemplate.DELETE_MAEK, true);
            newValue.put("updatetime", new Date());
            update(query, new BasicDBObject("$set",newValue), false, false);
            return true;
        }
    }

    public WriteResult update(DBObject find,DBObject update,boolean insert,boolean multi){
        return dataStatisticsTemplateManager.update(find, update, insert, multi);
    }

    public WriteResult update(DBObject find,DBObject update){
        return dataStatisticsTemplateManager.update(find, update);
    }

    /**
     * 不存在则插入
     * @param find
     * @param update
     * @return
     */
    public WriteResult updateOrInsert(DBObject find,DBObject update){
        return dataStatisticsTemplateManager.updateOrInsert(find, update);
    }

    public void saveAndModifyTreeDoc(DBObject update){
        save(update);
        if(!update.get("uuid").toString().equals("doc_tree")&&(update.get("type").toString().equals("group")||update.get("type").toString().equals("field"))){
            removeOrAddChildToTree(update.get("orgId").toString(),update.get("parentId").toString(),update.get("uuid").toString(),true);
        }
    }

    /**
     *为模板树节点增加或删除节点
     * @param orgId
     * @param parentUuid
     * @param uuid
     * @param removeOrAddFlag(true:添加,flase:删除)
     * @return
     */
    private boolean removeOrAddChildToTree(String orgId,String parentUuid,String uuid,boolean removeOrAddFlag){
        DBObject query=DataStatisticsTemplateQueryUtil.getTreeDocQuery(orgId);
        DBObject treeDoc=dataStatisticsTemplateManager.findOne(query);
        String updateKey=null;
        if(treeDoc.containsField("children")==false||parentUuid.equals("doc_tree")){
            updateKey="children";
        }else{
            List<String> sqlList=new ArrayList<String>();
            updateKey=DataStatisticsTemplateQueryUtil.buildAddChildrenUpdateSql((List<BasicDBObject>)treeDoc.get("children"),parentUuid,sqlList,updateKey);
        }
        DBObject updateValue=new BasicDBObject("uuid",uuid);
        Object temp=removeOrAddFlag?updateValue.put("show",true):null;
        DBObject update=new BasicDBObject(removeOrAddFlag?"$addToSet":"$pull", new BasicDBObject(updateKey,updateValue));
        update(query, update, false, false);
        return true;
    }

    /**
     *
     * @param orgId
     * @param uuid
     * @param state：显示状态（true,false）
     * @return
     */
    public boolean updateTreeState(String orgId,String uuid,boolean state){
        DBObject query=DataStatisticsTemplateQueryUtil.getTreeDocQuery(orgId);
        DBObject treeDoc=dataStatisticsTemplateManager.findOne(query);
        String updateKey=null;
        List<String> sqlList=new ArrayList<String>();
        updateKey=DataStatisticsTemplateQueryUtil.buildAddChildrenUpdateSql((List<BasicDBObject>)treeDoc.get("children"),uuid,sqlList,updateKey);
        DBObject update=new BasicDBObject("$set", new BasicDBObject(updateKey.substring(0,updateKey.lastIndexOf("children"))+"show",state));
        update(query, update, false, false);
        return true;
    }

    public boolean upOrDown(String orgId,String uuid,int moveDirection){
        DBObject query=DataStatisticsTemplateQueryUtil.getTreeDocQuery(orgId);
        DBObject treeDoc=dataStatisticsTemplateManager.findOne(query);
        List<BasicDBObject>  forest=(List<BasicDBObject>)treeDoc.get("children");
        DataStatisticsTemplateQueryUtil.moveNode(forest, uuid, moveDirection);
        DBObject update=new BasicDBObject("$set", new BasicDBObject("children",forest));
        update(query, update, false, false);
        return true;
    }


    public WriteResult cloneTreeNode(String parentId,String orgId){
        BasicDBObject treeNode=dataStatisticsTemplateService.getDocTreeNode(parentId);
        treeNode.put(DataStatisticsTemplate.DocTree.orgId,orgId);
        treeNode.put(DataStatisticsTemplate.DocTree.datetime,new Date());
        treeNode.remove("_id");
        DBObject query = DataStatisticsTemplateQueryUtil.getTreeDocQuery(orgId);
        return dataStatisticsTemplateManager.save(query,treeNode);
    }
    /**
     * 初始化 ：doc_tree节点
     * @param name
     * @return
     */
    private WriteResult init(String name,int cols){
        DBObject obj=new BasicDBObject();
        obj.put("name",name);//树结构名称
        obj.put("children",null);//子节点标识
        obj.put("cols",cols);//形成table表格时的总列数
        obj.put("code","doc_tree");//code标识
        return dataStatisticsTemplateManager.save(obj);
    }
}
