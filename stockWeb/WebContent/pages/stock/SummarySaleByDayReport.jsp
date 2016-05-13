<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.SummarySaleByDayReportBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="summarySaleByDayReportForm" class="th.go.stock.app.enjoy.form.SummarySaleByDayReportForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	SummarySaleByDayReportBean 			summarySaleByDayReportBean 	= summarySaleByDayReportForm.getSummarySaleByDayReportBean();
	List<SummarySaleByDayReportBean> 	dataList 					= summarySaleByDayReportForm.getDataList();
	String								titlePage					= summarySaleByDayReportForm.getTitlePage();
	List<ComboBean> 					companyCombo 				= summarySaleByDayReportForm.getCompanyCombo();
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
			
			$('#btnShowData').click(function(){ 
				var params = "";
				
				try{
					if(!lp_validate()){
						return;
					}
					
					params = "tin=" 				+ $("#tin").val()
							+ "&invoiceDateFrom=" 	+ $("#invoiceDateFrom").val()
							+ "&invoiceDateTo=" 	+ $("#invoiceDateTo").val();
					
					gp_dialogPopUp(gv_url + "?" + gv_service + "&pageAction=showData&" + params, "<%=titlePage%>");
				}catch(err){
					alert("showData :: " + err);
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
		
		function lp_validate(){
			var la_validate             = new Array( "tin:บริษัทที่สังกัด", "invoiceDateFrom:วันที่เริ่มค้นหา", "invoiceDateTo:วันที่สิ้นสุดค้นหา");
		    var lv_return				= true;
		    
			try{
				if(!gp_validateEmptyObj(la_validate)){
					return false;
				}
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return lv_return;
		}
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.SummarySaleByDayReportServlet" />
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
								        				บริษัทที่สังกัด <span style="color: red;"><b>*</b></span> : &nbsp;
								        			</td>
								        			<td align="left">
								        				<select id="tin" name="tin" style="width: 220px;" >
								        					<% for(ComboBean comboBean:companyCombo){ %>
								        					<option value="<%=comboBean.getCode()%>" <%if(summarySaleByDayReportBean.getTin().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
								        					<%} %>
								        				</select>
								        			</td>
								        			<td align="right">
								        				วันที่ขาย <span style="color: red;"><b>*</b></span> :&nbsp;
								        			</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="invoiceDateFrom" 
								        					   name='invoiceDateFrom' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onchange="gp_checkDate(this);"
								        					   style="width: 100px;"
								        					   value="<%=summarySaleByDayReportBean.getInvoiceDateFrom()%>"  />
								        				&nbsp;-&nbsp;
								        				<input type='text' 
								        					   id="invoiceDateTo" 
								        					   name='invoiceDateTo' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onchange="gp_checkDate(this);"
								        					   style="width: 100px;"
								        					   value="<%=summarySaleByDayReportBean.getInvoiceDateTo()%>"  />
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="left" colspan="4">
								        				<input type="button" id="btnShowData" class='btn btn-primary pull-right padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='แสดงข้อมูล'/>
								        				<input type="button" id="btnReset" class='btn pull-right padding-sm'  style="margin-right:12px" value='เริ่มใหม่' />
								        			</td>
								        		</tr>
								        	</table>
							        	</div>
									</div>
								</div>
								<div style="clear:both"></div><br>
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