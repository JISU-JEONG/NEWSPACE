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
import store from "../store";
import info from "../services/getInfo"
import stmop from "../services/stomp"
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
      countReadMessages: 0,
      message: "",
      usernumber: 0,
      userlist:[],
      receivemessage: [],
      fromMe: 'from-me', // 동적 css 위한 변수저장
      fromThem: 'from-them',
      autofocus: false, // 채팅창 켜지면 입력창에 바로 focus되게 하려 했으나..
    };
  },
  computed: {
    countUnreadMessages: function() {
      return this.receivemessage.length - this.countReadMessages
    }
  },
  methods: {
    openChat() { // 채팅창 열고 닫기
      this.show = !this.show
      if (!this.show) {
        this.countReadMessages = this.receivemessage.length
      } 
      this.autofocus = !this.autofocus
    },
    connect() {
      var socket = new SockJS("http://192.168.31.84:8080/ws");
      stompClient = Stomp.over(socket);

      stompClient.connect({username : localStorage.getItem("member_name"), member_id : localStorage.getItem("member_id")}, this.onConnected, this.onError);
    },
    onConnected() {
      stompClient.subscribe("/topic/publicChatRoom", this.onMessageReceived);

      stompClient.send(
        "/app/chat.addUser",
        {},
        JSON.stringify({ sender: localStorage.getItem("member_name"), type: "JOIN" })
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
          sender: localStorage.getItem("member_name"),
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
        if(message.sessionid===localStorage.getItem("member_id")){
          this.receivemessage.push({from_me:message.sessionid, content:message.sender + "님이 들어오셨습니다.", sender:"system"});
        }
        else{
          this.receivemessage.push({from_me:message.sessionid, content:message.sender + "님이 들어오셨습니다.", sender:"system"});
        }
      } else if (message.type === "LEAVE") {
        if(message.sessionid===localStorage.getItem("member_id")){
          this.receivemessage.push({from_me:message.sessionid, content:message.sender + "님이 떠나셨습니다.", sender:"system"});
        }
        else{
          this.receivemessage.push({from_me:message.sessionid, content:message.sender + "님이 떠나셨습니다", sender:"system"});
        }
      } else if (message.type === "JOINUSER") {
        this.userlist = message.users;
        this.usernumber = message.usernumber;
      }
      else {
        if(message.sessionid===localStorage.getItem("member_id")){
          this.receivemessage.push({from_me:true, content:message.content, sender:message.sender});
        } else{
          this.receivemessage.push({from_me:false, content:message.content, sender:message.sender});
        }
      }
    }
  },
  beforeMount(){
    if(localStorage.getItem("member_name") === null){
      var nick = ["기분나쁜", "기분좋은", "신바람나는", "상쾌한", "짜릿한", "그리운", "자유로운", "서운한", "울적한", "비참한",
            "위축되는", "긴장되는", "두려운", "당당한", "배부른", "수줍은", "창피한", "멋있는", "열받은", "심심한", "잘생긴", "이쁜", "시끄러운"];
      var name = ["사자", "코끼리", "호랑이", "곰", "여우", "늑대", "너구리", "침팬치", "고릴라", "참새", "고슴도치", "강아지",
            "고양이", "거북이", "토끼", "앵무새", "하이에나", "돼지", "하마", "원숭이", "물소", "얼룩말", "치타", "악어", "기린", "수달", "염소", "다람쥐",
            "판다"];
      var randomnick = Math.floor(Math.random() * nick.length) + 1;
      var randomname = Math.floor(Math.random() * name.length) + 1;
    }
    else{
      this.sender = localStorage.getItem("member_name");
    }

    if(localStorage.getItem("member_id") === null){
      var randomid =  Math.floor(Math.random() * 2000000) + 1000000;
    }
    
    console.log(nick[randomnick] + " " + name[randomname]);
    console.log(randomid);
  },
  mounted() {
    this.connect();
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
  height: 90%;
  padding-top: 48px;  
  background: #BBDEFB;
  overflow-x:hidden;
  overflow-y: scroll;
}
.chat-form {
  width: 100%;
  height: 10%;
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
/* .from-me li:before {
  content: "";
  position: absolute;
  z-index: -1;
  bottom: -2px;
  right: -7px;
  height: 20px;
  border-right: 20px solid #0B93F6;
  border-bottom-left-radius: 16px 14px;
  transform: translate(0, -2px);
} */
/* .from-me li:after {
  content: "";
  position: absolute;
  z-index: 1;
  bottom: -2px;
  right: -56px;
  width: 26px;
  height: 20px;
  background: white;
  border-bottom-left-radius: 10px;
  transform: translate(-30px, -2px);
} */

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

/* .from-them li:before {
  content: "";
  position: absolute;
  z-index: 2;
  bottom: -2px;
  left: -7px;
  height: 20px;
  border-left: 20px solid #fff;
  border-bottom-right-radius: 16px 14px;
  transform: translate(0, -2px);
}
.from-them li:after {
  content: "";
  position: absolute;
  z-index: 3;
  bottom: -2px;
  left: 4px;
  width: 26px;
  height: 20px;
  background: white;
  border-bottom-right-radius: 10px;
  transform: translate(-30px, -2px);
} */
</style>