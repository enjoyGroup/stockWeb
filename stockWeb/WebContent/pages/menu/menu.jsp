<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.UserDetailsBean,th.go.stock.app.enjoy.bean.UserPrivilegeBean,th.go.stock.app.enjoy.bean.PagesDetailBean,th.go.stock.app.enjoy.main.Constants"%>
<%
	//final String servURL1		= Constants.SERV_URL;
	UserDetailsBean userDeatil 	= (UserDetailsBean) request.getSession().getAttribute("userBean");
%>
	<style>
		.fixs{
			position: fixed;
			margin-top: 0px;
			margin-left:0px;
			width: 100%;
			background: black;
			z-index: 9999;
		}
		
		#xx {
		    border-radius: 8px;
		    background-color:white;
		    width: 200px;
		    height: 26px;
		    vertical-align: middle;    
		    border:1px solid black;
		    padding-top: 2px;
		}
		
	</style>
	<script>
		$(document).ready(function(){
			
			$('#menu1').ptMenu();
			$(window).scroll(function(){//alert($(window).scrollTop());
				gp_controlMenu();
		    });
			
			$('#logOut').click(function(){ 
				try{
					confirm("ต้องการออกจากระบบ?", function(){
						window.location.replace('<%=Constants.LOGOUT_URL%>'); 
					});
				}catch(err){
					alert("logOut :: " + err);
				}
				
			});		
		});
	</script>
<div class="headwrap" id="headwrap">
	<div class="row" style="position: relative;">
    	<div class="brand span4" style="position: absolute;left:10px;" align="left">
    		<a href="/stockWeb/pages/menu/welcome.jsp">
     			<img src="<%=Constants.SERV_URL%>/images/logo.png" title="Home" alt="Home" />
     		</a>
        </div>
        
        <div class="span8 user"  style="position: absolute;margin-right: 0px;width: 95%;">
        	<div class="font14" align="right">
        		<table border="0" width="45%">
        			<tr>
        				<td rowspan="2" align="right" width="50%">
        					<img src="<%=Constants.SERV_URL%>/images/icon-user.png" alt="<%=userDeatil.getCompanyName()%>" title="<%=userDeatil.getUserName() %>&nbsp;&nbsp;<%=userDeatil.getUserSurname()%>" />
        				</td>
        				<td align="right">ชื่อผู้ใช้งาน&nbsp;</td>
        				<td align="left"> <span class="text_white"><%=userDeatil.getUserName() %>&nbsp;&nbsp;<%=userDeatil.getUserSurname()%></span></td>
        			</tr>
        			<tr>
        				<td align="right">บริษัทที่สังกัด&nbsp;</td>
        				<td align="left"><span class="text_white"><%=userDeatil.getCompanyName()%></span></td>
        			</tr>
        		</table>
        	</div>
            <div style="height: 5px;"></div>
            <div style="width: 100%;" align="right">
	            <div id="xx" align="center">
	            	<span>เข้าใช้ระบบ : <%=userDeatil.getCurrentDate() %></span>
	            </div>
            </div>
            <div style="height: 2px;"></div>
        </div>
    </div><!-- container -->
</div><!-- headwrap -->
<div id="m1">
  <ul id="menu1" <%if(userDeatil.getFlagChkCompany().equals("Y")){ %> style="visibility: hidden;"<%}%> >
  	<li><a href="javascript:void(0);" id="logOut" name="logOut" >ออกจากระบบ</a></li>
	<%if(userDeatil.getUserUniqueId()!=Constants.ADMIN){ %>
	<li><a href="<%=Constants.SERV_URL%>/EnjoyGenericSrv?service=servlet.ChangePasswordServlet&pageAction=new">เปลี่ยนรหัสผ่าน</a></li>
 	<%} %>
	<%
		UserPrivilegeBean 			userPrivilegeBean 		= null;
		PagesDetailBean 			pagesDetailBean 		= null;
		int							s						= userDeatil.getUserPrivilegeList().size() -1;
		for(int i=s;i >=0;i--){		
			userPrivilegeBean = userDeatil.getUserPrivilegeList().get(i);
	%>
			<li style="text-align: right;" ><a href="javascript:void(0);"><%=userPrivilegeBean.getPrivilegeName()%></a>
				<ul style="list-style-type: none;">
					<%
					for(int j=0;j<userPrivilegeBean.getPagesDetail().size();j++){
						pagesDetailBean = userPrivilegeBean.getPagesDetail().get(j);
					%>	
						<li style="width: 220px;"><a href="<%=pagesDetailBean.getPathPages()%>"><%=pagesDetailBean.getPageNames()%></a></li>
					<%
					}
					%>
				</ul>	
			</li>	
	<%		
		}
	%>
</ul>
</div>










