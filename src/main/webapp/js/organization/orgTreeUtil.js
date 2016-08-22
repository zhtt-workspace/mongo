/**
 * Created by zhtt on 2016/8/13.
 */
function orgTreeUtil(obj){
    var setting={
        view: {
            selectedMulti: false
        },
        async: {
            enable: true,
            url:typeof obj=="undefined"?"":obj.url,
            autoParam:typeof obj=="undefined"?["uuid"]:obj.autoParam
        },
        callback: {
            onClick: onClick,
            onAsyncSuccess:onAsyncSuccess
        }
    };
    this.init=function(){
        if(check()){
            $.fn.zTree.init($("#"+obj.treeDivId), setting);
        }
    }
    function check(){
        if(typeof obj=="undefined"||typeof obj.treeDivId=="undefined"){
            LobiboxUtil.notify("树容器ID未传入！");
            return false;
        }else if($("#"+obj.treeDivId).length>1){
            LobiboxUtil.notify("树容器ID："+obj.treeDivId+" 已存在！");
            return false;
        }
        var url=obj.url;
        if(typeof url=="undefined"){
            LobiboxUtil.notify("数据请求地址未传入！");
            return false;
        }
        return true;

    }
    function onClick(event, treeId, treeNode){
        if(typeof obj.onClick=="function"){
            obj.onClick(event, treeId, treeNode);
        }
    }
    function onAsyncSuccess(event,treeId,treeNode,msg){
        if(typeof obj.onAsyncSuccess=="function"){
            obj.onAsyncSuccess(event, treeId, treeNode);
        }
    }
}