package zhtt.service.dataStatistics;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.dataStatistics.DataStatisticsManager;
import zhtt.entity.templeate.DataStatisticsTemplate;

import java.util.List;

/**
 * Created by zhtt on 2016/9/18.
 */
@Service
public class DataStatisticsService {

    @Autowired
    private DataStatisticsManager dataStatisticsManager;

    public List<BasicDBObject> queryJuniorDataListByReceiveOrgId(String orgId){
        BasicDBObject query=new BasicDBObject();
        query.put(DataStatisticsTemplate.DataKey.receiveOrgId,orgId);
        return null;
    }

    public List<BasicDBObject> statisJuniorDataByReceiveOrgId(String orgId){

        return null;
    }
}
