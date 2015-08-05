var ws = null;

function startWebSocket() {
	if ('WebSocket' in window) {
			ws = new WebSocket("ws://127.0.0.1:8080/MyWebSocket/websocket");
	} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket("ws://127.0.0.1:8080/MyWebSocket/websocket");
	} else {
		alert("您当前使用的浏览器不支持，请使用火狐或者谷歌浏览器！");
	}

	ws.onmessage = function(evt) {
		say(evt.data);
	};

	ws.onclose = function(evt) {
		alert("close!");
	};

	ws.onerror = function() {
		alert("ERROR");
	};

	ws.onopen = function(evt) {
		var div = document.getElementById("username");
		div.innerHTML = user;
	};

	// 消息接收分级处理
	ws.onmessage = function(message) {
		//返回消息主体
		var message = JSON.parse(message.data);
		messageTool(message);
	};
}

// 发送消息
function sendMsg() {
	var message = {
		"type" : "send",
		"content" : document.getElementById('writeMsg').value,
		"user" : user
	};
	ws.send(JSON.stringify(message));
	showMessage("me",document.getElementById('writeMsg').value);
}