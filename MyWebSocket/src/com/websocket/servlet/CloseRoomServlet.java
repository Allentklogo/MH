package com.websocket.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.websocket.messageInbound.ClientMessageInbound;
import com.websocket.messageInbound.ClientMessageInboundPool;
import com.websocket.messageInbound.MessageInboundRoomPool;
import com.websocket.tools.Message;

public class CloseRoomServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7957330997606757387L;

	/**
	 * Constructor of the object.
	 */
	public CloseRoomServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getParameter("user");
		ClientMessageInbound client = ClientMessageInboundPool.getOnLineClient(user);
		String roomName = client.getRoomName();

		if ( MessageInboundRoomPool.findRoomByName(roomName).getRoomHost().equals(user) ){
			//聊天室创始人关闭聊天室
			MessageInboundRoomPool.findRoomByName(roomName).closeRoom();
			ClientMessageInboundPool.sendMessageToAllClient(
					Message.getJsonMessage(
							"room_list", 
							MessageInboundRoomPool.getOnLineRoom())
					);
		}else{
			//聊天室成员退出聊天室
			MessageInboundRoomPool.findRoomByName(roomName).removeClientMessageInbound(client);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
