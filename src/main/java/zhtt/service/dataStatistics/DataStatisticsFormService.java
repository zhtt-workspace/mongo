package zhtt.service.dataStatistics;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.dataStatistics.DataStatisticsManager;
import zhtt.entity.templeate.DataStatisticsTemplate;

/**
 * Created by zhtt on 2016/9/28.
 */
@Service
public class DataStatisticsFormService {

    @Autowired
    private DataStatisticsManager dataStatisticsManager;

    /**
     * 保存：保存时，自动上报本机构数据，自动下发下级机构数据
     * @param obj
     * @return
     */
    public WriteResult save(DBObject obj){
        BasicDBObject query=new BasicDBObject();
        query.put(DataStatisticsTemplate.DataKey.orgId,obj.get(DataStatisticsTemplate.DataKey.orgId));
        query.put(DataStatisticsTemplate.DataKey.date,obj.get(DataStatisticsTemplate.DataKey.date));
        query.put(DataStatisticsTemplate.DataKey.dataType,obj.get(DataStatisticsTemplate.DataKey.dataType));
        return dataStatisticsManager.saveOrUpdate(query,obj);
    }

    /**
     * 上报数据
     * @param orgId
     * @param date
     * @return
     */
    public WriteResult report(String orgId,String date){
        return null;
    }

    /**
     * 下发数据
     * @param orgId
     * @param date
     * @return
     */
    public WriteResult send(String orgId,String date){
        return null;
    }
}
