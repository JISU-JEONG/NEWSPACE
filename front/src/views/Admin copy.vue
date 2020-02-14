<template>
  <div>
    <apexchart
      ref="realtimeChart"
      type="line"
      height="650"
      :options="chartOptions"
      :series="series"
    />
    <div id="chart">

    </div>
  </div>
</template>

<script>
import VueApexCharts from "vue-apexcharts";
import axios from "axios";

var lastDate = 0;
var data = [];
var data2 = [];

//처음 차트뛰울때
function getDayWiseTimeSeries(baseval, count, yrange) {
  var i = 0;
  while (i < count) {
    var x = baseval;

    var y = 10;

    data.push({
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

//계속 바뀌는
function getNewSeries(baseval, yrange) {
  var newDate = baseval + 86400000;
  var cpu = 0;
  var memory = 0;
  lastDate = newDate;
  var _promise = function() {
    return new Promise(function(resolve, reject) {
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
          cpu = response.data.cpuusage;
          memory = response.data.freememory;
          resolve("ㄲ");
        })
        .catch(error => {
          console.log(error);
        });
    });
  };
  _promise().then(() => {
    console.log(cpu);
    console.log(memory);

    data.push({
      x: newDate,
      y: cpu
    });
    data2.push({
      x: newDate,
      y: memory
    });
  });
}

function resetData() {
  data = data.slice(data.length - 10, data.length);
  data2 = data2.slice(data2.length - 10, data2.length);
}

export default {
  name: "home",
  components: {
    apexchart: VueApexCharts
  },
  data() {
    return {
      series: [
        {
          name: 'RX',
          data: data.slice()
        },
        {
          name: 'TX',
          data: data2.slice()
        }
      ],
      chartOptions: {
        chart: {
          animations: {
            enabled: true,
            easing: "linear",
            dynamicAnimation: {
              speed: 500
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
          enabled: true
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
          type: 'datetime',
          labels: {
            format: "HH:mm:ss"
          },
          range: 777600000
        },
        yaxis: {
          max: 50
        },
        legend: {
          show: false
        }
      }
    };
  },
  mounted() {
    this.intervals();
  },
  methods: {
    intervals: function() {
      var me = this;
      window.setInterval(function() {
        getNewSeries(lastDate, {
          min: 10,
          max: 90
        });

        me.$refs.realtimeChart.updateSeries([
          {
            data: data
          },
          {
            data: data2
          }
        ]);
      }, 500);

      window.setInterval(function() {
        resetData();
        me.$refs.realtimeChart.updateSeries(
          [
            {
              data
            },
            {
              data2
            }
          ],
          false,
          true
        );
      }, 60000);
    }
  },
  destroyed() {
    console.log("destroyed");
    clearInterval(this.intervals);
  }
};
</script>