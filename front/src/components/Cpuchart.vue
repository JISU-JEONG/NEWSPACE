<template>
  <div>
    <apexcharts ref="realtime" height="350" type="line" :options="options" :series="series"></apexcharts>
  </div>
</template>

<script>
import VueApexCharts from "vue-apexcharts";

export default {
  name: "Cpuchart",
  components: {
    apexcharts: VueApexCharts
  },
  props: ["cpuidle", "cpuusage"],
  mounted() {
    this.loop();
  },
  destroyed() {
    console.log("destroyed");
    clearInterval(this.s);
  },
  methods: {
    init() {
      const BUF_SIZE = 86400000;
      let d = new Date().getTime();

      if (this.series[0].data.length >= BUF_SIZE) {
        this.series[0].data.shift();
      }

      this.series[0].data.push([d, this.cpuusage]);
      this.$refs.realtime.updateSeries([
        {
          name: "cpuusage",
          data: this.series[0].data
        }
      ]);
    },

    loop() {
      this.s = setInterval(() => {
        this.init();
      }, 1000);
    }
  },
  data: function() {
    return {
      options: {
        chart: {
          animations: {
            enabled: true,
            easing: "linear",
            dynamicAnimation: {
              speed: 1000
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
          text: "CPU",
          align: "center"
        },
        markers: {
          size: 0
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
        }
      },
      series: [
        {
          name: "cpuusage",
          data: [[new Date().getTime(), 0]]
        }
      ]
    };
  }
};
</script>