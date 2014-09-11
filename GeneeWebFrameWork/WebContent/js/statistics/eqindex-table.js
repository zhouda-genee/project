// 指标对象
function indexEntity(name, code, id, width, notCount) {
	this.name = name;
	this.code = code;
	this.id = id;
	this.width = width;
	this.notCount = notCount;
}

// 分页结果集
function EquipmentIndexPageSupport(indexData, pageCount, pageSize, page) {
	this.indexData = indexData;
	this.pageCount = pageCount;
	this.pageSize = pageSize;
	this.page = page;
}

// 自定义统计列表参数对象
function SearchParam(eq_name, eq_type, eq_org, eq_contact, eq_incharge,
		lab_org, lab, user, dstart, dend, sort_name, sort, page, size) {
	this.eq_name = eq_name;
	this.eq_type = eq_type;
	this.eq_org = eq_org;
	this.eq_contact = eq_contact;
	this.eq_incharge = eq_incharge;
	this.lab_org = lab_org;
	this.lab = lab;
	this.user = user;
	this.dstart = dstart;
	this.dend = dend;
	this.sort_name = sort_name;
	this.sort = sort;
	this.size = size;
	this.page = page;
}

// 获取查询条件
function getSearchParam(page, size) {
	var searchParam = new SearchParam();
	searchParam.size = size;
	searchParam.page = page;
	// 仪器名称
	var eq_name = $("#eq_name").val();
	if (eq_name != null && eq_name != "") {
		searchParam.eq_name = eq_name;
	}
	// 仪器类型
	var eq_type = $("#eq_type").val();
	if (eq_type != null && eq_type != "") {
		searchParam.eq_type = eq_type;
	}
	// 仪器组织机构
	var eq_org = $("#eq_org").val();
	if (eq_org != null && eq_org != "") {
		searchParam.eq_org = eq_org;
	}
	// 仪器负责人
	var eq_incharge = $("#eq_incharge").val();
	if (eq_incharge != null && eq_incharge != "") {
		searchParam.eq_incharge = eq_incharge;
	}
	// 仪器联系人
	var eq_contact = $("#eq_contact").val();
	if (eq_contact != null && eq_contact != "") {
		searchParam.eq_contact = eq_contact;
	}
	// 课题组组织机构
	var lab_org = $("#lab_org").val();
	if (lab_org != null && lab_org != "") {
		searchParam.lab_org = lab_org;
	}
	// 课题组
	var lab = $("#lab").val();
	if (lab != null && lab != "") {
		searchParam.lab = lab;
	}
	// 使用者
	var user = $("#user").val();
	if (user != null && user != "") {
		searchParam.user = user;
	}

	var dstart = $("#dstart").val().replace(/-/g, '/') + " 00:00:00";
	searchParam.dstart = new Date(dstart).getTime();
	var dend = $("#dend").val().replace(/-/g, '/') + " 23:59:59";
	searchParam.dend = new Date(dend).getTime();
	return searchParam;
}

