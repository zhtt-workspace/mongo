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
import zhtt.util.DataState.IssueState;
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

    /**
     * 信息采集，需要初始化加载的数据
     * @param receiveOrgId
     * @return
     */
    public Map<String, Object> buildCreateTableForm(String receiveOrgId,String date){
        String todayDateStr=CalendarHelp.getTodayDateStr();
        BasicDBObject query=buildQuerySqlByDate(date==null?todayDateStr:date,date==null?todayDateStr:date);
        query=buildQueryJuniorSql(query,receiveOrgId);
        List<BasicDBObject>  objList=dataStatisticsManager.query(query);
        List<BasicDBObject>  totalObjList=null;
        BasicDBObject unitData=null;
        List<BasicDBObject> reportedOrgList=formatJuniorData(objList);
        List<String> reportOrgIdList=null;
        if(objList!=null&&objList.size()>0){
            reportOrgIdList=new ArrayList<>();
            totalObjList=statisJuniorDataByReceiveOrgId(query);
            unitData=reportedOrgList.get(0);
            for(BasicDBObject obj:reportedOrgList){
                reportOrgIdList.add(obj.getString(DataStatisticsTemplate.DataKey.orgId));
            }
            if(DataStatisticsTemplate.Type.headquartersCN.equals(unitData.getString(DataStatisticsTemplate.DataKey.orgName))){
                reportedOrgList.remove(0);
            }else{
                unitData=null;
            }
        }
        List<Organization> orgList=organizationService.queryJuniorOrgNameAndUuidList(receiveOrgId,reportOrgIdList);
        List<Map<String, String>> noReportOrgList=new ArrayList<Map<String, String>>();
        for(Organization org:orgList){
            Map<String,String> map=new HashMap<String,String>();
            map.put(DataStatisticsTemplate.DataKey.orgId,org.getUuid());
            map.put(DataStatisticsTemplate.DataKey.orgName,org.getName());
            noReportOrgList.add(map);
        }
        Map<String, Object> mapListMap=new HashMap<String, Object>();
        if(date==null){
            mapListMap.put("tableConfig",dataStatisticsTemplateService.getDataStatisticsTable(receiveOrgId).getData());/** 初始化table表格的配置信息 **/
        }
        mapListMap.put("noReportOrgList",noReportOrgList);/** 未上报单位 **/
        mapListMap.put("reportedOrgList",reportedOrgList);/** 已上报单位 **/
        mapListMap.put("totalData",totalObjList==null||totalObjList.size()==0?null:totalObjList.get(0));/** 本次汇总的数据 **/
        mapListMap.put("unitData",unitData);/** 本单位内部数据 **/
        mapListMap.put("orgData",null);/** 本机构汇总后保存的数据 **/
        return mapListMap;
    }

    private List<BasicDBObject>  formatJuniorData(List<BasicDBObject>  objList){
        if(objList==null||objList.size()==0){
            return null;
        }else{
            List<BasicDBObject> juniorDataList=new ArrayList<>();
            for(BasicDBObject obj:objList){
                BasicDBObject data=(BasicDBObject)obj.get("content");
                if(data==null){
                    data=new BasicDBObject();
                }
                data.put(DataStatisticsTemplate.DataKey.orgId,obj.getString(DataStatisticsTemplate.DataKey.orgId));
                data.put(DataStatisticsTemplate.DataKey.orgName,obj.getString(DataStatisticsTemplate.DataKey.orgName));
                if(DataStatisticsTemplate.Type.headquarters.equals(obj.getString(DataStatisticsTemplate.DataKey.dataType))){
                    juniorDataList.add(0,data);
                }else{
                    juniorDataList.add(data);
                }
            }
            return juniorDataList;
        }
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
