<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Welcome</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<script>
	$(document).ready(function(){
		
		$( "#dialog" ).dialog({
	      autoOpen: false,
	      height: 600,
	      width: 1050,
	      show: {
	        effect: "clip",
	        duration: 500
	      },
	      hide: {
	        effect: "clip",
	        duration: 500
	      },
	      close: function() {
	    	  gp_progressBarOff();
	    	  $( "#dialog" ).removeClass( "zoom" );
	        },
	      dialogClass: 'zoom'
	    });
		
		$( "#btnZoom" ).live("click", function(event){
			
			var lo_dialog = null;
			var lo_iframe = null;
			
			try{
				
				lo_dialog 	= $( "#dialog" );
				lo_iframe	= $("<iframe />").attr("src", "/stockWeb/EnjoyGenericSrv?service=servlet.DemoLookUpServlet&pageAction=new")
											 .attr("width", "100%")
											 .attr("height", "100%")
											 .attr("border", "0");
				
				gp_progressBarOn();
				lo_dialog.empty();
				lo_dialog.append(lo_iframe).dialog( "open" );
				event.preventDefault();
			}catch(e){
				alert("btnZoom :: " + e);
			}
	    });
		
		gp_progressBarOff();
		
	});
	
	function lp_returnData(av_val){
		
		try{
			
			$("#name").val(av_val);
			$( "#dialog" ).dialog( "close" );
		}catch(e){
			alert("lp_returnData :: " + e);
		}
		
	}
	
	</script>
</head>
<body>
	<form id="frm" action="<%=servURL%>/EnjoyGenericSrv">
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<div id="contents">
			<input type="text" id="name" name="name" value="" />
			<input type="button" id="btnZoom" name="btnZoom" value="Zoom" />
		</div>
		
		
		<div id="dialog" title="Look up"></div>
		<div align="center" class="FreezeScreen" style="display:none;">
        	<center>
        		<img id="imgProgress" valign="center" src="<%=imgURL%>/loading36.gif" alt="" />
        		<span style="font-weight: bold;font-size: large;color: black;">Loading...</span>
        	</center>
    	</div>
	</form>
</body>
</html>