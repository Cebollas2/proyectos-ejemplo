 $(document).ready(function() {
	$(function() {
		var visit=GetCookie("cookies_surestao");
		if (visit==1){ 
			popboxCookies(true);
		}else {
			popboxCookies(false);
		}
	});

	$('.eupopup-button_1').click(function(event) {
		event.preventDefault();
		accept_cookies();
	});
	
	$('.eupopup-closebutton').click(function(event) {
		event.preventDefault();
		$('#cookiesBox').css('display', 'none');
	});

 });
 
 function GetCookie(name) {
		var arg=name+"=";
		var alen=arg.length;
		var clen=document.cookie.length;
		var i=0;

		while (i<clen) {
			var j=i+alen;

			if (document.cookie.substring(i,j)==arg)
				return "1";
			i=document.cookie.indexOf(" ",i)+1;
			if (i==0)
				break;
		}

		return null;
	}
 
 function getCookieValue(cname) {
	  var name = cname + "=";
	  var decodedCookie = decodeURIComponent(document.cookie);
	  var ca = decodedCookie.split(';');
	  for(var i = 0; i <ca.length; i++) {
	    var c = ca[i];
	    while (c.charAt(0) == ' ') {
	      c = c.substring(1);
	    }
	    if (c.indexOf(name) == 0) {
	      return c.substring(name.length, c.length);
	    }
	  }
	  return "";
	}
 
 function accept_cookies(){
		var expire=new Date();
		expire=new Date(expire.getTime()+7776000000);
		document.cookie="cookies_surestao=aceptada;path=/;expires="+expire+"";

		var visit=GetCookie("cookies_surestao");

		if (visit==1){ 
			popboxCookies(true);
		}else {
			popboxCookies(false);
		}
	}
 
 function popboxCookies(showBox) {
		var e = $('#cookiesBox');
		if(showBox)
			e.css('display', 'none');
		else
			e.css('display', 'block');
	}
 
 function setCookie(cookieName, cookieValue) {
		var expire=new Date();
		expire=new Date(expire.getTime()+7776000000);
		document.cookie= cookieName + "=" + cookieValue + ";path=/;expires="+expire+"";
	}

	function delete_cookie(name) {
	    document.cookie = name + '=;expires=Mon, 01 Jan 1968 00:00:01 GMT;';
	};