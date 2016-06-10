<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.UserDetailsBean,th.go.stock.app.enjoy.bean.ComboBean,th.go.stock.app.enjoy.model.Userprivilege"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="userDetailsMaintananceForm" class="th.go.stock.app.enjoy.form.UserDetailsMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String 					pageMode 			= userDetailsMaintananceForm.getPageMode();
	UserDetailsBean 		userDetailsBean 	= userDetailsMaintananceForm.getUserDetailsBean();
	List<ComboBean> 		refuserstatusCombo 	= userDetailsMaintananceForm.getStatusCombo();
	List<Userprivilege> 	userprivilegeList	= userDetailsMaintananceForm.getUserprivilegeList();
	List<ComboBean> 		companyCombo 		= userDetailsMaintananceForm.getCompanyCombo();
	int 					couChkRow			= 0;
	String					titlePage			= userDetailsMaintananceForm.getTitlePage();

%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title><%=titlePage%></title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<script>
		var gv_service 			= null;
		var gv_url 				= '<%=servURL%>/EnjoyGenericSrv';
		var gv_checkDupUserId 	= false;
		
		$(document).ready(function(){
			//gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			$("#circlePic").corner("80px");
			
			if($("#pageMode").val()=="EDIT"){
				lp_setModeEdit();
			}else{
				$("#userStatusDis").prop('disabled', true);
			}
			
			lp_ctrlCommission();
			
			//gp_progressBarOff();
			
		});
		
		function lp_validate(){
		    var la_validate             = new Array( "userName:ชื่อ"	
													, "userSurname:นามสกุล"
													, "userId:User ID"
													, "userEmail:E-mail");
		    
		    var lo_flagSalesman 		= null;
			var lo_commission 			= null;
		    
			try{
				lo_flagSalesman 	= document.getElementById("flagSalesman");
				lo_commission 		= document.getElementById("commission");
				
				if(!gp_validateEmptyObj(la_validate)){
					return false;
				}
				
				if(gv_checkDupUserId==false){
					alert("มี userid นี้ในระบบแล้ว", function() { 
						$("#userId").focus();
	    		    });
					//alert("มี userid นี้ในระบบแล้ว");
					//$("#userId").focus();
					return false;
				}
				
				if(lo_flagSalesman.checked==true && lo_commission.value.trim()==""){
					alert("กรุณาระบุค่าคอม");
					return false;
				}
				
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return true;
		}
		
		function lp_checkDupUserId(){
			
			var lv_userId 	= null;
			var params		= "";
			
			try{
				lv_userId = gp_trim($("#userId").val());
				
				$("#inValidSpan").html("");
				
				if(lv_userId==""){
					return;
				}
				
				$("#userId").val(lv_userId);
				
				params 	= "pageAction=checkDupUserId&" + $('#frm').serialize();
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var cou					= 0;
		            	
		            	try{
		            		gp_progressBarOff();
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			cou = parseInt(jsonObj.COU);
		            			
		            			if(cou > 0){
		            				$("#inValidSpan").css("color", "red");
		            				$("#inValidSpan").html("มี userid นี้ในระบบแล้ว");
		            				
		            				gv_checkDupUserId = false;
		            				
		            			}else{
		            				$("#inValidSpan").css("color", "green");
		            				$("#inValidSpan").html("userid นี้ใช้งานได้");
		            				
		            				gv_checkDupUserId = true;
		            			}
		            			
		            		}else{
		            			alert(jsonObj.errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_checkDupUserId :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_checkDupUserId :: " + e);
			}
		}
		
		function lp_setModeEdit(){
			
			var la_chkUserPrivilege = null;
			var la_userPrivilegeDb	= null;
			
			try{
				la_chkUserPrivilege = document.getElementsByName("chkUserPrivilege");
				lo_hidUserPrivilege = document.getElementsByName("hidUserPrivilege");
				
				if(gp_trim($("#hidUserPrivilege").val())!=""){
					la_userPrivilegeDb = gp_trim($("#hidUserPrivilege").val()).split(",");
					
					for(var i=0;i<la_userPrivilegeDb.length;i++){
						for(var j=0;j<la_chkUserPrivilege.length;j++){
							if(la_chkUserPrivilege[j].value==la_userPrivilegeDb[i]){
								la_chkUserPrivilege[j].checked = true;
								break;
							}
						}
					}
					
				}
				
				lp_checkDupUserId();
				
			}catch(e){
				alert("lp_setModeEdit :: " + e);
			}
			
		}
		
		function lp_save(){
			
			try{
				
				if($('input[name="chkUserPrivilege"]:checked').length<=0){
                    alert("กรุณาเลือกสิทธิ์การใช้ระบบอย่างน้อย 1 รายการ");
                    return;
                }
				
				if(!lp_validate()){
					return;
				}
				
				if($("#pageMode").val()=="NEW"){
					
					confirm("Password จะถูกส่งไปที่ E-mail ที่คุณกรอก คุณกรอก E-mail ถูกต้องแล้วใช่หรือไม่ ?", function(){
						onSave();
					},function(){
						$('#userEmail').focus();
						return;
					});
				}else if ($("#pageMode").val()=="EDIT"){
					onSave();
				}
				
			}catch(e){
				alert("lp_save :: " + e);
			}
		}
		
		function onSave(){
			var params 				= "";
			var la_chkUserPrivilege = null;
			var lv_userPrivilege	= "";
			
			try{
				la_chkUserPrivilege = document.getElementsByName("chkUserPrivilege");
				
				for(var i=0;i<la_chkUserPrivilege.length;i++){
					
					if(la_chkUserPrivilege[i].checked==true){
						
						if(lv_userPrivilege==""){
							lv_userPrivilege = la_chkUserPrivilege[i].value;
						}else{
							lv_userPrivilege = lv_userPrivilege + "," + la_chkUserPrivilege[i].value;
						}
						
					}
					
				}
				
				$("#hidUserPrivilege").val(lv_userPrivilege);
				
				params 				= "pageAction=save&" + $('#frm').serialize();
				
				$.ajax({
					async:true,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var userUniqueId		= 0;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			userUniqueId = jsonObj.userUniqueId;
		            			alert("บันทึกเรียบร้อย", function() { 
		            				lp_reset();
		    	    		    });
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in onSave :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("onSave :: " + e);
			}
		}
		
		function lp_reset(){
			try{
				if($("#pageMode").val()=="NEW"){
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
				}else{
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getUserDetail&userUniqueId=" + $('#userUniqueId').val();
				}
			}catch(e){
				alert("lp_reset :: " + e);
			}
			
		}
		
		function lp_resetPass(){
			
			var params				= "";
			
			try{
				if(!confirm("Password จะถูกส่งไปที่ E-mail ที่คุณกรอก คุณกรอก E-mail ถูกต้องแล้วใช่หรือไม่ ?")){
					$('#userEmail').focus();
					return;
				}
				
				params 	= "pageAction=resetPass&" + $('#frm').serialize();
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	
		            	try{
		            		gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			alert("Password ถูกส่งไปที่ E-mail แล้ว");
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_resetPass :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_resetPass :: " + e);
			}
		}
		
		function lp_ctrlCommission(){
			
			var lo_flagSalesman 	= null;
			var lo_commission 		= null;
			
			try{
				lo_flagSalesman 	= document.getElementById("flagSalesman");
				lo_commission 		= document.getElementById("commission");
				
				if(lo_flagSalesman.checked==true){
					lo_commission.readOnly 		= false;
					lo_commission.className 	= "";
				}else{
					lo_commission.value			= "";
					lo_commission.readOnly 		= true;
					lo_commission.className 	= "input-disabled";
				}
				
			}catch(e){
				alert("lp_ctrlCommission :: " + e);
			}
		}
		
		function lp_setUserStatus(){
			try{
				$("#userStatus").val($("#userStatusDis").val());
			}catch(e){
				alert("lp_setUserStatus :: " + e);
			}
		}
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.UserDetailsMaintananceServlet" />
		<input type="hidden" id="hidUserPrivilege" 	name="hidUserPrivilege" value="<%=userDetailsBean.getUserPrivilege()%>" />
		<input type="hidden" id="pageMode" 	name="pageMode" value="<%=pageMode%>" />
		<input type="hidden" id="userUniqueId" 	name="userUniqueId" value="<%=userDetailsBean.getUserUniqueId()%>" />
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<section class="vbox">
		<section>
		<section class="hbox stretch">
		<section id="content">
		<section class="vbox">
		<section class="scrollable padder">
		<div class="alert alert-block alert-error fade in container">
			<h4 class="alert-heading">ผู้ดูแลระบบ/จัดการผู้ใช้งาน</h4>
		</div>					          	
		<div class="container main-container round-sm padding-xl-h">
				<div class="col-sm-12 toppad" >
						<div id="seasonTitle" class="padding-md round-sm season-title-head" style="">
							<h6 class="panel-title" style="font-size:1.0em"><%=titlePage%></h6>
						</div>
           				<div class="panel-body">
           					<table border="0" width="100%">
           						<tr>
           							<td align="center" width="30%" valign="top">
           								<div style="width: 200px;height: 200px;border:4px solid #ccc;overflow: hidden;" id="circlePic">
           									<img src="<%=imgURL%>/add_user.png" width="200px" height="200px" border="0" />
           								</div>
           							</td>
           							<td align="left" width="70%">
           								<div class=" col-md-8 line-left"> 
											<table class="table user-register-table" style="border-bottom-color: white;">
												<tr>
									        		<td align="right" width="30px">
														ชื่อ <span style="color: red;"><b>*</b></span> : &nbsp;
													</td>
								        			<td align="left" width="200px">
								        				<input type='text' id="userName" name='userName' value="<%=userDetailsBean.getUserName() %>" maxlength="50" style="width: 250px;" />
								        			</td>
									        	</tr>
									        	<tr>
								        			<td align="right">
								        				นามสกุล <span style="color: red;"><b>*</b></span> : &nbsp;
								        			</td>
								        			<td align="left">
								        				<input type='text' id="userSurname" name='userSurname' value="<%=userDetailsBean.getUserSurname() %>" maxlength="100" style="width: 250px;" />
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				User ID <span style="color: red;"><b>*</b></span> : &nbsp;
								        			</td>
								        			<td align="left">
								        				<input type='text' id="userId" name='userId' value="<%=userDetailsBean.getUserId() %>" onchange="lp_checkDupUserId();" maxlength="20" style="width: 250px;" />
								        				&nbsp;
								        				<span id="inValidSpan"></span>
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				E-mail <span style="color: red;"><b>*</b></span> : &nbsp;
								        			</td>
								        			<td align="left">
								        				<input type="text" id="userEmail" name="userEmail" value="<%=userDetailsBean.getUserEmail()%>" maxlength="100" style="width: 250px;" />
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				สถานะ <span style="color: red;"><b>*</b></span> :&nbsp;
								        			</td>
								        			<td align="left">
								        				<select id="userStatusDis" name="userStatusDis" style="width: 250px;" onchange="lp_setUserStatus();">
								        					<% for(ComboBean comboBean:refuserstatusCombo){ %>
								        					<option value="<%=comboBean.getCode()%>" <%if(userDetailsBean.getUserStatus().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
								        					<%} %>
								        				</select>
								        				<input type="hidden" id="userStatus" name="userStatus" value="<%=userDetailsBean.getUserStatus()%>" />
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				หมายเหตุ :&nbsp;
								        			</td>
								        			<td align="left">
								        				<textarea rows="3" style="width: 100%;" id="remark" name="remark"><%=userDetailsBean.getRemark() %></textarea>
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				<input type="checkbox" id="flagSalesman" name="flagSalesman" onclick="lp_ctrlCommission();" value="Y" <%if(userDetailsBean.getFlagSalesman().equals("Y")){ %> checked="checked" <%} %> /> :&nbsp;
								        			</td>
								        			<td align="left" valign="middle">
								        				<span>
								        					เป็นพนักงานขาย &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ค่าคอม&nbsp;:&nbsp;
								        					<input  type="text" 
									        						id="commission" 
									        						name="commission" 
									        						class="moneyOnly"
									        						style="width: 80px;"
									        						value="<%=userDetailsBean.getCommission()%>" />
									        				&nbsp;%
								        				</span>
								        				
								        			</td>
									        	</tr>
								        		<tr>
								        			<td align="right">
								        				<input type="checkbox" id="flagChangePassword" name="flagChangePassword" value="Y" <%if(userDetailsBean.getFlagChangePassword().equals("Y")){ %> checked="checked" <%} %> /> :&nbsp;
								        			</td>
								        			<td align="left">
								        				ต้องการเปลี่ยนรหัสผ่านเมื่อ Login ครั้งแรก
								        			</td>
									        	</tr>
									        	<tr>
								        			<td align="right">
								        				<input type="checkbox" id="flagAlertStock" name="flagAlertStock" value="Y" <%if(userDetailsBean.getFlagAlertStock().equals("Y")){ %> checked="checked" <%} %> /> :&nbsp;
								        			</td>
								        			<td align="left">
								        				ต้องการรับ E-mail แจ้งเตือนเมื่อสินค้าลดเหลือถึงกำหนด
								        			</td>
									        	</tr>
									        </table>
									        <table class="user-register-table" style="margin-left:40px" width="80%" border="0" cellpadding="5" cellspacing="5">
								        		<tr>
								        			<td align="left" colspan="4">
								        				สิทธิ์การใช้ระบบ
								        			</td>
								        		</tr>
									        		
									        		<%
									        			for(Userprivilege beanUserprivilege:userprivilegeList){
									        		%>
									        		<%if(couChkRow==0){ %><tr><%} %>
									        			<td align="right">
									        				<input type="checkbox" id="chkUserPrivilege<%=beanUserprivilege.getPrivilegeCode() %>" name="chkUserPrivilege" value="<%=beanUserprivilege.getPrivilegeCode() %>" /> :&nbsp;
									        			</td>
									        			<td align="left">
									        				<%=beanUserprivilege.getPrivilegeName() %>
									        			</td>
									        		<%if(couChkRow==1){ %>
									        		</tr>
									        		<%}
									        			if(couChkRow==1){
									        				couChkRow=0;
									        			}else{
									        				couChkRow++;
									        			}
									        			
									        		} %>
											</table>	
											<br/>    
											<div align="right" style="width: 100%;">
												<span>
							        				<input type="button" id="btnSave" class="btn btn-sm btn-warning" value='บันทึก' onclick="lp_save();" />&nbsp;&nbsp;&nbsp;
							        				
							        				<%if(pageMode.equals(userDetailsMaintananceForm.EDIT)){%>
							        				<input type="button" id="btnResetPass" class="btn btn-sm btn-warning" value='Reset Password' onclick="lp_resetPass();" />&nbsp;&nbsp;&nbsp;
							        				<%}%>
							        				
			     									<input type="button" id="btnReset" onclick="lp_reset();" class="btn btn-sm btn-danger" value='เริ่มใหม่' />
			     								</span>
											</div>          
										</div>
           							</td>
           						</tr>
           					</table>
               						
						</div>
		            </div>          
				</div>
						    
				</section>
		</section>
		</section>
			<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
		</section>
		</section>
		</section>
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