// 填充搜索结果中的搜索项
function fillSearchItem() {
	// 仪器名称
	$("#s_eq_name").val($("#eq_name").val());
	
	// 仪器类型
	$("#s_eq_type").val($("#eq_type_name").val());
	
	// 仪器组织机构
	$("#s_eq_org").val($("#eq_org_name").val());
	
	// 仪器负责人
	var incharge_tokens = $("#eq_incharge_tokenfield").tokenfield('getTokens');
	var incharge_label = "";
	$.each(incharge_tokens, function(index, token) {
		incharge_label += token.value + ",";
	});
	if (incharge_tokens.length > 0) {
		incharge_label = incharge_label.substring(0, incharge_label.length - 1);
	}
	$("#s_eq_incharge").val(incharge_label);
	
	// 仪器联系人
	var contact_tokens = $("#eq_contact_tokenfield").tokenfield('getTokens');
	var contact_label = "";
	$.each(contact_tokens, function(index, token) {
		contact_label += token.value + ",";
	});
	if (contact_tokens.length > 0) {
		contact_label = contact_label.substring(0, contact_label.length - 1);
	}
	$("#s_eq_contact").val(contact_label);
	
	// 课题组组织机构
	$("#s_lab_org").val($("#lab_org_name").val());
	
	// 课题组
	var lab_tokens = $("#lab_tokenfield").tokenfield('getTokens');
	var lab_label = "";
	$.each(lab_tokens, function(index, token) {
		lab_label += token.value + ",";
	});
	if (lab_tokens.length > 0) {
		lab_label = lab_label.substring(0, lab_label.length - 1);
	}
	$("#s_lab").val(lab_label);
	
	// 使用者
	var user_tokens = $("#user_tokenfield").tokenfield('getTokens');
	var user_label = "";
	$.each(user_tokens, function(index, token) {
		user_label += token.value + ",";
	});
	if (user_tokens.length > 0) {
		user_label = user_label.substring(0, user_label.length - 1);
	}
	$("#s_user").val(user_label);

	$("#s_dstart").val($("#dstart").val());
	$("#s_dend").val($("#dend").val());
}

// 获取仪器统计列表数据
function getEquipmentIndexData(param) {

	var jsonRPCobject = new JSONRPC2Object();
	jsonRPCobject.id = new Date().getTime();
	jsonRPCobject.method = "statistics_eqindex_page";
	jsonRPCobject.params = JSON.stringify(param);

	var equipmentIndexDataPageSupport = new EquipmentIndexPageSupport();
	// 查询统计结果
	$.ajax({
		url : servletPath,
		data : jsonRPCobject,
		cache : false,
		async : false,
		dataType : "json",
		type : "post",
		success : function(data) {
			var result = data.result;
			equipmentIndexDataPageSupport.pageCount = result.pageCount;
			equipmentIndexDataPageSupport.pageSize = result.pageSize;
			equipmentIndexDataPageSupport.page = result.page;
			equipmentIndexDataPageSupport.indexData = result.items;
		}
	});
	return equipmentIndexDataPageSupport;
}

// 获取仪器总计值
function getEquipmentIndexCount(param) {

	var jsonRPCobject = new JSONRPC2Object();
	jsonRPCobject.id = new Date().getTime();
	jsonRPCobject.method = "statistics_eqindex_count";
	jsonRPCobject.params = JSON.stringify(param);

	var result;

	// 查询统计结果
	$.ajax({
		url : servletPath,
		data : jsonRPCobject,
		cache : false,
		async : false,
		dataType : "json",
		type : "post",
		success : function(data) {
			result = data.result;
		}
	});
	return result;
}

// 创建表头左侧
function buildTableHeaderLeft() {
	var html = "<table><tr><th class=\"eq-name-cell\">仪器名称</th></tr></table>";
	$("#table-left-head").empty().append(html);
}

// 创建表头右侧
function buildTableHeaderRight() {
	var indexEntityArray = new Array();

	var html = "<table>";
	html += "<tr>";

	$("div .index-grid").find("div .type").each(
			function() {
				// 指标类型的div
				var indexTypeDiv = $(this);
				// 指标类型名称
				var indexTypeText = indexTypeDiv.children("label").text();
				// 当前指标类型里所选择的指标
				var indexOption = $(indexTypeDiv.next("div .index")[0])
						.children("input:checked");
				// 指标类型合并单元格数量
				if (indexOption.size() != 0) {
					var width = 0;
					indexOption.each(function(i) {
						var o = $(this);
						var index = new indexEntity();
						index.code = o.attr("code");
						index.width = o.attr("index-width");
						index.notCount = o.attr("index-not-count");
						index.id = o.val();
						index.name = $(o.next("label")[0]).text();
						indexEntityArray.push(index);
						width += parseInt(index.width);
					});
					html += "<th style=\"width: " + width + "px;\" colspan=\""
							+ indexOption.size() + "\">" + indexTypeText
							+ "</th>";
				}
			});
	html += "</tr>";
	html += "<tr>"
	for (var i = 0; i < indexEntityArray.length; i++) {
		html += "<th style=\"width: " + indexEntityArray[i].width + "px;\">"
				+ indexEntityArray[i].name + "</th>";
	}
	html += "</tr>";
	html += "</table>";
	$("#table-right-head").empty().append(html);
	return indexEntityArray;
}

