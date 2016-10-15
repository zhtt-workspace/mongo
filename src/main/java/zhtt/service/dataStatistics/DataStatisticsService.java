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
import zhtt.util.JsonResponse;

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
        BasicDBObject query=DataStatisticsUtil.buildQuerySqlByDate(date == null ? todayDateStr : date, date == null ? todayDateStr : date);
        query=DataStatisticsUtil.buildQueryJuniorSql(query, receiveOrgId);
        List<BasicDBObject>  objList=dataStatisticsManager.query(query);
        List<BasicDBObject>  totalObjList=null;
        Map<String,Object> mapListMap=formatJuniorData(objList,receiveOrgId);
        List<String> reportOrgIdList=null;
        if(mapListMap==null){
            mapListMap=new HashMap<>();
        }else{
            if(objList!=null&&objList.size()>0){
                List<BasicDBObject> reportedOrgList=(List<BasicDBObject>)mapListMap.get("reportedOrgList");
                reportOrgIdList=new ArrayList<>();

                totalObjList=statisJuniorDataByReceiveOrgId(query,receiveOrgId);
                for(BasicDBObject obj:reportedOrgList){
                    reportOrgIdList.add(obj.getString(DataStatisticsTemplate.DataKey.orgId));
                }
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
        if(date==null){
            JsonResponse jsonResponse=dataStatisticsTemplateService.getDataStatisticsTable(receiveOrgId);
            mapListMap.put("tableConfig", jsonResponse.getData());/** 初始化table表格的配置信息 **/
        }
        mapListMap.put("noReportOrgList",noReportOrgList);/** 未上报单位 **/
        mapListMap.put("totalData",totalObjList==null||totalObjList.size()==0?null:totalObjList.get(0));/** 本次汇总的数据 **/
        return mapListMap;
    }

    private Map<String,Object>  formatJuniorData(List<BasicDBObject>  objList,String loginOrgId){
        Map<String,Object> returnMap=new HashMap<>();
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
                    returnMap.put("unitData", data);
                }else if(loginOrgId.equals(obj.getString(DataStatisticsTemplate.DataKey.orgId))){
                    returnMap.put("orgData",data);
                }else{
                    juniorDataList.add(data);
                }
            }
            returnMap.put("reportedOrgList",juniorDataList);
            return returnMap;
        }
    }

    /**
     * 根据接收机构统计下级数据
     * @param query
     * @param orgId
     * @return
     */
    public List<BasicDBObject> statisJuniorDataByReceiveOrgId(BasicDBObject query,String orgId){
        /**
         * 增加过滤条件，只统计本单位的，不统计本机构的
         */
        List<BasicDBObject> condition = new ArrayList<BasicDBObject>();
        BasicDBObject orgCondition=new BasicDBObject(DataStatisticsTemplate.DataKey.orgId,new BasicDBObject("$ne",orgId));
        BasicDBObject headquarters=new BasicDBObject(DataStatisticsTemplate.DataKey.dataType,DataStatisticsTemplate.Type.headquarters);
        condition.add(orgCondition);
        condition.add(headquarters);
        //query.put("$or",condition);
        query.put(DataStatisticsTemplate.DataKey.orgId,new BasicDBObject("$ne",orgId));
        BasicDBObject groupField=new BasicDBObject(DataStatisticsTemplate.DataKey.receiveOrgId, true);
        String finalizer = FileUtil.getJavaScricptFunctionFromFile(this, "/js/dataStatistics-totalAllFileld.js");
        DBObject totalField=dataStatisticsTemplateService.getAllTotalField(orgId);
        return dataStatisticsManager.group(groupField, query, totalField,  finalizer);
    }
}
