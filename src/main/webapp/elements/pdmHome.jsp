<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String idx = request.getParameter("idx");
	String prtId = request.getParameter("prtId");
	String nodeId = request.getParameter("nodeId");
	String nodeType = request.getParameter("nodeType");
	String rootType = request.getParameter("rootType");
%>
<style>
    .coverWidth{
        width: 250px !important;
        text-overflow:ellipsis;white-space:nowrap;overflow:hidden;
    }
</style>
<script>
$(document).ready(function() {
	loadCdmEntity();
	initModel();
});

function queryEntity(nodeId, idx, parentNode){
	if(parentNode != null && parentNode != "" && parentNode != "null"){
		createEditTables(null, idx, null, parentNode);
	}else{
		$.ajax({
			url : '${ctx}/meta/queryModelNodeInfo?rnd=' + Math.random(),
			data : {
				nodeId:nodeId
			},
			type : 'GET',
			dataType : 'json',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
				createEditTables(data, idx, nodeId, null);
			}
		});
	}
}

function RemoveUnWantedHeader(col, th) {
    if (th.textContent == "A" || th.textContent == "B" || th.textContent == "C" 
       || th.textContent == "D" || th.textContent == "E"
       || th.textContent == "F" || th.textContent == "G" || th.textContent == "H" 
       || th.textContent == "I" || th.textContent == "J"
       || th.textContent == "K" || th.textContent == "L" || th.textContent == "M" 
       || th.textContent == "N" || th.textContent == "O"
       || th.textContent == "P" || th.textContent == "Q" || th.textContent == "R" 
       || th.textContent == "S" || th.textContent == "T"
       || th.textContent == "U" || th.textContent == "V" || th.textContent == "W" 
       || th.textContent == "X" || th.textContent == "Y" || th.textContent == "Z"
       || th.textContent == "AQ" || th.textContent == "AR" || th.textContent == "AS"
       || th.textContent == "AT" || th.textContent == "AU" || th.textContent == "AV" || th.textContent == "AW") {
        th.style.display = 'none';
    }
}

