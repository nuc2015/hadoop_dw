<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <script src="${ctx}/resource/js/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>


    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/font-awesome.min.css"/>

    <script src="${ctx}/resource/js/ztree/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
    <link href="${ctx}/resource/js/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>

    <%--<link href="${ctx}/resource/js/ztree/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css"/>--%>

    <script src="${ctx}/resource/js/i18n/jquery.i18n.properties.js" type="text/javascript"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxcore.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxdata.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxbuttons.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxscrollbar.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxdatatable.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxcheckbox.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxlistbox.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxdropdownlist.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxtabs.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxwindow.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxpanel.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxdata.export.js"></script>
    <script src="${ctx}/resource/js/jqwidgets/jqxtooltip.js"></script>

    <link href="${ctx}/resource/js/jqwidgets/styles/jqx.base.css" rel="stylesheet" type="text/css"/>

    <script src="${ctx}/resource/js/editor/xheditor.js" type="text/javascript"></script>
    <script src="${ctx}/resource/js/editor/src/core.js" type="text/javascript"></script>
    <script src="${ctx}/resource/js/editor/src/lang.js" type="text/javascript"></script>
    <script src="${ctx}/resource/js/editor/src/main.js" type="text/javascript"></script>
    <script src="${ctx}/resource/js/editor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <link href="${ctx}/resource/js/editor/xheditor_skin/default/ext.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/resource/js/encode/jquery.xbase64.js" type="text/javascript"></script>
    <!-- <script src="${ctx}/resource/js/handsontable/dist/handsontable.full0.12.2.js"></script>
	<link rel="stylesheet" media="screen" href="${ctx}/resource/js/handsontable/dist/handsontable.full.css"> -->

    <script src="${ctx}/resource/js/handsontable/nv/pikaday/pikaday.js"></script>
    <script src="${ctx}/resource/js/handsontable/nv/pikaday/moment.js"></script>
    <link rel="stylesheet" media="screen" href="${ctx}/resource/js/handsontable/nv/pikaday/pikaday.css">
    <script src="${ctx}/resource/js/handsontable/nv/handsontable.js"></script>
    <link rel="stylesheet" media="screen" href="${ctx}/resource/js/handsontable/nv/handsontable.css">

    <script src="${ctx}/resource/js/handsontable/plugins/ms/chosen.jquery.js"></script>
    <script src="${ctx}/resource/js/handsontable/plugins/ms/handsontable-chosen-editor.js"></script>

    <link rel="stylesheet" media="screen" href="${ctx}/resource/js/handsontable/plugins/ms/chosen.css">

    <link rel="stylesheet" href="${ctx}/resource/layui/css/layui.css" media="all">
    <script src="${ctx}/resource/layui/layui.js"></script>

    <style>
        button,.button{
            background:#81d515;
            border:0 none;
            font-size:16px !important;
            display: inline-block;

            display: inline;/*IE6、7下inline-block*/
            overflow: hidden;
            padding: 0 10px;

            text-align: center;
            vertical-align: middle;
            cursor: pointer;

            height:24px !important;
            line-height:28px !important;

            color:#fff;
            font-family: "microsoft yahei" !important;
            margin-right:5px;
        }
        .ztree li span{
            line-height:26px !important;
            margin-left:5px !important;
        }
        .ztree li a.curSelectedNode{
            height:26px !important;
        }
        .ztree li span.button.ico_docu{
            vertical-align:middle !important;
        }
        .zTreeDemoBackground{
            overflow-y: hidden !important;
        }
        #line{
            position:absolute;top:0;left:280px;height:100%;width:4px;overflow:hidden;cursor:w-resize;
        }
    </style>

    <script>
        var idx = 1;
        var tabCntDefWidth = 1000;
        var xdtArray = [];
        var nodeType;
        var nodeId;
        var tbArray = [];
        var tbRelArray = [];

        var mainCntMdl;
        var setting = {
            view: {
                selectedMulti: false
            },
            async: {
                enable: true,
                url: "${ctx}/meta/queryTreeNode?rnd=" + Math.random(),
                autoParam: ["id", "name=n", "level=lv", "nodeType", "nodeFrom"],
                otherParam: {"otherParam": "zTreeAsyncTest"},
                dataFilter: filter
            },
            callback: {
                beforeClick: beforeClick,
                beforeAsync: beforeAsync,
                onAsyncError: onAsyncError,
                onAsyncSuccess: onAsyncSuccess
            }
        };
        var layer;
        layui.use('layer', function(){
            layer = layui.layer;
        });

        function filter(treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            for (var i = 0, l = childNodes.length; i < l; i++) {
                if (childNodes[i].name != null) {
                    childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
                }
            }
            return childNodes;
        }

        function getPlainTextFromHtml(htmlText) {
            htmlText = htmlText.replace(/(\r)/g, "");
            htmlText = htmlText.replace(/<\/?[^>]*>/g, " ");
            htmlText = htmlText.replace("&nbsp;", " ");
            return htmlText;
        }

        function createNewQueryTab() {
            if (nodeType == "relation.Schema" || nodeType == "phy.DataSource" || nodeType == "relation.Table" || nodeType == "relation.View") {
            } else {
                layer.alert("请选择要查询的库！", {icon:0,offset: '130px'});
                return false;
            }

            var tableInfoArray = nodeId.split(".");
            //var tabName;
            //var retFlag = false;
            var liWidth = 0;
            $('#navtabs li').each(function () {
                liWidth += $(this).width();
            });
            var tbCntWidth = $("#navtabs").width();

            if (tbCntWidth < (liWidth + 230)) {
                $("#navtabs").width((liWidth + 230) + "px");
            }

            $('.nav-tabs li.active').removeClass('active');
            $('.tab-content div.active').removeClass('active');
            $('.nav-tabs').append('<li class="active" role="presentation"><a class="active taba" href="#home' + idx + '" aria-controls="home' + idx + '" alt="数据查询' + idx + '" role="tab" data-toggle="tab" aria-expanded="true">数据查询' + idx + '</a><i class="xclose"></i></li>');

            if (tableInfoArray.length == 3) {
                $('.tab-content').append('<div id="home' + idx + '" class="tab-pane clearfix active" role="tabpanel"><div class="adhocResultCnt" style="overflow-x:auto;overflow-y:hidden;"><textarea id="sqlEditor" name="sqlEditor" class="veditor xheditor {tools:\'\',skin:\'default\'}" rows="10" cols="80" style="width: 100%">select * from ' + tableInfoArray[2] + '</textarea><div id="queryResultDiv' + idx + '" style="height:420px;overflow-x:scroll;overflow-y:scroll;"></div><div style="padding:4px;"><p id="useTimes' + idx + '"></p><p id="emsg' + idx + '">默认展示50条记录，可以设置查询条件缩小查询范围</p></div></div></div>');
            } else {
                $('.tab-content').append('<div id="home' + idx + '" class="tab-pane clearfix active" role="tabpanel"><div class="adhocResultCnt" style="overflow-x:auto;overflow-y:hidden;"><textarea id="sqlEditor" name="sqlEditor" class="veditor xheditor {tools:\'\',skin:\'default\'}" rows="10" cols="80" style="width: 100%"></textarea><div id="queryResultDiv' + idx + '" style="height:420px;overflow-x:scroll;overflow-y:scroll;"></div><div style="padding:4px;"><p id="useTimes' + idx + '"><p><p id="emsg' + idx + '">默认展示50条记录，可以设置查询条件缩小查询范围</p></div></div></div>');
            }

            //$("#home" + idx).load("${ctx}/meta/elements/metaNodeTable.jsp?THEME_PATH=${THEME_PATH}&nodeId="+treeNode.id+"&nodeType="+nodeType+"&nodeFrom="+nodeFrom+"&nodeVersion="+nodeVersion+"&idx=" + idx);

            var editor;
            if (tabCntDefWidth < (liWidth + 100)) {
                $("#navtabs").css({"left": "-" + ((liWidth + 230) - tabCntDefWidth) + "px"});
                $('.jqx-widget-headerx').show();
            }

            xidx = idx;
            var extPlugin = {
                run: {
                    idx: idx,
                    c: 'run',
                    t: '<spring:message code="run.script" text="SQL执行" />',
                    s: 'ctrl+r',
                    e: function () {
                        this.xidx = idx;
                        var sql = getPlainTextFromHtml(this.getSource());
                        var encodeSql = jQuery.base64.encode(sql);

                        if (sql.toLowerCase().indexOf("select ") >= 0 || sql.toLowerCase().indexOf("sel ") >= 0 || sql.toLowerCase().indexOf("scan ") >= 0) {
                            querySqlData(encodeSql, this.getIdx(), this);
                        } else if (sql.toLowerCase().indexOf("update ") >= 0 || sql.toLowerCase().indexOf("insert ") >= 0
                            || sql.toLowerCase().indexOf("delete ") >= 0 || sql.toLowerCase().indexOf("deleteall ") >= 0
                            || sql.toLowerCase().indexOf("put ") >= 0) {
                            executeSqlData(encodeSql, this);
                        }
                    }
                },
                saveq: {
                    idx: idx, c: 'saveq', t: '<spring:message code="save.script" text="删除" />', s: '', e: function () {
                        if (this.update == "0") {
                            layer.alert("多表查询不允许删除操作！", {icon:0,offset: '130px'});
                            return false;
                        }

                        var selection = this.hot.getSelected();

                        if (selection == null) {
                            layer.alert("请选择要删除的行", {icon:0,offset: '130px'});
                            return false;
                        } else if (selection[0] >= this.ordDatas.length) {
                            this.hot.alter('remove_row', selection[0], 1);
                            var loop = this.hot.editCells.length;
                            for (var y = 0; y < loop; y++) {
                                if (this.hot.editCells[y][0] == selection[0]) {
                                    this.hot.editCells.splice(y, 1);
                                    y = y - 1;
                                    loop = loop - 1;
                                }
                            }

                            return false;
                        }

                        var delSql = createDelSql(this.metas, selection[0], this);
                        if (confirm("确认执行删除语句吗：\r\n" + delSql)) {
                            if (selection[0] < this.ordDatas.length) {
                                //executeDelData(encodeSql, this);

                                delSql = jQuery.base64.encode(delSql);
                                executeDelData(delSql, this, selection[0]);
                            }
                        }
                    }
                },
                etxt: {
                    idx: idx,
                    c: 'etxt',
                    t: '<spring:message code="save.script" text="导出为文本" />',
                    s: '',
                    e: function () {
                        var sql = getPlainTextFromHtml(this.getSource());

                        if (sql != null && (sql.toLowerCase().indexOf("sel ") >= 0 || sql.toLowerCase().indexOf("select ") >= 0 || sql.toLowerCase().indexOf("scan ") >= 0)) {
                            var encodeSql = jQuery.base64.encode(sql);
                            var schema;
                            var dsId;
                            var tableName;
                            if (this.nodeId.indexOf(".") > 0) {
                                var tableInfoArray = this.nodeId.split(".");
                                if (tableInfoArray.length == 2) {
                                    dsId = tableInfoArray[0];
                                    schema = tableInfoArray[1];
                                } else if (tableInfoArray.length == 3) {
                                    dsId = tableInfoArray[0];
                                    schema = tableInfoArray[1];
                                    tableName = tableInfoArray[2];
                                }
                            } else {
                                schema = "";
                                dsId = this.nodeId;
                            }

                            if (tableName == null || tableName == "") {
                                tableName = "临时导出" + getNowFormatDate();
                            }

                            exportSqlData(encodeSql, "txt", dsId, tableName);
                        } else {
                            layer.alert("更新、删除、插入语句无法导出", {icon:0,offset: '130px'});
                        }
                    }
                },
                exls: {
                    idx: idx,
                    c: 'exls',
                    t: '<spring:message code="save.script" text="导出为xls" />',
                    s: '',
                    e: function () {
                        var sql = getPlainTextFromHtml(this.getSource());

                        if (sql != null && (sql.toLowerCase().indexOf("sel ") >= 0 || sql.toLowerCase().indexOf("select ") >= 0 || sql.toLowerCase().indexOf("scan ") >= 0 )) {
                            var encodeSql = jQuery.base64.encode(sql);
                            var schema;
                            var dsId;
                            var tableName;
                            if (this.nodeId.indexOf(".") > 0) {
                                var tableInfoArray = this.nodeId.split(".");
                                if (tableInfoArray.length == 2) {
                                    dsId = tableInfoArray[0];
                                    schema = tableInfoArray[1];
                                } else if (tableInfoArray.length == 3) {
                                    dsId = tableInfoArray[0];
                                    schema = tableInfoArray[1];
                                    tableName = tableInfoArray[2];
                                }
                            } else {
                                schema = "";
                                dsId = this.nodeId;
                            }

                            if (tableName == null || tableName == "") {
                                tableName = "临时导出" + getNowFormatDate();
                            }

                            exportSqlData(encodeSql, "xls", dsId, tableName);
                        } else {
                            layer.alert("更新、删除、插入语句无法导出", {icon:0,offset: '130px'});
                        }
                    }
                },
                ecsv: {
                    idx: idx,
                    c: 'ecsv',
                    t: '<spring:message code="save.script" text="导出为csv" />',
                    s: '',
                    e: function () {
                        var sql = getPlainTextFromHtml(this.getSource());

                        if (sql != null && (sql.toLowerCase().indexOf("sel ") >= 0 || sql.toLowerCase().indexOf("select ") >= 0 || sql.toLowerCase().indexOf("scan ") >= 0 )) {
                            var encodeSql = jQuery.base64.encode(sql);
                            var schema;
                            var dsId;
                            var tableName;
                            if (this.nodeId.indexOf(".") > 0) {
                                var tableInfoArray = this.nodeId.split(".");
                                if (tableInfoArray.length == 2) {
                                    dsId = tableInfoArray[0];
                                    schema = tableInfoArray[1];
                                } else if (tableInfoArray.length == 3) {
                                    dsId = tableInfoArray[0];
                                    schema = tableInfoArray[1];
                                    tableName = tableInfoArray[2];
                                }
                            } else {
                                schema = "";
                                dsId = this.nodeId;
                            }

                            if (tableName == null || tableName == "") {
                                tableName = "临时导出" + getNowFormatDate();
                            }

                            exportSqlData(encodeSql, "csv", dsId, tableName);
                        } else {
                            layer.alert("更新、删除、插入语句无法导出", {icon:0,offset: '130px'});
                        }
                    }
                }
            };

            //editor = $('#home'+idx+' .veditor').xheditor({plugins:extPlugin,tools:'run,Cut,Copy,Paste,saveq'});
            editor = $('#home' + idx + ' .veditor').xheditor({
                height: 173,
                plugins: extPlugin,
                tools: 'run,saveq,etxt,exls,ecsv'
            });
            $("#queryResultDiv" + idx).css("height", 380);

            editor.nodeId = nodeId + "";

            setTimeout(function () {
                $(".xheIframeArea iframe").height(173);
            }, 500);

            if (tableInfoArray.length == 3) {
                var initSql = getPlainTextFromHtml(editor.getSource());
                var initEncodeSql = jQuery.base64.encode(initSql);
                querySqlData(initEncodeSql, idx, editor);
            }

            //queryAdhocData(tableInfoArray, "home"+idx);

            initTabs();
            idx++;
        }

        function executeSqlData(sql, tbCnt) {
            if (sql == null || sql.length < 5) {
                layer.alert("请输入语", {icon:0,offset: '130px'});
                return false;
            }

            var schema;
            var dsId;
            var tableName;
            if (tbCnt.nodeId.indexOf(".") > 0) {
                var tableInfoArray = tbCnt.nodeId.split(".");
                if (tableInfoArray.length == 2) {
                    dsId = tableInfoArray[0];
                    schema = tableInfoArray[1];
                } else if (tableInfoArray.length == 3) {
                    dsId = tableInfoArray[0];
                    schema = tableInfoArray[1];
                    tableName = tableInfoArray[2];
                }
            } else {
                schema = "";
                dsId = tbCnt.nodeId;
            }

            $("#useTimes" + xidx).html("开始执行<img src='${ctx}/resource/js/ztree/css/zTreeStyle/img/loading.gif' />");

            var hiveDataCnt = [];

            if (tbCnt.dbType == "HIVE2KBS" || tbCnt.dbType == "HIVE2" || tbCnt.dbType == "HIVE") {
                hiveDataCnt = JSON.stringify(tbCnt.hot.getData());
            }

            $.ajax({
                url: '${ctx}/meta/excuteSql?rnd=' + Math.random(),
                data: {
                    schema: schema,
                    dsId: dsId,
                    tableName: tableName,
                    hiveDataCnt: hiveDataCnt,
                    queryql: sql//default encode
                },
                type: 'POST',
                timeout: 20000,
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (data) {
                    if (data.result == "succ") {
                        tbCnt.ordDatas = tbCnt.ordDatas;
                        //$("#useTimes" + xidx).html("执行成功,用时:" + data.userTimes+"秒");

                        setTimeout(function () {
                            $("#useTimes" + xidx).html(data.msg + "</br>执行成功,用时:" + data.userTimes + "秒");
                        }, 500);

                        if (tbCnt.insertDatas != null && tbCnt.insertDatas.length > 0) {
                            for (var x = 0; x < tbCnt.insertDatas.length; x++) {
                                tbCnt.ordDatas.push(tbCnt.insertDatas[x]);
                            }
                        }

                        tbCnt.hot.editCells = [];
                    } else {
                        $("#useTimes" + xidx).html("执行失败");
                        $("#emsg" + xidx).html(data.msg);
                    }
                }
            });
        }

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
                url + "'>"
            $.each(data, function (idx, element) {
                iframe_html += "<input type='hidden' name='" + element.name + "' value='" + element.value + "'>";
            });

            iframe_html += "</form></body></html>";

            iframe_doc.open();
            iframe_doc.write(iframe_html);
            $(iframe_doc).find('form').submit();
        }

        function exportSqlData(sql, type, dsId, tableName) {
            var conditionFormParas = [];
            conditionFormParas.push({name: 'tableName', value: tableName});
            conditionFormParas.push({name: 'dsId', value: dsId});
            conditionFormParas.push({name: 'exportType', value: type});
            conditionFormParas.push({name: 'expSql', value: sql});
            ajax_download('${ctx}/meta/exportSqlData?rnd=' + Math.random(), conditionFormParas);
        }


        function querySqlData(sql, xidx, tableCnt) {
            $("#queryResultDiv" + xidx).css("width", "100%");

            if (sql == null || sql.length < 5) {
                layer.alert("请输入查询语句", {icon:0,offset: '130px'});
                return false;
            }

            var schema;
            var dsId;
            var tableName;
            if (tableCnt.nodeId.indexOf(".") > 0) {
                var tableInfoArray = tableCnt.nodeId.split(".");
                if (tableInfoArray.length == 2) {
                    dsId = tableInfoArray[0];
                    schema = tableInfoArray[1];
                } else if (tableInfoArray.length == 3) {
                    dsId = tableInfoArray[0];
                    schema = tableInfoArray[1];
                    tableName = tableInfoArray[2];
                }
            } else {
                schema = "";
                dsId = tableCnt.nodeId;
            }

            //var editCells = [];
            $("#useTimes" + xidx).html("开始执行<img src='${ctx}/resource/js/ztree/css/zTreeStyle/img/loading.gif' />");

            $.ajax({
                url: '${ctx}/meta/excuteSqlQuery?rnd=' + Math.random(),
                data: {
                    schema: schema,
                    dsId: dsId,
                    tableName: tableName,
                    queryql: sql//default encode
                },
                type: 'GET',
                timeout: 100000,
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (data) {
                    if (data.dbType == "HbaseKBS" && tableName != null) {
                        //tableCnt.setSource("scan '" + schema + ":" + tableName + "'");

                        tableCnt.setSource(data.querySql);
                    }

                    if (data.result == "fail") {
                        setTimeout(function () {
                            $("#useTimes" + xidx).html("执行异常：" + data.expMsg);
                        }, 500);

                        return false;
                    }

                    setTimeout(function () {
                        $("#useTimes" + xidx).html("用时:" + data.userTimes + "秒，返回[" + (data.datas.length - 1) + "]条记录");
                    }, 500);

                    if (data.header == null || data.header.length <= 0) {
                        return false;
                    }

                    $("#queryResultDiv" + xidx).empty();

                    var container = document.getElementById('queryResultDiv' + xidx);

                    //var ordDatas = data.datas.splice(0);

                    var ordDatas = [];
                    for (var x = 0; x < data.datas.length; x++) {
                        ordDatas.push(data.datas[x].slice());
                    }
                    if (data.datas.length == 0) {
                        var defValue = new Array(data.header.length);
                        data.datas.push(defValue);
                    }

                    tableCnt.update = data.update;
                    if (data.update == "1") {
                        if (tableName != null) {
                            tableCnt.tableName = tableName;
                        } else {
                            tableCnt.tableName = data.tableName;
                        }
                    }

                    tableCnt.ordDatas = ordDatas;
                    tableCnt.schema = schema;
                    tableCnt.dsId = dsId;

                    tableCnt.dbType = data.dbType;

                    var tableClmFmts = [];

                    if (tableCnt.update == "1") {
                        for (var u = 0; u < data.metas.length; u++) {
                            if (data.metas[u].type.toLowerCase() == "date") {

                                tableClmFmts.push({type: 'date', dateFormat: 'YYYY-MM-DD'});
                                //tableClmFmts.push({type:Handsontable.My97DateCell});

                            } else if (data.metas[u].type.toLowerCase() == "datetime" || data.metas[u].type.toLowerCase() == "timestamp") {
                                //tableClmFmts.push({type:Handsontable.My97DateCell});
                                tableClmFmts.push({type: 'date', dateFormat: 'YYYY-MM-DD hh:mm:ss'});
                            } else {
                                tableClmFmts.push({});
                                //tableClmFmts[u] = {};
                            }
                        }

                        var hot = new Handsontable(container, {
                            data: data.datas,
                            colHeaders: data.header,
                            rowHeaders: true,
                            contextMenu: false,
                            minSpareRows: 1,
                            fillHandle: false,//拖动不自动填充值
                            columnSorting: true,
                            columns: tableClmFmts,
                            height: $(document).height() - 220
                        });
                    } else {
                        for (var u = 0; u < data.metas.length; u++) {
                            if (data.metas[u].type.toLowerCase() == "date") {

                                tableClmFmts.push({readOnly: true, type: 'date', dateFormat: 'YYYY-MM-DD'});
                                //tableClmFmts.push({type:Handsontable.My97DateCell});

                            } else if (data.metas[u].type.toLowerCase() == "datetime" || data.metas[u].type.toLowerCase() == "timestamp") {
                                //tableClmFmts.push({type:Handsontable.My97DateCell});
                                tableClmFmts.push({readOnly: true, type: 'date', dateFormat: 'YYYY-MM-DD hh:mm:ss'});
                            } else {
                                tableClmFmts.push({readOnly: true});
                                //tableClmFmts[u] = {};
                            }
                        }

                        var hot = new Handsontable(container, {
                            data: data.datas,
                            colHeaders: data.header,
                            rowHeaders: true,
                            contextMenu: false,
                            minSpareRows: 0,
                            fillHandle: false,//拖动不自动填充值
                            columnSorting: true,
                            columns: tableClmFmts,
                            height: $(document).height() - 220
                        });
                    }

                    hot.editCells = [];
                    tableCnt.crtData = data.datas;
                    tableCnt.metas = data.metas;

                    var changedRow, changedCol;
                    hot.updateSettings({
                        beforeKeyDown: function (e) {
                            var selection = hot.getSelected();
                            if (selection) {
                                changedRow = selection[0];
                                changedCol = selection[1];
                            }
                        },
                        afterChange: function (e) {
                            if (changedRow >= 0) {
                                if (!changedRowExist(hot.editCells, changedRow, changedCol)) {
                                    hot.editCells.push([changedRow, changedCol]);
                                }

                                var sqlArray = createDataEditSql(data.metas, tableCnt.ordDatas, hot.editCells, hot, schema, tableCnt);

                                var sqlString = "";
                                for (var x = 0; x < sqlArray.length; x++) {
                                    sqlString += sqlArray[x] + ";<br/>";
                                }

                                tableCnt.setSource(sqlString);
                            } else {
                                var selection = hot.getSelected();

                                if (selection) {
                                    changedRow = selection[0];
                                    changedCol = selection[1];

                                    if (!changedRowExist(hot.editCells, changedRow, changedCol)) {
                                        hot.editCells.push([changedRow, changedCol]);
                                    }

                                    var sqlArray = createDataEditSql(data.metas, tableCnt.ordDatas, hot.editCells, hot, schema, tableCnt);

                                    var sqlString = "";
                                    for (var x = 0; x < sqlArray.length; x++) {
                                        sqlString += sqlArray[x] + ";<br/>";
                                    }

                                    tableCnt.setSource(sqlString);
                                }
                            }

                            changedRow = -1;
                            changedCol = -1;
                        }
                    });

                    var table = document.querySelector('#queryResultDiv' + xidx + ' table');
                    //Handsontable.Dom.addClass(table, 'table');
                    //Handsontable.Dom.addClass(table,'table-striped');
                    //Handsontable.Dom.addClass(table,'table-hover');
                    tableCnt.hot = hot;
                },
                error: function (xhr, textStatus) {
                    //exceptionHandler(xhr,textStatus);
                }
            });
        }

        function createDataEditSql(metas, ordDatas, editCells, hot, schema, tableCnt) {
            var hotValue, ordValue;
            var rowArray = [];
            var updObj = {};
            var insertArray = [];
            var sqlArray = [];
            for (var i = 0; i < editCells.length; i++) {
                hotValue = hot.getDataAtCell(editCells[i][0], editCells[i][1]);

                if (editCells[i][0] > (ordDatas.length - 1)) {
                    //add new data should insert into ...
                    //sqlArray.push("inser into ...");
                    if (!containObj(insertArray, editCells[i][0])) {
                        insertArray.push(editCells[i][0]);
                    }
                } else {
                    ordValue = ordDatas[editCells[i][0]][editCells[i][1]];
                    //update...
                    if (hotValue == ordValue || (hotValue == null && ordValue == "") || (hotValue == "" && ordValue == null)) {
                        //not dealwith
                    } else {
                        if (!containObj(rowArray, editCells[i][0])) {
                            if (updObj["p" + editCells[i][0]] == null) {
                                updObj["p" + editCells[i][0]] = [];
                            }
                            updObj["p" + editCells[i][0]].push(editCells[i]);
                        }

                    }
                }
            }

            var updateWhereInfo = ' 1=1 ';
            var pkMetas = [];
            var hasPk = false;
            for (var x = 0; x < metas.length; x++) {
                if (metas[x].pk) {
                    //pkMetas["p"+x] = metas[x];

                    metas[x].pidx = x;
                    pkMetas.push(metas[x]);
                    hasPk = true;
                }
            }


            //for(var metaProp in pkMetas){
            //updateWhereInfo += " and " + pkMetas[metaProp].name + "=" getQueryPara(pkMetas[metaProp], );
            //	hasPk = true;
            //}

            if (hasPk || (tableCnt.dbType == "HbaseKBS" || tableCnt.dbType == "Hbase")) {
                if ((tableCnt.dbType == "HbaseKBS" || tableCnt.dbType == "Hbase")) {
                    var updateSql = "";

                    for (var prop in updObj) {
                        var updateClmArray = updObj[prop];

                        for (var x = 0; x < updateClmArray.length; x++) {
                            updateSql = "";
                            updateSql += "put '" + schema + ":" + tableCnt.tableName + "','" + hot.getDataAtCell(updateClmArray[x][0], 0) + "','" + metas[updateClmArray[x][1]].layer + ":" + metas[updateClmArray[x][1]].name + "'," + getQueryPara(metas[updateClmArray[x][1]], hot.getDataAtCell(updateClmArray[x][0], updateClmArray[x][1]), tableCnt) + '\r\n';
                            sqlArray.push(updateSql);
                        }
                    }
                } else {
                    for (var prop in updObj) {
                        var updateClmArray = updObj[prop];
                        var updateSql = "update " + schema + "." + tableCnt.tableName + " set ";

                        for (var x = 0; x < updateClmArray.length; x++) {
                            if (x == (updateClmArray.length - 1)) {
                                updateSql += metas[updateClmArray[x][1]].name + "=" + getQueryPara(metas[updateClmArray[x][1]], hot.getDataAtCell(updateClmArray[x][0], updateClmArray[x][1]), tableCnt);
                            } else {
                                updateSql += metas[updateClmArray[x][1]].name + "=" + getQueryPara(metas[updateClmArray[x][1]], hot.getDataAtCell(updateClmArray[x][0], updateClmArray[x][1]), tableCnt) + ",";
                            }

                            //for(var metaProp in pkMetas){
                            //	if(metaProp == updateClmArray[x][1]){
                            //updateWhereInfo += " and " + pkMetas[metaProp].name + "=" getQueryPara(pkMetas[metaProp], hot.getDataAtCell(updateClmArray[0], updateClmArray[1]));
                            //	}
                            //}
                        }

                        updateSql += " where 1=1 ";

                        var xrow = parseInt(prop.substring(1));
                        var xcol;
                        var updateRowData = hot.getDataAtRow(xrow);

                        if (updateRowData != null && updateRowData.length > 0) {
                            for (var p = 0; p < pkMetas.length; p++) {
                                updateSql += " and " + pkMetas[p].name + "=" + getQueryPara(pkMetas[p], ordDatas[xrow][pkMetas[p].pidx], tableCnt);
                            }
                        }

                        sqlArray.push(updateSql);
                    }
                }
            } else {
                for (var prop in updObj) {
                    var updateClmArray = updObj[prop];
                    var updateSql = "update " + schema + "." + tableCnt.tableName + " set ";

                    for (var x = 0; x < updateClmArray.length; x++) {
                        if (x == (updateClmArray.length - 1)) {
                            updateSql += metas[updateClmArray[x][1]].name + "=" + getQueryPara(metas[updateClmArray[x][1]], hot.getDataAtCell(updateClmArray[x][0], updateClmArray[x][1]), tableCnt);
                        } else {
                            updateSql += metas[updateClmArray[x][1]].name + "=" + getQueryPara(metas[updateClmArray[x][1]], hot.getDataAtCell(updateClmArray[x][0], updateClmArray[x][1]), tableCnt) + ",";
                        }

                        //for(var metaProp in pkMetas){
                        //	if(metaProp == updateClmArray[x][1]){
                        //updateWhereInfo += " and " + pkMetas[metaProp].name + "=" getQueryPara(pkMetas[metaProp], hot.getDataAtCell(updateClmArray[0], updateClmArray[1]));
                        //	}
                        //}
                    }

                    var xrow = parseInt(prop.substring(1));
                    if (tableCnt.dbType == "Mysql" || tableCnt.dbType == "HIVE2KBS" || tableCnt.dbType == "HIVE" || tableCnt.dbType == "HIVE2") {
                        updateSql += " where 1=1 ";
                        var xcol;
                        var updateRowData = hot.getDataAtRow(xrow);
                        if (updateRowData != null && updateRowData.length > 0) {
                            for (var x = 0; x < updateRowData.length; x++) {
                                if (ordDatas[xrow][x] == null || ordDatas[xrow][x] == "null") {
                                    updateSql += " and " + getCndQueryPara(metas[x], ordDatas[xrow][x], tableCnt);
                                } else {
                                    updateSql += " and " + getCndQueryPara(metas[x], ordDatas[xrow][x], tableCnt);
                                }
                            }
                            //for(var metaProp in pkMetas){
                            //	xcol = parseInt(metaProp.substring(1));
                            //	updateSql += metas[mrow].name = getQueryPara(metas[mrow], hot.getDataAtCell(xrow, xcol));
                            //}
                        }

                        updateSql += " limit 1";
                    } else if (tableCnt.dbType == "Oracle") {
                        var rd = ordDatas[xrow];
                        updateSql += " where rowid='" + rd[rd.length - 1] + "'";
                    }

                    sqlArray.push(updateSql);
                }
            }

            var insertClms = "("
            for (var x = 0; x < metas.length; x++) {
                if (x == (metas.length - 1)) {
                    insertClms += metas[x].name;
                } else {
                    insertClms += metas[x].name + ",";
                }
            }
            insertClms += ")";

            tableCnt.insertDatas = [];

            if (tableCnt.dbType == "HbaseKBS" || tableCnt.dbType == "Hbase") {
                var inserSql = "";
                for (var x = 0; x < insertArray.length; x++) {
                    var insertRowData = hot.getDataAtRow(insertArray[x]);
                    for (var y = 1; y < insertRowData.length; y++) {
                        if (getQueryPara(metas[y], insertRowData[y], tableCnt) != null) {
                            var inserSql = "";
                            inserSql += "put '" + schema + ":" + tableCnt.tableName + "','" + insertRowData[0] + "','" + metas[y].layer + ":" + metas[y].name + "'," + getQueryPara(metas[y], insertRowData[y], tableCnt) + '\r\n';
                            sqlArray.push(inserSql);
                        }
                    }
                }
            } else {
                for (var x = 0; x < insertArray.length; x++) {
                    var inserSql = "";

                    if (tableCnt.dbType == "HIVE2KBS" || tableCnt.dbType == "HIVE" || tableCnt.dbType == "HIVE2") {
                        inserSql = "insert into " + schema + "." + tableCnt.tableName + " values (";
                    } else {
                        inserSql = "insert into " + schema + "." + tableCnt.tableName + insertClms + " values (";
                    }

                    var insertRowData = hot.getDataAtRow(insertArray[x]);

                    //alert(hot.getDataAtCell(0,0));

                    for (var y = 0; y < insertRowData.length; y++) {
                        if (y == (insertRowData.length - 1)) {
                            inserSql += getQueryPara(metas[y], insertRowData[y], tableCnt);
                        } else {
                            inserSql += getQueryPara(metas[y], insertRowData[y], tableCnt) + ",";
                        }
                    }
                    inserSql += ")";

                    tableCnt.insertDatas.push(insertRowData);

                    sqlArray.push(inserSql);
                }
            }

            return sqlArray;
        }

        function getCndQueryPara(meta, paraValue, tableCnt) {
            if (paraValue == null || paraValue == "null") {
                return meta.name + " is null";
            }

            if (meta.type.toLowerCase().indexOf("varchar") >= 0 || meta.type.toLowerCase().indexOf("char") >= 0 || meta.type.toLowerCase().indexOf("string") >= 0) {
                return meta.name + "='" + paraValue + "'";
            } else if (meta.type.toLowerCase().indexOf("int") >= 0 || meta.type.toLowerCase().indexOf("double") >= 0 || meta.type.toLowerCase().indexOf("float") >= 0
                || meta.type.toLowerCase().indexOf("decimal") >= 0 || meta.type.toLowerCase().indexOf("numeric") >= 0) {
                return meta.name + "=" + paraValue;
            } else if (meta.type.toLowerCase().indexOf("date") >= 0 || meta.type.toLowerCase().indexOf("time") >= 0) {
                var timeFmt = "";
                if (tableCnt.dbType.toLowerCase() == "mysql" || tableCnt.dbType.toLowerCase() == "gbase") {
                    if (meta.type.toLowerCase() == "datetime" || meta.type.toLowerCase() == "timestamp") {
                        timeFmt = '%Y-%m-%d %H:%i:%s';
                    }else{
                        timeFmt = "%Y-%m-%d";
                    }
                    return 'str_to_date(\'' + paraValue + '\',\'' + timeFmt + '\')';
                }else if(tableCnt.dbType.toLowerCase() == "oracle") {
                    if (meta.type.toLowerCase() == "datetime" || meta.type.toLowerCase() == "timestamp") {
                        timeFmt = 'yyyy-MM-dd HH24:mm:ss';
                    }else{
                        timeFmt = "yyyy-MM-dd";
                    }
                    return 'to_date(\'' + paraValue + '\',\'' + timeFmt + '\')';
                }
            } else {
                return paraValue;
            }
        }

        function getQueryPara(meta, paraValue, tableCnt) {
            if (paraValue == null || paraValue == "null") {
                return null;
            }

            if (meta.type.toLowerCase().indexOf("varchar") >= 0 || meta.type.toLowerCase().indexOf("char") >= 0 || meta.type.toLowerCase().indexOf("string") >= 0) {
                return "'" + paraValue + "'";
            } else if (meta.type.toLowerCase().indexOf("int") >= 0 || meta.type.toLowerCase().indexOf("double") >= 0 || meta.type.toLowerCase().indexOf("float") >= 0
                || meta.type.toLowerCase().indexOf("decimal") >= 0 || meta.type.toLowerCase().indexOf("numeric") >= 0) {
                return paraValue;
            } else if (meta.type.toLowerCase().indexOf("date") >= 0 || meta.type.toLowerCase().indexOf("time") >= 0) {
                var timeFmt = "";
                if (tableCnt.dbType.toLowerCase() == "mysql" || tableCnt.dbType.toLowerCase() == "gbase") {
                    if (meta.type.toLowerCase() == "datetime" || meta.type.toLowerCase() == "timestamp") {
                        timeFmt = '%Y-%m-%d %H:%i:%s';
                    }else{
                        timeFmt = "%Y-%m-%d";
                    }
                    return 'str_to_date(\'' + paraValue + '\',\'' + timeFmt + '\')';
                }else if(tableCnt.dbType.toLowerCase() == "oracle") {
                    if (meta.type.toLowerCase() == "datetime" || meta.type.toLowerCase() == "timestamp") {
                        timeFmt = 'yyyy-MM-dd HH24:mm:ss';
                    }else{
                        timeFmt = "yyyy-MM-dd";
                    }
                    return 'to_date(\'' + paraValue + '\',\'' + timeFmt + '\')';
                }
            } else {
                return paraValue;
            }
        }

        function containObj(cntArray, cobj) {
            for (var x = 0; x < cntArray.length; x++) {
                if (cntArray[x] == cobj) {
                    return true;
                }
            }
            return false;
        }

        function changedRowExist(editCells, changedRow, changedCol) {
            for (var i = 0; i < editCells.length; i++) {
                if (editCells[i][0] == changedRow && editCells[i][1] == changedCol) {
                    return true;
                }
            }
            return false;
        }


        function getNowFormatDate() {
            var date = new Date();
            var seperator1 = "";
            var seperator2 = "";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + date.getHours() + seperator2 + date.getMinutes()
                + seperator2 + date.getSeconds();
            return currentdate;
        }

        function createDelSql(metas, delRow, tbCnt) {
            var hasPk = false;
            var pkMetas = [];

            for (var x = 0; x < metas.length; x++) {
                if (metas[x].pk) {
                    hasPk = true;
                    metas[x].idx = x;
                    pkMetas.push(metas[x]);
                }
            }
            var delRowDatas = tbCnt.hot.getDataAtRow(delRow);

            var delWhere = "";
            if (tbCnt.dbType == "HbaseKBS" || tbCnt.dbType == "Hbase") {
                delWhere = "deleteall '" + tbCnt.schema + ":" + tbCnt.tableName + "','" + delRowDatas[0] + "'\r\n";
            } else {
                if (hasPk) {
                    delWhere = "delete from " + tbCnt.schema + "." + tbCnt.tableName + " where 1=1 ";
                    for (var x = 0; x < pkMetas.length; x++) {
                        delWhere += " and " + getCndQueryPara(pkMetas[x], delRowDatas[pkMetas[x].idx], tbCnt);
                    }
                } else {
                    if (tbCnt.dbType == "Mysql") {
                        var delWhere = "delete from " + tbCnt.schema + "." + tbCnt.tableName + " where 1=1 ";
                        for (var x = 0; x < metas.length; x++) {
                            delWhere += " and " + getCndQueryPara(metas[x], delRowDatas[x], tbCnt);
                        }

                        delWhere += " limit 1";
                    } else if (tbCnt.dbType == "Oracle") {
                        var delRowData = tbCnt.ordDatas[delRow];
                        var delWhere = "delete from " + tbCnt.schema + "." + tbCnt.tableName + " where rowid='" + delRowData[delRowData.length - 1] + "'";
                    } else if (tbCnt.dbType == "HIVE2KBS" || tbCnt.dbType == "HIVE" || tbCnt.dbType == "HIVE2") {
                        var delRowData = tbCnt.ordDatas[delRow];
                        var delWhere = "delete from " + tbCnt.schema + "." + tbCnt.tableName + " where 1=1 ";
                        for (var x = 0; x < metas.length; x++) {
                            delWhere += " and " + getCndQueryPara(metas[x], delRowDatas[x], tbCnt);
                        }
                    }
                }
            }

            return delWhere;
        }

        function executeDelData(sql, tbCnt, idx) {
            if (sql == null || sql.length < 5) {
                layer.alert("请输入语句", {icon:0,offset: '130px'});
                return false;
            }

            $("#useTimes" + xidx).html("开始执行<img src='${ctx}/resource/js/ztree/css/zTreeStyle/img/loading.gif' />");

            $.ajax({
                url: '${ctx}/meta/excuteSql?rnd=' + Math.random(),
                data: {
                    schema: tbCnt.schema,
                    dsId: tbCnt.dsId,
                    queryql: sql//default encode
                },
                type: 'GET',
                timeout: 20000,
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (data) {
                    if (data.result == "succ") {
                        tbCnt.ordDatas = tbCnt.ordDatas;

                        setTimeout(function () {
                            $("#useTimes" + xidx).html(data.msg + "</br>执行成功,用时:" + data.userTimes + "秒");
                        }, 500);

                        $("#useTimes" + xidx).html("执行成功,用时:" + data.userTimes + "秒");
                        if (tbCnt.insertDatas != null && tbCnt.insertDatas.length > 0) {
                            for (var x = 0; x < tbCnt.insertDatas.length; x++) {
                                tbCnt.ordDatas.splice(idx, 1);
                            }
                        }

                        tbCnt.hot.alter('remove_row', idx, 1);
                        tbCnt.hot.editCells = [];
                    } else {
                        $("#useTimes" + xidx).html("执行失败");
                        $("#emsg" + xidx).html(data.msg);
                    }
                }
            });
        }


        //json to string
        function jsonToString(obj) {
            var THIS = this;
            switch (typeof(obj)) {
                case 'string':
                    return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
                case 'array':
                    return '[' + obj.map(THIS.jsonToString).join(',') + ']';
                case 'object':
                    if (obj instanceof Array) {
                        var strArr = [];
                        var len = obj.length;
                        for (var i = 0; i < len; i++) {
                            strArr.push(THIS.jsonToString(obj[i]));
                        }
                        return '[' + strArr.join(',') + ']';
                    } else if (obj == null) {
                        return 'null';

                    } else {
                        var string = [];
                        for (var property in obj) string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj[property]));
                        return '{' + string.join(',') + '}';
                    }
                case 'number':
                    return obj;
                case false:
                    return obj;
            }
        }

        function initEntityTab(idx) {
            $('.manage_content_todo').css("height", $(document).height() - 16 + "px");

            $(".op_tab_row" + idx + " a").click(function () {
                var selClassName = $(this).attr("class");
                var tabName = selClassName.substring(0, selClassName.indexOf(" "));
                $(".op_tab_row" + idx + " a").removeClass("selected");
                $(".op_tab_row" + idx + " a." + tabName).addClass("selected");
                $("#op_cnt" + idx + " > div").hide();

                $("#op_cnt" + idx + " > div." + tabName).show();
            });
        }


        function createCdmTab(selCdmName, nodeId, type, parentNode) {
            var retFlag = false;
            var liWidth = 0;
            var selType;

            if (selCdmName != null && !selCdmName == "") {
                $('#navtabs li').each(function () {
                    liWidth += $(this).width();
                    tabName = $(this).children()[0].text;
                    selType = $(this).attr('type');

                    if (selCdmName == tabName.toLowerCase() && selType == type) {

                        if (liWidth > tabCntDefWidth) {
                            var lft = liWidth - tabCntDefWidth / 2;

                            $("#navtabs").css({"left": "-" + lft + "px"});
                        } else {
                            var lft = liWidth - (tabCntDefWidth / 2);
                            if (lft < 0) {
                                lft = 0;
                            }

                            $("#navtabs").css({"left": "-" + lft + "px"});
                        }

                        $('.nav-tabs li.active').removeClass('active');
                        $('.tab-content div.active').removeClass('active');

                        $(this).addClass('active');
                        var trg = $($(this).children()[0]).attr('href');
                        $('' + trg).addClass('active');

                        retFlag = true;
                    }
                });
            } else {
                selCdmName = "新建模型" + idx;
            }

            if (retFlag) {
                return false;
            }

            var tbCntWidth = $("#navtabs").width();
            if (tbCntWidth < (liWidth + 230)) {
                $("#navtabs").width((liWidth + 230) + "px");
            }

            $('.nav-tabs li.active').removeClass('active');
            $('.tab-content div.active').removeClass('active');

            var dynTabHeight = $(document).height() - 60;
            $('.nav-tabs').append('<li class="active" type="' + type + '" role="presentation"><a class="active taba" href="#home' + idx + '" aria-controls="home' + idx + '" role="tab" data-toggle="tab" aria-expanded="true">' + selCdmName + '</a><i class="xclose"></i></li>');
            $('.tab-content').append('<div style="height:' + dynTabHeight + 'px;" id="home' + idx + '" class="tab-pane clearfix active" role="tabpanel"></div>');

            if (parentNode) {
                $("#home" + idx).load("${ctx}/elements/pdm.jsp?THEME_PATH=${THEME_PATH}&nodeId=" + nodeId  +"&nodeType=model.LogicModel&idx=" + idx + "&parentNode="+ parentNode);
            }else{
                $("#home" + idx).load("${ctx}/elements/pdm.jsp?THEME_PATH=${THEME_PATH}&nodeId=" + nodeId + "&nodeType=model.LogicModel&idx=" + idx);
            }

            if (tabCntDefWidth < liWidth) {
                $("#navtabs").css({"left": "-" + ((liWidth + 230) - tabCntDefWidth) + "px"});
                $('.jqx-widget-headerx').show();
            }

            initTabs();
            idx++;
        }

        function createTableTab(selTableName, nodeId) {
            var retFlag = false;
            var liWidth = 0;

            $('#navtabs li').each(function () {
                liWidth += $(this).width();
                tabName = $(this).children()[0].text;

                if (selTableName == tabName) {
                    //alert(liWidth + "vs" + tabCntDefWidth );

                    if (liWidth > tabCntDefWidth) {
                        var lft = liWidth - tabCntDefWidth / 2;

                        $("#navtabs").css({"left": "-" + lft + "px"});
                    } else {
                        var lft = liWidth - (tabCntDefWidth / 2);
                        if (lft < 0) {
                            lft = 0;
                        }

                        $("#navtabs").css({"left": "-" + lft + "px"});
                    }

                    $('.nav-tabs li.active').removeClass('active');
                    $('.tab-content div.active').removeClass('active');

                    $(this).addClass('active');
                    var trg = $($(this).children()[0]).attr('href');
                    $('' + trg).addClass('active');

                    retFlag = true;
                }
            });

            if (retFlag) {
                return false;
            }

            var tbCntWidth = $("#navtabs").width();
            if (tbCntWidth < (liWidth + 230)) {
                $("#navtabs").width((liWidth + 230) + "px");
            }

            $('.nav-tabs li.active').removeClass('active');
            $('.tab-content div.active').removeClass('active');
            $('.nav-tabs').append('<li class="active" role="presentation"><a class="active taba" href="#home' + idx + '" aria-controls="home' + idx + '" role="tab" data-toggle="tab" aria-expanded="true">' + selTableName + '</a><i class="xclose"></i></li>');
            $('.tab-content').append('<div id="home' + idx + '" class="tab-pane clearfix active" role="tabpanel"></div>');

            $("#home" + idx).load("${ctx}/elements/metaNodeTable.jsp?THEME_PATH=${THEME_PATH}&nodeId=" + nodeId + "&nodeType=relation.Table");
            if (tabCntDefWidth < liWidth) {
                $("#navtabs").css({"left": "-" + ((liWidth + 230) - tabCntDefWidth) + "px"});
                $('.jqx-widget-headerx').show();
            }

            initTabs();
            idx++;
        }

        function initLiTableEvt() {
            $("#models li").click(function () {
                if ($(this).hasClass('selx')) {
                    $(this).removeClass("selx");
                } else {
                    //$("#models li.selx").removeClass('selx');
                    $(this).addClass("selx");
                }
            })
        }

        function beforeClick(treeId, treeNode) {
            nodeType = treeNode.nodeType;
            var nodeFrom = treeNode.nodeFrom;
            var nodeVersion = treeNode.nodeVersion;
            nodeId = treeNode.id;
            var prtId = treeNode.pId;
            //实体节点
            if (nodeType.indexOf(".") > 0) {
                if (nodeType == "core.BasePackage") {
                    var rootType = getRootNodeType(treeNode);
                    if(rootType=='PDM'){
                        $("#home0").empty().load("${ctx}/elements/pdmHome.jsp?idx=" + idx + "&nodeId=" + treeNode.id + "&nodeType=" + nodeType +"&rootType=" + rootType + "&prtId=" + prtId);
                    }else if(rootType=='ITF'){
                        $("#home0").empty().load("${ctx}/elements/pkgNode.jsp?idx=" + idx + "&nodeId=" + treeNode.id + "&nodeType=" + nodeType +"&rootType=" + rootType + "&prtId=" + prtId);
                    }else if(rootType=='DB'){
                        $("#home0").empty().load("${ctx}/elements/pkgNode.jsp?idx=" + idx + "&nodeId=" + treeNode.id + "&nodeType=" + nodeType +"&rootType=" + rootType + "&prtId=" + prtId);
                    }else if(rootType=='HOST'){
                        $("#home0").empty().load("${ctx}/elements/pkgNode.jsp?idx=" + idx + "&nodeId=" + treeNode.id + "&nodeType=" + nodeType +"&rootType=" + rootType + "&prtId=" + prtId);
                    }
                    switchToHome();
                }
                else if (nodeType == "model.PhysicalModel") {
                    $("#home0").empty().load("${ctx}/elements/pdmHome.jsp?idx=" + idx + "&prtId=" + treeNode.id, function () {
                        createCdmTab(treeNode.name, treeNode.id, "pdm");
                    });
                }
                else if (nodeType == "phy.DataSource") {
                    $("#home0").empty().load("${ctx}/elements/ds.jsp?idx=" + idx + "&nodeId=" + treeNode.id);
                    switchToHome();
                }
                else if (nodeType == "relation.Schema") {
                    $("#home0").load("${ctx}/elements/normal.jsp");
                    switchToHome();
                    $.ajax({
                        url: '${ctx}/meta/queryTreeNode?rnd=' + Math.random(),
                        data: {
                            id: treeNode.id,
                            nodeType: 'tables'
                        },
                        type: 'GET',
                        timeout: 20000,
                        dataType: 'json',
                        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                        success: function (data) {
                            mainCntMdl = data;
                            var modelCnt = $('#models');
                            modelCnt.height($(window).height() - 50);

                            $.each(data, function (idx, element) {
                                modelCnt.append('<li title="' + element.id + '" class="modelInfo">' + element.name + '</li>');
                            })
                            $("#models li.modelInfo").dblclick(function () {
                                nodeId = $(this).attr("title");
                                createTableTab($(this).text(), nodeId);
                            })
                            initLiTableEvt();
                        }
                    });

                }
                else if (nodeType == "relation.Table") {
                    var retFlag = false;
                    var liWidth = 0;

                    $('#navtabs li').each(function () {
                        liWidth += $(this).width();
                        tabName = $(this).children()[0].text;

                        if (treeNode.name == tabName) {
                            //alert(liWidth + "vs" + tabCntDefWidth );

                            if (liWidth > tabCntDefWidth) {
                                var lft = liWidth - tabCntDefWidth / 2;

                                $("#navtabs").css({"left": "-" + lft + "px"});
                            } else {
                                var lft = liWidth - (tabCntDefWidth / 2);
                                if (lft < 0) {
                                    lft = 0;
                                }

                                $("#navtabs").css({"left": "-" + lft + "px"});
                            }

                            $('.nav-tabs li.active').removeClass('active');
                            $('.tab-content div.active').removeClass('active');

                            $(this).addClass('active');
                            var trg = $($(this).children()[0]).attr('href');
                            $('' + trg).addClass('active');

                            retFlag = true;
                        }
                    });

                    if (retFlag) {
                        //return false;
                    } else {
                        var tbCntWidth = $("#navtabs").width();
                        if (tbCntWidth < (liWidth + 230)) {
                            $("#navtabs").width((liWidth + 230) + "px");
                        }

                        $('.nav-tabs li.active').removeClass('active');
                        $('.tab-content div.active').removeClass('active');
                        $('.nav-tabs').append('<li class="active" role="presentation"><a class="active taba" href="#home' + idx + '" aria-controls="home' + idx + '" role="tab" data-toggle="tab" aria-expanded="true">' + treeNode.name + '</a><i class="xclose"></i></li>');
                        $('.tab-content').append('<div id="home' + idx + '" class="tab-pane clearfix active" role="tabpanel"></div>');

                        $("#home" + idx).load("${ctx}/elements/metaNodeTable.jsp?THEME_PATH=${THEME_PATH}&nodeId=" + treeNode.id + "&nodeType=" + nodeType + "&nodeFrom=" + nodeFrom + "&nodeVersion=" + nodeVersion + "&idx=" + idx);
                        if (tabCntDefWidth < liWidth) {
                            $("#navtabs").css({"left": "-" + ((liWidth + 230) - tabCntDefWidth) + "px"});
                            $('.jqx-widget-headerx').show();
                        }

                        initTabs();
                        idx++;
                    }
                }
                else {
                    $("#home0").empty();
                    switchToHome();
                }
            }
            else{ //虚拟节点
                if(nodeType == "PDM"){
                    $("#home0").empty().load("${ctx}/elements/pkgNode.jsp?idx=" + idx + "&nodeId=" + treeNode.id + "&nodeType=" + nodeType);
                    switchToHome();
                } else if (nodeType == "DB") {
                    $("#home0").empty().load("${ctx}/elements/db.jsp?idx=" + idx + "&nodeId=" + treeNode.id);
                    switchToHome();
                } else if(nodeType == "ITF"){
                    $("#home0").empty().load("${ctx}/elements/pkgNode.jsp?idx=" + idx + "&nodeId=" + treeNode.id + "&nodeType=" + nodeType);
                    switchToHome();
                }else if(nodeType == "HOST"){
                    $("#home0").empty().load("${ctx}/elements/pkgNode.jsp?idx=" + idx + "&nodeId=" + treeNode.id + "&nodeType=" + nodeType);
                    switchToHome();
                }else {
                    $("#home0").empty();
                    switchToHome();
                }
            }

            if (!treeNode.isParent) {
                return true;
            } else {
                return true;
            }
        }

        function getRootNodeType(treeNode){
            if(treeNode.getParentNode() == null){
                return treeNode.nodeType;
            }else{
                return getRootNodeType(treeNode.getParentNode());
            }
        }

        function switchToHome() {
            $('.nav-tabs li.active').removeClass('active');
            $('.tab-content div.active').removeClass('active');

            $("#home0").addClass('active');
            $("#tab0").addClass('active');
        }

        function filterModels() {
            var filterTbNames = $('#tbFilter').val();

            if (mainCntMdl.length > 0) {
                var modelCnt = $('#models').empty();

                if (filterTbNames == null || filterTbNames == "") {
                    $.each(mainCntMdl, function (idx, element) {
                        modelCnt.append('<li title="' + element.id + '" class="modelInfo">' + element.name + '</li>');
                    })
                } else {
                    $.each(mainCntMdl, function (idx, element) {
                        if (element.name.toLowerCase().indexOf(filterTbNames.toLowerCase()) >= 0) {
                            modelCnt.append('<li title="' + element.id + '" class="modelInfo">' + element.name + '</li>');
                        }
                    })
                }

                //$("#models li.modelInfo").dblclick(function(){
                //	createTableTab($(this).text(), $(this).attr("title"));
                //})
                $("#models li.modelInfo").dblclick(function () {
                    nodeId = $(this).attr("title");
                    createTableTab($(this).text(), nodeId);
                })
                initLiTableEvt();
            }
        }

        var log, className = "dark";

        function beforeAsync(treeId, treeNode) {
            className = (className === "dark" ? "" : "dark");
            return true;
        }

        function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
                              errorThrown) {
        }

        function onAsyncSuccess(event, treeId, treeNode, msg) {
            setTimeout(function () {
                if ($(window.parent.document))
                    $(window.parent.document).find("iframe").height($(document).height());
            }, 1500);
        }

        function refreshNode(e, refreshType) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"), type = e.data.type, silent = e.data.silent, nodes = zTree
                .getSelectedNodes();
            if(refreshType == 'father'){
                nodes = nodes[0].getParentNode();
            }
            if(refreshType == 'all'){
                zTree.reAsyncChildNodes(null, "refresh", silent);
            }else{
                if(nodes instanceof Array == false){
                    zTree.reAsyncChildNodes(nodes, type, silent);
                    zTree.selectNode(nodes);
                }else{
                    if (nodes.length == 0) {
                        layui.layer.alert("Please select one parent node at first...");
                    }else{
                        for (var i = 0, l = nodes.length; i < l; i++) {
                            zTree.reAsyncChildNodes(nodes[i], type, silent);

                            if (!silent)
                                zTree.selectNode(nodes[i]);
                        }
                    }
                }
            }
        }

        function loadTree(arg) {
            if (arg) {
                var empty = {};
                var options = {async: {otherParam: {"otherParam": "zTreeAsyncTest", "view": arg}}};
                $.fn.zTree.init($("#treeDemo"), jQuery.extend(true, empty, setting, options));
            } else {
                $.fn.zTree.init($("#treeDemo"), setting);
            }
            $("#refreshNode").bind("click", {
                type: "refresh",
                silent: false
            }, refreshNode);
            $("#refreshNodeSilent").bind("click", {
                type: "refresh",
                silent: true
            }, refreshNode);
            $("#addNode").bind("click", {
                type: "add",
                silent: false
            }, refreshNode);
            $("#addNodeSilent").bind("click", {
                type: "add",
                silent: true
            }, refreshNode);
        }

        $(document).ready(function () {
            $("#tcnt").css("height", ($(document).height() - 120) + "px");
            loadTree();
            $("#treeDemo").css("height", ($(document).height() - 40) + "px");
            $("#mainCnt").css("width", $(window).width() - 290);
            $("#mainCnt").css("height", ($(document).height() - 50) + "px");

            tabCntDefWidth = $('#mainCnt').width();

            initTabs();
            $('.jqx-tabs-arrow-right').click(function () {
                var left = $('#navtabs').css("left");

                if (left.length > 2) {
                    left = left.substring(1, left.length - 2);
                    left = parseInt(left);
                } else {
                    left = 0;
                }
                var xwidth = $('#navtabs').width();
                var liWidth = 0;
                $('#navtabs li').each(function () {
                    liWidth += $(this).width();
                });

                var diffWidth = xwidth - left - 21;

                if (diffWidth > tabCntDefWidth) {
                    if (diffWidth < 40) {
                        left = diffWidth + left;
                    } else {
                        left = 40 + left;
                    }
                    $("#navtabs").css({"left": "-" + left + "px"});
                }
            });

            $('.jqx-tabs-arrow-left').click(function () {
                var left = $('#navtabs').css("left");
                if (left.length > 2) {
                    left = left.substring(1, left.length - 2);
                }

                if (left > 0) {
                    if (left > 40) {
                        left = left - 40;
                    } else {
                        left = 0;
                    }
                    $("#navtabs").css({"left": "-" + left + "px"});
                }

            });
        });

        function initTabs() {
            $('#navtabs').off('click', '.taba');
            $('#navtabs').off('click', '.xclose');

            //$('.manage_content_todo').css("height", $(document).height()-16+"px");

            //$('.nav-tabs li a').live('click', function(){
            $('#navtabs').on('click', '.taba', function () {
                $('.nav-tabs li.active').removeClass('active');
                $('.tab-content div.active').removeClass('active');

                $(this).parent().addClass('active');
                var trg = $(this).attr('href');

                $('' + trg).addClass('active');
            });

            $('#navtabs').on('click', '.xclose', function () {
                var trg = $(this).prev().attr("href");
                var nextShow;

                if ($(this).parent().next() != null && $(this).parent().next().prop("nodeName") == "LI") {
                    nextShow = $(this).parent().next();
                } else {
                    if ($(this).parent().prev() != null) {
                        nextShow = $(this).parent().prev();
                    }
                }

                $(this).parent().remove();

                trg = trg.substring(1);

                $('#' + trg).remove();

                var atv = $('.nav-tabs li.active');
                var liWidth = 0;
                $('#navtabs li').each(function () {
                    liWidth += $(this).width();
                });
                if (tabCntDefWidth < liWidth) {
                    $("#navtabs").width(liWidth - 20);
                } else {
                    $("#navtabs").css({"left": "0px"});

                    $('.jqx-widget-headerx').hide();
                }

                if (atv.length <= 0) {
                    if (nextShow != null) {
                        nextShow.addClass("active");
                        var xtrg = $($(nextShow).children()[0]).attr("href");
                        $('' + xtrg).addClass('active');
                        return false;
                    }
                } else {
                    if (nextShow != null) {
                        var xtrg = $($(nextShow).children()[0]).attr("href");
                        $('' + xtrg).addClass('active');
                        return false;
                    }
                }
            })
        }

    </script>
