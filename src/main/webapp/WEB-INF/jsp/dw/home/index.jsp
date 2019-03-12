<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>医疗数据仓库管理系统</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/admin/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/admin.css"/>
</head>
<body>
<div class="main-layout" id='main-layout'>
    <!--侧边栏-->
    <div class="main-layout-side">
        <div class="m-logo"></div>
        <ul class="layui-nav layui-nav-tree" lay-filter="leftNav">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="iconfont">&#xe607;</i>数据资产</a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;" data-url="${ctx}/login.do?action=meta" data-id='1' data-text="元数据管理"><span class="l-line"></span>元数据管理</a></dd>
                    <dd><a href="javascript:;" data-url="${ctx}/login.do?action=etl" data-id='2' data-text="ETL"><span class="l-line"></span>ETL</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;" data-url="${ctx}/login.do?action=system" data-id='3' data-text="个人信息"><i class="iconfont">&#xe606;</i>个人信息</a>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;" data-url="${ctx}/login.do?action=system" data-id='4' data-text="系统设置"><i class="iconfont">&#xe60b;</i>系统设置</a>
            </li>
        </ul>
    </div>
    <!--右侧内容-->
    <div class="main-layout-container">
        <!--头部-->
        <div class="main-layout-header">
            <div class="menu-btn" id="hideBtn">
                <a href="javascript:;">
                    <span class="iconfont">&#xe60e;</span>
                </a>
            </div>
            <ul class="layui-nav" lay-filter="rightNav">
                <li class="layui-nav-item"><a href="javascript:;" data-url="" data-id='4' data-text="邮件系统"><i class="iconfont">&#xe603;</i></a></li>
                <li class="layui-nav-item">
                    <a href="javascript:;" data-url="" data-id='5' data-text="个人信息">超级管理员</a>
                </li>
                <li class="layui-nav-item"><a href="javascript:;">退出</a></li>
            </ul>
        </div>
        <!--主体内容-->
        <div class="main-layout-body">
            <!--tab 切换-->
            <div class="layui-tab layui-tab-brief main-layout-tab" lay-filter="tab" lay-allowClose="true">
                <ul class="layui-tab-title">
                    <li class="layui-this welcome">后台主页</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show" style="background: #f5f5f5;">
                        <!--1-->
                        <iframe src="${ctx}/login.do?action=meta" width="100%" height="100%" name="iframe" scrolling="auto" class="iframe" framborder="0"></iframe>
                        <!--1end-->
                    </div>
                </div>
            </div>
        </div>
        </div>
    </div>
    <!--遮罩-->
    <div class="">

    </div>
</div>
<script type="text/javascript">
    var scope={
        link:'./manager.jsp'
    }
</script>
<script src="${ctx}/static/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/admin/js/common.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/admin/js/main.js" type="text/javascript" charset="utf-8"></script>

</body>
</html>