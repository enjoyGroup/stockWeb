<%@ page import = "th.go.stock.app.enjoy.main.Constants"%>
<%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

if(session != null){ 
	session.invalidate();
	response.sendRedirect(Constants.SERV_URL);
	return;
	
}

%>