</head>
<body>
<input type="hidden" name="vtime" value="" id="vtime">
<table id="tcnt" style="width:100%;">
    <tr class="vat">
        <td id="line1" style="width:280px;">
            <div class="diroper">
                <div id="line2" class="more-option">
                    <!-- <a href="#">
								<img src="${THEME_PATH}/nresources/tianjin/images/icon/db--add.png" title="添加元数据存储库" />
								添加元数据存储库
							</a> -->
                    <!-- <a href="#" title="技术视角" onclick="loadTree('tech')" >
								<img src="${ctx}/resource/css/images/icon/analyze.png" title="技术视角" />
								技术
							</a>
							<a href="#" title="业务分域视角" onclick="loadTree('biz')" >
								<img src="${ctx}/resource/css/images/icon/analysisb.png" title="业务分域视角" />
								业务
							</a>
							<a href="#" title="功能分层视角" onclick="loadTree('layer')" >
								<img src="${THEME_PATH}/nresources/tianjin/images/icon/analysisa.png" title="功能分层视角" />
								分层
							</a>-->
                    <%--                    <a href="#" title="增加包" id="addPkgButton">
                                            <img src="${ctx}/resource/css/images/icon/package.png" title="增加包"/>
                                            增加目录
                                        </a>--%>
                    <!-- <a href="#" title="增加包" id="addPkgButton" >
								<img src="${ctx}/resource/css/images/icon/package.png" title="增加包" />
								增加
							</a> -->
                    <%--<a href="javascript:;" onclick="createNewQueryTab()" title="新建SQL查询" id="addPkgButton">
                        <img src="${ctx}/resource/css/images/icon/sql.png" title="新建SQL查询"/>新建查询</a>--%>
                   <%-- <a onclick="loadTree()" href="avascript:;" style="margin-left:170px;">
                        <img src="${ctx}/resource/css/images/icon/refresh.png" title="刷新"/>
                        刷新
                    </a>--%>
                    <a onclick="createNewQueryTab()" href="avascript:;" style="margin-left:170px;">
                        <img src="${ctx}/resource/css/images/icon/sql.png" title="新建SQL查询"/>
                        新建查询
                    </a>
                </div>
            </div>
            <div id="line3" class="zTreeDemoBackground left" style="width:280px;">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
            <div id="line"></div>
        </td>
        <td>
            <div id="mainCnt" class="ctn">
                <div>
                    <div role="tabpanel" id="tabcnt">
                        <ul id="navtabs" class="nav nav-tabs font-title-tab" role="tablist"
                            style="border-bottom: 0 solid #ddd;">
                            <li id="tab0" class="active" role="presentation">
                                <a class="active taba" href="#home0" aria-controls="home0" role="tab" data-toggle="tab"
                                   aria-expanded="true">Home</a>
                            </li>
                        </ul>
                        <div class="jqx-tabs-arrow-background jqx-widget-headerx"
                             style="width: 16px; height: 100%; z-index: 30; position: absolute; top: 0px; left: 0px;">
                            <span class="jqx-tabs-arrow-left"
                                  style="display: block; width: 16px; height: 16px; margin-top: 8.5px;"></span>
                        </div>
                        <div class="jqx-tabs-arrow-background jqx-widget-headerx"
                             style="width: 16px; height: 100%; z-index: 30; position: absolute; top: 0px; right: 0px;">
                            <span class="jqx-tabs-arrow-right"
                                  style="display: block; width: 16px; height: 16px; margin-top: 8.5px;"></span>
                        </div>
                    </div>
                    <div class="tab-content">
                        <div id="home0" class="tab-pane clearfix active" role="tabpanel">
                            <div class="page-title" style="height:32px;border-width: 0;">
                                <div class="left-btn-ext">
                                    <ul>
                                        <li>
											<a id="" href="javascript:;" onclick="">
											<img title="分析" src="${ctx}/resource/css/images/icon/analyze.png" width="16" height="16">
											分析
											</a>
										</li>
                                        <li>
                                            <a id="" href="javascript:;" onclick="">
                                                <img title="分析" src="${ctx}/resource/css/images/icon/analyze.png" width="16" height="16">
                                                分析
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="right-btn-ext">
                                    <ul>
                                        <li>
                                            <input type="text" name="tbFilter" id="tbFilter"/>
                                            <a onclick="filterModels()" alt="过滤"><img style="margin-bottom:12px;" title="分析" src="${ctx}/resource/css/images/icon/search.png" width="16" height="16"></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div style="clear:both;">
                                <ul id="models">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>
</body>
</html>