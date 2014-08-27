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
		
    <base href="<%= basePath %>">	
	<script type="text/javascript">
		$(function(){
			$.ajax({
				type:"GET",
				async:false,
				url:"<%=basePath %>" + "statistics/indexconfig/type",
				success: function(infoType){
					var dataType = JSON.parse(infoType);
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					$.ajax({
						type: "POST",
						async: false,
						url: "<%=basePath %>" + "statistics/indexconfig/${requestScope.roleId }",
						success: function(infoRole){
							var dataRole = JSON.parse(infoRole);
							$.each(dataRole.result, function(i, roleKey){
								$.each(roleKey.indexs, function(j, indexKey){
									arrRoleIndex.push(indexKey.sId); // 此处会重复循环，需要优化
								});
							});							

							$.each(dataType.result, function(i, typeKey){
								$div = $("<div><lable>" + typeKey.tName + "</label></div>");		
								$div.appendTo("#template");
								$.each(typeKey.indexs, function(j, indexKey){
									$checkbox = $("<input type='checkbox' name='ckb' value='" + indexKey.sId + "'><label>" + indexKey.sName + "</label>" );
									$checkbox.appendTo("#template");
									if($.inArray(indexKey.sId, arrRoleIndex) != -1){
										$("input[value=" + indexKey.sId +"]").attr("checked", true);
 									}
								});
							});
						}
					});
				}
			});
		});
	</script>
</head>
<body>
	<a style="font-size: 18px;">${requestScope.roleId }</a>
	<form action="statistics/indexconfig/editrole" method="post" id="myFormOne">
		<div id="template" style="margin: 10px;" ></div>
		<input type="submit"  value="提交" class="btn btn-primary" />
	</form>
</body>
</html>