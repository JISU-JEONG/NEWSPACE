<template>
  <v-container>
    <!-- <chat /> -->
    <div class="newsbody">
      <div>
        <h1>{{ news.title }}</h1>
      </div>
      <div class="tagfont d-flex justify-space-between align-end">
        <span>
          <span
            v-for="key in keywords"
            :key="key"
            @click="onClickKeyword(key)"
            style="cursor: pointer;"
          >#{{ key }} </span>
        </span>
        <span v-if="is_like===true && $store.state.token" class="like" @click="like()">
            <v-icon size=40px color="yellow">
              mdi-star
            </v-icon>
        </span>
        <v-menu v-else top offset-y left open-on-click>
          <template v-slot:activator="{ on }">
            <span class="like" @click="like()" v-on="on">
              <v-icon size=40px color="yellow">
                mdi-star-outline
              </v-icon>
            </span>
          </template>
          <v-card>
            <v-container>
              <p>기사 스크랩 기능은   로그인이 필요합니다.<br>로그인 페이지로 이동하시겠습니까?</p>
              <v-btn dark color="blue lighten-2" @click="$router.push({path: '/login'})">이동</v-btn>
              <v-btn dark color="red lighten-2" absolute right >취소</v-btn>
            </v-container>
          </v-card>
        </v-menu>
      </div>
      <hr />
      <br />
      <div v-html="news.body" class="page"></div>
      <hr />
      <CommentForm @commentCreate-event="CommentCreate" />
      <CommentList :comments="comments"></CommentList>
      <v-snackbar
        v-model="snackbar"
        bottom
        right
        color="blue lighten-2 text--white" 
        :timeout=timeout
      >
        {{snackbarInnerText}}
        <v-btn text @click="snackbar=false">
          닫기
        </v-btn>
      </v-snackbar>
    </div>
  </v-container>
</template>

<script>
import axios from "axios";
import CommentList from "../components/CommentList";
import CommentForm from "../components/CommentForm";
import info from "../services/getInfo";
import http from "../services/http-common";
import Chat from "../components/Chat"

export default {
  name: "detail",
  components: {
    CommentList,
    CommentForm,
    Chat
  },
  data() {
    return {
      news: [],
      keywords: [],
      comments: [],
      snackbar: false,
      timeout: 2000,
      snackbarInnerText: '',
      is_like : false,
      searchWord: ''
    };
  },

  methods: {
    CommentCreate(text) {
      if (text !== "") {
        const storage = localStorage;
        const data = {
          news_id: this.$route.params.id,
          comment_text: text
        };
        axios
          .post("http://192.168.31.84:8080/api/comment", data, {
            headers: {
              "login-token": storage.getItem("login-token")
            }
          })
          .then(response => {
            this.snackbar = true
            this.snackbarInnerText = "댓글이 등록되었습니다."
            this.CommentGet();
          })
          .catch(e => {
            console.log(e);
          });
      }
    },
    getNews() {
      const storage = localStorage;
      const token = {
            headers: {
              "login-token": storage.getItem("login-token"),
        }
      }
      axios
        .get(`http://192.168.31.85:8080/api/news/${this.$route.params.id}`,token)
        .then(response => {
          this.news = response.data.news;
          this.keywords = this.news.keyword.split(" ");
          // if(this.searchWord !== ' ' && this.searchWord !== '')
          //   this.news.body =this.news.body.split(`${this.searchWord}`).join('<strong><strong>'+`${this.searchWord}`+'</strong></strong>')
          // this.is_like = response.data.is_like
        })
        .catch(error => {
          console.log(error);
        });
    },
    CommentGet() {
      axios
        .get(`http://192.168.31.85:8080/api/comment/${this.$route.params.id}`)
        .then(response => {
          this.comments = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    onClickKeyword(key) {
      this.$router
        .push({
          name: "search",
          params: { searchValue: key }
        })
        .catch(err => {});
    },
    like(){
      if(this.$store.state.token !== null)
      {
        const storage = localStorage;
        const data = {
          news_id: this.$route.params.id,
        };
        axios
          .post("http://192.168.31.85:8080/api/news", data, {
            headers: {
              "login-token": storage.getItem("login-token")
            }
          })
          .then(response => {
              this.is_like=response.data
          })
          .catch(e => {
            console.log(e);
          });
      }
    },
  },
  beforeMount() {
    am4core.disposeAllCharts();
    info();
  },
  mounted() {
    this.searchWord = this.$route.params.keyword
    this.getNews();
    this.CommentGet();
  },
  watch: {
    '$route': ['getNews', 'CommentGet']
  },
  beforeRouteEnter (to, from, next) {
    if (from.name === 'search') {
      next(vm => {
        vm.searchWord = from.params.searchValue
      })
    } else {
      next()
    }
  }

  };
</script>

<style scoped>
.newsbody {
  box-shadow: 1px 3px 7px grey;
  padding: 3vw 3vw 5vw;
  margin: 50px auto;
  background-color: #fff;
  border-radius: 10px;
}
.tagfont {
  width: 100%;
  font-size: 20px;
  color : #42A5F5;
  margin-bottom: 10px;
  margin-top: 10px;
}
.page {
  padding-bottom: 20px;
}
.page >>> img {
  width: 50vw !important;
  display: block !important;
  margin: 0px auto !important;
}
.page >>> div {
  display: block !important;
  margin: 0px auto !important;
}
.like{
  align-content: right;
  margin-right: 20px;
  cursor: pointer;
}
.page >>> strong > strong{
  color: indigo !important;
  background-color:yellow !important;
  font-size: 18px !important;
}
</style>