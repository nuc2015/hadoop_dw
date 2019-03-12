<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
	String THEME_PATH = request.getParameter("THEME_PATH");
	String nodeId     = request.getParameter("nodeId");
	String nodeType   = request.getParameter("nodeType");
	String nodeFrom   = request.getParameter("nodeFrom");
	String nodeVersion= request.getParameter("nodeVersion");
	String columnId   = request.getParameter("columnId");
	String idx        = request.getParameter("idx");
	String dbName = nodeId.split("\\.")[1];
	String tableName = nodeId.split("\\.")[2];
%>
<style>
	.form-normal td,.form-normal th {
		text-align: center;
	}
	.form-normal .odd {
       background: #FFFF00 none repeat scroll 0 0;
    }
</style>

<script type="text/javascript">

$(document).ready(function(){
	var nodeId = '<%=nodeId%>';

	$(".op_tab_row<%=idx%> a").click(function(){
		var selClassName = $(this).attr("class");
		var tabName = selClassName.substring(0, selClassName.indexOf(" "));
		$(".op_tab_row<%=idx%> a").removeClass("selected");
		$(".op_tab_row<%=idx%> a."+tabName).addClass("selected");
		$("#op_cnt<%=idx%> > div").hide();
		
		$("#op_cnt<%=idx%> > div."+tabName).show();
		
		if($(this).text() == "基本信息"){
			$("#tbscrolldiv").show();
		}
	});
	
	$.ajax({
		url : '${pageContext.request.contextPath}/meta/queryNodeInfo?rnd='+Math.random(),
		data : {nodeId:'<%=nodeId%>',
	   			nodeFrom:'<%=nodeFrom%>',
			    nodeVersion:'<%=nodeVersion%>'
        },
   		type : 'GET',
   		dataType : 'json',
   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){

			$("#metaModel<%=idx%>").empty();
			$.each(data.properties,function(idx, element) {
				$("<tr><th width='180'>"+element.key+"</th><td style='text-align:left;'>"+element.value+"</td></tr>").appendTo($("#metaModel<%=idx%>"));
			});
			
			if(data.columns != null){
				var nullHtml = "";var pkHtml;
				
				$("#clmcnt<%=idx%>").empty();
				$.each(data.columns,function(idx, element) {
					if(element.nullable){
						nullHtml = "<input type=checkbox  />";
					}else{
						nullHtml = "<input type=checkbox checked/>";
					}
					
					if(element.pk){
						pkHtml = "<input type=checkbox checked/>";
					}else{
						pkHtml = "<input type=checkbox />";
					}

                    var length = element.length==null?"":element.length;
                    var precision = element.precision ==null?"":element.precision;
                    if(element.id=='<%=columnId%>')
                        $("<tr class='odd'><td>"+element.name+"</td><td>"+element.chName+"</td><td>"+element.type+"</td><td>"+length+"</td><td>"+precision+"</td><td>"+nullHtml+"</td><td>"+pkHtml+"</td><td>"+element.cmt+"</td></tr>").appendTo($("#clmcnt<%=idx%>"));
                    else
                        $("<tr><td>"+element.name+"</td><td>"+element.chName+"</td><td>"+element.type+"</td><td>"+length+"</td><td>"+precision+"</td><td>"+nullHtml+"</td><td>"+pkHtml+"</td><td>"+element.cmt+"</td></tr>").appendTo($("#clmcnt<%=idx%>"));
                });
			}
		}
	});


	$.ajax({
		url : '${ctx}/etl/queryTableData?nodeId='+ nodeId,
		type : 'GET',
		dataType : 'json',
		success: function(data){
			showTableDataList('<%=idx%>', data)
		}
	});
});

