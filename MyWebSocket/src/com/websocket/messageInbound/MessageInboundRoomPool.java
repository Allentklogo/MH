package com.websocket.messageInbound;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MessageInboundRoomPool {
	
	//聊天室map容器
	private static Map<String,MessageInboundRoom> onLineRoom = new HashMap<String, MessageInboundRoom>();
	
	//得到在线聊天室的名字
	public static Set<String> getOnLineRoom(){
		return onLineRoom.keySet();
	}
	
	//得到在线聊天室的数量
	public static int getOnLineRoomSize(){
		return onLineRoom.size();
	}
	
	/**
	 * 名字查找聊天室
	 * @param roomName
	 * @return boolean
	 */
	public static MessageInboundRoom findRoomByName(String roomName){
		Set<String> setKey = onLineRoom.keySet();
		for(String key : setKey){
			if( roomName.equals(key) )
				return onLineRoom.get(roomName);
		} 
		return null;
	}
	
	public static void addRoom(MessageInboundRoom newRoom){
		onLineRoom.put(newRoom.getRoomName(), newRoom);
	}
	
	/**
	 * 关闭聊天室
	 * @param roomName
	 */
	public static void removeRoom(String roomName){
		//删除map容器元素
		onLineRoom.remove(roomName);
	}
	
}
