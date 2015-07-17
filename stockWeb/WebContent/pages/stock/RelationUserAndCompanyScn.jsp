<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.RelationUserAndCompanyBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="relationUserAndCompanyForm" class="th.go.stock.app.enjoy.form.RelationUserAndCompanyForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String								titlePage					= "ผูกความสัมพันธ์ผุ้ใช้กับบริษัท";
	List<RelationUserAndCompanyBean> 	relationUserAndCompanyList	= relationUserAndCompanyForm.getRelationUserAndCompanyList();
	boolean								chk							= relationUserAndCompanyForm.isChk();
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
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
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
					lo_iframe	= $("<iframe />").attr("src", "/stockWeb/EnjoyGenericSrv?service=servlet.UserDetailsLookUpServlet&pageAction=new")
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
			
			$('#btnSearch').click(function(){ 
				try{
					if(gp_trim($("#companyName").val())==""){
		            	alert("กรุณาระบุชื่อบริษัท");
		            	$("#companyName").focus();
		                return;
		            }
					
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=search&" + $('#frm').serialize(),
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
			            	
			            	try{
			            		gp_progressBarOff();
			            		
			            		jsonObj = JSON.parse(data);
			            		status	= jsonObj.status;
			            		
			            		if(status=="SUCCESS"){
			            			window.location.replace('<%=pagesURL%>/RelationUserAndCompanyScn.jsp');
			            		}else{
			            			alert(jsonObj.errMsg);
			            		}
			            	}catch(e){
			            		alert("in btnSearch :: " + e);
			            	}
			            }
			        });
				}catch(err){
					alert("btnSearch :: " + err);
				}
			});
				
			$( "#companyName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getCompanyName&companyName=" + gp_trim(request.term),//request,
		                success: function( data, textStatus, jqXHR) {
		                    var items = data; 
		                    response(items);
		                },
		                error: function(jqXHR, textStatus, errorThrown){
		                     alert( textStatus);
		                }
		            });
		          },
			      minLength: 0,//กี่ตัวอักษรถึงทำงาน
			      open: function() {
						//Data return กลับมาแล้วทำไรต่อ
			      },
			      close: function() {

			      },
			      focus:function(event,ui) {

			      },
			      select: function( event, ui ) {
			    	//เมื่อเลือก Data แล้ว
			      }
			});
			
			$( "#userFullName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getUserFullName&userFullName=" + gp_trim(request.term),//request,
		                success: function( data, textStatus, jqXHR) {
		                    var items = data; 
		                    response(items);
		                },
		                error: function(jqXHR, textStatus, errorThrown){
		                     alert( textStatus);
		                }
		            });
		          },
			      minLength: 0,//กี่ตัวอักษรถึงทำงาน
			      open: function() {
						//Data return กลับมาแล้วทำไรต่อ
			      },
			      close: function() {

			      },
			      focus:function(event,ui) {

			      },
			      select: function( event, ui ) {
			    	//เมื่อเลือก Data แล้ว
			      }
			});
				
			gp_progressBarOff();
				
		});
			
		function lp_validate(){
		    var lv_return				= true;
		    
			try{
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=validate&" + $('#frm').serialize(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			lv_return = true;
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			
		            			alert(errMsg);
	            				lv_return = false;
		            		}
		            	}catch(e){
		            		alert("in lp_validate :: " + e);
		            		lv_return = false;
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return lv_return;
		}
		
		function lp_returnData(av_userUniqueId, av_userFullName, av_userId, av_userStatus, av_userStatusName){
			
			try{
				
				lp_newRecord(av_userUniqueId, av_userFullName, av_userId, av_userStatus, av_userStatusName);
				$( "#dialog" ).dialog( "close" );
			}catch(e){
				alert("lp_returnData :: " + e);
			}
			
		}
		
		function lp_newRecord(av_userUniqueId, av_userFullName, av_userId, av_userStatus, av_userStatusName){
			var params							= "";
		    var lo_table                        = document.getElementById("resultData");
		    var lo_seqTemp            			= document.getElementById("seqTemp");
		    var lv_maxSeq                       = parseInt(lo_seqTemp.value) + 1;
		    var rowIndex                      	= lo_table.rows.length;
		    var newNodeTr 	                	= null;
		    var newNodeTd1 						= null;
		    var newNodeTd2 						= null;
		    var newNodeTd3 						= null;
		    var newNodeTd4 						= null;
		    var newNodeTd5 						= null;
			
			try{
				
				params 	= gv_service + "&pageAction=newRecord"
									 + "&newSeq=" 				+ lv_maxSeq 
									 + "&tin=" 					+ $("#tin").val()
									 + "&userUniqueId=" 		+ av_userUniqueId
									 + "&userFullName=" 		+ av_userFullName
									 + "&userId=" 				+ av_userId
									 + "&userStatus=" 			+ av_userStatus
									 + "&userStatusName=" 		+ av_userStatusName;
				
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
		            		
		            		if(status=="SUCCESS"){
		            			newNodeTr       = lo_table.insertRow(rowIndex);
		                        newNodeTd1      = newNodeTr.insertCell(0);
		                        newNodeTd2      = newNodeTr.insertCell(1);
		                        newNodeTd3      = newNodeTr.insertCell(2);
		                        newNodeTd4      = newNodeTr.insertCell(3);
		                        newNodeTd5      = newNodeTr.insertCell(4);
		                        
		                        //ลำดับ
		                        newNodeTd1.align 			= "center";
		                        newNodeTd1.innerHTML        = rowIndex;
		                        
		                      	//UserId
		                      	newNodeTd2.align 			= "center";
		                       	newNodeTd2.innerHTML        = av_userId
		                       								+ '<input type="hidden" id="userId' + lv_maxSeq + '" name="userId" value="' + av_userId + '" />'
		                       								+ '<input type="hidden" id="userUniqueId' + lv_maxSeq + '" name="userUniqueId" value="' + av_userUniqueId + '" />';
		                       	
		                      	//ชื่อ-นามสกุล
		                       	newNodeTd3.innerHTML        = av_userFullName
		                       								+ '<input type="hidden" id="userFullName' + lv_maxSeq + '" name="userFullName" value="'+av_userFullName+'" />';
		                       	
		                      	//สถานะ
		                       	newNodeTd4.innerHTML        = av_userStatusName
		                       								+ '<input type="hidden" id="userStatus' + lv_maxSeq + '" name="userStatus" value="'+av_userStatus+'" />';
		                       	
		                      	//Action
		                      	newNodeTd5.align 			= "center";
		                       	newNodeTd5.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + lv_maxSeq + '\');" />'
															+ '<input type="hidden" id="seq'+lv_maxSeq+'" name="seq" value="'+lv_maxSeq+'" />';
		                        
								lo_seqTemp.value  = lv_maxSeq;
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_newRecord :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_newRecord :: " + e);
			}
		}
		
		function lp_deleteRecord(ao_obj, av_val){
			var params							= "";
		    var lo_table                        = document.getElementById("resultData");
			
			try{
				
				params 	= gv_service + "&pageAction=deleteRecord&seq=" + av_val;
				
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
		            		
		            		if(status=="SUCCESS"){
		            			lo_table.deleteRow(gp_rowTableIndex(ao_obj)); 
		            			
		            			for(var i=1;i<(lo_table.rows.length - 1);i++){
		                            lo_table.rows[i].cells[0].innerHTML = (i);
		                        }
		            			
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_deleteRecord :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_deleteRecord :: " + e);
			}
		}
		
		function lp_reset(){
			try{
				window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
			}catch(e){
				alert("lp_reset :: " + e);
			}
			
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
			}
		}
		
		function lp_enterToSearch(e)
		{
			var keycode =(window.event) ? event.keyCode : e.keyCode;
			if(keycode == 13) {
				//lp_submit_login();
				$('#btnSearch').click();
			}
		} 
		
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.RelationUserAndCompanyServlet" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=relationUserAndCompanyForm.getSeqTemp()%>" />
		<input type="hidden" id="tin" name="tin" value="<%=relationUserAndCompanyForm.getTin()%>" />
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
								<h4 class="alert-heading"><%=titlePage%></h4>
							</div>					          	
							<div class="container main-container round-sm padding-xl-h">
								<div class="col-sm-12 toppad" >
									<table class="user-register-table user-search-table" width="100%" border="0" cellpadding="5" cellspacing="5">
						        		<tr>
						        			<td align="right" width="150px;">
						        				ชื่อบริษัท <span style="color: red;"><b>*</b></span> : &nbsp;
						        			</td>
						        			<td align="left" width="350px;">
						        				<input type='text' 
						        					   id="companyName" 
						        					   name='companyName' 
						        					   maxlength="100" 
						        					   placeholder="ชื่อบริษัท"  
						        					   value="<%=relationUserAndCompanyForm.getCompanyName()%>" 
						        					   <%if(chk==true){%> class="input-disabled" readonly<%}%> />
						        			</td>
						        			<td align="right" width="150px;">
						        				ชื่อพนักงาน  : &nbsp;
						        			</td>
						        			<td align="left" width="350px;">
						        				<input type='text' 
						        					   id="userFullName" 
						        					   name='userFullName' 
						        					   maxlength="151" 
						        					   placeholder="ชื่อพนักงาน"  
						        					   value="<%=relationUserAndCompanyForm.getUserFullName()%>" 
						        					   <%if(chk==true){%> class="input-disabled" readonly<%}%> />
						        			</td>
						        		</tr>
						        		<tr>
						        			<td align="right" colspan="4">
						        				<input type="button" id="btnSearch" class='btn btn-primary padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา' <%if(chk==true){%>disabled<%}%> />
						        			</td>
						        		</tr>
						        	</table><br/>
						        	<%if(chk==true){%>
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em">ข้อมูลผูกความสัมพันธ์ผุ้ใช้กับบริษัท</h6>
									</div>
				         			<div class="panel-body">
				         				<table border="0" cellpadding="3" cellspacing="0" width="100%">
											<tr>
												<td align="left">
													<input type="button" id="btnZoom" class="btn btn-sm btn-warning" value='เลือกผู้ใช้งานระบบ' />
												</td>
											</tr>
										</table>
				         				<table class="table sim-panel-result-table" id="resultData">
											<tr height="26px;">
												<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
												<th  style="text-align: center;" width="20%"><B>รหัสผู้ใช้งาน</B></th>
												<th  style="text-align: center;" width="40%"><B>ชื่อ-นามสกุล</B></th>
												<th  style="text-align: center;" width="20%"><B>สถานะ</B></th>
												<th style="text-align: center;" width="15%">Action</th>
											</tr> 
											<%
   											int					  	seq		= 1;
											for(RelationUserAndCompanyBean bean:relationUserAndCompanyList){
												if(!bean.getRowStatus().equals(relationUserAndCompanyForm.DEL)){
											%>
													<tr>
														<td style="text-align:center">
															<%=seq%>
														</td>
														<td align="center">
															<%=bean.getUserId()%>
															<input type="hidden" id="userId<%=bean.getSeq()%>" name="userId" value="<%=bean.getUserId()%>" />
															<input type="hidden" id="userUniqueId<%=bean.getSeq()%>" name="userUniqueId" value="<%=bean.getUserUniqueId()%>" />
														</td>
														<td align="left">
															<%=bean.getUserFullName()%>
															<input type="hidden" id="userFullName<%=bean.getSeq()%>" name="userFullName" value="<%=bean.getUserFullName()%>" />
														</td>
														<td align="left">
															<%=bean.getUserStatusName()%>
															<input type="hidden" id="userStatus<%=bean.getSeq()%>" name="userStatus" value="<%=bean.getUserStatus()%>" />
														</td>
														<td align="center">
															<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, '<%=bean.getSeq()%>');" />
															<input type="hidden" id="seq<%=bean.getSeq()%>" name="seq" value="<%=bean.getSeq()%>" />
														</td>
													</tr>
											<% seq++;}}%>
										</table>
										<br/>
										<table border="0" cellpadding="3" cellspacing="0" width="100%">
											<tr>
												<td align="right">
													<input type="button" id="btnSave" class="btn btn-sm btn-warning" value='บันทึก' onclick="lp_save();" />&nbsp;&nbsp;&nbsp;
				   									<input type="button" id="btnReset" onclick="lp_reset();" class="btn btn-sm btn-danger" value='เริ่มใหม่' />
												</td>
											</tr>
										</table>
									</div>
									<%}%>
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