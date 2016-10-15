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
    statisticsTableBoxId:"dataStatisticsFormBox",
    statisticsTableId:"dataStatisticsTable",
    createStatisticsTableId:"createDataStatisticsTable"
};
/**
 * 根据日期查看数据
 */
dataStatisticsCreate.query=function(){
    var date=$("#query-data-date").val();
    if(date==""){
        return;
    }else{
        $.get(dataStatistics.buildCreateFormUrl+"?date="+date,function(data){
            if(data.status=="success"){
                var cfgData=data.data;
                cfgData.method='browseDataListTable';
                cfgData.tableConfig=dataStatisticsCreate.tableConfig;
                cfgData.tableId=dataStatisticsCreate.statisticsTableId;
                var html=dataStatisticsBuildTable.buildTable(cfgData);
                $("#"+dataStatisticsCreate.statisticsTableBoxId).html(html);
                var options = {
                    fixColumnColor: '#f2f2f2',
                    fixColumnNumber: 2,
                    width: $("#"+dataStatisticsCreate.statisticsTableBoxId).width(),
                    height: document.body.clientHeight-$("#"+dataStatisticsCreate.statisticsTableBoxId).offset().top-30
                };
                $("#"+dataStatisticsCreate.statisticsTableId).fixedTable(options);
            }else{
                LobiboxUtil.notify(data.message);
            }
        });
    }
}
/**
 * 去后台请求模板数据、下级与本级填报的数据，用于显示数据采集的表单
 */
