<template>
  <div class="chat-world">
    <v-btn color="green" style="position: fixed; left:24px; bottom: 72px; z-index:50" dark fab @click="openChat" >
      <v-icon v-if="show || !countUnreadMessages">mdi-message-processing</v-icon>
      <v-badge
        v-else
        color="red"
        :content="countUnreadMessages"
      >
        <v-icon>mdi-message-processing</v-icon>
      </v-badge>
    </v-btn>
    <div class="chat-container" v-drag:header v-show="show">
        <div class="chat-nav " >
          <v-row>
            <v-col cols="11" id="header" style="cursor:move; padding:0 0 0 12px;" >
              <span> 현재접속유저 : {{usernumber}}</span>
            </v-col>
            <v-col cols="1" style="padding:0;" >
              <v-icon @click="openChat">mdi-close-outline</v-icon>
            </v-col>
          </v-row>
        </div>
        <v-container class="chat-room">
        <ul v-for="(item, index) in receivemessage" :key="index">
          <div v-if="item.sender !== 'system'" :class="[item.from_me ? fromMe : fromThem]" >
            <p>{{ item.sender }}</p>
            <li>{{ item.content }}</li>            
          </div>
          <div v-else style="text-align:center">
            <li>{{ item.content }}</li>
          </div>

        </ul>
      </v-container>
      <div class="chat-form">
        <v-form @submit.prevent="sendMessage()" autocomplete="off">
          <v-text-field 
            ref="form"
            solo
            v-model="message"
            :disabled="disabled"
            :label="label"
            type="text"
            append-outer-icon="mdi-send"
            @click:append-outer="sendMessage"
            hide-details="true"
            autofocus="autofocus"
          />
        </v-form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "../store"
import info from "../services/getInfo"
import stmop from "../services/stomp"
import sockjs from '../services/sockjs'
import drag from '@branu-jp/v-drag'

var stompClient = null;

export default {
  name: "chat",
  directives: { 
    drag 
  },
  data() {
    return {
      show: false,
      disabled: true, // 로그인 안한 사용자 채팅 막기
      label: "로그인이 필요합니다.",
      countReadMessages: 0, // 읽지않은 메세지 카운트
      message: "",
      usernumber: 0,
      username: "",
      flag: false,
      userlist:[],
      receivemessage: [],
      fromMe: 'from-me', // css class 바인딩 위한 변수
      fromThem: 'from-them',
      autofocus: false, // 채팅창 켜지면 입력창에 바로 focus되게 하려 했으나..
    };
  },
  computed: {
    countUnreadMessages: function() {
      return this.receivemessage.length - this.countReadMessages
    },
    member_name: function(){
      return this.$store.state.member_name;
    }
  },
  watch:{
    member_name: function(){
      console.log("member_name : " + this.member_name)
      console.log("username : " + this.username)
      if(this.member_name === null || this.member_name === undefined){
        if(!stompClient){
          this.flag = false;
          stompClient.disconnect();
          this.receivemessage.push({from_me:true, content:"연결이 종료되었습니다.", sender:"system"});
          this.username = "익명의 사용자";
          this.disabled = true,
          this.label = '로그인이 필요합니다.'
          console.log("disconnet");
        }
      }
      else{
        this.username = this.member_name;
        this.disabled = false,
        this.label = ''

      }
    }
  },
  methods: {
    openChat() { // 채팅창 버튼 클릭시
      this.show = !this.show // 채팅창 열고 닫기
      if (!this.show) { // 닫혔을 때, show ==== false가 되었을때 카운트
        this.countReadMessages = this.receivemessage.length;
      }
      else{
        if(!this.flag){
          this.flag = true;
          this.connect();
        }
      }
      this.autofocus = !this.autofocus
    },
    connect() {
      var socket = new sockjs("http://192.168.31.84:8080/ws");
      stompClient = Stomp.over(socket);

      stompClient.connect({username : this.username, member_id : this.member_id}, this.onConnected, this.onError);
    },
    onConnected() {
      stompClient.subscribe("/topic/publicChatRoom", this.onMessageReceived);

      stompClient.send(
        "/app/chat.addUser",
        {},
        JSON.stringify({ sender: this.username, type: "JOIN" })
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
          sessionid: localStorage.getItem("member_id"),
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
        this.receivemessage.push({from_me:message.sessionid, content:message.sender + "님이 들어오셨습니다.", sender:"system"});
      } else if (message.type === "LEAVE") {  
        this.receivemessage.push({from_me:message.sessionid, content:message.sender + "님이 떠나셨습니다", sender:"system"});
      } else if (message.type === "JOINUSER") {
        this.userlist = message.users;
        this.usernumber = message.usernumber;
      }
      else {
        if(message.sessionid===localStorage.getItem("member_id")){
          console.log(message.sessionid);
          this.receivemessage.push({from_me:true, content:message.content, sender:message.sender});
        } else{
          console.log(message.sessionid);
          this.receivemessage.push({from_me:false, content:message.content, sender:message.sender});
        }
      }
    }
  },
  beforeMount() {
    info();
  },
  mounted(){
    this.username = localStorage.getItem("member_name");
    if(this.username === null){
      this.username = "익명의 사용자";
    }
    console.log("접속유저 : " + this.username);
  },
	updated() {
    // 채팅창 스크롤 맨 아래로 내리기
		this.$nextTick(function () {
      var objDiv = document.querySelector('.chat-room')
      objDiv.scrollTop = objDiv.scrollHeight
    })
  }
  
};

