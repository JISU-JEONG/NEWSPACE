package com.ssafy.edu.controller;

import java.util.ArrayList;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.ChatMessage;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class WebSocketController {
	static ArrayList<String> user = new ArrayList<>();
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    	System.out.println(chatMessage.getContent());
        return chatMessage;
    }
 
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println(headerAccessor.getSessionAttributes());
        System.out.println(chatMessage.getType());
        user.add((String)headerAccessor.getSessionAttributes().get("username"));
        int size = user.size();
        System.out.println(size);
        for(int i=0; i<size; i++) {
        	System.out.println(user.get(i));
        }
        return chatMessage;
    }
 
}