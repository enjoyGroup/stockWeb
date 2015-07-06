<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
		<title>Chainart FC</title>
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
			    box-shadow:0 1px 0 #cfcfcf;
			}
			
			h4 { 
			 border:0 solid #fff; 
			 border-bottom-width:1px;
			 padding-bottom:10px;
			 text-align: center;
			}
			
			.form-control {
			    border-radius: 10px;
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
		</style>
		<script language="JavaScript" type="text/JavaScript">
		function lp_changeEnterToTab(e)
		{
			var keycode =(window.event) ? event.keyCode : e.keyCode;
			if(keycode == 13) {
				event.keyCode = 9;
			}
		} 
		
		function lp_changeEnterToTab_forPWD(e)
		{
			var keycode =(window.event) ? event.keyCode : e.keyCode;
			if(keycode == 13) {
				//lp_submit_login();
				$('#btnLogin').click();
			}
		} 
		
		$(document).ready(function(){
			$('#username').focus();				
			$('#btnLogin').click(function(){
				var url 	= '<%=servURL%>/EnjoyGenericSrv?service=servlet.LoginServlet';
				var userId	= null;
				var pass	= null;
				var params	= null;
				
				try{
					userId 	= $('#username').val();
					pass 	= $('#user_pwd').val();						
					if (userId == "") {
						alert("กรุณาระบุรหัสผู้ใช่ก่อนทำการเข้าสู่ระบบ");
						$('#username').focus();
						return false;
					}
					if (pass == "") {
						alert("กรุณาระบุรหัสผ่านก่อนทำการเข้าสู่ระบบ");
						$('#user_pwd').focus();
						return false;
					}
					
					params 	= "userId=" + userId + "&passWord=" + pass;
					$.ajax({
						async:false,
			            type: "POST",
			            url: url,
			            data: params,
			            beforeSend: "",
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			if (jsonObj.FlagChange == "Y"){
		            				window.location.replace('<%=pagesURL%>/ChangePassScn.jsp');
		            			} else {
		            				window.location.replace('<%=servURL%>/pages/menu/index.jsp');
		            			}
		            		}else{
		            			alert(jsonObj.errMsg);
		            		}
			            }
			        });
				}catch(err){
					alert("btnLogin :: " + err);
				}
				
			});
		});
		</script>	
	</head>
	<body>
		<div class="container">
		    <div class="row">
		        <div class="col-md-offset-5 col-md-3">
		            <div class="form-login">
		           	<img src="<%=imgURL%>/CHN300_1.png">
		            <input id="username"  name="username" type="text"  maxlength="20" class="form-control input-sm chat-input" placeholder="username"  onkeydown="lp_changeEnterToTab_forPWD();" style="margin:4px; margin-top:12px;" />
		            </br>
		            <input id="user_pwd"  name="user_pwd"  type="password"  maxlength="50" class="form-control input-sm chat-input" placeholder="password" onkeydown="lp_changeEnterToTab_forPWD();" style="margin:4px"  />
		            </br>
		            <div class="wrapper">
		            <span class="group-btn">     
		                <button type="submit" href="javascript:void(0)" id="btnLogin" class="btn btn-primary btn-md">Login &nbsp;<i class="fa fa-sign-in"></i></button>
		            </span>
		            </div>
		            </div>		        
		        </div>
		    </div>
		</div>
	</body>
</html>
