<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean,
				th.go.stock.app.enjoy.bean.InvoiceCreditDetailBean,
				th.go.stock.app.enjoy.bean.CustomerDetailsBean,
				th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="invoiceCreditMaintananceForm" class="th.go.stock.app.enjoy.form.InvoiceCreditMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String 							pageMode 				= invoiceCreditMaintananceForm.getPageMode();
	InvoiceCreditMasterBean 		invoiceCreditMasterBean = invoiceCreditMaintananceForm.getInvoiceCreditMasterBean();
	List<ComboBean> 				invoiceStatusCombo 		= invoiceCreditMaintananceForm.getInvoiceStatusCombo();
	String							titlePage				= invoiceCreditMaintananceForm.getTitlePage();
	CustomerDetailsBean 			customerDetailsBean 	= invoiceCreditMaintananceForm.getCustomerDetailsBean();
	List<InvoiceCreditDetailBean> 	invoiceCreditDetailList = invoiceCreditMaintananceForm.getInvoiceCreditDetailList();
	List<ComboBean> 				companyCombo 			= invoiceCreditMaintananceForm.getCompanyCombo();

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
			
			$("input[name=productName]").live("focus",function(){
				$(this).autocomplete({
					 
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
				    	  var lv_seq	= $(this).attr('id').substr($(this).attr('name').length,  $(this).attr('id').length);
				    	  
				    	  lp_getProductDetailByName(lv_seq, ui.item.value);
				      }
				  });
				
			});
			
			$( "#saleName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getSaleName&saleName=" + gp_trim(request.term),//request,
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
			    	$("#saleUniqueId").val(ui.item.id);
			      }
			});
			
			/*$( "#dialog" ).dialog({
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
		    });*/
			
			$( "#btnZoom" ).live("click", function(event){
				
				//var lo_dialog = null;
				//var lo_iframe = null;
				
				try{
					
					if(!gp_validateEmptyObj(new Array( "tin:บริษัทที่สังกัด"))){
						return false;
					}
					
					gp_dialogPopUp("/stockWeb/EnjoyGenericSrv?service=servlet.ProductDetailsLookUpServlet&pageAction=new", "เลือกสินค้า");
					
					/*lo_dialog 	= $( "#dialog" );
					lo_iframe	= $("<iframe />").attr("src", "/stockWeb/EnjoyGenericSrv?service=servlet.ProductDetailsLookUpServlet&pageAction=new")
												 .attr("width", "100%")
												 .attr("height", "100%")
												 .attr("border", "0");
					
					gp_progressBarOn();
					lo_dialog.empty();
					lo_dialog.dialog("option", "title", "เลือกสินค้า");
					lo_dialog.append(lo_iframe).dialog( "open" );
					event.preventDefault();*/
				}catch(e){
					alert("btnZoom :: " + e);
				}
		    });
			
			$("#productCodeDis").live("focus", function(){
				if(document.getElementById("headwrap").style.display=="none"){
					$('html, body').animate({
				        scrollTop: parseInt($("#div_barcode").offset().top) - 50
				    }, 800);
				}else if(document.getElementById("headwrap").style.display=="block"){
					$('html, body').animate({
				        scrollTop: parseInt($("#div_barcode").offset().top) - 180
				    }, 800);
				}else if(document.getElementById("headwrap").style.display==""){
					$('html, body').animate({
				        scrollTop: parseInt($("#div_barcode").offset().top) - 900
				    }, 800);
				}
			    
			});
			
			$('#printSection').load(function(){
				var lo_pdf = document.getElementById("printSection");
				lo_pdf.focus();
				lo_pdf.contentWindow.print();
				return false;
			});
			
			if($("#pageMode").val()=="EDIT"){
				lp_setModeEdit();
			}else{
				lp_setModeNew();
			}
			
		});
		
		function lp_validate(){
			var la_validate             = new Array( "invoiceDate:วันที่ขาย", "tin:บริษัทที่สังกัด", "cusCodeDis:รหัสลูกค้า");
		    var lv_return				= true;
		    var la_productName			= null;
		    var la_productCode			= null;
		    var la_inventory			= null;
		    var la_quantity				= null;
		    var lv_inventory			= 0.00;
		    var lv_quantity				= 0.00;
		    
			try{
				la_productName		= document.getElementsByName("productName");
				la_productCode		= document.getElementsByName("productCode");
				la_inventory		= document.getElementsByName("inventory");
				la_quantity			= document.getElementsByName("quantity");
				
				if(!gp_validateEmptyObj(la_validate)){
					return false;
				}
				
				/*Begin รหัสลูกค้า*/
				if($("#cusCode").val().trim()=="" && $("#cusCodeDis").val().trim()!=""){ 
					alert("ระบุรหัสลูกค้าผิด", function() { 
						$("#cusCodeDis").focus();
	    		    });
					//alert("ระบุรหัสลูกค้าผิด");
					//$("#cusCodeDis").focus();
	                return false;
				}
				/*End รหัสลูกค้า*/
				
				/*Begin พนักงานขาย*/
				if($("#saleUniqueId").val().trim()=="" && $("#saleName").val().trim()!=""){ 
					alert("ระบุพนักงานขายผิด", function() { 
						$("#saleName").focus();
	    		    });
					//alert("ระบุพนักงานขายผิด");
					//$("#saleName").focus();
	                return false;
				}
				/*End พนักงานขาย*/
				
				/*Begin Check รายการสินค้า*/
				for(var i=0;i<la_productName.length;i++){
					if(la_productName[i].value.trim()==""){
						alert("กรุณาระบุสินค้า", function() { 
							la_productName[i].focus();
		    		    });
						//alert("กรุณาระบุสินค้า");
						//la_productName[i].focus();
						return false;
					}else{
						if(la_productCode[i].value.trim()==""){
							alert("ระบุสินค้าชื่อสินค้าผิดกรุณาตรวจสอบ", function() { 
								la_productName[i].focus();
			    		    });
							//alert("ระบุสินค้าชื่อสินค้าผิดกรุณาตรวจสอบ");
							//la_productName[i].focus();
							return false;
						}else{
							lv_quantity = gp_parseFloat(la_quantity[i].value);
							if(lv_quantity < 1){
								alert(la_productName[i].value + "กรุณาระบุปริมาณอย่างน้อย 1", function() { 
									la_quantity[i].focus();
				    		    });
								return false;
							}
							
							lv_inventory = gp_parseFloat(la_inventory[i].value) - gp_parseFloat(la_quantity[i].value);
							
							if(lv_inventory < 0){
								alert(la_productName[i].value + "เหลือไม่เพียงพอจำหน่าย", function() { 
									la_productName[i].focus();
				    		    });
								return false;
							}
						}
					}
				}
				/*End Check รายการสินค้า*/
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return lv_return;
		}
		
		function lp_setModeEdit(){
			
			try{
				$("form").each(function(){
				    //$(this).find(':input').attr('readonly', true).attr('class', 'input-disabled');
					$(this).find('input:visible').prop("disabled", true);
					$(this).find('textarea:visible').prop("disabled", true);
					$(this).find('select:visible').prop("disabled", true);
				    
				});
				
				$("#btnPrint").prop("disabled", false);
				$("#btnCancel").prop("disabled", false);
				
			}catch(e){
				alert("lp_setModeEdit :: " + e);
			}
			
		}
		
		function lp_setModeNew(){
			try{
				$("#invoiceStatus").prop("disabled", true);
				lp_setSaleCommission();
				
				$("#cusCodeDis").focus();
				$("body").scrollTop(0);
				
				/*if($('#tin').val()!=""){
					$("#productCodeDis").focus();
				}*/
				
			}catch(e){
				alert("setModeNew :: " + e);
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
					async:true,
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
		            				window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getDetail&invoiceCode=" + jsonObj.invoiceCode;
				    		    });
		            			//alert("บันทึกเรียบร้อย");//alert(jsonObj.invoiceCode);
		            			//window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getDetail&invoiceCode=" + jsonObj.invoiceCode;
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
				confirm("คุณแน่ใจว่าต้องการยกเลิกรายการนี้", function(){
					//params 	= "&pageAction=cancel&invoiceCode=" + $('#invoiceCode').val().trim() + "&tin=" + $('#tin').val().trim();
					params 	= "&pageAction=cancel&" + $('#frm').serialize();
					
					$.ajax({
						async:true,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + params,
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
			            	
			            	try{
			            		gp_progressBarOff();
			            		//alert(data);
			            		jsonObj = JSON.parse(data);
			            		status	= jsonObj.status;
			            		
			            		if(status=="SUCCESS"){
			            			alert("ยกเลิกเรียบร้อย", function() { 
			            				window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getDetail&invoiceCode=" + jsonObj.invoiceCode;
					    		    });
			            			//alert("ยกเลิกเรียบร้อย");
			            			//window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getDetail&invoiceCode=" + jsonObj.invoiceCode;
			            		}else{
			            			alert(jsonObj.errMsg);
			            			
			            		}
			            	}catch(e){
			            		alert("in lp_cancel :: " + e);
			            	}
			            }
			        });
				});
				
			}catch(e){
				alert("lp_cancel :: " + e);
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
		            			
		            			lp_invoicePrice();
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

		function lp_newRecord(ao_obj, ao_jsonObj){
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
		    var newNodeTd7 						= null;
		    var newNodeTd8 						= null;
		    var newNodeTd9 						= null;
			
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
		            	var lv_productCode		= "";
		            	var lv_productName		= "";
		            	var lv_pricePerUnit		= "0.00";
		            	var lv_inventory		= "0.00";
		            	var lv_quantity			= "1.00";
		            	var lv_unitCode			= "";
		            	var lv_unitName			= "";
		            	var lv_discount			= "0.00";
		            	
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
		                        newNodeTd7      = newNodeTr.insertCell(6);
		                        newNodeTd8      = newNodeTr.insertCell(7);
		                        newNodeTd9      = newNodeTr.insertCell(8);
		                        
		                        if(ao_jsonObj!=null){
		                        	lv_productCode 	= ao_jsonObj.productCode;
		                        	lv_productName 	= ao_jsonObj.productName;
		                        	lv_pricePerUnit	= ao_jsonObj.pricePerUnit;
		                        	lv_inventory 	= ao_jsonObj.inventory;
		                        	lv_unitCode 	= ao_jsonObj.unitCode;
		                        	lv_unitName 	= ao_jsonObj.unitName;
		                        	lv_discount		= ao_jsonObj.discount;
		                        }
		                        
		                        //ลำดับ
		                        newNodeTd1.align 			= "center";
		                        newNodeTd1.innerHTML        = rowIndex;
		                        
		                      	//สินค้า
		                      	newNodeTd2.align 			= "left";
		                       	newNodeTd2.innerHTML        = '<input type="text" style="width: 100%" onblur="lp_getProductDetailByName(' + lv_maxSeq + ', this.value);" id="productName' + lv_maxSeq + '" name="productName" value="'+lv_productName+'" />'
		                       								+ '<input type="hidden" id="productCode'+lv_maxSeq+'" name="productCode" value="'+lv_productCode+'" />';
		                       	
		                      	//เหลือในคลัง
		                       	newNodeTd3.innerHTML        = '<input type="text" style="width: 100%;" class="input-disabled" readonly="readonly" id="inventory' + lv_maxSeq + '" name="inventory" value="'+lv_inventory+'" />';
		                       	
		                      	//ปริมาณ
		                       	newNodeTd4.innerHTML        = '<input type="text" style="width: 100%"  onblur="gp_checkAmtOnly(this, 12);lp_getDiscount('+lv_maxSeq+');" class="moneyOnly" id="quantity' + lv_maxSeq + '" name="quantity" value="'+lv_quantity+'" />';
		                       	
		                      	//หน่วย
		                       	newNodeTd5.innerHTML        = '<input type="text" style="width: 100%;" id="unitName' + lv_maxSeq + '" class="input-disabled" readonly="readonly" name="unitName" value="'+lv_unitName+'" />'
   															+ '<input type="hidden" id="unitCode'+lv_maxSeq+'" name="unitCode" value="'+lv_unitCode+'" />';
   								
								//ราคาต่อหน่วย
		                       	newNodeTd6.innerHTML        = '<input type="text" style="width: 100%"  onblur="gp_checkAmtOnly(this, 12);lp_calAmount('+lv_maxSeq+');" class="moneyOnly" id="pricePerUnit' + lv_maxSeq + '" name="pricePerUnit" value="'+lv_pricePerUnit+'" />';
		                       	
		                      	//ส่วนลด
		                       	newNodeTd7.innerHTML        = '<input type="text" style="width: 100%"  onblur="gp_checkAmtOnly(this, 12);lp_calAmount('+lv_maxSeq+');" class="moneyOnly" id="discount' + lv_maxSeq + '" name="discount" value="'+lv_discount+'" />';
		                       	
		                      	//จำนวนเงิน
		                       	newNodeTd8.innerHTML        = '<input type="text" style="width: 100%"  onblur="gp_checkAmtOnly(this, 12);lp_invoicePrice();lp_updateRecord('+lv_maxSeq+');" class="moneyOnly" id="price' + lv_maxSeq + '" name="price" value="0.00" />';
		                       	
		                      	//Action
		                      	newNodeTd9.align 			= "center";
		                       	newNodeTd9.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + lv_maxSeq + '\');" />'
															+ '<input type="hidden" id="seq'+lv_maxSeq+'" name="seq" value="'+lv_maxSeq+'" />';
		                        
								lo_seqTemp.value  = lv_maxSeq;
								
								if(ao_jsonObj!=null){
									lp_calAmount(lv_maxSeq);
									$("#productCodeDis").val('');
									$("#productCodeDis").focus();
									//lp_updateRecord(lv_maxSeq);
								}else{
									$('#productName' + lv_maxSeq).focus();
								}
								
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
															 + "&productCode=" 	+ $("#productCode" + av_val).val() .trim()
															 + "&productName=" 	+ $("#productName" + av_val).val() .trim()
															 + "&inventory=" 	+ $("#inventory" + av_val).val() .trim()
															 + "&quantity=" 	+ $("#quantity" + av_val).val() .trim()
															 + "&unitCode=" 	+ $("#unitCode" + av_val).val() .trim()
															 + "&unitName=" 	+ $("#unitName" + av_val).val() .trim()
															 + "&pricePerUnit=" + $("#pricePerUnit" + av_val).val() .trim()
															 + "&discount=" 	+ $("#discount" + av_val).val() .trim()
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
		
		function lp_getProductDetailByName(av_seq, av_productName){
			
			try{
				
				if(!gp_validateEmptyObj(new Array( "tin:บริษัทที่สังกัด"))){
					return false;
				}
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getProductDetailByName&productName=" + av_productName.trim() + "&groupSalePrice=" + $("#groupSalePrice").val().trim() + "&tin=" + $("#tin").val().trim(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			if(jsonObj.productCode==""){
		            				$("#productCode" + av_seq).val('');
			            			$("#pricePerUnit" + av_seq).val('0.00');
			            			$("#inventory" + av_seq).val('');
			            			$("#quantity" + av_seq).val('0.00');
			            			$("#unitCode" + av_seq).val('');
			            			$("#unitName" + av_seq).val('');
			            			$("#price" + av_seq).val('0.00');
			            			$("#discount" + av_seq).val('0.00');
		            			}else{
		            				$("#productCode" + av_seq).val(jsonObj.productCode);
			            			$("#pricePerUnit" + av_seq).val(jsonObj.pricePerUnit);
			            			$("#inventory" + av_seq).val(jsonObj.inventory);
			            			//$("#quantity" + av_seq).val('1.00');
			            			$("#unitCode" + av_seq).val(jsonObj.unitCode);
			            			$("#unitName" + av_seq).val(jsonObj.unitName);
			            			$("#discount" + av_seq).val(jsonObj.discount);
		            			}
		            			
		            			lp_calAmount(av_seq);
		            			//lp_updateRecord(av_seq);
		            			
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
		
		function lp_getProductDetailByCode(av_seq, av_productCode){
			
			try{
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getProductDetailByCode&productCode=" + av_productCode.trim() + "&groupSalePrice=" + $("#groupSalePrice").val().trim() + "&tin=" + $("#tin").val().trim(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			$("#pricePerUnit" + av_seq).val(jsonObj.pricePerUnit);
		            			//alert($("#pricePerUnit" + av_seq).val());
		            			lp_calAmount(av_seq);
		            			//lp_updateRecord(av_seq);
		            			
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_getProductDetailByCode :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_getProductDetailByCode :: " + e);
			}
		}
		
		function lp_calAmount(av_seq){
			
			var lv_quantity 		= 0.00;
			var lv_pricePerUnit 	= 0.00;
			var lv_discount 		= 0.00;
			var lv_price 			= 0.00;
			
			try{
				lv_quantity 		= gp_parseFloat($("#quantity" + av_seq).val());
				lv_pricePerUnit		= gp_parseFloat($("#pricePerUnit" + av_seq).val());
				lv_discount 		= 100 - gp_parseFloat($("#discount" + av_seq).val());
				
				if(lv_quantity > 0){
					lv_price = ((lv_quantity * lv_pricePerUnit) * lv_discount) / 100;
				}
				
				
				$("#price" + av_seq).val(lv_price);
				gp_format(document.getElementById("price" + av_seq), 2);
				
				lp_invoicePrice();
				
				lp_updateRecord(av_seq);
				
			}catch(e){
				alert("lp_calAmount :: " + e);
			}
		}
		
		function lp_invoicePrice(){
			
			var lv_invoicePrice		= 0.00;
			
			try{
				
				$("input[name=price]").each(function() {
					lv_invoicePrice += gp_parseFloat($(this).val());
				});
				
				$("#invoicePrice").val(lv_invoicePrice);
				gp_format(document.getElementById("invoicePrice"), 2);
				
				lp_calInvoiceVat();
				
			}catch(e){
				alert("lp_invoicePrice :: " + e);
			}
		}
		
		function lp_calInvoiceVat(){
			
			var lv_invoiceVat		= 0.00;
			var lv_invoicePrice		= 0.00;
			var lv_invoicediscount	= 0.00;
			var lv_systemVat		= 0.00;
			var lo_invoiceType1		= null;
			
			try{
				lo_invoiceType1		= document.getElementById("invoiceType1");
				lv_systemVat		= gp_parseFloat($("#systemVat").val());
				lv_invoicePrice 	= gp_parseFloat($("#invoicePrice").val());
				lv_invoicediscount 	= gp_parseFloat($("#invoicediscount").val());
				
				if(lo_invoiceType1.checked==true){
					lv_invoiceVat		= ((lv_invoicePrice - lv_invoicediscount) * lv_systemVat)/100;
				}
				
				$("#invoiceVat").val(lv_invoiceVat);
				gp_format(document.getElementById("invoiceVat"), 2);
				
				lp_InvoiceTotal();
				
			}catch(e){
				alert("lp_calInvoiceVat :: " + e);
			}
		}
		
		function lp_InvoiceTotal(){
			
			var lv_invoiceVat		= 0.00;
			var lv_invoicePrice		= 0.00;
			var lv_invoicediscount	= 0.00;
			var lv_invoiceTotal		= 0.00;
			var lv_invoiceDeposit	= 0.00;
			
			try{
				
				lv_invoicePrice 	= gp_parseFloat($("#invoicePrice").val());
				lv_invoicediscount 	= gp_parseFloat($("#invoicediscount").val());
				lv_invoiceVat 		= gp_parseFloat($("#invoiceVat").val());
				lv_invoiceDeposit 	= gp_parseFloat($("#invoiceDeposit").val());
				lv_invoiceTotal		= (lv_invoicePrice - lv_invoicediscount - lv_invoiceDeposit) + lv_invoiceVat;
				
				$("#invoiceTotal").val(lv_invoiceTotal);
				gp_format(document.getElementById("invoiceTotal"), 2);
				
			}catch(e){
				alert("lp_InvoiceTotal :: " + e);
			}
		}
		
		function lp_getDiscount(av_seq){
			
			var lv_productCode 	= "";
			var lv_quantity 	= "";
			
			try{
				lv_productCode 	= $("#productCode" + av_seq).val().trim();
				lv_quantity		= $("#quantity" + av_seq).val().trim();
				
				if(lv_productCode==""){
					return;
				}
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getDiscount&productCode=" + lv_productCode + "&quantity=" + lv_quantity,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			$("#discount" + av_seq).val(jsonObj.discount);
		            			lp_calAmount(av_seq);
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_getDiscount :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_getDiscount :: " + e);
			}
		}
		
		function lp_returnProduct(ao_jsonObj){
			
			var productList			= null;
			
			try{
				$( "#dialog" ).dialog( "close" );
				
				productList 	= ao_jsonObj.productList;
				
				$.each(productList, function(idx, obj) {
					//alert(obj.productName.trim());
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=getProductDetailByName&productName=" + obj.productName.trim() + "&tin=" + $("#tin").val().trim(),
			            beforeSend: "",
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
			            	var errMsg				= null;
			            	
			            	try{
			            		jsonObj = JSON.parse(data);
			            		status	= jsonObj.status;
			            		
			            		if(status=="SUCCESS"){
			            			lp_newRecord(document.getElementById("btnAdd"), jsonObj);
			            		}else{
			            			errMsg 	= jsonObj.errMsg;
			            			alert(errMsg);
			            		}
			            	}catch(e){
			            		alert("in lp_returnProduct :: " + e);
			            	}
			            }
			        });
    			});
				
			}catch(e){
				alert("lp_returnProduct :: " + e);
			}
		}
		
		function lp_lookUpCusDetail(){
			
			try{
				
				gp_dialogPopUp("/stockWeb/EnjoyGenericSrv?service=servlet.CustomerDetailsLookUpServlet&pageAction=new", "ค้นหาลุกค้า");
			}catch(e){
				alert("lp_lookUpCusDetail :: " + e);
			}
		}
		
		function lp_returnCustomerData(av_cusCode, av_cusGroupCode, av_fullName, av_groupSalePrice){
			
			var la_productCode 	= null;
			var la_seq 			= null;
			
			try{
				
				la_productCode 	= document.getElementsByName("productCode");
				la_seq 			= document.getElementsByName("seq");
				
				$("#cusCodeDis").val(av_cusCode);
				$("#cusCode").val(av_cusCode);
				$("#cusGroupCode").val(av_cusGroupCode);
				$("#groupSalePrice").val(av_groupSalePrice);
				$("#spanCusName").html(av_fullName);
				$('#spanCusName').attr('class', 'correct');
				
				for(var i=0;i<la_productCode.length;i++){
					if(la_productCode[i].value!=""){
						lp_getProductDetailByCode(la_seq[i].value, la_productCode[i].value);
					}
				}
				
				$( "#dialog" ).dialog( "close" );
			}catch(e){
				alert("lp_returnCustomerData :: " + e);
			}
		}
		
		function getCustomerDetail(){
			var cusCodeDis 		= null;
			var la_productCode 	= null;
			var la_seq 			= null;
			
			try{
				cusCodeDis 		= $("#cusCodeDis").val().trim();
				la_productCode	= document.getElementsByName("productCode");
				la_seq			= document.getElementsByName("seq");
				
				$("#spanCusName").html("");
				$("#cusCode").val('');
				$("#cusGroupCode").val('');
				$("#groupSalePrice").val('');
				if(cusCodeDis==""){
					for(var i=0;i<la_productCode.length;i++){
						if(la_productCode[i].value!=""){
							lp_getProductDetailByCode(la_seq[i].value, la_productCode[i].value);
						}
					}
					return;
				}
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getCustomerDetail&cusCode=" + cusCodeDis,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			if(jsonObj.cusCode!=""){
		            				$("#cusCodeDis").val(jsonObj.cusCode);
		            				$("#cusCode").val(jsonObj.cusCode);
		            				$("#cusGroupCode").val(jsonObj.cusGroupCode);
		            				$("#groupSalePrice").val(jsonObj.groupSalePrice);
		            				$("#spanCusName").html(jsonObj.fullName);
		            				$('#spanCusName').attr('class', 'correct');
		            			}else{
		            				$("#cusCode").val('');
		            				$("#cusGroupCode").val('');
		            				$("#groupSalePrice").val('');
		            				$("#spanCusName").html("รหัสลูกค้าผิด");
		            				$('#spanCusName').attr('class', 'wrong');
		            			}
		            			
		            			for(var i=0;i<la_productCode.length;i++){
		    						if(la_productCode[i].value!=""){
		    							lp_getProductDetailByCode(la_seq[i].value, la_productCode[i].value);
		    						}
		    					}
		            			
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in getCustomerDetail :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("getCustomerDetail :: " + e);
			}
		}
		
		function getSaleNameDetail(){
			var saleName 	= null;
			
			try{
				saleName = $("#saleName").val().trim();
				
				if(saleName==""){
					$("#saleUniqueId").val('');
					lp_setSaleCommission();
					return true;
				}
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getSaleNameDetail&saleName=" + gp_sanitizeURLString(saleName),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			if(jsonObj.fullName!=""){
		            				$("#saleUniqueId").val(jsonObj.saleUniqueId);
		            			}else{
		            				$("#saleUniqueId").val("");
		            			}
		            			
		            			lp_setSaleCommission();
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in getSaleNameDetail :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("getSaleNameDetail :: " + e);
			}
		}
		
		function lp_lookUpSaleName(){
			try{
				gp_dialogPopUp("/stockWeb/EnjoyGenericSrv?service=servlet.UserDetailsLookUpServlet&pageAction=new", "ค้นหาพนักงานขาย");
			}catch(e){
				alert("lp_lookUpSaleName :: " + e);
			}
		}
		
		function lp_returnData(av_userUniqueId, av_userFullName, av_userId, av_userStatus, av_userStatusName){
			
			try{
				$("#saleUniqueId").val(av_userUniqueId);
				$("#saleName").val(av_userFullName);
				$( "#dialog" ).dialog( "close" );
				lp_setSaleCommission();
			}catch(e){
				alert("lp_returnData :: " + e);
			}
			
		}
		
		function lp_setSaleCommission(){
			
			var lv_saleName = "";
			
			try{
				lv_saleName = $("#saleName").val().trim();
				
				if(lv_saleName==""){
					$('#saleCommission').prop("readonly", true)
										.val('0.00')
										.removeClass("moneyOnly")
										.addClass("moneyOnly-disabled");
				}else{
					$('#saleCommission').prop("readonly", false)
										.val('0.00')
										.removeClass("moneyOnly-disabled")
										.addClass("moneyOnly");
				}
				
			}catch(e){
				alert("lp_setSaleCommission :: " + e);
			}
		}
		
		function readBarcode(event) {
		    var x 		= event.which || event.keyCode;

			try{
				if(x==13){
					lp_searchProductByProductCode();
				}
			}catch(e){
				alert("readBarcode :: " + e);
			}
		}
		
		function lp_searchProductByProductCode(){
			var params 				= "";
			var letters 			= /^[\u0E01-\u0E5B]+$/;
			var lv_productCodeDis 	= "";
			
			try{
				
				if(!gp_validateEmptyObj(new Array( "tin:บริษัทที่สังกัด"))){
					return false;
				}
				
				lv_productCodeDis = $("#productCodeDis").val().trim();
				
				if(lv_productCodeDis==""){
					$("#productCodeDis").focus();
					return;
				}
				
				if(letters.test(lv_productCodeDis)){
					alert("รหัสสินค้าห้ามเป็นภาษาไทย", function() { 
						$("#productCodeDis").val('');
	        			$("#productCodeDis").focus();
	    		    });
					//alert("รหัสสินค้าห้ามเป็นภาษาไทย");
					//$("#productCodeDis").val('');
        			//$("#productCodeDis").focus();
					return;
				}
				
				params = "&productCode=" + lv_productCodeDis + "&groupSalePrice=" + $("#groupSalePrice").val().trim() + "&tin=" + $("#tin").val().trim();
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getProductDetailByCode" + params,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			if(jsonObj.productCode!=""){
		            				lp_newRecord(document.getElementById("btnAdd"), jsonObj);
		            			}else{
		            				alert("รหัสสินค้า "+ lv_productCodeDis + " ไม่มีอยู่ในระบบ", function() { 
		            					$("#productCodeDis").val('');
				            			$("#productCodeDis").focus();
		        	    		    });
		            				//alert("รหัสสินค้า "+ lv_productCodeDis + " ไม่มีอยู่ในระบบ");
		            				//$("#productCodeDis").val('');
			            			//$("#productCodeDis").focus();
		            			}
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_searchProductByProductCode :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_searchProductByProductCode :: " + e);
			}
		}
		
		function lp_focusBarCodeTxt(event){
			var x = event.which || event.keyCode;
			
			try{
				if(x==113){//F2
					$("#productCodeDis").val('');
					$("#productCodeDis").focus();
				}
			}catch(e){
				alert("lp_focusBarCodeTxt :: " + e);
			}
		}
		
		function lp_print(){
			
			try{
				
				$('#printSection').attr('src', gv_url + "?" + gv_service + "&pageAction=print&invoiceCode=" + $("#invoiceCode").val());
				
			}catch(e){
				alert("lp_print :: " + e);
			}
		}
		
		function lp_onchangeTin(){
			var la_productCode 	= null;
			var la_inventory 	= null;
			var params			= "";
			
			try{
				la_productCode		= document.getElementsByName("productCode");
				la_inventory		= document.getElementsByName("inventory");
				
				if($("#tin").val()==""){
					for(var i=0;i<la_inventory.length;i++){
						la_inventory[i].value = "0.00";
					}
					return;
				}
				
				params 	= "pageAction=getInventoryForProduct&" + $('#frm').serialize();
				
				$.ajax({
					async:true,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var flag				= null;
		            	
		            	try{
		            		//gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			flag	= jsonObj.flag;
		            			
		            			if(flag=="Y"){
		            				for(var i=0;i<la_productCode.length;i++){
		            					if(la_productCode[i].value==""){
		            						la_inventory[i].value = "0.00";
		            					}else{
		            						inventoryList 	= jsonObj.inventoryList;
		            						
		            						$.each(inventoryList, function(idx, obj) {
		            							if(obj.productCode==la_productCode[i].value){
		            								la_inventory[i].value = obj.inventory;
		            							}
		            						});
		            					}
		            				}
		            			}
		            			
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_onchangeTin :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_onchangeTin :: " + e);
			}
		}
		
	</script>
</head>
<body onkeydown="lp_focusBarCodeTxt(event);">
	<form id="frm" onsubmit="return false;">
		<input type="hidden" id="service" 	name="service" value="servlet.InvoiceCreditMaintananceServlet" />
		<input type="hidden" id="pageMode" 	name="pageMode" value="<%=pageMode%>" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=invoiceCreditMaintananceForm.getSeqTemp()%>" />
		<input type="hidden" id="systemVat" name="systemVat" value="<%=invoiceCreditMaintananceForm.VAT%>" />
		<input type="hidden" id="invoiceCash" name="invoiceCash" value="<%=invoiceCreditMasterBean.getInvoiceCash()%>" />
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
									<h4 class="alert-heading">บันทึกการขายต่างๆ</h4>
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
														เลขที่ใบเสร็จ <span style="color: red;"><b>*</b></span>:
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="invoiceCodeDis" 
								        					   name='invoiceCodeDis' 
								        					   value="<%=invoiceCreditMasterBean.getInvoiceCode()%>" 
								        					   maxlength="50" 
								        					   class="input-disabled"
							        					   	   readonly="readonly"
								        					   style="width: 220px;" />
								        				<input type="hidden" id="invoiceCode" name="invoiceCode" value="<%=invoiceCreditMasterBean.getInvoiceCode()%>" />
								        			</td>
								        			<td align="right">
														วันที่ขาย<span style="color: red;"><b>*</b></span>:
													</td>
								        			<td align="left" colspan="3">
								        				<%if(invoiceCreditMaintananceForm.getPageMode().equals(invoiceCreditMaintananceForm.NEW)){%>
								        				<input type='text' 
								        					   id="invoiceDate" 
								        					   name='invoiceDate' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onchange="gp_checkDate(this);"
								        					   value="<%=invoiceCreditMasterBean.getInvoiceDate()%>" 
								        					   style="width: 100px;" />
				   										<%}else{%>
				   										<input type='text' 
								        					   id="invoiceDate" 
								        					   name='invoiceDate'
								        					   value="<%=invoiceCreditMasterBean.getInvoiceDate()%>" 
								        					   style="width: 100px;" />
				   										<%}%>
								        			</td>
									        	</tr>
										    </table>
									        <div id="seasonTitle" class="padding-sm round-sm season-title-head2">
												<h6 class="panel-title" style="font-size:1.0em">รายละเอียดใบขาย</h6>
											</div>
					         				<table class="table user-register-table" style="border-bottom-color: white;">
					         					<tr>
					         						<td align="right">
								        				รหัสลูกค้า <span style="color: red;"><b>*</b></span> : &nbsp;
								        			</td>
								        			<td align="left" colspan="5">
								        				<input type='text' 
								        					   id="cusCodeDis" 
								        					   name='cusCodeDis' 
								        					   maxlength="10" 
								        					   style="width: 220px;"
								        					   onblur="getCustomerDetail();"
								        					   value="<%=customerDetailsBean.getCusCode() %>" />
								        				<input type="hidden" id="cusGroupCode" name="cusGroupCode" value="<%=customerDetailsBean.getCusGroupCode()%>" />
								        				<input type="hidden" id="groupSalePrice" name="groupSalePrice" value="<%=customerDetailsBean.getGroupSalePrice()%>" />
								        				<input type="hidden" id="cusCode" name="cusCode" value="<%=customerDetailsBean.getCusCode()%>" />
								        				<%if(invoiceCreditMaintananceForm.getPageMode().equals(invoiceCreditMaintananceForm.NEW)){%>
								        				<img alt="lookUp" title="lookUp" src="<%=imgURL%>/lookup.png" width="30px" height="30px" border="0" onclick="lp_lookUpCusDetail();" />
				   										<%}%>
								        				<span id="spanCusName" class="correct"><%=customerDetailsBean.getFullName()%></span>
								        			</td>
					         					</tr>
												<tr>
									        		<td align="right">
														พนักงานขาย :
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="saleName" 
								        					   name='saleName' 
								        					   onblur="getSaleNameDetail();"
								        					   value="<%=invoiceCreditMasterBean.getSaleName()%>"
								        					   style="width: 220px;" />
								        				<input type="hidden" id="saleUniqueId" name="saleUniqueId" value="<%=invoiceCreditMasterBean.getSaleUniqueId()%>" />
								        				<%if(invoiceCreditMaintananceForm.getPageMode().equals(invoiceCreditMaintananceForm.NEW)){%>
								        				<img alt="lookUp" title="lookUp" src="<%=imgURL%>/lookup.png" width="30px" height="30px" border="0" onclick="lp_lookUpSaleName();" />
				   										<%}%>
								        			</td>
								        			<td align="right">
														ค่าคอม :
													</td>
								        			<td align="left" colspan="3">
								        				<input type='text' 
								        					   id="saleCommission" 
								        					   name='saleCommission'
								        					   class="moneyOnly"
								        					   onblur="gp_checkAmtOnly(this, 12);"
								        					   value="<%=invoiceCreditMasterBean.getSaleCommission() %>" 
								        					   style="width: 220px;" />
								        			</td>
									        	</tr>
									        	<tr>
								        			<td align="right">
														บริษัทที่สังกัด<span style="color: red;"><b>*</b></span> :
													</td>
								        			<td align="left" colspan="3">
								        				<%if(invoiceCreditMaintananceForm.getPageMode().equals(invoiceCreditMaintananceForm.NEW)){%>
									        				<select id="tin" name="tin" style="width: 220px;" onchange="lp_onchangeTin();" >
									        					<% for(ComboBean comboBean:companyCombo){ %>
									        					<option value="<%=comboBean.getCode()%>" <%if(invoiceCreditMasterBean.getTin().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
									        					<%} %>
									        				</select>
				   										<%}else{%>
				   											<select id="tinDis" name="tinDis" style="width: 220px;" onchange="lp_onchangeTin();" >
									        					<% for(ComboBean comboBean:companyCombo){ %>
									        					<option value="<%=comboBean.getCode()%>" <%if(invoiceCreditMasterBean.getTin().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
									        					<%} %>
								        				</select>
								        				<input type="hidden" id="tin" name="tin" value="<%=invoiceCreditMasterBean.getTin()%>" />
				   										<%}%>
								        			</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														ประเภทราคา <span style="color: red;"><b>*</b></span> :
													</td>
								        			<td align="left" valign="middle">
								        				<span>
									        				<input  type="radio" 
																	id="invoiceType1" 
																	name="invoiceType" 
																	value="V" 
																	onclick="lp_calInvoiceVat();"
																	<%if(invoiceCreditMasterBean.getInvoiceType().equals("V")){%> checked="checked" <%} %> 
															/>
															มี VAT
														</span>
														<span style="margin-left: 5px;">
															<input  type="radio" 
																	id="invoiceType2" 
																	name="invoiceType" 
																	value="N" 
																	onclick="lp_calInvoiceVat();"
																	<%if(invoiceCreditMasterBean.getInvoiceType().equals("N")){%> checked="checked" <%} %> 
															/>
															ไม่มี VAT
														</span>
								        			</td>
								        			<td align="right">
														สถานะ :
													</td>
								        			<td align="left" colspan="3">
								        				<select id="invoiceStatus" name="invoiceStatus" style="width: 220px;" disabled="disabled" >
								        					<% for(ComboBean comboBean:invoiceStatusCombo){ %>
								        					<option value="<%=comboBean.getCode()%>" <%if(invoiceCreditMasterBean.getInvoiceStatus().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
								        					<%} %>
								        				</select>
								        			</td>
								        		</tr>
									        </table>
									        <div id="div_barcode"></div>
									        <div id="seasonTitle" class="padding-sm round-sm season-title-head2">
												<h6 class="panel-title" style="font-size:1.0em">รายการสินค้า</h6>
											</div>
											<div style="position:relative;left:10px;top: 5px;" align="left">
												<span>รหัสสินค้า : </span>
												<input type="text" 
													   style="width:220px;"
													   id="productCodeDis" 
													   name="productCodeDis"
													   maxlength="17"
													   onkeydown="readBarcode(event);"
													   value="" />
												<input type="button" id="btnSearch" name="btnSearch" class="btn btn-primary" value='ตกลง' onclick="lp_searchProductByProductCode();" />
											</div>
									        <table class="table sim-panel-result-table" id="resultData">
												<tr height="26px;">
													<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
													<th  style="text-align: center;" width="16%"><B>สินค้า</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="12%"><B>เหลือในคลัง</B></th>
													<th  style="text-align: center;" width="12%"><B>ปริมาณ</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="12%"><B>หน่วย</B></th>
													<th  style="text-align: center;" width="12%"><B>ราคาต่อหน่วย</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="12%"><B>ส่วนลด(%)</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="12%"><B>ราคาขาย</B><span style="color: red;"><b>*</b></span></th>
													<th style="text-align: center;" width="9%">Action</th>
												</tr> 
												<%
														int					  	seq		= 1;
													for(InvoiceCreditDetailBean bean:invoiceCreditDetailList){
														if(!bean.getRowStatus().equals(invoiceCreditMaintananceForm.DEL)){
													%>
												<tr>
													<td style="text-align:center">
														<%=seq%>
													</td>
													<td align="left">
														<input type="text" 
															   style="width: 100%"
															   id="productName<%=bean.getSeq()%>" 
															   name="productName"
															   onblur="lp_getProductDetailByName('<%=bean.getSeq()%>', this.value);"
															   value="<%=bean.getProductName()%>" />
														<input type="hidden" id="productCode<%=bean.getSeq()%>" name="productCode" value="<%=bean.getProductCode()%>" />
													</td>
													<td align="left">
														<input type="text" 
															   style="width: 100%"
															   id="inventory<%=bean.getSeq()%>" 
															   name="inventory" 
															   class="input-disabled"
															   readonly="readonly"
															   value="<%=bean.getInventory()%>" />
													</td>
													<td align="left">
														<input type='text' 
								        					   id="quantity<%=bean.getSeq()%>" 
								        					   name='quantity'
								        					   class="moneyOnly"
								        					   onblur="gp_checkAmtOnly(this, 12);lp_getDiscount('<%=bean.getSeq()%>');"
								        					   value="<%=bean.getQuantity() %>" 
								        					   style="width: 100%" />
													</td>
													<td align="left">
														<input type="text" 
															   style="width: 100%"
															   id="unitName<%=bean.getSeq()%>" 
															   name="unitName" 
															   class="input-disabled"
															   readonly="readonly"
															   value="<%=bean.getUnitName()%>" />
														<input type="hidden" id="unitCode<%=bean.getSeq()%>" name="unitCode" value="<%=bean.getUnitCode()%>" />
													</td>
													<td align="left">
														<input type='text' 
								        					   id="pricePerUnit<%=bean.getSeq()%>" 
								        					   name='pricePerUnit'
								        					   class="moneyOnly"
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calAmount('<%=bean.getSeq()%>');"
								        					   value="<%=bean.getPricePerUnit()%>" 
								        					   style="width: 100%" />
													</td>
													<td align="left">
														<input type='text' 
								        					   id="discount<%=bean.getSeq()%>" 
								        					   name='discount'
								        					   class="moneyOnly"
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calAmount('<%=bean.getSeq()%>');"
								        					   value="<%=bean.getDiscount()%>" 
								        					   style="width: 100%" />
													</td>
													<td align="left">
														<input type='text' 
								        					   id="price<%=bean.getSeq()%>" 
								        					   name='price'
								        					   class="moneyOnly"
								        					   onblur="gp_checkAmtOnly(this, 12);lp_invoicePrice();lp_updateRecord('<%=bean.getSeq()%>');"
								        					   value="<%=bean.getPrice()%>" 
								        					   style="width: 100%" />
													</td>
													<td align="center">
														<%if(invoiceCreditMaintananceForm.getPageMode().equals(invoiceCreditMaintananceForm.NEW)){%>
								        				<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, '<%=bean.getSeq()%>');" />
				   										<%}%>
														<input type="hidden" id="seq<%=bean.getSeq()%>" name="seq" value="<%=bean.getSeq()%>" />
													</td>
												</tr>
												<% seq++;}}%>
												<tr>
													<td colspan="8">&nbsp;</td>
													<td align="center">
														<%if(invoiceCreditMaintananceForm.getPageMode().equals(invoiceCreditMaintananceForm.NEW)){%>
								        				<img alt="เพิ่ม" title="เพิ่ม" src="<%=imgURL%>/Add.png" width="24" height="24" border="0" id="btnAdd" onclick="lp_newRecord(this, null);" />
														<img alt="รายการสินค้า" width="30px" height="30px" title="รายการสินค้า" id="btnZoom" src="<%=imgURL%>/zoom.png" border="0" />
				   										<%}%>
													</td>
												</tr>
											</table>
											<br/>
											<table class="table user-register-table" style="border-bottom-color: white;">
												<tr>
									        		<td align="right">
														รวมจำนวนเงิน<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="invoicePrice" 
								        					   name='invoicePrice' 
								        					   class="moneyOnly"
								        					   value="<%=invoiceCreditMasterBean.getInvoicePrice()%>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calInvoiceVat();"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														หักส่วนลด<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="invoicediscount" 
								        					   name='invoicediscount' 
								        					   class="moneyOnly"
								        					   value="<%=invoiceCreditMasterBean.getInvoicediscount()%>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calInvoiceVat();"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														VAT<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="invoiceVat" 
								        					   name='invoiceVat' 
								        					   class="moneyOnly"
								        					   value="<%=invoiceCreditMasterBean.getInvoiceVat()%>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);lp_InvoiceTotal();"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														หักมัดจำ<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="invoiceDeposit" 
								        					   name='invoiceDeposit' 
								        					   class="moneyOnly"
								        					   value="<%=invoiceCreditMasterBean.getInvoiceDeposit()%>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);lp_InvoiceTotal();"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														รวมทั้งสิ้น<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="invoiceTotal" 
								        					   name='invoiceTotal' 
								        					   class="moneyOnly"
								        					   value="<%=invoiceCreditMasterBean.getInvoiceTotal()%>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
								        		<tr>
									        		<td align="left">
									        			<table border="0" width="100%">
									        				<tr>
									        					<td width="10%" align="right" valign="top">หมายเหต :</td>
									        					<td width="90%" align="left">
									        						<textarea rows="3" style="width: 100%;" id="remark" name="remark">
											        					<%=invoiceCreditMasterBean.getRemark()%>
											        				</textarea>
									        					</td>
									        				</tr>
									        			</table>
													</td>
								        		</tr>
							        			<tr>
								        			<td align="right">
								        				<br/>
								        				<%if(invoiceCreditMaintananceForm.getPageMode().equals(invoiceCreditMaintananceForm.NEW)){%>
								        				<input type="button" id="btnSave" class="btn btn-sm btn-warning" value='บันทึก' onclick="lp_save();" />&nbsp;&nbsp;&nbsp;
				   										<input type="button" id="btnReset" onclick="lp_reset();" class="btn btn-sm btn-danger" value='เริ่มใหม่' />
				   										<%}else{%>
					   										<%if(!invoiceCreditMasterBean.getInvoiceStatus().equals("C")){ %>
						   										<input type="button" id="btnPrint" name="btnPrint" class="btn btn-sm btn-warning" value='พิมพ์' onclick="lp_print();" />&nbsp;&nbsp;&nbsp;
						   										<input type="button" id="btnCancel" name="btnCancel" onclick="lp_cancel();" class="btn btn-sm btn-danger" value='ยกเลิก' />
					   										<%}%>
				   										<%}%>
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
		<iframe name="printSection" 
				id="printSection"
				src="" 
				scrolling="yes"  
				frameborder="0" 
				width="0" 
				height="0">
		</iframe>
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