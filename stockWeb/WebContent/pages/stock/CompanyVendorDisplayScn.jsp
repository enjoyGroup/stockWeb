<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.CompanyVendorBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="companyVendorDisplayForm" class="th.go.stock.app.enjoy.form.CompanyVendorDisplayForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String						titlePage				= companyVendorDisplayForm.getTitlePage();
	CompanyVendorBean 			companyVendorBean 		= companyVendorDisplayForm.getCompanyVendorBean();


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
		var gv_checkFormatIdNumber 	= true;
		
		$(document).ready(function(){
			
			gv_service 		= "service=" + $('#service').val();
			
		});
		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.CompanyVendorDisplayServlet" />
		<input type="hidden" id="vendorCode" name="vendorCode" value="<%=companyVendorBean.getVendorCode()%>" />
		<div style="background-color:white;width: 100%;height: 100%;">
      		<table class="table user-register-table" style="border-bottom-color: white;">
				<tr>
	        		<td align="right" style="font-weight: bold;width:20%;">
						บริษัท :
					</td>
	       			<td align="left" style="width:30%;">
	       				<%=companyVendorBean.getVendorName().equals("")?"-":companyVendorBean.getVendorName()%>
	       			</td>
	       			<td align="right" style="font-weight: bold;width:20%;">
						สาขา :
					</td>
	       			<td align="left" style="width:30%;">
	       				<%=companyVendorBean.getBranchName().equals("")?"-":companyVendorBean.getBranchName()%>
	       			</td>
	        	</tr>
	        	<tr>
	       			<td align="right" style="font-weight: bold;">
						เลขประจำตัวผู้เสียภาษี :
					</td>
	       			<td align="left" colspan="3">
	       				<%=companyVendorBean.getTin().equals("")?"-":companyVendorBean.getTin()%>
	       			</td>
	        	</tr>
	       		<tr>
					<td align="right" style="font-weight: bold;">
						บ้านเลขที่ :
					</td>
					<td align="left">
						<%=companyVendorBean.getHouseNumber().equals("")?"-":companyVendorBean.getHouseNumber()%>
					</td>
					<td align="right" style="font-weight: bold;">
						หมู่ที่:
					</td>
					<td align="left">
						<%=companyVendorBean.getMooNumber().equals("")?"-":companyVendorBean.getMooNumber()%>
					</td>
				</tr>
	       		<tr>
					<td align="right" style="font-weight: bold;">
						อาคาร:
					</td>
					<td colspan="3" align="left" >
						<%=companyVendorBean.getBuildingName().equals("")?"-":companyVendorBean.getBuildingName()%>
					</td>													
				</tr>
				<tr>
					<td align="right" style="font-weight: bold;">
						ตรอกซอย:
					</td>
					<td align="left" >
						<%=companyVendorBean.getSoiName().equals("")?"-":companyVendorBean.getSoiName()%>
					</td>
					<td align="right" style="font-weight: bold;">
						ถนน:
					</td>
					<td align="left">
						<%=companyVendorBean.getStreetName().equals("")?"-":companyVendorBean.getStreetName()%>
					</td>
				</tr>
				<tr>
					<td align="right" style="font-weight: bold;">
						จังหวัด :
					</td>
					<td align="left" >
						<%=companyVendorBean.getProvinceName().equals("")?"-":companyVendorBean.getProvinceName()%>
					</td>
					<td align="right" style="font-weight: bold;">
						อำเภอ/เขต :
					</td>
					<td align="left">
						<%=companyVendorBean.getDistrictName().equals("")?"-":companyVendorBean.getDistrictName()%>
					</td>
				</tr>
	       		<tr>
					<td align="right" style="font-weight: bold;">
						ตำบล/แขวง :
					</td>
					<td align="left" >
						<%=companyVendorBean.getSubdistrictName().equals("")?"-":companyVendorBean.getSubdistrictName()%>
					</td>
					<td align="right" style="font-weight: bold;">
						รหัสไปรษณีย์:
					</td>
					<td align="left">
						<%=companyVendorBean.getPostCode().equals("")?"-":companyVendorBean.getPostCode()%>
					</td>
				</tr>
	       		<tr>
	       			<td align="right" style="font-weight: bold;">
	       				เบอร์โทร :
	       			</td>
	       			<td align="left">
	       				<%=companyVendorBean.getTel().equals("")?"-":companyVendorBean.getTel()%>
	       			</td>
	       			<td align="right" style="font-weight: bold;">
	       				เบอร์ Fax :
	       			</td>
	       			<td align="left">
	       				<%=companyVendorBean.getFax().equals("")?"-":companyVendorBean.getFax()%>
	       			</td>
	       		</tr>
	       		<tr>
	       			<td align="right" style="font-weight: bold;">
	       				E-mail :
	       			</td>
	       			<td align="left" colspan="3">
	       				<%=companyVendorBean.getEmail().equals("")?"-":companyVendorBean.getEmail()%>
	       			</td>
	       		</tr>
	       		<tr>
	       			<td align="right" style="font-weight: bold;">
	       				หมายเหต :
	       			</td>
	       			<td align="left" colspan="3">
	       				<%=companyVendorBean.getRemark().equals("")?"-":companyVendorBean.getRemark()%>
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