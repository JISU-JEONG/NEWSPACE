<template>
  <div>
    <apexcharts ref="realtime" type="line" height="300" :options="options" :series="series"></apexcharts>
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
    init() {
      const BUF_SIZE = 86400000;
      let d = new Date().getTime();

      if (this.series[0].data.length >= BUF_SIZE) {
        this.series[0].data.shift();
        this.series[1].data.shift();
      }

      this.series[0].data.push([d, this.totalmemory - this.freememory]);
      this.series[1].data.push([d, this.totalmemory]);
      this.$refs.realtime.updateSeries([
        {
          name: "freememory",
          data: this.series[0].data
        },
        { name: "totalmemory", data: this.series[1].data }
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
          text: "Memory",
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
          max: 16384
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
          name: "freememory",
          data: [[new Date().getTime(), 0]]
        },
        {
          name: "totalmemory",
          data: [[new Date().getTime(), 0]]
        }
      ]
    };
  }
};
</script>