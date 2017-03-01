<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.ProductmasterBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*,org.apache.commons.lang3.StringEscapeUtils"%>
<jsp:useBean id="productDetailsLookUpForm" class="th.go.stock.app.enjoy.form.ProductDetailsLookUpForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ProductmasterBean 			productmasterBean 		= productDetailsLookUpForm.getProductmasterBean();
	List<ProductmasterBean> 	dataList 				= productDetailsLookUpForm.getDataList();
%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>LookUp</title>
<%@ include file="/pages/include/enjoyInclude.jsp"%>
<style type="text/css">
		.height250 {
	        height: 250px;
	        overflow-x: auto;
	        overflow-y: auto;
		}
	</style>
	<script>
		var gv_service 			= null;
		var gv_url 				= '<%=servURL%>/EnjoyGenericSrv';
		
		$(document).ready(function(){
			
			gv_service 		= "service=" + $('#service').val();
			
			$('#btnSearch').click(function(){ 
				try{
					
					if(gp_trim($("#productTypeName").val())==""){
						alert("กรุณาระบุหมวดสินค้า", function() { 
							$("#productTypeName").focus();
		    		    });
						//alert("กรุณาระบุหมวดสินค้า");
						//$("#productTypeName").focus();
						return;
					}
					
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=search&" + $('#frm').serialize(),
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	gp_progressBarOff();
			            	window.location.replace('<%=pagesURL%>/zoom/ProductDetailsLookUpScn.jsp');
			            }
			        });
				}catch(err){
					alert("btnSearch :: " + err);
				}
				
			});
			
			$('#btnReset').click(function(){
			 
			    try{
			    	gp_progressBarOn();
			    	window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
			    	gp_progressBarOff();
			    }catch(e){
			    	alert("lp_reset_page :: " + e);
			    }				
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
			
			$( "#productName" ).autocomplete({
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getProductNameList&productTypeName=" + gp_trim($( "#productTypeName" ).val()) + "&productGroupName=" + gp_trim($( "#productGroupName" ).val()) + "&productName=" + gp_trim(request.term),//request,
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
			
			$('#btnSubmit').click(function(){
				
				try{
					
					$.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=checkProduct",//request,
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
			            	var errMsg				= null;
			            	var sizeList			= 0;
			            	var productList			= null;
			            	
			            	try{
			            		
			            		jsonObj = data;//JSON.parse(data);
			            		status	= jsonObj.status;
			            		//alert(status);
			            		if(status=="SUCCESS"){
			            			productList 	= jsonObj.productList;
			            			sizeList 		= parseInt(productList.length);
			            			
			            			//alert(sizeList);
			            			if(sizeList > 0){
			            				
			            				parent.lp_returnProduct(jsonObj);
			            				
			            				/*$.each(productCodeList, function(idx, obj) {
			            					alert(obj.productCode);
				            			});*/
			            			}else{
			            				alert("กรุณาข้อมูลอย่างน้อย 1 รายการ");
			            			}
			            			
			            		}else{
			            			errMsg = jsonObj.errMsg;
			            			alert(errMsg);
			            		}
			            	}catch(e){
			            		alert("in btnSubmit :: " + e);
			            	}
			            }
		            });
				}catch(err){
					alert("btnSubmit :: " + err);
				}
				
			});
			
		});
		
		function lp_setForChooseProduct(ao_obj, av_chkBoxSeq){
			
			var chkBox = "N";
			
			try{
				
				if(ao_obj.checked==true) chkBox = "Y";
				
				$.ajax({
	            	async:false,
		            type: "POST",
	                url: gv_url,
	                dataType: "json",
	                data: gv_service + "&pageAction=setForChooseProduct&chkBoxSeq=" + av_chkBoxSeq + "&pageNum=" + $("#selPage").val() + "&chkBox=" + chkBox,//request,
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	
		            	try{
		            		//alert(data);
		            		jsonObj = data;//JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status!="SUCCESS"){
		            			alert(jsonObj.errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_setForChooseProduct :: " + e);
		            	}
		            }
	            });
				
				if (event.stopPropagation) {
					event.stopPropagation;
				}
				event.cancelBubble = true;
				return true;
				
			}catch(e){
				alert("lp_setForChooseProduct :: " + e);
			}
		}
		
		function lp_clickSearch(event) {
		    var x 		= event.which || event.keyCode;

			try{
				if(x==13){
					$('#btnSearch').click();
				}
			}catch(e){
				alert("lp_clickSearch :: " + e);
			}
		}
		
		/*function lp_setData(av_productCode, av_productName){
			try{
				parent.lp_returnData(av_productCode, av_productName);
			}catch(e){
				alert("lp_sendEditPage :: " + e);
			}
		}*/
		
		//*********************************************************************************//
		//    รายละเอียดเกี่ยวกับการกดปุ่ม First/Previous/Next/Last บนหน้าจอ				       //												
		//*********************************************************************************//
		function lp_first_page()
		{
			if ($("#selPage").val() != "1")
			{
				$("#selPage").val("1");
				lp_selPage();
			}	
		}
		
		function lp_previous_page()
		{
			if ($("#selPage").val() != "1")
			{
				var lv_selPage = parseFloat($("#selPage").val()) - 1;
				$("#selPage").val(lv_selPage);
				lp_selPage();
			}	
		}
		
		function lp_next_page()
		{
			if ($("#selPage").val() != $("#txtMaxresult").val())
			{
				var lv_selPage = parseFloat($("#selPage").val()) + 1;
				$("#selPage").val(lv_selPage);
				lp_selPage();
			}	
		}
		
		function lp_last_page()
		{
			if ($("#selPage").val() != $("#txtMaxresult").val())
			{
				$("#selPage").val($("#txtMaxresult").val());
				lp_selPage();
			}	
		}

		function lp_selPage(){
			
			var lv_selPage = null;
			
			try{
				lv_selPage = $("#selPage").val();
		    	params 	= "service=" + $("#service").val() + "&pageAction=getPage&pageNum=" + lv_selPage;
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: "",
		            success: function(data){
		            	window.location.replace('<%=pagesURL%>/zoom/ProductDetailsLookUpScn.jsp');
		            }
		        });
				
			}catch(e){
				alert("lp_selPage :: " + e);
			}
		}
		//*********************************************************************************//
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.ProductDetailsLookUpServlet" />
		<div style="background-color:white;width: 100%;height: 100%;">
			<table class="user-register-table user-search-table" width="100%" border="0" cellpadding="5" cellspacing="5">
        		<tr>
        			<td align="right" width="150px;">
        				หมวดสินค้า<span style="color: red;"><b>*</b></span> : &nbsp;
        			</td>
        			<td align="left" width="350px;">
        				<input type='text' 
        					   id="productTypeName" 
        					   name='productTypeName' 
        					   value="<%=StringEscapeUtils.escapeHtml4(productmasterBean.getProductTypeName())%>" 
        					   maxlength="200" 
        					   onkeydown="lp_clickSearch(event);"
        					   style="width: 220px;" />
        			</td>
        			<td align="right">
        				หมู่สินค้า : &nbsp;
        			</td>
        			<td align="left">
        				<input type='text' 
        					   id="productGroupName" 
        					   name='productGroupName' 
        					   value="<%=StringEscapeUtils.escapeHtml4(productmasterBean.getProductGroupName())%>" 
        					   maxlength="200" 
        					   onkeydown="lp_clickSearch(event);"
        					   style="width: 220px;" />
        			</td>
        		</tr>
        		<tr>
        			<td align="right">
        				ชื่อสินค้า :&nbsp;
        			</td>
        			<td align="left" colspan="3">
        				<input type='text' 
        					   id="productName" 
        					   name='productName' 
        					   value="<%=StringEscapeUtils.escapeHtml4(productmasterBean.getProductName())%>" 
        					   maxlength="255" 
        					   onkeydown="lp_clickSearch(event);"
        					   style="width: 220px;" />
        			</td>
        		</tr>
        		<tr>
        			<td align="right" colspan="4">
        				<input type="button" id="btnSearch" class='btn btn-primary pull-right padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา'/>
        				<input type="button" id="btnReset" class='btn pull-right padding-sm'  style="margin-right:12px" value='เริ่มใหม่' />
        			</td>
        		</tr>
        	</table>
        	<div id="seasonTitle" class="padding-md round-sm season-title-head" style="">
				<h6 class="panel-title" style="font-size:1.0em">ข้อมูลรายละเอียดสินค้า</h6>
			</div>
			<table class="table sim-panel-result-table" id="tbl_result" width="100%">
				<thead> 
	               <tr height="26px;">
	               		<th  style="text-align: center;" width="5%" ><B>เลือก</B></th>
						<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
						<th  style="text-align: center;" width="20%"><B>หมวดสินค้า</B></th>
						<th  style="text-align: center;" width="15%"><B>หมู่สินค้า</B></th>
						<th  style="text-align: center;" width="20%"><B>รหัสสินค้า</B></th> 
						<th  style="text-align: center;" width="20%"><B>ชื่อสินค้า</B></th>
						<th  style="text-align: center;" width="15%"><B>ราคาขายสินค้า</B></th>  
					</tr> 
				</thead>
				<tfoot>
					<tr height="26px;">
						<td colspan="7" align="right">
							<span style="top: -3px;">จำนวน&nbsp;</span>
							<input type="text" id="i_txt_nvt_totalresult" name="i_txt_nvt_totalresult" style="top:0px;left:0px;width: 50px;"  readonly="readonly" value="<%=productDetailsLookUpForm.getTotalRecord()%>" />
							<span style="top: -3px;">&nbsp;รายการ&nbsp;&nbsp;</span>
							<img id="i_img_nvt_first" name="i_img_nvt_first" src="<%=imgURL%>/first.gif" style="cursor:hand;top:1px;" title="First" onclick="lp_first_page();" />
							<img id="i_img_nvt_prev"  name="i_img_nvt_prev"  src="<%=imgURL%>/prv.gif"   style="cursor:hand;top:1px;" title="Previous" onclick="lp_previous_page();" />
							<input type="text" id="selPage" name="selPage" style="top:0px;left:0px;width: 30px;text-align: right;" maxlength="3" readonly="readonly" value="<%=productDetailsLookUpForm.getPageNum()%>" />
		
							<span class="c_field_label" style="top:-5px;">/</span>
							<span id="i_txt_nvt_total" class="c_field_label" style="top:-5px;" name="i_txt_nvt_total"><%=productDetailsLookUpForm.getTotalPage()%></span>
							<input type="hidden" id="txtMaxresult" name="txtMaxresult" value="<%=productDetailsLookUpForm.getTotalPage()%>" >
							<img id="i_img_nvt_next"  name="i_img_nvt_next" src="<%=imgURL%>/next.gif" style="cursor:hand;top:1px;" title="Next" onclick="lp_next_page();" />
							<img id="i_img_nvt_last"  name="i_img_nvt_last" src="<%=imgURL%>/last.gif" style="cursor:hand;top:1px;" title="Last" onclick="lp_last_page();" />
						</td>
					</tr>
				</tfoot>
				<tbody>
					<%
						ProductmasterBean 	bean 		= null;
						//int					seq			= 1;
				
						if(dataList.size()>0){
							for(int i=0;i<dataList.size();i++){
								bean = dataList.get(i);
					%>
								<tr class="rowSelect">
									<td style="text-align:center;">
										<input type="checkbox" 
											   id="chkBox<%=bean.getChkBoxSeq()%>" 
											   name="chkBox" 
											   value="<%=bean.getChkBoxSeq()%>"
											   onclick="lp_setForChooseProduct(this, <%=bean.getChkBoxSeq()%>);"
											   <%if(bean.getChkBox().equals("Y")){%>checked="checked"<%}%> />
									</td>
									<td style="text-align:center;"><%=bean.getSeqDis()%></td>
									<td style="text-align:left;"><%=StringEscapeUtils.escapeHtml4(bean.getProductTypeName())%></td>
									<td style="text-align:left;"><%=StringEscapeUtils.escapeHtml4(bean.getProductGroupName())%></td>
									<td style="text-align:left;"><%=StringEscapeUtils.escapeHtml4(bean.getProductCodeDis())%></td>
									<td style="text-align:left;"><%=StringEscapeUtils.escapeHtml4(bean.getProductName())%></td>
									<td style="text-align:left;"><%=bean.getSalePrice1()%></td>
								</tr>
					<% 			//seq++;
							} 
						} else{  %>
							<tr height="26px;"><td colspan="7" align="center"><b>ไม่พบข้อมูลที่ระบุ</b></td></tr>
					<%  } %>  
				</tbody>
			</table>
			<br/>
			<table border="0" width="100%">
				<tr>
					<td align="center">
						<input type="button" 
								   id="btnSubmit" 
								   class='btn btn-primary padding-sm' 
								   style="margin-right:12px; padding-right:24px; padding-left:24px;" 
								   value='ตกลง'/>
					</td>
				</tr>
			</table>
		</div>
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