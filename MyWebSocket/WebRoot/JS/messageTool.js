//消息分级处理
function messageTool(message){
	if (message.type == 'room_list') {
		// 更新在线房间列表
		getOnLineRoom(message.content);
	} else if( message.type == 'client_list' ){
		//更新聊天室在线成员
		getOnLineClient(message.content);
	} else if (message.type == 'room_create') {
		alert(message.user + message.content);
		// 关闭创建聊天室功能
		CloseRoomFunction();
		//开启聊天功能
		OpenSendFunction();
	} else if (message.type == 'room_error') {
		alert(message.user + message.content);
	} else if (message.type == 'room_close') {
		alert(message.user + message.content);
		// 开启聊天室功能
		OpenRoomFunction();
		//关闭聊天功能
		CloseSendFunction();
		clearMsg();
	} else if( message.type == 'client_join_success' ){
		alert(message.user + message.content);
		if( message.user == user ){
			// 关闭创建聊天室功能
			CloseRoomFunction();
			//开启聊天功能
			OpenSendFunction();
		}
	}else if( message.type == 'client_remove_success' ){
		alert(message.user + message.content);
		if( message.user == user ){
			// 开启聊天室功能
			OpenRoomFunction();
			//关闭聊天功能
			CloseSendFunction();
			clearMsg();
		}
	}else if( message.type == 'send' ){
		if( message.user != user ){
			showMessage(message.user,message.content);
		}
	}
}

//得到在线聊天室
function getOnLineRoom(content) {
	var div = document.getElementById("room_list");
	div.innerHTML = "";
	var ul = document.createElement('ul');
	for(var con in content){
		var li = document.createElement("li");
		li.innerHTML = content[con];
		ul.appendChild(li);
		li = null;delete li;
	}  
	div.appendChild(ul);
	div = null;delete div;
	ul = null;delete ul;
}

//得到聊天室在线用户
function getOnLineClient(content) {
	var div = document.getElementById("client_list");
	div.innerHTML = "";
	var ul = document.createElement('ul');
	for(var con in content){
		var li = document.createElement("li");
		li.innerHTML = content[con];
		ul.appendChild(li);
		li = null;delete li;
	}  
	div.appendChild(ul);
	div = null;delete div;
	ul = null;delete ul;
}
// 创建聊天室
function CreateRoom() {
	var submitData = {
		"roomname" : $("#roomname").val(),
		"user" : user
	};
	$.post('http://127.0.0.1:8080/MyWebSocket/servlet/RoomServlet', submitData,
			function(data) {
			});
}

//加入聊天室
function JoinRoom(){
	var submitData = {
			"roomname" : $("#roomname").val(),
			"user" : user
		};
		$.post('http://127.0.0.1:8080/MyWebSocket/servlet/JoinRoomServlet', submitData,
				function(data) {
				});
}

// 关闭聊天室
function CloseRoom() {
	var submitData = {
		"user" : user
	};
	$.post('http://127.0.0.1:8080/MyWebSocket/servlet/CloseRoomServlet',
			submitData, function(data) {
			});
}

// 关闭聊天室功能
function CloseRoomFunction() {
	// 1.删除创建聊天室功能
	var div = document.getElementById("create_room");
	div.innerHTML="";
	// 2.创建关闭聊天室功能
	var input = document.createElement("input");
	input.setAttribute("type", "button");
	input.setAttribute("value", "CloseRoom");
	input.setAttribute("onclick", "CloseRoom()");
	div.appendChild(input);
	div = null; delete div;
	input = null; delete input;
}

// 开启聊天室功能
function OpenRoomFunction() {
	// 删除关闭聊天室功能
	var div = document.getElementById("create_room");
	div.innerHTML="";
	var input = document.createElement("input");
	input.setAttribute("type", "text");
	input.setAttribute("id", "roomname");
	div.appendChild(input);
	input = null; delete input;
	input = document.createElement("input");
	input.setAttribute("type", "button");
	input.setAttribute("value", "CreateRoom");
	input.setAttribute("onclick", "CreateRoom()");
	div.appendChild(input);
	input = document.createElement("input");
	input.setAttribute("type", "button");
	input.setAttribute("value", "JoinRoom");
	input.setAttribute("onclick", "JoinRoom()");
	div.appendChild(input);
	div = null;delete div;
	input = null; delete input;
}

//开启聊天功能
function OpenSendFunction(){
	var div = document.getElementById("client_send");
	div.innerHTML = "";
	var input = document.createElement("input");
	input.setAttribute("type", "text");
	input.setAttribute("id", "writeMsg");
	div.appendChild(input);
	input = null;delete input;
	
	input = document.createElement("input");
	input.setAttribute("type", "button");
	input.setAttribute("value", "send");
	input.setAttribute("onclick", "sendMsg()");
	div.appendChild(input);
	div = null ;delete div;
	input = null;delete input;
}

//关闭聊天功能
function CloseSendFunction(){
	var div = document.getElementById("client_send");
	div.innerHTML = "";
}

function showMessage(user,content){
	var div = document.getElementById("showmsg");
	var p = document.createElement("div");
	p.innerHTML = user+":"+content;
	div.appendChild(p);
	div = null; delete div;
	p = null; delete p;
}

function clearMsg(){
	var div = document.getElementById("showmsg");
	div.innerHTML = "";
	div = null;delete div;
}