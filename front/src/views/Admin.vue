<template>
  <div>
    <apexchart
      ref="realtimeChart1"
      type="line"
      height="350"
      :options="chartOptions"
      :series="series1"
    />
  </div>
</template>

<script>
// @ is an alias to /src
import VueApexCharts from "vue-apexcharts";

var lastDate = 0;
var data1 = [];
var data2 = [];

function getDayWiseTimeSeries(baseval, count, yrange) {
  var i = 0;
  while (i < count) {
    var x = baseval;
    var y =
      yrange.max - yrange.min + 1;
    data1.push({
      x,
      y
    });
    data2.push({
      x,
      y
    });
    lastDate = baseval;
    baseval += 86400000;
    i++;
  }
}

getDayWiseTimeSeries(new Date("11 Feb 2017 GMT").getTime(), 10, {
  min: 10,
  max: 90
});

function getNewSeries(baseval, yrange) {
  var newDate = baseval + 86400000;
  lastDate = newDate;
  data1.push({
    x: newDate,
    y: yrange.max - yrange.min
  });
}

function resetData() {
  data1 = data1.slice(data1.length - 10, data1.length);
}

export default {
  name: "home",
  components: {
    apexchart: VueApexCharts
  },
  data() {
    return {
      series1: [
        {
          data: data1.slice()
        }
      ],
      chartOptions: {
        chart: {
          animations: {
            enabled: true,
            easing: "linear",
            dynamicAnimation: {
              speed: 980
            }
          },
          toolbar: {
            show: false
          },
          zoom: {
            enabled: false
          },
          foreColor: "#fff"
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: "smooth"
        },

        title: {
          text: "Dynamic Updating Chart",
          align: "left"
        },
        markers: {
          size: 0
        },
        xaxis: {
          type: "datetime",
          range: 777600000,
          labels: {
            show: true
            // formatter: value => {
            //   return this.$moment.unix(value).format("HH:mm:ss");
            // }
          }
        },
        yaxis: {},
        legend: {
          show: false
        }
      }
    };
  },
  mounted() {
    this.intervals1();
  },
  methods: {
    intervals1: function() {
      var me = this;
      window.setInterval(function() {
        getNewSeries(lastDate, {
          min: 10,
          max: 90
        });
        console.log(data1);
        me.$refs.realtimeChart1.updateSeries([
          {
            data: data1
          }
        ]);
      }, 1000);

      // every 60 seconds, we reset the data to prevent memory leaks
      window.setInterval(function() {
        resetData();
        me.$refs.realtimeChart1.updateSeries(
          [
            {
              data: []
            }
          ],
          false,
          true
        );
      }, 60000);
    }
  }
};
</script>