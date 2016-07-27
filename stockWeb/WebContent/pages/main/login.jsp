<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
		<title>Stock</title>
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
		</style>
		<script language="JavaScript" type="text/JavaScript">
			var gv_service 				= null;
			var gv_url 					= '<%=servURL%>/EnjoyGenericSrv';
		
			$(document).ready(function(){
				
				gv_service 		= "service=" + $('#service').val();
				
			    if (gp_getCookie("userEmail") != "") {
			    	$('#userEmail').val(gp_getCookie("userEmail"));
			    	$('#user_pwd').focus();
			    }else{
			    	$('#userEmail').focus();
			    }
				
				$(".form-login").corner();
				
				$('#btnLogin').click(function(){
					var userEmail	= null;
					var pass		= null;
					
					try{
						userEmail 	= $('#userEmail').val();
						pass 		= $('#user_pwd').val();						
						if (userEmail == "") {
							alert("กรุณาระบุE-mailก่อนทำการเข้าสู่ระบบ", function() { 
								$('#userEmail').focus();
			    		    });
							return false;
						}
						if (pass == "") {
							alert("กรุณาระบุรหัสผ่านก่อนทำการเข้าสู่ระบบ", function() { 
								$('#user_pwd').focus();
			    		    });
							return false;
						}
						
						$.ajax({
							async:true,
				            type: "POST",
				            url: gv_url,
				            data: gv_service + "&pageAction=login&" + $('#frm').serialize(),
				            beforeSend: gp_progressBarOn(),
				            success: function(data){
				            	var jsonObj 			= null;
				            	var status				= null;
				            	var countUserIncompany	= 0;
				            	
				            	try{
				            		jsonObj = JSON.parse(data);
				            		status	= jsonObj.status;
				            		
				            		if(status=="SUCCESS"){
				            			
				            			gp_setCookie("userEmail", userEmail, 3);
				            			
				            			if (jsonObj.flagChkCompany == "Y"){
				            				window.location.replace('<%=servURL%>/EnjoyGenericSrv?service=servlet.CompanyDetailsMaintananceServlet&pageAction=new');
				            			}else if (jsonObj.FlagChange == "Y"){
				            				window.location.replace('<%=pagesURL%>/ChangePassScn.jsp');
				            			} else {
				            				countUserIncompany = parseInt(jsonObj.countUserIncompany);
				            				
				            				if(countUserIncompany==1){
				            					window.location.replace('<%=servURL%>/pages/menu/index.jsp');
				            				}else{
				            					lp_chooseCompany(jsonObj.companyObjList);
				            				}
				            			}
				            		}else{
				            			alert(jsonObj.errMsg);
				            		}
				            	}catch(e){
				            		alert("in btnLogin :: " + e);
				            	}
				            }
				        });
					}catch(err){
						alert("btnLogin :: " + err);
					}
					
				});
				
				$('#btnSubmit').live("click", function(){
					try{
						
						if($("#tin").val()==""){
							$("#tin").focus();
							return false;
						}
						
						$.ajax({
							async:true,
				            type: "POST",
				            url: gv_url,
				            data: gv_service + "&pageAction=setTinForTinUser&tin=" + $('#tin').val(),
				            beforeSend: gp_progressBarOn(),
				            success: function(data){
				            	var jsonObj 			= null;
				            	var status				= null;
				            	
				            	try{
				            		gp_progressBarOff();
				            		
				            		jsonObj = JSON.parse(data);
				            		status	= jsonObj.status;
				            		
				            		if(status=="SUCCESS"){
				            			window.location.replace('<%=servURL%>/pages/menu/index.jsp');
				            		}else{
				            			alert(jsonObj.errMsg);
				            		}
				            		$( "#dialog" ).dialog( "close" );
				            	}catch(e){
				            		alert("in btnSubmit :: " + e);
				            	}
				            }
				        });
					}catch(err){
						alert("btnSubmit :: " + err);
					}
				});
				
				$('#btnCancel').live("click", function(){
					try{
						$( "#dialog" ).dialog( "close" );
					}catch(err){
						alert("btnSubmit :: " + err);
					}
				});
				
			});
			
			function lp_changeEnterToTab_forPWD(e){
				var keycode =(window.event) ? event.keyCode : e.keyCode;//alert(keycode);
				if(keycode == 13) {
					$('#btnLogin').click();
				}
			} 
			
			function lp_chooseCompany(av_companyObjList){
				var lv_html = "";
				var lv_select = "";
				
				try{
					lv_select += '<select id="tin" name="tin" style="width:80%">';
					lv_select += '<option value="">กรุณาระบุ</option>';
					$.each(av_companyObjList, function(idx, obj) {
						lv_select+= '<option value="' + obj.code + '">' + obj.desc + '</option>';
					});
					lv_select += '</select>';
					
					lv_html = '<table border="0" width="100%">'
							 + '	<tr>'
							 + '		<td align="center">' + lv_select + '</td>'
							 + '	</tr>'
							 + '	<tr><td style="height:40px;"></td></tr>'
							 + '	<tr>'
							 + '		<td align="center" colspan="2">'
							 + '			<input type="button" id="btnSubmit" class="btn btn-primary btn-md" name="btnSubmit" value="ตกลง" />'
							 + '			<input type="button" id="btnCancel" class="btn btn-primary btn-md" name="btnCancel" value="ยกเลิก" />'
							 + '		</td>'
							 + '	</tr>'
							 + '</table>';
					
					gp_dialogPopUpHtml(lv_html, "กรุณาระบุบริษัทที่ต้องการเข้าใช้งานระบบ",350, 200);
				}catch(e){
					alert("lp_chooseCompany :: " + e);
				}
			}
		
		</script>	
	</head>
	<body>
		<form id="frm" onsubmit="return true;" >
			<input type="hidden" id="service" 	name="service" value="servlet.LoginServlet" />
			<div class="container" align="center">
			    <div class="row" align="center" style="width: 100%;">
			        <div class="form-login">
			           	<img src="<%=imgURL%>/logo-login.png"><br/><br/>
			            <input id="userEmail"  
			            	   name="userEmail" 
			            	   type="text"  
			            	   maxlength="100" 
			            	   placeholder="E-mail"  
			            	   onkeypress="return lp_changeEnterToTab_forPWD(event);" 
			            	   style="width: 100%;" />
			            <br/><br/>
			            <input id="user_pwd"  
			            	   name="user_pwd"  
			            	   type="password"  
			            	   maxlength="50" 
			            	   placeholder="password" 
			            	   onkeypress="return lp_changeEnterToTab_forPWD(event);" 
			            	   style="width: 100%;"  />
			            <br/>
			            <div class="wrapper">
				            <span class="group-btn">     
				                <button type="button" href="javascript:void(0);" id="btnLogin" class="btn btn-primary btn-md">Login &nbsp;<i class="fa fa-sign-in"></i></button>
				            </span>
			            </div>
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
