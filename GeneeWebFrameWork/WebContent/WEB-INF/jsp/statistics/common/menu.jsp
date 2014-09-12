<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 左侧菜单 -->
<div id="menu" class="col-md-2 sidebar">
	<ul class="nav nav-sidebar">
		<li>
			<div class="avatar">
				<div class="left">
					<a href="#"><img src="" alt="头像" class="img-responsive"></a>
				</div>
				<div class="left avatar-intro">
					<a href="#">用户名</a><br> <a href="#" class="role">用户级别</a>
				</div>
			</div>
		</li>
	</ul>
	<ul class="nav nav-sidebar">
		<li><a href="statistics/result/search">统计列表</a></li>
		<!-- <li><a href="#">统计图表</a></li>
		<li><a href="#">绩效评估</a></li> -->
		<li><a href="statistics/result/search">权限设置</a></li>
	</ul>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#menu > ul:last > li:first").addClass("active");
		$("#menu > ul:last").children("li").click(function(){
			var li = $(this);
			li.siblings("li").removeClass("active");
			li.addClass("active");
		});
	});
</script>
<!-- sidebar -->