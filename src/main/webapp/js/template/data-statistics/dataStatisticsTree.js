/**
 * Created by zhtt on 2016/8/8.
 */
var dataStatisticsTree={
    treeUrl:ctx+"/template/data-statistics/tree?tree=false",
    treeId:"dataStatisticsTreeDiv"
}
dataStatisticsTree.setting={
    callback: {
        onClick: dataStatisticsTreeOnclick
    }
};
/** 树节点的点击事件回调方法 **/
function dataStatisticsTreeOnclick (event, treeId, treeNode){
    alert(treeId);
}
/** 加载树的节点数据 **/
dataStatisticsTree.loadTreeData=function(){
    $.get(dataStatisticsTree.treeUrl,function(data){
        if(data.status=="success"&&data.data==null){
            LobiboxUtil.notify(data.message);
        }else if(data.status=="error"){
            LobiboxUtil.notify(data.message);
        }
    });
}
$(function(){
    $.fn.zTree.init($("#"+dataStatisticsTree.treeId), dataStatisticsTree.setting, dataStatisticsTree.zNodes);
});
dataStatisticsTree.zNodes =[
    { name:"父节点1 - 展开", open:true,id:"123"},
    { name:"父节点2 - 折叠",
        children: [
            { name:"父节点21 - 展开", open:true,
                children: [
                    { name:"叶子节点211", open:true,
                        children: [
                            { name:"叶子节点235"},
                            { name:"叶子节点236"},
                            { name:"叶子节点237"},
                            { name:"叶子节点238", open:true,
                                children: [
                                    { name:"叶子节点235"},
                                    { name:"叶子节点236"},
                                    { name:"叶子节点237"},
                                    { name:"叶子节点238", open:true,
                                        children: [
                                            { name:"叶子节点235"},
                                            { name:"叶子节点236"},
                                            { name:"叶子节点237"},
                                            { name:"叶子节点238"}
                                        ]}
                                ]}
                        ]},
                    { name:"叶子节点212"},
                    { name:"叶子节点213"},
                    { name:"叶子节点214", open:true,
                        children: [
                            { name:"叶子节点235"},
                            { name:"叶子节点236"},
                            { name:"叶子节点237"},
                            { name:"叶子节点238", open:true,
                                children: [
                                    { name:"叶子节点235"},
                                    { name:"叶子节点236"},
                                    { name:"叶子节点237"},
                                    { name:"叶子节点238", open:true,
                                        children: [
                                            { name:"叶子节点235"},
                                            { name:"叶子节点236"},
                                            { name:"叶子节点237"},
                                            { name:"叶子节点238", open:true,
                                                children: [
                                                    { name:"叶子节点235"},
                                                    { name:"叶子节点236"},
                                                    { name:"叶子节点237"},
                                                    { name:"叶子节点238", open:true,
                                                        children: [
                                                            { name:"叶子节点235"},
                                                            { name:"叶子节点236"},
                                                            { name:"叶子节点237"},
                                                            { name:"叶子节点238", open:true,
                                                                children: [
                                                                    { name:"叶子节点235"},
                                                                    { name:"叶子节点236"},
                                                                    { name:"叶子节点237"},
                                                                    { name:"叶子节点238", open:true,
                                                                        children: [
                                                                            { name:"叶子节点235"},
                                                                            { name:"叶子节点236"},
                                                                            { name:"叶子节点237"},
                                                                            { name:"叶子节点238"}
                                                                        ]}
                                                                ]}
                                                        ]}
                                                ]}
                                        ]}
                                ]}
                            ]
                    }
                ]},
            { name:"父节点22 - 折叠",
                children: [
                    { name:"叶子节点221"},
                    { name:"叶子节点222"},
                    { name:"叶子节点223"},
                    { name:"叶子节点224"}
                ]},
            { name:"父节点23 - 折叠",
                children: [
                    { name:"叶子节点231"},
                    { name:"叶子节点232"},
                    { name:"叶子节点233"},
                    { name:"叶子节点234"}
                ]}
        ]},
    { name:"父节点3 - 没有子节点", isParent:true}

];