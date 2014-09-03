<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@include file="../common/base.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>统计列表</title>
<script>
	$(document).ready(function() {
		// 自定义统计列表参数对象
		function statisticsParam(eq_name, eq_type, eq_org, eq_contact, eq_incharge, lab_org, lab, 
				user, dstart, dend, sort_name, sort, page){
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
			this.size = 15;
			this.page = page;
		}
		
		$("#dosearch").click(function(){
			
			var jsonRPCobject = new JSONRPC2Object();
			jsonRPCobject.id = new Date().getTime();
			jsonRPCobject.method = "statistics_eqindex_page";
			
			var param = new statisticsParam();
			param.page = 1;
			// 仪器名称
			var eq_name = $("#eq_name").val();
			if (eq_name != null && eq_name != ""){
				param.eq_name = eq_name;
			}
			// 这里添加其它查询条件
			// ......
			
			var dstart = $("#dstart").val().replace(/-/g,'/') + " 00:00:00";
			param.dstart = new Date(dstart).getTime();
			var dend = $("#dend").val().replace(/-/g,'/') + " 23:59:59";
			param.dend = new Date(dend).getTime();
			
			jsonRPCobject.params = JSON.stringify(param);
			
			// 查询统计结果
			$.ajax({
				url: "http://localhost:8088/geneeservletfw/API2",
				data: jsonRPCobject,
				cache: false,
				async: false,
				dataType: "json",
				type: "post",
				success: function(data){
					var id = data.id;
					var jsonrpc = data.jsonrpc;
					alert("id:" + id + " jsonrpc:" + jsonrpc);
					var result = data.result;
					alert(result);
					var totalCount = result.totalCount;
					var pageSize = result.pageSize;
					var page = result.page;
					alert("page:" + page + " totalCount:" + totalCount + " pageSize:" + pageSize);
					var items = result.items;
					for (var i=0; i<items.length; i++) {
						var item = items[i];
						alert(item.get("eq_id"));
					}
				}
			});
		});
	}); 
	
	function displaySearchProperties() {
		if ($("#searchProperties").is(":hidden")) {
			$("#searchProperties").slideDown(1000);
		} else {
			$("#searchProperties").slideUp(1000);
		}
	}

	function tableScroll() {
		var a = document.getElementById("t_r_content").scrollTop;
		var b = document.getElementById("t_r_content").scrollLeft;
		document.getElementById("cl_freeze").scrollTop = a;
		document.getElementById("t_r_t").scrollLeft = b;
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
										value="光谱仪"></li>
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

					<div class="table-left">
						<div>
							<table>
								<tr>
									<th class="eq-name-cell">仪器名称</th>
								</tr>
							</table>
						</div>
						<div class="col-freeze" id="cl_freeze">
							<table>
								<tbody><tr><td>光谱仪</td></tr></tbody>
								<tfoot><tr><td class="count">
									<span style="font-size: 14px; padding-left: 10px; color: #000;">
									总计&nbsp;&nbsp;仪器台数：23
									</span></td></tr>
								</tfoot>
							</table>
						</div>
					</div>
					<div class="table-row">
						<div id="t_r_t" class="table-row-title t-r-t">
							<table>
								<tr>
									<th colspan="4" style="width: 480px; font-size: 18px;">基本信息</th>
									<th colspan="6" style="width: 720px; font-size: 18px;">使用信息</th>
									<th colspan="10" style="width: 1200px; font-size: 18px;">测样信息</th>
									<th colspan="4" style="width: 480px; font-size: 18px;">计费信息</th>
								</tr>
								<tr>
									<th>仪器价格（¥）</th>
									<th>负责人</th>
									<th>联系人</th>
									<th>故障时长</th>
									<th>入网时长</th>
									<th>预约机时</th>
									<th>使用机时</th>
									<th>机主使用机时</th>
									<th>开放机时</th>
									<th>有效机时</th>
									<th>委托测试机时</th>
									<th>科研机时</th>
									<th>教学机时</th>
									<th>社会项目机时</th>
									<th>使用次数</th>
									<th>测样数</th>
									<th>使用测样数</th>
									<th>送样测样数</th>
									<th>机主测样数</th>
									<th>学生测样数</th>
									<th>使用收费</th>
									<th>校内收费</th>
									<th>校外收费</th>
									<th>服务科研项目数</th>
								</tr>
							</table>
						</div>
						<div class="table-row-content" id="t_r_content" onscroll="tableScroll()">
							<table>
								<tr>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>6</td>
									<td>7</td>
									<td>8</td>
									<td>9</td>
									<td>10</td>
									<td>11</td>
									<td>12</td>
									<td>13</td>
									<td>14</td>
									<td>15</td>
									<td>16</td>
									<td>17</td>
									<td>18</td>
									<td>19</td>
									<td>20</td>
									<td>21</td>
									<td>22</td>
									<td>23</td>
								</tr>
								<tr id="count">
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>6</td>
									<td>7</td>
									<td>8</td>
									<td>9</td>
									<td>10</td>
									<td>11</td>
									<td>12</td>
									<td>13</td>
									<td>14</td>
									<td>15</td>
									<td>16</td>
									<td>17</td>
									<td>18</td>
									<td>19</td>
									<td>20</td>
									<td>21</td>
									<td>22</td>
									<td>23</td>
								</tr>
							</table>
						</div>
					</div>
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
                          <input id="eq_contact" type="text" placeholder="可添加5个"/> 
                        </li>
                        <li>
                          <label>仪器联系人</label>
                          <input id="eq_incharge" type="text"/>
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
              <div class="index-grid">
                <div>
                  <div class="type">
                    <label>基本信息</label>
                  </div>
                  <div class="index">
                    <input type="checkbox" value="1" name="ckb_index" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                  </div>
                </div>
                <div>
                  <div class="type">
                    <label>基本信息</label>
                  </div>
                  <div class="index">
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                  </div>
                </div>
                <div>
                  <div class="type">
                    <label>基本信息</label>
                  </div>
                  <div class="index">
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                  </div>
                </div>
                <div>
                  <div class="type">
                    <label>基本信息</label>
                  </div>
                  <div class="index">
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                  </div>
                  <div class="type">
                    <label>基本信息</label>
                  </div>
                  <div class="index">
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                    <input type="checkbox" value="1" class="middle"><label class="middle">仪器名称</label>
                  </div>

                </div>
              </div><!-- index-grid -->
            </div>

            <div class="modal-footer">
              <button type="button" class="link link-primary" id="dosearch">提交</button>
              <button type="button" class="link link-default" data-dismiss="modal">取消</button>
            </div>
          </div>
      </div>
</body>
</html>
