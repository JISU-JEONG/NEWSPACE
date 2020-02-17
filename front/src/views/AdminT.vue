<template>
  <v-container fluid :grid-list-md="!$vuetify.breakpoint.xs">
    <v-layout wrap row>
      <v-flex xs12 sm6 class="pb-2">
        <v-card>
          <Cpuchart v-bind:cpuidle="cpuidle" v-bind:cpuusage="cpuusage" />
        </v-card>
      </v-flex>
            <v-flex xs12 sm6 class="pb-2">
        <v-card>
          <Memorychart v-bind:freememory="freememory" v-bind:totalmemory="totalmemory" />
        </v-card>
      </v-flex>
      <v-flex xs12 sm6 md3 class="pb-2">
        <v-card height="130px">
          각종 현황
        </v-card>
      </v-flex>
      <v-flex xs12 sm6 md3 class="pb-2">
        <v-card height="130px">
          각종 현황
        </v-card>
      </v-flex>
      <v-flex xs12 sm6 md3 class="pb-2">
        <v-card height="130px">
          각종 현황
        </v-card>
      </v-flex>
      <v-flex xs12 sm6 md3 class="pb-2">
        <v-card height="130px">
          각종 현황
        </v-card>
      </v-flex>
      <v-flex xs12 sm4 class="pb-2">
        <v-card height="140px">
          간단한 차트
        </v-card>
      </v-flex>
      <v-flex xs12 sm4 class="pb-2">
        <v-card height="140px">
          간단한 차트
        </v-card>
      </v-flex>
      <v-flex xs12 sm4 class="pb-2">
        <v-card height="140px">
          간단한 차트
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import Cpuchart from "../components/Cpuchart";
import Memorychart from "../components/Memorychart";
import Status from "../components/Serverstatus";
import Logs from "../components/LogList";
import axios from "axios";

export default {
  name: "Chart",
  components: {
    Cpuchart,
    Memorychart,
    Status,
    Logs
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
    };
  },
  mounted() {
    this.loop();
    this.getMember();
  },
  methods: {
    getMember(){
      axios.post(
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
        })
        .catch(error => {
          console.log(error);
        });
    },
    deleteMember(member_id){
      axios
        .delete(`http://192.168.31.85:8080/member/deleteMember/${member_id}`
        ,{
          headers:{
            "login-token": localStorage.getItem("login-token")
          }
        })
        .then(response => {
          this.members = response.data;
        })
        .catch(e => {
          console.log(e);
        })
    },
    loop() {
      this.s = setInterval(() => {
        this.init();
      }, 1000);
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
        })
        .catch(error => {
          console.log(error);
        });
    }
  },

  destroyed() {
    console.log("destroyed");
    clearInterval(this.s);
  }
};
</script>