/**
 * Created by zhtt on 2016/10/10.
 */
var dataStatisticsBuildTable={};
dataStatisticsBuildTable.buildTable=function(cfg){
    var table=[];
    table.push('<table id="'+cfg.tableId+'" class="table table-bordered table-hover  table-striped">');
    table.push(dataStatisticsBuildTable.buildTableHeader(cfg));
    var childCfg={};
    $.extend(childCfg,cfg);
    childCfg.tableConfig=cfg.tableConfig.children;
    childCfg.trFlag=false;
    table.push(dataStatisticsBuildTable.buildTableContentTr(childCfg));
    table.push('</table>');
    return table.join('');
}
/**
 * 创建头部标题
 */
dataStatisticsBuildTable.buildTableHeader=function(cfg){
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
            headerTr.push('<th >数据值</th>');
            headerTr.push('</tr>');
            return headerTr.join("");
        case 'browseDataListTable':
            /** 上次汇总保存的数据 **/
            if(cfg.orgData){
                headerTr.push('<th >上次汇总保存的数据</th>');
            }else{
                headerTr.push('<th class="noReportOrgList">上次汇总保存的数据</th>');
            }
            /** 本次查询汇总结果 **/
            if(cfg.totalData){
                headerTr.push('<th id="queryTotal">本次查询汇总结果</th>');
            }else{
                headerTr.push('<th id="queryTotal" class="noReportOrgList">本次查询汇总结果</th>');
            }
            /** 本单位内部 **/
            if(cfg.unitData){
                headerTr.push('<th id="'+loginRootOrganization.uuid+'">本机构录入的数据</th>');
            }else{
                headerTr.push('<th class="noReportOrgList" id="'+loginRootOrganization.uuid+'">本机构录入的数据</th>');
            }
            /** 已上报单位 **/
            if(cfg.reportedOrgList&&cfg.reportedOrgList.length>0){
                for(var i=0;i<cfg.reportedOrgList.length;i++){
                    var org=cfg.reportedOrgList[i];
                    headerTr.push('<th id="'+org.orgId+'"  onclick="dataStatisticsCreate.openFormBox(this)">'+org.orgName+'</th>');
                }
            }
            /** 未上报单位 **/
            if(cfg.noReportOrgList&&cfg.noReportOrgList.length>0){
                for(var i=0;i<cfg.noReportOrgList.length;i++){
                    var org=cfg.noReportOrgList[i];
                    headerTr.push('<th class="noReportOrgList" id="'+org.orgId+'"  onclick="dataStatisticsCreate.openFormBox(this)">'+org.orgName+'</th>');
                }
            }
            headerTr.push('</tr>');
            return headerTr.join("");
            break;
        default :return "";
    }

}
dataStatisticsBuildTable.buildTableContentTr=function(cfg){
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
                table.push(dataStatisticsBuildTable.buildTableContentTr(childCfg));
            }
        }else if(item.type=="field"){
            table.push(dataStatisticsBuildTable.buildField(cfg,item));
        }
        table.push('</tr>');
        trFlag=true;
    }
    return table.join('');
}
dataStatisticsBuildTable.buildField=function(cfg,item){
    var fieldHtml=[];
    fieldHtml.push('<td rowspan="'+(item.childrenSize||"1")+'" colspan="'+(item.colspan||'1')+'">'+item.name+'（'+item.unit+'）'+'<span style="visibility:hidden;">'+item.name+'（'+item.unit+'）'+'</span>'+'</td>');
    switch (cfg.method){
        case 'template':
            fieldHtml.push('<td>'+item.maxNumber+'</td>');
            fieldHtml.push('<td>'+item.minNumber+'</td>');
            fieldHtml.push('<td>'+item.beyondRemind+'</td>');
            return fieldHtml.join("");
        case 'createForm':
            var numberType=parseInt(item.decimalDigits)==0?'digits=true':'number=true';
            var validate='class="{range:['+item.minNumber+','+item.maxNumber+'],max:5}"';
            fieldHtml.push('<td><input '+numberType+' max="'+item.maxNumber+'" min="'+item.minNumber+'" type="text" name="'+item.uuid+'"></td>');
            return fieldHtml.join("");
        case 'browseDataListTable':
            /** 上次汇总保存的数据 **/
            if(cfg.orgData){
                fieldHtml.push('<td class="'+item.uuid+'">'+(cfg.orgData[item.uuid]?cfg.orgData[item.uuid]:"")+'</td>');
            }else{
                fieldHtml.push('<td></td>');
            }
            /** 本次查询汇总数据 **/
            if(cfg.totalData){
                fieldHtml.push('<td class="'+item.uuid+'">'+(cfg.totalData[item.uuid]?cfg.totalData[item.uuid]:"")+'</td>');
            }else{
                fieldHtml.push('<td class="'+item.uuid+'"></td>');
            }
            /** 本单位内部数据 **/
            if(cfg.unitData){
                fieldHtml.push('<td >'+(cfg.unitData[item.uuid]?cfg.unitData[item.uuid]:"")+'</td>');
            }else{
                fieldHtml.push('<td  onclick="dataStatisticsCreate.openFormBox(this)"></td>');
            }
            /** 已上报单位 **/
            if(cfg.reportedOrgList&&cfg.reportedOrgList.length>0){
                for(var i=0;i<cfg.reportedOrgList.length;i++){
                    var org=cfg.reportedOrgList[i];
                    fieldHtml.push('<th onclick="dataStatisticsCreate.openFormBox(this)">'+(org[item.uuid]?org[item.uuid]:"")+'</th>');
                }
            }
            /** 未上报单位 **/
            if(cfg.noReportOrgList&&cfg.noReportOrgList.length>0){
                for(var i=0;i<cfg.noReportOrgList.length;i++){
                    var org=cfg.noReportOrgList[i];
                    fieldHtml.push('<td onclick="dataStatisticsCreate.openFormBox(this)"></td>');
                }
            }
            return fieldHtml.join("");
            break;
        default :return "";
    }
}