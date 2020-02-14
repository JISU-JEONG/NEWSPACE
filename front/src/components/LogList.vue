<template>
  <v-container>
    <div class="v-alert v-alert--notification mb-3 info elevation-6" v-for="l in logs" :key="l.log_id">
      <div>
        {{l.log_content}}
        <br />
        {{l.log_date}}
      </div>
    </div>
  </v-container>
</template>

<script>

export default {
  name: "LogList",
  props: ["logs"],
};
</script>