<template>
  <v-container>
    <v-row align="center" justify="center">
      <v-col cols="12" md="8">
        <v-card>
          <v-container style="min-height:430px;">
            <v-card-title>관심 키워드 등록</v-card-title>
            <v-card-subtitle>
              0. 클릭하여 키워드를 추가하거나 삭제할 수 있습니다.
              <br />1. 키워드와 일치하는 뉴스를 이메일로 전송해드립니다 (이메일 인증 필요)
              <br />2. 로그인시 해당 키워드와 일치하는 뉴스를 따로 보여드립니다.
            </v-card-subtitle>
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
            <v-btn
              id="signupBtn"
              color="blue lighten-2"
              class="mt-3 white--text"
              @click="Signup"
            >가입하기</v-btn>
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import http from "../services/http-common";
import axios from "axios";
import store from "../store";

export default {
  name: "SocialSignpPage",
  data() {
    return {
      userInputKeyword: "", // 가입자가 입력한 키워드
      selectedKeywords: [],
      unselectedKeywords: [],
      error: false,
      errorMessages: "",
      trySignup: false
    };
  },
  methods: {
    Signup() {
      if (this.selectedKeywords.length > 0 || this.trySignup) {
        // axios
        //     .post("http://52.79.249.4:8080/member/signup", {
        axios
          .post("http://192.168.31.84:8080/member/signup", {
            email: localStorage.getItem("member_email"),
            name: localStorage.getItem("member_name"),
            inputkeyword: this.selectedKeywords,
            type: localStorage.getItem("member_type")
          })
          .then(() => {
            alert("가입성공!!");
            this.logintoken();
          })
          .catch(e => {
            console.log(e);
          });
      } else {
        alert("관심 키워드를 등록하시면 더 많은 정보를 받아보실 수 있습니다.");
        this.trySignup = true;
      }
    },
    logintoken() {
      const parentFunc = this;
      var _promise = function() {
        return new Promise(function(resolve) {
          // axios
          //   .post("http://52.79.249.4:8080/member/socialtoken", {
          axios
            .post("http://192.168.31.84:8080/member/socialtoken", {
              email: localStorage.getItem("member_email"),
              name: localStorage.getItem("member_name"),
              type: localStorage.getItem("member_type")
            })
            .then(res => {
              localStorage.setItem("login-token", res.headers["login-token"]);
              localStorage.setItem("loginStatus", true);
              localStorage.setItem("member_keyword", res.data.member_keyword);
              localStorage.setItem("member_name", res.data.member_name);
              resolve("ㄲ");
            });
        });
      };
      _promise().then(() => {
        localStorage.setItem("loginStatus", parentFunc.name);
        const payload = {
          token: localStorage.getItem("login-token"),
          member_id: "",
          member_name: localStorage.getItem("member_name"),
          auth: localStorage.removeItem("auth"),
          member_keyword: localStorage.getItem("member_keyword")
        };
        this.$store.dispatch("login", payload);
        parentFunc.$store.dispatch("setMemberNews");
        this.$router.push("/", () => {});
      });
    },
    keywordSetting() {
      http.get("/getUserKeyword").then(response => {
        this.unselectedKeywords = response.data;
      });
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
    inputKeyword(value) {
      if (value.length) {
        this.error = false;
        this.errorMessages = "";
      }
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
    }
  },
  mounted() {
    this.keywordSetting();
  },
  beforeMount() {
    info();
  },
  beforeRouteLeave (to, from, next) {
    if (this.selectedKeywords.length === 0) {
      localStorage.clear()
      this.$store.dispatch("logout")
    }
    next()
  }
};
</script>
<style scoped>
li {
  list-style: none;
  display: inline-block;
  margin-left: 10px;
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
</style>