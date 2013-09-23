function postData(obj) {
	jQuery.ajax({
		type:'POST',
		url: PM_URI + '/ws/api/filedownload',
		data: obj.toString(),
		dataType: 'text',
        contentType:'text/plain',
		processData: false
	})
}

function json2string(json){
	seen = []

	return JSON.stringify(json, function(key, val) {
	   if (typeof val == "object") {
	        if (seen.indexOf(val) >= 0)
	            return
	        seen.push(val)
	    }
	    return val
	})
}