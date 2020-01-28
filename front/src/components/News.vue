<template>
  <div class="cardbox">
        <div class="Samsung_font" v-if="news.brand === 'SAMSUNG'" >{{news.brand}}<hr></div>
        <div class="LG_font" v-else-if="news.brand === 'LG'">{{news.brand}} Electronics<hr></div>
        <div class="SK_font" v-else>{{news.brand}} Hynix<hr></div>
        <div class="head_font" v-bind:style="mystyle" @click="goDetail(news.news_id)" v-on:mouseover = "changebgcolor" v-on:mouseout = "originalcolor">{{news.title}}</div>
        <div class="box">
          {{news.date}}
        </div>
        <div class="tagfont">
          <span v-for="key in news.keyword.split(' ')" :key="key">
            #{{key}}
          </span>
        </div>
  </div>
</template>

<script>
import VClamp from 'vue-clamp'
import router from '../router'

export default {
   name: 'News',
   data() {
     return{
      mystyle: {
        color:"black",
        fontSize: 25,
      },
     }
  },
   props: {
    news : {type: Object},
   },
   components: {
      VClamp
   },
   methods: {
     goDetail(id) {
       alert(id);
       router.push({ name: 'detail', params: { id: id }})
        // this.$router.push(`/detail/${id}`)
     },
     changebgcolor: function() {
      this.mystyle.color = "grey";
      this.mystyle.fontSize = "30px";
    },
    originalcolor: function() {
      this.mystyle.color = "black";
      this.mystyle.fontSize = "25px";
    }
   },
   
}
</script>

<style>
  .cardbox{
    margin: 30px 30px;
    padding: 10px 20px;
    border: solid 1px gray;
    border-radius: 10px;
  }
  .tagfont{
  width: 100%;
  font-size: 18px;
  color : blue;
  margin-top: 10px;
  }
  .head_font{
    width: 100%;
    font-size: 25px;
  }
  .box{
    width: 100%;
    font-size:15px;
    color: gray;
  }
  .Samsung_font{
    width: 100%;
    font-size:30px;
    color: #1428A0;
    margin-bottom: 10px;
  }
  .LG_font{
    width: 100%;
    font-size:30px;
    color: #A50034;
    margin-bottom: 10px;
  }
  .SK_font{
    width: 100%;
    font-size:30px;
    color: #DB1026;
    margin-bottom: 10px;
  }
</style>