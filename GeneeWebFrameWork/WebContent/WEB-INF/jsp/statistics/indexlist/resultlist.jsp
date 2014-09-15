<%@page import="com.genee.web.module.enums.RoleEnum"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<!-- 公用base -->
	<%@include file="../common/base.jsp" %>
	<link href="css/jquery/jquery-ui.min.css" rel="stylesheet"/>
	<link href="css/bootstrap/tokenfield-typeahead.css" rel="stylesheet"/>
	<link href="css/bootstrap/bootstrap-tokenfield.css" rel="stylesheet"/>
	<link href="css/bootstrap/bootstrap-datepicker.css" rel="stylesheet"/>
	<link href="css/tag.css" rel="stylesheet"/>
	<!-- 自己的js文件 -->
	<script src="js/statistics/eqindex-table.js"></script>
	<script type="text/javascript" src="js/bootstrap/bootstrap-tokenfield.js"></script>
	<script type="text/javascript" src="js/bootstrap/typeahead.bundle.js"></script>
	<script type="text/javascript" src="js/bootstrap/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="js/tag_selector.js"></script>
	<script type="text/javascript" src="js/statistics/search-items.js"></script>
	<script>
	// 指标消息提示
	$(document).ready(function() {
		initSearchItems();
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
                                    	<span id="s_eq_name"></span>
                                    </li>
									<li>
										<label>仪器分类</label>
										<span id="s_eq_type"></span>
									</li>
									<li>
										<label>时间范围</label>
										<span id="s_dstart" class="time-input"></span>
										<span style="color: #777;">到</span>
										<span id="s_dend" class="time-input"></span>
									</li>
                                </ul>
                            </div><!-- result left -->
                            <div class="result-middle">
                                <ul>
                                    <li>
                                    	<label>仪器组织机构</label>
										<span id="s_eq_org"></span>
									</li>
									<li>
										<label>仪器负责人</label>
										<span id="s_eq_incharge"></span>
									</li>
									<li>
										<label>仪器联系人</label>
										<span id="s_eq_contact"></span>
									</li>
                                </ul>
                            </div><!-- result middle -->
                            <div class="result-right">
                                <ul>
                                    <li>
                                    	<label>课题组组织机构</label>
                                    	<span id="s_lab_org"></span>
                                    </li>
									<li>
										<label>课题组</label>
										<span id="s_lab"></span>
									</li>
									<li>
										<label>使用者</label>
										<span id="s_user"></span>
									</li>
                                </ul>               
                            </div><!-- result right -->
                        </div><!-- search-result -->
                        <div class="table-container">
                            <div class="table-left">
                                <div class="table-left-head" id="table-left-head">
                                </div>
								<div class="table-border-diy-up"></div>
                                <div class="table-left-body" id="table-left-body">    
                                </div>
 								<div class="table-border-diy-down"></div>
                                <div class="table-left-foot" id="table-left-foot">
                                </div>
                            </div><!-- table left -->
                            <div class="table-right">
                                <div class="table-right-head" id="table-right-head">
                                </div>
								<div class="table-border-diy-up"></div>
                                <div class="table-right-body" id="table-right-body"  onscroll="scorlling();">
                                </div>
 								<div class="table-border-diy-down"></div>
                                <div class="table-right-foot" id="table-right-foot">
                                </div>
                            </div><!-- table right -->
                        </div><!-- table container -->
                    </div><!-- panel self -->
                </div><!-- main -->
            </div><!-- row -->
        </div><!-- container-fluid -->


        <!-- 搜索的弹出框效果 -->
	    <div id="searchProperties" class="popup panel-self" aria-labelledby="myModalLabel" aria-hidden="true">
	    	<div class="modal-content panel-content">
			<label class="search-title">搜索条件</label>
			<div id="alert-warning" class="alert alert-warning fade hide in">
					<button type="button" class="close">&times;</button>
			  		时间范围不允许为空
			</div>
			<div id="alert-index" class="alert alert-warning fade hide in">
					<button type="button" class="close">&times;</button>
			  		请至少选择一个指标
			</div>
	        	<div class="search-list">
	            	<div class="left-list">
	                    	<div class="list-item">
	                          	<label>仪器名称</label>
	                          	<input id="eq_name" name="eq_name" type="text" placeholder="请输入仪器名称">
	                        </div>
	                        <div class="list-item">
	                          	<label>仪器分类</label>
	                          	<input id="eq_type" name="eq_type" type="hidden"/>
	                          	<input id="eq_type_name" type="hidden"/>
	                          	<div id="eq_type_tagselector"></div>
	                        </div>
	                        <div class="list-item">
	                          	<label>仪器组织机构</label>
	                          	<input type="hidden" id="eq_org" name="eq_org"/>
	                          	<input type="hidden" id="eq_org_name"/>
							  	<div id="eq_org_tagselector"></div>
	                        </div>
	                        <div class="list-item">
	                          	<label>仪器负责人</label>
	                          	<input type="text" class="form-control" id="eq_incharge_tokenfield" value="" <% if(loggedRoleId.equals(RoleEnum.INCHARGE.getId()) || loggedRoleId.equals(RoleEnum.LAB_INCHARGE.getId())) {%>disabled="disabled"<%} %> placeholder="可添加5个"/>
	                          	<input id="eq_incharge" name="eq_incharge" type="hidden" value="<% if(loggedRoleId.equals(RoleEnum.INCHARGE.getId()) || loggedRoleId.equals(RoleEnum.LAB_INCHARGE.getId())) {%><%=loggedUserId %><%} %>"/>
	                        </div>
	                        <div class="list-item">
	                          	<label>仪器联系人</label>
	                          	<input type="text" class="form-control" id="eq_contact_tokenfield" value="" <% if(loggedRoleId.equals(RoleEnum.INCHARGE.getId()) || loggedRoleId.equals(RoleEnum.LAB_INCHARGE.getId())) {%>disabled="disabled"<%} %>  placeholder="可添加5个"/>
	                          	<input id="eq_contact" name="eq_contact" type="hidden" value="<% if(loggedRoleId.equals(RoleEnum.INCHARGE.getId()) || loggedRoleId.equals(RoleEnum.LAB_INCHARGE.getId())) {%><%=loggedUserId %><%} %>"/>
	                        </div>
					</div>
	                <div class="right-list">
	                        <div class="list-item">
	                          	<label>时间范围</label>
	                          	<input id="dstart" name="dstart" type="text" class="time-input" readonly="" placeholder="YYYY-MM-DD">
	                          	<span style="color: #d7d7d7; font-size: 12px;">到</span>
	                          	<input id="dend" name="dend" type="text" class="time-input" readonly="" placeholder="YYYY-MM-DD">
	                        </div>
	                        <div class="list-item">
	                          	<label>课题组组织机构</label>
	                          	<input id="lab_org" name="lab_org" type="hidden"/>
	                          	<input id="lab_org_name" type="hidden"/>
	                          	<div id="lab_org_tagselector"></div>
	                        </div>
	                        <div class="list-item">
	                          	<label>课题组</label>
	                          	<input type="text" class="form-control" id="lab_tokenfield" value="" <% if(loggedRoleId.equals(RoleEnum.LAB.getId()) || loggedRoleId.equals(RoleEnum.LAB_INCHARGE.getId())) {%>disabled="disabled"<%} %> placeholder="可添加5个"/>
	                        		<input id="lab" name="lab" type="hidden" value="<% if(loggedRoleId.equals(RoleEnum.LAB.getId()) || loggedRoleId.equals(RoleEnum.LAB_INCHARGE.getId())) {%><%=loggedLabId %><%} %>" />
	                        </div>
	                        <div class="list-item">
	                          	<label>使用者</label>
	                          	<input type="text" class="form-control" id="user_tokenfield" placeholder="可添加5个"/>
	                          	<input id="user" name="user" type="hidden" />
	                        </div>
	               	</div>
				</div>
				<div class="index-item">
		            <label class="search-title">搜索结果</label>
		            <div class="index-grid" id="indexContent"></div><!-- index-grid -->
		
		            <div class="search-link-group">
		              	<a class="link link-primary link-tab-primary" id="dosearch" onclick="dosearch()">搜索</a>
		              	<a class="link link-default link-tab-default" data-dismiss="modal" onclick="displaySearchProperties()">取消</a>
		            </div>
	            </div>
	        </div>
	   	</div>
      <input type="hidden" id="pagecode" value="result-list"/>
    </body>
</html>
