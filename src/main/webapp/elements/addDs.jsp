<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="st" uri="http://www.si-tech.com/tag"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String prtId = request.getParameter("prtId");
	String dsId  = request.getParameter("dsId");
%>

<script>
	var defPort = {"DB2":5000,"MYSQL":3306,"Oracle":1521, "Hbase":60000, "Hive":10000,"IMPALA":25000,"HIVE2":10000,"HIVE2KBS":10000, "SybaseIQ":5007,"GBASE":5258};

	function testConnection(){
		var name = $('#name').val();
		var type = $('#type').val();
		
		var host = $('#host').val();
		var port = $('#port').val();
		var serverName = $('#serverName').val();
		var user = $('#user').val();
		var pwd = $('#pwd').val();
		
		if(host == null || host == ""){
            layer.alert("服务器地址不能为空", {icon:0,offset: '100px'});
			return false;
		}
		if(port == null || port == ""){
            layer.alert("端口不能为空", {icon:0,offset: '100px'});
			return false;
		}
		if(serverName == null || serverName == ""){
            layer.alert("服务名不能为空", {icon:0,offset: '100px'});
			return false;
		}
		
		$.ajax({
			url : '${ctx}/meta/checkDsConnect?rnd=' + Math.random(),
	   		data : {
					name:name,
	   				type:type,
	   				host:host,
	   				port:port,
	   				serverName:serverName,
	   				user:user,
	   				pwd:pwd,
	   				krb5ConfigPath:$('#krb5ConfigPath').val(),
	   				hadoopHomeDir:$('#hadoopHomeDir').val(),
	   				keytabPath:$('#keytabPath').val(),
	   				principal:$('#principal').val()
	   		       },
	   		type : 'POST',
	   		timeout:20000,
	   		dataType : 'json',
	   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
				if(data.result == null || data.result == ""){
					$('#operRet').html(data.msg);
				}else if(data.result == "succ"){
					$('#operRet').html(data.msg);
				}
			}
		});
	}
	
	function addConnection(){
		var name = $('#name').val();
		var type = $('#type').val();
		
		var host = $('#host').val();
		var port = $('#port').val();
		var serverName = $('#serverName').val();
		var user = $('#user').val();
		var pwd = $('#pwd').val();
		
		if(name == null || name == ""){
            layer.alert("连接名称不能为空", {icon:0,offset: '100px'});
			return false;
		}
		
		if(host == null || host == ""){
            layer.alert("服务器地址不能为空", {icon:0,offset: '100px'});
			return false;
		}
		if(port == null || port == ""){
            layer.alert("端口不能为空", {icon:0,offset: '100px'});
			return false;
		}
		if(serverName == null || serverName == ""){
            layer.alert("服务名不能为空", {icon:0,offset: '100px'});
			return false;
		}
		
		$.ajax({
			url : '${ctx}/meta/addConnection?rnd=' + Math.random(),
	   		data : {
	   				uid:$('#uid').val(),
					name:name,
	   				type:type,
	   				host:host,
	   				port:port,
	   				serverName:serverName,
	   				user:user,
	   				pwd:pwd,
	   				prtId:'<%=prtId%>',
	   				krb5ConfigPath:$('#krb5ConfigPath').val(),
	   				hadoopHomeDir:$('#hadoopHomeDir').val(),
	   				keytabPath:$('#keytabPath').val(),
	   				principal:$('#principal').val(),
	   				hostUser:$('#hostUser').val(),
	   				hostPwd:$('#hostPwd').val(),
	   				hostDir:$('#hostDir').val()
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
	
	function dealDsPersonalInfo(){
		var dsType = $('#type').val();
		
		$('#port').val(defPort[dsType]);
		if(dsType == "HIVE2KBS"){
			$('#hiveKbs').show();
			$('#diffTitleCnt').html("principal：");
		}else if(dsType == "HbaseKBS"){
			$('#hiveKbs').show();
			$('#diffTitleCnt').html("Hbase配置路径：");
		}else{
			$('#hiveKbs').hide();
		}
	}
	
	$(document).ready(function() {
		var dsId = '<%=dsId%>';
		
		if(dsId != null && dsId != ""){
			loadDsInfo(dsId);
		}
	});
	
	function loadDsInfo(dsId){
		$.ajax({
			url : '${ctx}/meta/getMetaCore?rnd=' + Math.random(),
	   		data : {
					nodeId:dsId
	   		       },
	   		type : 'GET',
	   		timeout:20000,
	   		dataType : 'json',
	   		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
				$('#importbutton').val("修改数据源");
				
				$('#name').val(data.name);
				$('#type').val(data.type);
				$('#host').val(data.host);
				$('#port').val(data.port);
				$('#serverName').val(data.serverName);
				$('#user').val(data.user);
				$('#pwd').val(data.pwd);
				$('#uid').val(data.id);
				
				if(data.type=="HIVE2KBS" || data.type == "HbaseKBS"){
					$('#hiveKbs').show();
					
					$('#krb5ConfigPath').val(data.krb5ConfigPath);
					$('#hadoopHomeDir').val(data.hadoopHomeDir);
					$('#keytabPath').val(data.keytabPath);
					$('#principal').val(data.principal);
					
					$('#hostUser').val(data.fileHeader);
					$('#hostPwd').val(data.fileName);
					$('#hostDir').val(data.cmt);
					
					if(data.type=="HIVE2KBS"){
						$('#keytabPath').html('principal');
					}else{
						$('#keytabPath').html('Hbase配置路径');
					}
				}
			}
		})
	}
</script>

<div style="color:red;width:600px;" id="operRet"></div>

<input type="hidden" name="uid" id="uid" value=""/>
<table class="normal-table" style="margin:0px auto;width:650px">
	<tbody>
		<tr>
			<td width="130">连接名称：</td>
			<td>
				<input id="name" name="name" style="width: 400px;height: 20px;" type="text">
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="110">数据库类型：</td>
			<td>
				<select onchange="dealDsPersonalInfo()" id="type" name="type" style="width: 400px;height: 25px;">
					<option value="">请选择！</option>
					<st:option dictCode="data.dbtype" />
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="110">服务器地址：</td>
			<td>
				<input id="host" name="host" style="width: 400px;height: 20px;" type="text">
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="100">端口：</td>
			<td>
				<input id="port" name="port" style="width: 400px;height: 20px;" type="text">
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="100">服务名：</td>
			<td>
				<input id="serverName" name="serverName" style="width: 400px;height: 20px;" type="text">
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="100">用户：</td>
			<td>
				<input id="user" name="user" style="width: 400px;height: 20px;" type="text">
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="100">密码：</td>
			<td>
				<input id="pwd" name="pwd" style="width: 400px;height: 20px;" type="text">
				<font color="red">*</font>
			</td>
		</tr>
	</tbody>
</table>
<table id="hiveKbs" class="normal-table" style="margin:0px auto;width:650px;display:none;">
	<tr>
		<td width="120">krb5文件路径：</td>
		<td>
			<input id="krb5ConfigPath" name="krb5ConfigPath" style="width: 400px;height: 20px;" type="text">
			<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td width="130">Hadoop home路径：</td>
		<td>
			<input id="hadoopHomeDir" name="hadoopHomeDir" style="width: 400px;height: 20px;" type="text">
			<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td width="100">keytab文件路径：</td>
		<td>
			<input id="keytabPath" name="keytabPath" style="width: 400px;height: 20px;" type="text">
			<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td width="100" id="diffTitleCnt">Principle：</td>
		<td>
			<input id="principal" name="principal" style="width: 400px;height: 20px;" type="text">
			<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td width="100" id="diffTitleCnt">FTP主机用户：</td>
		<td>
			<input id="hostUser" name="hostUser" style="width: 400px;height: 20px;" type="text">
			<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td width="100" id="diffTitleCnt">FTP主机密码：</td>
		<td>
			<input id="hostPwd" name="hostPwd" style="width: 400px;height: 20px;" type="text">
			<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td width="100" id="diffTitleCnt">FTP目录：</td>
		<td>
			<input id="hostDir" name="hostDir" style="width: 400px;height: 20px;" type="text">
			<font color="red">*</font>
		</td>
	</tr>
</table>

<div style="text-align:left;margin-left:124px;width:100%">
	<p> </p>
	<input id="testbutton" class="button-secondary pure-button button-std" value="测试" onclick="testConnection()" type="button">
	<input id="importbutton" class="button-error pure-button button-std" value="添加数据源" onclick="addConnection()" type="button">
	
	<p> </p>
</div>