<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="page-title" style="height: 32px; border-width: 0;">
	<div class="left-btn-ext">

	</div>
	<div class="right-btn-ext">
		<ul>
			<li><input type="text" name="tbFilter" id="tbFilter" /> <a
					onclick="filterModels()" alt="过滤"><img
					style="margin-bottom: 12px;" title="分析"
					src="${ctx}/resource/css/images/icon/search.png" width="16"
					height="16"></a></li>
		</ul>
	</div>
</div>
<div style="clear: both;">
	<ul id="models">
	</ul>
</div>