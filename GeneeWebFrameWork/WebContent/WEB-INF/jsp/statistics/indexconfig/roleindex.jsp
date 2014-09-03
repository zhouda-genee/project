<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
	
	<!--[if lt IE 9]>
	    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<style type="text/css">
		.accordion{
			margin-bottom:20px
		}
		
		.accordion-group{
			margin-bottom:2px;
			border:1px solid #e5e5e5;
			-webkit-border-radius:4px;
			-moz-border-radius:4px;
			border-radius:4px
		}
		
		.accordion-heading{
			border-bottom:0
		}
		
		.accordion-heading .accordion-toggle{
			display:block;
			padding:8px 15px
		}
		
		.accordion-toggle{
			cursor:pointer
		}
		
		.accordion-inner{
			padding:9px 15px;
			border-top:1px solid #e5e5e5
		}
	</style>
	
    <title>角色指标列表</title>
    <%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
    <base href="<%= basePath %>">	
</head>
<body>
	<div class="container-fluid">
		<div id="roleconfig" class="accordion">
			<div class="accordion-group">
				<div class="accordion-heading">
					<a style="font-size: 18px;" data-toggle="collapse" data-parent="#roleconfig" href="#collapseOne">中心管理员</a>
					<div id="collapseOne" class="accordion-body collapse">
						<div class="accordion-inner">
							<div id="templateOne" style="margin: 10px;" ></div>
							<input id="roleOne" type="hidden" value="1" />
							<input id="sbmOne" type="button"  value="提交" onclick="subOne();"/>
						</div>
					</div>
				</div>
			</div>
			
			<div id="result">
			</div>
			
			<div class="accordion-group">
				<div class="accordion-heading">
					<a style="font-size: 18px;" data-toggle="collapse" data-parent="#roleconfig" href="#collapseTwo">课题组PI</a>
					<div id="collapseTwo" class="accordion-body collapse">
						<div class="accordion-inner">
							<div id="templateTwo" style="margin: 10px;" ></div>
							<input id="roleTwo" type="hidden" value="2" />
							<input id="sbmTwo" type="button"  value="提交" class="btn btn-primary" onclick="subTwo();"/>
						</div>
					</div>
				</div>
			</div>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a style="font-size: 18px;" data-toggle="collapse" data-parent="#roleconfig" href="#collapseThree">仪器管理员</a>
					<div id="collapseThree" class="accordion-body collapse">
						<div class="accordion-inner">
							<div id="templateThree" style="margin: 10px;" ></div>
							<input id="roleThree" type="hidden" value="3" />
							<input id="sbmThree" type="button"  value="提交" class="btn btn-primary" onclick="subThree();"/>
						</div>
					</div>
				</div>
			</div>	
		</div>
	</div>
	
	<script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>	
	<script type="text/javascript">		
		// 定义指标类型的全局变量 
		var types = (function GetTypeJsonString() {
			var result = "";
			$.ajax({
				type:"GET",
				async: false, // 同步加载，因为这组数据要优先获取
				url:"<%=basePath %>" + "statistics/indexconfig/type",
				success: function(infoType){
					result = infoType;
				}
			});
			return result;			
		})();
		// 将 json字符串转化为json对象
		var dataType = JSON.parse(types);
		$(function(){
			// 这段代码重复性太强，需要优化
			// 对中心管理员的数据请求
			$.ajax({
				type: "GET",
				url: "<%=basePath %>" + "statistics/indexconfig/1",
				success: function(infoRole){
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result, function(i, roleKey){
						$.each(roleKey.indexs, function(j, indexKey){
							arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
						});
					});							

					$.each(dataType.result, function(i, typeKey){
						$div = $("<div><lable>" + typeKey.tName + "</label></div>");		
						$div.appendTo("#templateOne");
						$.each(typeKey.indexs, function(j, indexKey){
							$checkbox = $("<input type='checkbox' name='ckbOne' value='" + indexKey.sId + "'><label>" + indexKey.sName + "</label>" );
							$checkbox.appendTo("#templateOne");
							if($.inArray(indexKey.sId, arrRoleIndex) != -1){
								$("input[name='ckbOne'][value=" + indexKey.sId + "]").attr("checked", true);
							}
						});
					});
				}
			});
			
			// 对课题组PI的数据请求
			$.ajax({
				type: "GET",
				url: "<%=basePath %>" + "statistics/indexconfig/2",
				success: function(infoRole){
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result, function(i, roleKey){
						$.each(roleKey.indexs, function(j, indexKey){
							arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
						});
					});							

					$.each(dataType.result, function(i, typeKey){
						$div = $("<div><lable>" + typeKey.tName + "</label></div>");		
						$div.appendTo("#templateTwo");
						$.each(typeKey.indexs, function(j, indexKey){
							$checkbox = $("<input type='checkbox' name='ckbTwo' value='" + indexKey.sId + "'><label>" + indexKey.sName + "</label>" );
							$checkbox.appendTo("#templateTwo");
							if($.inArray(indexKey.sId, arrRoleIndex) != -1){
								$("input[name='ckbTwo'][value=" + indexKey.sId + "]").attr("checked", true);
							}
						});
					});
				}
			});
			
			// 对仪器管理员的数据请求
			$.ajax({
				type: "GET",
				url: "<%=basePath %>" + "statistics/indexconfig/3",
				success: function(infoRole){
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result, function(i, roleKey){
						$.each(roleKey.indexs, function(j, indexKey){
							arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
						});
					});							

					$.each(dataType.result, function(i, typeKey){
						$div = $("<div><lable>" + typeKey.tName + "</label></div>");		
						$div.appendTo("#templateThree");
						$.each(typeKey.indexs, function(j, indexKey){
							$checkbox = $("<input type='checkbox' name='ckbThree' value='" + indexKey.sId + "'><label>" + indexKey.sName + "</label>" );
							$checkbox.appendTo("#templateThree");
							if($.inArray(indexKey.sId, arrRoleIndex) != -1){
								$("input[name='ckbThree'][value=" + indexKey.sId + "]").attr("checked", true);
							}
						});
					});
				}
			});
		});
		
		/** 
		 *	jquery 提交form表单中的数据，一共提交两组数据（一组是roleId， 一组是由被选中复选框构成的value数组)
		 *	提交到controller中的editRole方法进行处理
		 */
			function subOne() {
		 		var ckbString = "";
				var roleId = $("input#roleOne").val();
				$("input[name='ckbOne']:checked").each(function(){
					ckbString += $(this).val() + ",";
				});
				
				if(ckbString == "") {
					alert("您还没有勾选指标！");
				}
					
				$.ajax({
					type:"POST",
					url: "statistics/indexconfig/editrole",
					cache: false,
					data: { 
					 "roleId" : roleId,
					 "ckbString" : ckbString
					}
				});
			};
		 
			function subTwo() {
		 		var ckbString = "";
				var roleId = $("input#roleTwo").val();
				$("input[name='ckbTwo']:checked").each(function(){
					ckbString += $(this).val() + ",";
				});
				
				if(ckbString == "") {
					alert("您还没有勾选指标！");
				}
					
				$.ajax({
					type:"POST",
					url: "statistics/indexconfig/editrole",
					cache: false,
					data: { 
					 "roleId" : roleId,
					 "ckbString" : ckbString
					},
				});
			};
		
			function subThree() {
		 		var ckbString = "";
				var roleId = $("input#roleThree").val();
				$("input[name='ckbThree']:checked").each(function(){
					ckbString += $(this).val() + ",";
				});
				
				if(ckbString == "") {
					alert("您还没有勾选指标！");
				}
					
				$.ajax({
					type:"POST",
					url: "statistics/indexconfig/editrole",
					cache: false,
					data: { 
					 "roleId" : roleId,
					 "ckbString" : ckbString
					}
				});
			};	 
		 
	</script>    
</body>
</html>