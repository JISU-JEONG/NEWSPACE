<template>
  <v-container>
    <input type="text" v-model="message" placeholder="Type a message..." />
    <button v-on:click="sendMessage()">전송</button>
    <br />
    <ul v-for="(item, index) in receivemessage" :key="index">{{ item }}</ul>
  </v-container>
</template>

<script>
import axios from "axios";

var stompClient = null;
var username = "test";

export default {
  name: "Profile",
  data() {
    return {
      message: "",
      receivemessage: []
    };
  },
  methods: {
    login() {
      axios
        .post("http://192.168.31.84:8080/login", {
          username: "aaa"
        })
        .then(res => {
          console.log(res);
        })
        .catch(e => {
          console.log(e);
        });
    },
    connect() {

      var socket = new SockJS("http://192.168.31.84:8080/ws");
      stompClient = Stomp.over(socket);

      stompClient.connect({}, this.onConnected, this.onError);
    },
    onConnected() {
      // Subscribe to the Public Topic
      stompClient.subscribe("/topic/publicChatRoom", this.onMessageReceived);

      // Tell your username to the server
      stompClient.send(
        "/app/chat.addUser",
        {},
        JSON.stringify({ sender: username, type: "JOIN" })
      );
    },
    onError(error) {
      connectingElement.textContent =
        "Could not connect to WebSocket server. Please refresh this page to try again!";
      connectingElement.style.color = "red";
    },
    sendMessage() {
      var messageContent = this.message.trim();
      
      if (messageContent && stompClient) {
        var chatMessage = {
          sender: this.username,
          content: this.message,
          type: "CHAT"
        };
        stompClient.send(
          "/app/chat.sendMessage",
          {},
          JSON.stringify(chatMessage)
        );
      }
      this.message = "";
    },
    onMessageReceived(payload) {
      var message = JSON.parse(payload.body);
      if (message.type === "JOIN") {
        this.receivemessage.push(message.sender + "가 들어오셨습니다.");
      } else if (message.type === "LEAVE") {
        this.receivemessage.push(message.sender + "가 떠나셨습니다.");
      } else {
        this.receivemessage.push(this.username + " : " + message.content);
      }
    }
  },
  beforeMount(){
      this.username = prompt("닉");
  },
  mounted() {
    
    this.connect();
  }
};
</script>
<style>
</style>    