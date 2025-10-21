function auditDocument(uuid) {
	data = {};
	data['uuidDocumento'] = uuid;
	$.ajax({
		type: 'POST',
		url: PATH_API + '/private/auditar-documentos/v1/registrar',
	  	data: JSON.stringify(data),
	  	async: false,
	  	contentType: 'application/json; charset=utf-8',
        success : function(response) {
        	console.log(response);
        }, 
        error : function(data) {
			console.log(data);
		}
	});
}