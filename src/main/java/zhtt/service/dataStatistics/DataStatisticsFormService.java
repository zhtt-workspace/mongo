package zhtt.service.dataStatistics;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.dataStatistics.DataStatisticsManager;

/**
 * Created by zhtt on 2016/9/28.
 */
@Service
public class DataStatisticsFormService {

    @Autowired
    private DataStatisticsManager dataStatisticsManager;

    public WriteResult save(DBObject obj){
        return dataStatisticsManager.save(obj);
    }
}
