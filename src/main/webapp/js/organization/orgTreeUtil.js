/**
 * Created by zhtt on 2016/8/13.
 * {
 *  treeDivId：加载树的div容器【这个是必填】
 *  url:加载节点的地址,
 *  autoParam:参数,
 *  ajaxLoad：点击的时候，是否加载子节点
 *
 *  下面是方法
 *  onClick:点击事件触发的方法
 *  onAsyncSuccess：加载成功后需要执行的方法
 *  getSelectedNodes：获取选中的节点
 * }
 */
function orgTreeUtil(options){
    this.init=function(){
        if(check()){
            $.fn.zTree.init($("#"+options.treeDivId), setting);
        }
    }
    var setting={
        view: {
            selectedMulti: false
        },
        async: {
            enable: true,
            url:typeof options=="undefined"?"":options.url,
            autoParam:typeof options=="undefined"?["uuid"]:options.autoParam
        },
        callback: {
            onClick: onClick,
            onAsyncSuccess:onAsyncSuccess
        }
    };
    /**
     * 检查必填参数是否有
     * @returns {boolean}
     */
    function check(){
        if(typeof options=="undefined"||typeof options.treeDivId=="undefined"){
            LobiboxUtil.notify("树容器ID未传入！");
            return false;
        }else if($("#"+options.treeDivId).length>1){
            LobiboxUtil.notify("树容器ID："+options.treeDivId+" 已存在！");
            return false;
        }
        var url=options.url;
        if(typeof url=="undefined"){
            LobiboxUtil.notify("数据请求地址未传入！");
            return false;
        }
        return true;

    }

    /**
     * 点击事件
     * @param event
     * @param treeId
     * @param treeNode
     */
    function onClick(event, treeId, treeNode){
        if(typeof options.ajaxLoad=="boolean"&&options.ajaxLoad==true){
            ajaxLoad(event, treeId, treeNode);
        }
        if(typeof options.onClick=="function"){
            options.onClick(event, treeId, treeNode);
        }
    }

    /**
     * 点击的时候加载子节点
     * @param event
     * @param treeId
     * @param treeNode
     */
    function ajaxLoad(event, treeId, treeNode){
        if(typeof treeNode.children=="undefined"&&typeof treeNode.isAjaxLoading=="undefined"){
            $.get(options.url+treeNode.uuid,function(data){
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                treeObj.addNodes(treeNode, data);
                treeNode.isAjaxLoading=true;
            });
        }
    }

    /**
     * 加载磖需要执行的方法
     * @param event
     * @param treeId
     * @param treeNode
     * @param msg
     */
    function onAsyncSuccess(event,treeId,treeNode,msg){
        if(typeof options.onAsyncSuccess=="function"){
            options.onAsyncSuccess(event, treeId, treeNode,msg);
        }
    }

    /**
     * {
     * msg:未获取到节点时的提醒信息【可选】
     * callback:获取成功时，需要执行的方法【可选】
     * }
     * @param args【可选】
     * @returns {*}
     */
    this.getSelectedNodes=function(args){
        if(typeof args=="string"){
            args={msg:args};
        }else if(typeof args=="undefined"){
            args={};
        }
        var zTree = $.fn.zTree.getZTreeObj(options.treeDivId);
        if(zTree==null||zTree.getNodes().length==0){
            return {"zTree":null,"nodes":null};;
        }
        var nodes = zTree.getSelectedNodes();
        if(nodes.length==0){
            if(args.msg){
                LobiboxUtil.notify(args.msg);
            }
            return {"zTree":zTree,"nodes":null};
        }else{
            if(typeof args=="object"&&typeof args.callback=="function"){
                args.callback({"zTree":zTree,"nodes":nodes});
            }
            return {"zTree":zTree,"nodes":nodes};
        }
    }
}