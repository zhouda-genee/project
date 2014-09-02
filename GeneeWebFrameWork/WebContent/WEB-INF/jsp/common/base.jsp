<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + this.getServletContext().getContextPath() + "/" ;
%>
<base href="<%=basePath %>" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap.css">
<!-- Bootstrap -->
<link href="css/layout.css" rel="stylesheet">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery-1.11.1.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>