package com.websocket.tools;

import java.util.Set;

import net.sf.json.JSONObject;

public class Message {
	private String type;
	private String content;
	private String user;
	
	public static String getJsonMessage(String type,String content,String user){
		JSONObject result = new JSONObject();
		result.element("type", type);
		result.element("user", user);
		result.element("content",content);
		return result.toString();
	}
	
	public static String getJsonMessage(String type,Set<String> content){
		JSONObject result = new JSONObject();
		result.element("type", type);
		result.element("content",content.toArray());
		return result.toString();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
