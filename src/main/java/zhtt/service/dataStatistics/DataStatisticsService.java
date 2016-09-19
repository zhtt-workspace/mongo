package zhtt.service.dataStatistics;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.dao.dataStatistics.DataStatisticsManager;
import zhtt.entity.templeate.DataStatisticsTemplate;
import zhtt.entity.user.Organization;
import zhtt.service.template.DataStatisticsTemplateService;
import zhtt.service.user.OrganizationService;
import zhtt.util.CalendarHelp;
import zhtt.util.DataState.ReportState;
import zhtt.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhtt on 2016/9/18.
 */
@Service
public class DataStatisticsService {

    @Autowired
    private DataStatisticsManager dataStatisticsManager;

    @Autowired
    private DataStatisticsTemplateService dataStatisticsTemplateService;

    @Autowired
    private OrganizationService organizationService;

    public List<BasicDBObject> queryJuniorDataListByReceiveOrgId(String receiveOrgId){
        String todayDateStr=CalendarHelp.getTodayDateStr();
        BasicDBObject query=buildQuerySqlByDate(todayDateStr,todayDateStr);
        query.put(DataStatisticsTemplate.DataKey.receiveOrgId,receiveOrgId);
        query.put(DataStatisticsTemplate.DataKey.reportState, ReportState.reported);
        List<BasicDBObject>  objList=dataStatisticsManager.query(query);
        List<BasicDBObject>  totalObjList=statisJuniorDataByReceiveOrgId(query);
        List<Organization> orgList=organizationService.queryJuniorOrgNameAndUuidList(receiveOrgId);
        return null;
    }

    /**
     * 根据接收机构统计下级数据
     * @param query
     * @return
     */
    public List<BasicDBObject> statisJuniorDataByReceiveOrgId(BasicDBObject query){
        BasicDBObject groupField=new BasicDBObject(DataStatisticsTemplate.DataKey.receiveOrgId, true);
        String finalizer = FileUtil.getJavaScricptFunctionFromFile(this, "/js/dataStatistics-totalAllFileld.js");
        DBObject totalField=dataStatisticsTemplateService.getAllTotalField(query.getString(DataStatisticsTemplate.DataKey.receiveOrgId));
        return dataStatisticsManager.group(groupField, query, totalField,  finalizer);
    }

    /**
     * 查询两个时间段内的数据
     * @param startTime
     * @param endTime
     * @return
     */
    public static BasicDBObject buildQuerySqlByDate (String startTime,String endTime){
        List<BasicDBObject> dateCondition = new ArrayList<BasicDBObject>();
        dateCondition.add(new BasicDBObject("date", new BasicDBObject("$gt", CalendarHelp.formatDayStartTime(startTime))));
        dateCondition.add(new BasicDBObject("date", new BasicDBObject("$lt", CalendarHelp.formatDayEndTime(endTime))));
        BasicDBObject query=new BasicDBObject();
        query.put("$and", dateCondition);
        return query;
    }
}
