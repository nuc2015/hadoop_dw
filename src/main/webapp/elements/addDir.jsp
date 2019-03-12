<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="st" uri="http://www.si-tech.com/tag"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String nodeId = request.getParameter("nodeId");
	String nodeType = request.getParameter("nodeType");
%>

<table class="form-table" style="width:650px;">
	<tbody>
		<tr>
			<td>目录名：</td>
			<td>
				<input id="name" name="name" style="width: 200px;height: 30px;" type="text">
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td>目录描述：</td>
			<td>
				<input id="dirDesc" name="dirDesc" style="width: 200px;height: 30px;" type="text">
				<font color="red">*</font>
			</td>
		</tr>
	</tbody>
</table>

<div style="text-align:left;margin-left:164px;width:100%">
	<p> </p>
	<input id="importbutton" class="btn btn-w-m btn-success" value="添加目录" onclick="addDirSubmit()" type="button">
	<p> </p>
</div>

<script>
function addDirSubmit(){
	var dirName = $("#name").val();
	var dirDesc = $("#dirDesc").val();
	$.ajax({
		url : '${ctx}/meta/addDir?rnd=' + Math.random(),
   		data : {
   			dirName: dirName,
   			dirDesc: dirDesc,
   			nodeId: '<%=nodeId%>',
   			nodeType: '<%=nodeType%>'
   		},
   		type : 'POST',
   		timeout:20000,
   		dataType : 'json',
   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){
			refreshNode({data:{type: "refresh", silent: false}});
            layer.alert(data.msg, {icon:1,offset: '100px'});
		}
	});
}
</script>