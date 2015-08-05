<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="gb2312" import="com.websocket.messageInbound.*"%>
<%
	String user = (String) session.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style>
div {
	margin-left: auto;
	margin-right: auto;
}
</style>
<script type="text/javascript" src="../JS/messageTool.js" ></script>
<script type="text/javascript" src="../JS/websocket.js" ></script>
<script type="text/javascript" src="../JS/jquery-1.11.1.min.js"></script>
</head>
<body onload="startWebSocket()">
	<script type="text/javascript">
	    $(window).unload(function (evt) {  
	    var submitData = {
	    	"user" : user
	    	};
	    	$.post('http://127.0.0.1:8080/MyWebSocket/servlet/ClientCloseServlet', submitData,
			function(data) {
			});
       });  
		var user = "${user}";
	</script>
	<div>WebSocket聊天室</div>
	<div id="username"></div>
	<div style="border:1px solid #09F"></div>
	<div id="client_send" >
	</div>
	<div id="create_room" >
		<input type="text" id="roomname"></input>
		<input type="button" value="CreateRoom" onclick="CreateRoom()"></input>
		<input type="button" value="JoinRoom" onclick="JoinRoom()"></input>
	</div>
	<div id="room_list"
		style="width: 200px;height: 200px;right:  1%;position: absolute;">
		<ul>
			<li>房间列表</li>
		</ul>
	</div>
	
	<div id="client_list"
		style="width: 200px;height: 200px;left:  1%;position: absolute;">
		<ul>
			<li>聊天室成员列表</li>
		</ul>
	</div>
	
	<div id="showmsg" style="width: 400px;height: 500px;">
	</div>
	
</body>
</html>