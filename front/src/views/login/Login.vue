<template>
  <v-app>
    <h1>This is an Login page</h1>
    <LoginPage></LoginPage>
  </v-app>
</template>

<script>
// @ is an alias to /src
import LoginPage from "@/components/login/LoginPage.vue";

export default {
  name: "login",
  components: {
    LoginPage
  }
};
</script>
