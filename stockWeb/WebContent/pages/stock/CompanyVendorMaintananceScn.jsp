<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.CompanyVendorBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="companyVendorMaintananceForm" class="th.go.stock.app.enjoy.form.CompanyVendorMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String 						pageMode 			= companyVendorMaintananceForm.getPageMode();
	String						titlePage			= companyVendorMaintananceForm.getTitlePage();
	CompanyVendorBean 			companyVendorBean 	= companyVendorMaintananceForm.getCompanyVendorBean();

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
			//gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			$( "#provinceName" ).autocomplete({ 
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=p&provinceName=" + gp_trim(request.term),//request,
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
			
			
			$( "#districtName" ).autocomplete({
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=d&provinceName=" + gp_trim($( "#provinceName" ).val()) + "&districtName=" + gp_trim(request.term),//request,
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
			
			$( "#subdistrictName" ).autocomplete({
				 source: function(request, response) {
		            $.ajax({
		            	async:false,
			            type: "POST",
		                url: gv_url,
		                dataType: "json",
		                data: gv_service + "&pageAction=s&provinceName=" + gp_trim($( "#provinceName" ).val()) + "&districtName=" + gp_trim($( "#districtName" ).val()) + "&subdistrictName=" + gp_trim(request.term),//request,
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
			
			
			
			
			//gp_progressBarOff();
			
		});
		
		
		function lp_validate(){
			var la_validate             = new Array("vendorName:บริษัท"	
													, "branchName:สาขา"
													, "tin:เลขประจำตัวผู้เสียภาษี"
													, "provinceName:จังหวัด"
													, "districtName:อำเภอ/เขต"
													, "subdistrictName:ตำบล/แขวง"
													, "postCode:รหัสไปรษณีย์"
													, "tel:เบอร์โทร");
		    var lv_return				= true;
		    //var provinceName			= "";
		    //var districtName			= "";
		    //var subdistrictName			= "";
		    
			try{
				//provinceName		= $("#provinceName").val().trim();
				//districtName		= $("#districtName").val().trim();
				//subdistrictName		= $("#subdistrictName").val().trim();
				
				if(!gp_validateEmptyObj(la_validate)){
					return false;
				}
				
				/*Begin Check เลขประจำตัวผู้เสียภาษี*/
				if(gv_checkFormatIdNumber==false){
					alert("เลขประจำตัวผู้เสียภาษีผิด", function() { 
						$("#tin").focus();
	    		    });
					//alert("เลขประจำตัวผู้เสียภาษีผิด");
					//$("#tin").focus();
	                return false;
				}
				/*End Check เลขประจำตัวผู้เสียภาษี*/
				
				/*Begin Check จังหวัด อำเภอ/เขต และตำบล/แขวง*/
				/*if(provinceName=="" && districtName=="" && subdistrictName==""){
					$("#provinceCode").val("");
        			$("#districtCode").val("");
        			$("#subdistrictCode").val("");
					//return true;
				}else{
					if(provinceName=="" || districtName=="" || subdistrictName==""){
						alert("กรุณาระบุจังหวัด อำเภอ/เขต และตำบล/แขวงให้ครบ");
						return false;
					}
				}*/
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: gv_service + "&pageAction=validate&" + $('#frm').serialize(),
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	var errMsg				= null;
		            	var errType				= null;
		            	
		            	try{
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			
		            			if(provinceName!="" && districtName!="" && subdistrictName!=""){
			            			$("#provinceCode").val(jsonObj.provinceCode);
			            			$("#districtCode").val(jsonObj.districtCode);
			            			$("#subdistrictCode").val(jsonObj.subdistrictCode);
		            			}
		            			
		            			lv_return = true;
		            		}else{
		            			errMsg 	= jsonObj.errMsg;
		            			errType = jsonObj.errType;
		            			
		            			if(errType=="E"){
		            				alert(errMsg);
		            				lv_return = false;
		            			}else{
		            				$("#provinceCode").val(jsonObj.provinceCode);
			            			$("#districtCode").val(jsonObj.districtCode);
			            			$("#subdistrictCode").val(jsonObj.subdistrictCode);
		            				lv_return = confirm(errMsg);
		            			}
		            		}
		            	}catch(e){
		            		alert("in lp_validate :: " + e);
		            		lv_return = false;
		            	}
		            }
		        });
				/*End Check จังหวัด อำเภอ/เขต และตำบล/แขวง*/
				
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return lv_return;
		}
		
		function lp_onBlurPostCode(){
			
			var lo_postCode 		= null;//replaceComma
			
			try{
				lo_postCode 			= document.getElementById("postCode");
				
				if(gp_number(lo_postCode)==false){
					alert("กรุณาระบุตัวเลขเท่านั้น", function() { 
						//lo_postCode.value = "";
						$('#postCode').val('');
						$('#postCode').focus().select();
	    		    });
					//alert("กรุณาระบุตัวเลขเท่านั้น");
					//lo_postCode.value = "";
					return;
				}
				
				if(gp_trim(lo_postCode.value)!="" && gp_trim(lo_postCode.value).length < 5){
					alert("ระบุได้รหัสไปรษณีย์ผิด", function() { 
						$('#postCode').focus().select();
	    		    });
					//alert("ระบุได้รหัสไปรษณ๊ย์ผิด");
					//$('#postCode').focus().select();
					return;
				}
				
			}catch(e){
				alert("lp_onBlurPostCode :: " + e);
			}
			
		}
		
		function lp_save(){
			var params				= "";
			
			try{
				
				if(!lp_validate()){
					return;
				}
				
				params 	= "pageAction=save&" + $('#frm').serialize();
				
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
		            		//gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			alert("บันทึกเรียบร้อย", function() { 
		            				lp_reset();
		    	    		    });
		            			//alert("บันทึกเรียบร้อย");
		            			//lp_reset();
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_save :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_save :: " + e);
				return;
			}
		}
		
		function lp_reset(){
			try{
				if($("#pageMode").val()=="NEW"){
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
				}else{
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getDetail&vendorCode=" + $('#vendorCode').val();
				}
			}catch(e){
				alert("lp_reset :: " + e);
			}
			
		}		

		function lp_checkIdNumber(){
			
			var lv_tin 		= null;
			
			try{
				lv_tin = gp_trim($("#tin").val());
				
				$("#inValidSpan").html("");
				
				if(lv_tin==""){
					gv_checkFormatIdNumber = true;
					return;
				}
				
				if(!gp_validatePin("tin")){
					$("#inValidSpan").css("color", "red");
    				$("#inValidSpan").html("เลขประจำตัวผู้เสียภาษีผิด");
    				
    				gv_checkFormatIdNumber = false;
	                return;
				}else{
					gv_checkFormatIdNumber = true;
				}
				
				//$("#tin").val(lv_tin);
				
				
			}catch(e){
				alert("lp_checkIdNumber :: " + e);
			}
		}


	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.CompanyVendorMaintananceServlet" />
		<input type="hidden" id="pageMode" 	name="pageMode" value="<%=pageMode%>" />
		<input type="hidden" id="provinceCode" name="provinceCode" value="" />
		<input type="hidden" id="districtCode" name="districtCode" value="" />
		<input type="hidden" id="subdistrictCode" name="subdistrictCode" value="" />
		<input type="hidden" id="vendorCode" name="vendorCode" value="<%=companyVendorBean.getVendorCode()%>" />
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
								<h4 class="alert-heading">เพิ่มลูกค้า/บริษัทสั่งซื้อ</h4>
							</div>					          	
							<div class="container main-container round-sm padding-xl-h">
								<div class="col-sm-12 toppad" >
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em"><%=titlePage%></h6>
									</div>
				         			<div class="panel-body">
				            			<table class="table user-register-table" style="border-bottom-color: white;">
											<tr>
								        		<td align="right">
													บริษัท<span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left">
							        				<input type='text' 
							        					   id="vendorName" 
							        					   name='vendorName' 
							        					   value="<%=companyVendorBean.getVendorName() %>" 
							        					   maxlength="100" 
							        					   onblur="lp_getCompanyVendorDetail();"
							        					   style="width: 220px;" />
							        			</td>
							        			<td align="right">
													สาขา<span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left" colspan="3">
							        				<input type='text' 
							        					   id="branchName" 
							        					   name='branchName' 
							        					   value="<%=companyVendorBean.getBranchName() %>" 
							        					   maxlength="30" 
							        					   onblur="lp_getCompanyVendorDetail();"
							        					   style="width: 220px;" />
							        			</td>
								        	</tr>
								        	<tr>
							        			<td align="right">
													เลขประจำตัวผู้เสียภาษี<span style="color: red;"><b>*</b></span> :
												</td>
							        			<td align="left" colspan="5">
							        				<input  type="text" 
							        						id="tin" 
							        						name="tin" 
							        						class="numberOnly"
							        						style="width: 200px;"
							        						onblur="lp_checkIdNumber();"
							        						value="<%=companyVendorBean.getTin()%>" 
							        						maxlength="13"  />
							        				&nbsp;
							        				<span id="inValidSpan"></span>
							        			</td>
								        	</tr>
							        		<tr>
												<td align="right">
													บ้านเลขที่ :
												</td>
												<td align="left">
													<input  type="text"
															id="houseNumber" 
															name="houseNumber"
															size="5"
															maxlength="10" 
															value="<%=companyVendorBean.getHouseNumber() %>" />
												</td>
												<td align="right">
													หมู่ที่:
												</td>
												<td align="left" colspan="3">
													<input type="text"
														   id="mooNumber" 
														   name="mooNumber"
														   size="5"
														   maxlength="10" 
														   value="<%=companyVendorBean.getMooNumber() %>" />
												</td>
											</tr>
							        		<tr>
												<td align="right">
													อาคาร:
												</td>
												<td colspan="5" align="left" >
													<input  type="text" 
															id="buildingName" 
															name="buildingName"
															maxlength="200" 
															style="width: 220px;"
															value="<%=companyVendorBean.getBuildingName()%>"
													/>
												</td>													
											</tr>
											<tr>
												<td align="right">
													ตรอกซอย:
												</td>
												<td align="left" >
													<input  type="text" 
															id="soiName" 
															name="soiName"
															maxlength="250" 
															style="width: 220px;"
															value="<%=companyVendorBean.getSoiName() %>"
													/>
												</td>
												<td align="right">
													ถนน:
												</td>
												<td align="left" colspan="3">
													<input  type="text" 
															id="streetName" 
															name="streetName"
															maxlength="250" 
															style="width: 220px;"
															value="<%=companyVendorBean.getStreetName() %>"
													/>
												</td>
											</tr>
											<tr>
												<td align="right">
													จังหวัด<span style="color: red;"><b>*</b></span> :
												</td>
												<td align="left" >
													<input  type="text" 
															id="provinceName" 
															name="provinceName"
															placeholder="จังหวัด"
															style="width: 220px;"
															value="<%=companyVendorBean.getProvinceName() %>"
													/>
												</td>
												<td align="right">
													อำเภอ/เขต<span style="color: red;"><b>*</b></span> :
												</td>
												<td align="left" colspan="3">
													<input  type="text"
															id="districtName" 
															name="districtName"
															placeholder="อำเภอ"
															style="width: 220px;"
															value="<%=companyVendorBean.getDistrictName() %>"
													/>
												</td>
											</tr>
							        		<tr>
												<td align="right">
													ตำบล/แขวง<span style="color: red;"><b>*</b></span> :
												</td>
												<td align="left" >
													<input  type="text"
																id="subdistrictName" 
																name="subdistrictName"
																placeholder="ตำบล"
																style="width: 220px;"
																value="<%=companyVendorBean.getSubdistrictName() %>"
														/>
												</td>
												<td align="right">
													รหัสไปรษณีย์<span style="color: red;"><b>*</b></span> :
												</td>
												<td align="left" colspan="3">
													<input  type="text" 
															size="7"
															id="postCode" 
															name="postCode"
															maxlength="5"
															placeholder="รหัสไปรษณ๊ย์"
															onchange="lp_onBlurPostCode();"
															value="<%=companyVendorBean.getPostCode() %>"
													/>
												</td>
											</tr>
							        		<tr>
							        			<td align="right">
							        				เบอร์โทร<span style="color: red;"><b>*</b></span> :
							        			</td>
							        			<td align="left">
							        				<input  type="text" 
							        						id="tel" 
							        						name="tel" 
							        						class="telOnly"
							        						style="width: 220px;"
							        						value="<%=companyVendorBean.getTel()%>" 
							        						maxlength="50"  />
							        			</td>
							        			<td align="right">
							        				เบอร์ Fax :
							        			</td>
							        			<td align="left">
							        				<input  type="text" 
							        						id="fax" 
							        						name="fax" 
							        						class="telOnly"
							        						style="width: 220px;"
							        						value="<%=companyVendorBean.getFax()%>" 
							        						maxlength="50" />
							        			</td>
							        			<td align="right">
							        				E-mail :
							        			</td>
							        			<td align="left">
							        				<input  type="text" 
							        						id="email" 
							        						name="email" 
							        						style="width: 220px;"
							        						value="<%=companyVendorBean.getEmail()%>" 
							        						maxlength="200"  />
							        			</td>
							        		</tr>
							        		<tr>
							        			<td align="right">
							        				หมายเหต :
							        			</td>
							        			<td align="left" colspan="5">
							        				<textarea rows="4" style="width: 500px;" id="remark" name="remark" ><%=companyVendorBean.getRemark()%></textarea>
							        			</td>
							        		</tr>
							        		<tr>
							        			<td align="right" colspan="6">
							        				<br/>
							        				<input type="button" id="btnSave" class="btn btn-sm btn-warning" value='บันทึก' onclick="lp_save();" />&nbsp;&nbsp;&nbsp;
			   										<input type="button" id="btnReset" onclick="lp_reset();" class="btn btn-sm btn-danger" value='เริ่มใหม่' />
							        			</td>
							        		</tr>
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