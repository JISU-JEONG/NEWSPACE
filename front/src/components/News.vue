<template>
  <v-container>
      <div class="cardbox" :class="news.brand" @click="onClickNews($event, news)">
        <v-row>
          <v-col
          sm=12
          md=8
          >
            <div>
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
              <span class="tagfont" v-for="key in news.keyword.split(' ')" :key="key" style="cursor: pointer;" :data-key="key">
                #{{key}}
              </span>
            </div>
          </v-col>
          <v-col
          sm=12
          md=4 
          class="imgbox"
          >
            <v-img
              :src="news.thumb"
            ></v-img>
          </v-col>
        </v-row>
      </div>
  </v-container>
</template>

<script>
import router from '../router'

export default {
  name: 'News',
  props: {
    company : { type: String },
    news : { type: Object},
    keyword : { type: String, default: '' },
  },
  data() {
    return {
    }
  },
  methods: {  
    onClickNews(event, news) {
      if (event.target.classList.contains('tagfont')) {  // news 클릭시
        this.onClickKeyword(event.target.dataset.key) // tag 이면 검색으로
      } else {
        if(this.keyword==='')
        {
          this.goDetail(news.news_id,' ') // 그 외에는 상세보기로
        }
        else{
          this.goDetail(news.news_id, this.keyword) // 그 외에는 상세보기로
        }
      }
    },
    goDetail(id, keyword) {
      router.push({ name: 'detail', params: { "id": id, "keyword": keyword }})
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
    margin: 0px auto;
    padding: 10px 10px;
    box-shadow: 1px 2px 7px lightgray;
    /* border-radius: 10px; */
    /* background-color: #FFFFFF; */
    box-sizing: border-box;
    cursor: pointer;
    border-radius: 7px;
    transition: 300ms linear;
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
  .cardbox:hover {
    box-shadow: 1px 2px 4px gray;
  }
  .SAMSUNG .brand-title {
    width: 100%;
    font-size: 18px;
    font-family: 'Noto Sans KR', sans-serif;
    color: #1428A0;
  }
  .LG .brand-title{
    width: 100%;
    font-size: 18px;
    font-family: 'Noto Sans KR', sans-serif;
    color: #A50034;
  }
  .SK .brand-title{
    width: 100%;
    font-size: 18px;
    font-family: 'Noto Sans KR', sans-serif;
    color: #DB1026;
  }
  v-img{
    display: block;
    align-content: center;
    padding-bottom: 0px;
  }
  .imgbox{
    padding: 12px 10px;
  }
</style>