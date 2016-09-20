/**
 * 录入本机构数据，并统计汇总下级数据，同时可为下级数据补录、修改，但要记录日志
 * Created by zhtt on 2016/9/18.
 */
$(function(){
    dataStatisticsCreate.initCreateForm();
})
var dataStatisticsCreate={};
/**
 * 去后台请求模板数据、下级与本级填报的数据，用于显示数据采集的表单
 */
dataStatisticsCreate.initCreateForm=function(){
    $.get(dataStatistics.buildCreateFormUrl,function(data){
        if(data.status=="success"){
            var cfgData=data.data;
            cfgData.method='createForm';
            var html=dataStatisticsCreate.tableForm(cfgData);
            $("#dataStatisticsFormBox").html(html);
            var options = {
                fixColumnColor: '#f2f2f2',
                fixColumnNumber: 3,
                width: 900,
                height: 400
            };
            //$("#table").fixedTable(options);
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}

dataStatisticsCreate.tableForm=function(cfg){
    var table=[];
    table.push('<table id="table" class="table table-bordered table-hover  table-striped">');
    table.push(dataStatisticsCreate.buildTableHeader(cfg));
        var childCfg={};
        $.extend(childCfg,cfg);
        childCfg.tableConfig=cfg.tableConfig.children;
        childCfg.trFlag=false;
        table.push(dataStatisticsCreate.buildTableContentTr(childCfg));
    table.push('</table>');
    return table.join('');
}
/**
 * 创建头部标题
 */
dataStatisticsCreate.buildTableHeader=function(cfg){
    var headerTr=[];
    headerTr.push('<tr class="dstHeader">');
    headerTr.push('<th colspan="'+(parseInt(cfg.tableConfig.colspan)-1)+'"  style="width:533px;letter-spacing: 12px;" id="dataitem">数据项</th>');
    switch (cfg.method){
        case 'template':
            headerTr.push('<th >最小值</th>');
            headerTr.push('<th >最大值</th>');
            headerTr.push('<th >阀值提醒</th>');
            headerTr.push('</tr>');
            return headerTr.join("");
            break;
        case 'createForm':
            /** 上次汇总保存的数据 **/
            if(cfg.orgData){
                headerTr.push('<th >上次汇总保存的数据</th>');
            }else{
                headerTr.push('<th class="noReportOrgList">上次汇总保存的数据</th>');
            }
            /** 本次查询汇总结果 **/
            if(cfg.totalObjList){
                headerTr.push('<th >本次查询汇总结果</th>');
            }else{
                headerTr.push('<th class="noReportOrgList">本次查询汇总结果</th>');
            }
            /** 本单位内部 **/
            if(cfg.unitData){
                headerTr.push('<th >本机构录入的数据</th>');
            }else{
                headerTr.push('<th class="noReportOrgList">本机构录入的数据</th>');
            }
            /** 未上报单位 **/
            if(cfg.noReportOrgList&&cfg.noReportOrgList.length>0){
                for(var i=0;i<cfg.noReportOrgList.length;i++){
                    var org=cfg.noReportOrgList[i];
                    headerTr.push('<th class="noReportOrgList">'+org.orgName+'</th>');
                }
            }
            headerTr.push('</tr>');
            return headerTr.join("");
            break;
        default :return "";
    }

}
dataStatisticsCreate.buildTableContentTr=function(cfg){
    var trFlag=cfg.trFlag||true;
    var tableConfig=cfg.tableConfig;/** 生成table用的数据 **/
    var table=[];
    for(var i=0,j=tableConfig.length;i<j;i++) {
        var item = tableConfig[i];
        if (trFlag&&i>0){
            table.push('<tr>');
        }
        if(item.type=="group"){
            table.push('<td rowspan="'+(item.childrenSize||"1")+'" colspan="'+(item.colspan||'1')+'">'+item.name+'</td>');
            if(item.children){
                var childCfg={};
                $.extend(childCfg,cfg)
                childCfg.tableConfig=item.children;
                table.push(dataStatisticsCreate.buildTableContentTr(childCfg));
            }
        }else if(item.type=="field"){
            return dataStatisticsCreate.buildField(cfg,item);
        }
        table.push('</tr>');
        trFlag=true;
    }
    return table.join('');
}
dataStatisticsCreate.buildField=function(cfg,item){
    var fieldHtml=[];
    fieldHtml.push('<td rowspan="'+(item.childrenSize||"1")+'" colspan="'+(item.colspan||'1')+'">'+item.name+'（'+item.unit+'）</td>');
    switch (cfg.method){
        case 'template':
            fieldHtml.push('<td>'+item.maxNumber+'</td>');
            fieldHtml.push('<td>'+item.minNumber+'</td>');
            fieldHtml.push('<td>'+item.beyondRemind+'</td>');
            return fieldHtml.join("");
        case 'createForm':
            /** 上次汇总保存的数据 **/
            if(cfg.orgData){
                fieldHtml.push('<td ></td>');
            }else{
                fieldHtml.push('<td>0</td>');
            }
            /** 本次查询汇总数据 **/
            if(cfg.totalObjList){
                fieldHtml.push('<td ></td>');
            }else{
                fieldHtml.push('<td>0</td>');
            }
            /** 本单位内部数据 **/
            if(cfg.unitData){
                fieldHtml.push('<td >1</td>');
            }else{
                fieldHtml.push('<td ></td>');
            }
            /** 未上报单位 **/
            if(cfg.noReportOrgList&&cfg.noReportOrgList.length>0){
                for(var i=0;i<cfg.noReportOrgList.length;i++){
                    var org=cfg.noReportOrgList[i];
                    fieldHtml.push('<td ></td>');
                }
            }
            return fieldHtml.join("");
            break;
        default :return "";
    }
}