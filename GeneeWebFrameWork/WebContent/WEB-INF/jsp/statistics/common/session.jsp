<%@page import="java.util.Map"%>
<%@page import="com.genee.web.module.constants.SessionAttributeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Map<String, String> loggedSessionMap =  (Map<String, String>) request.getSession().getAttribute(SessionAttributeType.PARAM_USER);
	String loggedUserId = loggedSessionMap.get(SessionAttributeType.USER_ID);
	String loggedUserName = loggedSessionMap.get(SessionAttributeType.USER_NAME);
	
	String loggedRoleId = loggedSessionMap.get(SessionAttributeType.ROLE);
	String loggedRoleName = loggedSessionMap.get(SessionAttributeType.ROLE_NAME);
	
	String loggedToken = loggedSessionMap.get(SessionAttributeType.TOKEN);
	String loggedLabId = loggedSessionMap.get(SessionAttributeType.LAB_ID);
	
%>