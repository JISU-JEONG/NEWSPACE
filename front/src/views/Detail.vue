<template>
  <div>
    <v-container class="newsbody">
      <div>
        <h1>{{ news.title }}</h1>
      </div>
      <div class="tagfont">
        <span v-for="i in keywords.length" :key="i">
          #{{ keywords[i-1]+' ' }}
        </span>
      </div>
      <hr>
      <br>
      <div v-html="news.body" class="page"></div>
      <hr> 
      <CommentForm @commentCreate-event="CommentCreate"/>
      <CommentList
      :comments=comments
      ></CommentList>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios'
import CommentList from '../components/CommentList'
import CommentForm from '../components/CommentForm'

export default {
    name : "detail",
    data(){
        return {
            news:[],
            keywords:[],
            comments:[],
            member_name: '',
        }
    },
    components: {
      CommentList,
      CommentForm,
	  },
    methods: {
          info(){
            const storage = localStorage;
          axios.post("http://192.168.31.85:8080/info",
                    {},
                    {
                      headers: {
                        "login-token": storage.getItem("login-token")
                      }
                    }
                  )
                .then(res => {
                    this.member_name = res.data.data.name;
                  })
                  .catch(e => {
                    console.log(e);
                  });
        },
        CommentCreate(text){
          if(text!==''){
            const storage = localStorage;
            const data ={
                news_id: this.$route.params.id,
                comment_text: text,
                member_name:this.member_name,
              }
            axios.post("http://192.168.31.85:8080/addComment", data,
              {
                headers: {
                  "login-token": storage.getItem("login-token")
                }
              })
              .then(response => {
                this.CommentGet()
              })
              .catch(e => {
                console.log(e);
              });
          }
        },
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
        CommentGet() {
          axios.get(`http://192.168.31.85:8080/getComment/${this.$route.params.id}`)
                .then(response => {
                    this.comments = response.data
                    console.log(this.comments)
                })
                .catch(error => {
                    console.log(error)
                })
        }
    },
    mounted(){
        this.getNews(),
        this.CommentGet(),
        this.info()
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