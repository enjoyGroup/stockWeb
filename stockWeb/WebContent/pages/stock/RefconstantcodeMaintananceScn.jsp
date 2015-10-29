<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.RefconstantcodeBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="refconstantcodeMaintananceForm" class="th.go.stock.app.enjoy.form.RefconstantcodeMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String						titlePage				= refconstantcodeMaintananceForm.getTitlePage();
	List<RefconstantcodeBean> 	refconstantcodeList 	= refconstantcodeMaintananceForm.getRefconstantcodeList();


%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title><%=titlePage%></title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<script>
		var gv_service 				= null;
		var gv_url 					= '<%=servURL%>/EnjoyGenericSrv';
		//var gv_checkFormatIdNumber 	= true;
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			gp_progressBarOff();
				
		});
			
		
		function lp_validate(){
		    var la_codeDisplay			= null;
		    
			try{
				la_codeDisplay 		= document.getElementsByName("codeDisplay");
				
				for(var i=0;i<la_codeDisplay.length;i++){
					if(la_codeDisplay[i].value.trim()==""){
						alert("กรุณาระบุรหัสเอกสาร");
						la_codeDisplay[i].focus();
						return false;
					}
				}
				
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return true;
		}
		
		
		function lp_save(){
			var params				= "";
			
			try{
				
				if(!lp_validate()){
					return;
				}
				
				params 	= "pageAction=save&" + $('#frm').serialize();
				
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
		            			alert("บันทึกเรียบร้อย");
		            			lp_reset();
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_save :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_save :: " + e);
				return;
			}
		}
		
		function lp_reset(){
			try{
				window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
			}catch(e){
				alert("lp_reset :: " + e);
			}
			
		}		

		function lp_updateRecord(av_val){
			var params					= "";
			
			try{
				
				params 	= gv_service + "&pageAction=updateRecord&id=" + av_val 
												 + "&codeDisplay=" 	+ $("#codeDisplay" + av_val).val() .trim()
												 + "&codeNameTH=" 	+ $("#codeNameTH" + av_val).val() .trim()
												 + "&codeNameEN=" 	+ $("#codeNameEN" + av_val).val() .trim();
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status!="SUCCESS"){
		            			alert(jsonObj.errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_updateRecord :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_updateRecord :: " + e);
			}
		}
		


	</script>
</head>
<body>
	<form id="frm" onsubmit="return false;">
		<input type="hidden" id="service" 	name="service" value="servlet.RefconstantcodeMaintananceServlet" />
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
								<h4 class="alert-heading">ตั้งค่าระบบ</h4>
							</div>					          	
							<div class="container main-container round-sm padding-xl-h">
								<div class="col-sm-12 toppad" >
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em"><%=titlePage%></h6>
									</div>
				         			<div class="panel-body">
									        <table class="table sim-panel-result-table" id="resultData">
												<tr height="26px;">
													<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
													<th  style="text-align: center;" width="25%"><B>รหัสเอกสาร</B></th>
													<th  style="text-align: center;" width="35%"><B>ชื่อเอกสาร(ไทย)</B></th>
													<th  style="text-align: center;" width="35%"><B>ชื่อเอกสาร(อังกฤษ)</th>
												</tr> 
												<%
 													int					  	seq		= 1;
													for(RefconstantcodeBean bean:refconstantcodeList){
														if(!bean.getRowStatus().equals(refconstantcodeMaintananceForm.DEL)){
 												%>
												<tr>
													<td style="text-align:center">
														<%=seq%>
													</td>
													<td align="left">
														<input type="text" 
															   style="width: 100%"
															   id="codeDisplay<%=bean.getId()%>" 
															   name="codeDisplay" 
															   onblur="lp_updateRecord('<%=bean.getId()%>');"
															   value="<%=bean.getCodeDisplay()%>" />
														<input type="hidden" id="id<%=bean.getId()%>" name="id" value="<%=bean.getId()%>" />
													</td>
													<td align="left">
														<input type='text' 
								        					   id="codeNameTH<%=bean.getId()%>" 
								        					   name='codeNameTH'
								        					   class="input-disabled"
															   readonly="readonly"
								        					   value="<%=bean.getCodeNameTH() %>" 
								        					   style="width: 100%" />
													</td>
													<td align="left">
														<input type='text' 
								        					   id="codeNameEN<%=bean.getId()%>" 
								        					   name='codeNameEN'
								        					   class="input-disabled"
															   readonly="readonly"
								        					   value="<%=bean.getCodeNameEN() %>" 
								        					   style="width: 100%" />
													</td>
												</tr>
												<% seq++;}}%>
											</table>
											<br/>
											<table class="table user-register-table" style="border-bottom-color: white;">
							        			<tr>
								        			<td align="right">
								        				<br/>
								        				<input type="button" id="btnSave" class="btn btn-sm btn-warning" value='บันทึก' onclick="lp_save();" />&nbsp;&nbsp;&nbsp;
				   										<input type="button" id="btnReset" onclick="lp_reset();" class="btn btn-sm btn-danger" value='เริ่มใหม่' />
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