function createEditTables(data, idx, nodeId, parentNode){
	var modelContainer = document.getElementById('modelCnt' + idx);
	var relModelContainer = document.getElementById('modelRelCnt' + idx);
	var dplInfoContainer = document.getElementById('dplInfo' + idx);

	var hideFirstClmRenderer = function(instance, td, row, col, prop, value, cellProperties) {
		if(col == 0){
			td.hidden = true;
		}else{
			td.hidden = false;
		}
	};
	var ordDatas = [];var dataClms;var dataClmNames = [];
	if(data && data != null){
		for(var x=0; x<data.columns.length; x++){
			ordDatas.push(data.columns[x]);
			dataClmNames.push(data.columns[x].name);
		}
		dataClms = data.columns;
	}else{
		dataClms = [];
	}

	$("#op_cnt"+idx+" > div.tab2").show();
	var relModelContainer = document.getElementById('modelRelCnt' + idx);

	var rdata = [];
	if(data && data.relations && data.relations.length >0){
		rdata = data.relations;
	}
	var rhot = new Handsontable(relModelContainer,{
		data: rdata,
		colHeaders: ['名称','字段','引用表','引用字段','删除时','更新时','备注'],
		rowHeaders: true,
		colWidths: [100,100, 200, 100, 100, 100, 220],
		outsideClickDeselects: false,
		contextMenu: false,
		manualRowMove:true,
		minSpareRows: 1,
		manualColumnResize: true,
		fillHandle: true,//拖动不自动填充值
		columnSorting: true,
		height:$(document).height()-420,
		columns:[
			{data:'cmt'},
			{data:'srcName',type: 'autocomplete', source:dataClmNames},
			{data:'dest',type: 'autocomplete', source:function (query, process) {
					$.ajax({
						url : '${ctx}/meta/queryModelNames?rnd=' + Math.random(),
						data : {
							parentNode:'<%=prtId%>',
							type:'model.PhysicalModel'
						},
						type : 'GET',

						dataType : 'json',
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						success: function(xdata){
							process(xdata);
						}
					});
				}},
			{data:'destName',type:'autocomplete', source: function (query, process) {
					var selection = rhot.getSelected();
					if(selection[0] >= 0){
						var modelName = rhot.getData()[selection[0]][2];
						if(modelName == null || modelName == ""){
							layer.alert("请选择引用表", {icon:0,offset: '100px'});
						}else{
							$.ajax({
								url : '${ctx}/meta/queryPdmModelClms?rnd=' + Math.random(),
								data : {
									modelName:modelName,
									parentNode:'<%=prtId%>'
								},
								type : 'GET',

								dataType : 'json',
								contentType: "application/x-www-form-urlencoded; charset=UTF-8",
								success: function(xdata){
									process(xdata);
								}
							});
						}
					}
				}},
			{data:'relType',type: 'autocomplete',source: ['RESTRICT', 'NOACTION','CASCADE','SETNULL']},
			{data:'calType',type:'autocomplete',source: ['RESTRICT', 'NOACTION','CASCADE','SETNULL']},
			{data:'relDtl'}
		]
	});
	$("#op_cnt"+idx+" > div.tab2").hide();

	var xhot = new Handsontable(modelContainer,{
		data: dataClms,
		colHeaders: ['字段','类型','属性名称','属性描述','长度','小数点','是否允许为空','是否主键','备注'],
		rowHeaders: true,
		colWidths: [100,100, 200, 100, 100, 100, 100,220,100],
		outsideClickDeselects: false,
		contextMenu: false,
		manualRowMove:true,
		minSpareRows: 0,
		manualColumnResize: true,
		fillHandle: false,//拖动不自动填充值
		columnSorting: true,
		height:$(document).height()-420,
		columns:[
			{data:'id', renderer:hideFirstClmRenderer},
			{data:'name'},
			{data:'type', type: 'autocomplete',source: ['VARCHAR','CHAR','INT','LONG','DECIMAL','FLOAT','NUMBER','DATE','DATETIME','TEXT']},
			{data:'chName'},
			{data:'remarks'},
			{data:'length'},
			{data:'precision'},
			{data:'nullable',type:'checkbox'},
			{data:'pk',type:'checkbox'},
			{data:'cmt'}
		]
	});

	if(parentNode){
		xhot.parentNode = parentNode;
		xhot.alter('insert_row', null, 1);
	}else{
		$('#cdmName' + idx).val(data.name);
		$('#cdmChName' + idx).val(data.chName);
		$('#cmt' + idx).val(data.msg);
	}

	xhot.ordDatas = ordDatas;
	xhot.editCells = [];
	xhot.nodeId = nodeId;

	xhot.addHook('afterGetColHeader', RemoveUnWantedHeader);

	var changedRow, changedCol;
	xhot.updateSettings({
		beforeKeyDown:function (e){
			var selection = xhot.getSelected();
			if(selection){
				changedRow = selection[0];
				changedCol = selection[1];
			}
		},
		afterChange: function (e) {
			if(changedRow>=0){
				xhot.editCells.push([changedRow, changedCol]);
			}
		}
	});

	var extTable = tableExist(tbArray, idx);
	if(extTable == null){
		tbArray.push({xidx:idx, tb:xhot});
	}else{
		extTable.tb = xhot;
	}

	var exRelArray = tableExist(tbRelArray, idx);
	if(exRelArray == null){
		tbRelArray.push({xidx:idx, tb:rhot});
	}else{
		tbRelArray.tb = rhot;
	}
}

function tableExist(tbArray, idx){
	for(var x=0; x<tbArray.length; x++){
		if(tbArray[x].xidx == idx){
			return tbArray[x];
		}
	}
	return null;
}

