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
			
			var h = $(document).height();
			
			//$("#xx").corner("round 8px");
			
			$('#menu1').ptMenu();
			$(window).scroll(function(){//alert($(window).scrollTop());
				
				h = $(document).height();
				//var options = {};
				
		        if(document.documentElement.scrollTop || jQuery(this).scrollTop() > 10){//alert("s " + h); 
		        	document.getElementById("m1").className 			= "fixs";
		        	document.getElementById("headwrap").style.display 	= "none";
		        	
		        	//document.getElementById("m1").className 			= "fixs";
		        	//$("#headwrap").removeAttr("style").hide();
		        	
		        }else{
		        	document.getElementById("m1").className 			= "";
		        	document.getElementById("headwrap").style.display 	= "block";
		        	
		        	//$("#headwrap").show("slide",{direction: 'up'}, 1000);
		        	//document.getElementById("m1").className 			= "";
		        }
		        $('body').css('height', h);
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
        	<img src="<%=Constants.SERV_URL%>/images/logo.png" />
        </div>
        
        <div class="span8 user"  style="position: absolute;margin-right: 0px;width: 95%;">
        	<div class="font14"><img src="<%=Constants.SERV_URL%>/images/icon-user.png" alt="">ชื่อผู้ใช้งาน <span class="text_white"><%=userDeatil.getUserName() %>&nbsp;&nbsp;<%=userDeatil.getUserSurname() %></span></div>
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
	<%if(!userDeatil.getUserId().equals(Constants.ADMIN)){ %>
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










