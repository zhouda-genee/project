<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@include file="../common/base.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>统计列表</title>
<style type="text/css">
.text_overflow{white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; -moz-binding:url('ellipsis.xml#ellipsis'); overflow:hidden;}
</style>
<script src="js/statistics/eqindex-table.js"></script>

<link href="css/bootstrap/bootstrap-tokenfield.css" rel="stylesheet"/>
<link href="css/bootstrap/tokenfield-typeahead.css" rel="stylesheet"/>
<link href="css/bootstrap/bootstrap-datepicker.css" rel="stylesheet"/>
<link href="css/tag.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="js/bootstrap/bootstrap-tokenfield.js"></script>
<script type="text/javascript" src="js/bootstrap/typeahead.bundle.js"></script>
<script type="text/javascript" src="js/bootstrap/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="js/tag_selector.js"></script>
<script>
	$(document).ready(function() {
		var contactPath = webPath + 'statistics/result/contact';
		var inchargePath = webPath + 'statistics/result/incharge';
		var labPath = webPath + 'statistics/result/lab';
		var userPath = webPath + 'statistics/result/user';
		var lab_map = [];
		var eq_incharge_map = [];
		var eq_contact_map = [];
		var user_map = [];	
		
		$('#dstart').val("");
		$('#dend').val("");
		$('#dstart').datepicker({
			format: 'yyyy-mm-dd'
		});
		
		$('#dend').datepicker({
			format: 'yyyy-mm-dd'
		});
		
		$('#lab_tokenfield').tokenfield({
			tokens : [],
			limit : 5,
			typeahead: [
			{
      			hint: true
    			}, 
			{
				displayKey: "name",
				source: function (query, process) {
			        var parameter = {name: query};

					$.post(labPath, parameter, function (data) {
						$.each(data.result, function(i, object) {
							lab_map[object.name] = object;
			            });
			            process(data.result);
			        }, "json");
			    }
			}]
		}).on('tokenfield:createtoken', function (e) {
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
		}).on('tokenfield:createdtoken', function (e) {
			var existingTokens = $(this).tokenfield('getTokens');
			var ids = "";
			$.each(existingTokens, function(index, token) {
				ids += lab_map[token.value].id + ",";
            });

			ids = ids.substring(0,ids.length-1);
			$("#lab").val(ids);
		}).on('tokenfield:removedtoken', function (e) {
			var existingTokens = $(this).tokenfield('getTokens');
			var ids = "";
			$.each(existingTokens, function(index, token) {
				ids += lab_map[token.value].id + ",";	
            });
			
			if (existingTokens.length > 0) {
				ids = ids.substring(0,ids.length-1);
			}

			$("#lab").val(ids);
		});
		
		$('#eq_incharge_tokenfield').tokenfield({
			tokens : [],
			limit : 5,
			typeahead: [
			{
      			hint: true
    			}, 
			{
				displayKey: "name",
				source: function (query, process) {
			        var parameter = {name: query};

					$.post(inchargePath, parameter, function (data) {
						$.each(data.result, function(i, object) {
							eq_incharge_map[object.name] = object;
			            });
			            process(data.result);
			        }, "json");
			    }
			}]
		}).on('tokenfield:createtoken', function (e) {
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
		}).on('tokenfield:createdtoken', function (e) {
			var existingTokens = $(this).tokenfield('getTokens');
			var ids = "";
			$.each(existingTokens, function(index, token) {
				ids += eq_incharge_map[token.value].id + ",";
            });

			ids = ids.substring(0,ids.length-1);
			$("#eq_incharge").val(ids);
		}).on('tokenfield:removedtoken', function (e) {
			var existingTokens = $(this).tokenfield('getTokens');
			var ids = "";
			$.each(existingTokens, function(index, token) {
				ids += eq_incharge_map[token.value].id + ",";	
            });
			
			if (existingTokens.length > 0) {
				ids = ids.substring(0,ids.length-1);
			}

			$("#eq_incharge").val(ids);
		});
		
		$('#eq_contact_tokenfield').tokenfield({
			tokens : [],
			limit : 5,
			typeahead: [
			{
      			hint: true
    			}, 
			{
				displayKey: "name",
				source: function (query, process) {
			        var parameter = {name: query};

					$.post(contactPath, parameter, function (data) {
						$.each(data.result, function(i, object) {
							eq_contact_map[object.name] = object;
			            });
			            process(data.result);
			        }, "json");
			    }
			}]
		}).on('tokenfield:createtoken', function (e) {
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
		}).on('tokenfield:createdtoken', function (e) {
			var existingTokens = $(this).tokenfield('getTokens');
			var ids = "";
			$.each(existingTokens, function(index, token) {
				ids += eq_contact_map[token.value].id + ",";
            });

			ids = ids.substring(0,ids.length-1);
			$("#eq_contact").val(ids);
		}).on('tokenfield:removedtoken', function (e) {
			var existingTokens = $(this).tokenfield('getTokens');
			var ids = "";
			$.each(existingTokens, function(index, token) {
				ids += eq_contact_map[token.value].id + ",";	
            });
			
			if (existingTokens.length > 0) {
				ids = ids.substring(0,ids.length-1);
			}

			$("#eq_contact").val(ids);
		});
		
		
		$('#user_tokenfield').tokenfield({
			tokens : [],
			limit : 5,
			typeahead: [
			{
      			hint: true
    			}, 
			{
				displayKey: "name",
				source: function (query, process) {
			        var parameter = {name: query};

					$.post(userPath, parameter, function (data) {
						$.each(data.result, function(i, object) {
							user_map[object.name] = object;
			            });
			            process(data.result);
			        }, "json");
			    }
			}]
		}).on('tokenfield:createtoken', function (e) {
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
		}).on('tokenfield:createdtoken', function (e) {
			var existingTokens = $(this).tokenfield('getTokens');
			var ids = "";
			$.each(existingTokens, function(index, token) {
				ids += user_map[token.value].id + ",";
            });

			ids = ids.substring(0,ids.length-1);
			$("#user").val(ids);
		}).on('tokenfield:removedtoken', function (e) {
			var existingTokens = $(this).tokenfield('getTokens');
			var ids = "";
			$.each(existingTokens, function(index, token) {
				ids += user_map[token.value].id + ",";	
            });
			
			if (existingTokens.length > 0) {
				ids = ids.substring(0,ids.length-1);
			}

			$("#user").val(ids);
		});

		var orgRootPath = webPath + 'statistics/result/rootOrganization';
		$.get(orgRootPath, null, function (data) {
			var orgChildPath = webPath + 'statistics/result/childOrganization';
			var receiver = {
				id : $("#eq_org"),
				name : $("#eq_org_name")
			};
			var opt = {
				root_id:data.result.id,
				url:orgChildPath,
				ajax:true,
				receiver: receiver
			};
			$("#eq_org_tagselector").tagSelector(opt);
        }, "json");
		
		$.get(orgRootPath, null, function (data) {
			var orgChildPath = webPath + 'statistics/result/childOrganization';
			var receiver = {
				id : $("#lab_org"),
				name : $("#lab_org_name")
			};
			var opt = {
				root_id:data.result.id,
				url:orgChildPath,
				ajax:true,
				receiver: receiver
			};
			$("#lab_org_tagselector").tagSelector(opt);
        }, "json");
		
		var eqRootPath = webPath + 'statistics/result/rootEquipment';
		$.get(eqRootPath, null, function (data) {
			var eqChildPath = webPath + 'statistics/result/childEquipment';
			var receiver = {
				id : $("#eq_type"),
				name : $("#eq_type_name")
			};
			var opt = {
				root_id:data.result.id,
				url:eqChildPath,
				ajax:true,
				receiver: receiver
			};
			$("#eq_type_tagselector").tagSelector(opt);
        }, "json");
		
		var indexTypePath = webPath + "statistics/result/roleindextype";
		var indexPath = webPath + "statistics/result/roleindex";
		
		// 查询当前角色对应的所有指标	并填充页面
		$.ajax({
			url: indexTypePath,
			cache: false,
			async: false,
			dataType: "json",
			type: "get",
			success: function(indexTypeData){
				var indexTypeResult = indexTypeData.result;
				$.ajax({
					url: indexPath,
					cache: false,
					async: false,
					dataType: "json",
					type: "get",
					success: function(indexData){
						var indexResult = indexData.result;
						$('#indexContent').empty();
						
						$.each(indexTypeResult, function(typeNum, typeValue) {

							var rootDiv = $("<div/>");
							var parentDiv = $("<div/>").attr({
								"class" : "type"
							});
							var parentLabel = $("<label/>").html(typeValue.tName);
							
							parentDiv.append(parentLabel);
							
							var childDiv = $("<div/>").attr({
								"class" : "index"
							});

							$.each(indexResult,function(indexNum,indexValue) {   
								if (typeValue.tId == indexValue.tId) {
									var checkBox;

									if (indexValue.sCode == "eq_name") {
										checkBox = $("<input/>").attr({
											"name" : "ckbIndex",
											"type" : "checkbox",
											"value" : indexValue.sId,
											"code" : indexValue.sCode,
											"index-location" : indexValue.sLocation,
											"index-description" : indexValue.sDescription,
											"index-width" : indexValue.sWidth,
											"index-not-count" : indexValue.sNotCount,
											"class" : "middle",
											"checked" : "checked",
											"disabled" : "true"
										});
									} else {
										checkBox = $("<input/>").attr({
											"name" : "ckbIndex",
											"type" : "checkbox",
											"value" : indexValue.sId,
											"code" : indexValue.sCode,
											"index-location" : indexValue.sLocation,
											"index-description" : indexValue.sDescription,
											"index-width" : indexValue.sWidth,
											"index-not-count" : indexValue.sNotCount,
											"class" : "middle"
										});
									}
									
									var childLabel = $("<label/>").attr({
										"class" : "middle"
									}).html(indexValue.sName);
									
									childDiv.append(checkBox).append(childLabel);
								}
							});
							
							rootDiv.append(parentDiv).append(childDiv);
							
							$("#indexContent").append(rootDiv);
						});
					}
				});
			}
		});
		
		
		$("#exportBtn").click(function(){
			var indexId = "";
			var ckbIndex = $("input:checkbox[name=ckbIndex]:checked");
			
			var indexId = "";
			$.each(ckbIndex, function(index, checkbox) {
				indexId += checkbox.value + ",";
            });

			indexId = indexId.substring(0,indexId.length-1);
			$("#indexId").val(indexId);
			
			$("#searchForm").attr("action", "statistics/result/excel");
			$("#searchForm").submit();
		});
		
		$("#printBtn").click(function(){
			var indexId = "";
			var ckbIndex = $("input:checkbox[name=ckbIndex]:checked");
			
			var indexId = "";
			$.each(ckbIndex, function(index, checkbox) {
				indexId += checkbox.value + ",";
            });

			indexId = indexId.substring(0,indexId.length-1);
			$("#indexId").val(indexId);
			
			$("#searchForm").attr("action", "statistics/result/print");
			$("#searchForm").submit();
		});
	
		$("#dosearch").click(function() {
			// 加载等待效果
			overlayShow();
			
			//填充搜索项
			fillSearchItem();
			
			/* // 获取查询条件
			searchParam = getSearchParam(1, 16);
			// 创建表头左侧
			buildTableHeaderLeft();
			// 创建表头右侧
			indexEntityArray = buildTableHeaderRight();
			// 获取统计列表记录
			var equipmentIndexData = getEquipmentIndexData(searchParam);
			pageCount = equipmentIndexData.pageCount;
			page = equipmentIndexData.page;
			// 创建统计列表右侧
			buildTableBodyRight(equipmentIndexData, indexEntityArray, true);
			// 创建统计列表左侧
			buildTableBodyLeft(equipmentIndexData, true);
			// 获取总计记录
			var equipmentIndexCount = getEquipmentIndexCount(searchParam);
			// 创建总计左侧
			buildTableFootLeft(equipmentIndexCount);
			// 创建总计右侧
			buildTableFootRight(equipmentIndexCount, indexEntityArray);
			// 点击搜索后，将滚动到顶部
			$("#table-right-body").scrollTop(0);
			$("#table-right-body").scrollLeft(0); */
			// 收回窗口
			displaySearchProperties();
			// 去掉等待效果
			overlayHide();
		});
		
		$("#table-right-body").scroll(function () {
			// 滚动
			tableScroll();
			var scrollTop = $("#table-right-body")[0].scrollTop;
			var scrollHeight = $("#table-right-body")[0].scrollHeight;
			var divHeight = $("#table-right-body").height();
			if (scrollTop + divHeight >= scrollHeight && page < pageCount){
				// 加载等待效果
				overlayShow();
				page += 1;
				searchParam.page = page;
				// 获取统计列表记录
				var equipmentIndexData = getEquipmentIndexData(searchParam);
				// 创建统计列表右侧
				buildTableBodyRight(equipmentIndexData, indexEntityArray, false);
				// 创建统计列表左侧
				buildTableBodyLeft(equipmentIndexData, false);
				// 去掉等待效果
				overlayHide();
			}
		});
	});
	
	function displaySearchProperties() {
		if ($("#searchProperties").is(":hidden")) {
			$("#searchProperties").slideDown(1000);
		} else {
			$("#searchProperties").slideUp(1000);
		}
	}
