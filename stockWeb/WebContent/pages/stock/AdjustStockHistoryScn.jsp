<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.AdjustStockBean, th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*,org.apache.commons.lang3.StringEscapeUtils"%>
<jsp:useBean id="adjustStockHistoryForm" class="th.go.stock.app.enjoy.form.AdjustStockHistoryForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String						titlePage					= "ประวัติการปรับยอดสต๊อกสินค้า";
	List<AdjustStockBean> 		adjustHistoryListList		= adjustStockHistoryForm.getAdjustHistoryListList();
	AdjustStockBean 			adjustStockBean				= adjustStockHistoryForm.getAdjustStockBean();
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
			
			$('#btnSearch').click(function(){ 
				var la_validate             = new Array( "productName:สินค้า");
				
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
			            			window.location.replace('<%=pagesURL%>/AdjustStockHistoryScn.jsp');
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
			    	  //$('#btnSearch').click();
			      }
			});
			
			lp_calQuantity();
			
			//gp_progressBarOff();
				
		});
		
		function lp_enterToSearch(e){
			var keycode =(window.event) ? event.keyCode : e.keyCode;
			if(keycode == 13) {
				$('#btnSearch').click();
			}
		}
		
		function lp_calQuantity(){
			
			var lv_quanOld 		= 0.00;
			var lv_quanNew 		= 0.00;
			var lv_quantity 	= 0.00;
			
			try{
				lv_quanOld 		= gp_parseFloat($("#quanOld").val());
				lv_quanNew 		= gp_parseFloat($("#quanNew").val());
				lv_quantity 	= lv_quanOld + lv_quanNew;
				
				$("#quantity").val(gp_format_str(lv_quantity.toString(), 2));
				
			}catch(e){
				alert("lp_calQuantity :: " + e);
			}
		}
		
		function lp_loadNextRangeOrder(ao_obj){
			var params							= "";
		    var lo_table                        = document.getElementById("resultData");
		    var rowIndex                      	= null;
		    var newNodeTr 	                	= null;
		    var newNodeTd1 						= null;
		    var newNodeTd2 						= null;
		    var newNodeTd3 						= null;
		    var newNodeTd4 						= null;
		    var newNodeTd5 						= null;
		    
			try{
				params 	 = gv_service + "&pageAction=loadNextRangeOrder"
						 + "&lastOrder=" 			+ $("#lastOrder").val().trim() 
						 + "&productCode=" 			+ $("#productCode").val();
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
		            		gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			$.each(jsonObj.adjustHistoryList, function(idx, obj) {
		            				rowIndex        = gp_rowTableIndex(ao_obj);
			            			newNodeTr       = lo_table.insertRow(rowIndex);
			                        newNodeTd1      = newNodeTr.insertCell(0);
			                        newNodeTd2      = newNodeTr.insertCell(1);
			                        newNodeTd3      = newNodeTr.insertCell(2);
			                        newNodeTd4      = newNodeTr.insertCell(3);
			                        newNodeTd5      = newNodeTr.insertCell(4);
			                        
		                      		//ลำดับ
			                        newNodeTd1.align 			= "center";
			                        newNodeTd1.innerHTML        = rowIndex;
			                        
			                      	//วันที่ปรับ
			                        newNodeTd2.align 			= "center";
			                        newNodeTd2.innerHTML        = obj.adjustDate;
			                        
			                      	//ค่าเดิม
			                        newNodeTd3.align 			= "right";
			                        newNodeTd3.innerHTML        = obj.quanOld;
			                        
			                      	//ค่าใหม่
			                        newNodeTd4.align 			= "right";
			                        newNodeTd4.innerHTML        = obj.quanNew;
			                        
			                      	//หมายเหตุ
			                        newNodeTd5.align 			= "left";
			                        newNodeTd5.innerHTML        = obj.remark;
		            			});
		            			
		            			$("#lastOrder").val(jsonObj.lastOrder);
		            			
		            			if(jsonObj.limitAdjustHistoryFlag==false){
		            				$("#disNextRangeOrder").hide();
		            			}
		            			
		            			
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_loadNextRangeOrder :: " + e);
		            	}
		            }
		        });
			}catch(e){
				alert("lp_loadNextRangeOrder" + e);
			}
		}
		
		
	</script>
</head>
<body>
	<form id="frm" onsubmit="return false;">
		<input type="hidden" id="service" 	name="service" value="servlet.AdjustStockHistoryServlet" />
		<input type="hidden" id="productCode" name="productCode" value="<%=adjustStockBean.getProductCode()%>" />
		<input type="hidden" id="lastOrder" name="lastOrder" value="<%=adjustStockBean.getLastOrder()%>" />
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
									<table width="100%" border="0" cellpadding="5" cellspacing="5">
						        		<tr>
						        			<td align="right" width="10%">
						        				สินค้า <span style="color: red;"><b>*</b></span> : &nbsp;
						        			</td>
						        			<td align="left">
						        				<input type='text' 
						        					   id="productName" 
						        					   name='productName'
						        					   placeholder="สินค้า"  
						        					   size="25"
						        					   maxlength="255" 
						        					   onkeypress="return lp_enterToSearch(event);"
						        					   value="<%=StringEscapeUtils.escapeHtml4(adjustStockBean.getProductName())%>" />
						        				<input type="button" id="btnSearch" class='btn btn-primary padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา' />
						        			</td>
						        		</tr>
						        	</table><br/>
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em">รายละเอียดการปรับยอดสต๊อกสินค้า</h6>
									</div>
				         			<div class="panel-body">
				         				<table class="table sim-panel-result-table" id="resultData">
											<tr height="26px;">
												<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
												<th  style="text-align: center;" width="15%"><B>วันที่ปรับ</B></th>
												<th  style="text-align: center;" width="20%"><B>ค่าเดิม</B></th>
												<th  style="text-align: center;" width="20%"><B>ค่าใหม่</B></th>
												<th style="text-align: center;" width="40%">หมายเหตุ</th>
											</tr> 
											<%
   											int					  	seq		= 1;
											for(AdjustStockBean bean:adjustHistoryListList){
											%>
											<tr>
												<td style="text-align:center">
													<%=seq%>
												</td>
												<td align="center">
													<%=bean.getAdjustDate()%>
												</td>
												<td align="right">
													<%=bean.getQuanOld()%>
												</td>
												<td align="right">
													<%=bean.getQuanNew()%>
												</td>
												<td align="left">
													<%=StringEscapeUtils.escapeHtml4(bean.getRemark())%>
												</td>
											</tr>
											<% seq++;}%>
											<%if(adjustStockHistoryForm.isLimitAdjustHistoryFlag()==true){%>
											<tr align="right" id="disNextRangeOrder">
												<td colspan="5">
													<a href="javascript:void(0);" onclick="lp_loadNextRangeOrder(this);">ดูรายการเพิ่มเติม</a>
												</td>
											</tr>
											<%} %>
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