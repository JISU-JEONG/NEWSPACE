<template>
  <div>
    <v-container>
      <v-row>
      <div class="SamsungFont">SAMSUNG NEWS</div>
        <carousel :perPageCustom="[[0,1],[600,3],[960, 4],[1264,5]]">
          <slide v-for = "i in list.length > limits ? limits : list.length" :key="i">
            <News
            :title="list[i-1].title"
            :bodytext="list[i-1].bodytext"
            :body = "list[i-1].body"
            :id = "list[i-1].number">
            </News>
          </slide>
        </carousel>
      </v-row>
    </v-container>
  </div>
</template>
<!--
<v-btn color="green darken-1" flat="flat" @click.native="dialog = false">Close</v-btn>
-->         

<script>
import VClamp from "vue-clamp";
import http from "../http-common";
import news from "@/components/News";
import { Carousel, Slide } from 'vue-carousel';
import json from "@/components/getNews"
import News from "./News"
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
      list: json,
      popup: true,
      dialog: false
    };
  },

  components: {
    News,
    VClamp,
    Carousel,
    Slide
  },

  mounted() {
    // this.NewsList();
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
.SamsungFont{
  font-size: 5vw;
  margin-bottom: 1vw;
  color: blue;
}

iframe{
  height: auto;
  max-width: 90%;
}
.cardbox{
  color: #E0E0E0;
}
.cardtext{
  font-size: 1vw;
  height: 1vw;
}
</style>