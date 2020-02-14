<template>
  <v-container>
      <div class="v-alert v-alert--notification mb-3 info elevation-6" v-for="m in members" :key="m.member_id">
      <div>
          {{m.name}} {{m.email}}
      </div>
    </div>
  </v-container>
</template>

<script>

export default {
  name: "MemberList",
  props: ["members"],
};
</script>