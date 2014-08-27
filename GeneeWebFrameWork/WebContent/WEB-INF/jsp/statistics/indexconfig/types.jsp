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
    <![endif]--><title>指标类型列表</title>
    
    <%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#btn").click(function(){
				$.ajax({
					type:"post",
					async:"false",
					url:"<%=basePath%>" + "statistics/indexconfig/type",
					success: function(msg){
						var data = JSON.parse(msg);
						$.each(data.result,function(i,resultKey){
							$div = $("<div><input type='checkbox' checked='true' value='" +  resultKey.tId + "'><label>" + resultKey.tName + "</label></div>");
							$div.appendTo("#template");
							$.each(resultKey.indexs,function(j,indexKey){
 								var $checkbox = $("<input type='checkbox' checked='true' value='" + indexKey.sId + "'><label>" + indexKey.sName + "</label>" );
								$checkbox.appendTo("#template");
							});
						});
					},
					error: function(){
						alert("failure");
					}
				});				
			});
		});
	</script>	
</head>
<body>
	<input type="button"  id="btn" class="btn btn-primary" value="配置统计项">
	<form action="">
		<div id="template" style="margin: 10px;">
		</div>
		<input type="button" value="保存" class="btn btn-primary">
	</form>
</body>
</html>