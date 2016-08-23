/**
 * Created by zhtt on 2016/8/10.
 */


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
function zTreeUtil(options){
    this.init=function(){
        if(checkOptions()){
            initSetting();
            $.fn.zTree.init($("#"+options.treeDivId), setting);
        }
    }
    var setting={
        view: {
            selectedMulti: false,
            dblClickExpand: false
        },
        async: {
            enable: true,
            url:typeof options=="undefined"?"":options.url,
            autoParam:typeof options=="undefined"?["uuid"]:options.autoParam
        },
        callback: {
            onClick: onClick,
            onAsyncSuccess:onAsyncSuccess,
            onCheck: onCheck
        }
    };
    /**
     * 检查必填参数是否有
     * @returns {boolean}
     */
    function checkOptions(){
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

    function initSetting(){
        if(typeof options.radio!="undefined"&&options.radio){
            setting.check= {
                enable: true,
                chkStyle: "radio",
                radioType: "all"
            }
        }
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
                if(typeof data=="object"&&data.length>0){
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    treeObj.addNodes(treeNode, data);
                }
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
     * 当点击单选框或复选框时，触发的事件
     * @param e
     * @param treeId
     * @param treeNode
     */
    function onCheck(e, treeId, treeNode){
        if(typeof options.onCheck=="function"){
            options.onAsyncSuccess(e, treeId, treeNode,msg);
        }
    }

    /**
     * 获取选中的节点，并返回当前树对象
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
/** 增加右键菜单  **/
(function (window) {
    yzTreeUtils = {
        addMenu: function (zTreeId, menuId) {
            /// <summary>
            /// 增加右键菜单
            /// </summary>
            /// <param name="_zTreeId">_zTreeID</param>
            /// <param name="menuId">右键菜单ID</param>
            var _zTree = $.fn.zTree.getZTreeObj(zTreeId);
            var _rMenu = $("#" + menuId);
            if (_zTree && _rMenu) {
                _rMenu.css({
                    "position": "absolute",
                    "visibility": "hidden",
                    "top": 0,
                    "background-color": "#555",
                    "text-align": "left",
                    "padding": "2px"
                });
                _rMenu.find('ul').css({
                    "margin": "1px 0",
                    "padding": "0 5px",
                    "cursor": "pointer",
                    "background-color": " #DFDFDF",
                    "list-style": "none outside none"
                });
                _zTree.setting.callback.onRightClick = function (event, treeId, treeNode) {
                    if (treeNode && !treeNode.noR) {
                        _zTree.selectNode(treeNode);
                        _rMenu.find('ul').show();
                        var x = event.clientX, y = event.clientY;
                        _rMenu.css({ "top": y + "px", "left": x + "px", "visibility": "visible" });
                    }
                }
                $("body").bind("mousedown", function (event) {
                    if (!(event.target.id == menuId || $(event.target).parents("#" + menuId).length > 0)) {
                        _rMenu.css({ "visibility": "hidden" });
                    }
                });
            }
        }
    }
    window.yzTreeUtils = yzTreeUtils;
})(window);