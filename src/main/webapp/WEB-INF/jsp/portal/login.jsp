<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>医疗数据仓库管理系统</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/admin/layui/css/layui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/login.css" />
</head>
<body>
<div class="m-login-bg">
    <div class="m-login">
        <h3>医疗数据仓库管理系统登录</h3>
        <div class="m-login-warp">
            <form class="layui-form" method="post" action="${ctx}/login.do?action=login">
                <div class="layui-form-item">
                    <input type="text" name="name" required lay-verify="required" placeholder="用户名" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-item">
                    <input type="password" name="password" required lay-verify="required" placeholder="密码" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-item m-login-btn">
                    <div class="layui-inline">
                        <input type="submit" style="width: 160px" value="登录" class="layui-btn layui-btn-normal">
                    </div>
                    <div class="layui-inline">
                        <button type="reset" class="layui-btn layui-btn-primary">取消</button>
                    </div>
                </div>
            </form>
        </div>
        <p class="copyright">Copyright 2018-2019 by LPP</p>
    </div>
</div>
</body>
</html>
