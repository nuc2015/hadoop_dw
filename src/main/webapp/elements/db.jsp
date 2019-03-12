<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String idx  = request.getParameter("idx");
	String nodeId = request.getParameter("nodeId");
%>

<script type="text/javascript">
<!--
	function createDs(){
		$('#xcnt0').load("${ctx}/elements/addDs.jsp?prtId=<%=nodeId%>");
	}
	
    function syncHiveHbase(){
        $.ajax({
            url : '${ctx}/meta/syncDatabase?rnd=' + Math.random(),
            data : {
                nodeId:'<%=nodeId%>'
            },
            type : 'GET',
            dataType : 'json',
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function(data){
                layer.alert(data.msg, {icon:1,offset: '130px'});
                refreshNode({data:{type: "refresh", silent: false}});
                nodeId = "";
            }
        });
    }
//-->
</script>

<div class="page-title" style="height: 32px; border-width: 0;">
	<div class="left-btn-ext">
		<ul>
			<li><a id="saveHref" href="javascript:;" onclick="createDs()"> <img
					title="添加数据源" src="${ctx}/resource/css/images/icon/db--add.png"
					width="16" height="16"> 添加数据源
			</a></li>
            <li>
                <a id="saveHref" href="javascript:;" onclick="syncHiveHbase()"> <img
                        title="同步数据源" src="${ctx}/resource/css/images/icon/import.png"
                        width="16" height="16"> 同步数据源
                </a>
            </li>
		</ul>
	</div>
</div>

<div id="xcnt0" class="cnt" style="margin:10px 4px 4px 16px;float:left;">
	
</div>