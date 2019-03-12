<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String nodeId = request.getParameter("nodeId");
%>

<script type="text/javascript">
<!--
	var jsNodeId = '<%=nodeId%>';var dsType;
	
	function deleteDs(dsId){
		if(jsNodeId == null || nodeId == ""){
            layer.alert("节点已经被删除，请重新选择需要删除的节点", {icon:0,offset: '130px'});
			return false;
		}

        layer.confirm("确认删除该数据源吗", {
                title:'确认框',
                offset: '100px',
                btn: ['确定','取消']
            }, function(){
                $.ajax({
                    url : '${ctx}/meta/delNode?rnd=' + Math.random(),
                    data : {
                        nodeId:nodeId
                    },
                    type : 'GET',
                    timeout:20000,
                    dataType : 'json',
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    success: function(data){
                        layer.alert(data.msg, {icon:1,offset: '130px'});
                        refreshNode({data:{type: "refresh", silent: false}},'father');
                        nodeId = "";
                    }
                });
            }, function(){
            }
        );

	}
	
	function queryDsInfo(nodeId){
		$.ajax({
		url : '${ctx}/meta/getMetaCore?rnd=' + Math.random(),
   		data : {
				nodeId:nodeId
   		       },
   		type : 'GET',
   		timeout:20000,
   		dataType : 'json',
   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){
			dsType = data.type;
			var dsInfoHtml = '<tr><th width="180">编号</th><td>' + data.id+ '</td></tr>';
			dsInfoHtml += '<tr><th width="180">名称</th><td>' + data.name+ '</td></tr>';
			
			if(data.type == "HIVE2KBS"){
				dsInfoHtml += '<tr><th width="180">数据库类型</th><td>Hive with Kerberos</td></tr>';
			}
			dsInfoHtml += '<tr><th width="180">地址</th><td>' + data.host+ '</td></tr>';
			dsInfoHtml += '<tr><th width="180">端口</th><td>' + data.port+ '</td></tr>';
			dsInfoHtml += '<tr><th width="180">服务名</th><td>' + data.serverName+ '</td></tr>';
			dsInfoHtml += '<tr><th width="180">访问用户</th><td>' + data.user+ '</td></tr>';
			
			if(data.type == "HIVE2KBS"){
				dsInfoHtml += '<tr><th width="180">krb5文件路径</th><td>' + data.krb5ConfigPath+ '</td></tr>';
				dsInfoHtml += '<tr><th width="180">Hadoop home路径</th><td>' + data.hadoopHomeDir+ '</td></tr>';
				dsInfoHtml += '<tr><th width="180">keytab文件路径</th><td>' + data.keytabPath+ '</td></tr>';
				dsInfoHtml += '<tr><th width="180">Principle</th><td>' + data.principal+ '</td></tr>';
			}
			
			$('#metaModel').html(dsInfoHtml);
		}
		})
	}
	
	function editDs(){
		$("#home0").empty().load("${ctx}/elements/addDs.jsp?dsId=<%=nodeId%>").css("float", "left").css("padding","4px");
	}
	
	$(document).ready(function() {
		queryDsInfo('<%=nodeId%>');
	});
//-->
</script>

<div class="page-title" style="height: 32px; border-width: 0;">
	<div class="left-btn-ext">
		<ul>
			<li><a id="saveHref" href="javascript:;" onclick="deleteDs()"> <img
					title="保存" src="${ctx}/resource/css/images/icon/dbdel.png"
					width="16" height="16"> 删除数据源
			</a></li>
			<li><a id="saveHref" href="javascript:;" onclick="editDs()"> <img
					title="保存" src="${ctx}/resource/css/images/icon/edit.png"
					width="16" height="16"> 修改数据源
			</a></li>
			
		</ul>
	</div>
</div>

<div class="fullheight page-title-wrapper">
	<div class="page-ctn">
		<div class="item p1">
			<div class="caption">
				<span>基本信息</span>
			</div>
			<div class="content">
				<table class="form-normal-left" cellspacing="0" cellpadding="0" border="0">
					<tbody id="metaModel">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>