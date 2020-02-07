<template>
  <v-app>
    <Navigation />
    <v-content class="background">
      <router-view />
    </v-content>
    <chat />
    <AppFooter />
  </v-app>
</template>

<script>
import "@mdi/font/css/materialdesignicons.css";
import Navigation from "./components/Navigation";
import AppFooter from "./components/AppFooter";
import Chat from "./components/Chat"

export default {
  name: "App",
  data() {
    return {
      //
    };
  },
  icons: {
    iconfont: "mdi" // default - only for display purposes
  },
  components: {
    Navigation,
    AppFooter,
    Chat
  }
};
</script>

<style>
.header {
  position: fixed;
  left: 0;
  top: 0;
  width: 100vw;
  z-index: 200;
}
.background {
  background-color: white;
}

</style>