// 创建统计列表右侧
function buildTableBodyRight(equipmentIndexPageSupport, indexEntityArray,
		isEmpty) {
	var equipmentIndexDataArray = equipmentIndexPageSupport.indexData;
	var html = "";
	for (var i = 0; i < equipmentIndexDataArray.length; i++) {
		html += "<tr>";
		var equipmentIndexData = equipmentIndexDataArray[i];
		for (var j = 0; j < indexEntityArray.length; j++) {
			var index = equipmentIndexData[indexEntityArray[j].code];
			if (index == 0) {
				html += "<td class=\"text_overflow\" style=\"width:"
						+ indexEntityArray[j].width + "px\">-</td>";
			} else {
				html += "<td class=\"text_overflow\" style=\"width:"
						+ indexEntityArray[j].width + "px\">" + index + "</td>";
			}
		}
		html += "</tr>";
	}
	if (isEmpty) {
		$("#table-right-body").empty().append("<table>" + html + "</table>");
	} else {
		$("#table-right-body > table").append(html);
	}
	interleaveChangeColor($("#table-right-body > table"));
}
// 创建统计列表左侧体
function buildTableBodyLeft(equipmentIndexPageSupport, isEmpty) {
	var equipmentIndexDataArray = equipmentIndexPageSupport.indexData;
	var html = "";
	for (var i = 0; i < equipmentIndexDataArray.length; i++) {
		var equipmentIndexData = equipmentIndexDataArray[i];
		html += "<tr><td class=\"text_overflow\">"
				+ equipmentIndexData["eq_name"] + "</td></tr>";
	}
	html += "";
	if (isEmpty) {
		$("#table-left-body").empty().append("<table>" + html + "</table>");
	} else {
		$("#table-left-body > table").append(html);
	}

	interleaveChangeColor($("#table-left-body > table"));

}

// 创建统计总计左侧
function buildTableFootLeft(equipmentIndexCount) {
	var html = "<table><tr><td>" + "总计&nbsp;&nbsp;仪器台数："
			+ equipmentIndexCount.eq_count + "</td></tr></table>";
	$("#table-left-foot").empty().append(html);
}

// 创建统计总计右侧
function buildTableFootRight(equipmentIndexCount, indexEntityArray) {
	var html = "<table>";
	html += "<tr>";
	for (var j = 0; j < indexEntityArray.length; j++) {
		var index = indexEntityArray[j];
		// 0 代表统计
		if (index.notCount == "0") {
			var data = equipmentIndexCount[index.code];
			if (data == 0) {
				html += "<td style=\"width:" + index.width + "px\">-</td>";
			} else {
				html += "<td class=\"text_overflow\" style=\"width:"
						+ index.width + "px\">" + data + "</td>";
			}
		} else {
			html += "<td style=\"width:" + index.width + "px\">-</td>";
		}
	}
	html += "</tr>";
	html += "</table>";
	$("#table-right-foot").empty().append(html);
}

function tableScroll() {
	var a = document.getElementById("table-right-body").scrollTop;
	var b = document.getElementById("table-right-body").scrollLeft;
	document.getElementById("table-left-body").scrollTop = a;
	document.getElementById("table-right-head").scrollLeft = b;
	document.getElementById("table-right-foot").scrollLeft = b;
}

function interleaveChangeColor(object) {
	object.find("tr:even").css("background", "#C00");
	object.find("tr:odd").css("background", "#09F");
}