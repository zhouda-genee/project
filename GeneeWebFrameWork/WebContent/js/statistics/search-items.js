var lab_map = [];
var eq_incharge_map = [];
var eq_contact_map = [];
var user_map = [];

function initSearchItems() {
	// 清除hidden值
	$(".search-list").find("input").val("");
	//加载时间控件
	//$('#dstart').val("");
	//$('#dend').val("");
	$('#dstart').datepicker({
		format : 'yyyy-mm-dd'
	});

	$('#dend').datepicker({
		format : 'yyyy-mm-dd'
	});

	//加载tokenfield
	$('#lab_tokenfield').tokenfield({
		tokens : [],
		limit : 5,
		typeahead : [ {
			hint : true
		}, {
			displayKey : "name",
			source : function(query, process) {
				var jsonRPCobject = new JSONRPC2Object();
				var param = {
					name: query
				};
				jsonRPCobject.id = new Date().getTime();
				jsonRPCobject.method = "lab_name";
				jsonRPCobject.params = JSON.stringify(param);

				$.post(servletPath, jsonRPCobject, function(data) {
					$.each(data.result, function(i, object) {
						lab_map[object.name] = object;
					});
					process(data.result);
				}, "json");
			}
		} ]
	}).on('tokenfield:createtoken', function(e) {
		if (lab_map[e.attrs.value] != undefined) {
			var existingTokens = $(this).tokenfield('getTokens');
			if (existingTokens.length > 0) {
				$.each(existingTokens, function(index, token) {
					if (token.value == e.attrs.value) {
						e.preventDefault();
					}
				});
			}
		} else {
			e.preventDefault();
		}
	}).on('tokenfield:createdtoken', function(e) {
		var existingTokens = $(this).tokenfield('getTokens');
		var ids = "";
		$.each(existingTokens, function(index, token) {
			ids += lab_map[token.value].id + ",";
		});

		ids = ids.substring(0, ids.length - 1);
		$("#lab").val(ids);
	}).on('tokenfield:removedtoken', function(e) {
		var existingTokens = $(this).tokenfield('getTokens');
		var ids = "";
		$.each(existingTokens, function(index, token) {
			ids += lab_map[token.value].id + ",";
		});

		if (existingTokens.length > 0) {
			ids = ids.substring(0, ids.length - 1);
		}

		$("#lab").val(ids);
	});

	$('#eq_incharge_tokenfield').tokenfield({
		tokens : [],
		limit : 5,
		typeahead : [ {
			hint : true
		}, {
			displayKey : "name",
			source : function(query, process) {
				var jsonRPCobject = new JSONRPC2Object();
				var param = {
					name: query
				};
				jsonRPCobject.id = new Date().getTime();
				jsonRPCobject.method = "user_incharge";
				jsonRPCobject.params = JSON.stringify(param);

				$.post(servletPath, jsonRPCobject, function(data) {
					$.each(data.result, function(i, object) {
						eq_incharge_map[object.name] = object;
					});
					process(data.result);
				}, "json");
			}
		} ]
	}).on('tokenfield:createtoken', function(e) {
		if (eq_incharge_map[e.attrs.value] != undefined) {
			var existingTokens = $(this).tokenfield('getTokens');
			if (existingTokens.length > 0) {
				$.each(existingTokens, function(index, token) {
					if (token.value == e.attrs.value) {
						e.preventDefault();
					}
				});
			}
		} else {
			e.preventDefault();
		}
	}).on('tokenfield:createdtoken', function(e) {
		var existingTokens = $(this).tokenfield('getTokens');
		var ids = "";
		$.each(existingTokens, function(index, token) {
			ids += eq_incharge_map[token.value].id + ",";
		});

		ids = ids.substring(0, ids.length - 1);
		$("#eq_incharge").val(ids);
	}).on('tokenfield:removedtoken', function(e) {
		var existingTokens = $(this).tokenfield('getTokens');
		var ids = "";
		$.each(existingTokens, function(index, token) {
			ids += eq_incharge_map[token.value].id + ",";
		});

		if (existingTokens.length > 0) {
			ids = ids.substring(0, ids.length - 1);
		}

		$("#eq_incharge").val(ids);
	});

	$('#eq_contact_tokenfield').tokenfield({
		tokens : [],
		limit : 5,
		typeahead : [ {
			hint : true
		}, {
			displayKey : "name",
			source : function(query, process) {
				var jsonRPCobject = new JSONRPC2Object();
				var param = {
					name: query
				};
				jsonRPCobject.id = new Date().getTime();
				jsonRPCobject.method = "user_contact";
				jsonRPCobject.params = JSON.stringify(param);

				$.post(servletPath, jsonRPCobject, function(data) {
					$.each(data.result, function(i, object) {
						eq_contact_map[object.name] = object;
					});
					process(data.result);
				}, "json");
			}
		} ]
	}).on('tokenfield:createtoken', function(e) {
		if (eq_contact_map[e.attrs.value] != undefined) {
			var existingTokens = $(this).tokenfield('getTokens');
			if (existingTokens.length > 0) {
				$.each(existingTokens, function(index, token) {
					if (token.value == e.attrs.value) {
						e.preventDefault();
					}
				});
			}
		} else {
			e.preventDefault();
		}
	}).on('tokenfield:createdtoken', function(e) {
		var existingTokens = $(this).tokenfield('getTokens');
		var ids = "";
		$.each(existingTokens, function(index, token) {
			ids += eq_contact_map[token.value].id + ",";
		});

		ids = ids.substring(0, ids.length - 1);
		$("#eq_contact").val(ids);
	}).on('tokenfield:removedtoken', function(e) {
		var existingTokens = $(this).tokenfield('getTokens');
		var ids = "";
		$.each(existingTokens, function(index, token) {
			ids += eq_contact_map[token.value].id + ",";
		});

		if (existingTokens.length > 0) {
			ids = ids.substring(0, ids.length - 1);
		}

		$("#eq_contact").val(ids);
	});

	$('#user_tokenfield').tokenfield({
		tokens : [],
		limit : 5,
		typeahead : [ {
			hint : true
		}, {
			displayKey : "name",
			source : function(query, process) {
				var jsonRPCobject = new JSONRPC2Object();
				var param = {
					name: query
				};
				jsonRPCobject.id = new Date().getTime();
				jsonRPCobject.method = "user_recuser";
				jsonRPCobject.params = JSON.stringify(param);

				$.post(servletPath, jsonRPCobject, function(data) {
					$.each(data.result, function(i, object) {
						user_map[object.name] = object;
					});
					process(data.result);
				}, "json");
			}
		} ]
	}).on('tokenfield:createtoken', function(e) {
		if (user_map[e.attrs.value] != undefined) {
			var existingTokens = $(this).tokenfield('getTokens');
			if (existingTokens.length > 0) {
				$.each(existingTokens, function(index, token) {
					if (token.value == e.attrs.value) {
						e.preventDefault();
					}
				});
			}
		} else {
			e.preventDefault();
		}
	}).on('tokenfield:createdtoken', function(e) {
		var existingTokens = $(this).tokenfield('getTokens');
		var ids = "";
		$.each(existingTokens, function(index, token) {
			ids += user_map[token.value].id + ",";
		});

		ids = ids.substring(0, ids.length - 1);
		$("#user").val(ids);
	}).on('tokenfield:removedtoken', function(e) {
		var existingTokens = $(this).tokenfield('getTokens');
		var ids = "";
		$.each(existingTokens, function(index, token) {
			ids += user_map[token.value].id + ",";
		});

		if (existingTokens.length > 0) {
			ids = ids.substring(0, ids.length - 1);
		}

		$("#user").val(ids);
	});

	//加载tagSelector
	var orgRootPath = webPath + 'statistics/result/rootOrganization';
	$.get(orgRootPath, null, function(data) {
		var orgChildPath = webPath + 'statistics/result/childOrganization';
		var receiver = {
			id : $("#eq_org"),
			name : $("#eq_org_name")
		};
		var opt = {
			root_id : data.result.id,
			url : orgChildPath,
			ajax : true,
			receiver : receiver
		};
		$("#eq_org_tagselector").tagSelector(opt);
	}, "json");

	$.get(orgRootPath, null, function(data) {
		var orgChildPath = webPath + 'statistics/result/childOrganization';
		var receiver = {
			id : $("#lab_org"),
			name : $("#lab_org_name")
		};
		var opt = {
			root_id : data.result.id,
			url : orgChildPath,
			ajax : true,
			receiver : receiver
		};
		$("#lab_org_tagselector").tagSelector(opt);
	}, "json");

	var eqRootPath = webPath + 'statistics/result/rootEquipment';
	$.get(eqRootPath, null, function(data) {
		var eqChildPath = webPath + 'statistics/result/childEquipment';
		var receiver = {
			id : $("#eq_type"),
			name : $("#eq_type_name")
		};
		var opt = {
			root_id : data.result.id,
			url : eqChildPath,
			ajax : true,
			receiver : receiver
		};
		$("#eq_type_tagselector").tagSelector(opt);
	}, "json");

	var indexTypePath = webPath + "statistics/result/roleindextype";
	var indexPath = webPath + "statistics/result/roleindex";

	// 查询当前角色对应的所有指标 并填充页面
	$
			.ajax({
				url : indexTypePath,
				cache : false,
				async : false,
				dataType : "json",
				type : "get",
				success : function(indexTypeData) {
					var indexTypeResult = indexTypeData.result;
					$
							.ajax({
								url : indexPath,
								cache : false,
								async : false,
								dataType : "json",
								type : "get",
								success : function(indexData) {
									var indexResult = indexData.result;
									$('#indexContent').empty();

									$
											.each(
													indexTypeResult,
													function(typeNum, typeValue) {

														var rootDiv = $("<div/>");
														var classDiv = $("<div/>").addClass("indextype");
														rootDiv.append(classDiv);
														
														var parentDiv = $(
																"<div/>")
																.attr(
																		{
																			"class" : "indextype-name"
																		});
														var parentLabel = $(
																"<label/>")
																.html(
																		typeValue.tName);

														parentDiv
																.append(parentLabel);
														
														
														

														var childDiv = $(
																"<div/>")
																.attr(
																		{
																			"class" : "index-list"
																		});
														

														$
																.each(
																		indexResult,
																		function(
																				indexNum,
																				indexValue) {
																			if (typeValue.tId == indexValue.tId) {
																				if (indexValue.sCode != "eq_name") {
																					var li = $("<li/>");
																					var checkBox = $(
																							"<input/>")
																							.attr(
																									{
																										"name" : "ckbIndex",
																										"type" : "checkbox",
																										"value" : indexValue.sId,
																										"code" : indexValue.sCode,
																										"index-location" : indexValue.sLocation,
																										"index-description" : indexValue.sDescription,
																										"index-width" : indexValue.sWidth,
																										"index-not-count" : indexValue.sNotCount
																									});

																					var childLabel = $(
																							"<label/>")
																							.html(
																									indexValue.sName);

																					li
																							.append(
																									checkBox)
																							.append(
																									childLabel);
																					
																					childDiv.append(li);
																				}
																			}
																		});

														classDiv
																.append(
																		parentDiv)
																.append(
																		childDiv);

														$("#indexContent")
																.append(rootDiv);
													});
								}
							});
				}
			});
	
	//加载弹出框事件
	$('.close').click(function(){
		$(this).parent().addClass('hide'); 
	 });
}

