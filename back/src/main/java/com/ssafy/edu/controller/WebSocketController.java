package com.ssafy.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.ChatMessage;
import com.ssafy.edu.listener.WebSocketEventListener;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class WebSocketController {
	@Autowired
	WebSocketEventListener websocket;
	
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    	System.out.println(chatMessage);
        return chatMessage;
    }
 
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    	System.out.println(chatMessage);
        websocket.usercont();
        return chatMessage;
    }
    
    @MessageMapping("/chat.sendMessage/test")
    @SendTo("/topic/publicChatRoom/test")
    public ChatMessage sendMessagetest(@Payload ChatMessage chatMessage) {
    	System.out.println("test : " + chatMessage);
        return chatMessage;
    }
 
    @MessageMapping("/chat.addUser/test")
    @SendTo("/topic/publicChatRoom/test")
    public ChatMessage addUsertest(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    	System.out.println("test : " + chatMessage);
        websocket.usercont();
        return chatMessage;
    }
 
}