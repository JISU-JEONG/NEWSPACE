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


	//Connect
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {

		MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(),
				SimpMessageHeaderAccessor.class);
		
		GenericMessage generic = (GenericMessage) accessor.getHeader("simpConnectMessage");
		Map nativeHeaders = (Map) generic.getHeaders().get("nativeHeaders");
		Map simpSessionAttributes = (Map) generic.getHeaders().get("simpSessionAttributes");
		String username = nativeHeaders.get("username").toString().replace("[", "").replace("]", "");
		System.out.println(username);
		if(username == null || username.equals("null")) {
			System.out.println("익명의 유저 접속");
		}
		else {
			user.put(simpSessionAttributes.get("sessionId").toString(), username);
			usercont();
		}
		logger.info("Received a new web socket connection");
	}

	//Disconnet
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");
		ChatMessage chatMessage = new ChatMessage();
		System.out.println(sessionId);
		user.remove(sessionId);
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
//			System.out.println(key + " " + value);
			userlist[i++] = value;
		}

		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setType(ChatMessage.MessageType.JOINUSER);
		chatMessage.setUsers(userlist);
		chatMessage.setUsernumber(user.size());
		messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
	}
}