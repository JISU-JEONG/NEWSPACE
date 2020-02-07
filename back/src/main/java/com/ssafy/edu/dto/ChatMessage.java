package com.ssafy.edu.dto;

public class ChatMessage {
    
    private MessageType type;
    private String content;
    private String sender;
    private String[] users;
    private int usernumber;
    
    public enum MessageType {
        CHAT, JOIN, LEAVE, JOINUSER
    }
 
	public String[] getUsers() {
		return users;
	}

	public void setUsers(String[] users) {
		this.users = users;
	}

	
	public int getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(int usernumber) {
		this.usernumber = usernumber;
	}

	public MessageType getType() {
        return type;
    }
 
    public void setType(MessageType type) {
        this.type = type;
    }
 
    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
 
    public String getSender() {
        return sender;
    }
 
    public void setSender(String sender) {
        this.sender = sender;
    }
     
}