function isNodeDel(nodeId, currentNodes){
	for(var x=0; x<currentNodes.length; x++){
		if(currentNodes[x][0] == nodeId){
			return false;
		}
	}
	
	return true;
}

function loadCdmEntity(){
	$.ajax({
		url : '${ctx}/meta/queryTreeNode?rnd=' + Math.random(),
   		data : {
					id:'<%=nodeId%>',
                    nodeType: ''
   		       },
   		type : 'GET',
   		dataType : 'json',
   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){
			mainCntMdl = data;	
			var modelCnt = $('#models').empty();
			modelCnt.height($(window).height() - 50);
		
			$.each(data,function(idx,element){
				modelCnt.append('<li title="'+element.id+'" class="modelInfo coverWidth">' + element.name+'</li>');
			})
			
			$("#models li.modelInfo").dblclick(function(){
				nodeId = $(this).attr("title");
				createCdmTab($(this).text(), nodeId, 'pdm');
			})
			//models
			initLiTableEvt();
		}
	});
}

function delEntity(){
	var selImportModels = [];
	
	$('#models li.selx').each(function(idx, element){
		selImportModels.push($(element).attr("title"));
	});
	
	if(selImportModels.length <= 0){
        layer.alert("请选择需要删除的模型！", {icon:0,offset: '100px'});
		return false;
	}else{
		$.ajax({
			url : '${ctx}/meta/delMetaNode?rnd=' + Math.random(),
	   		data : {
	   					nodeId:selImportModels.join(",")
	   		       },
	   		type : 'POST',

	   		dataType : 'json',
	   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
                refreshNode({data:{type: "refresh", silent: false}});
				if(data.result == "fail"){
                    layer.alert(data.msg, {icon:0,offset: '100px'});
					return false;
				}else{
                    loadCdmEntity();
                    layer.alert("删除成功", {icon:1,offset: '100px'});
				}
			}
		});
	}
}

function initModel(){
	if($('.jqx-popup').length <= 0){
		$('#importWindow').jqxWindow({
            maxHeight: 600, maxWidth: 900, minHeight: 600, minWidth: 900, height: 600, width: 900,
            resizable: false, isModal: true, modalOpacity: 0.3,
            cancelButton: $('#cancel'),
            initContent: function () {
                $('#cancel').jqxButton({ width: '65px' });
            }
        });
		$('#importWindow').jqxWindow('close');
		
		$('#deployWindow').jqxWindow({
            maxHeight: 600, maxWidth: 900, minHeight: 600, minWidth: 900, height: 600, width: 900,
            resizable: false, isModal: true, modalOpacity: 0.3,
            cancelButton: $('#xcancel'),
            initContent: function () {
                $('#xcancel').jqxButton({ width: '65px' });
            }
        });
		$('#deployWindow').jqxWindow('close');
		
	}else{
		$('.jqx-popup').remove();
		$('.jqx-window-modal').remove();
		
		$('#importWindow').jqxWindow({
            maxHeight: 600, maxWidth: 900, minHeight: 600, minWidth: 900, height: 600, width: 900,
            resizable: false, isModal: true, modalOpacity: 0.3,
            cancelButton: $('#cancel'),
            initContent: function () {
                $('#cancel').jqxButton({ width: '65px' });
            }
        });
		$('#importWindow').jqxWindow('close');
		
		$('#deployWindow').jqxWindow({
            maxHeight: 600, maxWidth: 900, minHeight: 600, minWidth: 900, height: 600, width: 900,
            resizable: false, isModal: true, modalOpacity: 0.3,
            cancelButton: $('#xcancel'),
            initContent: function () {
                $('#xcancel').jqxButton({ width: '65px' });
            }
        });
		$('#deployWindow').jqxWindow('close');
	}
}


