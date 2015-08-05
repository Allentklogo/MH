package com.websocket.servlet;

import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.StreamInbound;
import com.websocket.messageInbound.ClientMessageInbound;

public class WebSocketServlet extends org.apache.catalina.websocket.WebSocketServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105834598246307363L;
	
	
	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest arg1) {
		return new ClientMessageInbound( (String) arg1.getSession().getAttribute("user"));
	}

}
