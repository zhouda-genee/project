<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<!-- 公用base -->
	<%@include file="../common/base.jsp" %>
	<!-- 自己的js文件 -->
	<script src="js/statistics/eqindex-table.js"></script>
	<script>
	$(document).ready(function() {
		// 指标消息提示
		var options = {
			animation : true,
			trigger : 'hover'
		};

		$("label").tooltip(options);
	});
	</script>
</head>
    <body>
        <%@include file="../common/title.jsp" %>
        <div class="container-fluid">
            <div class="row">
                <%@include file="../common/menu.jsp" %>
                <div class="col-md-10 main">
                    <div class="link-group">
                    	<a href="javascript:displaySearchProperties();" id="searchBtn" class="link link-primary"><img src="<%=webPath%>icon/search.png" class="icon-search">搜索</a>
                    </div>
                    <div class="panel-self">
                        <!-- 搜索条件结果 -->
                        <div class="search-result">
                            <div class="result-left">
                                <ul>
                                    <li>
                                        <label>仪器名称</label>
                                        <input type="text" readonly="true" value="光谱仪">
                                    </li>
                                    <li>
                                        <label>仪器分类</label>
                                        <input  type="text" readonly="true" value="X射线仪器">
                                    </li>
                                    <li>
                                        <label>时间范围</label>
                                        <input type="text" readonly="true" class="time-input" value="2012-08-30">
                                        <span style="color: #777;">到</span>
                                        <input type="text" readonly="true" class="time-input" value="2013-03-18">
                                    </li>
                                </ul>
                            </div><!-- result left -->
                            <div class="result-middle">
                                <ul>
                                    <li>
                                        <label>仪器组织机构</label>
                                        <input type="text" readonly="true" value="南开大学">
                                    </li>
                                    <li>
                                        <label>仪器负责人</label>
                                        <input type="text" readonly="true" value="张三">                    
                                    </li>
                                    <li>
                                        <label>仪器联系人</label>
                                        <!-- input -->
                                        <input type="text" readonly="true" value="张三">                    
                                    </li>
                                </ul>
                            </div><!-- result middle -->
                            <div class="result-right">
                                <ul>
                                    <li>
                                        <label>课题组组织机构</label>
                                        <input type="text" readonly="true" value="全部">
                                    </li>
                                    <li>
                                        <label>课题组</label>
                                        <input type="text" readonly="true" value="课题组1， 课题组2, ">
                                    </li>
                                    <li>
                                        <label>使用者</label>
                                        <input type="text" readonly="true" value="张三">
                                    </li>
                                </ul>               
                            </div><!-- result right -->
                        </div><!-- panel head -->
                        <div class="table-container">
                            <div class="table-left">
                                <div class="table-left-head" id="table-left-head">
                                </div>
                                <div class="table-left-body" id="table-left-body">    
                                </div>
                                <div class="table-left-foot" id="table-left-foot">
                                </div>
                            </div><!-- table left -->
                            <div class="table-right">
                                <div class="table-right-head" id="table-right-head">
                                </div>
                                <div class="table-right-body" id="table-right-body"  onscroll="scorlling();">
                                </div>
                                <div class="table-right-foot" id="table-right-foot">
                                </div>
                            </div><!-- table right -->
                        </div><!-- table container -->
                    </div><!-- panel self -->
                </div><!-- main -->
            </div><!-- row -->
        </div>


        <!-- 搜索的弹出框效果 -->
    <div id="searchProperties" class="modal-self" aria-labelledby="myModalLabel" aria-hidden="true">

          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" onclick="displaySearchProperties()"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
              <h4 class="modal-title" id="myModalLabel">请输入搜索内容</h4>
            </div>
            <div class="modal-body">
              <label class="search-title">搜索条件</label>
                <div style="position: relative;">
                    <div class="left-list">
                      <ul>
                        <li>
                          <label>仪器名称</label>
                          <input type="text" placeholder="请输入仪器名称">
                        </li>
                        <li>
                          <label>仪器分类</label>
                          <input type="text">
                        </li>
                        <li>
                          <label>仪器组织机构</label>
                          <input type="text">
                        </li>
                        <li>
                          <label>仪器负责人</label>
                          <input type="text" placeholder="可添加5个">                    
                        </li>
                        <li>
                          <label>仪器联系人</label>
                          <!-- input -->
                          <label class="eq-people">James<img src="<%=webPath%>icon/close.png"></label>
                        </li>
                      </ul>
                    </div>
                    <div class="right-list">
                      <ul>
                        <li>
                          <label>时间范围</label>
                          <input id="dstart" type="text" placeholder="YYYY-MM-DD"/>
                          <span style="color: #d7d7d7;">到</span>
                          <input id="dend" type="text" placeholder="YYYY-MM-DD"/>
                        </li>
                        <li><label>课题组组织机构</label></li>
                        <li>
                          <label>课题组</label>
                          <input type="text" placeholder="可添加5个">
                        </li>
                        <li><label>使用者</label></li>
                      </ul>               
                    </div>
                </div>
              <label style="margin-top: 3em ;">搜索结果</label>
              <div class="index-grid">
                <div>
                  <div class="indextype-name">
                    <label>基本信息</label>
                  </div>
                  <div class="index-list">
                  	<li><input type="checkbox" value="2" index-description="abc" index-width="200" index-not-count="1" code="eq_price" class="middle"><label class="middle">仪器价格</label></li>
                    <li><input type="checkbox" value="3" index-description="abc" index-width="200" index-not-count="1" code="principal" class="middle"><label class="middle">负责人</label></li>
                    <li><input type="checkbox" value="4" index-description="abc" index-width="200" index-not-count="1" code="linkman" class="middle"><label class="middle">联系人</label></li>
                    <li><input type="checkbox" value="5" index-description="abc" index-width="120" index-not-count="1" code="innet_dur" class="middle"><label class="middle">入网时长</label></li>
                    <li><input type="checkbox" value="6" index-description="abc" index-width="120" index-not-count="0" code="fault_dur" class="middle"><label class="middle">故障时长</label></li>
                  </div>
                  <div class="indextype-name">
                    <label>计费信息</label>
                  </div>
                  <div class="index-list">
                  	<li><input type="checkbox" value="22" index-description="abc" index-width="120" index-not-count="0" code="used_charge" class="middle"><label class="middle">使用收费</label></li>
                    <li><input type="checkbox" value="23" index-description="abc" index-width="120" index-not-count="0" code="on_cam_charge" class="middle"><label class="middle">校内收费</label></li>
                    <li><input type="checkbox" value="24" index-description="abc" index-width="120" index-not-count="0" code="off_cam_charge" class="middle"><label class="middle">校外收费</label></li>
                    <li><input type="checkbox" value="25" index-description="abc" index-width="120" index-not-count="0" code="delegation_charge" class="middle"><label class="middle">委托测试收费</label></li>
                  </div>
                </div>
              </div><!-- index-grid -->
            </div>

            <div class="modal-footer">
              <button type="button" class="link link-primary" id="dosearch" onclick="dosearch()">提交</button>
              <button type="button" class="link link-default" data-dismiss="modal" onclick="displaySearchProperties()">取消</button>
            </div>
        </div>
      </div>
    </body>
</html>
