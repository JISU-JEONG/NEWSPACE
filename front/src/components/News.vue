<template>
  <v-container>
      <div class="cardbox">
        <div  @click="goDetail(news.news_id)">
          <div class="Samsung_font" v-if="news.brand === 'SAMSUNG'" >{{news.brand}}<hr></div>
          <div class="LG_font" v-else-if="news.brand === 'LG'">{{news.brand}} Electronics<hr></div>
          <div class="SK_font" v-else>{{news.brand}} Hynix<hr></div>
          <div class="head_font">{{news.title}}</div>
          <div class="box">
            {{news.date}}
        </div>
        </div>
        <div class="tagfont">
          <span v-for="key in news.keyword.split(' ')" :key="key" @click="onClickKeyword(key)" style="cursor: pointer;" >
            #{{key}}
          </span>
        </div>
      </div>
      
  </v-container>
</template>

<script>
import VClamp from 'vue-clamp'
import router from '../router'

export default {
  name: 'News',
  data() {
    return {
    }
  },
  props: {
    news : {type: Object},
    company : {type: String}
  },
  components: {
    VClamp
  },
  methods: {
    goDetail(id) {
      router.push({ name: 'detail', params: { id: id }})
    },
    onClickKeyword(key){
      this.$router.push({ 
        name: 'search',
        params: { searchValue: key }
      }).catch(err =>{})
    },
   },
}
</script>

<style>
  .cardbox{
    padding: 10px 20px;
    border: solid 1px gray;
    border-radius: 10px;
    box-sizing: border-box;
    cursor: pointer;
    transition: all 0.5s ;
  }
  .cardbox:hover {
    border: solid 2px rgb(4, 0, 255);
    box-sizing: border-box;
    padding: 9px 19px;
    transform: scale(1.05);
  }
  .tagfont{
  width: 100%;
  font-size: 18px;
  color : blue;
  margin-top: 10px;
  }
  .head_font{
    color: black;
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