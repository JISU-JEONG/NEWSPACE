<template>
  <v-container>
      <div class="cardbox" :class="news.brand">
        <div  @click="goDetail(news.news_id)">
          <div class="brand-title">
            <span v-if="news.brand === 'LG'">{{news.brand}} Electronics</span>
            <span v-else-if="news.brand === 'SK'">{{news.brand}} Hynix</span>
            <span v-else>{{news.brand}}</span>
          </div>
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
  .tagfont{
  width: 100%;
  font-size: 18px;
  color : #42A5F5;
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
  .SAMSUNG:hover {
    border: solid 2px #1428A0;
    box-shadow: 1px 1px 3px 0.1px #1428A0;
    box-sizing: border-box;
    padding: 9px 19px;
    transform: scale(1.05);    
  }
  .SAMSUNG .brand-title {
    width: 100%;
    font-size:30px;
    color: #1428A0;
    margin-bottom: 10px;
  }
  .LG:hover {
    border: solid 2px #A50034;
    box-shadow: 1px 1px 3px 0.1px #A50034;
    box-sizing: border-box;
    padding: 9px 19px;
    transform: scale(1.05);    
  }
  .LG .brand-title{
    width: 100%;
    font-size:30px;
    color: #A50034;
    margin-bottom: 10px;
  }
  .SK:hover {
    border: solid 2px #DB1026;
    box-shadow: 1px 1px 3px 0.1px #DB1026;
    box-sizing: border-box;
    padding: 9px 19px;
    transform: scale(1.05);    
  }
  .SK .brand-title{
    width: 100%;
    font-size:30px;
    color: #DB1026;
    margin-bottom: 10px;
  }
</style>