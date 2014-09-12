<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	String webPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + this.getServletContext().getContextPath() + "/" ;
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指标初始化</title>
<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.7.2/jquery.min.js"></script>
<script>
	function run(){
		var startDate = $("input[name='startdate']").val();
		var endDate = $("input[name='enddate']").val();
		$.ajax({
			url : "<%=webPath%>index/run",
			data : "startdate="+startDate+"&enddate="+endDate,
			async : true,
			dataType : "json",
			type : "get",
			success : function(data) {
				alert(data["request-status"]);
			}
		});
	}
</script>
</head>
<body>
	开始日期：<input type="text" name="startdate"/>
	结束日期：<input type="text" name="enddate"/>
	<button onclick="run()">提交</button>
</body>
</html>