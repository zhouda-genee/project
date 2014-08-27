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
	<script type="text/javascript">
		$(document).ready(function(){
			$.ajax({
				type:"post",
				async:"false", // 同步加载
				url:"<%=basePath %>" + "statistics/indexconfig/role",
				success: function(msg){
					var data = JSON.parse(msg);
					$.each(data.result,function(i,resultKey){
						$btn = $("<button style='border:none; margin-left: 2em;' id='" +  resultKey.rId + "'>" + resultKey.rName +"</button>");
						$btn.click(function(){
							var roleId = $(this).attr("id");
							var url = "<%= basePath %>" + "statistics/indexconfig/editrole";
							if($(this).attr("id") == "1") {
								location.href = url;
							} 
							if($(this).attr("id") == "2") {
								location.href = url;
							}
							if($(this).attr("id") == "3") {
								location.href = url;
							}
						});
						$btn.appendTo("#template");
					});
				},
				error: function(){
					alert("failure");
				}
			});		
		});
	</script>	
</head>
<body>
		<div id="template" style="margin: 10px;">
		</div>
</body>
</html>