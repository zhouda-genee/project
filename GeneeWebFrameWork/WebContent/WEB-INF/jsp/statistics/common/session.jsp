<%@page import="java.util.Map"%>
<%@page import="com.genee.web.module.constants.SessionAttributeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.genee.web.framework.utils.prop.PropertiesUtil"%>
<%
	Map<String, String> loggedSessionMap = 
		(Map<String, String>) request.getSession().getAttribute(SessionAttributeType.PARAM_USER);
	String loggedUserId = loggedSessionMap.get(SessionAttributeType.USER_ID);
	String loggedToken = loggedSessionMap.get(SessionAttributeType.TOKEN);
	String loggedRoleId = loggedSessionMap.get(SessionAttributeType.ROLE);
	String loggedLabId = loggedSessionMap.get(SessionAttributeType.LAB_ID);
%>