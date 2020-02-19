<template>
  <div v-show="flag">
    <v-tabs dark background-color="teal darken-3" show-arrows vertical class="admintabs">
      <v-tabs-slider color="teal lighten-3"></v-tabs-slider>
      <v-tab class="mx-5">
          Admin
        </v-tab>
        <v-tab class="mx-5">
          LOG
        </v-tab>
        <v-tab class="mx-5">
          Members
        </v-tab>
        <v-tab class="mx-5" @click="goHome">
          NEWSPACE
        </v-tab>

      <v-tab-item>
        <v-container>
          <strong>서버 현황</strong>
            <hr>
          <v-row>
            <v-col>
              <Cpuchart v-bind:cpuidle="cpuidle" v-bind:cpuusage="cpuusage" />
            </v-col>
            <v-col>
              <Memorychart v-bind:freememory="freememory" v-bind:totalmemory="totalmemory" />
            </v-col>
          </v-row>
          <v-row>
            <v-col class="mx-5">
              <Status
                v-bind:serverSamsung="serverSamsung"
                v-bind:serverLg="serverLg"
                v-bind:serverSk="serverSk"
              />
            </v-col>
            <v-col>
              <NewsStatus />
            </v-col>
          </v-row>
        </v-container>
      </v-tab-item>
      <v-tab-item>
        <div class="adminback">
          <div class="logtitle">NEWSPACE LOG</div>
          <div class="adminlog">
            <Logs v-bind:logs="logs" />
          </div>
          <div class="logtitle"></div>
        </div>
      </v-tab-item>
      <v-tab-item>
        <v-container>
          <strong>유저 목록</strong>
          <v-row>
            <v-col>
              <ul class="member-list">
                <li v-for="(m,i) in members" :key="m.member_id">
                  <v-avatar class="img" color="blue lighten-2">
                    <span class="white--text headline">{{m.name[0]}}</span>
                  </v-avatar>
                  <div class="username">
                    <p @click="detailMember(i)">{{m.name}}</p>
                    <p @click="detailMember(i)">{{m.email}}</p>
                    <p></p>
                    <v-icon v-on:click="deleteMember(m.member_id)">fa-times</v-icon>
                  </div>
                </li>
              </ul>
            </v-col>
            <v-col>
              <ul col>
                <v-card>
                  <v-container style="min-height:430px;">
                    <v-card-title>유저정보</v-card-title>
                    <v-form>
                      <v-text-field outlined label="이름" v-model="name" type="text" />
                      <v-text-field outlined label="이메일" v-model="email" type="text" />
                      <v-text-field outlined label="키워드" v-model="keyword" type="text" />
                      <v-text-field outlined label="로그인 타입" v-model="type" type="text" />
                    </v-form>
                  </v-container>
                </v-card>
              </ul>
            </v-col>
          </v-row>
        </v-container>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import Cpuchart from "../components/Cpuchart";
import Memorychart from "../components/Memorychart";
import Status from "../components/Serverstatus";
import Logs from "../components/LogList";
import axios from "axios";
import NewsStatus from '../components/NewsStatus'

export default {
  name: "Chart",
  components: {
    Cpuchart,
    Memorychart,
    Status,
    Logs,
    NewsStatus
  },
  data: function() {
    return {
      cpuidle: 0,
      cpuusage: 0,
      freememory: 0,
      totalmemory: 0,
      serverSamsung: false,
      serverLg: false,
      serverSk: false,
      logs: [],
      members: [],
      name: "",
      email: "",
      keyword: "",
      type: "",
      flag: false,
      check: true
    };
  },
  mounted() {
    this.loop();
    this.getMember();
  },
  methods: {
    detailMember(member_id) {
      this.name = this.members[member_id].name;
      this.email = this.members[member_id].email;
      this.keyword = this.members[member_id].keyword;
      this.type = this.members[member_id].type;
    },
    goHome(){
      this.$router.push('/')
    },
    getMember() {
      axios
        .post(
          "http://192.168.31.85:8080/member/adminManage/",
          {},
          {
            headers: {
              "login-token": localStorage.getItem("login-token")
            }
          }
        )
        .then(response => {
          this.members = response.data;
          this.flag = true
        })
        .catch(error => {
          console.log(error);
          alert('접근 권한이 없습니다.')
          this.$router.push('/')
        });
    },
    deleteMember(member_id) {
      var ans = confirm("정말 지우시겠습니까?");
      if (ans) {
        axios
          .delete(
            `http://192.168.31.85:8080/member/deleteMember/${member_id}`,
            {
              headers: {
                "login-token": localStorage.getItem("login-token")
              }
            }
          )
          .then(response => {
            this.members = response.data;
            this.flag=true
          })
          .catch(e => {
            console.log(e);

          });
      }
    },
    loop() {
          this.init();
    },
    init() {
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
          this.cpuidle = response.data.cpuidle;
          this.cpuusage = response.data.cpuusage;
          this.freememory = response.data.freememory;
          this.totalmemory = response.data.totalmemory;
          this.serverSamsung = response.data.serverSamsung;
          this.serverLg = response.data.serverLg;
          this.serverSk = response.data.serverSk;
          this.logs = response.data.log;
          this.loop()
        })
        .catch(error => {
          console.log(error);
          this.loop()
        });
    }
  },
  destroyed() {
    clearInterval(this.s);
  }
};
</script>
<style scoped>
p {
  margin: 0;
  padding: 0;
}
ul {
  padding: 20px;
}
li {
  list-style: none;
  overflow: hidden;
  margin-bottom: 10px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 10px;
  max-width: 400px;
  height: 70px;
  position: relative;
}

li .img {
  width: 48px;
  height: 48px;
  float: left;
  border: 2px solid #333;
  border-radius: 40px;
  margin-right: 10px;
}

.username {
  float: left;
  margin-top: 5px;
}
i {
  float: left;
  position: absolute;
  right: 0;
  top: 10px;
  padding: 6px 12px;
  border-radius: 4px;
  line-height: 1.42857143;
  cursor: pointer;
  outline: none;
}
.admintabs {
  width: 100vw;
  height: 100vh;
}
.adminlog {
  overflow-y: scroll;
  width: 87vw;
  height: 80vh;
  background-color: black;
  margin-left: 20px;
}
.logtitle {
  width: 100vw;
  height: 10vh;
  color: white;
  font-size: 30px;
  padding: 15px 0px 0px 30px;
}
.adminback {
  background-color: rgb(32, 32, 32);
}
.member-list {
  height: 80vh;
  overflow-y: scroll;
}
</style>