<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.UserDetailsBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="userDetailsLookUpForm" class="th.go.stock.app.enjoy.form.UserDetailsLookUpForm" scope="session"/>
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
		//var gv_checkDupUserId 	= false;
		
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
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=search&" + $('#frm').serialize(),
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	gp_progressBarOff();
			            	window.location.replace('<%=pagesURL%>/zoom/UserDetailsLookUpScn.jsp');
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
		
		function lp_setData(av_userUniqueId, av_userFullName, av_userEmail, av_userStatus, av_userStatusName){
			try{
				parent.lp_returnData(av_userUniqueId, av_userFullName, av_userEmail, av_userStatus, av_userStatusName);
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
		            	window.location.replace('<%=pagesURL%>/zoom/UserDetailsLookUpScn.jsp');
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
		<input type="hidden" id="service" 	name="service" value="servlet.UserDetailsLookUpServlet" />
		<div style="background-color:white;width: 100%;height: 100%;">
			<table class="user-register-table user-search-table" width="100%" border="0" cellpadding="5" cellspacing="5">
        		<tr>
        			<td align="right" width="120px;">
        				คำค้นหา  : &nbsp;
        			</td>
        			<td align="left" width="150px;">
        				<input type='text' id="find" name='find' value="<%=userDetailsLookUpForm.getFind()%>" />
        			</td>
        			<td align="right" width="155px;">
        				ค้นหาที่ : &nbsp;
        			</td>
        			<td align="left" width="140px;">
        				<select id="column" name="column">
        					<%
        						for(ComboBean bean:userDetailsLookUpForm.getColumnList()){
        					%>
        						<option value="<%=bean.getCode()%>" <%if(bean.getCode().equals(userDetailsLookUpForm.getColumn())){%> selected="selected" <%}%>>
        							<%=bean.getDesc()%>
        						</option>
        					<%
        						}
        					%>
        				</select>
        				<input type="hidden" id="likeFlag" name="likeFlag" value="<%=userDetailsLookUpForm.getLikeFlag()%>" />
        			</td>
        		</tr>
        		<tr>
        			<td align="right">
        				เรียงลำดับตาม  : &nbsp;
        			</td>
        			<td align="left">
        				<select id="orderBy" name="orderBy">
        					<%
        						for(ComboBean bean:userDetailsLookUpForm.getOrderByList()){
        					%>
        						<option value="<%=bean.getCode()%>" <%if(bean.getCode().equals(userDetailsLookUpForm.getOrderBy())){%> selected="selected" <%}%>>
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
        						for(ComboBean bean:userDetailsLookUpForm.getSortByList()){
        					%>
        						<option value="<%=bean.getCode()%>" <%if(bean.getCode().equals(userDetailsLookUpForm.getSortBy())){%> selected="selected" <%}%>>
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
						<th  style="text-align: center;" width="15%"><B>ลำดับ</B></th>
						<th  style="text-align: center;" width="20%"><B>E-mail</B></th>
						<th  style="text-align: center;" width="40%"><B>ชื่อ-นามสกุล</B></th> 
						<th  style="text-align: center;" width="25%"><B>สถานะ</B></th>
					</tr> 
				</thead>
				<tfoot>
					<tr height="26px;">
						<td colspan="4" align="right">
							<span style="top: -3px;">จำนวน&nbsp;</span>
							<input type="text" id="i_txt_nvt_totalresult" name="i_txt_nvt_totalresult" style="top:0px;left:0px;width: 50px;"  readonly="readonly" value="<%=userDetailsLookUpForm.getTotalRecord()%>" >
							<span style="top: -3px;">&nbsp;รายการ&nbsp;&nbsp;</span>
							<img id="i_img_nvt_first" name="i_img_nvt_first" src="<%=imgURL%>/first.gif" style="cursor:hand;top:1px;" title="First" onclick="lp_first_page();">
							<img id="i_img_nvt_prev"  name="i_img_nvt_prev"  src="<%=imgURL%>/prv.gif"   style="cursor:hand;top:1px;" title="Previous" onclick="lp_previous_page();">
							<input type="text" id="selPage" name="selPage" style="top:0px;left:0px;width: 30px;text-align: right;" maxlength="3" readonly="readonly" value="<%=userDetailsLookUpForm.getPageNum()%>">
		
							<span class="c_field_label" style="top:-5px;">/</span>
							<span id="i_txt_nvt_total" class="c_field_label" style="top:-5px;" name="i_txt_nvt_total"><%=userDetailsLookUpForm.getTotalPage()%></span>
							<input type="hidden" id="txtMaxresult" name="txtMaxresult" value="<%=userDetailsLookUpForm.getTotalPage()%>" >
							<img id="i_img_nvt_next"  name="i_img_nvt_next" src="<%=imgURL%>/next.gif" style="cursor:hand;top:1px;" title="Next" onclick="lp_next_page();">
							<img id="i_img_nvt_last"  name="i_img_nvt_last" src="<%=imgURL%>/last.gif" style="cursor:hand;top:1px;" title="Last" onclick="lp_last_page();">
						</td>
					</tr>
				</tfoot>
				<tbody>
					<%
						//int				 		seq			= 1;
						List<UserDetailsBean> 	dataList 	= userDetailsLookUpForm.getDataList();
										
						if(dataList.size()>0){
							for(UserDetailsBean bean:dataList){
					%>
								<tr class="rowSelect" onclick="lp_setData('<%=bean.getUserUniqueId()%>', '<%=bean.getUserFullName()%>', '<%=bean.getUserEmail()%>', '<%=bean.getUserStatus()%>', '<%=bean.getUserStatusName()%>');" >
									<td align="center"><%=bean.getSeqDis()%></td>
									<td><%=bean.getUserEmail()%></td>
									<td><%=bean.getUserFullName()%></td>
									<td><%=bean.getUserStatusName()%></td>
								</tr>
					<% 			//seq++;
							} 
						} else{  %>
							<tr height="26px;"><td colspan="4" align="center"><b>ไม่พบข้อมูลที่ระบุ</b></td></tr>
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