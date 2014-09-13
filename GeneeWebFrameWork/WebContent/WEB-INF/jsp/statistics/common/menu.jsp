<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="session.jsp" %>
<!-- 左侧菜单 -->
<div id="menu" class="col-md-2 sidebar">
	<ul class="nav nav-sidebar">
		<li>
			<div class="avatar">
				<div class="left avatar-intro">
					<a href="#"><%=loggedUserName %></a><br> <a href="#" class="role"><%=loggedRoleName %></a>
				</div>
			</div>
		</li>
	</ul>
	<ul class="nav nav-sidebar">
		<li id="result-list"><a href="statistics/result/search">统计列表</a></li>
		<!-- <li><a href="#">统计图表</a></li>
		<li><a href="#">绩效评估</a></li> -->
		<li id="role-index"><a href="statistics/indexconfig">权限设置</a></li>
	</ul>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#menu > ul:last > li").removeClass("active");
		$("#"+$("#pagecode").val()).addClass("active");
	});
</script>
<!-- sidebar -->