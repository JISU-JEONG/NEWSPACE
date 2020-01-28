<template>
  <div @click="goDetail(id)">
    <v-card class="cardbox" color="#E0E0E0" >
      <v-card-title class="cardfont">
        <v-clamp autoresize :max-lines="2">
          {{title}}
        </v-clamp>
      </v-card-title>
      <v-card-text>
        <v-clamp autoresize :max-lines="10">
          {{bodytext}}
        </v-clamp>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import VClamp from 'vue-clamp'
import router from '../router'

export default {
   name: 'News',
   props: {
    id : {type: Number},
    title : {type: String},
    bodytext : {type: String},
    body : {type: String}
   },
   components: {
      VClamp
   },
   methods: {
     goDetail(id) {
       alert(id);
       router.push({ name: 'detail', params: { id: id }})
        // this.$router.push(`/detail/${id}`)
     }
   }
}
</script>