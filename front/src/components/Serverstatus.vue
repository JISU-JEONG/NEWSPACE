<template>
  <v-container>
    {{cpuidle}}
  </v-container>
</template>

<script>
import VueApexCharts from "vue-apexcharts";
import axios from "axios";

export default {
  name: "Serverstatus",
  methods: {
    
  },
    props:[
    'cpuidle'
  ],
  data: function() {
    return {
      Samsung : false,
      LG : false,
      SK : false
    };
  }
};
</script>