var crtPrtID;
function importModel(nodeId){
	$('#importWindow').jqxWindow('open');
    $('.jqx-popup').css("top", "30px");
    $('#apxDetailApxTgt').css("height", "20px");
    crtPrtID = nodeId;

    $.ajax({
        url : '${ctx}/meta/queryMetaNodes?rnd=' + Math.random(),
        data : {
            metaMdl:'phy.DataSource',
            withStrc:'1'
        },
        type : 'GET',
        dataType : 'json',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function(data){
            var dsHtml = "";
            $.each(data, function(idx, element){
                dsHtml += '<option value="'+element[0]+'">' + element[1] + '</option>';
            });
            $('#datasource').html(dsHtml);

            queryDsSchemas();
        }
    });
}

function queryDsSchemas(){
	$('#xloading').show();
	var dsId = $('#datasource').val();
	$.ajax({
		url : '${ctx}/meta/queryTreeNode?rnd=' + Math.random(),
   		data : {
   					id:dsId,
   					nodeType:'phy.DataSource'
   		       },
   		type : 'GET',
   		dataType : 'json',
   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){
			var schemaHtml = "";
			$.each(data, function(idx,element){
				schemaHtml += '<option value="'+element.id+'">' + element.name+ '</option>';
			})
			
			$('#dsSchema').html(schemaHtml);
			querySchemaModels();
		}
	});
}

function cmpModelDiff(modelId){
	$.ajax({
			url : '${ctx}/meta/cmpModelDiff?rnd=' + Math.random(),
	   		data : {
	   				modelId:modelId,
	   				dsInfo:$('#xdsSchema').val()
	   		       },
	   		type : 'GET',
	   		dataType : 'json',
	   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
				$('.jqx-tooltip').each(function(){
					if($(this).css("display") == "block"){
						$(this).find('.jqx-tooltip-text').html(data.diffCmt);
					}
				})
			}
	});
}

function deployModel(){
	var selImportModels = [];
	
	$('#models li.selx').each(function(idx, element){
		selImportModels.push($(element).attr("title"));
	});
	
	if(selImportModels.length <= 0){
        layer.alert("请选择需要部署的模型！", {icon:0,offset: '100px'});
		return false;
	}else{
		$('#deployWindow').jqxWindow('open');
		$('.jqx-popup').css("top", "30px");
		
		$('#cmpClmCnt').empty();
		$('#dplSqls').empty();
		
		$.ajax({
			url : '${ctx}/meta/queryMetaNodes?rnd=' + Math.random(),
	   		data : {
				metaMdl:'phy.DataSource'
			},
	   		type : 'GET',
	   		dataType : 'json',
	   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
				var dsHtml = "";
				$.each(data, function(idx, element){
					dsHtml += '<option value="'+element[0]+'">' + element[1] + '</option>';
				});
				$('#xdatasource').html(dsHtml);
				queryDplDsSchemas();
			}
		});
	}
}

function queryDplDsSchemas(){
	var dsId = $('#xdatasource').val();
	$.ajax({
		url : '${ctx}/meta/queryTreeNode?rnd=' + Math.random(),
   		data : {
			id:dsId,
			nodeType:'phy.DataSource'
		},
   		type : 'GET',
   		dataType : 'json',
   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){
			var schemaHtml = "";
			$.each(data, function(idx,element){
				schemaHtml += '<option value="'+element.id+'">' + element.name+ '</option>';
			})
			
			$('#xdsSchema').html(schemaHtml);
		}
	});
}


var phyMdls ;
function querySchemaModels(){
	$('#xloading').show();
	var dsId = $('#dsSchema').val();
		
	$.ajax({
		url : '${ctx}/meta/queryTreeNode?rnd=' + Math.random(),
   		data : {
   					id:dsId,
   					nodeType:'tables'
   		       },
   		type : 'GET',

   		dataType : 'json',
   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){
			$('#xloading').hide();
			phyMdls = data;
			var schemaHtml = "";
			var modelCnt = $('#phyModels').empty();
			$.each(data, function(idx,element){
				modelCnt.append('<li title="'+element.id+'" class="modelInfo coverWidth">' + element.name+'</li>');
			})
			
			initTblLiTableEvt();
		}
	});
}

