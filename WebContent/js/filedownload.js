function postXML(xml) {
	jQuery.ajax({
		type:'POST',
		url: PM_URI + '/ws/api/filedownload',
		data: xml,
		dataType: 'json',
		processData: false
	})
}