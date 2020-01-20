<template>
  <v-layout mt-5 wrap>
    <v-flex v-for="i in list.length > limits ? limits : list.length" :key="i" xs12 sm6 lg3>
      <v-dialog max-width="75vw">
        <template v-slot:activator="{ on }">
          <button v-on="on">
            <news class="ma-3" :title="list[i - 1].title" :bodytext="list[i - 1].bodytext"></news>
          </button>
        </template>
        <v-card>
          <v-card-title class="headline">{{list[i-1].title}}</v-card-title>
          <v-card-text v-html="list[i-1].body"></v-card-text>
        </v-card>
      </v-dialog>
    </v-flex>
  </v-layout>
</template>
<!--
<v-btn color="green darken-1" flat="flat" @click.native="dialog = false">Close</v-btn>
-->         

<script>
import VClamp from "vue-clamp";
import http from "../http-common";
import news from "@/components/News";

export default {
  name: "NewsList",
  props: {
    limits: { type: Number, default: 8 },
    loadMore: { type: Boolean, default: false }
  },

  methods: {
    NewsList() {
      http
        .get("/getNews")
        .then(response => (this.list = response.data))
        .catch(() => {
          this.errored = true;
        });
    }
  },

  data() {
    return {
      list: [],
      popup: true,
      dialog: false
    };
  },

  components: {
    news,
    VClamp
  },

  mounted() {
    this.NewsList();
  }
};
</script>

<style>
 
.size-full{
  height: auto;
  max-width: 90%;
}

img{
  height: auto;
  max-width: 90%;
}

.srcset{
  height: auto;
  max-width: 90%;  
}

.alignnone{
  height: auto;
  max-width: 90%;
}

iframe{
  height: auto;
  max-width: 90%;
}

</style>