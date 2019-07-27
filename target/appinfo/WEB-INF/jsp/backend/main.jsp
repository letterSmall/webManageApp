<%@ page import="cn.appsys.pojo.BackendUser" %>
<%@ page import="cn.appsys.tools.Constants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="common/header.jsp" %>
<div class="page-title">
	<div class="title_left">
		<h3>
			<h3>
				<%
					BackendUser backendUser= (BackendUser) request.getSession().getAttribute(Constants.USER_SESSION);
				%>
				欢迎你：<%=backendUser.getUserName()%><strong> | 角色：<%=backendUser.getUserType()%></strong>
			</h3>
		</h3>
	</div>
</div>
<div class="clearfix"></div>        
<%@include file="common/footer.jsp" %>  
     