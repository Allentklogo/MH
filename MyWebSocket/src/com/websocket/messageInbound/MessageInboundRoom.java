package com.websocket.messageInbound;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.websocket.tools.Message;

public class MessageInboundRoom {
	// 保存聊天室在线用户的Map容器
	private Map<String, ClientMessageInbound> onLineUser = new HashMap<String, ClientMessageInbound>();
	// 设置聊天室在线用户上限
	private  int RoomNumber = 15;
	//聊天室名字
	private String roomName;
	//聊天室主人
	private String roomHost;

	/**
	 *含有参数的构造函数
	 * @param roomName 聊天室名称
	 * @param roomHost 聊天室建造人
	 */
	public MessageInboundRoom(String roomName,String roomHost){
		setRoomHost(roomHost);
		setRoomName(roomName);
	}
	
	// 返回聊天室用户数据
		public  Set<String> getOnLineUser() {
			return onLineUser.keySet();
		}
		
		//返回聊天室用户数量
		public int getRoomClientNumber(){
			return onLineUser.size();
		}
	
	// 聊天室新增用户
	public  void addClientMessageInbound(ClientMessageInbound client) {
		if (onLineUser.size() < RoomNumber) {
			//map容器增加client
			onLineUser.put(client.getUserName(), client);
			//client设置聊天室名称
			client.setRoomName(roomName);
			//向聊天室所有人发送用户加入聊天室消息
			SendMessageToAllClient(Message.getJsonMessage("client_join_success", "加入聊天室", client.getUserName()));
			SendMessageToAllClient(Message.getJsonMessage("client_list", getOnLineUser()));
		} else {
			// 向当前用户发送不能加入聊天室消息
			try {
				client.getWsOutbound().writeTextMessage(CharBuffer.wrap(
						Message.getJsonMessage("client_join_error", "聊天室："+roomName+"满人", client.getUserName())
						));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 用户下线离开聊天室
	public  void removeClientMessageInbound(ClientMessageInbound client) {
		// client设置聊天室名称
		client.setRoomName(null);
		// 向聊天室所有人发送用户离开聊天室消息
		SendMessageToAllClient(Message.getJsonMessage("client_remove_success",
				"离开聊天室", client.getUserName()));
		onLineUser.remove(client.getUserName());
		SendMessageToAllClient(Message.getJsonMessage("client_list", getOnLineUser()));
		try {
			client.getWsOutbound().writeTextMessage(CharBuffer.wrap(
					Message.getJsonMessage("client_list", new HashSet<String>())
					));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//关闭聊天室
	public void closeRoom(){
			SendMessageToAllClient(
						Message.getJsonMessage("room_close", 
																	"关闭聊天室："+roomName,
																	getRoomHost()));
			MessageInboundRoomPool.removeRoom(roomName);
			SendMessageToAllClient(Message.getJsonMessage("client_list", new HashSet<String>()));
			onLineUser.clear();
			roomHost = null;
			roomName = null;
	}
	
	//开启聊天室
	public void openRoom(ClientMessageInbound client){
		//map容器增加client
		onLineUser.put(client.getUserName(), client);
		//client设置聊天室名称
		client.setRoomName(roomName);
		// 聊天室容器添加新元素
		MessageInboundRoomPool.addRoom(this);
		// 发送聊天室创建消息
		SendMessageToAllClient(Message.getJsonMessage("room_create",
				"创建聊天室：" + roomName, client.getUserName()));
	}

	// 向聊天室所有在线用户发送消息
	public  void SendMessageToAllClient(String message) {
		try {
			Set<String> keySet = onLineUser.keySet();
			for (String key : keySet) {
				ClientMessageInbound inbound = onLineUser.get(key);
				if(inbound != null){
					inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomHost() {
		return roomHost;
	}

	public void setRoomHost(String roomHost) {
		this.roomHost = roomHost;
	}

}
