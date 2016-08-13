/**
 * Created by zhtt on 2016/8/13.
 */
var modalUtil={}
/**
 * .class
 * #id
 * 等……
 * @param selectors
 */
modalUtil.close=function(selectors){
    $(selectors).modal("hide");
}