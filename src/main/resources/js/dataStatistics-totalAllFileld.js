/**
 * @author zhtt
 */
function(obj, prev) { 
	if (obj.content) {
		var oc = obj.content; 
		for (var k in prev) if (oc[k] !== undefined){
			prev[k] += oc[k];
		}
	}
    prev.unitName=obj.unitName;
}
