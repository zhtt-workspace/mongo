/**
 * Created by zhtt on 2016/8/10.
 */

zTreeUtil.prototype.comboTree=function(){
    if(this.checkOptions()){
        this.initSetting();
        var _options=this._options;
        var _this=this;
        $("#"+this._options.inputId).click(function(){
            var jqueryObj = $(this);
            var jqueryObjOffset = $(this).offset();
            var zindex=parseInt($("[style*='z-index']").attr("style").match(/z-index.*;/)[0].match(/[\d]+/))+1;
            $("#"+_options.treeDivId+"Container").css({left:jqueryObjOffset.left + "px", top:jqueryObjOffset.top + this.offsetHeight + "px","z-index":zindex}).slideDown("fast");
            $("#"+_options.treeDivId+"Container").show();
            //$("body").bind("mousedown", _this.onBodyDown);
            ;
        });
        this.addComboTreeHtml();
        $.fn.zTree.init($("#"+this._options.treeDivId), this.setting);
    }
}
zTreeUtil.prototype.addComboTreeHtml=function(){
    var _this=this;
    var html=[];
    html.push('<div id="'+this._options.treeDivId+'Container" style="display:none; position: absolute;">');
    html.push('<div class="panel panel-default">');
    html.push('<div class="panel-body" style="overflow: auto;">');
    html.push('<ul id="'+this._options.treeDivId+'" class="ztree" style="margin-top:0; width:180px; height: 200px;"></ul>');
    html.push('</div>');
    html.push('<div class="panel-footer">');
    html.push('<button type="button" id="'+this._options.treeDivId+'btn">确定</button>');
    html.push('<button type="button" id="'+this._options.treeDivId+'btn2">关闭</button>');
    html.push('</div>');
    html.push('</div>');
    html.push('</div>');
    $("body").append(html.join(""));
    $("#"+this._options.treeDivId+'btn').click(function(){
        var nodes=_this.getSelectedNodes().nodes;
        if(typeof _this._options.enter=="function"){
            _this._options.enter(nodes);
        }else{
            $("#"+_this._options.inputId).after($("#"+_this._options.inputId).clone().attr("id",_this._options.inputId+"_").hide()).removeAttr("name");
            if(nodes!=null&&nodes.length>0){
                var nodename=[];
                var nodeid=[];
                for(var i=0;i<nodes.length;i++){
                    var node=nodes[i];
                    nodename.push(node.name);
                    nodeid.push(node.uuid);
                }
                $("#"+_this._options.inputId).val(nodename.join(","));
                $("#"+_this._options.inputId+"_").val(nodeid.join(","));
            }
        }
        _this.hideCombo();
    });
    $("#"+this._options.treeDivId+'btn2').click(function(){
        _this.hideCombo();
    });
}
zTreeUtil.prototype.hideCombo=function() {
    $("#"+this._options.treeDivId+"Container").fadeOut("fast");
    //$("body").unbind("mousedown", this.onBodyDown);
}

zTreeUtil.prototype.onBodyDown=function(event) {
    /*if ($("#"+this._options.treeDivId+"Container",event.target).length!=0) {
        this.hideCombo();
    }*/
}