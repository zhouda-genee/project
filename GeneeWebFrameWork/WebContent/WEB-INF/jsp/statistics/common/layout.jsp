<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Properties"%>
	<%
		Enumeration<String> sessionEnum = session.getAttributeNames();
		while(sessionEnum.hasMoreElements()){
			Object obj = sessionEnum.nextElement();
			session.removeAttribute(String.valueOf(obj));
		}
		session.invalidate();
	%>
	<script>
		javascript:window.close();
	</script>
