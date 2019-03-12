<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String idx  = request.getParameter("idx");
	String nodeId = request.getParameter("nodeId");
	String parentNode = request.getParameter("parentNode");
	String nodeType = request.getParameter("nodeType");
%>

<script>
	$(document).ready(function() {
		initEntityTab('<%=idx%>');
		queryEntity('<%=nodeId%>', '<%=idx%>', '<%=parentNode%>');
	});

	function addRow(idx){
		var xhot ;
		for(var x=0;x<tbArray.length; x++){
			if(tbArray[x].xidx == idx){
				xhot = tbArray[x].tb;
				break;
			}
		}
		if(xhot != null){
			xhot.alter('insert_row', null, 1);
		}
	}

	function  delRelRow(idx){
		var rhot ;
		for(var x=0;x<tbRelArray.length; x++){
			if(tbRelArray[x].xidx == idx){
				rhot = tbRelArray[x].tb;
				break;
			}
		}
		if(rhot != null){
			var selection = rhot.getSelected();
			if(selection != null){
				rhot.alter('remove_row', selection[0], 1);
			}else{
				layer.alert("选择需要删除的行", {icon:0,offset: '100px'});
			}
		}
	}

	function delRow(idx){
		var xhot ;
		for(var x=0;x<tbArray.length; x++){
			if(tbArray[x].xidx == idx){
				xhot = tbArray[x].tb;
				break;
			}
		}
		if(xhot != null){
			var selection = xhot.getSelected();
			if(selection != null){
				xhot.alter('remove_row', selection[0], 1);
			}else{
				layer.alert("选择需要删除的行", {icon:0,offset: '100px'});
			}
		}
	}

	function insertRow(idx){
		var xhot ;
		for(var x=0;x<tbArray.length; x++){
			if(tbArray[x].xidx == idx){
				xhot = tbArray[x].tb;
				break;
			}
		}
		if(xhot != null){
			var selection = xhot.getSelected();

			if(selection == null){
				layer.alert("请选择插入位置", {icon:0,offset: '100px'});
			}else{
				xhot.alter('insert_row', selection[0], 1);
			}
		}

	}

	function saveAsModel(idx){
		var xhot ;
		for(var x=0;x<tbArray.length; x++){
			if(tbArray[x].xidx == idx){
				xhot = tbArray[x].tb;
				break;
			}
		}

		if(xhot != null){
			layer.prompt({title: "请输入表名",offset:'130px'},function(modelName, index){
				layer.close(index);
				if(modelName != null && modelName != ""){
					$.ajax({
						url : '${ctx}/meta/saveAsPdmModelNodeInfo?rnd=' + Math.random(),
						data : {
							prtNodeId:xhot.nodeId,
							nodes:JSON.stringify(xhot.getData()),
							modelName:modelName
						},
						type : 'POST',
						dataType : 'json',
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						success: function(data){
							if(data.result == "fail"){
								layer.alert(data.msg, {icon:0,offset: '100px'});
								return false;
							}else{
								xhot.loadData(data.columns);

								////todo
								xhot.nodeId = data.nodeId;

								$('#navtabs li.active a').text(modelName);

								var ordDatas = [];
								for(var x=0; x<data.columns.length; x++){
									ordDatas.push(data.columns[x]);
								}

								xhot.ordDatas = ordDatas;

								refreshNode({data:{type: "refresh", silent: false}},'father');
								layer.alert("保存成功！", {icon:1,offset: '100px'});
							}
						}
					});
				}
			});
		}
	}

</script>

<div class="page-title" style="height: 32px; border-width: 0;">
	<div class="left-btn-ext" style="width:950px">
		<ul>
			<li><a id="saveHref" href="javascript:;" onclick="saveModel('<%=idx%>')"> <img
					title="保存" src="${ctx}/resource/css/images/icon/save.png"
					width="16" height="16"> 保存
			</a></li>
			<li><a id="saveHref" href="javascript:;" onclick="saveAsModel('<%=idx%>')"> <img
					title="保存" src="${ctx}/resource/css/images/icon/saveas.png"
					width="16" height="16"> 另存为
			</a></li>
			<li class="seperator"></li>
			<li><a id="addPropHref" href="javascript:;" onclick="addRow('<%=idx%>')"> <img
					title="添加属性" src="${ctx}/resource/css/images/icon/addc.png"
					width="16" height="16"> 添加字段
			</a></li>
			<li><a id="addPropHref" href="javascript:;" onclick="insertRow('<%=idx%>')"> <img
					title="添加属性" src="${ctx}/resource/css/images/icon/insert.png"
					width="16" height="16"> 插入字段
			</a></li>
			<li><a id="delPropHref" href="javascript:;" onclick="delRow('<%=idx%>')"> <img
					title="删除属性" src="${ctx}/resource/css/images/icon/del.png"
					width="16" height="16"> 删除字段
			</a></li>
			<li><a id="delPropHref" href="javascript:;" onclick="delRelRow('<%=idx%>')"> <img
					title="删除属性" src="${ctx}/resource/css/images/icon/del.png"
					width="16" height="16"> 删除关系
			</a></li>
		</ul>
	</div>
</div>

<div id="xcnt0" class="cnt" style="margin:10px 4px 4px 0px;">
	<div class="box">
		<div class="op_tab">
			<ul class="op_tab_row<%=idx%> op_tab_row">
				<li style="margin-left:25px;"><a class="tab1 tab_item selected"
					href="javascript:void(0);" pmode="1">属性信息</a></li>
				<li><a class="tab2 tab_item" href="javascript:void(0);"
					pmode="1">关系</a></li>
				<li><a class="tab3 tab_item" href="javascript:void(0);"
					   pmode="1">基本信息</a></li>
			</ul>
		</div>
		<div id="op_cnt<%=idx%>">
			<div
				style="width: 98%; height: 550px; overflow-y: hidden;margin-top: -7px;"
				 class="tab1 manage_content_todo">
				<div id="modelCnt<%=idx %>">
				</div>
			</div>
			<div style="display: none;width:98%;height:200px;margin-top: -7px;" class="tab2 manage_content_todo">
				<div id="modelRelCnt<%=idx %>" >
					
				</div>
			</div>
			<div style="display: none;width:98%;height:200px;margin-top: -7px;" class="tab3 manage_content_todo">
				<div id="modelBaseInfo<%=idx %>" >
					<table class="form-normal-left" cellspacing="0" cellpadding="0" border="0">
						<tbody id="cdmModel<%=idx %>">
						<tr>
							<th width="100">名称</th>
							<td><input type="text" id="cdmName<%=idx %>" name="cdmName<%=idx %>" /></td>
						</tr>
						<tr>
							<th width="100">中文名</th>
							<td><input type="text" id="cdmChName<%=idx %>" name="cdmChName<%=idx %>" /></td>
						</tr>
						<tr>
							<th width="100">描述</th>
							<td><input type="text" id="cmt<%=idx %>" name="cmt<%=idx %>" /></td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

