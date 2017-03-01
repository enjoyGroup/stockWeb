<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.ProductmasterBean,th.go.stock.app.enjoy.bean.ProductdetailBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*,org.apache.commons.lang3.StringEscapeUtils"%>
<jsp:useBean id="productDetailsMaintananceForm" class="th.go.stock.app.enjoy.form.ProductDetailsMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String 						pageMode 			= productDetailsMaintananceForm.getPageMode();
	ProductmasterBean 			productmasterBean 	= productDetailsMaintananceForm.getProductmasterBean();
	//List<ComboBean> 			statusCombo 		= productDetailsMaintananceForm.getStatusCombo();
	String						titlePage			= productDetailsMaintananceForm.getTitlePage();
	List<ProductdetailBean> 	productdetailList	= productDetailsMaintananceForm.getProductdetailList();
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
			//gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			/*if($("#pageMode").val()=="EDIT"){
				//lp_setModeEdit();
				$("#productName").focus();
			}else{
				$("#productCodeDis").focus();
			}*/
			
			$("#productCodeDis").focus();
			
			$('html, body').animate({scrollTop: '0px'}, 300);
			
			$( "#productTypeName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getProductTypeNameList&productTypeName=" + gp_trim(request.term),//request,
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
			    	//alert(ui.item.id);
			      }
			});
			
			$( "#productGroupName" ).autocomplete({
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getProductGroupNameList&productTypeName=" + gp_trim($( "#productTypeName" ).val()) + "&productGroupName=" + gp_trim(request.term),//request,
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
			
			$( "#unitName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getUnitNameList&unitName=" + gp_trim(request.term),//request,
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
			    	//alert(ui.item.id);
			      }
			});
			
			//gp_progressBarOff();
			
		});
		
		function lp_validate(){
			var la_validate               = new Array("productCodeDis:รหัสสินค้า"	
													,"productName:ชื่อสินค้า"
													, "productTypeName:หมวดสินค้า"
													, "productGroupName:หมู่สินค้า"
													, "unitName:หน่วยสินค้า"
													//, "quantity:ปริมาณสินค้า"
													, "minQuan:ยอดต่ำสุดที่ต้องแจ้งเตือน"
													, "costPrice:ราคาทุน"
													, "salePrice1:ราคาขาย 1");
			var la_temp					= "";
		    var lv_return				= true;
		    //var startDate				= "";
		    //var expDate					= "";
		    var la_quanDiscount			= null;
		    var la_discountRate			= null;
		    var la_startDate			= null;
		    var la_expDate				= null;
		    
			try{
				//startDate		= gp_trim($("#startDate").val());
				//expDate			= gp_trim($("#expDate").val());
				la_quanDiscount = document.getElementsByName("quanDiscount");
				la_discountRate = document.getElementsByName("discountRate");
				la_startDate 	= document.getElementsByName("startDate");
				la_expDate 		= document.getElementsByName("expDate");
				
				if(!gp_validateEmptyObj(la_validate)){
					return false;
				}
				
				/*if(expDate!=""){
					if(gp_toDate(startDate) > gp_toDate(expDate)){
						alert("วันที่สิ้นสุดการใช้งานต้องมากกว่าวันที่เริ่มต้น");
		                return false;
	                }
				}*/
				
				if(!(gp_checkThaiLetter($("#productCodeDis").val()))){
					alert("รหัสสินค้าต้องเปนภาษาอังกฤษหรือตัวเลขเท่านั้น !!", function() { 
						$("#productCodeDis").val('');
	        			$("#productCodeDis").focus();
	    		    });
					return;
				}
				
				for(var i=0;i<la_quanDiscount.length;i++){
					if(gp_trim(la_quanDiscount[i].value)=="" || gp_trim(la_quanDiscount[i].value)=="0.00"){
						alert("กรุณาระบุปริมาณที่ซื้อ", function() { 
							la_quanDiscount[i].focus();
		    		    });
		            	//alert("กรุณาระบุปริมาณที่ซื้อ");
		            	//la_quanDiscount[i].focus();
		                return false;
		            }
					
					if(gp_trim(la_discountRate[i].value)=="" || gp_trim(la_discountRate[i].value)=="0.00"){
						alert("กรุณาระบุลดจำนวนเงิน", function() { 
							la_discountRate[i].focus();
		    		    });
		            	//alert("กรุณาระบุลดจำนวนเงิน");
		            	//la_discountRate[i].focus();
		                return false;
		            }
					
					if(gp_trim(la_startDate[i].value)==""){
						alert("กรุณาระบุวันที่มีผล", function() { 
							la_startDate[i].focus();
		    		    });
		            	//alert("กรุณาระบุวันที่มีผล");
		            	//la_startDate[i].focus();
		                return false;
		            }
					
					if(la_expDate[i].value.trim()!=""){
						if(gp_toDate(la_startDate[i].value.trim()) > gp_toDate(la_expDate[i].value.trim())){
							alert("วันที่สิ้นสุดการใช้งานต้องมากกว่าวันที่มีผล", function() { 
								la_expDate[i].focus();
			    		    });
							//alert("วันที่สิ้นสุดการใช้งานต้องมากกว่าวันที่มีผล");
							//la_expDate[i].focus();
			                return false;
		                }
					}
					
				}
				
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
		            			
		            			$("#productTypeCode").val(jsonObj.productTypeCode);
		            			$("#productGroupCode").val(jsonObj.productGroupCode);
		            			$("#unitCode").val(jsonObj.unitCode);
		            			
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
		
		function lp_setModeEdit(){
			
			try{
				document.getElementById("productCodeDis").readOnly 	= true;
				document.getElementById("productCodeDis").className 	= "input-disabled";
			}catch(e){
				alert("lp_setModeEdit :: " + e);
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
		            		//gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			alert("บันทึกเรียบร้อย", function() { 
		            				lp_reset();
				    		    });
		            			//alert("บันทึกเรียบร้อย");
		            			//lp_reset();
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
		
		function lp_cancel(){
			var params				= "";
			
			try{
				gp_progressBarOn();
				confirm("คุณแน่ใจว่าต้องการยกเลิกสินค้ารายการนี้", function(){
					params 	= "&pageAction=cancel&" + $('#frm').serialize();
					
					$.ajax({
						async:true,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + params,
			            beforeSend: "",
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
			            	
			            	try{
			            		//gp_progressBarOff();
			            		jsonObj = JSON.parse(data);
			            		status	= jsonObj.status;
			            		
			            		if(status=="SUCCESS"){
			            			alert("ยกเลิกเรียบร้อย", function() {
			            				window.location = gv_url + "?service=servlet.ProductDetailsSearchServlet&pageAction=new";
					    		    }, false);
			            		}else{
			            			alert(jsonObj.errMsg);
			            			
			            		}
			            	}catch(e){
			            		alert("in lp_cancel :: " + e);
			            	}
			            }
			        });
				}, function(){gp_progressBarOff();}, false);
				
			}catch(e){
				alert("lp_cancel :: " + e);
				return;
			}
		}
		
		function lp_reset(){
			try{
				if($("#pageMode").val()=="NEW"){
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
				}else{
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getDetail&productCode=" + $('#productCode').val();
				}
			}catch(e){
				alert("lp_reset :: " + e);
			}
			
		}		
		
		/*function lp_calendarItmClick(av_val){
			
			try{
				$('#'+av_val).focus();
			}catch(e){
				alert("btnAddDate :: " + e);
			}
		}*/
		

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
		    var newNodeTd5 						= null;
		    var newNodeTd6 						= null;
			
			try{
				
				params 	= gv_service + "&pageAction=newRecord&newSeq=" + lv_maxSeq;
				
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
		                        
		                      	//ปริมาณที่ซื้อ
		                       	newNodeTd2.innerHTML        = '<input type="text" style="width: 100%;" onblur="gp_checkAmtOnly(this, 9);lp_updateRecord('+lv_maxSeq+');" class="moneyOnly" id="quanDiscount' + lv_maxSeq + '" name="quanDiscount" value="" />';
		                       	
		                      	//ลดจำนวนเงิน
		                       	newNodeTd3.innerHTML        = '<input type="text" style="width: 100%;" onblur="gp_checkAmtOnly(this, 9);lp_updateRecord('+lv_maxSeq+');" class="moneyOnly" id="discountRate' + lv_maxSeq + '" name="discountRate" value="" />';
		                       	
		                      	//วันที่มีผล
		                       	newNodeTd4.innerHTML        = '<input type="text" style="width: 100px;"  onchange="gp_checkDate(this);lp_updateRecord('+lv_maxSeq+');" class="dateFormat" id="startDate' + lv_maxSeq + '"  placeholder="DD/MM/YYYY" name="startDate" value="" />';
		                       	
		                      	//วันที่มีผล
		                       	newNodeTd5.innerHTML        = '<input type="text" style="width: 100px;"  onchange="gp_checkDate(this);lp_updateRecord('+lv_maxSeq+');" class="dateFormat" id="expDate' + lv_maxSeq + '"  placeholder="DD/MM/YYYY" name="expDate" value="" />';
		                       	
		                      	//Action
		                      	newNodeTd6.align 			= "center";
		                       	newNodeTd6.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + lv_maxSeq + '\');" />'
															+ '<input type="hidden" id="seq'+lv_maxSeq+'" name="seq" value="'+lv_maxSeq+'" />';
		                        
								lo_seqTemp.value  = lv_maxSeq;
								$('#quanDiscount' + lv_maxSeq).focus();
								
								$( ".dateFormat" ).datepicker( $.datepicker.regional["th"] );
							    $( ".dateFormat" ).datepicker( "option", "defaultDate", +0 );
								
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
		
		function lp_updateRecord(av_val){
			var params					= "";
			var quanDiscount			= null;
			var discountRate			= null;
			var startDate				= null;
			var expDate					= null;
			
			try{
				
				quanDiscount 	= document.getElementById("quanDiscount" + av_val).value;
				discountRate 	= document.getElementById("discountRate" + av_val).value;
				startDate 		= document.getElementById("startDate" + av_val).value;
				expDate 		= document.getElementById("expDate" + av_val).value;
				
				params 	= gv_service + "&pageAction=updateRecord&seq=" + av_val 
															 + "&quanDiscount=" + quanDiscount 
															 + "&discountRate=" + discountRate
															 + "&startDate=" 	+ startDate
															 + "&expDate=" 		+ expDate;
				
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
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.ProductDetailsMaintananceServlet" />
		<input type="hidden" id="pageMode" 	name="pageMode" value="<%=pageMode%>" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=productDetailsMaintananceForm.getSeqTemp()%>" />
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
								<h4 class="alert-heading">จัดการรายละเอียดสินค้าต่างๆ</h4>
							</div>					          	
							<div class="container main-container round-sm padding-xl-h">
								<div class="col-sm-12 toppad" >
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em"><%=titlePage%></h6>
									</div>
				         			<div class="panel-body">
				         				<table class="table user-register-table" style="border-bottom-color: white;">
											<tr>
								        		<td align="right">
													รหัสสินค้า <span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="productCodeDis" 
							        					   name='productCodeDis' 
							        					   value="<%=StringEscapeUtils.escapeHtml4(productmasterBean.getProductCodeDis())%>" 
							        					   maxlength="17" 
							        					   style="width: 220px;" />
							        				<input type='hidden' id="productCode" name='productCode' value="<%=productmasterBean.getProductCode()%>"  />
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													ชื่อสินค้า <span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="productName" 
							        					   name='productName' 
							        					   value="<%=StringEscapeUtils.escapeHtml4(productmasterBean.getProductName())%>" 
							        					   maxlength="255" 
							        					   style="width: 220px;" />
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													หมวดสินค้า <span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="productTypeName" 
							        					   name='productTypeName' 
							        					   value="<%=StringEscapeUtils.escapeHtml4(productmasterBean.getProductTypeName())%>" 
							        					   maxlength="200" 
							        					   style="width: 220px;" />
							        				<input type="hidden" id="productTypeCode" name="productTypeCode" value="<%=productmasterBean.getProductTypeCode()%>" />
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													หมู่สินค้า <span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="productGroupName" 
							        					   name='productGroupName' 
							        					   value="<%=StringEscapeUtils.escapeHtml4(productmasterBean.getProductGroupName())%>" 
							        					   maxlength="200" 
							        					   style="width: 220px;" />
							        				<input type="hidden" id="productGroupCode" name="productGroupCode" value="<%=productmasterBean.getProductGroupCode()%>" />
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													หน่วยสินค้า <span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="unitName" 
							        					   name='unitName' 
							        					   value="<%=StringEscapeUtils.escapeHtml4(productmasterBean.getUnitName())%>" 
							        					   maxlength="200" 
							        					   style="width: 220px;" />
							        				<input type="hidden" id="unitCode" name="unitCode" value="<%=productmasterBean.getUnitCode()%>" />
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													ยอดต่ำสุดที่ต้องแจ้งเตือน <span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="minQuan" 
							        					   name='minQuan' 
							        					   class="moneyOnly"
							        					   onblur="gp_checkAmtOnly(this, 9);"
							        					   value="<%=productmasterBean.getMinQuan()%>" 
							        					   style="width: 220px;" />
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													ราคาทุน <span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="costPrice" 
							        					   name='costPrice' 
							        					   class="moneyOnly"
							        					   onblur="gp_checkAmtOnly(this, 11);"
							        					   value="<%=productmasterBean.getCostPrice()%>" 
							        					   style="width: 220px;" />
							        				&nbsp;บาท
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													ราคาขาย 1 <span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="salePrice1" 
							        					   name='salePrice1' 
							        					   class="moneyOnly"
							        					   onblur="gp_checkAmtOnly(this, 11);"
							        					   value="<%=productmasterBean.getSalePrice1()%>" 
							        					   style="width: 220px;" />
							        				&nbsp;บาท
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													ราคาขาย 2 :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="salePrice2" 
							        					   name='salePrice2' 
							        					   class="moneyOnly"
							        					   onblur="gp_checkAmtOnly(this, 11);"
							        					   value="<%=productmasterBean.getSalePrice2()%>" 
							        					   style="width: 220px;" />
							        				&nbsp;บาท
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													ราคาขาย 3 :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="salePrice3" 
							        					   name='salePrice3' 
							        					   class="moneyOnly"
							        					   onblur="gp_checkAmtOnly(this, 11);"
							        					   value="<%=productmasterBean.getSalePrice3()%>" 
							        					   style="width: 220px;" />
							        				&nbsp;บาท
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													ราคาขาย 4 :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="salePrice4" 
							        					   name='salePrice4' 
							        					   class="moneyOnly"
							        					   onblur="gp_checkAmtOnly(this, 11);"
							        					   value="<%=productmasterBean.getSalePrice4()%>" 
							        					   style="width: 220px;" />
							        				&nbsp;บาท
							        			</td>
								        	</tr>
								        	<tr>
								        		<td align="right">
													ราคาขาย 5 :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="salePrice5" 
							        					   name='salePrice5' 
							        					   class="moneyOnly"
							        					   onblur="gp_checkAmtOnly(this, 11);"
							        					   value="<%=productmasterBean.getSalePrice5()%>" 
							        					   style="width: 220px;" />
							        				&nbsp;บาท
							        			</td>
								        	</tr>
								        </table>
								        <br/>
								        <table class="table sim-panel-result-table" id="resultData">
											<tr height="26px;">
												<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
												<th  style="text-align: center;" width="20%"><B>ปริมาณที่ซื้อ</B></th>
												<th  style="text-align: center;" width="20%"><B>ลดจำนวนเงิน(%)</B></th>
												<th  style="text-align: center;" width="20%"><B>วันที่มีผล</B></th>
												<th  style="text-align: center;" width="20%"><B>วันที่สิ้นสุด</B></th>
												<th style="text-align: center;" width="15%">Action</th>
											</tr> 
											<%
													int					  	seq		= 1;
												for(ProductdetailBean bean:productdetailList){
													if(!bean.getRowStatus().equals(productDetailsMaintananceForm.DEL)){
												%>
											<tr>
												<td style="text-align:center">
													<%=seq%>
												</td>
												<td align="center">
													<input type="text" 
														   style="width: 100%" 
														   onblur="gp_checkAmtOnly(this, 9);lp_updateRecord(<%=bean.getSeq()%>);" 
														   id="quanDiscount<%=bean.getSeq()%>" 
														   name="quanDiscount" 
														   class="moneyOnly"
														   value="<%=bean.getQuanDiscount()%>" />
												</td>
												<td align="left">
													<input type="text" 
														   style="width: 100%" 
														   onblur="gp_checkAmtOnly(this, 9);lp_updateRecord(<%=bean.getSeq()%>);" 
														   id="discountRate<%=bean.getSeq()%>" 
														   name="discountRate" 
														   class="moneyOnly"
														   value="<%=bean.getDiscountRate()%>" />
												</td>
												<td align="left">
													<input type='text' 
							        					   id="startDate<%=bean.getSeq()%>" 
							        					   name='startDate' 
							        					   placeholder="DD/MM/YYYY"
							        					   class="dateFormat"
							        					   onchange="gp_checkDate(this);lp_updateRecord(<%=bean.getSeq()%>);"
							        					   value="<%=bean.getStartDate() %>" 
							        					   style="width: 100px;" />
												</td>
												<td align="left">
													<input type='text' 
							        					   id="expDate<%=bean.getSeq()%>" 
							        					   name='expDate' 
							        					   placeholder="DD/MM/YYYY"
							        					   class="dateFormat"
							        					   onchange="gp_checkDate(this);lp_updateRecord(<%=bean.getSeq()%>);"
							        					   value="<%=bean.getExpDate() %>" 
							        					   style="width: 100px;" />
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
													<img alt="เพิ่ม" title="เพิ่ม" src="<%=imgURL%>/Add.png" width="24" height="24" border="0" onclick="lp_newRecord(this);" />
												</td>
											</tr>
										</table>
										<br/>
										<table width="100%" border="0">
											<tr>
							        			<td align="center">
							        				<br/>
							        				<input type="button" id="btnSave" class="btn btn-sm btn-primary" value='บันทึก' onclick="lp_save();" />&nbsp;&nbsp;&nbsp;
							        				<%if(pageMode.equals(productDetailsMaintananceForm.EDIT)){ %>
							        					<input type="button" id="btnCancel" class="btn btn-sm btn-danger" value='ยกเลิก' onclick="lp_cancel();" />&nbsp;&nbsp;&nbsp;
							        				<%} %>
			   										<input type="button" id="btnReset" onclick="lp_reset();" class="btn btn-sm btn-warning" value='เริ่มใหม่' />
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