<template>
  <div>
    <div class="demos">
      <strong>Crawling 현황</strong>
      <hr><br>
      <div class="demo-up" v-if="serverSamsung">
        <span class="server-status" type="up"></span>
        <span>Samsung Crawling ON.</span>
      </div>
      <div class="demo-down" v-else>
        <span class="server-status" type="down"></span>
        <span>Samsung Crawling OFF.</span>
      </div>
      <div class="demo-up" v-if="serverLg">
        <span class="server-status" type="up"></span>
        <span>LG Electronics Crawling ON.</span>
      </div>
      <div class="demo-down" v-else>
        <span class="server-status" type="down"></span>
        <span>LG Electronics Crawling OFF.</span>
      </div>
      <div class="demo-up" v-if="serverSk">
        <span class="server-status" type="up"></span>
        <span>SK Hynix Crawling ON.</span>
      </div>
      <div class="demo-down" v-else>
        <span class="server-status" type="down"></span>
        <span>SK Hynix Crawling OFF.</span>
      </div>
    </div>
    <br>
    <div>
        <v-btn v-on:click="ServerOn">Check Crawling</v-btn>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Serverstatus",
  props: ["serverSamsung", "serverLg", "serverSk"],
  methods: {
    ServerOn(){
      axios
      .post("http://192.168.31.85:8080/member/adminServerOn/",{}
        ,{
          headers: {
              "login-token": localStorage.getItem("login-token")
          }
        })
      .then(response => {
      })
      .catch(e => {
        console.log(e);
      })
    }
  }
};
</script>

<style scoped>
h1,
h3 {
  margin: 10px;
}
.demos {
  display: block;
}
.demos div span:nth-child(2) {
  margin-left: 30px;
  line-height: 35px;
}
.demo-up {
  display: block;
  height: 35px;
  position: relative;
  color: #2ecc71;
}
.demo-down {
  display: block;
  height: 35px;
  position: relative;
  color: #e74c3c;
}

.server-status {
  left: 10px;
  top: 50%;
  margin-left: 0px;
  margin-top: -5px;
  position: absolute;
  vertical-align: middle;
  width: 10px;
  height: 10px;
  border-radius: 50%;
}
.server-status::before,
.server-status::after {
  left: 0;
  top: 50%;
  margin-left: -1px;
  margin-top: -6px;
  position: absolute;
  vertical-align: middle;
  width: 12px;
  height: 12px;
  border-radius: 50%;
}
.server-status[type="up"],
.server-status[type="up"]::before,
.server-status[type="up"]::after {
  background: #2ecc71;
}
.server-status[type="down"],
.server-status[type="down"]::before,
.server-status[type="down"]::after {
  background: #e74c3c;
}

.server-status::before {
  content: "";
  animation: bounce 1.5s infinite;
}
.server-status::after {
  content: "";
  animation: bounce 1.5s -0.4s infinite;
}

@keyframes bounce {
  0% {
    transform: scale(1);
    -webkit-transform: scale(1);
    opacity: 1;
  }
  100% {
    transform: scale(2);
    -webkit-transform: scale(2);
    opacity: 0;
  }
}

@-webkit-keyframes bounce {
  0% {
    transform: scale(1);
    -webkit-transform: scale(1);
    opacity: 1;
  }
  100% {
    transform: scale(2);
    -webkit-transform: scale(2);
    opacity: 0;
  }
}
</style>
