<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="invoiceCreditSearchForm" class="th.go.stock.app.enjoy.form.InvoiceCreditSearchForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	InvoiceCreditMasterBean 		invoiceCreditMasterBean = invoiceCreditSearchForm.getInvoiceCreditMasterBean();
	List<InvoiceCreditMasterBean> 	dataList 				= invoiceCreditSearchForm.getDataList();
	String							titlePage				= invoiceCreditSearchForm.getTitlePage();
	List<ComboBean> 				invoiceStatusCombo 		= invoiceCreditSearchForm.getInvoiceStatusCombo();
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
			//gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			//gp_progressBarOff();
			
			$( "#cusFullName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=getCusFullName&cusFullName=" + gp_trim(request.term),//request,
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
			
			 
			$('#btnSearch').click(function(){ 
				try{
					$.ajax({
						async:true,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=search&" + $('#frm').serialize(),
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	gp_progressBarOff();
			            	window.location.replace('<%=pagesURL%>/InvoiceCreditSearchScn.jsp');
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
			
		});
		
		function lp_sendEditPage(av_invoiceCode){
			try{
				window.location.replace(gv_url + "?service=servlet.InvoiceCreditMaintananceServlet&pageAction=getDetail&invoiceCode=" + av_invoiceCode);
			}catch(e){
				alert("lp_sendEditPage :: " + e);
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
		            	window.location.replace('<%=pagesURL%>/InvoiceCreditSearchScn.jsp');
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
		<input type="hidden" id="service" 	name="service" value="servlet.InvoiceCreditSearchServlet" />
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
										        	<table width="100%" border="0" cellpadding="5" cellspacing="5">
										        		<tr>
										        			<td align="right" width="150px;">
										        				เลขที่ใบเสร็จ  : &nbsp;
										        			</td>
										        			<td align="left">
										        				<input type='text' id="invoiceCode" name='invoiceCode' maxlength="14" value="<%=invoiceCreditMasterBean.getInvoiceCode()%>" />
										        			</td>
										        			<td align="right">
										        				วันที่ขาย :&nbsp;
										        			</td>
										        			<td align="left" colspan="3">
										        				<input type='text' 
										        					   id="invoiceDateForm" 
										        					   name='invoiceDateForm' 
										        					   placeholder="DD/MM/YYYY"
										        					   class="dateFormat"
										        					   onchange="gp_checkDate(this);"
										        					   style="width: 100px;"
										        					   value="<%=invoiceCreditMasterBean.getInvoiceDateForm()%>"  />
										        				&nbsp;-&nbsp;
										        				<input type='text' 
										        					   id="invoiceDateTo" 
										        					   name='invoiceDateTo' 
										        					   placeholder="DD/MM/YYYY"
										        					   class="dateFormat"
										        					   onchange="gp_checkDate(this);"
										        					   style="width: 100px;"
										        					   value="<%=invoiceCreditMasterBean.getInvoiceDateTo()%>"  />
										        			</td>
										        		</tr>
										        		<tr>
										        			<td align="right">
										        				ชื่อลุกค้า :&nbsp;
										        			</td>
										        			<td align="left">
										        				<input type='text' 
										        					   id="cusFullName" 
										        					   name='cusFullName' 
										        					   value="<%=invoiceCreditMasterBean.getCusFullName()%>" />
										        			</td>
										        			<td align="right">
										        				สถานะ :&nbsp;
										        			</td>
										        			<td align="left" colspan="3">
										        				<select id="invoiceStatus" name="invoiceStatus" style="width: 220px;" >
										        					<% for(ComboBean comboBean:invoiceStatusCombo){ %>
										        					<option value="<%=comboBean.getCode()%>" <%if(invoiceCreditMasterBean.getInvoiceStatus().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
										        					<%} %>
										        				</select>
										        			</td>
										        		</tr>
										        		<tr>
										        			<td align="left" colspan="6">
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
											<h6 class="panel-title" style="font-size:1.0em">ข้อมูลรายละเอียดการขาย</h6>
										</div>
										<table class="table sim-panel-result-table" id="tbl_result" border="1" width="100%">
											<thead> 
								               <tr height="26px;">
													<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
													<th  style="text-align: center;" width="15%"><B>เลขที่ใบเสร็จ</B></th>
													<th  style="text-align: center;" width="15%"><B>ประเภทการขาย</B></th>
													<th  style="text-align: center;" width="20%"><B>ลูกค้า</B></th>
													<th  style="text-align: center;" width="15%"><B>วันที่ขาย</B></th>
													<th  style="text-align: center;" width="15%"><B>จำนวนเงิน</B></th>
													<th  style="text-align: center;" width="15%"><B>สถานะ</B></th>
												</tr> 
											</thead>
											<tfoot>
												<tr height="26px;">
													<td colspan="7" align="right">
														<span style="top: -3px;">จำนวน&nbsp;</span>
														<input type="text" id="i_txt_nvt_totalresult" name="i_txt_nvt_totalresult" style="top:0px;left:0px;width: 50px;"  readonly="readonly" value="<%=invoiceCreditSearchForm.getTotalRecord()%>" >
														<span style="top: -3px;">&nbsp;รายการ&nbsp;&nbsp;</span>
														<img id="i_img_nvt_first" name="i_img_nvt_first" src="<%=imgURL%>/first.gif" style="cursor:hand;top:1px;" title="First" onclick="lp_first_page();">
														<img id="i_img_nvt_prev"  name="i_img_nvt_prev"  src="<%=imgURL%>/prv.gif"   style="cursor:hand;top:1px;" title="Previous" onclick="lp_previous_page();">
														<input type="text" id="selPage" name="selPage" style="top:0px;left:0px;width: 30px;text-align: right;" maxlength="3" readonly="readonly" value="<%=invoiceCreditSearchForm.getPageNum()%>">
									
														<span class="c_field_label" style="top:-5px;">/</span>
														<span id="i_txt_nvt_total" class="c_field_label" style="top:-5px;" name="i_txt_nvt_total"><%=invoiceCreditSearchForm.getTotalPage()%></span>
														<input type="hidden" id="txtMaxresult" name="txtMaxresult" value="<%=invoiceCreditSearchForm.getTotalPage()%>" >
														<img id="i_img_nvt_next"  name="i_img_nvt_next" src="<%=imgURL%>/next.gif" style="cursor:hand;top:1px;" title="Next" onclick="lp_next_page();">
														<img id="i_img_nvt_last"  name="i_img_nvt_last" src="<%=imgURL%>/last.gif" style="cursor:hand;top:1px;" title="Last" onclick="lp_last_page();">
													</td>
												</tr>
											</tfoot>
											<tbody>
												<%
	    											//int					  	seq		= 1;
													
													if(dataList.size()>0){
														for(InvoiceCreditMasterBean bean:dataList){
												%>
															<tr class="rowSelect" onclick="lp_sendEditPage('<%=bean.getInvoiceCode()%>')" >
																<td style="text-align:center"><%=bean.getSeqDis()%></td>
																<td align="left"><%=bean.getInvoiceCode()%></td>
																<td align="left"><%=bean.getInvoiceTypeDesc()%></td>
																<td align="left"><%=bean.getCusFullName()%></td>
																<td align="center"><%=bean.getInvoiceDate()%></td>
																<td align="right"><%=bean.getInvoiceTotal()%></td>
																<td align="left"><%=bean.getInvoiceStatusDesc()%></td>
															</tr>
												<% 			//seq++;
														} 
													} else{  %>
														<tr height="26px;"><td colspan="7" align="center"><b>ไม่พบข้อมูลที่ระบุ</b></td></tr>
												<%  } %>  
											</tbody>
										</table>
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