function initTblLiTableEvt(){
	$("#phyModels li").click(function(){
		if($(this).hasClass('selx')){
			$(this).removeClass("selx");
		}else{
			$(this).addClass("selx");
		}
	})
}

function filterPhyModels(){
	var tableRule = $('#tableRule').val();
	
	if(phyMdls.length > 0){
		var modelCnt = $('#phyModels').empty();
		if(tableRule == null || tableRule == ""){
			$.each(phyMdls, function(idx,element){
				modelCnt.append('<li title="'+element.id+'" class="modelInfo coverWidth">' + element.name+'</li>');
			})
		}else{
			
			$.each(phyMdls, function(idx,element){
				if(element.name.toLowerCase().indexOf(tableRule.toLowerCase()) >= 0){
					modelCnt.append('<li title="'+element.id+'" class="modelInfo coverWidth">' + element.name+'</li>');
				}
			})
		}
	}
	initTblLiTableEvt();
}
var flag=0;
function selectAllModels(){
    if(flag==0){
        $("#phyModels li").each(function (i) {
            $(this).addClass("selx");
        });
        flag=1;
    }else{
        $("#phyModels li").each(function (i) {
            $(this).removeClass("selx");
        });
        flag=0;
    }
}
var flag1=0;
function selectAllLogicModel(){
    if(flag1==0){
        $("#models li").each(function (i) {
            $(this).addClass("selx");
        });
        flag1=1;
    }else{
        $("#models li").each(function (i) {
            $(this).removeClass("selx");
        });
        flag1=0;
    }
}

function importToCdm(){
	var selImportModels = [];
	
	$('#phyModels li.selx').each(function(idx, element){
		selImportModels.push($(element).attr("title"));
	});
	
	if(selImportModels.length <= 0){
        layer.alert("请选择需要导入的模型！", {icon:0,offset: '100px'});
		return false;
	}else{
		$.ajax({
			url : '${ctx}/meta/importPdmModel?rnd=' + Math.random(),
		   	data : {
		   			prtNodeId:crtPrtID,
		   			nodes:selImportModels.join(",")
		   		   },
		   	type : 'POST',
		   	dataType : 'json',
		   	contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
				if(data.result == "fail"){
                    layer.alert(data.msg, {icon:0,offset: '100px'});
					return false;
				}else{
                    refreshNode({data:{type: "refresh", silent: false}});
                    loadCdmEntity();
                    layer.alert("成功导入["+data.length+"]个模型:" + data.join(','), {icon:1,offset: '100px'});
				}
			}
		});
	}
}

function exportDDL(){
	var conditionFormParas = [];
	var schema = $('#xdsSchema').val();
	var selImportModels = [];
	$('#models li.selx').each(function(idx, element){
		selImportModels.push($(element).attr("title"));
	});
	conditionFormParas.push({name:'schema',value:schema});
	
	var importModels = selImportModels.join(",");
	conditionFormParas.push({name:'nodes',value:importModels});
	
	ajax_download('${ctx}/meta/exportModelDDL',conditionFormParas);
	
	return false;
}

/*导出*/
function ajax_download(url, data) {
	$('#download_iframe').remove();
	
    var $iframe,
        iframe_doc,
        iframe_html;

    if (($iframe = $('#download_iframe')).length === 0) {
        $iframe = $("<iframe id='download_iframe'" +
                    " style='display: none' src='about:blank'></iframe>"
                   ).appendTo("body");
    }
    iframe_doc = $iframe[0].contentWindow || $iframe[0].contentDocument;
    
    if (iframe_doc.document) {
        iframe_doc = iframe_doc.document;
    }

    iframe_html = "<html><head></head><body><form method='POST' action='" +
                  url +"'>" 
	
    $.each(data, function(idx, element){
    	iframe_html += "<input type='hidden' name='"+element.name+"' value='"+element.value+"'>";
    });

        iframe_html +="</form></body></html>";

    iframe_doc.open();
    iframe_doc.write(iframe_html);
    $(iframe_doc).find('form').submit();
}

