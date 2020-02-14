<template>
  <div>
    <Status v-bind:serverSamsung="serverSamsung" v-bind:serverLg="serverLg" v-bind:serverSk="serverSk"/>
    <Cpuchart v-bind:cpuidle="cpuidle" v-bind:cpuusage="cpuusage" />
    <Memorychart v-bind:freememory="freememory" v-bind:totalmemory="totalmemory" />
    
  </div>
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
      serverSk: false
    };
  },
  mounted() {
    this.loop();
  },
  methods: {
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