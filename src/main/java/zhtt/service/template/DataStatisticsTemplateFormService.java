package zhtt.service.template;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.service.util.DataStatisticsTemplateManager;

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

    public WriteResult updateOrInsert(DBObject find,DBObject update){
        return dataStatisticsTemplateManager.updateOrInsert(find,update);
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
