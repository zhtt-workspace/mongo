/**
 * @author zhtt
 * 获取当前机构的直接分机构或根机构
 */
function(obj, prev) {
	var prevCode=prev.code;
	var objCode=obj.code;
	var re =new RegExp("^"+objCode);
	if(re.test(prevCode)&prevCode.length>=objCode.length){
		if(prev.rootParentCode){
			if(prev.rootParentCode.length<obj.code.length){
				prev.rootParentCode=obj.code;
				prev.rootParentUuid=obj.uuid;
				prev.rootParentName=obj.name;
			}
		}else{
			prev.rootParentCode=obj.code;
			prev.rootParentUuid=obj.uuid;
			prev.rootParentName=obj.name;
		}
	}
}
