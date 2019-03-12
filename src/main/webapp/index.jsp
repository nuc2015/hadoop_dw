<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>正在跳转</title>
    <meta http-equiv="Content-Language" content="zh-CN">
    <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
    <meta http-equiv="refresh" content="0.2;url=${ctx}/login.do?action=index">
</head>
<body>
</body>
</html>
