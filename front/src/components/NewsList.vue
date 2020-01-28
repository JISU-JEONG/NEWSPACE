<template>
  <v-container class="main_web">
      <v-tabs
        v-model="tab"
        background-color="transparent"
        grow
      >
        <v-tab>
          all
        </v-tab>
        <v-tab>
          Samsung
        </v-tab>
        <v-tab>
          LG
        </v-tab>
        <v-tab>
          SK
        </v-tab>
      </v-tabs>
  
      <v-tabs-items v-model="tab">
        <v-tab-item>
          <div class="box"
            v-for="i in AllList.length" :key="i"
          >
            <News
              :news = AllList[i-1]
            >
            </News>
          </div>
        </v-tab-item>
        <v-tab-item>
          <div class="box"
            v-for="i in Samsunglist.length" :key="i"
          >
            <News
              :news = Samsunglist[i-1]
            >
            </News>
          </div>
        </v-tab-item>
        <v-tab-item>
          <div class="box"
            v-for="i in Samsunglist.length" :key="i"
          >
            <News
              :news = LGlist[i-1]
            >
            </News>
          </div>
        </v-tab-item>
        <v-tab-item>
          <div class="box"
            v-for="i in Samsunglist.length" :key="i"
          >
            <News
              :news = SKlist[i-1]
            >
            </News>
          </div>
        </v-tab-item>
      </v-tabs-items>
  </v-container>
</template>      

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
    SamsungRecentNewsList() {
      http
        .get("/getSamsungRecent")
        .then(response => (this.Samsunglist = response.data))
        .catch(() => {
          this.errored = true;
        });
    },
    LgRecentNewsList() {
      http
        .get("/getLgRecent")
        .then(response => (this.LGlist = response.data))
        .catch(() => {
          this.errored = true;
        });
    },
    SkRecentNewsList() {
      http
        .get("/getSkRecent")
        .then(response => (this.SKlist = response.data))
        .catch(() => {
          this.errored = true;
        });
    },
    getAllNewsRecent(){
      http
        .get("/getAllNewsRecent")
        .then(response => (this.AllList = response.data))
        .catch(() => {
          this.errored = true;
        });
    }
  },

  data() {
    return {
      Samsunglist: [],
      LGlist: [],
      SKlist: [],
      AllList:[],
      tab: null,
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
    this.SamsungRecentNewsList();
    this.LgRecentNewsList();
    this.SkRecentNewsList();
    this.getAllNewsRecent();
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
  font-size: 30px;
  margin-bottom: 1vw;
  color: blue;
  width: 100%;
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
.main_web{
  background-color: white;
}
</style>