<section id="cookiesBox" style="display:none">
	<div class="eupopup-container eupopup-container-bottom eupopup-color-default">
	    <div class="eupopup-body">
	        <a href="#" class="eupopup-closebutton">
	            <i class="fa fa-times" aria-hidden="true"></i>
	        </a>
	        [#if content.cookiesText?? && content.cookiesText?has_content]
	        	${cmsfn.decode(content).cookiesText!}
	        [/#if]
	        <div class="eupopup-buttons">
	        		[#if content.acceptLabel?? && content.acceptLabel?has_content]
			        	<a href="#" class="eupopup-button eupopup-button_1">${content.acceptLabel!"Aceptar"}</a>
			        [/#if]
	            <div class="clearfix"></div>
	        </div>
	        <div></div>
	    </div>
	</div>
</section>
