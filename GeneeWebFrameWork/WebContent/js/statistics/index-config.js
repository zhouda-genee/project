/** 
 *	通过ajax请求到角色和指标数据，并动态显示
 */

	var typePath = webPath + 'statistics/type/';
	var roleOnePath = webPath + 'statistics/1';
	var roleTwoPath = webPath + 'statistics/2';
	var roleThreePath = webPath + 'statistics/3';
	var editIndexPath = webPath + 'statistics/editindex';
	
		// 定义指标类型的全局变量 
		var types = (function GetTypeJsonString() {
			var result = "";
			$.ajax({
				type:"GET",
				async: false, // 同步加载，因为这组数据要优先获取
				url: typePath,
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
				url: roleOnePath,
				success: function(infoRole){	
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result.indexs, function(i, indexKey){
						arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
					});							

					$.each(dataType.result, function(i, typeKey){
						// 指标类型最外层的div
						$div = $("<div class='indextype' id='" + 1 + "_" + typeKey.tId + "'>" + "</div>");
						$div.appendTo("#templateOne");
						// 指标类型名称的div
						$divType = $("<div class='indextype-name'><lable>" + typeKey.tName + "</label></div>");		
						$divType.appendTo("#1_" + typeKey.tId);
						// 对应类型所有指标名称的div
						$divIndex = $("<div class='index-list' id='" + 1 + "-" + typeKey.tId + "'>" + "</div>");
						$divIndex.appendTo("#1_" + typeKey.tId);
						$.each(typeKey.indexs, function(j, indexKey){
							$index = $("<li><input type='checkbox' name='ckbOne' value='" + indexKey.sId + "'><label>" + indexKey.sName + "</label></li>" );
							$index.appendTo("#1-" + typeKey.tId);
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
				url: roleTwoPath,
				success: function(infoRole){
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result.indexs, function(i, indexKey){
						arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
					});

					$.each(dataType.result, function(i, typeKey){
						// 指标类型最外层的div
						$div = $("<div class='indextype' id='" + 2 + "_" + typeKey.tId + "'>" + "</div>");
						$div.appendTo("#templateTwo");
						// 指标类型名称的div
						$divType = $("<div class='indextype-name'><lable>" + typeKey.tName + "</label></div>");		
						$divType.appendTo("#2_" + typeKey.tId);
						// 对应类型所有指标名称的div
						$divIndex = $("<div class='index-list' id='" + 2 + "-" + typeKey.tId + "'>" + "</div>");
						$divIndex.appendTo("#2_" + typeKey.tId);
						$.each(typeKey.indexs, function(j, indexKey){
							$index = $("<li><input type='checkbox' name='ckbTwo' value='" + indexKey.sId + "'><label>" + indexKey.sName + "</label></li>" );
							$index.appendTo("#2-" + typeKey.tId);
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
				url: roleThreePath,
				success: function(infoRole){
					var arrRoleIndex = []; // 用来存放角色已拥有指标ID的数组
					var dataRole = JSON.parse(infoRole);
					$.each(dataRole.result.indexs, function(i, indexKey){
						arrRoleIndex.push(indexKey.sId); // 此处会出现多余的循环，需要优化
					});

					$.each(dataType.result, function(i, typeKey){
						// 指标类型最外层的div
						$div = $("<div class='indextype' id='" + 3 + "_" + typeKey.tId + "'>" + "</div>");
						$div.appendTo("#templateThree");
						// 指标类型名称的div
						$divType = $("<div class='indextype-name'><lable>" + typeKey.tName + "</label></div>");		
						$divType.appendTo("#3_" + typeKey.tId);
						// 对应类型所有指标名称的div
						$divIndex = $("<div class='index-list' id='" + 3 + "-" + typeKey.tId + "'>" + "</div>");
						$divIndex.appendTo("#3_" + typeKey.tId);
						$.each(typeKey.indexs, function(j, indexKey){
							$index = $("<li><input type='checkbox' name='ckbThree' value='" + indexKey.sId + "'><label>" + indexKey.sName + "</label></li>" );
							$index.appendTo("#3-" + typeKey.tId);
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
				var roleId = 1;
				$("input[name='ckbOne']:checked").each(function(){
					ckbString += $(this).val() + ",";
				});
				
				if(ckbString == "") {
					$('#alert-warning').removeClass('hide');
				}
				else {	
					$.ajax({
						type:"POST",
						url: editIndexPath,
						cache: false,
						data: { 
						 "roleId" : roleId,
						 "ckbString" : ckbString
						}
					});
					$('#alert-success').removeClass('hide');
				}
			};
		 
			function subTwo() {
		 		var ckbString = "";
				var roleId = 2;
				$("input[name='ckbTwo']:checked").each(function(){
					ckbString += $(this).val() + ",";
				});
				
				if(ckbString == "") {
					$('#alert-warning').removeClass('hide');
				}
				else {	
					$.ajax({
						type:"POST",
						url: editIndexPath,
						cache: false,
						data: { 
						 "roleId" : roleId,
						 "ckbString" : ckbString
						},
					});
					$('#alert-success').removeClass('hide');
				}
			};
		
			function subThree() {
		 		var ckbString = "";
				var roleId = 3;
				$("input[name='ckbThree']:checked").each(function(){
					ckbString += $(this).val() + ",";
				});
				
				if(ckbString == "") {
					$('#alert-warning').removeClass('hide');
				}
				else {	
					$.ajax({
						type:"POST",
						url: editIndexPath,
						cache: false,
						data: { 
						 "roleId" : roleId,
						 "ckbString" : ckbString
						}
					});
					$('#alert-success').removeClass('hide');
				}
			};