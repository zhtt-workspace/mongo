package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.templeate.DataStatisticsTemplateManager;

/**
 * Created by zhtt on 2016/9/10.
 * 本表单负责增删改
 */
@Service
public class DataStatisticsTemplateFormService {

    @Autowired
    private DataStatisticsTemplateManager dataStatisticsTemplateManager;

    public WriteResult save(DBObject obj){
        return dataStatisticsTemplateManager.save(obj);
    }

    public WriteResult update(DBObject find,DBObject update,boolean insert,boolean multi){
        return dataStatisticsTemplateManager.update(find, update, insert, multi);
    }

    public WriteResult updateOrInsert(DBObject find,DBObject update){
        return dataStatisticsTemplateManager.updateOrInsert(find, update);
    }

    public void save(DBObject find,DBObject update){
        updateOrInsert(find,update);
        if(!update.get("uuid").toString().equals("doc_tree")&&(update.get("type").toString().equals("group")||update.get("type").toString().equals("field"))){
            removeOrAddChildToTree(update.get("orgId").toString(),update.get("parentId").toString(),update.get("uuid").toString(),true);
        }
    }
    /**
     *为模板树节点增加或删除节点
     * @param orgId
     * @param parentUuid
     * @param uuid
     * @param removeOrAddFlag
     * @return
     */
    private boolean removeOrAddChildToTree(String orgId,String parentUuid,String uuid,boolean removeOrAddFlag){
        DBObject query=DataStatisticsTemplateQueryUtil.getTreeDocQuery(orgId);
        DBObject treeDoc=dataStatisticsTemplateManager.findOne(query);
        if(treeDoc.containsField("children")==false||parentUuid.equals(treeDoc.get("uuid").toString())){
            DBObject update=new BasicDBObject(removeOrAddFlag?"$addToSet":"$pull", new BasicDBObject("children",new BasicDBObject("uuid",uuid)));
            update(query, update, false, false);
        }else{

        }
        return false;
    }

    /**
     * 初始化 ：doc_tree节点
     * @param name
     * @return
     */
    public WriteResult init(String name,int cols){
        DBObject obj=new BasicDBObject();
        obj.put("name",name);//树结构名称
        obj.put("children",null);//子节点标识
        obj.put("cols",cols);//形成table表格时的总列数
        obj.put("code","doc_tree");//code标识
        return dataStatisticsTemplateManager.save(obj);
    }
}
