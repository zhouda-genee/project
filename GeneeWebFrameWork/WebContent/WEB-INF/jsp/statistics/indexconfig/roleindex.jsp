<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/base.jsp" %>
<%@include file="../common/title.jsp" %>
<%@include file="../common/menu.jsp" %>
<!DOCTYPE">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--[if lt IE 9]>
	    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link href="css/indexconfig.css" rel="stylesheet">
    <title>角色指标列表</title>
</head>
<body>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div>
	  <a href="#" class="link link-tab-primary">统计项设置</a>
	  <a href="#" class="link link-tab-default">绩效评估设置</a>
	</div>
	<div class="pannel">
		<div class="container-fluid">
			<div id="roleconfig" class="accordion">
				<div class="">
					<div class="container-self">
						<a style="font-size: 18px;" data-toggle="collapse" data-parent="#roleconfig" href="#collapseOne">中心管理员</a>						
						<div id="collapseOne" class="accordion-body collapse">
							<div class="accordion-inner">
								<div class="index-grid radio-choice">
									<div class="right" style="margin-right: 3em;">
										<input type="radio" name="sysAdmin"><label>部分(19项)</label>
									</div>
									<div class="right">
										<input type="radio" name="sysAdmin"><label >默认(17项)</label>
									</div>
									<div class="right">
										<input type="radio" name="sysAdmin"><label>全部(43项)</label>
									</div>
									
									<div id="templateOne"></div>
									<input id="sbmOne" type="button"  value="提交" class="link link-primary" onclick="subOne();"/>
								</div>
							<input id="roleOne" type="hidden" value="1" />
							</div>
						</div>
					</div>
				</div>
				
				<div class="accordion-group">
					<div class="accordion-heading container-self">
						<a style="font-size: 18px;" data-toggle="collapse" data-parent="#roleconfig" href="#collapseTwo">课题组PI</a>
						<div id="collapseTwo" class="accordion-body collapse">
							<div class="accordion-inner">
								<div class="index-grid radio-choice">
									<div class="right" style="margin-right: 3em;">
										<input type="radio" name="sysAdmin"><label>部分(19项)</label>
									</div>
									<div class="right">
										<input type="radio" name="sysAdmin"><label >默认(17项)</label>
									</div>
									<div class="right">
										<input type="radio" name="sysAdmin"><label>全部(43项)</label>
									</div>

									<div id="templateTwo"></div>
									<input id="sbmTwo" type="button"  value="提交" class="link link-primary" onclick="subTwo();"/>
								</div>
								<input id="roleTwo" type="hidden" value="2" />
							</div>
						</div>
					</div>
				</div>
				
				<div class="accordion-group">
					<div class="accordion-heading container-self">
						<a style="font-size: 18px;" data-toggle="collapse" data-parent="#roleconfig" href="#collapseThree">仪器管理员</a>
						<div id="collapseThree" class="accordion-body collapse">
							<div class="accordion-inner">
								<div class="index-grid radio-choice">
									<div class="right" style="margin-right: 3em;">
										<input type="radio" name="sysAdmin"><label>部分(19项)</label>
									</div>
									<div class="right">
										<input type="radio" name="sysAdmin"><label >默认(17项)</label>
									</div>
									<div class="right">
										<input type="radio" name="sysAdmin"><label>全部(43项)</label>
									</div>
									
									<div id="templateThree"></div>
									<input id="sbmThree" type="button"  value="提交" class="link link-primary" onclick="subThree();"/>									
								</div>
								<input id="roleThree" type="hidden" value="3" />
							</div>
						</div>
					</div>
				</div>	
			</div>
		</div><!-- container-fluid -->
	</div><!-- pannel -->
</div>	
	<script type="text/javascript">		
		// 定义指标类型的全局变量 
		var types = (function GetTypeJsonString() {
			var result = "";
			$.ajax({
				type:"GET",
				async: false, // 同步加载，因为这组数据要优先获取
				url:"<%=webPath %>" + "statistics/indexconfig/type",
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
				url: "<%=webPath %>" + "statistics/indexconfig/1",
				success: function(infoRole){	
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result.indexs, function(i, indexKey){
						arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
					});							

					$.each(dataType.result, function(i, typeKey){
						$div = $("<div class='index-config-type'><lable>" + typeKey.tName + "</label></div>");		
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
				url: "<%=webPath %>" + "statistics/indexconfig/2",
				success: function(infoRole){
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result.indexs, function(i, indexKey){
						arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
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
				url: "<%=webPath %>" + "statistics/indexconfig/3",
				success: function(infoRole){
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result.indexs, function(i, indexKey){
						arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
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
				else {	
					$.ajax({
						type:"POST",
						url: "statistics/indexconfig/editrole",
						cache: false,
						data: { 
						 "roleId" : roleId,
						 "ckbString" : ckbString
						}
					});
					alert("保存成功！")
				}
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
				else {	
					$.ajax({
						type:"POST",
						url: "statistics/indexconfig/editrole",
						cache: false,
						data: { 
						 "roleId" : roleId,
						 "ckbString" : ckbString
						},
					});
					alert("保存成功！")
				}
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
				else {	
					$.ajax({
						type:"POST",
						url: "statistics/indexconfig/editrole",
						cache: false,
						data: { 
						 "roleId" : roleId,
						 "ckbString" : ckbString
						}
					});
					alert("保存成功！");
				}
			};	 
		 
	</script>    
</body>
</html>