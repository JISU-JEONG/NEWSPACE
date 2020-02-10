package com.ssafy.edu.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    	if(chatMessage.getSender() == null) {
			List<String> 닉 = Arrays.asList("기분나쁜", "기분좋은", "신바람나는", "상쾌한", "짜릿한", "그리운", "자유로운", "서운한", "울적한", "비참한",
					"위축되는", "긴장되는", "두려운", "당당한", "배부른", "수줍은", "창피한", "멋있는", "열받은", "심심한", "잘생긴", "이쁜", "시끄러운");
			List<String> 네임 = Arrays.asList("사자", "코끼리", "호랑이", "곰", "여우", "늑대", "너구리", "침팬치", "고릴라", "참새", "고슴도치", "강아지",
					"고양이", "거북이", "토끼", "앵무새", "하이에나", "돼지", "하마", "원숭이", "물소", "얼룩말", "치타", "악어", "기린", "수달", "염소", "다람쥐",
					"판다");
			Collections.shuffle(닉);
			Collections.shuffle(네임);
			chatMessage.setSender(닉.get(0) + 네임.get(0));
		}
    	else {    		
    		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    	}
		if(chatMessage.getSessionid() == null) {
			int num = (int)(Math.random() * (200000 - 100000 + 1)) + 100000;
			chatMessage.setSessionid(num+"");
		}
        websocket.usercont();
        return chatMessage;
    }
 
}