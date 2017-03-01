<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.ProductmasterBean"%>
<%@ page import="java.util.*,org.apache.commons.lang3.StringEscapeUtils"%>
<jsp:useBean id="multiManageProductForm" class="th.go.stock.app.enjoy.form.MultiManageProductForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String						titlePage		= "เพิ่มรายละเอียดสินค้า(หลายรายการ)";
	List<ProductmasterBean> 	productList		= multiManageProductForm.getProductList();
	boolean						chk				= multiManageProductForm.isChk();
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title><%=titlePage%></title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<style type="text/css">
		.scrollDetail {
	        height: 500px;
	        overflow-x: auto;
	        overflow-y: auto;
		}
	</style>
	<script>
		var gv_service 				= null;
		var gv_url 					= '<%=servURL%>/EnjoyGenericSrv';
		
		$(document).ready(function(){
			
			gv_service 		= "service=" + $('#service').val();
			
			if($("#chk").val()=="false"){
				$("#productTypeName").focus();
			}
			
			$('#resultData').fixedHeaderTable();
			
			$('input[type="file"]').ajaxfileupload({
				'action': gv_url + "?" + gv_service + "&pageAction=uploadFile",           
			   	'onComplete': function(response) {
			   			       lp_newRecordFromExcelFile(response);
						     },
			    'onStart': function() {
					    	  gp_progressBarOn();
					       },
				'validate_extensions' : true,
				'valid_extensions' : [ 'xls', 'xlsx' ],
			    'submit_button' :  $('#btnUpload')
			});
			
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
			
			$('#btnSearch').click(function(){ 
				var la_validate  = new Array("productTypeName:หมวดสินค้า"
											, "productGroupName:หมู่สินค้า"
											, "unitName:หน่วยสินค้า");
				try{
					
					if(!gp_validateEmptyObj(la_validate)){
						return false;
					}
					
					$.ajax({
						async:true,
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
			            			window.location.replace('<%=pagesURL%>/MultiManageProductScn.jsp');
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
				
		});
			
		function lp_validate(){
		    var la_productCodeDis	= null;
		    var la_productName		= null;
		    var lv_return			= true;
		    
			try{
				
				la_productCodeDis 	= document.getElementsByName("productCodeDis");
				la_productName 		= document.getElementsByName("productName");
				
				if(la_productCodeDis.length<=0){
					alert("กรุณาระบุรายการสินค้า");
	                return false;
				}
				
				for(var i=0;i<la_productCodeDis.length;i++){
					if(gp_trim(la_productCodeDis[i].value)==""){
						alert("กรุณาระบุรหัสสินค้า", function() { 
							la_productCodeDis[i].focus();
		    		    });
		                return false;
		            }else if(gp_trim(la_productName[i].value)==""){
						alert("กรุณาระบุชื่อสินค้า", function() { 
							la_productName[i].focus();
		    		    });
		                return false;
		            }
					
					if(!(gp_checkThaiLetter(gp_trim(la_productCodeDis[i].value)))){
						alert("รหัสสินค้าต้องเปนภาษาอังกฤษหรือตัวเลขเท่านั้น !!", function() { 
							//la_productCodeDis[i].value = "";
							la_productCodeDis[i].focus();
		    		    });
						return false;
					}
					
				}
				
				for(var i=0;i<la_productCodeDis.length;i++){
					for(var j=(i+1);j<la_productCodeDis.length;j++){
						if(gp_trim(la_productCodeDis[i].value)==gp_trim(la_productCodeDis[j].value)){
							alert("รหัสสินค้า " + la_productCodeDis[j].value + "มีอยู่ในระบบแล้วกรุณาระบุใหม่", function() { 
								la_productCodeDis[j].focus();
			    		    });
							return false;
						}else if(gp_trim(la_productName[i].value)==gp_trim(la_productName[j].value)){
							alert("ชื่อสินค้า " + la_productName[j].value + "มีอยู่ในระบบแล้วกรุณาระบุใหม่", function() { 
								la_productName[j].focus();
			    		    });
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
		
		function lp_newRecord(){
		    var lo_seqTemp  = document.getElementById("seqTemp");
		    var lv_maxSeq   = parseInt(lo_seqTemp.value) + 1;
			
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
		            			if(lp_addRowtable("", "", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", lv_maxSeq)){
		        					lo_seqTemp.value  = lv_maxSeq;
		        					$('#productGroupCode' + lv_maxSeq).focus();
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
		
		function lp_newRecordFromExcelFile(av_json){
		    var lo_seqTemp  = document.getElementById("seqTemp");
		    var status		= av_json.status;
		    
			try{
				if(status!="SUCCESS"){
					alert(av_json.errMsg);
					return;
				}
				
				$.each(av_json.productList, function(idx, obj) {
					//alert(obj.productName);
					lp_addRowtable(obj.productCodeDis
								 , obj.productName
								 , obj.minQuan
								 , obj.costPrice
								 , obj.salePrice1
								 , obj.salePrice2
								 , obj.salePrice3
								 , obj.salePrice4
								 , obj.salePrice5
								 , obj.quantity
								 , obj.seq);
    			});
				
				lo_seqTemp.value  = av_json.seqTemp;
				
				gp_progressBarOff();
				$('#productGroupCode' + av_json.seqTemp).focus();
			}catch(e){
				alert("lp_newRecordFromExcelFile :: " + e);
			}
		}
		
		function lp_addRowtable(av_productCodeDis
								, av_productName
								, av_minQuan
								, av_costPrice
								, av_salePrice1
								, av_salePrice2
								, av_salePrice3
								, av_salePrice4
								, av_salePrice5
								, av_quantity
								, av_seq){
			var lo_table                        = document.getElementById("resultData");
			var rowIndex                      	= lo_table.rows.length;//gp_rowTableIndex(ao_obj);
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
		    var newNodeTd10 					= null;
		    var newNodeTd11 					= null;
		    var newNodeTd12 					= null;
			
			try{
				
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
                newNodeTd10     = newNodeTr.insertCell(9);
                newNodeTd11     = newNodeTr.insertCell(10);
                newNodeTd12     = newNodeTr.insertCell(11);
                
                //ลำดับ
                newNodeTd1.align 			= "center";
                newNodeTd1.innerHTML        = rowIndex;
                
              	//รหัสสินค้า
               	newNodeTd2.innerHTML        = '<input type="text" maxlength="17" style="width: 100%;" onblur="lp_updateRecord('+av_seq+');" id="productCodeDis' + av_seq + '" name="productCodeDis" value="" />';
               	$("#productCodeDis"+av_seq).val(av_productCodeDis);
               	
              	//ชื่อสินค้า
               	newNodeTd3.innerHTML        = '<input type="text" maxlength="255" style="width: 100%" onblur="lp_updateRecord('+av_seq+');" id="productName' + av_seq + '" name="productName" value="" />';
               	$("#productName"+av_seq).val(av_productName);
               	
              	//ยอดต่ำสุดที่ต้องแจ้งเตือน
               	newNodeTd4.innerHTML        = '<input type="text" class="moneyOnly" style="width: 100%" onblur="gp_checkAmtOnly(this, 9);lp_updateRecord('+av_seq+');" id="minQuan' + av_seq + '" name="minQuan" value="" />';
               	$("#minQuan"+av_seq).val(av_minQuan);
               	
              	//ราคาทุน
               	newNodeTd5.innerHTML        = '<input type="text" class="moneyOnly" style="width: 100%" onblur="gp_checkAmtOnly(this, 11);lp_updateRecord('+av_seq+');" id="costPrice' + av_seq + '" name="costPrice" value="" />';
               	$("#costPrice"+av_seq).val(av_costPrice);
               	
              	//ราคาขาย 1
               	newNodeTd6.innerHTML        = '<input type="text" class="moneyOnly" style="width: 100%" onblur="gp_checkAmtOnly(this, 11);lp_updateRecord('+av_seq+');" id="salePrice1' + av_seq + '" name="salePrice1" value="" />';
               	$("#salePrice1"+av_seq).val(av_salePrice1);
               	
              	//ราคาขาย 2
               	newNodeTd7.innerHTML        = '<input type="text" class="moneyOnly" style="width: 100%" onblur="gp_checkAmtOnly(this, 11);lp_updateRecord('+av_seq+');" id="salePrice2' + av_seq + '" name="salePrice2" value="" />';
               	$("#salePrice2"+av_seq).val(av_salePrice2);
               	
              	//ราคาขาย 3
               	newNodeTd8.innerHTML        = '<input type="text" class="moneyOnly" style="width: 100%" onblur="gp_checkAmtOnly(this, 11);lp_updateRecord('+av_seq+');" id="salePrice3' + av_seq + '" name="salePrice3" value="" />';
               	$("#salePrice3"+av_seq).val(av_salePrice3);
               	
              	//ราคาขาย 4
               	newNodeTd9.innerHTML        = '<input type="text" class="moneyOnly" style="width: 100%" onblur="gp_checkAmtOnly(this, 11);lp_updateRecord('+av_seq+');" id="salePrice4' + av_seq + '" name="salePrice4" value="" />';
               	$("#salePrice4"+av_seq).val(av_salePrice4);
               	
              	//ราคาขาย 5
               	newNodeTd10.innerHTML        = '<input type="text" class="moneyOnly" style="width: 100%" onblur="gp_checkAmtOnly(this, 11);lp_updateRecord('+av_seq+');" id="salePrice5' + av_seq + '" name="salePrice5" value="" />';
               	$("#salePrice5"+av_seq).val(av_salePrice5);
               	
              	//จำนวน
               	newNodeTd11.innerHTML        = '<input type="text" class="moneyOnly" style="width: 100%" onblur="gp_checkAmtOnly(this, 12);lp_updateRecord('+av_seq+');" id="quantity' + av_seq + '" name="quantity" value="" />';
               	$("#quantity"+av_seq).val(av_quantity);
               	
              	//Action
              	newNodeTd12.align 			= "center";
               	newNodeTd12.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + av_seq + '\');" />'
											+ '<input type="hidden" id="seq'+av_seq+'" name="seq" value="'+av_seq+'" />';
               	
               	return true;
				
			}catch(e){
				alert("lp_addRowtable :: " + e);
				return false;
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
			var params			= "";
			
			try{
				
				params 	= gv_service 
						+ "&pageAction=updateRecord&seq=" + av_val 
						+ "&productCodeDis=" 	+ $("#productCodeDis" + av_val).val() 
						+ "&productName=" 	+ $("#productName" + av_val).val()
						+ "&minQuan=" 		+ $("#minQuan" + av_val).val()
						+ "&costPrice=" 	+ $("#costPrice" + av_val).val()
						+ "&salePrice1=" 	+ $("#salePrice1" + av_val).val()
						+ "&salePrice2=" 	+ $("#salePrice2" + av_val).val()
						+ "&salePrice3=" 	+ $("#salePrice3" + av_val).val()
						+ "&salePrice4=" 	+ $("#salePrice4" + av_val).val()
						+ "&salePrice5=" 	+ $("#salePrice5" + av_val).val()
						+ "&quantity=" 		+ $("#quantity" + av_val).val();
				
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
					async:true,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			alert("บันทึกเรียบร้อย", function() { 
		            				lp_reset();
				    		    });
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
		<input type="hidden" id="service" 	name="service" value="servlet.MultiManageProductServlet" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=multiManageProductForm.getSeqTemp()%>" />
		<input type="hidden" id="productTypeCode" name="productTypeCode" value="<%=multiManageProductForm.getProductTypeCode()%>" />
		<input type="hidden" id="productGroupCode" name="productGroupCode" value="<%=multiManageProductForm.getProductGroupCode()%>" />
		<input type="hidden" id="unitCode" name="unitCode" value="<%=multiManageProductForm.getUnitCode()%>" />
		<input type="hidden" id="chk" name="chk" value="<%=chk%>" />
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
						        			<td align="right">
						        				หมวดสินค้า<span style="color: red;"><b>*</b></span> :&nbsp;
						        			</td>
						        			<td align="left">
						        				<input type='text' 
						        					   id="productTypeName" 
						        					   name='productTypeName' 
						        					   maxlength="200" 
						        					   placeholder="หมวดสินค้า"  
						        					   onkeydown="lp_enterToSearch();"
						        					   value="<%=StringEscapeUtils.escapeHtml4(multiManageProductForm.getProductTypeName())%>" 
						        					   <%if(chk==true){%> class="input-disabled" readonly<%}%> />
						        			</td>
						        			<td align="right">
						        				หมู่สินค้า <span style="color: red;"><b>*</b></span> :&nbsp;
						        			</td>
						        			<td align="left">
						        				<input type='text' 
						        					   id="productGroupName" 
						        					   name='productGroupName' 
						        					   maxlength="200" 
						        					   placeholder="หมู่สินค้า"  
						        					   onkeydown="lp_enterToSearch();"
						        					   value="<%=StringEscapeUtils.escapeHtml4(multiManageProductForm.getProductGroupName())%>" 
						        					   <%if(chk==true){%> class="input-disabled" readonly<%}%> />
						        			</td>
						        		</tr>
						        		<tr>
						        			<td align="right">
						        				หน่วยสินค้า <span style="color: red;"><b>*</b></span> :&nbsp;
						        			</td>
						        			<td align="left" colspan="3">
						        				<input type='text' 
						        					   id="unitName" 
						        					   name='unitName' 
						        					   maxlength="200" 
						        					   placeholder="หน่วยสินค้า "  
						        					   onkeydown="lp_enterToSearch();"
						        					   value="<%=StringEscapeUtils.escapeHtml4(multiManageProductForm.getUnitName())%>" 
						        					   <%if(chk==true){%> class="input-disabled" readonly<%}%> />
						        			</td>
						        		</tr>
						        		<tr><td align="right" colspan="4">
						        				<input type="button" id="btnSearch" class='btn btn-primary padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา' <%if(chk==true){%>disabled<%}%> />
						        			</td>
						        		</tr>
						        	</table><br/>
						        	<%if(chk==true){%>
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em">รายการสินค้า</h6>
									</div>
				         			<div class="panel-body">
				         				<table width="100%" border="0">
											<tr>
												<td align="left" width="25%">
													<input type="file" name="datafile" id="datafile" width="100%" />
												</td>
												<td align="left" width="75%">
													<input type="button" name="btnUpload" class="btn btn-sm btn-warning" id="btnUpload" value="Upload" />
													<a href="<%=servURL%>/upload/Uploadproducts.xlsx">ตัวอย่างไฟล์อัพโหลด</a>
												</td>
											</tr>
										</table>
										<div class="scrollDetail">
					         				<table class="table sim-panel-result-table" id="resultData">
					         					<thead> 
													<tr height="26px;">
														<th  style="text-align: center;vertical-align: middle;" width="5%" ><B>ลำดับ</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>รหัสสินค้า</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>ชื่อสินค้า</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>ยอดแจ้งเตือน</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>ราคาทุน</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>ราคาขาย 1</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>ราคาขาย 2</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>ราคาขาย 3</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>ราคาขาย 4</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>ราคาขาย 5</B></th>
														<th  style="text-align: center;vertical-align: middle;" width="9%"><B>จำนวน</B></th>
														<th style="text-align: center;vertical-align: middle;" width="5%">
															<img alt="เพิ่ม" title="เพิ่ม" src="<%=imgURL%>/Add.png" width="24" height="24" border="0" onclick="lp_newRecord();" />
														</th>
													</tr> 
												</thead>
												<tbody>
													<%
		   											int					  	seq		= 1;
													for(ProductmasterBean bean:productList){
														if(!bean.getRowStatus().equals(multiManageProductForm.DEL)){
													%>
															<tr>
																<td style="text-align:center">
																	<%=seq%>
																</td>
																<td align="center">
																	<%=StringEscapeUtils.escapeHtml4(bean.getProductCodeDis())%>
																	<input type="hidden" id="productCodeDis<%=bean.getSeq()%>" name="productCodeDis" value="<%=StringEscapeUtils.escapeHtml4(bean.getProductCodeDis())%>" />
																</td>
																<td align="left">
																	<%=StringEscapeUtils.escapeHtml4(bean.getProductName())%>
																	<input type="hidden" id="productName<%=bean.getSeq()%>" name="productName" value="<%=StringEscapeUtils.escapeHtml4(bean.getProductName())%>" />
																</td>
																<td align="right">
																	<%=bean.getMinQuan()%>
																	<input type="hidden" id="minQuan<%=bean.getSeq()%>" name="minQuan" value="<%=bean.getMinQuan()%>" />
																</td>
																<td align="right">
																	<%=bean.getCostPrice()%>
																	<input type="hidden" id="costPrice<%=bean.getSeq()%>" name="costPrice" value="<%=bean.getProductName()%>" />
																</td>
																<td align="right">
																	<%=bean.getSalePrice1()%>
																	<input type="hidden" id="salePrice1<%=bean.getSeq()%>" name="salePrice1" value="<%=bean.getSalePrice1()%>" />
																</td>
																<td align="right">
																	<%=bean.getSalePrice2()%>
																	<input type="hidden" id="salePrice2<%=bean.getSeq()%>" name="salePrice2" value="<%=bean.getSalePrice2()%>" />
																</td>
																<td align="right">
																	<%=bean.getSalePrice3()%>
																	<input type="hidden" id="salePrice3<%=bean.getSeq()%>" name="salePrice3" value="<%=bean.getSalePrice3()%>" />
																</td>
																<td align="right">
																	<%=bean.getSalePrice4()%>
																	<input type="hidden" id="salePrice4<%=bean.getSeq()%>" name="salePrice4" value="<%=bean.getSalePrice4()%>" />
																</td>
																<td align="right">
																	<%=bean.getSalePrice5()%>
																	<input type="hidden" id="salePrice5<%=bean.getSeq()%>" name="salePrice5" value="<%=bean.getSalePrice5()%>" />
																</td>
																<td align="right">
																	<%=bean.getQuantity()%>
																	<input type="hidden" id="quantity<%=bean.getSeq()%>" name="quantity" value="<%=bean.getQuantity()%>" />
																</td>
																<td align="center">
																	<input type="hidden" id="seq<%=bean.getSeq()%>" name="seq" value="<%=bean.getSeq()%>" />
																</td>
															</tr>
													<% seq++;}}%>
												</tbody>
											</table>
										</div>
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