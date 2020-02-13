<template>
  <div>
    <div class="admin_header">
      <h1 class="admin_font">NEWSPACE ADMIN PAGE</h1>
    </div>
    <div>
      <div class="adminbody">
        <div class="bodyfont">User 관리</div>
        <v-card v-for="u in userlist" :key="u.id">{{u.email}}</v-card>
        <div class="bodyfont">상태 관리</div>
        <v-card v-for="c in checkCrawlinglist" :key="c.id">{{c}}</v-card>
        <div></div>
        <v-card></v-card>
      </div>
      <div class="subbody">
        <v-card>나는 뭐쓸래?</v-card>
      </div>
    </div>
  </div>
</template>

<script>
import router from "../router";
import axios from "axios";
import store from "../store";
export default {
  name: "admin",
  data() {
    return {
      userlist: [],
      checkCrawlinglist: [],
      status: [],
      users: []
    };
  },
  methods: {
    getout() {
      if (this.$store.state.auth !== "1") {
        alert("잘못된 접근입니다. 꺼져 주세요.");
        router.push("/");
      } else {
        // alert("관리자넹?");
        this.t = setInterval(() => {
          this.read();
        }, 3000);
      }
    },
    test() {
      axios
        .post(
          "http://192.168.31.84:8080/member/adminManage/",
          {},
          {
            headers: {
              "login-token": localStorage.getItem("login-token")
            }
          }
        )
        .then(response => {
          console.log(response);
          this.userlist = response.data.memberList;
          this.checkCrawlinglist = response.data.checkCrawling;
          console.log(this.checkCrawlinglist);
        })
        .catch(error => {
          console.log(error);
        });
    },
    read() {
      axios
        .post(
          "http://192.168.31.85:8080/member/adminStatus/",
          {},
          {
            headers: {
              "login-token": localStorage.getItem("login-token")
            }
          }
        )
        .then(response => {
          console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  beforeMount() {
    this.getout();
  },
  mounted() {
    this.test();
  },
  destroyed() {
    console.log("destroyed");
    clearInterval(this.t);
  }
};
</script>

<style scoped>
.admin_header {
  width: 100%;
  background-color: grey;
  height: 150px;
}
.admin_font {
  display: block;
  color: white;
  text-align-last: center;
  line-height: 150px;
}
.adminbody {
  display: inline-block;
  width: 70%;
  padding: 30px;
}
.subbody {
  display: inline-block;
  width: 30%;
  padding: 10px;
}
.bodyfont {
  width: 70%;
}
</style>