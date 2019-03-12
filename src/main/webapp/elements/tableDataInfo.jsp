<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta charset="utf-8">
<title>表信息查看</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${ctx}/resource/layui/css/layui.css" media="all">
<html>
<head>
    <title>表信息查看</title>
</head>
<body>
<table id="testTable" lay-filter="testTable">
</table>

<script src="${ctx}/resource/layui/layui.all.js"></script>
<script src="${ctx}/resource/js/jquery/jquery-1.9.1.min.js"></script>
<script>
$(function($){
    var table = layui.table;

    var url = window.location.search;
    var dbName = url.split('&')[0].split('=')[1];
    var tableName = url.split('&')[1].split('=')[1];

    var data = new Array();
    var cols = new Array();
    var layerIndex = layui.layer.msg('加载中，请稍等......', {
        icon: 16,shade: 0.2,time: 0,offset: '130px'
    });
    $.ajax({
        url : '${ctx}/meta/getTableStaticInfo?dbName='+dbName+'&tableName='+tableName,
        type : 'GET',
        dataType : 'json',
        success: function(data){
            cols = data.cols;
            data = data.data;
            layui.layer.close(layerIndex);
            //执行渲染
            var ta = table.render({
                elem: '#testTable'
                ,width: 900
                ,height: 350
                ,cols: cols
                ,data: data
                ,skin: 'row' //表格风格
                ,even: true
                ,limit: data.length
            });
        }
    });

});


</script>
</body>
</html>
