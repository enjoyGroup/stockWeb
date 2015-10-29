<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.RelationGroupCustomerBean,
				th.go.stock.app.enjoy.bean.ComboBean"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="relationGroupCustomerForm" class="th.go.stock.app.enjoy.form.RelationGroupCustomerForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	List<ComboBean> 				groupSalePriceCombo 		= relationGroupCustomerForm.getGroupSalePriceCombo();
	String							titlePage					= relationGroupCustomerForm.getTitlePage();
	List<RelationGroupCustomerBean> relationGroupCustomerList 	= relationGroupCustomerForm.getRelationGroupCustomerList();


%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title><%=titlePage%></title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<script>
		var gv_service 					= null;
		var gv_url 						= '<%=servURL%>/EnjoyGenericSrv';
		var gv_jsonGroupSalePriceCombo 	= '<%=relationGroupCustomerForm.getJsonGroupSalePriceCombo()%>';
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gv_service 		= "service=" + $('#service').val();
			
			gp_progressBarOff();
			
		});
		
		function lp_validate(){
			 var la_cusGroupName			= null;
			 var la_groupSalePrice			= null;
		    
			try{
				la_cusGroupName 		= document.getElementsByName("cusGroupName");
				la_groupSalePrice 		= document.getElementsByName("groupSalePrice");
				
				
				
				/*Begin Check รายการสินค้า*/
				for(var i=0;i<la_cusGroupName.length;i++){
					if(la_cusGroupName[i].value.trim()==""){
						alert("กรุณาระบุชื่อกลุ่มลูกค้า");
						la_cusGroupName[i].focus();
						return false;
					}else if(la_groupSalePrice[i].value.trim()==""){
						alert("กรุณาระบุใช้ราคาสินค้า");
						la_groupSalePrice[i].focus();
						return false;
					}
				}
				/*End Check รายการสินค้า*/
				
				
			}catch(e){
				alert("lp_validate :: " + e);
				return false;
			}
			
			return true;
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
		            		gp_progressBarOff();
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			alert("บันทึกเรียบร้อย");
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
				window.location = gv_url + "?service=" + $("#service").val() + "&pageAction=new";
			}catch(e){
				alert("lp_reset :: " + e);
			}
			
		}		

		
		function lp_deleteRecord(ao_obj, av_val){
			var params							= "";
		    var lo_table                        = document.getElementById("resultData");
			
			try{
				
				params 	= gv_service + "&pageAction=deleteRecord&seq=" + av_val;
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status=="SUCCESS"){
		            			lo_table.deleteRow(gp_rowTableIndex(ao_obj)); 
		            			
		            			for(var i=1;i<(lo_table.rows.length - 1);i++){
		                            lo_table.rows[i].cells[0].innerHTML = (i);
		                        }
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_deleteRecord :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_deleteRecord :: " + e);
			}
		}

		function lp_newRecord(ao_obj){
			var params							= "";
		    var lo_table                        = document.getElementById("resultData");
		    var lo_seqTemp            			= document.getElementById("seqTemp");
		    var lv_maxSeq                       = parseInt(lo_seqTemp.value) + 1;
		    var rowIndex                      	= gp_rowTableIndex(ao_obj);//lo_table.rows.length;
		    var newNodeTr 	                	= null;
		    var newNodeTd1 						= null;
		    var newNodeTd2 						= null;
		    var newNodeTd3 						= null;
		    var newNodeTd4 						= null;
			
			try{
				
				params 	= gv_service + "&pageAction=newRecord&newSeq=" + lv_maxSeq;
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 				= null;
		            	var status					= null;
		            	var jsonCombo 				= null;
		            	var lv_combo				= "";
		            	
		            	try{
		            		
		            		jsonObj 	= JSON.parse(data);
		            		status		= jsonObj.status;
		            		
		            		jsonCombo	= JSON.parse(gv_jsonGroupSalePriceCombo);
		            		
		            		if(status=="SUCCESS"){
		            			newNodeTr       = lo_table.insertRow(rowIndex);
		                        newNodeTd1      = newNodeTr.insertCell(0);
		                        newNodeTd2      = newNodeTr.insertCell(1);
		                        newNodeTd3      = newNodeTr.insertCell(2);
		                        newNodeTd4      = newNodeTr.insertCell(3);
		                        
		                        //ลำดับ
		                        newNodeTd1.align 			= "center";
		                        newNodeTd1.innerHTML        = rowIndex;
		                        
		                      	//ชื่อกลุ่มลูกค้า
		                      	newNodeTd2.align 			= "left";
		                       	newNodeTd2.innerHTML        = '<input type="text" style="width: 100%;" id="cusGroupName' + lv_maxSeq + '" name="cusGroupName" onblur="lp_updateRecord('+lv_maxSeq+');" value="" />';
		                       	
		                      	//ใช้ราคาสินค้า
		                      	newNodeTd3.align 			= "left";
		                      	lv_combo = '<select id="groupSalePrice'+lv_maxSeq+'" name="groupSalePrice" style="width: 100%;" onchange="lp_updateRecord('+lv_maxSeq+');" >';
		                      	$.each(jsonCombo.jsonGroupSalePriceCombo, function (id, obj) {
		                      		lv_combo += '<option value="' + obj.code + '">' + obj.desc + '</option>';
                                });
		                      	lv_combo += '</select>';
		                       	newNodeTd3.innerHTML        = lv_combo;
		                       	
		                       	
		                      	//Action
		                      	newNodeTd4.align 			= "center";
		                       	newNodeTd4.innerHTML        = '<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, \'' + lv_maxSeq + '\');" />'
															+ '<input type="hidden" id="seq'+lv_maxSeq+'" name="seq" value="'+lv_maxSeq+'" />';
		                        
								lo_seqTemp.value  = lv_maxSeq;
								
								$('#cusGroupName' + lv_maxSeq).focus();
								
		            		}else{
		            			alert(jsonObj.errMsg);
		            			
		            		}
		            	}catch(e){
		            		alert("in lp_newRecord :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_newRecord :: " + e);
			}
		}
		
		
		function lp_updateRecord(av_val){
			var params					= "";
			
			try{
				
				params 	= gv_service + "&pageAction=updateRecord&seq=" 				+ av_val 
															 + "&cusGroupName=" 	+ $("#cusGroupName" + av_val).val() .trim()
															 + "&groupSalePrice=" 	+ $("#groupSalePrice" + av_val).val() .trim();
				
				$.ajax({
					async:false,
		            type: "POST",
		            url: gv_url,
		            data: params,
		            beforeSend: "",
		            success: function(data){
		            	var jsonObj 			= null;
		            	var status				= null;
		            	
		            	try{
		            		
		            		jsonObj = JSON.parse(data);
		            		status	= jsonObj.status;
		            		
		            		if(status!="SUCCESS"){
		            			alert(jsonObj.errMsg);
		            		}
		            	}catch(e){
		            		alert("in lp_updateRecord :: " + e);
		            	}
		            }
		        });
				
			}catch(e){
				alert("lp_updateRecord :: " + e);
			}
		}
		
	</script>
</head>
<body>
	<form id="frm" onsubmit="return false;">
		<input type="hidden" id="service" 	name="service" value="servlet.RelationGroupCustomerServlet" />
		<input type="hidden" id="seqTemp" name="seqTemp" value="<%=relationGroupCustomerForm.getSeqTemp()%>" />
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
									        <table class="table sim-panel-result-table" id="resultData">
												<tr height="26px;">
													<th  style="text-align: center;" width="5%" ><B>ลำดับ</B></th>
													<th  style="text-align: center;" width="40%"><B>ชื่อกลุ่มลูกค้า</B><span style="color: red;"><b>*</b></span></th>
													<th  style="text-align: center;" width="40%"><B>ใช้ราคาสินค้า</B><span style="color: red;"><b>*</b></span></th>
													<th style="text-align: center;" width="15%">Action</th>
												</tr> 
												<%
 													int					  	seq		= 1;
													for(RelationGroupCustomerBean bean:relationGroupCustomerList){
														if(!bean.getRowStatus().equals(relationGroupCustomerForm.DEL)){
 												%>
												<tr>
													<td style="text-align:center">
														<%=seq%>
													</td>
													<td align="left">
														<input type="text" 
															   style="width: 100%"
															   id="cusGroupName<%=bean.getSeq()%>" 
															   name="cusGroupName"
															   onblur="lp_updateRecord('<%=bean.getSeq()%>');"
															   value="<%=bean.getCusGroupName()%>" />
													</td>
													<td align="left">
														<select id="groupSalePrice<%=bean.getSeq()%>" name="groupSalePrice" style="width: 100%;" onchange="lp_updateRecord('<%=bean.getSeq()%>');" >
								        					<% for(ComboBean comboBean:groupSalePriceCombo){ %>
								        					<option value="<%=comboBean.getCode()%>" <%if(bean.getGroupSalePrice().equals(comboBean.getCode())){ %> selected <%} %> ><%=comboBean.getDesc()%></option>
								        					<%} %>
								        				</select>
													</td>
													<td align="center">
														<img alt="ลบ" title="ลบ" src="<%=imgURL%>/wrong.png" width="24" height="24" border="0" onclick="lp_deleteRecord(this, '<%=bean.getSeq()%>');" />
														<input type="hidden" id="seq<%=bean.getSeq()%>" name="seq" value="<%=bean.getSeq()%>" />
													</td>
												</tr>
												<% seq++;}}%>
												<tr>
													<td colspan="3">&nbsp;</td>
													<td align="center">
														<img alt="เพิ่ม" title="เพิ่ม" src="<%=imgURL%>/Add.png" width="24" height="24" border="0" id="btnAdd" onclick="lp_newRecord(this);" />
													</td>
												</tr>
											</table>
											<br/>
											<table class="table user-register-table" style="border-bottom-color: white;">
							        			<tr>
								        			<td align="right">
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