/**
 * 录入本机构数据，并统计汇总下级数据，同时可为下级数据补录、修改，但要记录日志
 * Created by zhtt on 2016/9/18.
 */
$(function(){
    dataStatisticsCreate.initCreateForm();
})
var dataStatisticsCreate={
    tableConfig:null,
    formWindow:null
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
                var html=dataStatisticsBuildTable.buildTable(cfgData);
                $("#dataStatisticsFormBox").html(html);
                var options = {
                    fixColumnColor: '#f2f2f2',
                    fixColumnNumber: 2,
                    width: $("#dataStatisticsFormBox").width(),
                    height: document.body.clientHeight-$("#dataStatisticsFormBox").offset().top-30
                };
                $("#table").fixedTable(options);
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
            var html=dataStatisticsBuildTable.buildTable(cfgData);
            $("#dataStatisticsFormBox").html(html);
            var options = {
                fixColumnColor: '#f2f2f2',
                fixColumnNumber: 2,
                width: $("#dataStatisticsFormBox").width(),
                height: document.body.clientHeight-$("#dataStatisticsFormBox").offset().top-30
            };
            $("#table").fixedTable(options);
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
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
    var tableHtml=dataStatisticsBuildTable.buildTable({tableConfig:dataStatisticsCreate.tableConfig,method:"createForm"});
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
        url:dataStatistics.createUrl,
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
dataStatisticsCreate.openUpdateDataItemForm=function(obj){
    var orgNodeIndex=obj.parentNode.children.length-obj.cellIndex;
    var orgNodes=$(obj).parent().prevAll(".dstHeader").children();
    var orgNode=orgNodes[orgNodes.length-orgNodeIndex];
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
    tableHtml.push('<table>');
        tableHtml.push('<tr>');
            tableHtml.push('<td>数据项名称</td>');
            tableHtml.push('<td><input name="datavalue" value=""></td>')
        tableHtml.push('</tr>');
        tableHtml.push('<tr>');
            tableHtml.push('<td>数据值</td>');
            tableHtml.push('<td><input name="datavalue" value=""></td>')
        tableHtml.push('</tr>');
    tableHtml.push('</table>');
    html.push(tableHtml.join(""));
    html.push('</form>');
    dataStatisticsCreate.formWindow=LobiboxUtil.formWindow(
        {
            title:"为"+orgName+"修改数据项",
            html:html.join(""),
            submit:dataStatisticsCreate.submitCreateDataForm,
            shown:function(){
                $(".updateDataItemForm").validate();
                $(".updateDataItemForm").valid();
            }
        }
    );

}