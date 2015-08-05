package com.websocket.messageInbound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import com.websocket.tools.Message;

public class ClientMessageInbound extends MessageInbound {

	//用户名称
	private String userName;
	//聊天室名称
	private String roomName;
	
	public ClientMessageInbound(String username){
		this.userName = username;
	}
	
	@Override
	protected void onOpen(WsOutbound outbound) {
		//用户加入在线用户
		ClientMessageInboundPool.addOnLineClient(userName, this);
		//向当前用户发送当前聊天室容器map信息
		try {
			this.getWsOutbound().writeTextMessage(CharBuffer.wrap(
					Message.getJsonMessage(
							"room_list", 
							MessageInboundRoomPool.getOnLineRoom())
					));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onClose(int status) {
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		throw new UnsupportedOperationException("Binary message not supported.");
	}

	//接受客户端发送的文本消息
	@Override
	protected void onTextMessage(CharBuffer arg0) throws IOException {
		//向聊天室所有在先用户发送消息
		if ( MessageInboundRoomPool.findRoomByName(getRoomName()) != null ){
			MessageInboundRoomPool.findRoomByName(getRoomName()).SendMessageToAllClient(arg0.toString());
		}
	}
	

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
