<template>
  <div>
    <v-btn to="/">
      <span>Home</span>
    </v-btn>

    <v-btn @click.stop="changeDrawer()" class="ml-3">
      사이드바(로그인,선택뉴스  )
    </v-btn> 
  </div>
</template>
<script>

import {CHANGE_DRAWER} from '../store'

export default {
  name: "Navigation",
  methods: {
    changeDrawer() {
      this.$store.commit(CHANGE_DRAWER)
    }
  }

}
</script>

<style>

</style>