/*模型比对*/
function cmpModels(){
	var schema = $('#xdsSchema').val();
	
	var selImportModels = [];
	$('#models li.selx').each(function(idx, element){
		selImportModels.push($(element).attr("title"));
	});
	
	var cmpClmCnt = $('#cmpClmCnt').empty();
	
	$.ajax({
		url : '${ctx}/meta/cmpModels?rnd=' + Math.random(),
	   	data : {
	   			schema:schema,
	   			nodes:selImportModels.join(",")
	   		   },
	   	type : 'POST',
	   	dataType : 'json',
	   	contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){
			var tbHtml = "";
			
			var sqlCnt = $('#dplSqls').empty();
			$.each(data,function(idx, element){
				tbHtml = '';
				if(element.result == "exist"){
					tbHtml += '<tr><td>' + element.name + '</td><td>' + element.msg + '</td><td><a id="a'+element.nodeId+'" onclick="cmpModelDiff(\''+element.nodeId+'\')" href="javascript:;">字段差异</a></td>';
					sqlCnt.append(element.sql + ";\r\n");
					cmpClmCnt.append(tbHtml);
					$("#a" + element.nodeId).jqxTooltip({ content: '点击链接进行比对', position: 'bottom', autoHide: true, name: 'movieTooltip'});
				}else{
					tbHtml += '<tr><td>' + element.name + '</td><td>' + element.msg + '</td><td></td>';
					cmpClmCnt.append(tbHtml);
					
					sqlCnt.append(element.sql + ";\r\n");
				}
			})
			
		}
	});
}


/*执行建表语句*/
function deploy(){
	var xsql = $('#dplSqls').val();
	if(xsql.length <=10){
        layer.alert("脚本脚本为空，请点击比对或者输入建表语句", {icon:0,offset: '100px'});
	}else{
		var encodeSql = jQuery.base64.encode(xsql);
		$.ajax({
			url : '${ctx}/meta/deployModel?rnd=' + Math.random(),
		   	data : {
		   			dsInfo:$('#xdsSchema').val(),
		   			esql:encodeSql
		   		   },
		   	type : 'POST',
		   	dataType : 'json',
		   	contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
				if(data.result == 'succ'){
                    layer.alert("建表成功", {icon:1,offset: '100px'});
				}else{
                    layer.alert(data.msg, {icon:0,offset: '100px'});
				}
			}
		});
	}
}

function delNode(){
    layer.confirm('确认删除吗?', {icon: 3, title:'提示',offset:'100px'}, function(index){
        $.ajax({
            url : '${ctx}/meta/deletePrjNode?rnd=' + Math.random(),
            data : {
                nodeId:'<%=nodeId%>'
            },
            type : 'POST',
            timeout:20000,
            dataType : 'json',
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function(data){
            	refreshNode({data:{type: "refresh", silent: false}}, 'father');
                layer.msg(data.msg,{shade: 0,time:3000,anim: 2,icon: 6,skin:'layer-ext-moon',offset:'100px'});
            }
        });
    })
}

function addChDir(){
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = zTree.getSelectedNodes();
	if (nodes.length<=0) {
		layer.msg('请选择父节点!', {time:3000, icon:1,offset: '100px'});
		return false;
	}

	$('#xcnt0').load("${ctx}/elements/addDir.jsp?nodeId=" + nodeId + "&nodeType=" + nodeType);
}

