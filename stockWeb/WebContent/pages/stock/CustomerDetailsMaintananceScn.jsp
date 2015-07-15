<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.CustomerDetailsBean,th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="customerDetailsMaintananceForm" class="th.go.stock.app.enjoy.form.CustomerDetailsMaintananceForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String 					pageMode 			= customerDetailsMaintananceForm.getPageMode();
	CustomerDetailsBean 	customerDetailsBean = customerDetailsMaintananceForm.getCustomerDetailsBean();
	List<ComboBean> 		statusCombo 		= customerDetailsMaintananceForm.getStatusCombo();
	List<ComboBean> 		sexCombo 			= customerDetailsMaintananceForm.getSexCombo();
	String					titlePage			= customerDetailsMaintananceForm.getTitlePage();


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
		var gv_checkFormatIdNumber 	= false;
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			if($("#pageMode").val()=="EDIT"){
				lp_setModeEdit();
			}
			
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
			

			
			gp_progressBarOff();
			
		});
		
		function lp_validate(){
			var la_idName               = new Array("idNumber"	, "cusStatus", "startDate"	);
		    var la_msg               	= new Array("เลขที่บัตร"	, "สถานะ"	 , "วันที่สมัคร"	);
		    var lv_return				= true;
		    var la_idType				= null;
		    var cou						= 0;
		    var provinceName			= "";
		    var districtName			= "";
		    var subdistrictName			= "";
		    var startDate				= "";
		    var expDate					= "";
		    
			try{
				
				la_idType 		= document.getElementsByName("idType");
				provinceName	= gp_trim($("#provinceName").val());
				districtName	= gp_trim($("#districtName").val());
				subdistrictName	= gp_trim($("#subdistrictName").val());
				startDate		= gp_trim($("#startDate").val());
				expDate			= gp_trim($("#expDate").val());
				
				for(var i=0;i<la_idType.length;i++){
					if(la_idType[i].checked==true){
						cou++;
						break;
					}
				}
				
				if(cou==0){
					alert("กรุณาระบุประเภทบัตร");
					return false;
				}
				
				for(var i=0;i<la_idName.length;i++){
		            lo_obj          = eval('$("#' + la_idName[i] + '")');
		            
		            if(gp_trim(lo_obj.val())==""){
		            	alert("กรุณาระบุ " + la_msg[i]);
		            	lo_obj.focus();
		                return false;
		            }
		        }
				//alert(gp_validatePin($("#tin").val()));
				if(gv_checkFormatIdNumber==false){
					alert("เลขที่บัตรผิด");
					$("#idNumber").focus();
	                return false;
				}
				
				if(expDate!=""){
					if(gp_toDate(startDate) > gp_toDate(expDate)){
						alert("วันที่หมดอายุต้องมากกว่าวันที่สมัคร");
		                return false;
	                }
				}
				
				
				if(provinceName=="" && districtName=="" && subdistrictName==""){
					$("#provinceCode").val("");
        			$("#districtCode").val("");
        			$("#subdistrictCode").val("");
					return true;
				}else{
					if(provinceName=="" || districtName=="" || subdistrictName==""){
						alert("กรุณาระบุจังหวัด อำเภอ/เขต และตำบล/แขวงให้ครบ");
						return false;
					}
				}
				
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
		            			
		            			$("#provinceCode").val(jsonObj.provinceCode);
		            			$("#districtCode").val(jsonObj.districtCode);
		            			$("#subdistrictCode").val(jsonObj.subdistrictCode);
		            			
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
					alert("กรุณาระบุตัวเลขเท่านั้น");
					lo_postCode.value = "";
					return;
				}
				
				if(gp_trim(lo_postCode.value)!="" && gp_trim(lo_postCode.value).length < 5){
					alert("ระบุได้รหัสไปรษณ๊ย์ผิด");
					$('#postCode').focus().select();
					return;
				}
				
			}catch(e){
				alert("lp_onBlurPostCode :: " + e);
			}
			
		}
		
		function lp_setModeEdit(){
			
			try{
				gv_checkFormatIdNumber 	= true;
			}catch(e){
				alert("lp_setModeEdit :: " + e);
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
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: gp_progressBarOn(),
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	
		            	try{
		            		gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			alert("บันทึกเรียบร้อย");
		            			//window.location = gv_url + "?service=servlet.UserDetailsMaintananceServlet&pageAction=getUserDetail&userUniqueId=" + userUniqueId;
		            			lp_reset();
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
					window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=getDetail&cusCode=" + $('#cusCode').val();
				}
			}catch(e){
				alert("lp_reset :: " + e);
			}
			
		}		
		
		function lp_calendarItmClick(av_val){
			
			try{
				$('#'+av_val).focus();
			}catch(e){
				alert("btnAddDate :: " + e);
			}
		}
		
		
		function lp_checkIdNumber(){
			
			var lv_idNumber 		= null;
			
			try{
				lv_idNumber = gp_trim($("#idNumber").val());
				
				$("#inValidSpan").html("");
				
				if(lv_idNumber==""){
					return;
				}
				
				if(!gp_validatePin(lv_idNumber)){
					$("#inValidSpan").css("color", "red");
    				$("#inValidSpan").html("เลขที่บัตรผิด");
    				
    				gv_checkFormatIdNumber = false;
	                return;
				}else{
					gv_checkFormatIdNumber = true;
				}
				
				$("#idNumber").val(lv_idNumber);
				
				
			}catch(e){
				alert("lp_checkIdNumber :: " + e);
			}
		}

		
	</script>
