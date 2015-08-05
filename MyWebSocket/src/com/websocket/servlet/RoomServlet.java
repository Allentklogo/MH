package com.websocket.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.websocket.messageInbound.ClientMessageInbound;
import com.websocket.messageInbound.ClientMessageInboundPool;
import com.websocket.messageInbound.MessageInboundRoom;
import com.websocket.messageInbound.MessageInboundRoomPool;
import com.websocket.tools.Message;

public class RoomServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4275719501950850905L;

	/**
	 * Constructor of the object.
	 */
	public RoomServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		String roomname = req.getParameter("roomname");
		ClientMessageInbound client = ClientMessageInboundPool
				.getOnLineClient(req.getParameter("user"));
		
		MessageInboundRoom newRoom = new MessageInboundRoom(roomname,client.getUserName());
		newRoom.openRoom(client);
		ClientMessageInboundPool.sendMessageToAllClient(
				Message.getJsonMessage(
						"room_list",
						MessageInboundRoomPool.getOnLineRoom()));
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
