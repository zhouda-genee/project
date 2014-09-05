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
<link href="css/jquery/jquery-ui.css" rel="stylesheet"/>
<link href="css/bootstrap/bootstrap-tokenfield.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="js/bootstrap/bootstrap-tokenfield.js"></script>
<script type="text/javascript" src="js/bootstrap/typeahead.bundle.js"></script>
<script>
	$(document).ready(function() {
		
		var engine = new Bloodhound({
			remote: {
				url: API_URL + '?action=message_friends&q=%QUERY',
				filter: function (response) {
					return $.map(response.users, function (user) {
						return {
							value: user.user_id,
							label: user.name
						};
					});
	    			}
  			},
  			datumTokenizer: function(d) {
    			return Bloodhound.tokenizers.whitespace(d.value); 
  		},
  		queryTokenizer: Bloodhound.tokenizers.whitespace    
		});

		engine.initialize();
		
		$('#tokenfield').tokenfield({
			autocomplete: {
				source: ['red','blue','green','yellow','violet','brown','purple','black','white'],
				delay: 100
			},
			showAutocompleteOnFocus: true
		});

		
		var indexTypePath = webPath + "statistics/result/roleindextype";
		var indexPath = webPath + "statistics/result/roleindex";
		
		// 查询当前角色对应的所有指标	并填充页面
		/*$.ajax({
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
		});*/
		
		
		$("#exportBtn").click(function(){
			var url = webPath + "statistics/result/excel?";
		/* 	url += "eq_name=" + $("eq_name").val();
			url += "&eq_type=" + $("#hidEqType").val();
			url += "&eq_org=" + $("#hidEqOrg").val();
			url += "&eq_contact=" + $("#hidEqContact").val();
			url += "&eq_incharge=" + $("#hidEqIncharge").val();
			url += "&lab_org=" + $("#hidLabOrg").val();
			url += "&lab=" + $("#hidLab").val();
			url += "&user=" + $("#hidUser").val(); */
			url += "dstart=1314806400";
			url += "&dend=1314892799";
			var indexId = "";
			
			$('input[name="ckbIndex"]:checked').each(function(){    
				indexId += $(this).val() + ",";
			});
			
			indexId = indexId.substr(0, indexId.length-1);//除去最后一个逗号
			
			url += "&index_id=" + indexId;
			window.open(url,"导出");
		});
	
		$("#dosearch").click(function() {
			// 获取查询条件
			var searchParam = getSearchParam(1, 16);
			// 创建表头左侧
			buildTableHeaderLeft();
			// 创建表头右侧
			var indexEntityArray = buildTableHeaderRight();
			// 获取统计列表记录
			var equipmentIndexData = getEquipmentIndexData(searchParam);
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
			$("#table-right-body").scrollLeft(0);
		});
		
		$("#table-right-body").scroll(function () {
			tableScroll();
			var scrollTop = $("#table-right-body").scrollTop();
			if (scrollTop == 1){
				alert("加载数据去了");
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
						href="#" class="link link-primary right" data-toggle="modal"
						data-target="#myModal">打印</a> <a href="#" id="exportBtn"
						class="link link-primary right" data-toggle="modal"
						data-target="#myModal">导出</a>
				</div>
				<!-- 搜索条件结果 -->
				<div class="pannel">
					<div class="container-self">
						<form>
							<div class="result-left">
								<ul>
									<li><label>仪器名称</label> <input type="text" readonly="true"
										value="光谱仪">
										<input type="text" class="form-control" id="tokenfield" value="red,green,blue" />
										</li>
									<li><label>仪器分类</label> <input type="text" readonly="true"
										value="X射线仪器"></li>
									<li><label>时间范围</label> <input type="text" readonly="true"
										style="width: 90px;" value="2012-08-30"> <span
										style="color: #d7d7d7;">到</span> <input type="text"
										readonly="true" style="width: 90px;" value="2013-03-18">
									</li>
								</ul>
							</div>
							<div class="result-middle">
								<ul>
									<li><label>仪器组织机构</label> <input type="text"
										readonly="true" value="南开大学"></li>
									<li><label>仪器负责人</label> <input type="text"
										readonly="true" value="张三"></li>
									<li><label>仪器联系人</label> <!-- input --> <label
										class="eq-people">James</label></li>
								</ul>
							</div>
							<div class="result-right">
								<ul>
									<li><label>课题组组织机构</label> <input type="text"
										readonly="true" value="全部"></li>
									<li><label>课题组</label> <input type="text" readonly="true"
										value="课题组1， 课题组2"></li>
									<li><label>使用者</label> <input type="text" readonly="true"
										value="张三"></li>
								</ul>
							</div>
						</form>
					</div>
					<!-- 创建统计列表开始 -->
					<div>
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
                          <input id="eq_name" type="text" placeholder="请输入仪器名称">
                        </li>
                        <li>
                          <label>仪器分类</label>
                          <input id="eq_type" type="text">
                        </li>
                        <li>
                          <label>仪器组织机构</label>
                          <input id="eq_org" type="text">
                        </li>
                        <li>
                          <label>仪器负责人</label>
                          <input id="eq_incharge" type="text"/>
                        </li>
                        <li>
                          <label>仪器联系人</label>
                          <input id="eq_contact" type="text"/>
                        </li>
                      </ul>
                    </div>
                    <div class="right-list">
                      <ul>
                        <li>
                          <label>时间范围</label>
                          <input id="dstart" type="text" placeholder="YYYY-MM-DD">
                          <span style="color: #d7d7d7;">到</span>
                          <input id="dend" type="text" placeholder="YYYY-MM-DD">
                        </li>
                        <li>
                          <label>课题组组织机构</label>
                          <input id="lab_org" type="text">
                        </li>
                        <li>
                          <label>课题组</label>
                          <input id="lab" type="text" placeholder="可添加5个">
                        </li>
                        <li>
                          <label>使用者</label>
                          <input id="user" type="text" >
                        </li>
                      </ul>               
                    </div>
                </div>
              <label style="margin-top: 3em ;">搜索结果</label>
              <div class="index-grid" id="indexContent">
                <div>
                  <div class="type">
                    <label>基本信息</label>
                  </div>
                  <div class="index">
                    <input type="checkbox" value="2" index-width="100" index-not-count="1" code="eq_price" class="middle"><label class="middle">仪器价格</label>
                    <input type="checkbox" value="3" index-width="200" index-not-count="1" code="principal" class="middle"><label class="middle">负责人</label>
                    <input type="checkbox" value="4" index-width="200" index-not-count="1" code="linkman" class="middle"><label class="middle">联系人</label>
                    <input type="checkbox" value="5" index-width="80" index-not-count="1" code="innet_dur" class="middle"><label class="middle">入网时长</label>
                    <input type="checkbox" value="6" index-width="80" index-not-count="0" code="fault_dur" class="middle"><label class="middle">故障时长</label>
                  </div>
                </div>
                <div>
                  <div class="type">
                    <label>计费信息</label>
                  </div>
                  <div class="index">
                    <input type="checkbox" value="22" index-width="80" index-not-count="0" code="used_charge" class="middle"><label class="middle">使用收费</label>
                    <input type="checkbox" value="23" index-width="80" index-not-count="0" code="on_cam_charge" class="middle"><label class="middle">校内收费</label>
                    <input type="checkbox" value="24" index-width="80" index-not-count="0" code="off_cam_charge" class="middle"><label class="middle">校外收费</label>
                    <input type="checkbox" value="25" index-width="80" index-not-count="0" code="delegation_charge" class="middle"><label class="middle">委托测试收费</label>
                    <input type="checkbox" value="26" index-width="80" index-not-count="0" code="earnings_charge" class="middle"><label class="middle">创收金额</label>
                    <input type="checkbox" value="27" index-width="80" index-not-count="0" code="repair_cost" class="middle"><label class="middle">维修费</label>
                  </div>
                </div>
              </div>
            </div>

            <div class="modal-footer">
              <button type="button" class="link link-primary" id="dosearch" onclick="displaySearchProperties()">提交</button>
              <button type="button" class="link link-default" data-dismiss="modal">取消</button>
            </div>
          </div>
         </div>
</body>
</html>
