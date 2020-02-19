<template>
<div>
  <div class="header_box">
      <div class="header_name">"{{user.member.name}}"님 환영합니다.</div>
      <div class="header_email" v-if="isemailcheck !== 'true'">이메일 인증을 완료하여 관심키워드 뉴스를 받아보세요</div>
      <div class="header_email">
        <v-btn text v-if="isemailcheck !== 'true'" @click="emailcheck()">이메일 인증하기</v-btn>
        <span v-else class="d-flex align-center justify-center"><v-icon color="green">mdi-check-decagram</v-icon>이메일 인증 완료</span>
        <v-snackbar
          v-model="snackbar"
          bottom
          right
          color="blue lighten-2 text--white" 
          :timeout=timeout
        >
          인증메일이 전송되었습니다.
          <v-btn text @click="snackbar=false">
            닫기
          </v-btn>
        </v-snackbar>
      </div>
      <div class="comment_font">
        <div>
          <h1>{{user.count}}</h1>
        </div>
      <div>
        <p>comments</p>
      </div>
    </div>
  </div>
  <v-container class="px-1" style="min-height:850px">
      <v-tabs
        :vertical=is_size
      >
        <v-tab>
          관심 뉴스
        </v-tab>
        <v-tab>
          최근 본 뉴스
        </v-tab>
        <v-tab>
          관심 키워드
        </v-tab>
  
        
      <v-tab-item class="px-2">
          <v-container fluid>
            <div style="min-height:800px">

            <div class="likeheader">
              <strong>{{user.member.name}}'s NEWS ROOM</strong>
              <hr>
            </div>
            <v-row v-if="like_list.length !== 0">
              <v-col
                v-for="i in like_list[page-1].length"
                :key="i"
                @click="goDetail(like_list[page-1][i-1].news_id)"
                xs=12
                sm=5
                md=4
                lg=3>
                <v-card
                  height=350px
                  class="cardhover"
                >
                  <v-img
                    :src="like_list[page-1][i-1].thumb"
                    height=150px
                  ></v-img>

                  <v-card-title>
                  <h4  v-line-clamp="2">
                    {{like_list[page-1][i-1].title}}
                  </h4>
                  </v-card-title>

                  <v-card-subtitle>
                  <p  v-line-clamp="5">
                    {{like_list[page-1][i-1].bodytext}}
                  </p>
                  </v-card-subtitle>
                </v-card>
              </v-col>
            </v-row>
            </div>
            <div class="text-center">
            <v-pagination
              v-model="page"
              :length=pagetotal
              circle
              total-visible="6"
            >
            </v-pagination>
          </div>
          </v-container>
        </v-tab-item>
        <v-tab-item class="px-2">
          <v-container fluid >
            <div style="min-height:800px">
            <div class="likeheader">
              <strong>{{user.member.name}}님이 최근 보신 뉴스</strong>
              <hr>
            </div>
            <v-row v-if="recent.length !== 0">
              <v-col
                v-for="i in recent.length"
                :key="i"
                @click="goDetail(recent[i-1].news_id)"
                xs=12
                sm=5
                md=4
                lg=3>
                <v-card
                  height=350px
                  class="cardhover"
                >
                  <v-img
                    :src="recent[i-1].thumb"
                    height=150px
                  ></v-img>

                  <v-card-title>
                  <h4  v-line-clamp="2">
                    {{recent[i-1].title}}
                  </h4>
                  </v-card-title>

                  <v-card-subtitle>
                  <p v-line-clamp="5">
                    {{recent[i-1].bodytext}}
                  </p>
                  </v-card-subtitle>
                </v-card>
              </v-col>
            </v-row>
            </div>
          </v-container>
        </v-tab-item>
        <v-tab-item>
                
          <v-container> 
            <div class="likeheader">
                <strong>{{user.member.name}}님 관심 키워드</strong> <v-btn text class="updatedfont" @click.stop="dialog = true">수정</v-btn>
                <hr>
              </div>
            <Treechart
              :keyword="selectedKeywords"
              />
              
          <v-dialog v-model="dialog" max-width="400">
            <v-card>
              <v-container style="min-height:430px;">
                <v-card-title>관심 키워드 수정</v-card-title>
                <v-divider></v-divider>
                <v-card style="min-height:120px;">
                  <span class="mx-3">선택된 키워드</span>
                  <v-divider></v-divider>
                  <v-container>
                    <ul @click.stop="unselectKeyword">
                      <transition-group name="list">
                        <li
                          v-for="keyword in selectedKeywords"
                          :key="keyword"
                          class="text--darken-3"
                        >{{keyword}}</li>
                      </transition-group>
                    </ul>
                  </v-container>
                </v-card>
                <hr />
                <v-card style="min-height:120px;" class="mt-3">
                  <span class="mx-3">추천 키워드</span>
                  <br />
                  <v-divider></v-divider>
                  <v-container>
                    <ul @click.stop="selectKeyword">
                      <transition-group name="list" id="selectedSpan">
                        <li v-for="keyword in unselectedKeywords" :key="keyword">{{keyword}}</li>
                      </transition-group>
                    </ul>
                  </v-container>
                </v-card>
                <v-text-field
                  outlined
                  style="margin-top:16px;"
                  label="추가하고싶은 키워드"
                  v-model="userInputKeyword"
                  append-icon="mdi-plus"
                  @click:append="userInputKeywordToList"
                  @keyup.enter.stop="userInputKeywordToList"
                  :error="error"
                  :error-messages="errorMessages"
                  @input="inputKeyword"
                />
              </v-container>
              <v-card-actions>
                <v-spacer></v-spacer>

                <v-btn color="green darken-1" text @click.stop="OkClick">ok</v-btn>

                <v-btn color="green darken-1" text @click.stop="cancleClick">Cancle</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          </v-container>
        </v-tab-item>
      </v-tabs>
  </v-container>
