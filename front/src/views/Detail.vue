<template>
  <div>
    <v-container class="newsbody">
      <div>
        <h1>{{ news.title }}</h1>
      </div>
      <div class="tagfont">
        <!-- for i in keywords.length에서 바꿨읍니다. -->
        <span v-for="key in keywords" :key="key" @click="onClickKeyword(key)" style="cursor: pointer;">
          #{{ key }}
        </span>
      </div>
      <hr>
      <br>
      <div v-html="news.body" class="page"></div>
      <hr>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios'

export default {
    name : "detail",
    data(){
        return {
            news:[],
            keywords:[],
        }
    },
    methods: {
      getNews() {
        axios.get(`http://192.168.31.85:8080/getNews/${this.$route.params.id}`)
          .then(response => {
            this.news = response.data
            this.keywords = this.news.keyword.split(' ')
          })
            .catch(error => {
              console.log(error)
            })

      },
      onClickKeyword(key){
        this.$router.push({ 
        name: 'search',
        params: { searchValue: key }
        }).catch(err =>{})
      },
    },
    mounted(){
        this.getNews()
    }
}
</script>

<style scoped>
.newsbody{
  border: solid 2px black;
  padding : 3vw 3vw;
  margin: 50px auto;
  background-color : #FFF;
  border-radius: 50px;
}
.tagfont{
  font-size: 20px;
  color : blue;
  margin-bottom: 10px;
  margin-top: 10px;
}
.page{
  padding-bottom: 20px;
}
.page >>> img { 
  width:50vw !important;
  display: block !important;
  margin: 0px auto !important; 
  }

</style>