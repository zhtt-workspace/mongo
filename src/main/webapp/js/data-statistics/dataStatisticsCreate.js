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
        if(data.state=="success"){

        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}

dataStatisticsCreate.template=function(cfg){
    var table=[];
    table.push('<table class="dstTable table table-bordered table-hover  table-striped">');
    table.push('<tr class="dstHeader">');
    table.push('<th colspan="'+(parseInt(cfg.colspan)-1)+'"  style="width:533px;letter-spacing: 12px;" id="dataitem">数据项</th>');
    table.push('<th style="width:99px;">最小值</th>');
    table.push('<th style="width:99px;">最大值</th>');
    table.push('<th style="width:99px;">阀值提醒</th>');
    table.push('</tr>');

    var childCfg={'data':cfg.children,'trFlag':false,'method':'template'};
    table.push(dataStatisticsTemplateTable.templateTr(childCfg));

    table.push('</table>');
    return table.join('');
}
dataStatisticsCreate.templateTr=function(childCfg){
    var trFlag=childCfg.trFlag||true;
    var buildTableData=childCfg.data;/** 生成table用的数据 **/
    var table=[];
    for(var i=0,j=buildTableData.length;i<j;i++) {
        var item = buildTableData[i];
        if (trFlag&&i>0){
            table.push('<tr>');
        }
        if(item.type=="group"){
            table.push('<td rowspan="'+(item.childrenSize||"1")+'" colspan="'+(item.colspan||'1')+'">'+item.name+'</td>');
            if(item.children){
                table.push(dataStatisticsTemplateTable.templateTr({data:item.children,trFlag:false}));
            }
        }else if(item.type=="field"){
            table.push('<td rowspan="'+(item.childrenSize||"1")+'" colspan="'+(item.colspan||'1')+'">'+item.name+'（'+item.unit+'）</td>');
            table.push('<td>'+item.maxNumber+'</td>');
            table.push('<td>'+item.minNumber+'</td>');
            table.push('<td>'+item.beyondRemind+'</td>');
        }
        table.push('</tr>');
        trFlag=true;
    }
    return table.join('');
}
