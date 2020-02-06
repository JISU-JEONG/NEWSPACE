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
        <div>
          <span class="tagfont" v-for="key in news.keyword.split(' ')" :key="key" @click="onClickKeyword(key)" style="cursor: pointer;" >
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

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');
  .cardbox{
    margin: 0 auto;
    padding: 10px 10px;
    border: solid 1px gray;
    border-radius: 10px;
    box-sizing: border-box;
    cursor: pointer;
    transition: all 0.5s ;
  }
  .tagfont{
    width: 100%;
    font-size: 15px;
    color : #42A5F5;
    margin-top: 5px;
    font-family: 'Noto Sans KR', sans-serif;
    font-style: oblique;
  }
  .tagfont:hover{
    text-decoration: underline;
  }
  .head_font{
    color: black;
    font-family: 'Noto Sans KR', sans-serif;
    width: 100%;
    font-size: 18px;
  }
  .box{
    width: 100%;
    font-size:13px;
    margin-left: 2px;
    font-family: 'Noto Sans KR', sans-serif;
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
    font-size: 18px;
    font-family: 'Noto Sans KR', sans-serif;
    color: #1428A0;
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
    font-size: 18px;
    font-family: 'Noto Sans KR', sans-serif;
    color: #A50034;
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
    font-size: 18px;
    font-family: 'Noto Sans KR', sans-serif;
    color: #DB1026;
  }
</style>