//加载导出、打印按钮点击事件
function exportExcel() {
	//校验
	var valid = false;
	if ($("#dstart").val() == "" || $("dend").val() == "") {
		$('#alert-warning').removeClass('hide');
		valid = true;
	}
	
	if ($("input[name=ckbIndex]:checked").length == 0) {
		$('#alert-index').removeClass('hide');
		valid = true;
	}
	
	if (valid) {
		return;
	}
	
	var indexId = "";
	var ckbIndex = $("input:checkbox[name=ckbIndex]:checked");

	var indexId = "";
	$.each(ckbIndex, function(index, checkbox) {
		indexId += checkbox.value + ",";
	});

	indexId = indexId.substring(0, indexId.length - 1);
	$("#indexId").val(indexId);

	$("#searchForm").attr("action", "statistics/result/excel");
	$("#searchForm").submit();
}

function printTable() {
	//校验
	var valid = false;
	if ($("#dstart").val() == "" || $("dend").val() == "") {
		$('#alert-warning').removeClass('hide');
		valid = true;
	}
	
	if ($("input[name=ckbIndex]:checked").length == 0) {
		$('#alert-index').removeClass('hide');
		valid = true;
	}
	
	if (valid) {
		return;
	}
	
	var indexId = "";
	var ckbIndex = $("input:checkbox[name=ckbIndex]:checked");

	var indexId = "";
	$.each(ckbIndex, function(index, checkbox) {
		indexId += checkbox.value + ",";
	});

	indexId = indexId.substring(0, indexId.length - 1);
	$("#indexId").val(indexId);

	$("#searchForm").attr("action", "statistics/result/print");
	$("#searchForm").submit();
}