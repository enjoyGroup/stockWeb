<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.ComparePriceBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="comparePriceForm" class="th.go.stock.app.enjoy.form.ComparePriceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String					titlePage			= "เปรียบเทียบราคา";
	List<ComparePriceBean> 	comparePriceList	= comparePriceForm.getComparePriceList();
	boolean					chk					= comparePriceForm.isChk();
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
			
			$("input[name=vendorName]").live("focus",function(){
				
				var lv_seq	= $(this).attr('id').substr($(this).attr('name').length,  $(this).attr('id').length);
				
				$(this).autocomplete({
					 
					 source: function(request, response) {
			           $.ajax({
			           		async:false,
				            type: "POST",
			               	url: gv_url,
			               	dataType: "json",
			                data: gv_service + "&pageAction=getVendorNameList&vendorName=" + gp_trim(request.term),//request,
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
				    	  lp_getCompanyVendorDetail(lv_seq, 'V');
				      }
				  });
				
			});
			
			$("input[name=branchName]").live("focus",function(){
				
				var lv_seq	= $(this).attr('id').substr($(this).attr('name').length,  $(this).attr('id').length);
				
				$(this).autocomplete({
					 
					 source: function(request, response) {
			           $.ajax({
			           		async:false,
				            type: "POST",
			               	url: gv_url,
			               	dataType: "json",
			                data: gv_service + "&pageAction=branchNameList&vendorName="+$("#vendorName" + lv_seq).val().trim()+"&branchName=" + request.term.trim(),//request,
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
				    	  lp_getCompanyVendorDetail(lv_seq, 'B');
				      }
				  });
				
			});
			
			$( "#productName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getProductNameList&productName=" + gp_trim(request.term),//request,
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
			    	  lp_getProductDetailByName();
			      }
			});
			
			$('#btnSearch').click(function(){ 
				try{
					if($("#productName").val().trim()==""){
		            	alert("กรุณาระบุสินค้า");
		            	$("#productName").focus();
		                return;
		            }
					
					if($("#productCode").val().trim()==""){
		            	alert("ระบุชื่อสินค้าผิด");
		            	$("#productName").focus();
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
			            			window.location.replace('<%=pagesURL%>/ComparePriceScn.jsp');
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
				
			gp_progressBarOff();
				
		});
		
		function lp_getProductDetailByName(){
			try{
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getProductDetailByName&productName=" + $("#productName").val().trim(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			$("#productCode").val(jsonObj.productCode);
		            			//$("#productName" + av_seq).val(jsonObj.productName);
		            			
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_getProductDetailByName :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_getProductDetailByName :: " + e);
			}
		}
		
		function lp_getCompanyVendorDetail(av_seq, av_flag){
			
			var vendorName = "";
			var branchName = "";
			
			try{
				
				vendorName = $("#vendorName" + av_seq).val().trim();
				branchName = $("#branchName" + av_seq).val().trim();
				
				//alert(vendorName + " :: " + branchName);
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getCompanyVendorDetail&vendorName=" + vendorName + "&branchName=" + branchName,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			$("#vendorCode"+ av_seq).val(jsonObj.vendorCode);
		            			
		            			if(av_flag=="V"){
		            				$("#branchName"+ av_seq).val(jsonObj.branchName);
		            			}
		            			
		            			lp_updateRecord(av_seq);
		            			
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_getCompanyVendorDetail :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_getCompanyVendorDetail :: " + e);
			}
		}
			
		function lp_validate(){
		    var la_vendorName			= null;
		    var	la_branchName			= null;
		    var	la_vendorCode			= null;
		    
			try{
				la_vendorName = document.getElementsByName("vendorName");
				la_branchName = document.getElementsByName("branchName");
				la_vendorCode = document.getElementsByName("vendorCode");
				
				for(var i=0;i < la_vendorName.length;i++){
					
					if(la_vendorName[i].value.trim()==""){
						alert("กรุณาระบุบริษัท");
						la_vendorName[i].focus();
						return false;
					}
					
					if(la_branchName[i].value.trim()==""){
						alert("กรุณาระบุสาขา");
						la_branchName[i].focus();
						return false;
					}
					
					if(la_vendorCode[i].value.trim()==""){
						alert("ระบุบริษัทหรือสาขาผิด");
						la_vendorCode[i].focus();
						return false;
					}
				}
				
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return true;
		}
		
		function lp_newRecord(){
			var params							= "";
		    var lo_table                        = document.getElementById("resultData");
		    var lo_seqTemp            			= document.getElementById("seqTemp");
		    var lv_maxSeq                       = parseInt(lo_seqTemp.value) + 1;
		    var rowIndex                      	= gp_rowTableIndex(document.getElementById("btnAdd"));
		    //var rowIndex                      	= lo_table.rows.length;
		    var newNodeTr 	                	= null;
		    var newNodeTd1 						= null;
		    var newNodeTd2 						= null;
		    var newNodeTd3 						= null;
		    var newNodeTd4 						= null;
		    var newNodeTd5 						= null;
		    var newNodeTd6 						= null;
			
			try{
				
				params 	= gv_service + "&pageAction=newRecord"
									 + "&newSeq=" 				+ lv_maxSeq;
				
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
		                        newNodeTd6      = newNodeTr.insertCell(5);
		                        
		                        //ลำดับ
		                        newNodeTd1.align 			= "center";
		                        newNodeTd1.innerHTML        = rowIndex;
		                        
		                      	//บริษัท
		                      	newNodeTd2.align 			= "left";
		                       	newNodeTd2.innerHTML        = '<input type="text" id="vendorName'+lv_maxSeq+'" name="vendorName" value="" maxlength="100" onblur="lp_getCompanyVendorDetail('+lv_maxSeq+', \'V\');" style="width: 100%;" />'
		                       								+ '<input type="hidden" id="vendorCode'+lv_maxSeq+'" name="vendorCode" value="" />';
		                       	
		                      	//สาขา
		                      	newNodeTd3.align 			= "left";
		                       	newNodeTd3.innerHTML        = '<input type="text" id="branchName'+lv_maxSeq+'" name="branchName" value="" maxlength="30" onblur="lp_getCompanyVendorDetail('+lv_maxSeq+', \'B\');" style="width: 100%;" />';
		                       	
		                      	//ปริมาณ
		                      	newNodeTd4.align 			= "left";
		                       	newNodeTd4.innerHTML        = '<input type="text" id="quantity'+lv_maxSeq+'" name="quantity" class="moneyOnly" onblur="gp_checkAmtOnly(this, 12);lp_updateRecord('+lv_maxSeq+');" value="0.00" style="width: 100%" />';
		                       	
		                      	//ราคาที่ซื้อ
		                      	newNodeTd5.align 			= "left";
		                       	newNodeTd5.innerHTML        = '<input type="text" id="price'+lv_maxSeq+'" name="price" class="moneyOnly" onblur="gp_checkAmtOnly(this, 12);lp_updateRecord('+lv_maxSeq+');" value="0.00" style="width: 100%" />';
		                       	
		                      	//Action
		                      	newNodeTd6.align 			= "center";
		                       	newNodeTd6.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + lv_maxSeq + '\');" />'
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
		
		function lp_updateRecord(av_val){
			var params					= "";
			
			try{
				
				params 	= gv_service + "&pageAction=updateRecord&seq=" + av_val 
															 + "&productCode=" 	+ $("#productCode").val().trim()
															 + "&vendorCode=" 	+ $("#vendorCode" + av_val).val() .trim()
															 + "&vendorName=" 	+ $("#vendorName" + av_val).val() .trim()
															 + "&branchName=" 	+ $("#branchName" + av_val).val() .trim()
															 + "&quantity=" 	+ $("#quantity" + av_val).val() .trim()
															 + "&price=" 		+ $("#price" + av_val).val() .trim();
				
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
				if($("#pageAction").val()=="lookup"){
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=lookup&productCode=" + $('#productCode').val() + "&productName=" + $("#productName").val();
				}else{
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
				}
				
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
				lp_getProductDetailByName();
				$('#btnSearch').click();
			}
		} 
		
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.ComparePriceServlet" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=comparePriceForm.getSeqTemp()%>" />
		<input type="hidden" id="productCode" name="productCode" value="<%=comparePriceForm.getProductCode()%>" />
		<input type="hidden" id="pageAction" name="pageAction" value="<%=comparePriceForm.getPageAction()%>" />
		<%if(!comparePriceForm.getPageAction().equals("lookup")){ %>
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<%}%>
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
						        			<td align="right" width="100px;">
						        				สินค้า <span style="color: red;"><b>*</b></span> : &nbsp;
						        			</td>
						        			<td align="left">
						        				<input type='text' 
						        					   id="productName" 
						        					   name='productName' 
						        					   maxlength="255" 
						        					   <%if(chk==false){%> onblur="lp_getProductDetailByName();"<%}%>
						        					   <%if(chk==false){%> onkeypress="return lp_enterToSearch(event);"<%}%>
						        					   placeholder="สินค้า"  
						        					   value="<%=comparePriceForm.getProductName()%>" 
						        					   <%if(chk==true){%> class="input-disabled" readonly<%}%> />
						        				<input type="button" id="btnSearch" class='btn btn-primary padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา' <%if(chk==true){%>disabled<%}%> />
						        			</td>
						        		</tr>
						        	</table><br/>
						        	<%if(chk==true){%>
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em">ข้อมูลเปรียบเทียบราคา</h6>
									</div>
				         			<div class="panel-body">
				         				<table class="table sim-panel-result-table" id="resultData">
											<tr height="26px;">
												<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
												<th  style="text-align: center;" width="20%"><B>บริษัท</B></th>
												<th  style="text-align: center;" width="20%"><B>สาขา</B></th>
												<th  style="text-align: center;" width="20%"><B>ปริมาณ</B></th>
												<th  style="text-align: center;" width="20%"><B>ราคาที่ซื้อ</B></th>
												<th style="text-align: center;" width="15%">Action</th>
											</tr> 
											<%
   											int					  	seq		= 1;
											for(ComparePriceBean bean:comparePriceList){
												if(!bean.getRowStatus().equals(comparePriceForm.DEL)){
											%>
													<tr>
														<td style="text-align:center">
															<%=seq%>
														</td>
														<td align="left">
															<input type='text' 
									        					   id="vendorName<%=bean.getSeq()%>" 
									        					   name='vendorName' 
									        					   value="<%=bean.getVendorName() %>" 
									        					   maxlength="100" 
									        					   onblur="lp_getCompanyVendorDetail(<%=bean.getSeq()%>, 'V');"
									        					   style="width: 100%;" />
									        				<input type="hidden" id="vendorCode<%=bean.getSeq()%>" name="vendorCode" value="<%=bean.getVendorCode()%>" />
														</td>
														<td align="left">
															<input type='text' 
								        					   id="branchName<%=bean.getSeq()%>" 
								        					   name='branchName' 
								        					   value="<%=bean.getBranchName() %>" 
								        					   maxlength="30" 
								        					   onblur="lp_getCompanyVendorDetail(<%=bean.getSeq()%>, 'B');"
								        					   style="width: 100%;" />
														</td>
														<td align="left">
															<input type='text' 
									        					   id="quantity<%=bean.getSeq()%>" 
									        					   name='quantity'
									        					   class="moneyOnly"
									        					   onblur="gp_checkAmtOnly(this, 12);lp_updateRecord(<%=bean.getSeq()%>);"
									        					   value="<%=bean.getQuantity() %>" 
									        					   style="width: 100%" />
														</td>
														<td align="left">
															<input type='text' 
									        					   id="price<%=bean.getSeq()%>" 
									        					   name='price'
									        					   class="moneyOnly"
									        					   onblur="gp_checkAmtOnly(this, 12);lp_updateRecord(<%=bean.getSeq()%>);"
									        					   value="<%=bean.getPrice()%>" 
									        					   style="width: 100%" />
														</td>
														<td align="center">
															<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, '<%=bean.getSeq()%>');" />
															<input type="hidden" id="seq<%=bean.getSeq()%>" name="seq" value="<%=bean.getSeq()%>" />
														</td>
													</tr>
											<% seq++;}}%>
													<tr>
														<td colspan="5">&nbsp;</td>
														<td align="center">
															<img alt="เพิ่ม" title="เพิ่ม" src="<%=imgURL%>/Add.png" width="24" height="24" border="0" id="btnAdd" onclick="lp_newRecord(this, null);" />
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