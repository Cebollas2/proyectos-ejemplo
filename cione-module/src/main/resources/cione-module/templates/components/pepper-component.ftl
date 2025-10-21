[#if cmsfn.editMode!false]
	<h1>Iframe Pepper Money</h1>
[#else]

	<div id="cmp-pepper">
		[#if model.url?has_content]
			<iframe referrerpolicy="origin" id="pepper-iframe" src="${model.url!}" style="width:100%;height:800px"></iframe>
		[/#if]
	</div>
	
	<script>
		var timeout = 2000;
	
		function initPage(){
			$("#loading").show();
			$.ajax({
				url: "${ctx.contextPath}${content.@handle}",
			  	data: {"iframe":"true"},
			  	success: function(response){
			  		var elem = $(response).find("#pepper-iframe");
			  		if(elem.length > 0){
			  			$("#cmp-pepper").html($(response).filter("#cmp-pepper").html());
			  		} else {
			  			alert("${i18n['cione-module.templates.components.pepper.error']}");
			  			timeout = 0;
			  		}
			  	},
				complete: function(){
					setTimeout( function(){ 
					    $("#loading").hide();
					}  , timeout );
				}
			});
			
		}
		
	</script>
[/#if]