<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
		<title>Login Fail</title>
		<%@ include file="/pages/include/enjoyInclude.jsp"%>
		<style type="text/css">
			@import url(http://fonts.googleapis.com/css?family=Roboto:400);
			body {
			  background-color:#fff;
			  -webkit-font-smoothing: antialiased;
			  font: normal 14px Roboto,arial,sans-serif;
			}
			
			.container {
			    padding: 25px;
			    position: fixed;
			    width: 100%;
			}
			
			.form-login {
			    background-color: #EDEDED;
			    padding-top: 10px;
			    padding-bottom: 20px;
			    padding-left: 20px;
			    padding-right: 20px;
			    border-radius: 15px;
			    border-color:#d2d2d2;
			    border-width: 5px;
			    box-shadow: 5px 5px 5px 5px #888888;
			    width: 25%;
			    
			}
			
			h4 { 
			 border:0 solid #fff; 
			 border-bottom-width:1px;
			 padding-bottom:10px;
			 text-align: center;
			}
			
			.wrapper {
			    text-align: center;
			}
			button{
				border:0px;
				background-color:#ffffff;
				cursor:pointerk
			}
			.txtbox
			{
				font-size: 12px;
				color: #336bba;
				font-family: "Tahoma";
				background-color: #eef5fb;
				padding: 1px;
				border: 1px solid #a7c1e5;
			}
			
			.my-form{
				margin-top:12px; 
				-webkit-border-radius: 12px;
				-moz-border-radius: 12px;
				border-radius: 12px;
			}
			
			#secNumber{
				color: red;
			}
			
		</style>
		<script>
			
			var gv_sec 		= 10;//set วินาทีที่่จะทำการ Redirect
			var gv_timFunc	= null;
			
			$(document).ready(function(){
				$("#secNumber").html(gv_sec);
				
				gv_timFunc = setInterval(function(){ lp_timeRedirect(); }, 1000);
			});
			
			function lp_timeRedirect(){
				try{
					if(gv_sec > 0){
						gv_sec = gv_sec - 1;
						$("#secNumber").html(gv_sec);
					}else{
						lp_goLoginPage();
					}
					
				}catch(e){
					alert("lp_timeRedirect :: " + e);
				}
			}
			
			function lp_goLoginPage(){
				try{
					clearInterval(gv_timFunc);
					window.location.replace('<%=Constants.LOGOUT_URL%>');
				}catch(e){
					alert("lp_goLoginPage :: " + e);
				}
			}
			
		</script>
	</head>
	<body>
		<form id="frm" onsubmit="return true;" >
			<div class="container" align="center">
			    <div class="row" align="center" style="width: 100%;">
			        <div class="form-login" align="center">
			         	<table width="100%" border="0">
			         		<tr>
			         			<td align="center">
			         				<img src="<%=imgURL%>/timeout.png" ><br/><br/>
			         			</td>
			         		</tr>
			         		<tr>
			         			<td align="center">
			         				กรุณา Log in ใหม่ระบบจะทำการ  Redirect page ไปยังหน้า Log in ในอีก <span id="secNumber">0</span> วินาที
			         			</td>
			         		</tr>
			         		<tr>
			         			<td align="right">
			         				<br/>
			         				<a href="javascript:void(0);" onclick="lp_goLoginPage();">กลับสู่หน้า Log in</a>
			         			</td>
			         		</tr>
			         	</table>
			        	
			        </div>
			    </div>
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