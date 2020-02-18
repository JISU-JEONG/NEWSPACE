<template>
  <div class="mx-5">
    <div v-for="l in logs" :key="l.log_id">
      <div class="datetext">
        {{l.log_date}}
      </div>
      <div class="maintext">
        - {{l.log_content}}
      </div>
      <br>
    </div>
  </div>
</template>

<script>

export default {
  name: "LogList",
  props: ["logs"],
  methods:{
  },
  mounted(){
  }
};
</script>
<style>
.logtext{
  display: inline-block;
}
.datetext{
  color: rgb(0, 194, 0);
}
.maintext{
  color:white;
}
</style>