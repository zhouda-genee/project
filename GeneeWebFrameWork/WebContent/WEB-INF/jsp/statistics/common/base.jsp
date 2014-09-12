<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.genee.web.framework.utils.prop.PropertiesUtil"%>
<%
	String webPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + this.getServletContext().getContextPath() + "/" ;
	String servletPath = PropertiesUtil.getPropertiesValue("application-config.properties", "servlet_url");
%>
<%@ include file="session.jsp" %>
<base href="<%=webPath %>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>统计列表</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="css/statistics/layout.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
	<script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery/jquery-1.11.1.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap/bootstrap.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
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
		img: "<%=webPath%>icon/loading40.gif"
	});
}
function overlayHide(){
	$(document).overlayhide();
}

var webPath = "<%=webPath%>";
var servletPath = "<%=servletPath%>";
</script>