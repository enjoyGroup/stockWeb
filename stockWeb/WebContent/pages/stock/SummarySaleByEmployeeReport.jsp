<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.SummarySaleByEmployeeReportBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="summarySaleByEmployeeReportForm" class="th.go.stock.app.enjoy.form.SummarySaleByEmployeeReportForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	SummarySaleByEmployeeReportBean 		summarySaleByEmployeeReportBean = summarySaleByEmployeeReportForm.getSummarySaleByEmployeeReportBean();
	List<SummarySaleByEmployeeReportBean> 	dataList 						= summarySaleByEmployeeReportForm.getDataList();
	String									titlePage						= summarySaleByEmployeeReportForm.getTitlePage();
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
		//var gv_checkDupUserId 	= false;
		
		$(document).ready(function(){
			//gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			//gp_progressBarOff();
			
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
			
			 
			$('#btnShowData').click(function(){ 
				var params = "";
				
				try{
					if(!lp_validate()){
						return;
					}
					
					params = "saleName=" 			+ $("#saleName").val()
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
			var la_validate             = new Array( "invoiceDateFrom:วันที่เริ่มค้นหา", "invoiceDateTo:วันที่สิ้นสุดค้นหา");
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
		
		function lp_lookUpSaleName(){
			try{
				gp_dialogPopUp(gv_url + "?service=servlet.UserDetailsLookUpServlet&pageAction=new", "ค้นหาพนักงานขาย");
			}catch(e){
				alert("lp_lookUpSaleName :: " + e);
			}
		}
		
		function lp_returnData(av_userUniqueId, av_userFullName, av_userEmail, av_userStatus, av_userStatusName){
			
			try{
				$("#saleName").val(av_userFullName);
				$( "#dialog" ).dialog( "close" );
			}catch(e){
				alert("lp_returnData :: " + e);
			}
			
		}
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.SummarySaleByEmployeeReportServlet" />
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
								        			<td align="right">
								        				พนักงานขาย :&nbsp;
								        			</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="saleName" 
								        					   name='saleName' 
								        					   value="<%=summarySaleByEmployeeReportBean.getSaleName()%>"
								        					   style="width: 220px;" />
								        				<img alt="lookUp" title="lookUp" src="<%=imgURL%>/lookup.png" width="30px" height="30px" border="0" onclick="lp_lookUpSaleName();" />
								        			</td>
								        			<td align="right">
								        				วันที่ขาย <span style="color: red;"><b>*</b></span> :&nbsp;
								        			</td>
								        			<td align="left" colspan="3">
								        				<input type='text' 
								        					   id="invoiceDateFrom" 
								        					   name='invoiceDateFrom' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onchange="gp_checkDate(this);"
								        					   style="width: 100px;"
								        					   value="<%=summarySaleByEmployeeReportBean.getInvoiceDateFrom()%>"  />
								        				&nbsp;-&nbsp;
								        				<input type='text' 
								        					   id="invoiceDateTo" 
								        					   name='invoiceDateTo' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onchange="gp_checkDate(this);"
								        					   style="width: 100px;"
								        					   value="<%=summarySaleByEmployeeReportBean.getInvoiceDateTo()%>"  />
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="left" height="50px;" colspan="6">&nbsp;</td>
								        		</tr>
								        		<tr>
								        			<td align="left" colspan="6">
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