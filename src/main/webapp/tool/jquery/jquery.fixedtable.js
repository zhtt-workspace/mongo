/* html table 表头列头固定 
* author：樊得涛
* e-mail:ustbtaotao@163.com
* time: 20140901
*/
(function ($) {
    $.fn.fixedTable = function (options) {
        var opts = $.extend({}, $.fn.fixedTable.defaults, options);
        // iterate and reformat each matched element    
        return this.each(function () {
            $this = $(this);
            // build element specific options    
            var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
            var tableID = $(this).attr("id");
            // update element styles    
            var fixColumnColor = o.fixColumnColor;
            var fixColumnNumber = o.fixColumnNumber;
            var width = o.width;
            var height = o.height;
            /// <summary>
            ///     锁定表头和列
            ///     <para> sorex.cnblogs.com </para>
            /// </summary>
            /// <param name="tableID" type="String">
            ///     要锁定的Table的ID
            /// </param>
            /// <param name="fixColumnNumber" type="Number"> 
            ///     要锁定列的个数
            /// </param>
            /// <param name="fixColumnColor" type="String">
            ///     要锁定列的颜色
            /// </param>
            /// <param name="width" type="Number">
            ///     显示的宽度
            /// </param>
            /// <param name="height" type="Number">
            ///     显示的高度
            /// </param>
            if ($("#" + tableID + "_tableLayout").length != 0) {
                $("#" + tableID + "_tableLayout").before($("#" + tableID));
                $("#" + tableID + "_tableLayout").empty();
            }
            else {
                $("#" + tableID).after("<div id='" + tableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
            }
            $('<div id="' + tableID + '_tableFix"></div>'
    + '<div id="' + tableID + '_tableHead"></div>'
    + '<div id="' + tableID + '_tableColumn"></div>'
    + '<div id="' + tableID + '_tableData"></div>').appendTo("#" + tableID + "_tableLayout");
            var oldtable = $("#" + tableID);
            var tableFixClone = oldtable.clone(true);
            tableFixClone.attr("id", tableID + "_tableFixClone");
            $("#" + tableID + "_tableFix").append(tableFixClone);
            var tableHeadClone = oldtable.clone(true);
            tableHeadClone.attr("id", tableID + "_tableHeadClone");
            $("#" + tableID + "_tableHead").append(tableHeadClone);
            var tableColumnClone = oldtable.clone(true);
            tableColumnClone.attr("id", tableID + "_tableColumnClone");
            $("#" + tableID + "_tableColumn").append(tableColumnClone);
            $("#" + tableID + "_tableData").append(oldtable);
            $("#" + tableID + "_tableLayout table").each(function () {
                $(this).css("margin", "0");
            });
            var HeadHeight = $("#" + tableID + "_tableHead tr:eq(0)").height();
            HeadHeight += 2;
            $("#" + tableID + "_tableHead").css("height", HeadHeight);
            $("#" + tableID + "_tableFix").css("height", HeadHeight);
            var ColumnsWidth = 0;
            var ColumnsNumber = 0;
            $("#" + tableID + "_tableColumn tr:last td:lt(" + fixColumnNumber + ")").each(function () {
                ColumnsWidth += $(this).outerWidth(true);
                ColumnsNumber++;
            });
            ColumnsWidth += 2;
            if ($.browser.msie) {
                switch ($.browser.version) {
                    case "7.0":
                        if (ColumnsNumber >= 3) ColumnsWidth--;
                        break;
                    case "8.0":
                        if (ColumnsNumber >= 2) ColumnsWidth--;
                        break;
                }
            }
            $("#" + tableID + "_tableColumn").css("width", ColumnsWidth);
            $("#" + tableID + "_tableFix").css("width", ColumnsWidth);
            $("#" + tableID + "_tableData").scroll(function () {
                $("#" + tableID + "_tableHead").scrollLeft($("#" + tableID + "_tableData").scrollLeft());
                $("#" + tableID + "_tableColumn").scrollTop($("#" + tableID + "_tableData").scrollTop());
            });
            $("#" + tableID + "_tableFix").css({ "overflow": "hidden", "position": "relative", "z-index": "50", "background-color": fixColumnColor });
            $("#" + tableID + "_tableHead").css({ "overflow": "hidden", "width": width - 17, "position": "relative", "z-index": "45", "background-color": fixColumnColor });
            $("#" + tableID + "_tableColumn").css({ "overflow": "hidden", "height": height - 17, "position": "relative", "z-index": "40", "background-color": fixColumnColor });
            $("#" + tableID + "_tableData").css({ "overflow": "scroll", "width": width, "height": height, "position": "relative", "z-index": "35" });
            if ($("#" + tableID + "_tableHead").width() > $("#" + tableID + "_tableFix table").width()) {
                $("#" + tableID + "_tableHead").css("width", $("#" + tableID + "_tableFix table").width());
                $("#" + tableID + "_tableData").css("width", $("#" + tableID + "_tableFix table").width() + 17);
            }
            if ($("#" + tableID + "_tableColumn").height() > $("#" + tableID + "_tableColumn table").height()) {
                $("#" + tableID + "_tableColumn").css("height", $("#" + tableID + "_tableColumn table").height());
                $("#" + tableID + "_tableData").css("height", $("#" + tableID + "_tableColumn table").height() + 17);
            }
            $("#" + tableID + "_tableFix").offset($("#" + tableID + "_tableLayout").offset());
            $("#" + tableID + "_tableHead").offset($("#" + tableID + "_tableLayout").offset());
            $("#" + tableID + "_tableColumn").offset($("#" + tableID + "_tableLayout").offset());
            $("#" + tableID + "_tableData").offset($("#" + tableID + "_tableLayout").offset());
            $("#" + tableID + "_tableLayout").css("width", width);
        });
    };
    // 插件的defaults    
    $.fn.fixedTable.defaults = {
        fixColumnColor: 'silver',
        fixColumnNumber: 1,
        width: 600,
        height: 350
    };
})(jQuery);  