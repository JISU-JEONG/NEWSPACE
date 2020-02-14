<template>
  <div class="app">
    <apexcharts ref="realtime" type="line" height="300" :options="options" :series="series"></apexcharts>
  </div>
</template>

<script>
import VueApexCharts from "vue-apexcharts";
import axios from "axios";

export default {
  name: "Cpuchart",
  components: {
    apexcharts: VueApexCharts
  },
  mounted() {
    this.loop();
  },
  methods: {
  },
  data: function() {
    return {
      options: {
        chart: {
          animations: {
            enabled: true,
            easing: "linear",
            dynamicAnimation: {
              speed: 3000
            }
          },
          toolbar: {
            show: false
          },
          zoom: {
            enabled: false
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: "smooth"
        },
        title: {
          text: "Cpu",
          align: "left"
        },
        markers: {
          size: 3
        },
        xaxis: {
          type: "datetime",
          range: 20000,
          tickAmount: 10,
          labels: {
            format: "HH:mm:ss"
          }
        },
        yaxis: {
          max: 100
        },
        legend: {
          show: true
        },
        tooltip: {
          x: {
            format: "dd/MM/yy HH:mm:ss"
          }
        }
      },
      series: [
        {
          name: "cpuusage",
          data: [[new Date().getTime(), 0]]
        },
        {
          name: "cpuidle",
          data: [[new Date().getTime(), 0]]
        }
      ]
    };
  },
  destroyed() {
    console.log("destroyed");
    clearInterval(this.s);
  }
};
</script>