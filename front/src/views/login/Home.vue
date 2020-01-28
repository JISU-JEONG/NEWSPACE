<template>
  <div class="home">
    {{name}} | <v-btn dark v-on:click="logout()">로그아웃</v-btn>

  </div>
</template>

<script>
// const storage = localStorage;
const storage = sessionStorage;

export default {
  name: "home",
  props : ["id"],
  data(){
    return {
      name : this.id
    }
  },
  methods:{
    logout() {
      storage.removeItem("login-token", "");
      this.$router.push("/Login");
    }
  }
};
</script>
