<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.AdjustStockBean, th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="adjustStockForm" class="th.go.stock.app.enjoy.form.AdjustStockForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String						titlePage					= "ปรับยอดสต๊อกสินค้า (Adjust Stock)";
	List<AdjustStockBean> 		adjustHistoryListList		= adjustStockForm.getAdjustStockList();
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
			gv_service 		= "service=" + $('#service').val();
			
			$( "#btnZoom" ).live("click", function(event){
				try{
					gp_dialogPopUp("/stockWeb/EnjoyGenericSrv?service=servlet.ProductDetailsLookUpServlet&pageAction=new", "เลือกสินค้า");
					//call back lp_returnProduct
				}catch(e){
					alert("btnZoom :: " + e);
				}
		    });
			
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
		});
		
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
		
		function lp_getProductDetailByName(av_seq, av_productName){
			var params = "";
			
			try{
				params = "productName=" + av_productName.trim();
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=getProductDetailByName&" + params,
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
			            			$("#quanOld" + av_seq).val('0.00');
			            			$("#quantity" + av_seq).val('0.00');
			            			$("#unitName" + av_seq).val('');
		            			}else{
		            				$("#productCode" + av_seq).val(jsonObj.productCode);
			            			$("#quanOld" + av_seq).val(jsonObj.quanOld);
			            			$("#quantity" + av_seq).val(jsonObj.quantity);
			            			$("#unitName" + av_seq).val(jsonObj.unitName);
		            			}
		            			
		            			lp_updateRecord(av_seq);
		            			
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
		            		//gp_progressBarOff();
		            		
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
		
		function lp_validate(){
		    var lv_return			= true;
		    var la_productName		= null;
		    var la_productCode		= null;
		    var la_quanNew			= null;
		    var lv_quanNew			= 0.00;
		    
			try{
				la_productName	= document.getElementsByName("productName");
				la_productCode	= document.getElementsByName("productCode");
				la_quanNew		= document.getElementsByName("quanNew");
				
				/*Begin Check รายการสินค้า*/
				for(var i=0;i<la_productName.length;i++){
					if(la_productName[i].value.trim()==""){
						alert("กรุณาระบุสินค้า", function() { 
							la_productName[i].focus();
		    		    });
						return false;
					}else{
						if(la_productCode[i].value.trim()==""){
							alert("ระบุสินค้าชื่อสินค้าผิดกรุณาตรวจสอบ", function() { 
								la_productName[i].focus();
			    		    });
							return false;
						}else{
							for(var j=(i+1);j<la_productCode.length;j++){
								if(la_productCode[i].value.trim()==la_productCode[j].value.trim()){
									alert("สินค้า"+la_productName[i].value+"ซ้ำ", function() { 
										la_productName[i].focus();
					    		    });
									return false;
								}
							}
							
							lv_quanNew = gp_parseFloat(la_quanNew[i].value);
							if(lv_quanNew == 0){
								alert(la_productName[i].value + "ไม่มีการเพิ่ม/ลด สินค้า", function() { 
									la_quanNew[i].focus();
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
		
		function lp_calQuantity(av_seq){
			
			var lv_quanOld 		= 0.00;
			var lv_quanNew 		= 0.00;
			var lv_quantity 	= 0.00;
			
			try{
				lv_quanOld 		= gp_parseFloat($("#quanOld" + av_seq).val());
				lv_quanNew 		= gp_parseFloat($("#quanNew" + av_seq).val());
				lv_quantity 	= lv_quanOld + lv_quanNew;
				
				$("#quantity" + av_seq).val(gp_format_str(lv_quantity.toString(), 2));
				
				lp_updateRecord(av_seq);
				
			}catch(e){
				alert("lp_calQuantity :: " + e);
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
		            	var lv_unitName			= "";
		            	var lv_quanOld			= "0.00";
		            	var lv_quanNew			= "0.00";
		            	var lv_quantity			= "0.00";
		            	var lv_remark			= "";
		            	
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
		                        
		                        if(ao_jsonObj!=null){
		                        	lv_productCode 	= ao_jsonObj.productCode;
		                        	lv_productName 	= ao_jsonObj.productName;
		                        	lv_unitName 	= ao_jsonObj.unitName;
		    		            	lv_quanOld		= ao_jsonObj.quanOld;
		    		            	lv_quantity		= ao_jsonObj.quantity;
		                        	
		                        }
		                        
		                        //ลำดับ
		                        newNodeTd1.align 			= "center";
		                        newNodeTd1.innerHTML        = rowIndex;
		                        
		                      	//สินค้า
		                      	newNodeTd2.align 			= "left";
		                       	newNodeTd2.innerHTML        = '<input type="text" style="width: 100%" onblur="lp_getProductDetailByName(' + lv_maxSeq + ', this.value);" id="productName' + lv_maxSeq + '" name="productName" value="'+lv_productName+'" />'
		                       								+ '<input type="hidden" id="productCode'+lv_maxSeq+'" name="productCode" value="'+lv_productCode+'" />';
		                       	
		                       	//หน่วย
		                       	newNodeTd3.innerHTML        = '<input type="text" style="width: 100%" id="unitName'+lv_maxSeq+'" name="unitName" class="input-disabled" readonly="readonly" value="'+lv_unitName+'" />';
		                       	
		                      	//เหลือในคลัง(เดิม)
		                       	newNodeTd4.innerHTML        = '<input type="text" id="quanOld'+lv_maxSeq+'" name="quanOld" class="moneyOnly-disabled" readonly="readonly" value="'+lv_quanOld+'" style="width: 100%" />';
		                       	
		                      	//เพิ่ม/ลด สินค้า
		                       	newNodeTd5.innerHTML        = '<input type="text" style="width: 100%" id="quanNew'+lv_maxSeq+'" name="quanNew" class="moneyOnly" onblur="gp_checkAmtOnly(this, 12);lp_calQuantity(' + lv_maxSeq + ');" value="'+lv_quanNew+'" />';
   								
								//เหลือในคลัง(ใหม่)
		                       	newNodeTd6.innerHTML        = '<input type="text" style="width: 100%" id="quantity'+lv_maxSeq+'" name="quantity" class="moneyOnly-disabled" readonly="readonly" value="'+lv_quantity+'" />';
		                       	
		                      	//หมายเหตุ
		                       	newNodeTd7.innerHTML        = '<input type="text" style="width: 100%" id="remark'+lv_maxSeq+'" onblur="lp_updateRecord('+lv_maxSeq+');" name="remark" maxlength="500" value="'+lv_remark+'" />';
		                       	
		                      	//Action
		                      	newNodeTd8.align 			= "center";
		                       	newNodeTd8.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + lv_maxSeq + '\');" />'
															+ '<input type="hidden" id="seq'+lv_maxSeq+'" name="seq" value="'+lv_maxSeq+'" />';
		                        
								lo_seqTemp.value  = lv_maxSeq;
								
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
		
		function lp_deleteRecord(ao_obj, av_val){
			var params		= "";
		    var lo_table    = document.getElementById("resultData");
			
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
			
			try{
				
				params 	= gv_service + "&pageAction=updateRecord&seq=" + av_val 
															 + "&productCode=" 	+ $("#productCode" + av_val).val() .trim()
															 + "&productName=" 	+ $("#productName" + av_val).val() .trim()
															 + "&unitName=" 	+ $("#unitName" + av_val).val() .trim()
															 + "&quanOld=" 		+ $("#quanOld" + av_val).val() .trim()
															 + "&quanNew=" 		+ $("#quanNew" + av_val).val() .trim()
															 + "&quantity=" 	+ $("#quantity" + av_val).val() .trim()
															 + "&remark=" 		+ $("#remark" + av_val).val() .trim();
				
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
		<input type="hidden" id="service" 	name="service" value="servlet.AdjustStockServlet" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=adjustStockForm.getSeqTemp()%>" />
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
				         			<div class="panel-body">
				         				<table class="table sim-panel-result-table" id="resultData">
											<tr height="26px;">
												<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
												<th  style="text-align: center;" width="22%"><B>สินค้า</B><span style="color: red;"><b>*</b></span></th>
												<th  style="text-align: center;" width="12%"><B>หน่วย</B></th>
												<th  style="text-align: center;" width="12%"><B>เหลือในคลัง(เดิม)</B></th>
												<th  style="text-align: center;" width="12%"><B>เพิ่ม/ลด สินค้า </B><span style="color: red;"><b>*</b></span></th>
												<th  style="text-align: center;" width="12%"><B>เหลือในคลัง(ใหม่)</B></th>
												<th  style="text-align: center;" width="16%"><B>หมายเหตุ</B></th>
												<th style="text-align: center;" width="9%">Action</th>
											</tr> 
											<%
													int					  	seq		= 1;
												for(AdjustStockBean bean:adjustHistoryListList){
													if(!bean.getRowStatus().equals(adjustStockForm.DEL)){
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
														   id="unitName<%=bean.getSeq()%>" 
														   name="unitName" 
														   class="input-disabled"
														   readonly="readonly"
														   value="<%=bean.getUnitName()%>" />
												</td>
												<td align="left">
													<input type='text' 
							        					   id="quanOld<%=bean.getSeq()%>" 
							        					   name='quanOld'
							        					   class="moneyOnly-disabled"
							        					   readonly="readonly"
							        					   value="<%=bean.getQuanOld()%>" 
							        					   style="width: 100%" />
												</td>
												<td align="left">
													<input type="text" 
														   style="width: 100%"
														   id="quanNew<%=bean.getSeq()%>" 
														   name="quanNew" 
														   class="moneyOnly"
								        				   onblur="gp_checkAmtOnly(this, 12);lp_calQuantity(<%=bean.getSeq()%>);"
							        					   value="<%=bean.getQuanNew() %>" />
												</td>
												<td align="left">
													<input type='text' 
							        					   id="quantity<%=bean.getSeq()%>" 
							        					   name='quantity'
							        					   class="moneyOnly-disabled"
							        					   readonly="readonly"
							        					   style="width: 100%"
							        					   value="<%=bean.getQuantity()%>" />
												</td>
												<td align="left">
													<input type="text" 
														   style="width: 100%"
														   id="remark<%=bean.getSeq()%>" 
														   name="remark"
														   onblur="lp_updateRecord(<%=bean.getSeq()%>);"
														   maxlength="500"
														   value="<%=bean.getRemark()%>" />
												</td>
												<td align="center">
													<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, '<%=bean.getSeq()%>');" />
													<input type="hidden" id="seq<%=bean.getSeq()%>" name="seq" value="<%=bean.getSeq()%>" />
												</td>
											</tr>
											<% seq++;}}%>
											<tr>
												<td colspan="7">&nbsp;</td>
												<td align="center">
													<img alt="เพิ่ม" title="เพิ่ม" src="<%=imgURL%>/Add.png" width="24" height="24" border="0" id="btnAdd" onclick="lp_newRecord(this, null);" />
													<img alt="รายการสินค้า" width="30px" height="30px" title="รายการสินค้า" id="btnZoom" src="<%=imgURL%>/zoom.png" border="0" />
												</td>
											</tr>
										</table>
									</div>
									<br/>
									<table class="table user-register-table" style="border-bottom-color: white;">
										<tr>
						        			<td align="right">
						        				<input type="button" id="btnSave" class="btn btn-sm btn-warning" value='บันทึก' onclick="lp_save();" />&nbsp;&nbsp;&nbsp;
		   										<input type="button" id="btnReset" onclick="lp_reset();" class="btn btn-sm btn-danger" value='เริ่มใหม่' />
						        			</td>
						        		</tr>
									</table>
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