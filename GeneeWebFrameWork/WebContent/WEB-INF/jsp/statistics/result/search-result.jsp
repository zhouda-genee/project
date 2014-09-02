<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<jsp:include page="../../common/base.jsp"></jsp:include>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>统计列表</title>
<script>
	$(document).ready(function() { 
		
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
		document.getElementById("t_r_f").scrollLeft = b;
	}
</script>
</head>
<body>
	<div class="topbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand topbar-brand" href="#">南开大学大型仪器管理平台</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">退出</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li>
						<div class="avatar">
							<div class="left">
								<a href="#"><img src="js/holder.js/200x200/" alt="头像"
									class="img-responsive"></a>
							</div>
							<div class="left avatar-intro">
								<a href="#">用户名</a><br> <a href="#" class="role">用户级别</a>
							</div>
						</div>
					</li>
					<li class="active"><a href="statistics-table.html">统计列表</a></li>
					<li><a href="#">统计图表</a></li>
					<li><a href="#">绩效评估</a></li>
					<li><a href="index-config.html">权限设置</a></li>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="link-group">
					<a href="javascript:displaySearchProperties();" id="searchBtn" class="link link-primary">搜索</a> <a
						href="#" class="link link-primary right" data-toggle="modal"
						data-target="#myModal">打印</a> <a href="#" id="exportBtn"
						class="link link-primary right" data-toggle="modal"
						data-target="#myModal">导出</a>
				</div>

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
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
								<tr>
									<td>光谱仪</td>
								</tr>
							</table>
						</div>
					</div>

					<div class="table-row">
						<div class="t-r-t" id="t_r_t">
							<div class="table-row-title">
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
						</div>
						<div class="table-row-content" id="t_r_content"
							onscroll="tableScroll()">
							<table>
								<tr>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
								</tr>
								<tr>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
								</tr>
								<tr>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
								</tr>
								<tr>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
								</tr>
								<tr>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
									<td>1</td>
								</tr>
								<tr>
									<td>6</td>
									<td>6</td>
									<td>6</td>
									<td>6</td>
									<td>6</td>
									<td>6</td>
								</tr>
								<tr>
									<td>7</td>
									<td>7</td>
									<td>7</td>
									<td>7</td>
									<td>7</td>
									<td>7</td>
								</tr>
								<tr>
									<td>8</td>
									<td>8</td>
									<td>8</td>
									<td>8</td>
									<td>8</td>
									<td>8</td>
								</tr>
								<tr>
									<td>9</td>
									<td>9</td>
									<td>9</td>
									<td>9</td>
									<td>9</td>
									<td>9</td>
								</tr>
								<tr>
									<td>10</td>
									<td>10</td>
									<td>10</td>
									<td>10</td>
									<td>10</td>
									<td>10</td>
								</tr>
								<tr>
									<td>11</td>
									<td>11</td>
									<td>11</td>
									<td>11</td>
									<td>11</td>
									<td>11</td>
								</tr>
								<tr>
									<td>12</td>
									<td>12</td>
									<td>12</td>
									<td>12</td>
									<td>12</td>
									<td>12</td>
								</tr>
								<tr>
									<td>13</td>
									<td>13</td>
									<td>13</td>
									<td>13</td>
									<td>13</td>
									<td>13</td>
								</tr>
								<tr>
									<td>14</td>
									<td>14</td>
									<td>14</td>
									<td>14</td>
									<td>14</td>
									<td>14</td>
								</tr>
								<tr>
									<td>15</td>
									<td>15</td>
									<td>15</td>
									<td>15</td>
									<td>15</td>
									<td>15</td>
								</tr>
								<tr>
									<td>16</td>
									<td>16</td>
									<td>16</td>
									<td>16</td>
									<td>16</td>
									<td>16</td>
								</tr>
								<tr>
									<td>17</td>
									<td>17</td>
									<td>17</td>
									<td>17</td>
									<td>17</td>
									<td>17</td>
								</tr>
								<tr>
									<td>18</td>
									<td>18</td>
									<td>18</td>
									<td>18</td>
									<td>18</td>
									<td>18</td>
								</tr>
								<tr>
									<td>19</td>
									<td>19</td>
									<td>19</td>
									<td>19</td>
									<td>19</td>
									<td>19</td>
								</tr>
								<tr>
									<td>20</td>
									<td>20</td>
									<td>20</td>
									<td>20</td>
									<td>20</td>
									<td>20</td>
								</tr>
								<tr>
									<td>21</td>
								</tr>
								<tr>
									<td>22</td>
								</tr>
								<tr>
									<td>23</td>
								</tr>
								<tr>
									<td>24</td>
								</tr>
								<tr>
									<td>25</td>
								</tr>
								<tr>
									<td>26</td>
								</tr>
								<tr>
									<td>27</td>
								</tr>
							</table>
						</div>
					</div>

					<div class="table-foot" id="t_r_f">
						<table>
							<tr>
								<td class="count">总计<span
									style="font-size: 14px; padding-left: 10px; color: #000;">仪器台数：23</span></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="searchProperties" style="display:none;position: absolute;left:185px;top:100px;" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-content">
            <div class="modal-header">
              <h3 class="modal-title" id="myModalLabel">请输入搜索内容</h4>
            </div>
            <div class="modal-body">
              <label>搜索条件</label>
                <div style="position: relative;">
                  <form>
                    <div class="left-list">
                      <ul>
                        <li>
                          <label>仪器名称</label>
                          <input type="text" placeholder="请输入仪器名称">
                        </li>
                        <li>
                          <label>仪器分类</label>
                          <input type="text">
                          <input type="hidden" id="eq_class">
                        </li>
                        <li>
                          <label>仪器组织机构</label>
                          <input type="text">
                          <input type="hidden" id="eq_group">
                        </li>
                        <li>
                          <label>仪器负责人</label>
                          <input type="text" placeholder="可添加5个"> 
                          <input type="hidden" id="principal">                   
                        </li>
                        <li>
                          <label>仪器联系人</label>
                          <!-- input -->
                          <label class="eq-people">James</label>
                          <input type="hidden" id="linkman">
                        </li>
                      </ul>
                    </div>
                    <div class="right-list">
                      <ul>
                        <li>
                          <label>时间范围</label>
                          <input type="text" placeholder="YYYY-MM-DD">
                          <span style="color: #d7d7d7;">到</span>
                          <input type="text" placeholder="YYYY-MM-DD">
                        </li>
                        <li>
                          <label>课题组组织机构</label>
                          <input type="text">
                          <input type="hidden" id="re_super_group">
                        </li>
                        <li>
                          <label>课题组</label>
                          <input type="text" placeholder="可添加5个">
                          <input type="hidden" id="re_group">
                        </li>
                        <li>
                          <label>使用者</label>
                          <input type="text" >
                          <input type="hidden" id="use_person">
                        </li>
                      </ul>               
                    </div>
                  </form>
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
              <button type="button" class="link link-primary">提交</button>
              <button type="button" class="link link-default" data-dismiss="modal">取消</button>
            </div>
          </div>
      </div>
</body>
</html>