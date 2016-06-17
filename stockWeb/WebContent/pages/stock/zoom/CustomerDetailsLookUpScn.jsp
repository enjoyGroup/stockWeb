<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.CustomerDetailsBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="customerDetailsLookUpForm" class="th.go.stock.app.enjoy.form.CustomerDetailsLookUpForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		var gv_checkDupUserId 	= false;
		
		$(document).ready(function(){
			
			gv_service 		= "service=" + $('#service').val();
			
			 
			$('#btnSearch').click(function(){ 
				try{
					$("#find").val(gp_trim($("#find").val()));
					
					if($("#find").val()!=""){
						if(gp_trim($("#column").val())==""){
							alert("กรุณาระบุค้นหาที่");
							return;
						}
					}
					
					
					$.ajax({
						async:true,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=search&" + $('#frm').serialize(),
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	gp_progressBarOff();
			            	window.location.replace('<%=pagesURL%>/zoom/CustomerDetailsLookUpScn.jsp');
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
		
		function lp_setData(av_cusCode, av_cusGroupCode, av_fullName, av_groupSalePrice){
			try{
				parent.lp_returnCustomerData(av_cusCode, av_cusGroupCode, av_fullName, av_groupSalePrice);
			}catch(e){
				alert("lp_setData :: " + e);
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
		            	window.location.replace('<%=pagesURL%>/zoom/CustomerDetailsLookUpScn.jsp');
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
	<form id="frm" onsubmit="return false;">
		<input type="hidden" id="service" 	name="service" value="servlet.CustomerDetailsLookUpServlet" />
		<div style="background-color:white;width: 100%;height: 100%;">
			<table class="user-register-table user-search-table" width="100%" border="0" cellpadding="5" cellspacing="5">
        		<tr>
        			<td align="right" width="120px;">
        				คำค้นหา  : &nbsp;
        			</td>
        			<td align="left" width="150px;">
        				<input type='text' id="find" name='find' value="<%=customerDetailsLookUpForm.getFind()%>" />
        			</td>
        			<td align="right" width="155px;">
        				ค้นหาที่ : &nbsp;
        			</td>
        			<td align="left" width="140px;">
        				<select id="column" name="column">
        					<%
        						for(ComboBean bean:customerDetailsLookUpForm.getColumnList()){
        					%>
        						<option value="<%=bean.getCode()%>" <%if(bean.getCode().equals(customerDetailsLookUpForm.getColumn())){%> selected="selected" <%}%>>
        							<%=bean.getDesc()%>
        						</option>
        					<%
        						}
        					%>
        				</select>
        				<input type="hidden" id="likeFlag" name="likeFlag" value="<%=customerDetailsLookUpForm.getLikeFlag()%>" />
        			</td>
        		</tr>
        		<tr>
        			<td align="right">
        				เรียงลำดับตาม  : &nbsp;
        			</td>
        			<td align="left">
        				<select id="orderBy" name="orderBy">
        					<%
        						for(ComboBean bean:customerDetailsLookUpForm.getOrderByList()){
        					%>
        						<option value="<%=bean.getCode()%>" <%if(bean.getCode().equals(customerDetailsLookUpForm.getOrderBy())){%> selected="selected" <%}%>>
        							<%=bean.getDesc()%>
        						</option>
        					<%
        						}
        					%>
        				</select>
        			</td>
        			<td align="right">
        				ประเภทการเรียงลำดับ : &nbsp;
        			</td>
        			<td align="left">
        				<select id="sortBy" name="sortBy">
        					<%
        						for(ComboBean bean:customerDetailsLookUpForm.getSortByList()){
        					%>
        						<option value="<%=bean.getCode()%>" <%if(bean.getCode().equals(customerDetailsLookUpForm.getSortBy())){%> selected="selected" <%}%>>
        							<%=bean.getDesc()%>
        						</option>
        					<%
        						}
        					%>
        				</select>
        			</td>
        		</tr>
        		<tr>
        			<td align="right" colspan="4">
        				<input type="button" id="btnSearch" class='btn btn-primary pull-right padding-sm' style="margin-right:12px; padding-right:24px; padding-left:24px;" value='ค้นหา'/>
        				<input type="button" id="btnReset" class='btn pull-right padding-sm'  style="margin-right:12px" value='เริ่มใหม่' />
        			</td>
        		</tr>
        	</table>
        	<div id="seasonTitle" class="padding-md round-sm season-title-head">
				<h6 class="panel-title" style="font-size:1.0em">ข้อมูล</h6>
			</div>
			<table class="table sim-panel-result-table" id="tbl_result" width="100%">
				<thead> 
	               <tr height="26px;">
						<th  style="text-align: center;" width="5%" ><B>รหัส</B></th>
						<th  style="text-align: center;" width="15%"><B>ชื่อ-นามสกุล</B></th>
						<th  style="text-align: center;" width="35%"><B>ที่อยู่</B></th>
						<th  style="text-align: center;" width="15%"><B>เบอร์โทร</B></th> 
						<th  style="text-align: center;" width="15%"><B>คะแนนสะสม</B></th>
						<th  style="text-align: center;" width="15%"><B>หมายเหตุ</B></th>  
					</tr> 
				</thead>
				<tfoot>
					<tr height="26px;">
						<td colspan="6" align="right">
							<span style="top: -3px;">จำนวน&nbsp;</span>
							<input type="text" id="i_txt_nvt_totalresult" name="i_txt_nvt_totalresult" style="top:0px;left:0px;width: 50px;"  readonly="readonly" value="<%=customerDetailsLookUpForm.getTotalRecord()%>" >
							<span style="top: -3px;">&nbsp;รายการ&nbsp;&nbsp;</span>
							<img id="i_img_nvt_first" name="i_img_nvt_first" src="<%=imgURL%>/first.gif" style="cursor:hand;top:1px;" title="First" onclick="lp_first_page();">
							<img id="i_img_nvt_prev"  name="i_img_nvt_prev"  src="<%=imgURL%>/prv.gif"   style="cursor:hand;top:1px;" title="Previous" onclick="lp_previous_page();">
							<input type="text" id="selPage" name="selPage" style="top:0px;left:0px;width: 30px;text-align: right;" maxlength="3" readonly="readonly" value="<%=customerDetailsLookUpForm.getPageNum()%>">
		
							<span class="c_field_label" style="top:-5px;">/</span>
							<span id="i_txt_nvt_total" class="c_field_label" style="top:-5px;" name="i_txt_nvt_total"><%=customerDetailsLookUpForm.getTotalPage()%></span>
							<input type="hidden" id="txtMaxresult" name="txtMaxresult" value="<%=customerDetailsLookUpForm.getTotalPage()%>" >
							<img id="i_img_nvt_next"  name="i_img_nvt_next" src="<%=imgURL%>/next.gif" style="cursor:hand;top:1px;" title="Next" onclick="lp_next_page();">
							<img id="i_img_nvt_last"  name="i_img_nvt_last" src="<%=imgURL%>/last.gif" style="cursor:hand;top:1px;" title="Last" onclick="lp_last_page();">
						</td>
					</tr>
				</tfoot>
				<tbody>
					<%
						List<CustomerDetailsBean> 	dataList 	= customerDetailsLookUpForm.getDataList();
										
						if(dataList.size()>0){
							for(CustomerDetailsBean bean:dataList){
					%>
								<tr class="rowSelect" onclick="lp_setData('<%=bean.getCusCode()%>', '<%=bean.getCusGroupCode()%>', '<%=bean.getFullName()%>', '<%=bean.getGroupSalePrice()%>');" >
									<td style="text-align:center"><%=bean.getCusCode()%></td>
									<td><%=bean.getFullName()%></td>
									<td><%=bean.getAddress()%></td>
									<td><%=bean.getTel()%></td>
									<td><%=bean.getPoint()%></td>
									<td><%=bean.getRemark()%></td>
								</tr>
					<% 			
							} 
						} else{  %>
							<tr height="26px;"><td colspan="6" align="center"><b>ไม่พบข้อมูลที่ระบุ</b></td></tr>
					<%  } %>  
				</tbody>
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