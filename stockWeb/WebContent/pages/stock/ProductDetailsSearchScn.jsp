<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.ProductmasterBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*,org.apache.commons.lang3.StringEscapeUtils"%>
<jsp:useBean id="productDetailsSearchForm" class="th.go.stock.app.enjoy.form.ProductDetailsSearchForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	ProductmasterBean 			productmasterBean 		= productDetailsSearchForm.getProductmasterBean();
	List<ProductmasterBean> 	dataList 				= productDetailsSearchForm.getDataList();
	String						titlePage				= productDetailsSearchForm.getTitlePage();
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><%=titlePage%></title>
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
		var gv_checkDupUserId 	= false;
		
		$(document).ready(function(){
			
			gv_service 		= "service=" + $('#service').val();
			
			//$( "#productTypeName" ).focus();
			//$('html, body').animate({scrollTop: '0px'}, 300);
			 
			$('#btnSearch').click(function(){ 
				try{
					
					if(!gp_validateEmptyObj(new Array( "productTypeName:หมวดสินค้า"))) return false;
					
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=search&" + $('#frm').serialize(),
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	gp_progressBarOff();
			            	window.location.replace('<%=pagesURL%>/ProductDetailsSearchScn.jsp');
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
			
			$('#btnPrint').click(function(){
				
				try{
					
					if($('input[name="radPrint"]:checked').length<=0){
						alert("กรุณาระบุประเภทในการพิมพ์");
						return;
	                }
					
					$.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=checkForPrint",//request,
			            success: function(data){
			            	var jsonObj 			= null;
			            	var status				= null;
			            	var errMsg				= null;
			            	var checkRecord			= 0;
			            	
			            	try{
			            		
			            		jsonObj = data;//JSON.parse(data);
			            		status	= jsonObj.status;
			            		//alert(status);
			            		if(status=="SUCCESS"){
			            			checkRecord 		= parseInt(jsonObj.checkRecord);
			            			
			            			if(checkRecord > 0){
			            				lp_print();
			            			}else{
			            				alert("กรุณาข้อมูลอย่างน้อย 1 รายการ");
			            			}
			            			
			            		}else{
			            			errMsg = jsonObj.errMsg;
			            			alert(errMsg);
			            		}
			            	}catch(e){
			            		alert("in btnPrint :: " + e);
			            	}
			            }
		            });
				}catch(err){
					alert("btnPrint :: " + err);
				}
				
			});
			
		});
		
		function lp_print(){
			var la_radPrint 	= null;
			var lv_printType	= "";
			var h				= "";
			
			try{
				la_radPrint = document.getElementsByName("radPrint");
				
				for(var i=0;i<la_radPrint.length;i++){
					if(la_radPrint[i].checked==true){
						lv_printType = la_radPrint[i].value;
						break;
					}
				}
				
				h = '<iframe name="printSection" '
				  + '		 id="printSection"'
				  + '		 src="'+gv_url + '?' + gv_service + '&pageAction=print&printType=' + lv_printType + '"'
				  + '		 scrolling="yes"'
				  + '		 frameborder="0"'
				  + '		 width="0"'
				  + '		 height="0">'
				  + '</iframe>';
				
				$("#printDiv").html('');
				$("#printDiv").html(h);
				
				$('#printSection').load(function(){
					var lo_pdf = document.getElementById("printSection");
					lo_pdf.focus();
					lo_pdf.contentWindow.print();
					return false;
				});
				
				//$('#printSection').removeAttr( "src" );
				//$('#printSection').attr('src', gv_url + "?" + gv_service + "&pageAction=print&printType=" + lv_printType);
				
			}catch(e){
				alert("lp_print :: " + e);
			}
		}
		
		function lp_sendEditPage(av_val, e){
			try{
				if($(e.target).is('input[type=checkbox]')) {
				    return;
				}
				
				window.location.replace(gv_url + "?service=servlet.ProductDetailsMaintananceServlet&pageAction=getDetail&productCode=" + av_val);
			}catch(e){
				alert("lp_sendEditPage :: " + e);
			}
		}
		
		function lp_setForPrint(ao_obj, av_chkBoxSeq){
			
			var chkBox = "N";
			
			try{
				
				if(ao_obj.checked==true) chkBox = "Y";
				
				//alert("chkBoxSeq :: " + av_chkBoxSeq + ", selPage :: " + $("#selPage").val() + ", chkBox :: " + chkBox);
				
				$.ajax({
	            	async:false,
		            type: "POST",
	                url: gv_url,
	                dataType: "json",
	                data: gv_service + "&pageAction=setForPrint&chkBoxSeq=" + av_chkBoxSeq + "&pageNum=" + $("#selPage").val() + "&chkBox=" + chkBox,//request,
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
		            		alert("in lp_setForPrint :: " + e);
		            	}
		            }
	            });
				
			}catch(e){
				alert("lp_setForPrint :: " + e);
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
		            	window.location.replace('<%=pagesURL%>/ProductDetailsSearchScn.jsp');
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
		<input type="hidden" id="service" 	name="service" value="servlet.ProductDetailsSearchServlet" />
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<section class="vbox">
			<section>
				<section class="hbox stretch">
					<section id="content">
						<section class="vbox">
							<section class="scrollable padder">
								<div>
									<div class="alert alert-block alert-error fade in container">
						            	<h4 class="alert-heading"><%=titlePage%></h4>
						            </div>
						            
										<div class="container main-container round-sm padding-no" >
											
												<div class="panel-body" align="center">
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
										        			<td align="left" colspan="4">
										        				<input type="button" id="btnSearch" class='btn btn-primary pull-right padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา'/>
										        				<input type="button" id="btnReset" class='btn pull-right padding-sm'  style="margin-right:12px" value='เริ่มใหม่' />
										        			</td>
										        		</tr>
										        	</table>
									        	</div>
											</div>
										</div>
										<div style="clear:both"></div><br>
									
							</section>
							
							<section class="scrollable padder">
									<div class="container main-container round-sm padding-no" >
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
														<input type="text" id="i_txt_nvt_totalresult" name="i_txt_nvt_totalresult" style="top:0px;left:0px;width: 50px;"  readonly="readonly" value="<%=productDetailsSearchForm.getTotalRecord()%>" />
														<span style="top: -3px;">&nbsp;รายการ&nbsp;&nbsp;</span>
														<img id="i_img_nvt_first" name="i_img_nvt_first" src="<%=imgURL%>/first.gif" style="cursor:hand;top:1px;" title="First" onclick="lp_first_page();" />
														<img id="i_img_nvt_prev"  name="i_img_nvt_prev"  src="<%=imgURL%>/prv.gif"   style="cursor:hand;top:1px;" title="Previous" onclick="lp_previous_page();" />
														<input type="text" id="selPage" name="selPage" style="top:0px;left:0px;width: 30px;text-align: right;" maxlength="3" readonly="readonly" value="<%=productDetailsSearchForm.getPageNum()%>" />
									
														<span class="c_field_label" style="top:-5px;">/</span>
														<span id="i_txt_nvt_total" class="c_field_label" style="top:-5px;" name="i_txt_nvt_total"><%=productDetailsSearchForm.getTotalPage()%></span>
														<input type="hidden" id="txtMaxresult" name="txtMaxresult" value="<%=productDetailsSearchForm.getTotalPage()%>" >
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
															<tr class="rowSelect" onclick="lp_sendEditPage('<%=bean.getProductCode()%>', event);" >
																<td style="text-align:center;">
																	<input type="checkbox" 
																		   id="chkBox<%=bean.getChkBoxSeq()%>" 
																		   name="chkBox" 
																		   value="<%=bean.getChkBoxSeq()%>"
																		   onclick="lp_setForPrint(this, <%=bean.getChkBoxSeq()%>);"
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
												<td align="left">
													<label style="padding-left: 10px;">
														<input type="radio" name="radPrint" id="radPrint1" <%if(productDetailsSearchForm.getRadPrint().equals("A")){%> checked="checked"<%}%> value="A" />พิมพ์ซ้ำรายการเดิม
														&nbsp;&nbsp;
														<input type="radio" name="radPrint" id="radPrint2" <%if(productDetailsSearchForm.getRadPrint().equals("B")){%> checked="checked"<%}%> value="B" />พิมพ์รายการต่อเนื่อง
														&nbsp;&nbsp;&nbsp;
														<input type="button" 
															   id="btnPrint" 
															   class='btn btn-primary padding-sm' 
															   style="margin-right:12px; padding-right:24px; padding-left:24px;" 
															   value='พิมพ์รหัสสินค้า'/>
													</label>
												</td>
											</tr>
										</table>
									</div>
							</section>
						</section>
					</section>
					<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
				</section>
			</section>
		</section>
		<div id="printDiv" style="display: none;"></div>
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