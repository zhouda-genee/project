<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<link href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">

	<!--[if lt IE 9]>
	    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]--><title>角色指标列表</title>
    <%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
    <base href = "<%=basePath%>">
    
</head>
<body>
	<a href="statistics/indexconfig/role/${requestScope.roles.get(0).rId }">${requestScope.roles.get(0).rName }</a>
	<a href="statistics/indexconfig/role/${requestScope.roles.get(1).rId }">${requestScope.roles.get(1).rName }</a>
	<a href="statistics/indexconfig/role/${requestScope.roles.get(2).rId }">${requestScope.roles.get(2).rName }</a>

<%-- 	<form action="statistics/indexconfig/role?roleId=${sessionScope.roles.get(0).rId }" method="post">
		<input type="submit" class="btn btn-primary" value="${sessionScope.roles.get(0).rName }">
	</form>
	<form action="statistics/indexconfig/role?roleId=${sessionScope.roles.get(1).rId }" method="post">
		<input type="submit" class="btn btn-primary" value="${sessionScope.roles.get(1).rName }">
	</form>
	<form action="statistics/indexconfig/role?roleId=${sessionScope.roles.get(2).rId }" method="post">
		<input type="submit" class="btn btn-primary" value="${sessionScope.roles.get(2).rName }">
	</form>	
 --%>
 </body>
</html>