<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.genee.web.framework.utils.prop.PropertiesUtil"%>
<%
	String webPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + this.getServletContext().getContextPath() + "/" ;
	String servletPath = PropertiesUtil.getPropertiesValue("application-config.properties", "servlet_url");
%>
<base href="<%=webPath %>" />
<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.css">
<!-- Bootstrap -->
<link href="css/layout.css" rel="stylesheet">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery/jquery-1.11.1.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap/bootstrap.js"></script>
<script src="js/jquery.overlay.js"></script>
<script>
//jsonrpc2对象
function JSONRPC2Object(id, method, params){
	this.jsonrpc = "2.0";
	this.id = id;
	this.method = method;
	this.params = params;
}

function overlayShow(){
	$(document).overlayshow({
		img: "<%=webPath%>img/statistics/loading40.gif"
	});
}
function overlayHide(){
	$(document).overlayhide();
}

var webPath = "<%=webPath%>";
var servletPath = "<%=servletPath%>";
</script>