dataStatisticsCreate.initCreateForm=function(){
    $.get(dataStatistics.buildCreateFormUrl,function(data){
        if(data.status=="success"){
            var cfgData=data.data;
            cfgData.method='browseDataListTable';
            dataStatisticsCreate.tableConfig=cfgData.tableConfig;
            cfgData.tableId=dataStatisticsCreate.statisticsTableId;
            var html=dataStatisticsBuildTable.buildTable(cfgData);
            $("#"+dataStatisticsCreate.statisticsTableBoxId).html(html);
            var options = {
                fixColumnColor: '#f2f2f2',
                fixColumnNumber: 2,
                width: $("#"+dataStatisticsCreate.statisticsTableBoxId).width(),
                height: document.body.clientHeight-$("#"+dataStatisticsCreate.statisticsTableBoxId).offset().top-30
            };
            $("#"+dataStatisticsCreate.statisticsTableId).fixedTable(options);
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}
dataStatisticsCreate.openFormBox=function(obj){
    var orgNode;
    if(obj.className=="noReportOrgList"){
        orgNode=obj;
    }else{
        var orgNodeIndex=obj.parentNode.children.length-obj.cellIndex;
        var orgNodes=$(obj).parent().prevAll(".dstHeader").children();
        orgNode=orgNodes[orgNodes.length-orgNodeIndex];
    }
    if(orgNode.className=="noReportOrgList"){
        dataStatisticsCreate.openCreateDataForm(orgNode);
    }else{
        dataStatisticsCreate.openUpdateDataItemForm(orgNode);
    }
}
/**
 * 打开新建数据的表单
 * @param obj
 */
dataStatisticsCreate.openCreateDataForm=function(orgNode){
    /*var orgNode;
    if(obj.className=="noReportOrgList"){
        orgNode=obj;
    }else{
        var orgNodeIndex=obj.parentNode.children.length-obj.cellIndex;
        var orgNodes=$(obj).parent().prevAll(".dstHeader").children();
        orgNode=orgNodes[orgNodes.length-orgNodeIndex];
    }*/
    var orgId=orgNode.id;
    var orgName=orgNode.innerText;
    var tableHtml=dataStatisticsBuildTable.buildTable({tableConfig:dataStatisticsCreate.tableConfig,method:"createForm",tableId:dataStatisticsCreate.createStatisticsTableId});
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
    ajaxUtil.submit({
        form:$(".createDataForm"),
        data:{jsonStr:"{"+jsonStr+"}"},
        url:dataStatistics.createUrl,
        success:function(data){
            if(data.status=="success"){
                dataStatisticsCreate.submitCreateDataFormBack(data.data);
                LobiboxUtil.notify("保存成功！");
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
}
/**
 * 提交保存成功之后，修改本次查询汇总列
 */
dataStatisticsCreate.submitCreateDataFormBack=function(data){
    var content=data.content;
    if(content){
        var orgNodes=$("#"+dataStatisticsCreate.statisticsTableId+" .dstHeader th");
        var orgNode=orgNodes.filter("#"+data.orgId);
        $('th[id="'+data.orgId+'"]').removeAttr("class");
        var orgNodeIndex=orgNodes.length-orgNode[0].cellIndex;
        for(key in content){
            $("."+key).each(function(){
                var newValue=content[key];
                var oldValue=this.innerText;
                $(this).parent().children()[$(this).parent().children().length-orgNodeIndex].innerText=newValue;
                if(oldValue==""){
                    oldValue=0;
                }else{
                    oldValue=oldValue.indexOf(".")==-1?parseInt(oldValue):parseFloat(oldValue);
                }
                this.innerText=oldValue+newValue;
            });
        }
    }
}
dataStatisticsCreate.getCreateDataJson=function(){
    var formObj=$(".createDataForm");
    var content=ajaxUtil.getumberJsonStr(formObj);
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
dataStatisticsCreate.openUpdateDataItemForm=function(orgNode){
    /*var orgNodeIndex=obj.parentNode.children.length-obj.cellIndex;
    var orgNodes=$(obj).parent().prevAll(".dstHeader").children();
    var orgNode=orgNodes[orgNodes.length-orgNodeIndex];*/
    var orgId=orgNode.id;
    var orgName=orgNode.innerText;
    var html=[];
    html.push('<form class="updateDataItemForm">');
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
    var tableHtml=[];
    tableHtml.push('<table class="formTable">');
        tableHtml.push('<tr>');
            tableHtml.push('<td>数据项名称</td>');
            tableHtml.push('<td><input name="datavalue" value=""></td>')
        tableHtml.push('</tr>');
        tableHtml.push('<tr>');
            tableHtml.push('<td>数据值</td>');
            tableHtml.push('<td><input name="datavalue" value=""></td>')
        tableHtml.push('</tr>');
        tableHtml.push('<tr>');
            tableHtml.push('<td>备注</td>');
            tableHtml.push('<td><textarea  style="resize: none;" ></textarea></td>')
        tableHtml.push('</tr>');
    tableHtml.push('</table>');
    html.push(tableHtml.join(""));
    html.push('</form>');
    dataStatisticsCreate.formWindow=LobiboxUtil.formWindow(
        {
            title:"为"+orgName+"修改数据项",
            html:html.join(""),
            height:300,
            submit:dataStatisticsCreate.submitCreateDataForm,
            shown:function(){
                $(".updateDataItemForm").validate();
                $(".updateDataItemForm").valid();
            }
        }
    );
}
/**
 保存本次统计数据
 **/
dataStatisticsCreate.saveStatisticsData=function(){
    var trNodes=$("#"+dataStatisticsCreate.statisticsTableId +" tr")
    var headerTrNode=$(trNodes[0]).children();
    var cellIndex=headerTrNode.length-headerTrNode.filter("#queryTotal")[0].cellIndex;
    var json=[];
    for(var i=1;i<trNodes.length;i++){
        var tdNodes=trNodes[i].children;
        var tdNode=tdNodes[tdNodes.length-cellIndex];
        var dataValue=tdNode.innerText;
        var dataKey=tdNode.className;
        var value=$.trim(dataValue);
        if(!(value==""||value=="0"||isNaN(value))){
            json.push('"'+dataKey+'":'+(value.indexOf(".")==-1?parseInt(value):parseFloat(value)));
        }
    }
    var jsonStr='"content":{'+json.join(",")+'}';
    var date=$("#query-data-date").val();
    date=date==""?timeUtil.getCurrentDateTime():date;
    jsonStr+=(',"date":"'+date+'"');
    jsonStr+=(',"orgId":"'+loginRootOrganization.uuid+'"');
    jsonStr+=(',"orgName":"'+loginRootOrganization.name+'"');
    jsonStr+=(',"createOrgId":"'+loginRootOrganization.uuid+'"');
    jsonStr+=(',"receiveOrgId":"'+loginRootOrganization.parentId+'"');
    jsonStr+=(',"dataType":"org"');
    jsonStr+=(',"reportState":"reported"');

    ajaxUtil.ajax({
        data:{jsonStr:"{"+jsonStr+"}"},
        url:dataStatistics.createUrl,
        success:function(data){
            if(data.status=="success"){
                dataStatisticsCreate.submitCreateDataFormBack(data.data);
                LobiboxUtil.notify("保存成功！");
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
}

/**
 上报机构数据
 ****/
 dataStatisticsCreate.reportStatisticsData=function(){
     var date=$("#query-data-date").val();
     date=date==""?timeUtil.getCurrentDateTime():date;
 }