package zhtt.service.dataStatistics;

import com.mongodb.BasicDBObject;
import zhtt.entity.templeate.DataStatisticsTemplate;
import zhtt.util.CalendarHelp;
import zhtt.util.DataState.IssueState;
import zhtt.util.DataState.ReportState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhtt on 2016/10/15.
 */
public class DataStatisticsUtil {


    /**
     * 查询两个时间段内的数据
     * @param startTime
     * @param endTime
     * @return
     */
    public static BasicDBObject buildQuerySqlByDate (String startTime,String endTime){

        List<BasicDBObject> basicDBObjectList = new ArrayList<BasicDBObject>();
        basicDBObjectList.add(new BasicDBObject(DataStatisticsTemplate.DataKey.date, new BasicDBObject("$gt", CalendarHelp.formatDayStartTime(startTime))));
        basicDBObjectList.add(new BasicDBObject(DataStatisticsTemplate.DataKey.date, new BasicDBObject("$lt", CalendarHelp.formatDayEndTime(endTime))));
        BasicDBObject query=new BasicDBObject("$and", basicDBObjectList);
        return query;
    }

    public static BasicDBObject buildQueryJuniorSql(BasicDBObject query,String orgId){
        if(query==null){
            query=new BasicDBObject();
        }
        List<BasicDBObject> dateCondition = new ArrayList<BasicDBObject>();
        dateCondition.add(new BasicDBObject(DataStatisticsTemplate.DataKey.reportState, ReportState.getValueString(ReportState.reported)));
        dateCondition.add(new BasicDBObject(DataStatisticsTemplate.DataKey.issueState, IssueState.getValueString(IssueState.noissue)));
        query.put("$or", dateCondition);
        query.put(DataStatisticsTemplate.DataKey.receiveOrgId,orgId);
        return query;
    }

    public static void main(String[] args){
        String startTime="2016-09-29";
        System.out.println(CalendarHelp.format(CalendarHelp.formatDayStartTime(startTime)));
        System.out.println(CalendarHelp.format(CalendarHelp.formatDayEndTime(startTime)));
    }
}
