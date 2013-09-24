function postData(obj) {
	if(m_resultSet<1){
		alert("No result to generate");
		return;
	}
	
	jQuery.ajax({
		type:'POST',
		url: PM_URI + '/ws/api/filedownload',
		data: obj.toString(),
		dataType: 'text',
        contentType:'text/plain',
		processData: false,
		success:function(data){
			jQuery('#downloadPlace').append('<a href="' + data.toString() + '">Click here to download</a>')
			//window.location.href = PM_URI + '/' + data;
		}
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