<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.UserDetailsBean,th.go.stock.app.enjoy.bean.ComboBean,th.go.stock.app.enjoy.model.Userprivilege"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="userDetailsSearchForm" class="th.go.stock.app.enjoy.form.UserDetailsSearchForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserDetailsBean 		userDetailsBean 	= userDetailsSearchForm.getUserDetailsBean();
	List<UserDetailsBean> 	dataList 			= userDetailsSearchForm.getUserDetailsBeanList();
	List<ComboBean> 		refuserstatusCombo 	= userDetailsSearchForm.getStatusCombo();
	int 					couChkRow			= 0;
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>ค้นหาผู้ใช้งานระบบ</title>
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
			gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			gp_progressBarOff();
			
			 
			$('#btnSearch').click(function(){ 
				try{
					$.ajax({
						async:false,
			            type: "POST",
			            url: gv_url,
			            data: gv_service + "&pageAction=searchUserDetail&" + $('#frm').serialize(),
			            beforeSend: gp_progressBarOn(),
			            success: function(data){
			            	gp_progressBarOff();
			            	window.location.replace('<%=pagesURL%>/UserDetailsSearchScn.jsp');
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
		
		function lp_sendEditPage(av_code, av_name){
			
			try{
				window.location.replace(gv_url + "?service=servlet.UserDetailsMaintananceServlet&pageAction=getUserDetail&userUniqueId=" + av_code);
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
		            	window.location.replace('<%=pagesURL%>/UserDetailsSearchScn.jsp');
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
		<input type="hidden" id="service" 	name="service" value="servlet.UserDetailsSearchServlet" />
		<input type="hidden" id="userUniqueId" 	name="userUniqueId" value="<%=userDetailsBean.getUserUniqueId()%>" />
		<input type="hidden" id="pageMode" 	name="pageMode" value="<%=userDetailsSearchForm.getPageMode()%>" />
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
						            	<h4 class="alert-heading"><%=userDetailsSearchForm.getTitlePage() %></h4>
						            </div>
									<div class="container main-container round-sm padding-no" >
										<div class="panel-body" align="center">
								        	<table class="user-register-table user-search-table" width="100%" border="0" cellpadding="5" cellspacing="5">
								        		<tr>
								        			<td align="right" width="150px;">
								        				ชื่อ-นามสกุล  : &nbsp;
								        			</td>
								        			<td align="left" width="350px;">
								        				<input type='text' id="userName" name='userName' maxlength="50" value="<%=userDetailsBean.getUserName() %>" />
								        			</td>
								        			<td align="right">
								        				User ID : &nbsp;
								        			</td>
								        			<td align="left">
								        				<input type='text' id="userId" name='userId' maxlength="20" value="<%=userDetailsBean.getUserId() %>" />
								        				&nbsp;
								        				<span id="inValidSpan"></span>
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				สถานะ :&nbsp;
								        			</td>
								        			<td align="left" colspan="3">
								        				<select id="userStatus" name="userStatus">
								        					<% for(ComboBean comboBean:refuserstatusCombo){ %>
								        					<option value="<%=comboBean.getCode()%>" <%if(userDetailsBean.getUserStatus().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
								        					<%} %>
								        				</select>
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="left" colspan="2">
								        				<span style="color: red;">*ถ้าต้องการค้นหาทั้งหมดให้ระบุช่องนั้นเป็น ***</span>
								        			</td>
								        			<td align="right" colspan="2">
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
										<h6 class="panel-title" style="font-size:1.0em">ข้อมูลผู้ใช้งานระบบ</h6>
									</div>
										<table class="table sim-panel-result-table" id="tbl_result" width="100%">
											<thead> 
								               <tr>
													<th width="5%" >ลำดับ</th>
													<th width="20%">ชื่อ-นามสกุล</th>
													<th width="15%">User Id</th> 
													<th width="20%">E-mail</th>
													<th width="20%">สถานะ</th> 
													<th width="20%">สิทธิ์การใช้งาน</th>
												</tr> 
											</thead>
											<tfoot>
												<tr height="26px;">
													<td colspan="6" align="right">
														<span style="top: -3px;">จำนวน&nbsp;</span>
														<input type="text" id="i_txt_nvt_totalresult" name="i_txt_nvt_totalresult" style="top:0px;left:0px;width: 50px;"  readonly="readonly" value="<%=userDetailsSearchForm.getTotalRecord()%>" >
														<span style="top: -3px;">&nbsp;รายการ&nbsp;&nbsp;</span>
														<img id="i_img_nvt_first" name="i_img_nvt_first" src="<%=imgURL%>/first.gif" style="cursor:hand;top:1px;" title="First" onclick="lp_first_page();">
														<img id="i_img_nvt_prev"  name="i_img_nvt_prev"  src="<%=imgURL%>/prv.gif"   style="cursor:hand;top:1px;" title="Previous" onclick="lp_previous_page();">
														<input type="text" id="selPage" name="selPage" style="top:0px;left:0px;width: 30px;text-align: right;" maxlength="3" readonly="readonly" value="<%=userDetailsSearchForm.getPageNum()%>">
									
														<span class="c_field_label" style="top:-5px;">/</span>
														<span id="i_txt_nvt_total" class="c_field_label" style="top:-5px;" name="i_txt_nvt_total"><%=userDetailsSearchForm.getTotalPage()%></span>
														<input type="hidden" id="txtMaxresult" name="txtMaxresult" value="<%=userDetailsSearchForm.getTotalPage()%>" >
														<img id="i_img_nvt_next"  name="i_img_nvt_next" src="<%=imgURL%>/next.gif" style="cursor:hand;top:1px;" title="Next" onclick="lp_next_page();">
														<img id="i_img_nvt_last"  name="i_img_nvt_last" src="<%=imgURL%>/last.gif" style="cursor:hand;top:1px;" title="Last" onclick="lp_last_page();">
													</td>
												</tr>
											</tfoot>
											<tbody>
												<%
													UserDetailsBean 	  bean 		= null;
	    											int					  seq		= 1;
													
													if(dataList.size()>0){
														for(int i=0;i<dataList.size();i++){
															bean = dataList.get(i);															
												%>
															<tr class="rowSelect" onclick="lp_sendEditPage('<%=bean.getUserUniqueId()%>', '<%=bean.getUserName()%>');" >
																<td style="text-align:center"><%=seq%></td>
																<td><%=bean.getUserName()%></td>
																<td><%=bean.getUserId()%></td>
																<td><%=bean.getUserEmail()%></td>
																<td><%=bean.getUserStatus()%></td>
																<td><%=bean.getUserPrivilege()%></td>
															</tr>
												<% 			seq++;
														} 
													} else{  %>
														<tr height="26px;"><td colspan="6" align="center"><b>ไม่พบข้อมูลที่ระบุ</b></td></tr>
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
		<div align="center" class="FreezeScreen" style="display:none;">
	        <center>
	        	<img id="imgProgress" valign="center" src="<%=imgURL%>/loading36.gif" alt="" />
	        	<span style="font-weight: bold;font-size: large;color: black;">Loading...</span>
	        </center>
	    </div>
	</form>
</body>
</html>