/**
 * Created by zhtt on 2016/8/10.
 */



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