</script>

<style scoped>
ul {
  padding: 0;
}
.chat-world {
  position: fixed;
  top: 0;
  left: 0;
  width: 0;
  height: 0;
  z-index: 10
}
.chat-container {
  position: absolute;
  top: 90px;
  left: 50px;
  height: 500px;
  width: 400px;
  background: #fff;
  border: solid 2px rgb(190, 190, 190);
  box-shadow: 2px rgb(190, 190, 190);
  font-family: "Helvetica Neue";
  font-size: 16px;
  
}
.chat-nav {
  width: 100%;
  height: 36px;
  position: absolute;
  top: 0;
  background: #BBDEFB;
  border-bottom: solid 1px rgb(190, 190, 190);
  z-index: 5;
  padding-left: 8px;
  line-height: 2em;
}
.chat-room {
  width: 100%;
  height: 100%;
  padding: 48px 12px;  
  background: #BBDEFB;
  overflow-x:hidden;
  overflow-y: scroll;
}
.chat-form {
  width: 100%;
  height: 48px;
  position: absolute;
  padding-right: 7px;
  bottom:0;
  border-top: solid 1px rgb(190, 190, 190);
  background: #fff000;
}

.clear {
  clear: both;
}
.from-me {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
.from-me p {
  display: inline-block;
  font-size: 13px;
  font-weight: 600;
  color: rgb(15, 7, 7);
  margin:0 8px 4px 0;
}
.from-me li{
  max-width: 90%;
  position: relative;
  padding: 8px 12px;
  margin-bottom: 6px;
  color: white;
  background: #0B93F6;
  border-radius: 15px;
  list-style: none;
}
.from-them {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.from-them p {
  display: inline-block;
  font-size: 13px;
  font-weight: 600;
  color: rgb(15, 7, 7);
  margin:0 8px 4px 0;
}
.from-them li {
  max-width: 90%;
  position: relative;
  padding: 8px 12px;
  margin-bottom: 6px;
  background: #fff;
  border-radius:  15px;
  color: black;
  list-style: none;
}
@media screen and (max-width:600px) {
  .chat-container {
    width: 100vw;
    height: 100vh;
    left: 0;
    top: 0;
    z-index: 50; 
  } 
}
</style>