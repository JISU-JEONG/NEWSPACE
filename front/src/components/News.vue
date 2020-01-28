<template>
  <div @click="goDetail(news.news_id)" class="cardbox">
        <div class="Samsung_font" v-if="news.brand === 'SAMSUNG'">{{news.brand}}<hr></div>
        <div class="LG_font" v-else-if="news.brand === 'LG'">{{news.brand}}<hr></div>
        <div class="SK_font" v-else>{{news.brand}}<hr></div>
        <div class="head_font">{{news.title}}</div>
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
   props: {
    news : {type: Object}
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
    color : black;
    font-size: 20px;
  }
  .box{
    width: 100%;
    font-size:15px;
    color: gray;
  }
  .Samsung_font{
    width: 100%;
    font-size:30px;
    color:blue;
    margin-bottom: 10px;
  }
  .LG_font{
    width: 100%;
    font-size:30px;
    color: red;
    margin-bottom: 10px;
  }
  .SK_font{
    width: 100%;
    font-size:30px;
    color: orange;
    margin-bottom: 10px;
  }
</style>