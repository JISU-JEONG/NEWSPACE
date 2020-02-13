package com.ssafy.edu.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.ssafy.edu.dto.ChatMessage;

@Component
public class WebSocketEventListener {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	static HashMap<String, String> user = new HashMap<>();

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {

		MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(),
				SimpMessageHeaderAccessor.class);
		
		GenericMessage generic = (GenericMessage) accessor.getHeader("simpConnectMessage");

		Map nativeHeaders = (Map) generic.getHeaders().get("nativeHeaders");
		Map simpSessionAttributes = (Map) generic.getHeaders().get("simpSessionAttributes");
		String username = nativeHeaders.get("username").toString().replace("[", "").replace("]", "");
		String room = nativeHeaders.get("room").toString().replace("[", "").replace("]", "");
		user.put(simpSessionAttributes.get("sessionId").toString(), username);
		System.out.println(room);
		usercont();
		logger.info("Received a new web socket connection");

	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		String member_id = (String) headerAccessor.getSessionAttributes().get("member_id");
		String room = (String) headerAccessor.getSessionAttributes().get("room");
		ChatMessage chatMessage = new ChatMessage();
		System.out.println(headerAccessor);
		if (username != null && username != "" && !username.equals("undefined")) {
			logger.info("User Disconnected : " + username);

			chatMessage.setType(ChatMessage.MessageType.LEAVE);
			chatMessage.setSender(username);
			chatMessage.setSessionid(member_id);
			user.remove(sessionId);
		}

		else {
			logger.info("Disconnected : " + sessionId);
			chatMessage.setType(ChatMessage.MessageType.LEAVE);
			chatMessage.setSender(user.get(sessionId));
			chatMessage.setSessionid(member_id);
			user.remove(sessionId);
		}

		usercont();
		messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
	}

	public void usercont() {
		Iterator<String> mapIter = user.keySet().iterator();
		String userlist[] = new String[user.size()];
		int i = 0;
		while (mapIter.hasNext()) {

			String key = mapIter.next();
			String value = user.get(key);
			System.out.println(key + " " + value);
			userlist[i++] = value;
		}

		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setType(ChatMessage.MessageType.JOINUSER);
		chatMessage.setUsers(userlist);
		chatMessage.setUsernumber(user.size());
		messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
	}
}