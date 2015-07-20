<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="th.go.stock.app.enjoy.bean.UserDetailsBean,th.go.stock.app.enjoy.bean.UserPrivilegeBean,th.go.stock.app.enjoy.bean.PagesDetailBean,th.go.stock.app.enjoy.main.Constants"%>
<%
	final String servURL1		= Constants.SERV_URL;
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
	</style>
	<script>
		$(document).ready(function(){
			
			var h = $(document).height();
			
			$('#menu1').ptMenu();
			$(window).scroll(function(){//alert($(window).scrollTop());
				
				h = $(document).height();
				
		        if(document.documentElement.scrollTop || jQuery(this).scrollTop() > 10){//alert("s " + h);
		        	document.getElementById("m1").className 			= "fixs";
		        	document.getElementById("headwrap").style.display 	= "none";
		        }else{
		        	document.getElementById("m1").className 			= "";
		        	document.getElementById("headwrap").style.display 	= "block";
		        }
		        $('body').css('height', h);
		    });
			
			$('#logOut').click(function(){ 
				try{
					if(confirm("ต้องการออกจากระบบ?")){   
						window.location.replace('<%=servURL1%>');
					}	
				}catch(err){
					alert("logOut :: " + err);
				}
				
			});		
		});
	</script>
<div class="headwrap" id="headwrap">
	<div class="row" style="position: relative;">
    	<div class="brand span4" style="padding-left: 15px;">
        	<img src="<%=servURL1%>/images/logo2.png" />
        </div>
        
        <div class="span8 user"  style="position: absolute;margin-right: 0px;width: 95%;">
        	<div class="font14"><img src="<%=servURL1%>/images/icon-user.jpg" alt="">ชื่อผู้ใช้งาน <span class="text_white"><%=userDeatil.getUserName() %>&nbsp;&nbsp;<%=userDeatil.getUserSurname() %></span></div>
            <div class="font12"></div>
            
            <ul>
            	<li class="date padding-md round-md" style="background:rgba(166, 19, 53, 0.37);border:1px solid rgb(194, 27, 27);">เข้าใช้ระบบ : <span class="text_white"><%=userDeatil.getCurrentDate() %></span> </li>
            </ul>
            
        </div>
    </div><!-- container -->
</div><!-- headwrap -->
<div id="m1">
  <ul id="menu1" >
  	<li style="width: 180px;"><a href="#" id="logOut" name="logOut" >ออกจากระบบ</a></li>
	<%if(!userDeatil.getUserId().equals(Constants.ADMIN)){ %>
	<li style="width: 180px;"><a href="<%=servURL1%>/EnjoyGenericSrv?service=servlet.ChangePasswordServlet&pageAction=new">เปลี่ยนรหัสผ่าน</a></li>
 	<%} %>
	<%
		UserPrivilegeBean 			userPrivilegeBean 		= null;
		PagesDetailBean 			pagesDetailBean 		= null;
		int							s						= userDeatil.getUserPrivilegeList().size() -1;
		for(int i=s;i >=0;i--){		
			userPrivilegeBean = userDeatil.getUserPrivilegeList().get(i);
	%>
			<li style="width: 180px;" ><a href="#"><%=userPrivilegeBean.getPrivilegeName()%></a>
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










