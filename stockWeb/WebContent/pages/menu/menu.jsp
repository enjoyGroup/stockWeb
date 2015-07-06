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
				
		        if($(window).scrollTop() > 10){
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
<div align="left" id="m1">
  <ul id="menu1">
	<%
		UserPrivilegeBean 			userPrivilegeBean 		= null;
		PagesDetailBean 			pagesDetailBean 		= null;
		for(int i=0;i<userDeatil.getUserPrivilegeList().size();i++){		
			userPrivilegeBean = userDeatil.getUserPrivilegeList().get(i);
	%>
			<li style="width: 165px;"><a href="#"><%=userPrivilegeBean.getPrivilegeName()%></a>
				<ul>
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
	<%if(!userDeatil.getUserId().equals(Constants.ADMIN)){ %>
 	<li><a href="<%=servURL1%>/EnjoyGenericSrv?service=servlet.ChangePasswordServlet&pageAction=new">เปลี่ยนรหัสผ่าน</a></li>
 	<%} %>
 	<li><a href="#" id="logOut" name="logOut" >ออกจากระบบ</a></li>
  	<!-- 
	<li><a href="#">รายงาน</a>
		<ul>
			<li><a href="/stockWeb/EnjoyGenericSrv?service=servlet.SummaryRevenueOfYearServlet&pageAction=new">รายงานสรุปรายได้ประจำปี</a></li>
			<li><a href="/stockWeb/EnjoyGenericSrv?service=servlet.DetailRevenueOfYearServlet&pageAction=new">รายงานแสดงรายละเอียดรายได้ประจำปี</a></li>
			<li><a href="#">รายงานแสดงรายละเอียดต่างๆ ภายใน Match</a></li>
		</ul></li>
	<li><a href="#">Menu2</a></li>
	<li><a href="#">Menu3</a>
		<ul>
			<li><a href="#">Menu3a</a></li>
			<li><a href="#">Menu3b</a></li>
		</ul></li>
	<li><a href="#">Menu4</a>
		<ul>
			<li><a href="#Menu4a">Menu4a(go to anchor)</a></li>
			<li><a href="#" onclick="alert('Menu4b');return false;">Menu4b(alert)</a></li>
		</ul></li>
	<li><a href="#">Menu5</a>
		<ul>
			<li><a href="#">Menu5a</a></li>
			<li><a href="#">Menu5b</a>
				<ul>
					<li><a href="#">Menu5b-i</a></li>
					<li><a href="#">Menu5b-ii</a></li>
				</ul></li>
			<li><a href="#">Menu5c</a></li>
		</ul>
	</li>
	 -->	
</ul>
</div>










