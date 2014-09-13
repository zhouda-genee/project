<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/base.jsp" %>
<%@include file="../common/title.jsp" %>
<%@include file="../common/menu.jsp" %>
<!DOCTYPE">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--[if lt IE 9]>
	    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->    
    <title>角色指标列表</title>
</head>
<body>
    <div class="col-md-10 main">
		<div class="link-group">
		  <a href="#" class="link link-tab-primary">统计项设置</a>
		  <a href="#" class="link link-tab-default">绩效评估设置</a>
		</div>
		<div class="panel-self panel-content">
			<div id="alert-success" class="alert alert-success fade hide in">
			  	<button type="button" class="close">&times;</button>
			  		保存成功！
			</div>
			
			<div id="alert-warning" class="alert alert-warning fade hide in">
			  	<button type="button" class="close">&times;</button>
			  		请勾选指标！
			</div>
	
			<div id="roleconfig">
		        	<div class="panel panel-default panel-fixed">
		            	<div class="role-name"><a data-toggle="collapse" data-parent="#roleconfig" href="#collapseOne">中心管理员</a></div>         
		            	<div id="collapseOne" class="collapse in">
		              		<div class="index-grid">
								<div class="checkall">
									<div class="right">
										<input type="checkbox" name="sysAdmin" onclick="selectAllCheckBox('templateOne',this.checked)"><label>全选</label>
									</div>
								</div>
								<div id="templateOne"></div>
								<div class="link-group">
									<a id="sbmOne" class="link link-large link-primary" onclick="subOne();">提交</a>
								</div>
							</div>
						</div>
		        	</div>
		        
		        	<div class="panel panel-default panel-fixed">
		            	<div class="role-name"><a data-toggle="collapse" data-parent="#roleconfig" href="#collapseTwo">课题组PI</a></div>           
		            	<div id="collapseTwo" class="collapse">
		              		<div class="index-grid">
								<div class="checkall">
									<div class="right">
										<input type="checkbox" name="sysAdmin" onclick="selectAllCheckBox('templateTwo',this.checked)"><label>全选</label>
									</div>
								</div>
								<div id="templateTwo"></div>
								<div class="link-group">
									<a id="sbmTwo"  class="link link-large link-primary" onclick="subTwo();">提交</a>
								</div>
							</div>
						</div>
		        	</div>
		        
		        	<div class="panel panel-default panel-fixed">
		            	<div class="role-name"><a data-toggle="collapse" data-parent="#roleconfig" href="#collapseThree">仪器管理员</a></div>
		            	<div id="collapseThree" class="collapse">
		              		<div class="index-grid">
								<div class="checkall">
									<div class="right">
										<input type="checkbox" name="sysAdmin" onclick="selectAllCheckBox('templateThree',this.checked)"><label>全选</label>
									</div>
								</div>
								<div id="templateThree"></div>
								<div class="link-group">
									<a id="sbmThree" class="link link-large link-primary" onclick="subThree();">提交</a>
								</div>
							</div>
						</div>            
		        	</div>
			</div><!-- roleconfig --> 
		</div><!-- panel self -->
	</div><!-- main -->
	
	<!-- 
		<script type="text/javascript" src="js/alloydesigner.js"></script>
	-->
	
	<script src="js/statistics/index-config.js"></script>
	<script type="text/javascript">		
			
			/** 
			 *	点击“全选”复选框的js实现
			 */
				function selectAllCheckBox (ParentID, bool) {
					// body...
					var pID = document.getElementById(ParentID);
					var cb = pID.getElementsByTagName('input');
					for (var i=0; i<cb.length; i++) {
						if (cb[i].type == "checkbox")
						cb[i].checked = bool;
					};
				};
				
			/**
			 * 警告框的关闭事件
			 */
				 $('.close').click(function(){
					$('.close').parent().addClass('hide'); 
				 });
			 
	</script>    
</body>
</html>