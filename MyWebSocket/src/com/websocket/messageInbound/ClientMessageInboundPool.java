package com.websocket.messageInbound;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class ClientMessageInboundPool {
	//在线用户map容器
	private static Map<String,ClientMessageInbound> onLineClient = new HashMap<String,ClientMessageInbound>(); 
	
	//返回在线用户数量
	public static int getOnLineClientNumber(){
		return onLineClient.size();
	}
	
	//输入用户名返回在线用户实例
	public static ClientMessageInbound getOnLineClient(String name){
		return onLineClient.get(name);
	}
	
	//用户加入在线用户map容器
	public static void addOnLineClient(String name,ClientMessageInbound client){
		onLineClient.put(name, client);
	}
	
	//用户移除在线用户map容器
	public static void removeOnLineClient(String name){
		onLineClient.remove(name);
	}
	
	//向所有在线用户发送消息
	public static void sendMessageToAllClient(String message){
		Set<String> setKey = onLineClient.keySet();
		for( String key : setKey ){
			ClientMessageInbound client = onLineClient.get(key);
			if( client != null ){
				try {
					client.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//向某个用户发送信息
	public static void senMessageToOneClient(String mseeage,String username) throws IOException{
		ClientMessageInbound client  = getOnLineClient(username);
		if( client != null ){
			client.getWsOutbound().writeTextMessage( CharBuffer.wrap(mseeage) );
		}
	}
	
	
}
