<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.ManageProductGroupBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="manageProductGroupForm" class="th.go.stock.app.enjoy.form.ManageProductGroupForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String							titlePage			= "เพิ่มหมู่สินค้า ";
	List<ManageProductGroupBean> 	productGroupList	= manageProductGroupForm.getProductGroupList();
	boolean							chk					= manageProductGroupForm.isChk();
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
			
			$('#btnSearch').click(function(){ 
				try{
					if(gp_trim($("#productTypeName").val())==""){
		            	alert("กรุณาระบุหมวดสินค้า");
		            	$("#productTypeName").focus();
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
			            			window.location.replace('<%=pagesURL%>/ManageProductGroupScn.jsp');
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
				
			$( "#productTypeName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getProductType&productTypeName=" + gp_trim(request.term),//request,
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
		    var la_productGroupCode		= null;
		    var la_productGroupName		= null;
		    
			try{
				
				la_productGroupCode = document.getElementsByName("productGroupCode");
				la_productGroupName = document.getElementsByName("productGroupName");
				
				for(var i=0;i<la_productGroupCode.length;i++){
					if(gp_trim(la_productGroupCode[i].value)==""){
		            	alert("กรุณาระบุรหัสหมู่สินค้า");
		            	la_productGroupCode[i].focus();
		                return false;
		            }
				}
				
				for(var i=0;i<la_productGroupName.length;i++){
					if(gp_trim(la_productGroupName[i].value)==""){
		            	alert("กรุณาระบุชื่อหมู่สินค้า");
		            	la_productGroupName[i].focus();
		                return false;
		            }
				}
				
				return true;
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
		}
		
		function lp_newRecord(ao_obj){
			var params							= "";
		    var lo_table                        = document.getElementById("resultData");
		    var lo_seqTemp            			= document.getElementById("seqTemp");
		    var lv_maxSeq                       = parseInt(lo_seqTemp.value) + 1;
		    var rowIndex                      	= gp_rowTableIndex(ao_obj);//lo_table.rows.length;
		    var newNodeTr 	                	= null;
		    var newNodeTd1 						= null;
		    var newNodeTd2 						= null;
		    var newNodeTd3 						= null;
		    var newNodeTd4 						= null;
			
			try{
				
				params 	= gv_service + "&pageAction=newRecord&newSeq=" + lv_maxSeq + "&productTypeCode=" + $("#productTypeCode").val();
				
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
		                        
		                        //ลำดับ
		                        newNodeTd1.align 			= "center";
		                        newNodeTd1.innerHTML        = rowIndex;
		                        
		                      	//รหัสหมู่สินค้า
		                       	newNodeTd2.innerHTML        = '<input type="text" maxlength="5" style="width: 100%;" onblur="lp_updateRecord('+lv_maxSeq+');" id="productGroupCode' + lv_maxSeq + '" name="productGroupCode" value="" />';
		                       	
		                      	//ชื่อหมู่สินค้า
		                       	newNodeTd3.innerHTML        = '<input type="text" maxlength="200" style="width: 100%" onblur="lp_updateRecord('+lv_maxSeq+');" id="productGroupName' + lv_maxSeq + '" name="productGroupName" value="" />';
		                       	
		                      	//Action
		                      	newNodeTd4.align 			= "center";
		                       	newNodeTd4.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + lv_maxSeq + '\');" />'
															+ '<input type="hidden" id="seq'+lv_maxSeq+'" name="seq" value="'+lv_maxSeq+'" />';
		                        
								lo_seqTemp.value  = lv_maxSeq;
								$('#productGroupCode' + lv_maxSeq).focus();
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
		
		function lp_updateRecord(av_val){
			var params					= "";
			var productGroupCode		= null;
			var productGroupName		= null;
			
			try{
				
				productGroupCode = document.getElementById("productGroupCode" + av_val).value;
				productGroupName = document.getElementById("productGroupName" + av_val).value;
				
				params 	= gv_service + "&pageAction=updateRecord&seq=" + av_val + "&productGroupCode=" + productGroupCode + "&productGroupName=" + productGroupName;
				
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
		<input type="hidden" id="service" 	name="service" value="servlet.ManageProductGroupServlet" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=manageProductGroupForm.getSeqTemp()%>" />
		<input type="hidden" id="productTypeCode" name="productTypeCode" value="<%=manageProductGroupForm.getProductTypeCode()%>" />
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
						        			<td align="right" width="25px;">
						        				หมวดสินค้า  : &nbsp;
						        			</td>
						        			<td align="left" width="150px;">
						        				<input type='text' 
						        					   id="productTypeName" 
						        					   name='productTypeName' 
						        					   maxlength="200" 
						        					   placeholder="หมวดสินค้า"  
						        					   onkeydown="lp_enterToSearch();"
						        					   value="<%=manageProductGroupForm.getProductTypeName()%>" 
						        					   <%if(chk==true){%> class="input-disabled" readonly<%}%> />
						        				<input type="button" id="btnSearch" class='btn btn-primary padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา' <%if(chk==true){%>disabled<%}%> />
						        			</td>
						        		</tr>
						        	</table><br/>
						        	<%if(chk==true){%>
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em">ข้อมูลหมู่สินค้า</h6>
									</div>
				         			<div class="panel-body">
				         				<table class="table sim-panel-result-table" id="resultData">
											<tr height="26px;">
												<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
												<th  style="text-align: center;" width="20%"><B>รหัสหมู่สินค้า</B></th>
												<th  style="text-align: center;" width="60%"><B>ชื่อหมู่สินค้า</B></th>
												<th style="text-align: center;" width="15%">Action</th>
											</tr> 
											<%
   											int					  	seq		= 1;
											for(ManageProductGroupBean bean:productGroupList){
												if(!bean.getRowStatus().equals(manageProductGroupForm.DEL)){
											%>
													<tr>
														<td style="text-align:center">
															<%=seq%>
														</td>
														<td align="center">
															<%=bean.getProductGroupCode()%>
															<input type="hidden" id="productGroupCode<%=bean.getSeq()%>" name="productGroupCode" value="<%=bean.getProductGroupCode()%>" />
														</td>
														<td align="left">
															<input type="text" style="width: 100%" maxlength="200" onblur="lp_updateRecord(<%=bean.getSeq()%>);" id="productGroupName<%=bean.getSeq()%>" name="productGroupName" value="<%=bean.getProductGroupName()%>" />
														</td>
														<td align="center">
															<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, '<%=bean.getSeq()%>');" />
															<input type="hidden" id="seq<%=bean.getSeq()%>" name="seq" value="<%=bean.getSeq()%>" />
														</td>
													</tr>
											<% seq++;}}%>
													<tr>
														<td colspan="3">&nbsp;</td>
														<td align="center">
															<img alt="เพิ่ม" title="เพิ่ม" src="<%=imgURL%>/Add.png" width="24" height="24" border="0" onclick="lp_newRecord(this);" />
														</td>
													</tr>
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
		<div align="center" class="FreezeScreen" style="display:none;">
        	<center>
        		<img id="imgProgress" valign="center" src="<%=imgURL%>/loading36.gif" alt="" />
        		<span style="font-weight: bold;font-size: large;color: black;">Loading...</span>
        	</center>
    	</div>
	</form>	
</body>
</html>