function saveModel(idx){
	var xhot ;var rxhot;
	for(var x=0;x<tbArray.length; x++){
		if(tbArray[x].xidx == idx){
			xhot = tbArray[x].tb;
			rxhot = tbRelArray[x].tb;
			break;
		}
	}
	var delNodes = [];

	if(xhot != null && xhot.nodeId != null){
		for(var x=0; x<xhot.ordDatas.length; x++){
			if(isNodeDel(xhot.ordDatas[x].id, xhot.getData())){
				delNodes.push(xhot.ordDatas[x].id);
			}
		}
	}

	if(xhot != null){
		if(xhot.nodeId == null){
			layer.prompt({title: "请输入表名",offset:'130px'},function(modelName, index){
				layer.close(index);
				if(modelName != null && modelName != ""){
					$.ajax({
						url : '${ctx}/meta/addPdmModelNodeInfo?rnd=' + Math.random(),
						data : {
							prtNodeId:xhot.parentNode,
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
								refreshNode({data:{type: "refresh", silent: false}});
								xhot.loadData(data.columns);
								////todo
								xhot.nodeId = data.nodeId;
								layer.alert("保存成功！", {icon:1,offset: '100px'});

								$('#navtabs li.active a').text(modelName);

								var ordDatas = [];
								for(var x=0; x<data.columns.length; x++){
									ordDatas.push(data.columns[x]);
								}
								xhot.ordDatas = ordDatas;
							}
							//createEditTables(data, idx);
						}
					});
				}
			});

		}else{
			$.ajax({
				url : '${ctx}/meta/updatePdmModelNodeInfo?rnd=' + Math.random(),
				data : {
					prtNodeId:xhot.nodeId,
					modelInfo:JSON.stringify(xhot.getData()),
					delNodes:delNodes.join(','),
					modelName:$('#cdmName' + idx).val(),
					chName:$('#cdmChName' + idx).val(),
					cmt:$('#cmt' + idx).val(),
					rnodes:JSON.stringify(rxhot.getData())
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
						layer.alert("保存成功！", {icon:1,offset: '100px'});

						var ordDatas = [];
						for(var x=0; x<data.columns.length; x++){
							ordDatas.push(data.columns[x]);
						}

						xhot.ordDatas = ordDatas;
					}
					//createEditTables(data, idx);
				}
			});
		}
	}
}

function createNewPdmModel(nodeId, type) {
	createCdmTab(null, null, type, nodeId);
}
</script>

<div class="page-title" style="height: 32px; border-width:0;">
	<div class="left-btn-ext">
		<ul>
			<li class="seperator"></li>
			<li><a id="saveHref" href="javascript:;" onclick="addChDir()"> <img
				title="添加下级目录" src="${ctx}/resource/css/images/icon/package.png"
				width="16" height="16"> 新增下级目录
			</a></li>
			<li><a id="" href="javascript:;" onclick="delNode()"> <img
					title="删除节点" src="${ctx}/resource/css/images/icon/deldir.png"
					width="16" height="16">删除目录
			</a></li>
			<li><a id="addHref" href="javascript:;" onclick="importModel('<%=nodeId %>')"> <img
					title="反向工程" src="${ctx}/resource/css/images/icon/import.png"
					width="16" height="16"> 反向工程
			</a></li>
			<li><a id="addHref" href="javascript:;" onclick="createNewPdmModel('<%=nodeId %>', 'pdm')"> <img
					title="添加实体" src="${ctx}/resource/css/images/icon/add.png" width="16"
					height="16"> 添加表
			</a></li>
			<li><a id="addHref" href="javascript:;" onclick="delEntity()"> <img
					title="删除实体" src="${ctx}/resource/css/images/icon/del.png"
					width="16" height="16"> 删除表
			</a></li>
			<li><a id="dplHref" href="javascript:;" onclick="deployModel()"> <img
					title="模型部署" src="${ctx}/resource/css/images/icon/deploy.png"
					width="16" height="16"> 模型部署
			</a></li>
			<li><a id="addHref" href="javascript:;" onclick="selectAllLogicModel()"> <img
                    title="全选" src="${ctx}/resource/css/images/icon/format.png"
                    width="16" height="16"> 全选
            </a></li>
		</ul>
	</div>
