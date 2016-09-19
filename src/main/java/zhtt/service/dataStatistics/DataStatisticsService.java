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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> buildCreateTableForm(String receiveOrgId){
        String todayDateStr=CalendarHelp.getTodayDateStr();
        BasicDBObject query=buildQuerySqlByDate(todayDateStr,todayDateStr);
        query.put(DataStatisticsTemplate.DataKey.receiveOrgId,receiveOrgId);
        query.put(DataStatisticsTemplate.DataKey.reportState, ReportState.getValueString(ReportState.reported));
        List<BasicDBObject>  objList=dataStatisticsManager.query(query);
        if(objList.size()>0){
            List<BasicDBObject>  totalObjList=statisJuniorDataByReceiveOrgId(query);
        }
        List<Organization> orgList=organizationService.queryJuniorOrgNameAndUuidList(receiveOrgId);
        List<Map<String, String>> noReportOrgList=new ArrayList<Map<String, String>>();
        for(Organization org:orgList){
            Map<String,String> map=new HashMap<String,String>();
            map.put("orgId",org.getUuid());
            map.put("orgName",org.getName());
            noReportOrgList.add(map);
        }
        Map<String, Object> mapListMap=new HashMap<String, Object>();
        mapListMap.put("tableConfig",dataStatisticsTemplateService.getDataStatisticsTable(receiveOrgId));/** 初始化table表格的配置信息 **/
        mapListMap.put("noReportOrgList",noReportOrgList);/** 未上报单位 **/
        mapListMap.put("reportedOrgList",null);/** 已上报单位 **/
        mapListMap.put("orgList",null);/** 本机构汇总 **/
        mapListMap.put("unitList",null);/** 本单位内部数据 **/
        return mapListMap;
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
