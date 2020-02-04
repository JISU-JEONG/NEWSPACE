<template>
  <div>
    <v-container class="newsbody">
      <div>
        <h1>{{ news.title }}</h1>
      </div>
      <div class="tagfont">
        <!-- for i in keywords.length에서 바꿨읍니다. -->
        <span
          v-for="key in keywords"
          :key="key"
          @click="onClickKeyword(key)"
          style="cursor: pointer;"
        >#{{ key }}</span>
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
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import CommentList from "../components/CommentList";
import CommentForm from "../components/CommentForm";
import info from "../services/getInfo";
export default {
  name: "detail",
  data() {
    return {
      news: [],
      keywords: [],
      comments: [],
      snackbar: false,
      timeout: 2000,
      snackbarInnerText: '',
    };
  },
  components: {
    CommentList,
    CommentForm
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
          .post("http://52.79.249.4:8080/api/comment", data, {
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
      axios
        .get(`http://52.79.249.4:8080/api/getNews/${this.$route.params.id}`)
        .then(response => {
          this.news = response.data;
          this.keywords = this.news.keyword.split(" ");
        })
        .catch(error => {
          console.log(error);
        });
    },
    CommentGet() {
      axios
        .get(`http://52.79.249.4:8080/api/comment/${this.$route.params.id}`)
        .then(response => {
          this.comments = response.data;
          console.log(this.comments);
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
    }
  },
  beforeMount() {
    info();
  },
  mounted() {
    this.getNews();
    this.CommentGet();

    console.log(localStorage.getItem("member_id"));
  }
};
</script>

<style scoped>
.newsbody {
  border: solid 2px black;
  padding: 3vw 3vw 5vw;
  margin: 50px auto;
  background-color: #fff;
  border-radius: 50px;
}
.tagfont {
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
</style>