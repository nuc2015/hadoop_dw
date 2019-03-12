<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String idx  = request.getParameter("idx");
	String nodeId = request.getParameter("nodeId");
	String nodeType = request.getParameter("nodeType");
	String rootType = request.getParameter("rootType");
%>

<script type="text/javascript">
<!--
	function addChDir(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getSelectedNodes();
		if (nodes.length<=0) {
			layer.msg('请选择父节点!', {time:3000, icon:1,offset: '100px'});
			return false;
		}
	
		$('#xcnt0').load("${ctx}/elements/addDir.jsp?nodeId=" + nodeId + "&nodeType=" + nodeType);
	}
//-->
</script>

<div class="page-title" style="height: 32px; border-width: 0;">
	<div class="left-btn-ext">
		<ul>
			<li><a id="saveHref" href="javascript:;" onclick="addChDir()"> <img
				title="添加下级目录" src="${ctx}/resource/css/images/icon/package.png"
				width="16" height="16"> 新增下级目录
			</a></li>
		</ul>
	</div>
</div>
<div id="xcnt0" class="cnt" style="margin:10px 4px 4px 16px;float:left;">
</div>