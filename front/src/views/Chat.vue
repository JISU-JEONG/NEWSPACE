<template>
  <v-container>
    <h1>현재접속유저 : {{usernumber}}</h1>
    <ul v-for="(item, index) in userlist" :key="index">{{ item }}</ul>
    <hr>
    <br />

    <section>
    <ul v-for="(item, index) in receivemessage" :key="index">
      <div class="from-me" :key="index" v-if="item.from_me">
        <p v-if="item.sender !== 'system'">{{ item.sender }}</p>
        {{ item.content }}
      </div>
      <div class="from-them" :key="index" v-else>
        <p v-if="item.sender !== 'system'">{{ item.sender }}</p>
        {{ item.content }}
      </div>
      <div class="clear"></div>
    </ul>
    </section>
    <input type="text" v-model="message" placeholder="Type a message..." v-on:keyup.enter="sendMessage()"/>
    <button v-on:click="sendMessage()">전송</button>
  </v-container>
</template>

<script>
import axios from "axios";
import store from "../store";
import info from "../services/getInfo"
var stompClient = null;
var username = "";

export default {
  name: "Chat",
  data() {
    return {
      message: "",
      usernumber: 0,
      userlist:[],
      receivemessage: []
    };
  },
  methods: {
    connect() {

      var socket = new SockJS("http://192.168.31.84:8080/ws");
      stompClient = Stomp.over(socket);

      stompClient.connect({username : username}, this.onConnected, this.onError);
    },
    onConnected() {
      stompClient.subscribe("/topic/publicChatRoom", this.onMessageReceived);

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
          sender: username,
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
        if(message.sender===username){
          this.receivemessage.push({from_me:true, content:message.sender + "님이 들어오셨습니다.", sender:"system"});
        }
        else{
          this.receivemessage.push({from_me:false, content:message.sender + "님이 들어오셨습니다.", sender:"system"});
        }
      } else if (message.type === "LEAVE") {
        if(message.sender===username){
          this.receivemessage.push({from_me:true, content:message.sender + "님이 떠나셨습니다.", sender:"system"});
        }
        else{
          this.receivemessage.push({from_me:false, content:message.sender + "님이 떠나셨습니다", sender:"system"});
        }
      } else if (message.type === "JOINUSER") {
        this.userlist = message.users;
        this.usernumber = message.usernumber;
      }
      else {
        if(message.sender===username){
          this.receivemessage.push({from_me:true, content:message.content, sender:message.sender});
        }
        else{
          this.receivemessage.push({from_me:false, content:message.content, sender:message.sender});
        }
      }
    }
  },
  beforeMount(){
    username = localStorage.getItem("member_name");
    if(username==null){
      username = "익명의사용자"
    }
  },
  mounted() {
    this.connect();
  }
};
</script>

<style>
body {
  font-family: "Helvetica Neue";
  font-size: 20px;
  font-weight: normal;
}

section {
  max-width: 450px;
  margin: 50px auto;
}
section div {
  max-width: 255px;
  word-wrap: break-word;
  margin-bottom: 20px;
  line-height: 24px;
}

.clear {
  clear: both;
}

.from-me {
  position: relative;
  padding: 10px 20px;
  color: white;
  background: #0B93F6;
  border-radius: 25px;
  float: right;
}
.from-me:before {
  content: "";
  position: absolute;
  z-index: -1;
  bottom: -2px;
  right: -7px;
  height: 20px;
  border-right: 20px solid #0B93F6;
  border-bottom-left-radius: 16px 14px;
  -webkit-transform: translate(0, -2px);
}
.from-me:after {
  content: "";
  position: absolute;
  z-index: 1;
  bottom: -2px;
  right: -56px;
  width: 26px;
  height: 20px;
  background: white;
  border-bottom-left-radius: 10px;
  -webkit-transform: translate(-30px, -2px);
}

.from-them {
  position: relative;
  padding: 10px 20px;
  background: #E5E5EA;
  border-radius: 25px;
  color: black;
  float: left;
}
.from-them:before {
  content: "";
  position: absolute;
  z-index: 2;
  bottom: -2px;
  left: -7px;
  height: 20px;
  border-left: 20px solid #E5E5EA;
  border-bottom-right-radius: 16px 14px;
  -webkit-transform: translate(0, -2px);
}
.from-them:after {
  content: "";
  position: absolute;
  z-index: 3;
  bottom: -2px;
  left: 4px;
  width: 26px;
  height: 20px;
  background: white;
  border-bottom-right-radius: 10px;
  -webkit-transform: translate(-30px, -2px);
}
</style>