</script>
</head>
<body>
	<%@include file="../common/title.jsp" %>
	<div class="container-fluid">
		<div class="row">
			<%@include file="../common/menu.jsp" %>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="link-group">
					<a href="javascript:displaySearchProperties();" id="searchBtn" class="link link-primary">搜索</a> <a
						data-target="#myModal" class="link link-primary right" id="printBtn">打印</a> <a href="#" id="exportBtn"
						class="link link-primary right" data-toggle="modal"
						data-target="#myModal">导出</a>
				</div>
				<!-- 搜索条件结果 -->
				<div class="pannel">
					<div class="container-self">
						<form>
							<div class="result-left">
								<ul>
									<li><label>仪器名称</label> <input type="text" id="s_eq_name" readonly="true"
										value=""></li>
									<li><label>仪器分类</label> <input type="text" id="s_eq_type" readonly="true"
										value=""></li>
									<li><label>时间范围</label> <input type="text" id="s_dstart" readonly="true"
										style="width: 90px;" value=""> <span
										style="color: #d7d7d7;">到</span> <input type="text"
										readonly="true"  id="s_dend" style="width: 90px;" value="">
									</li>
								</ul>
							</div>
							<div class="result-middle">
								<ul>
									<li><label>仪器组织机构</label>
									<input type="text"
										readonly="true" id="s_eq_org" value=""></li>
									<li><label>仪器负责人</label> <input type="text"
										readonly="true" id="s_eq_incharge" value=""></li>
									<li><label>仪器联系人</label> <input type="text"
										readonly="true" id="s_eq_contact" value=""></li>
								</ul>
							</div>
							<div class="result-right">
								<ul>
									<li><label>课题组组织机构</label> <input type="text"
										readonly="true" id="s_lab_org" value=""></li>
									<li><label>课题组</label> <input type="text" id="s_lab" readonly="true"
										value=""></li>
									<li><label>使用者</label> <input type="text" id="s_user" readonly="true"
										value=""></li>
								</ul>
							</div>
						</form>
					</div>
					<!-- 创建统计列表开始 -->
					<div id="table-all">
						<div class="table-left">
							<div id="table-left-head">
							</div>
							<div class="table-left-body" id="table-left-body">
							</div>
							<div id="table-left-foot">
							</div>
						</div>
						<div class="table-right">
							<div id="table-right-head" class="table-right-head">
							</div>
							<div id="table-right-body" class="table-right-body">
							</div>
							<div id="table-right-foot" class="table-right-foot">
							</div>
						</div>
					</div>
					<!-- 创建统计列表结束 -->
				</div>
			</div>
		</div>
	</div>

	<div id="searchProperties" style="display:none;position: absolute;left:185px;top:100px;" aria-labelledby="myModalLabel" aria-hidden="true">
          <form id="searchForm" method="post" target="_blank">
          <div class="modal-content">
            <div class="modal-header">
              <h3 class="modal-title" id="myModalLabel">请输入搜索内容</h3>
            </div>
            <div class="modal-body">
              <label>搜索条件</label>
                <div style="position: relative;">
                    <div class="left-list">
                      <ul>
                        <li>
                          <label>仪器名称</label>
                          <input id="eq_name" name="eq_name" type="text" placeholder="请输入仪器名称">
                        </li>
                        <li>
                          <label>仪器分类</label>
                          <input id="eq_type" name="eq_type" type="hidden"/>
                          <input id="eq_type_name" type="hidden"/>
                          <div id="eq_type_tagselector"></div>
                        </li>
                        <li>
                          <label>仪器组织机构</label>
                          <input type="hidden" id="eq_org" name="eq_org"/>
                          <input type="hidden" id="eq_org_name"/>
						 <div id="eq_org_tagselector"></div>
                        </li>
                        <li>
                          <label>仪器负责人</label>
                          <input type="text" class="form-control" id="eq_incharge_tokenfield" value=""  placeholder="可添加5个"/>
                          <input id="eq_incharge" name="eq_incharge" type="hidden"/>
                        </li>
                        <li>
                          <label>仪器联系人</label>
                          <input type="text" class="form-control" id="eq_contact_tokenfield" value=""  placeholder="可添加5个"/>
                          <input id="eq_contact" name="eq_contact" type="hidden"/>
                        </li>
                      </ul>
                    </div>
                    <div class="right-list">
                      <ul>
                        <li>
                          <label>时间范围</label>
                          <input id="dstart" name="dstart" value="" type="text" placeholder="YYYY-MM-DD">
                          <span style="color: #d7d7d7;">到</span>
                          <input id="dend" name="dend" value="" type="text" placeholder="YYYY-MM-DD">
                        </li>
                        <li>
                          <label>课题组组织机构</label>
                          <input id="lab_org" name="lab_org" type="hidden"/>
                          <input id="lab_org_name" type="hidden"/>
                          <div id="lab_org_tagselector"></div>
                        </li>
                        <li>
                          <label>课题组</label>
                          <input type="text" class="form-control" id="lab_tokenfield" value=""  placeholder="可添加5个"/>
                        	 <input id="lab" name="lab" type="hidden" />
                        </li>
                        <li>
                          <label>使用者</label>
                          <input type="text" class="form-control" id="user_tokenfield" value=""  placeholder="可添加5个"/>
                          <input id="user" name="user" type="hidden" />
                        </li>
                      </ul>               
                    </div>
                </div>
              <label style="margin-top: 3em ;">搜索结果</label>
              <input id="indexId" name="indexId" type="hidden"/>
              <div class="index-grid" id="indexContent"></div>
            </div>

            <div class="modal-footer">
              <button type="button" class="link link-primary" id="dosearch">提交</button>
              <button type="button" class="link link-default" data-dismiss="modal">取消</button>
            </div>
          </div>
          </form>
         </div>
</body>
</html>