</head>
<body>
	<form id="frm">
		<input type="hidden" id="service" 	name="service" value="servlet.CustomerDetailsMaintananceServlet" />
		<input type="hidden" id="pageMode" 	name="pageMode" value="<%=pageMode%>" />
		<input type="hidden" id="provinceCode" name="provinceCode" value="" />
		<input type="hidden" id="districtCode" name="districtCode" value="" />
		<input type="hidden" id="subdistrictCode" name="subdistrictCode" value="" />
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
								<h4 class="alert-heading">เพิ่มลูกค้า/พนักงานขาย</h4>
							</div>					          	
							<div class="container main-container round-sm padding-xl-h">
								<div class="col-sm-12 toppad" >
									<div id="seasonTitle" class="padding-md round-sm season-title-head">
										<h6 class="panel-title" style="font-size:1.0em"><%=titlePage%></h6>
									</div>
				         			<div class="panel-body">
				         				<table class="table user-register-table" style="border-bottom-color: white;" width="100%">
				         					<tr>
								        		<td align="right" width="10%">
													รหัสลูกค้า :
												</td>
							        			<td align="left"  width="90%">
							        				<input type='text' 
							        					   id="cusCode" 
							        					   name='cusCode' 
							        					   class="input-disabled"
							        					   value="<%=customerDetailsBean.getCusCode() %>" 
							        					   readonly="readonly"
							        					   style="width: 220px;" />
							        			</td>
								        	</tr>
				         				</table>
							            <div id="seasonTitle" class="padding-sm round-sm season-title-head2">
											<h6 class="panel-title" style="font-size:1.0em">ข้อมูลส่วนตัว</h6>
										</div>
				            			<table class="table user-register-table" style="border-bottom-color: white;">
												<tr>
									        		<td align="right">
														ชื่อ :
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="cusName" 
								        					   name='cusName' 
								        					   value="<%=customerDetailsBean.getCusName() %>" 
								        					   maxlength="100" 
								        					   style="width: 220px;" />
								        			</td>
								        			<td align="right">
														นามสกุล :
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="cusSurname" 
								        					   name='cusSurname' 
								        					   value="<%=customerDetailsBean.getCusSurname() %>" 
								        					   maxlength="100" 
								        					   style="width: 220px;" />
								        			</td>
								        			<td align="right">
														เพศ :
													</td>
								        			<td align="left">
								        				<select id="sex" name="sex" style="width: 120px;" >
								        					<% for(ComboBean comboBean:sexCombo){ %>
								        					<option value="<%=comboBean.getCode()%>" <%if(customerDetailsBean.getSex().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
								        					<%} %>
								        				</select>
								        			</td>
									        	</tr>
									        	<tr>
									        		<td></td>
								        			<td align="left" colspan="5" valign="middle">
								        				<label class="col-sm-2 control-label" style="text-align:right">
									        				<input  type="radio" 
																	id="idType1" 
																	name="idType" 
																	value="1" 
																	<%if(customerDetailsBean.getIdType().equals("1")){%> checked="checked" <%} %> 
															/>
															บุคคลธรรมดา
														</label>
														<label class="col-sm-2 control-label" style="text-align:right">
															<input  type="radio" 
																	id="idType2" 
																	name="idType" 
																	value="2" 
																	<%if(customerDetailsBean.getIdType().equals("2")){%> checked="checked" <%} %> 
															/>
															นิติบุคคล
														</label>
														เลขที่บัตร<span style="color: red;"><b>*</b></span> :
														<input  type="text" 
								        						id="idNumber" 
								        						name="idNumber" 
								        						class="numberOnly"
								        						style="width: 200px;"
								        						onblur="lp_checkIdNumber();"
								        						value="<%=customerDetailsBean.getIdNumber()%>" 
								        						maxlength="13"  />
								        				&nbsp;
								        				<span id="inValidSpan"></span>
								        			</td>
								        		</tr>
								        		<tr>
									        		<td align="right">
														วันเกิด :
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="birthDate" 
								        					   name='birthDate' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="birthDateFormat"
								        					   onblur="gp_checkDate(this);"
								        					   value="<%=customerDetailsBean.getBirthDate() %>" 
								        					   style="width: 100px;" />
								        				<i class="fa fa-fw fa-calendar" onclick="lp_calendarItmClick('birthDate');" style="cursor:pointer"></i>
								        			</td>
								        			<td align="right">
														ศาสนา :
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="religion" 
								        					   name='religion' 
								        					   value="<%=customerDetailsBean.getReligion() %>" 
								        					   maxlength="50" 
								        					   style="width: 100px;" />
								        			</td>
								        			<td align="right">
														อาชีพ  :
													</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="job" 
								        					   name='job' 
								        					   value="<%=customerDetailsBean.getJob() %>" 
								        					   maxlength="200" 
								        					   style="width: 150px;" />
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
																value="<%=customerDetailsBean.getHouseNumber() %>" />
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
															   value="<%=customerDetailsBean.getMooNumber() %>" />
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
																value="<%=customerDetailsBean.getBuildingName()%>"
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
																value="<%=customerDetailsBean.getSoiName() %>"
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
																value="<%=customerDetailsBean.getStreetName() %>"
														/>
													</td>
												</tr>
												<tr>
													<td align="right">
														จังหวัด :
													</td>
													<td align="left" >
														<input  type="text" 
																id="provinceName" 
																name="provinceName"
																placeholder="จังหวัด"
																style="width: 220px;"
																value="<%=customerDetailsBean.getProvinceName() %>"
														/>
													</td>
													<td align="right">
														อำเภอ/เขต :
													</td>
													<td align="left" colspan="3">
														<input  type="text"
																id="districtName" 
																name="districtName"
																placeholder="อำเภอ"
																style="width: 220px;"
																value="<%=customerDetailsBean.getDistrictName() %>"
														/>
													</td>
												</tr>
								        		<tr>
													<td align="right">
														ตำบล/แขวง :
													</td>
													<td align="left" >
														<input  type="text"
																	id="subdistrictName" 
																	name="subdistrictName"
																	placeholder="ตำบล"
																	style="width: 220px;"
																	value="<%=customerDetailsBean.getSubdistrictName() %>"
															/>
													</td>
													<td align="right">
														รหัสไปรษณ๊ย์:
													</td>
													<td align="left" colspan="3">
														<input  type="text" 
																size="7"
																id="postCode" 
																name="postCode"
																maxlength="5"
																placeholder="รหัสไปรษณ๊ย์"
																onblur="lp_onBlurPostCode();"
																value="<%=customerDetailsBean.getPostCode() %>"
														/>
													</td>
												</tr>
								        		<tr>
								        			<td align="right">
								        				เบอร์โทร :
								        			</td>
								        			<td align="left">
								        				<input  type="text" 
								        						id="tel" 
								        						name="tel" 
								        						class="telOnly"
								        						style="width: 220px;"
								        						value="<%=customerDetailsBean.getTel()%>" 
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
								        						value="<%=customerDetailsBean.getFax()%>" 
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
								        						value="<%=customerDetailsBean.getEmail()%>" 
								        						maxlength="200"  />
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				สถานะ <span style="color: red;"><b>*</b></span> :
								        			</td>
								        			<td align="left" colspan="5">
								        				<select id="cusStatus" name="cusStatus" style="width: 220px;" >
								        					<% for(ComboBean comboBean:statusCombo){ %>
								        					<option value="<%=comboBean.getCode()%>" <%if(customerDetailsBean.getCusStatus().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
								        					<%} %>
								        				</select>
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				วันที่สมัคร <span style="color: red;"><b>*</b></span> :
								        			</td>
								        			<td align="left">
								        				<input type='text' 
								        					   id="startDate" 
								        					   name='startDate' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onblur="gp_checkDate(this);"
								        					   value="<%=customerDetailsBean.getStartDate() %>" 
								        					   style="width: 100px;" />
								        				<i class="fa fa-fw fa-calendar" onclick="lp_calendarItmClick('startDate');" style="cursor:pointer"></i>
								        			</td>
								        			<td align="right">
								        				วันที่หมดอายุ :
								        			</td>
								        			<td align="left" colspan="3">
								        				<input type='text' 
								        					   id="expDate" 
								        					   name='expDate' 
								        					   placeholder="DD/MM/YYYY"
								        					   class="dateFormat"
								        					   onblur="gp_checkDate(this);"
								        					   value="<%=customerDetailsBean.getExpDate() %>" 
								        					   style="width: 100px;" />
								        				<i class="fa fa-fw fa-calendar" onclick="lp_calendarItmClick('expDate');" style="cursor:pointer"></i>
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				คะแนนสะสม :
								        			</td>
								        			<td align="left" colspan="5">
								        				<input  type="text" 
								        						id="point" 
								        						name="point" 
								        						class="numberOnly"
								        						style="width: 150px;"
								        						value="<%=customerDetailsBean.getPoint()%>" 
								        						maxlength="10" />
								        			</td>
								        		</tr>
								        		<tr>
								        			<td align="right">
								        				หมายเหต :
								        			</td>
								        			<td align="left" colspan="5">
								        				<textarea rows="4" style="width: 500px;" id="remark" name="remark" style="float:right;" >
								        					<%=customerDetailsBean.getRemark()%>
								        				</textarea>
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
		<div align="center" class="FreezeScreen" style="display:none;">
        	<center>
        		<img id="imgProgress" valign="center" src="<%=imgURL%>/loading36.gif" alt="" />
        		<span style="font-weight: bold;font-size: large;color: black;">Loading...</span>
        	</center>
    	</div>
	</form>	
</body>
</html>