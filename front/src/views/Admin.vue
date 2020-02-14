<template>
  <div>
    <!-- <Cpuchart/> -->
    <!-- <Memorychart/> -->
    <Status v-bind:cpuidle="cpuidle" />
  </div>
</template>

<script>
import Cpuchart from '../components/Cpuchart'
import Memorychart from '../components/Memorychart'
import Status from '../components/Serverstatus'
import axios from "axios";
export default {
  name: "Chart",
  components: {
    Cpuchart,
    Memorychart,
    Status
  },
  data: function() {
    return {
      cpuidle : 0
    };
  },
  methods:{
    init() {
      // const BUF_SIZE = 86400000;
      // let d = new Date().getTime();

      // if (this.series[0].data.length >= BUF_SIZE) {
      //   this.series[0].data.shift();
      //   this.series[1].data.shift();
      // }
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
          console.log(this.cpuidle);
          // this.series[0].data.push([d, response.data.totalmemory - response.data.freememory]);
          // this.series[1].data.push([d, response.data.totalmemory]);
          // this.$refs.realtime.updateSeries([
          //   {
          //     name: "usememory",
          //     data: this.series[0].data
          //   },
          //   { name: "totalmemory", data: this.series[1].data }
          // ]);

          console.log(response)
        })
        .catch(error => {
          console.log(error);
        });
    },
    loop() {
      this.s = setInterval(() => {
        this.init();
      }, 3000);
    }
  },
  mounted() {
    this.loop();
  },
  destroyed() {
    console.log("destroyed");
    clearInterval(this.s);
  }
};
</script>