</div>
</template>
<script>
import http from "../services/http-common.js";
import router from "../router";
import info from "../services/getInfo";
import Treechart from '../components/Treechart'
import axios from "axios";

export default {
  name: "Profile",
  components:{
    Treechart,
  },
  data() {
    return {
      user: {
        list: [],
        member: "",
        count: 0,
      },
      snackbar: false,
      timeout: 2000,
      dialog: false,
      userInputKeyword: "",
      originselectedKeywords: [],
      originunselectedKeywords:[],
      selectedKeywords: [],
      unselectedKeywords: [],
      error: false,
      errorMessages: "",
      isemailcheck: "",
      page: 1,
      like_list : [],
      pagetotal : 0,
      recent: [],
    };
  },
  methods: {
    get_user() {
      const token = {
        headers: {
          "login-token": localStorage.getItem("login-token")
        }
      };
      http
        .post(
          "/profile/",
          {
            member_id: localStorage.getItem("member_id")
          },
          token
        )
        .then(response => {
          this.user = response.data;
          this.selectedKeywords = this.user.member.keyword.split(" ");
          this.originselectedKeywords = this.user.member.keyword.split(" ");
          this.isemailcheck = localStorage.getItem("certifiedkey");
          this.recent = this.user.recentlist
          this.keywordSetting();
          let newslist = []
          if( this.user.list !==[]){
            for(let i=0;i<this.user.list.length;i++)
            {
              newslist.push(this.user.list[i])
              if (newslist.length===8)
              {
                this.like_list.push(newslist)
                newslist = []
              }
            }
            if (newslist.length!==0)
            {
              this.like_list.push(newslist)
            }
            this.pagetotal = this.like_list.length
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    emailcheck() {
      this.snackbar = true
      const token = {
        headers: {
          "login-token": localStorage.getItem("login-token")
        }
      };
      http
        .get("/sendmail/", token)
        .then(response => {
        })
        .catch(error => {
          console.log(error);
        });
    },
    goDetail(id) {
      router.push({ name: "detail", params: { id: id, keyword: ' ' } });
    },
    onClickKeyword(key) {
      this.$router
        .push({
          name: "search",
          params: { searchValue: key }
        })
        .catch(err => {});
    },
    userInputKeywordToList() {
      let errorKeyword = "";
      if (this.userInputKeyword.length < 2) {
        this.error = true;
        this.errorMessages = "두글자 이상을 입력해주세요";
      } else {
        this.userInputKeyword.split(" ").forEach(value => {
          if (
            this.selectedKeywords.findIndex(v => v === value) < 0 &&
            value.length > 1
          ) {
            this.selectedKeywords.push(value);
          } else {
            errorKeyword = errorKeyword.concat(` ${value}`);
          }
        });
        errorKeyword = errorKeyword.trim();
        if (errorKeyword) {
          this.error = true;
          this.errorMessages = `${errorKeyword}은(는) 이미 추가되었거나 너무 짧은 단어입니다.`;
        }
      }
      this.userInputKeyword = "";
    },
    inputKeyword(value) {
      if (value.length) {
        this.error = false;
        this.errorMessages = "";
      }
    },
    unselectKeyword(event) {
      if (event.target.tagName === "LI") {
        const keyword = event.target.innerText;
        const index = this.selectedKeywords.findIndex(v => v === keyword);
        if (this.unselectedKeywords.findIndex(v => v === keyword) < 0) {
          this.unselectedKeywords.push(keyword);
        }
        this.selectedKeywords.splice(index, 1);
      }
    },
    selectKeyword(event) {
      if (event.target.tagName === "LI") {
        const keyword = event.target.innerText;
        const index = this.unselectedKeywords.findIndex(v => v === keyword);
        if (this.selectedKeywords.findIndex(v => v === keyword) < 0) {
          this.selectedKeywords.push(keyword);
        }
        this.unselectedKeywords.splice(index, 1);
      }
    },
    keywordSetting() {
      http.get("/getUserKeyword").then(response => {
        this.unselectedKeywords = response.data;
        for (let i = 0; i<this.selectedKeywords.length; i++) {
            const keyword = this.selectedKeywords[i];
            const index = this.unselectedKeywords.findIndex(v => v === keyword);
            if (index >= 0) {
              this.unselectedKeywords.splice(index, 1);
            }
        }
        this.originunselectedKeywords = this.unselectedKeywords.slice();
      })
      .catch(error => {
          console.log(error);
      })
    },
    OkClick() {
      const token = {
        headers: {
          "login-token": localStorage.getItem("login-token")
        }
      };
      const options = {
        name: this.user.member.name,
        inputkeyword: this.selectedKeywords
      };
      http
        .post("/profileupdate", options, token)
        .then(response => {
          this.userInputKeyword = "";
          this.dialog = false;
          this.user.member = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    cancleClick() {
      this.selectedKeywords = this.originselectedKeywords.slice()
      this.unselectedKeywords = this.originunselectedKeywords.slice()
      this.userInputKeyword = "";
      this.dialog = false;
    },
    email(){
      console.log(localStorage.getItem("member_email"));
      axios
          .post("http://192.168.31.84:8080/member/emailcheck", {
          email : localStorage.getItem("member_email")
        })
        .then(response => {
          console.log(response.data.certifiedkey);
          this.certifiedkey = response.data.certifiedkey;
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  beforeMount() {
    this.get_user();
    this.email();
  },
  computed: {
    is_size() {
      if (this.$vuetify.breakpoint.name=='xs')
        return false
      else
        return true 
    }
  }
};
</script>
<style scoped>
.header_box {
  width: 100%;
  height: 360px;
  background-color : rgba(130, 215, 255, 0.863);
  /* background-image: url('../../'); */
  margin-bottom: 30px;
  padding-top: 50px;
}
.header_name {
  font-size: 30px;
  text-align: center;
  color : white;
}
.header_email {
  font-size: 20px;
  text-align: center;
  color: grey;
}
.body_box {
  margin: 50px 0px;
}
.newsbody {
  font-size: 30px;
}
.comment_font {
  text-align: center;
  margin: 30px;
}
.updatedfont {
  font-size: 10px;
  color: gray;
  margin-left: 10px;
}
.updatedfont:hover {
  font-size: 14px;
  color: black;
  margin-left: 7px;
}
.likeheader{
  font-size : 20px;
  margin-bottom: 20px;
}
ul {
  margin: 0;
  padding: 0;
}
li {
  list-style: none;
  display: inline-block;
  margin: 0 0 0 8px;
  cursor: pointer;
}
li:hover {
  transform: scale(1.2);
}
.list-enter-active {
  transition: all 1s;
}
.list-enter {
  opacity: 0;
  transform: translateY(30px);
}
.cardhover:hover {
  box-shadow: 1px 2px 4px gray;
}
</style>
