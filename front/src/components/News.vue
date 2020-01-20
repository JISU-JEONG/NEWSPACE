<template>
  <v-card>
    <v-card-title primary-title>
      <div>
        <div class="headline"><v-clamp autoresize :max-lines="1">{{ title }}</v-clamp></div>
        <span class="grey--text"><v-clamp autoresize :max-lines="4">{{ bodytext }}</v-clamp></span>
      </div>
    </v-card-title>
  </v-card>
</template>



<script>
import VClamp from 'vue-clamp'

export default {
   name: 'News',
   props: {
    title : {type: String},
    bodytext : {type: String}
   },
   components: {
      VClamp
   },
}
</script>