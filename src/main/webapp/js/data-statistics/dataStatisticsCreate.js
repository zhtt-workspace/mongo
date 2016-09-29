/**
 * 录入本机构数据，并统计汇总下级数据，同时可为下级数据补录、修改，但要记录日志
 * Created by zhtt on 2016/9/18.
 */
$(function(){
    dataStatisticsCreate.initCreateForm();
})
var dataStatisticsCreate={
    tableConfig:null,
    formWindow:null,
    createUrl:ctx+"/dataStatistics/create-form"
};
/**
 * 去后台请求模板数据、下级与本级填报的数据，用于显示数据采集的表单
 */
dataStatisticsCreate.initCreateForm=function(){
    $.get(dataStatistics.buildCreateFormUrl,function(data){
        if(data.status=="success"){
            var cfgData=data.data;
            cfgData.method='browseDataListTable';
            dataStatisticsCreate.tableConfig=cfgData.tableConfig;
            var html=dataStatisticsCreate.tableForm(cfgData);
            $("#dataStatisticsFormBox").html(html);
            var options = {
                fixColumnColor: '#f2f2f2',
                fixColumnNumber: 3,
                width: $("#dataStatisticsFormBox").width(),
                height: document.body.clientHeight-$("#dataStatisticsFormBox").offset().top-30
            };
            $("#table").fixedTable(options);
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
            if(cfg.totalObjList){
                headerTr.push('<th >本次查询汇总结果</th>');
            }else{
                headerTr.push('<th class="noReportOrgList">本次查询汇总结果</th>');
            }
            /** 本单位内部 **/
            if(cfg.unitData){
                headerTr.push('<th >本机构录入的数据</th>');
            }else{
                headerTr.push('<th class="noReportOrgList" id="'+loginRootOrganization.uuid+'">本机构录入的数据</th>');
            }
            /** 已上报单位 **/
            if(cfg.reportedOrgList&&cfg.reportedOrgList.length>0){
                for(var i=0;i<cfg.reportedOrgList.length;i++){
                    var org=cfg.reportedOrgList[i];
                    headerTr.push('<th id="'+org.orgId+'"  onclick="dataStatisticsCreate.openCreateDataItemForm(this)">'+org.orgName+'</th>');
                }
            }
            /** 未上报单位 **/
            if(cfg.noReportOrgList&&cfg.noReportOrgList.length>0){
                for(var i=0;i<cfg.noReportOrgList.length;i++){
                    var org=cfg.noReportOrgList[i];
                    headerTr.push('<th class="noReportOrgList" id="'+org.orgId+'"  onclick="dataStatisticsCreate.openCreateDataForm(this)">'+org.orgName+'</th>');
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
            table.push(dataStatisticsCreate.buildField(cfg,item));
        }
        table.push('</tr>');
        trFlag=true;
    }
    return table.join('');
}
dataStatisticsCreate.buildField=function(cfg,item){
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
                fieldHtml.push('<td ></td>');
            }else{
                fieldHtml.push('<td>0</td>');
            }
            /** 本次查询汇总数据 **/
            if(cfg.totalData){
                fieldHtml.push('<td ></td>');
            }else{
                fieldHtml.push('<td>0</td>');
            }
            /** 本单位内部数据 **/
            if(cfg.unitData){
                fieldHtml.push('<td ></td>');
            }else{
                fieldHtml.push('<td  onclick="dataStatisticsCreate.openCreateDataForm(this)"></td>');
            }
            /** 已上报单位 **/
            if(cfg.reportedOrgList&&cfg.reportedOrgList.length>0){
                for(var i=0;i<cfg.reportedOrgList.length;i++){
                    var org=cfg.reportedOrgList[i];
                    fieldHtml.push('<th onclick="dataStatisticsCreate.openCreateDataItemForm(this)">'+(org[item.uuid]?org[item.uuid]:"")+'</th>');
                }
            }
            /** 未上报单位 **/
            if(cfg.noReportOrgList&&cfg.noReportOrgList.length>0){
                for(var i=0;i<cfg.noReportOrgList.length;i++){
                    var org=cfg.noReportOrgList[i];
                    fieldHtml.push('<td onclick="dataStatisticsCreate.openCreateDataForm(this)"></td>');
                }
            }
            return fieldHtml.join("");
            break;
        default :return "";
    }
}
/**
 * 打开新建数据的表单
 * @param obj
 */
dataStatisticsCreate.openCreateDataForm=function(obj){
    var orgNodeIndex=obj.parentNode.children.length-obj.cellIndex;
    var orgNodes=$(obj).parent().prevAll(".dstHeader").children();
    var orgNode=orgNodes[orgNodes.length-orgNodeIndex];
    var orgId=orgNode.id;
    var orgName=orgNode.innerText;
    var tableHtml=dataStatisticsCreate.tableForm({tableConfig:dataStatisticsCreate.tableConfig,method:"createForm"});
    var html=[];
    html.push('<form class="createDataForm">');
    html.push('<input type="hidden" class="orgId" value="'+orgId+'">');
    html.push('<input type="hidden" class="orgName" value="'+orgName+'">');
    html.push('<input type="hidden" class="createOrgId" value="'+loginRootOrganization.uuid+'">');
    html.push('<input type="hidden" class="receiveOrgId" value="'+loginRootOrganization.uuid+'">');
    if(loginRootOrganization.uuid==orgId){
        html.push('<input type="hidden" class="dataType" value="headquarters">');
        html.push('<input type="hidden" class="reportState" value="reported">');
    }else{
        html.push('<input type="hidden" class="dataType" value="org">');
        html.push('<input type="hidden" class="issueState" value="noissue">');
    }
    html.push(tableHtml);
    html.push('</form>');
    dataStatisticsCreate.formWindow=LobiboxUtil.formWindow({title:"为"+orgName+"新建统计数据",html:html.join(""),submit:dataStatisticsCreate.submitCreateDataForm,shown:function(){
        $(".createDataForm").validate(); $(".createDataForm").valid();
    }});
}
/**
 * 提交新建数据的表单
 * @param obj
 */
dataStatisticsCreate.submitCreateDataForm=function(){
    var jsonStr=dataStatisticsCreate.getCreateDataJson();
    var date=$("#query-data-date").val();
    date=date==""?timeUtil.getCurrentDateTime():date;
    jsonStr+=(',"date":"'+date+'"');
    formUtil.submit({
        form:$(".createDataForm"),
        data:{jsonStr:"{"+jsonStr+"}"},
        url:dataStatisticsCreate.createUrl,
        success:function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("保存成功！");
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });

}
dataStatisticsCreate.getCreateDataJson=function(){
    var formObj=$(".createDataForm");
    var content=formUtil.getumberJsonStr(formObj);
    var json=[];
    if(content.length<=0){
        LobiboxUtil.notify("未添加数据！");
    }else{
        json.push('"content":{'+content+'}');
    }
    formObj.find("input:hidden").filter("[class]").each(function(){
        var value=$.trim(this.value);
        json.push('"'+this.className+'":"'+value+'"');
    });
    return json.join(",");
}

/**
 * 打开新建数据项的表单
 * @param obj
 */
dataStatisticsCreate.openCreateDataItemForm=function(obj){

}