function showTableDataList(idx, data){
	var container = document.getElementById('table-data-list'+idx);
	var dataList = data.clms;
	var metaList = data.cols;
	if (dataList.length>0){
		var hot = new Handsontable(container, {
			data: dataList,
			colHeaders: metaList,
			rowHeaders: true,
			manualColumnResize: true,
			height:$(document).height()-420,
			contextMenu: false,
			manualRowMove:true,
			minSpareRows: 0,
			manualColumnResize: true,
			fillHandle: false,//拖动不自动填充值
			columnSorting: true,
		});
	}else{
		var hot = new Handsontable(container, {
			colHeaders: metaList,
			rowHeaders: true,
			manualRowMove:true,
			manualColumnResize: true,
			fillHandle: false,//拖动不自动填充值
			columnSorting: true,
			height:$(document).height()-420
		});
	}
}

function showTableStaticInfo(dbName, tableName){
	layer.open({
		title:'表信息查看',
		type: 2,
		content: ['${ctx}/elements/tableDataInfo.jsp?dbName='+dbName+'&tableName='+tableName, 'no'],
		area: ['910px', '410px'],
		offset: '130px',
		closeBtn: 1,
		maxmin: true,
		moveOut: true
	});
}

function exportHiveTableDataList(dbName, tableName){
	window.location.href = '${ctx}/meta/exportHiveTableData?dbName='+dbName+'&tableName='+tableName;
}

</script>
<div class="cnt">
	<div style="height:32px;" class="page-title" style="width:100%;">
		<span id="metaPath"></span>
		<div class="left-btn-ext">
			<ul>
                <li id="showHiveTableStaticInfoId<%=idx%>">
                    <a href="javascript:void(0);" onclick="showTableStaticInfo('<%=dbName%>','<%=tableName%>')"><img height="16" width="16" title="表信息查看" src="${ctx}/resource/css/images/icon/import.png" />表信息查看</a>
                </li>
                <li id="exportHiveTableDataListId<%=idx%>">
                    <a href="javascript:void(0);" onclick="exportHiveTableDataList('<%=dbName%>','<%=tableName%>')"><img height="16" width="16" title="表样例数据导出" src="${ctx}/resource/css/images/icon/import.png" />表样例数据导出</a>
                </li>
			</ul>
		</div>
	</div>
	<div class="box">
		<div class="title">
			<div class="op_tab">
				<ul class="op_tab_row<%=idx%> op_tab_row">
					<li><a class="tab1 tab_item selected" href="javascript:void(0);" pmode="1">基本信息</a></li>
					<li><a class="tab2 tab_item " href="javascript:void(0);" pmode="1">字段</a></li>
					<li><a class="tab3 tab_item " href="javascript:void(0);" pmode="1">表数据</a></li>
				</ul>
			</div>
			<div id="op_cnt<%=idx%>">
				<div id="tab1" style="display: none; width: 98%;height:200px;" class="tab1 manage_content_todo">
					<table border="0" cellspacing="0" cellpadding="0" class="form-normal">
						<tbody id="metaModel<%=idx%>">
						</tbody>
					</table>
					<div class="item p1">
				</div>
				</div>
				<div id="tab2" style="width:98%;height:550px;overflow-y: auto;" class="tab2 manage_content_todo">
					<table class="form-normal" id="tableclm" style="width:100%;">
						<thead>
							<tr>
								<th width="200">字段</th>
								<th  width="100">中文名</th>
								<th  width="40">类型</th>
								<th  width="40">长度</th>
								<th  width="40">小数点</th>
								<th  width="50">不允许为空</th>
								<th  width="50">是否主键</th>
								<th width="300">描述</th>
							</tr>
						</thead>
						<tbody id="clmcnt<%=idx%>">
							<tr><td style="text-align:left;">
							<img src='${ctx}/resource/js/ztree/css/zTreeStyle/img/loading.gif' />
							</td></tr>
						</tbody>
					</table>
				</div>
				<div id="tab3" style="display: none; width: 98%;height:200px;" class="tab3 manage_content_todo">
					<div id="table-data-list<%=idx%>"></div>
				</div>
			</div>
		</div>
	</div>
</div>