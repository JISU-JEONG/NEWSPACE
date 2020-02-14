<template>
  <div>
    
  </div>
</template>

<script>
import VueApexCharts from "vue-apexcharts";

export default {
  name: "Memorychart",
  components: {
    apexcharts: VueApexCharts
  },
  props: ["freememory", "totalmemory"],
  mounted() {
    this.loop();
  },
  destroyed() {
    console.log("destroyed");
    clearInterval(this.s);
  },
  methods: {

    loop() {
      this.s = setInterval(() => {
        this.init();
      }, 1000);
    }
  },
  data: function() {
    return {
      
    };
  }
};
</script>