<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.ReciveOrderMasterBean,
				th.go.stock.app.enjoy.bean.ReciveOrdeDetailBean,
				th.go.stock.app.enjoy.bean.CompanyVendorBean,
				th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="reciveStockMaintananceForm" class="th.go.stock.app.enjoy.form.ReciveStockMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String 						pageMode 				= reciveStockMaintananceForm.getPageMode();
	ReciveOrderMasterBean 		reciveOrderMasterBean 	= reciveStockMaintananceForm.getReciveOrderMasterBean();
	List<ComboBean> 			statusCombo 			= reciveStockMaintananceForm.getStatusCombo();
	String						titlePage				= reciveStockMaintananceForm.getTitlePage();
	CompanyVendorBean 			companyVendorBean 		= reciveStockMaintananceForm.getCompanyVendorBean();
	List<ReciveOrdeDetailBean> 	reciveOrdeDetailList 	= reciveStockMaintananceForm.getReciveOrdeDetailList();


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
			
			if($("#pageMode").val()=="EDIT"){
				lp_setModeEdit();
			}else{
				lp_setModeNew();
			}
			
			/*$( "#provinceName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=p&provinceName=" + gp_trim(request.term),//request,
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
			
			
			$( "#districtName" ).autocomplete({
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=d&provinceName=" + gp_trim($( "#provinceName" ).val()) + "&districtName=" + gp_trim(request.term),//request,
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
			
			$( "#subdistrictName" ).autocomplete({
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=s&provinceName=" + gp_trim($( "#provinceName" ).val()) + "&districtName=" + gp_trim($( "#districtName" ).val()) + "&subdistrictName=" + gp_trim(request.term),//request,
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
			});*/
			
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
			
			$( "#vendorName" ).autocomplete({ 
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
			    	//เมื่อเลือก Data แล้ว
			    	//alert(ui.item.id);
			    	lp_getCompanyVendorDetail();
			      }
			});
			
			$( "#branchName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=branchNameList&branchName=" + gp_trim(request.term) + "&vendorName=" + $("#vendorName").val().trim(),//request,
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
			    	lp_getCompanyVendorDetail();
			      }
			});
			
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
					lo_iframe	= $("<iframe />").attr("src", "/stockWeb/EnjoyGenericSrv?service=servlet.ProductDetailsLookUpServlet&pageAction=new")
												 .attr("width", "100%")
												 .attr("height", "100%")
												 .attr("border", "0");
					
					gp_progressBarOn();
					lo_dialog.empty();
					lo_dialog.dialog("option", "title", "เลือกสินค้า");
					lo_dialog.append(lo_iframe).dialog( "open" );
					event.preventDefault();
				}catch(e){
					alert("btnZoom :: " + e);
				}
		    });
			
			gp_progressBarOff();
			
		});
		
		function lp_validate(){
			var la_validate             = new Array( "vendorName:บริษัท"	
													, "branchName:สาขา"
													, "reciveDate:วันที่สั่งซื้อ");
		    var lv_return				= true;
		    var la_reciveType			= null;
		    var la_priceType			= null;
		    //var provinceName			= "";
		    //var districtName			= "";
		    //var subdistrictName			= "";
		    var lv_reciveType			= "";
		    var lv_priceType			= "";
		    var la_productName			= null;
		    var la_productCode			= null;
		    var lv_currReciveStatus 	= "";
		    
			try{
				la_reciveType 		= document.getElementsByName("reciveType");
				la_priceType 		= document.getElementsByName("priceType");
				la_productName		= document.getElementsByName("productName");
				la_productCode		= document.getElementsByName("productCode");
				//provinceName		= $("#provinceName").val().trim();
				//districtName		= $("#districtName").val().trim();
				//subdistrictName		= $("#subdistrictName").val().trim();
				lv_currReciveStatus = $("#currReciveStatus").val().trim();
				
				for(var i=0;i<la_validate.length;i++){
					la_temp			= la_validate[i].split(":");
		            lo_obj          = eval('$("#' + la_temp[0] + '")');
		            
		            if(gp_trim(lo_obj.val())==""){
		            	alert("กรุณาระบุ " + la_temp[1]);
		            	lo_obj.focus();
		                return false;
		            }
		        }
				
				if(lv_currReciveStatus=="3" || lv_currReciveStatus=="4"){
					return true;
				}
				
				if($("#vendorCode").val().trim()==""){ 
					alert("ระบุรายละเอียดผู้จำหน่ายผิด");
	                return false;
				}
				
				/*Begin Check เลขประจำตัวผู้เสียภาษี*/
				/*if(gv_checkFormatIdNumber==false){
					alert("เลขประจำตัวผู้เสียภาษีผิด");
					$("#tin").focus();
	                return false;
				}*/
				/*End Check เลขประจำตัวผู้เสียภาษี*/
				
				/*Begin Check ประเภทใบสั่งซื้อ*/
				for(var i=0;i<la_reciveType.length;i++){
					if(la_reciveType[i].checked==true){
						lv_reciveType = la_reciveType[i].value;
						break;
					}
				}
				
				if(lv_reciveType==""){
					alert("กรุณาระบุประเภทใบสั่งซื้อ");
					return false;
				}
				
				if(lv_reciveType=="C" && $("#creditDay").val().trim()=="" && $("#creditExpire").val().trim()==""){
					alert("กรุณาระบุจำนวนวันและวันครบกำหนด");
					return false;
				}
				/*End Check ประเภทใบสั่งซื้อ*/
				
				/*Begin Check ประเภทราคา*/
				for(var i=0;i<la_priceType.length;i++){
					if(la_priceType[i].checked==true){
						lv_priceType = la_priceType[i].value;
						break;
					}
				}
				
				if(lv_priceType==""){
					alert("กรุณาระบุประเภทราคา");
					return false;
				}
				/*End Check ประเภทราคา*/
				
				/*Begin Check รายการสินค้า*/
				for(var i=0;i<la_productName.length;i++){
					if(la_productName[i].value.trim()==""){
						alert("กรุณาระบุสินค้า");
						la_productName[i].focus();
						return false;
					}else{
						if(la_productCode[i].value.trim()==""){
							alert("ระบุสินค้าชื่อสินค้าผิดกรุณาตรวจสอบ");
							la_productName[i].focus();
							return false;
						}
					}
				}
				/*End Check รายการสินค้า*/
				
				/*Begin Check จังหวัด อำเภอ/เขต และตำบล/แขวง*/
				/*if(provinceName=="" && districtName=="" && subdistrictName==""){
					$("#provinceCode").val("");
        			$("#districtCode").val("");
        			$("#subdistrictCode").val("");
					return true;
				}else{
					if(provinceName=="" || districtName=="" || subdistrictName==""){
						alert("กรุณาระบุจังหวัด อำเภอ/เขต และตำบล/แขวงให้ครบ");
						return false;
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
		            	var errType				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			$("#provinceCode").val(jsonObj.provinceCode);
		            			$("#districtCode").val(jsonObj.districtCode);
		            			$("#subdistrictCode").val(jsonObj.subdistrictCode);
		            			
		            			lv_return = true;
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			errType = jsonObj.errType;
		            			
		            			if(errType=="E"){
		            				alert(errMsg);
		            				lv_return = false;
		            			}else{
		            				$("#provinceCode").val(jsonObj.provinceCode);
			            			$("#districtCode").val(jsonObj.districtCode);
			            			$("#subdistrictCode").val(jsonObj.subdistrictCode);
		            				lv_return = confirm(errMsg);
		            			}
		            		}
		            	}catch(e){
		            		alert("in lp_validate :: " + e);
		            		lv_return = false;
		            	}
		            }
		        });*/
				/*End Check จังหวัด อำเภอ/เขต และตำบล/แขวง*/
				
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return lv_return;
		}
		
		function lp_onBlurPostCode(){
			
			var lo_postCode 		= null;//replaceComma
			
			try{
				lo_postCode 			= document.getElementById("postCode");
				
				if(gp_number(lo_postCode)==false){
					alert("กรุณาระบุตัวเลขเท่านั้น");
					lo_postCode.value = "";
					return;
				}
				
				if(gp_trim(lo_postCode.value)!="" && gp_trim(lo_postCode.value).length < 5){
					alert("ระบุได้รหัสไปรษณ๊ย์ผิด");
					$('#postCode').focus().select();
					return;
				}
				
			}catch(e){
				alert("lp_onBlurPostCode :: " + e);
			}
			
		}
		
		function lp_setModeEdit(){
			var lv_currReciveStatus = "";
			
			try{
				
				lv_currReciveStatus = $("#currReciveStatus").val().trim();
				
				if(lv_currReciveStatus!=1 && lv_currReciveStatus!=2){
					$("form").each(function(){
					    //$(this).find(':input').attr('readonly', true).attr('class', 'input-disabled');
						$(this).find('input:visible').prop("disabled", true);
					    
					});
					
					$("#remark").prop("disabled", true);
					
					$("#reciveDate").prop("disabled", false);
					$("#reciveStatus").prop("disabled", false);
					
					$("#btnSave").prop("disabled", false);
					//$("#btnReset").prop("disabled", false);
					 
					
					 
				}
				
				if(lv_currReciveStatus=="2"){
					$("#reciveStatus option[value='1']").remove();
				}else if(lv_currReciveStatus=="3"){
					$("#reciveStatus option[value='1']").remove();
					$("#reciveStatus option[value='2']").remove();
				}else if(lv_currReciveStatus=="4"){
					$("#reciveStatus option[value='1']").remove();
					$("#reciveStatus option[value='2']").remove();
					$("#reciveStatus option[value='3']").remove();
				}
				
				
			}catch(e){
				alert("lp_setModeEdit :: " + e);
			}
			
		}
		
		function lp_setModeNew(){
			try{
				$("#reciveStatus").prop("disabled", true);
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
		            			//window.location = gv_url + "?service=servlet.UserDetailsMaintananceServlet&pageAction=getUserDetail&userUniqueId=" + userUniqueId;
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
				if($("#pageMode").val()=="NEW"){
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
				}else{
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getDetail&reciveNo=" + $('#reciveNo').val();
				}
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
		            	var lv_inventory		= "";
		            	var lv_unitCode			= "";
		            	var lv_unitName			= "";
		            	
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
		                        	lv_inventory 	= ao_jsonObj.inventory;
		                        	lv_unitCode 	= ao_jsonObj.unitCode;
		                        	lv_unitName 	= ao_jsonObj.unitName;
		                        }
		                        
		                        //ลำดับ
		                        newNodeTd1.align 			= "center";
		                        newNodeTd1.innerHTML        = rowIndex;
		                        
		                      	//สินค้า
		                      	newNodeTd2.align 			= "left";
		                       	newNodeTd2.innerHTML        = '<input type="text" style="width: 77%" onblur="lp_getProductDetailByName(' + lv_maxSeq + ', this.value);lp_updateRecord('+lv_maxSeq+');" id="productName' + lv_maxSeq + '" name="productName" value="'+lv_productName+'" />'
		                       								+ '<img alt="lookUp" title="lookUp" src="<%=imgURL%>/lookup.png" width="20%" height="24" border="0" onclick="lp_comparePrice('+lv_maxSeq+');" />'
		                       								+ '<input type="hidden" id="productCode'+lv_maxSeq+'" name="productCode" value="'+lv_productCode+'" />';
		                       	
		                      	//เหลือในคลัง
		                       	newNodeTd3.innerHTML        = '<input type="text" style="width: 100%;" class="input-disabled" readonly="readonly" id="inventory' + lv_maxSeq + '" name="inventory" value="'+lv_inventory+'" />';
		                       	
		                      	//ปริมาณ
		                       	newNodeTd4.innerHTML        = '<input type="text" style="width: 100%"  onblur="gp_checkAmtOnly(this, 12);lp_calAmount('+lv_maxSeq+');" class="moneyOnly" id="quantity' + lv_maxSeq + '" name="quantity" value="0.00" />';
		                       	
		                      	//หน่วย
		                       	newNodeTd5.innerHTML        = '<input type="text" style="width: 100%;" id="unitName' + lv_maxSeq + '" class="input-disabled" readonly="readonly" name="unitName" value="'+lv_unitName+'" />'
   															+ '<input type="hidden" id="unitCode'+lv_maxSeq+'" name="unitCode" value="'+lv_unitCode+'" />';
   								
								//ราคาที่ซื้อ
		                       	newNodeTd6.innerHTML        = '<input type="text" style="width: 100%"  onblur="gp_checkAmtOnly(this, 12);lp_calAmount('+lv_maxSeq+');" class="moneyOnly" id="price' + lv_maxSeq + '" name="price" value="0.00" />';
		                       	
		                      	//ส่วนลด
		                       	newNodeTd7.innerHTML        = '<input type="text" style="width: 100%"  onblur="gp_checkAmtOnly(this, 12);lp_calAmount('+lv_maxSeq+');" class="moneyOnly" id="discountRate' + lv_maxSeq + '" name="discountRate" value="0.00" />';
		                       	
		                      	//จำนวนเงิน
		                       	newNodeTd8.innerHTML        = '<input type="text" style="width: 100%"  onblur="gp_checkAmtOnly(this, 12);lp_calReciveAmount();lp_updateRecord('+lv_maxSeq+');" class="moneyOnly" id="costPrice' + lv_maxSeq + '" name="costPrice" value="0.00" />';
		                       	
		                      	//Action
		                      	newNodeTd9.align 			= "center";
		                       	newNodeTd9.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + lv_maxSeq + '\');" />'
															+ '<input type="hidden" id="seq'+lv_maxSeq+'" name="seq" value="'+lv_maxSeq+'" />';
		                        
								lo_seqTemp.value  = lv_maxSeq;
								
								if(ao_jsonObj!=null){
									lp_updateRecord(lv_maxSeq);
								}
								
								$('#productName' + lv_maxSeq).focus();
								
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
															 + "&price=" 		+ $("#price" + av_val).val() .trim()
															 + "&discountRate=" + $("#discountRate" + av_val).val() .trim()
															 + "&costPrice=" 	+ $("#costPrice" + av_val).val() .trim();
				
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
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getProductDetailByName&productName=" + av_productName.trim(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			$("#productCode" + av_seq).val(jsonObj.productCode);
		            			//$("#productName" + av_seq).val(jsonObj.productName);
		            			$("#inventory" + av_seq).val(jsonObj.inventory);
		            			$("#unitCode" + av_seq).val(jsonObj.unitCode);
		            			$("#unitName" + av_seq).val(jsonObj.unitName);
		            			
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
		
		function lp_calAmount(av_seq){
			
			var lv_quantity 		= 0.00;
			var lv_price 			= 0.00;
			var lv_discountRate 	= 0.00;
			var lv_costPrice 		= 0.00;
			
			try{
				lv_quantity 	= gp_parseFloat($("#quantity" + av_seq).val());
				lv_price		= gp_parseFloat($("#price" + av_seq).val());
				lv_discountRate = gp_parseFloat($("#discountRate" + av_seq).val());
				
				if(lv_quantity > 0){
					lv_costPrice = (lv_quantity * lv_price) - lv_discountRate;
				}
				
				
				$("#costPrice" + av_seq).val(lv_costPrice);
				gp_format(document.getElementById("costPrice" + av_seq), 2);
				
				lp_calReciveAmount();
				
				lp_updateRecord(av_seq);
				
			}catch(e){
				alert("lp_calAmount :: " + e);
			}
		}
		
		function lp_calReciveAmount(){
			
			var lv_reciveAmount		= 0.00;
			
			try{
				
				$("input[name=costPrice]").each(function() {
					lv_reciveAmount += gp_parseFloat($(this).val());
				});
				
				$("#reciveAmount").val(lv_reciveAmount);
				gp_format(document.getElementById("reciveAmount"), 2);
				
				lp_calReciveVat();
				
			}catch(e){
				alert("lp_calReciveAmount :: " + e);
			}
		}
		
		function lp_calReciveVat(){
			
			var lv_reciveVat		= 0.00;
			var lv_reciveAmount		= 0.00;
			var lv_reciveDiscount	= 0.00;
			var lv_systemVat		= 0.00;
			
			try{
				lv_systemVat		= gp_parseFloat($("#systemVat").val());
				lv_reciveAmount 	= gp_parseFloat($("#reciveAmount").val());
				lv_reciveDiscount 	= gp_parseFloat($("#reciveDiscount").val());
				lv_reciveVat		= ((lv_reciveAmount - lv_reciveDiscount) * lv_systemVat)/100;
				
				$("#reciveVat").val(lv_reciveVat);
				gp_format(document.getElementById("reciveVat"), 2);
				
				lp_calReciveTotal();
				
			}catch(e){
				alert("lp_calReciveVat :: " + e);
			}
		}
		
		function lp_calReciveTotal(){
			
			var lv_reciveVat		= 0.00;
			var lv_reciveAmount		= 0.00;
			var lv_reciveDiscount	= 0.00;
			var lv_reciveTotal		= 0.00;
			
			try{
				
				lv_reciveAmount 	= gp_parseFloat($("#reciveAmount").val());
				lv_reciveDiscount 	= gp_parseFloat($("#reciveDiscount").val());
				lv_reciveVat 		= gp_parseFloat($("#reciveVat").val());
				lv_reciveTotal		= (lv_reciveAmount - lv_reciveDiscount) + lv_reciveVat;
				
				$("#reciveTotal").val(lv_reciveTotal);
				gp_format(document.getElementById("reciveTotal"), 2);
				
			}catch(e){
				alert("lp_calReciveTotal :: " + e);
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
			            data: gv_service + "&pageAction=getProductDetailByName&productName=" + obj.productName.trim(),
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
		
		function lp_getCompanyVendorDetail(){
			
			var vendorName = "";
			var branchName = "";
			
			try{
				
				vendorName = $("#vendorName").val().trim();
				branchName = $("#branchName").val().trim();
				
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
		            			
		            			$("#vendorCode").val(jsonObj.vendorCode);
		            			$("#vendorName").val(jsonObj.vendorName);
		            			$("#branchName").val(jsonObj.branchName);
		            			$("#buildingName").val(jsonObj.buildingName);
		            			$("#houseNumber").val(jsonObj.houseNumber);
		            			$("#mooNumber").val(jsonObj.mooNumber);
		            			$("#soiName").val(jsonObj.soiName);
		            			$("#streetName").val(jsonObj.streetName);
		            			$("#provinceCode").val(jsonObj.provinceCode);
		            			$("#districtCode").val(jsonObj.districtCode);
		            			$("#subdistrictCode").val(jsonObj.subdistrictCode);
		            			$("#subdistrictName").val(jsonObj.subdistrictName);
		            			$("#districtName").val(jsonObj.districtName);
		            			$("#provinceName").val(jsonObj.provinceName);
		            			$("#postcode").val(jsonObj.postcode);
		            			$("#tel").val(jsonObj.tel);
		            			$("#fax").val(jsonObj.fax);
		            			$("#email").val(jsonObj.email);
		            			$("#remark").val(jsonObj.remark);
		            			
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
		
		function lp_ctrllReciveType(){
			
			var la_reciveType = null;
			
			try{
				la_reciveType = document.getElementsByName("reciveType");
				
				for(var i=0;i<la_reciveType.length;i++){
					if(la_reciveType[i].checked==true){
						if(la_reciveType[i].value=="M"){
							
							$('#creditDay').val('');
							$('#creditExpire').val('');
							
							$('#creditDay').attr('readonly', true);
							$('#creditExpire').attr('readonly', true);
							
							$("#creditDay").attr('class', 'input-disabled');
							$("#creditExpire").attr('class', 'input-disabled');
							
							break;
						}else{
							$('#creditDay').attr('readonly', false);
							$('#creditExpire').attr('readonly', false);
							
							$("#creditDay").attr('class', '');
							$("#creditExpire").attr('class', '');
							break;
						}
					}
				}
				
				
			}catch(e){
				alert("lp_ctrllReciveType :: " + e);
			}
		}
		
		function lp_ctrlCreditDay(){
			
			try{
				
				if($("#creditDay").val().trim()==""){
					return;
				}
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=ctrlCreditDay&creditDay=" + $("#creditDay").val().trim(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			$("#creditExpire").val(jsonObj.creditExpire);
		            			
		            			
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			alert(errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_ctrlCreditDay :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_ctrlCreditDay :: " + e);
			}
		}
		
		function lp_comparePrice(av_seq){
			
			var lo_dialog 			= null;
			var lo_iframe 			= null;
			var lv_productCode 		= "";
			var lv_productName 		= "";
			var lv_param			= "";
			
			try{
				
				lv_productCode = $("#productCode" + av_seq).val().trim();
				lv_productName = $("#productName" + av_seq).val().trim();
				
				if(lv_productName==""){
					alert("กรุณาระบุสินค้า");
					return;
				}
				
				if(lv_productCode==""){
					alert("สินค้าที่ระบุไม่มีอยู่จริงในระบบ");
					return;
				}
				
				lv_param = "&productCode=" + lv_productCode + "&productName=" + lv_productName;
				
				lo_dialog 	= $( "#dialog" );
				lo_iframe	= $("<iframe />").attr("src", "/stockWeb/EnjoyGenericSrv?service=servlet.ComparePriceServlet&pageAction=lookup" + lv_param)
											 .attr("width", "100%")
											 .attr("height", "100%")
											 .attr("border", "0");
				
				gp_progressBarOn();
				lo_dialog.empty();
				
				lo_dialog.dialog("option", "title", "เปรียบเทียบราคา" + lv_productName);
				
				lo_dialog.append(lo_iframe).dialog( "open" );
				event.preventDefault();
			}catch(e){
				alert("lp_comparePrice :: " + e);
			}
		}
		
		/*function lp_checkIdNumber(){
			
			var lv_tin 		= null;
			
			try{
				lv_tin = gp_trim($("#tin").val());
				
				$("#inValidSpan").html("");
				
				if(lv_tin==""){
					gv_checkFormatIdNumber = true;
					return;
				}
				
				if(!gp_validatePin(lv_tin)){
					$("#inValidSpan").css("color", "red");
    				$("#inValidSpan").html("เลขประจำตัวผู้เสียภาษีผิด");
    				
    				gv_checkFormatIdNumber = false;
	                return;
				}else{
					gv_checkFormatIdNumber = true;
				}
				
				$("#tin").val(lv_tin);
				
				
			}catch(e){
				alert("lp_checkIdNumber :: " + e);
			}
		}*/


	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.ReciveStockMaintananceServlet" />
		<input type="hidden" id="pageMode" 	name="pageMode" value="<%=pageMode%>" />
		<input type="hidden" id="provinceCode" name="provinceCode" value="" />
		<input type="hidden" id="districtCode" name="districtCode" value="" />
		<input type="hidden" id="subdistrictCode" name="subdistrictCode" value="" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=reciveStockMaintananceForm.getSeqTemp()%>" />
		<input type="hidden" id="currReciveStatus" name="currReciveStatus" value="<%=reciveOrderMasterBean.getReciveStatus()%>" />
		<input type="hidden" id="vendorCode" name="vendorCode" value="<%=companyVendorBean.getVendorCode()%>" />
		<input type="hidden" id="systemVat" name="systemVat" value="<%=reciveStockMaintananceForm.VAT%>" />
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
								<h4 class="alert-heading">ดูแลจัดการ Stock สินค้า</h4>
							</div>					          	
							<div class="container main-container round-sm padding-xl-h">
								<div class="col-sm-12 toppad" >
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em"><%=titlePage%></h6>
									</div>
				         			<div class="panel-body">
							            <div id="seasonTitle" class="padding-sm round-sm season-title-head2">
											<h6 class="panel-title" style="font-size:1.0em">ผู้จำหน่าย</h6>
										</div>
				            			<table class="table user-register-table" style="border-bottom-color: white;">
												<tr>
									        		<td align="right">
														บริษัท<span style="color: red;"><b>*</b></span> :
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="vendorName" 
								        					   name='vendorName' 
								        					   value="<%=companyVendorBean.getVendorName() %>" 
								        					   maxlength="100" 
								        					   onblur="lp_getCompanyVendorDetail();"
								        					   style="width: 220px;" />
								        			</td>
								        			<td align="right">
														สาขา<span style="color: red;"><b>*</b></span> :
													</td>
								        			<td align="left" colspan="3">
								        				<input type='text' 
								        					   id="branchName" 
								        					   name='branchName' 
								        					   value="<%=companyVendorBean.getBranchName() %>" 
								        					   maxlength="30" 
								        					   onblur="lp_getCompanyVendorDetail();"
								        					   style="width: 220px;" />
								        			</td>
									        	</tr>
									        	<tr>
								        			<td align="right">
														เลขประจำตัวผู้เสียภาษี :
													</td>
								        			<td align="left" colspan="5">
								        				<input  type="text" 
								        						id="tin" 
								        						name="tin" 
								        						class="numberOnly"
								        						style="width: 200px;"
								        						disabled="disabled"
								        						onblur="lp_checkIdNumber();"
								        						value="<%=companyVendorBean.getTin()%>" 
								        						maxlength="13"  />
								        				&nbsp;
								        				<span id="inValidSpan"></span>
								        			</td>
									        	</tr>
								        		<tr>
													<td align="right">
														บ้านเลขที่ :
													</td>
													<td align="left">
														<input  type="text"
																id="houseNumber" 
																name="houseNumber"
																disabled="disabled"
																value="<%=companyVendorBean.getHouseNumber() %>" />
													</td>
													<td align="right">
														หมู่ที่:
													</td>
													<td align="left" colspan="3">
														<input type="text"
															   id="mooNumber" 
															   name="mooNumber"
															   disabled="disabled"
															   value="<%=companyVendorBean.getMooNumber() %>" />
													</td>
												</tr>
								        		<tr>
													<td align="right">
														อาคาร:
													</td>
													<td colspan="5" align="left" >
														<input  type="text" 
																id="buildingName" 
																name="buildingName"
																disabled="disabled"
																style="width: 220px;"
																value="<%=companyVendorBean.getBuildingName()%>"
														/>
													</td>													
												</tr>
												<tr>
													<td align="right">
														ตรอกซอย:
													</td>
													<td align="left" >
														<input  type="text" 
																id="soiName" 
																name="soiName"
																disabled="disabled"
																style="width: 220px;"
																value="<%=companyVendorBean.getSoiName() %>"
														/>
													</td>
													<td align="right">
														ถนน:
													</td>
													<td align="left" colspan="3">
														<input  type="text" 
																id="streetName" 
																name="streetName"
																maxlength="250" 
																disabled="disabled"
																style="width: 220px;"
																value="<%=companyVendorBean.getStreetName() %>"
														/>
													</td>
												</tr>
												<tr>
													<td align="right">
														จังหวัด :
													</td>
													<td align="left" >
														<input  type="text" 
																id="provinceName" 
																name="provinceName"
																placeholder="จังหวัด"
																disabled="disabled"
																style="width: 220px;"
																value="<%=companyVendorBean.getProvinceName() %>"
														/>
													</td>
													<td align="right">
														อำเภอ/เขต :
													</td>
													<td align="left" colspan="3">
														<input  type="text"
																id="districtName" 
																name="districtName"
																placeholder="อำเภอ"
																disabled="disabled"
																style="width: 220px;"
																value="<%=companyVendorBean.getDistrictName() %>"
														/>
													</td>
												</tr>
								        		<tr>
													<td align="right">
														ตำบล/แขวง :
													</td>
													<td align="left" >
														<input  type="text"
																	id="subdistrictName" 
																	name="subdistrictName"
																	placeholder="ตำบล"
																	disabled="disabled"
																	style="width: 220px;"
																	value="<%=companyVendorBean.getSubdistrictName() %>"
															/>
													</td>
													<td align="right">
														รหัสไปรษณ๊ย์:
													</td>
													<td align="left" colspan="3">
														<input  type="text" 
																size="7"
																id="postCode" 
																name="postCode"
																maxlength="5"
																disabled="disabled"
																placeholder="รหัสไปรษณ๊ย์"
																onchange="lp_onBlurPostCode();"
																value="<%=companyVendorBean.getPostCode() %>"
														/>
													</td>
												</tr>
								        		<tr>
								        			<td align="right">
								        				เบอร์โทร :
								        			</td>
								        			<td align="left">
								        				<input  type="text" 
								        						id="tel" 
								        						name="tel" 
								        						class="telOnly"
								        						disabled="disabled"
								        						style="width: 220px;"
								        						value="<%=companyVendorBean.getTel()%>" 
								        						maxlength="50"  />
								        			</td>
								        			<td align="right">
								        				เบอร์ Fax :
								        			</td>
								        			<td align="left">
								        				<input  type="text" 
								        						id="fax" 
								        						name="fax" 
								        						class="telOnly"
								        						disabled="disabled"
								        						style="width: 220px;"
								        						value="<%=companyVendorBean.getFax()%>" 
								        						maxlength="50" />
								        			</td>
								        			<td align="right">
								        				E-mail :
								        			</td>
								        			<td align="left">
								        				<input  type="text" 
								        						id="email" 
								        						name="email" 
								        						disabled="disabled"
								        						style="width: 220px;"
								        						value="<%=companyVendorBean.getEmail()%>" 
								        						maxlength="200"  />
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				หมายเหต :
								        			</td>
								        			<td align="left" colspan="5">
								        				<textarea rows="4" style="width: 500px;" id="remark" name="remark" disabled="disabled" >
								        					<%=companyVendorBean.getRemark()%>
								        				</textarea>
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				เลขที่บิล :
								        			</td>
								        			<td align="left" colspan="5">
								        				<input  type="text" 
								        						id="billNo" 
								        						name="billNo"
								        						style="width: 220px;"
								        						value="<%=reciveOrderMasterBean.getBillNo()%>" 
								        						maxlength="50" />
								        			</td>
								        		</tr>
									        </table>
									        <div id="seasonTitle" class="padding-sm round-sm season-title-head2">
												<h6 class="panel-title" style="font-size:1.0em">รายละเอียดใบสั่งซื้อ</h6>
											</div>
					         				<table class="table user-register-table" style="border-bottom-color: white;">
												<tr>
									        		<td align="right">
														เลขที่ใบสั่งซื้อ <span style="color: red;"><b>*</b></span>:
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="reciveNo" 
								        					   name='reciveNo' 
								        					   value="<%=reciveOrderMasterBean.getReciveNo() %>" 
								        					   maxlength="50" 
								        					   class="input-disabled"
							        					   	   readonly="readonly"
								        					   style="width: 220px;" />
								        			</td>
								        			<td align="right">
														วันที่สั่งซื้อ<span style="color: red;"><b>*</b></span>:
													</td>
								        			<td align="left" colspan="3">
								        				<input type='text' 
								        					   id="reciveDate" 
								        					   name='reciveDate' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onchange="gp_checkDate(this);"
								        					   value="<%=reciveOrderMasterBean.getReciveDate() %>" 
								        					   style="width: 100px;" />
								        			</td>
									        	</tr>
									        	<tr>
									        		<td></td>
								        			<td align="left" valign="middle">
								        				<span>
									        				<input  type="radio" 
																	id="reciveType1" 
																	name="reciveType" 
																	value="M" 
																	onclick="lp_ctrllReciveType();"
																	<%if(reciveOrderMasterBean.getReciveType().equals("M")){%> checked="checked" <%} %> 
															/>
															เงินสด
														</span>
														<span style="margin-left: 5px;">
															<input  type="radio" 
																	id="reciveType2" 
																	name="reciveType" 
																	value="C" 
																	onclick="lp_ctrllReciveType();"
																	<%if(reciveOrderMasterBean.getReciveType().equals("C")){%> checked="checked" <%} %> 
															/>
															เครดิต<span style="color: red;"><b>*</b></span>
														</span>
														<span style="margin-left: 50px;">
															วัน :
															<input  type="text" 
									        						id="creditDay" 
									        						name="creditDay" 
									        						class="numberOnly"
									        						style="width: 50px;"
									        						onblur="lp_ctrlCreditDay();"
									        						value="<%=reciveOrderMasterBean.getCreditDay()%>" 
									        						maxlength="5"  />
								        				</span>
								        			</td>
								        			<td align="right">
														วันครบกำหนด :
													</td>
								        			<td align="left" colspan="3">
								        				<%if(!reciveOrderMasterBean.getReciveStatus().equals("1") && !reciveOrderMasterBean.getReciveStatus().equals("2")){%>
															<input type='text' 
								        					   id="creditExpire" 
								        					   name='creditExpire'
								        					   value="<%=reciveOrderMasterBean.getCreditExpire() %>" 
								        					   style="width: 100px;" />
														<%}else{ %>
															<input type='text' 
								        					   id="creditExpire" 
								        					   name='creditExpire' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onchange="gp_checkDate(this);"
								        					   value="<%=reciveOrderMasterBean.getCreditExpire() %>" 
								        					   style="width: 100px;" />
														<%} %>
								        			</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														ประเภทราคา <span style="color: red;"><b>*</b></span> :
													</td>
								        			<td align="left" valign="middle">
								        				<span>
									        				<input  type="radio" 
																	id="priceType1" 
																	name="priceType" 
																	value="V" 
																	<%if(reciveOrderMasterBean.getPriceType().equals("V")){%> checked="checked" <%} %> 
															/>
															มี VAT
														</span>
														<span style="margin-left: 5px;">
															<input  type="radio" 
																	id="priceType2" 
																	name="priceType" 
																	value="N" 
																	<%if(reciveOrderMasterBean.getPriceType().equals("N")){%> checked="checked" <%} %> 
															/>
															ไม่มี VAT
														</span>
								        			</td>
								        			<td align="right">
														สถานะใบสั่งซื้อ<span style="color: red;"><b>*</b></span> :
													</td>
								        			<td align="left" colspan="3">
								        				<select id="reciveStatus" name="reciveStatus" style="width: 220px;" >
									        					<% for(ComboBean comboBean:statusCombo){ %>
									        					<option value="<%=comboBean.getCode()%>" <%if(reciveOrderMasterBean.getReciveStatus().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
									        					<%} %>
									        				</select>
								        			</td>
								        		</tr>
									        </table>
									        <div id="seasonTitle" class="padding-sm round-sm season-title-head2">
												<h6 class="panel-title" style="font-size:1.0em">รายการสินค้า</h6>
											</div>
									        <table class="table sim-panel-result-table" id="resultData">
												<tr height="26px;">
													<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
													<th  style="text-align: center;" width="16%"><B>สินค้า</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="12%"><B>เหลือในคลัง</B></th>
													<th  style="text-align: center;" width="12%"><B>ปริมาณ</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="12%"><B>หน่วย</B></th>
													<th  style="text-align: center;" width="12%"><B>ราคาที่ซื้อ</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="12%"><B>ส่วนลด</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="12%"><B>จำนวนเงิน</B><span style="color: red;"><b>*</b></span></th>
													<th style="text-align: center;" width="9%">Action</th>
												</tr> 
												<%
 													int					  	seq		= 1;
													for(ReciveOrdeDetailBean bean:reciveOrdeDetailList){
														if(!bean.getRowStatus().equals(reciveStockMaintananceForm.DEL)){
 												%>
												<tr>
													<td style="text-align:center">
														<%=seq%>
													</td>
													<td align="left">
														<input type="text" 
															   style="width: 77%"
															   id="productName<%=bean.getSeq()%>" 
															   name="productName"
															   onblur="lp_getProductDetailByName('<%=bean.getSeq()%>', this.value);lp_updateRecord('<%=bean.getSeq()%>');"
															   value="<%=bean.getProductName()%>" />
														<img alt="lookUp" title="lookUp" src="<%=imgURL%>/lookup.png" width="20%" height="24" border="0" onclick="lp_comparePrice(<%=bean.getSeq()%>);" />
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
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calAmount('<%=bean.getSeq()%>');"
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
								        					   id="price<%=bean.getSeq()%>" 
								        					   name='price'
								        					   class="moneyOnly"
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calAmount('<%=bean.getSeq()%>');"
								        					   value="<%=bean.getPrice()%>" 
								        					   style="width: 100%" />
													</td>
													<td align="left">
														<input type='text' 
								        					   id="discountRate<%=bean.getSeq()%>" 
								        					   name='discountRate'
								        					   class="moneyOnly"
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calAmount('<%=bean.getSeq()%>');"
								        					   value="<%=bean.getDiscountRate()%>" 
								        					   style="width: 100%" />
													</td>
													<td align="left">
														<input type='text' 
								        					   id="costPrice<%=bean.getSeq()%>" 
								        					   name='costPrice'
								        					   class="moneyOnly"
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calReciveAmount();lp_updateRecord('<%=bean.getSeq()%>');"
								        					   value="<%=bean.getCostPrice()%>" 
								        					   style="width: 100%" />
													</td>
													<td align="center">
														<%if(!reciveOrderMasterBean.getReciveStatus().equals("1") && !reciveOrderMasterBean.getReciveStatus().equals("2")){%>
															<img src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" />
														<%}else{ %>
															<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, '<%=bean.getSeq()%>');" />
														<%} %>
														<input type="hidden" id="seq<%=bean.getSeq()%>" name="seq" value="<%=bean.getSeq()%>" />
													</td>
												</tr>
												<% seq++;}}%>
												<tr>
													<td colspan="8">&nbsp;</td>
													<td align="center">
														<%if(!reciveOrderMasterBean.getReciveStatus().equals("1") && !reciveOrderMasterBean.getReciveStatus().equals("2")){%>
															<img src="<%=imgURL%>/Add.png" width="24" height="24" border="0" id="disBtnAdd" disabled="true"  />
															<img width="30px" height="30px" id="disBtnZoom" src="<%=imgURL%>/zoom.png" border="0" disabled="true" />
														<%}else{ %>
															<img alt="เพิ่ม" title="เพิ่ม" src="<%=imgURL%>/Add.png" width="24" height="24" border="0" id="btnAdd" onclick="lp_newRecord(this, null);" />
															<img alt="รายการสินค้า" width="30px" height="30px" title="รายการสินค้า" id="btnZoom" src="<%=imgURL%>/zoom.png" border="0" />
														<%} %>
													</td>
												</tr>
											</table>
											<br/>
											<table class="table user-register-table" style="border-bottom-color: white;">
												<tr>
									        		<td align="right">
														รวมจำนวนเงิน<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="reciveAmount" 
								        					   name='reciveAmount' 
								        					   class="moneyOnly"
								        					   value="<%=reciveOrderMasterBean.getReciveAmount() %>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calReciveVat();"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														หักเงินมัดจำ<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="reciveDiscount" 
								        					   name='reciveDiscount' 
								        					   class="moneyOnly"
								        					   value="<%=reciveOrderMasterBean.getReciveDiscount()%>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calReciveVat();"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														VAT<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="reciveVat" 
								        					   name='reciveVat' 
								        					   class="moneyOnly"
								        					   value="<%=reciveOrderMasterBean.getReciveVat()%>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);lp_calReciveTotal();"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														รวมทั้งสิ้น<span style="color: red;"><b>*</b></span> :
														<input type='text' 
								        					   id="reciveTotal" 
								        					   name='reciveTotal' 
								        					   class="moneyOnly"
								        					   value="<%=reciveOrderMasterBean.getReciveTotal()%>" 
								        					   maxlength="50" 
								        					   onblur="gp_checkAmtOnly(this, 12);"
								        					   style="width: 220px;" />
								        				<span>&nbsp;บาท</span>
													</td>
								        		</tr>
							        			<tr>
								        			<td align="right" colspan="2">
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