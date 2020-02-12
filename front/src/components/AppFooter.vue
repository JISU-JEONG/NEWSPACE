<template>
  <v-footer
      dark
      padless
      class="px-5 py-5"
      style="z-index:10"
      v-if="$route.name !== 'AboutUs'"
    >
    <router-link :to="{ name: 'AboutUs'}">ABOUT US</router-link>
      <v-spacer></v-spacer>
      <span >
        <v-icon size="20px">mdi-alpha-c-circle-outline</v-icon> {{ new Date().getFullYear() }} <strong>AMT</strong>, Made with <v-icon size="24px">mdi-heart</v-icon> for a better Web
      </span>
    </v-footer>
</template>

<script>

export default {
  name: "appfooter",
}
</script>

<style>
</style>