</div>
<div id="xcnt0" class="cnt" style="margin:10px 4px 4px 16px;float:left;">
<div style="clear: both;">
	<ul id="models">
	</ul>
</div>

<div id="deployWindow" style="display: none">
	<div id="customWindowHeader">
		<span id="captureContainer" style="float: left">模型部署 </span>
	</div>
	<div id="customWindowContent">
		<div style="margin: 5px 0px 5px 5px;">
			<div class="bdx">
			选择数据源:<select onchange="queryDplDsSchemas()" name="xdatasource" id="xdatasource" ></select>
			&nbsp;&nbsp;选择模式:<select name="xdsSchema" id="xdsSchema" ></select>
			<input class="jqx-rc-all jqx-button jqx-widget jqx-fill-state-normal" style="width: 65px;" onclick="cmpModels()" type="button" value="比对" style="margin-bottom: 5px;"
					id="xcheck" />
			<input class="jqx-rc-all jqx-button jqx-widget jqx-fill-state-normal" style="width: 65px;" onclick="exportDDL()" type="button" value="脚本导出" style="margin-bottom: 5px;"
					id="xcheck" />
			</div>
			</div>
			<div id="phyModels" style="height:500px; overflow-y:hide;">
				<table width="100%">
				<tr>
					<td valign="top" style="width:60%;">
						<table class="form-normal" id="cmptableclm" style="width: 100%;">
							<thead>
								<tr>
									<th width="100">表名</th>
									<th width="60">比对结果</th>
									<th width="100">模型比对</th>
								</tr>
							</thead>
							<tbody id="cmpClmCnt">
							</tbody>
						</table>
					</td>
					<td>
						<textarea placeholder="建表脚本" id="dplSqls" cols="35" rows="23"></textarea>
					</td>
				</tr>
			</table>
			</div>
			<div style="float:right;padding-right:12px;">
				<input class="jqx-rc-all jqx-button jqx-widget jqx-fill-state-normal" style="width: 65px;" onclick="deploy()" type="button" value="执行建表" style="margin-bottom: 5px;"
					id="xok" />&nbsp;<input type="button"
					value="取消" id="xcancel" />
			</div>
		</div>
	</div>
</div>

<%--反向工程--%>
<div id="importWindow" style="display: none">
	<div id="customWindowHeader">
		<span id="captureContainer" style="float: left">从数据库导入模型 </span>
	</div>
	<div id="customWindowContent" style="overflow: hidden">
		<div style="margin: 5px 0px 5px 5px;">
			<div class="bdx">
			选择数据源:<select onchange="queryDsSchemas()" name="datasource" id="datasource" ></select>
			&nbsp;&nbsp;选择模式:<select name="dsSchema" id="dsSchema" ></select>
			表规则:<input style="width:140px;" type="text" id="tableRule" name="tableRule" />&nbsp;&nbsp;<img id="xloading" style="display:none;" src='${ctx}/resource/js/ztree/css/zTreeStyle/img/loading.gif' />
                <input onclick="filterPhyModels()" type="button" value="过滤" style="margin-bottom: 5px;" id="queryTables" />
                <input onclick="selectAllModels();" type="button" value="全选" style="margin-bottom:5px;"/>
			</div>
			<ul id="phyModels" style="height:500px; overflow-y:scroll;">
			</ul>
			<div>
				<input class="jqx-rc-all jqx-button jqx-widget jqx-fill-state-normal" style="width: 65px;" onclick="return importToCdm()" type="button" value="导入" style="margin-bottom: 5px;"
					id="ok" />&nbsp;<input type="button"
					value="取消" id="cancel" />
			</div>
		</div>
	</div>
</div>