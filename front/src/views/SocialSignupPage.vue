<template>
  <v-container>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="12" md="4">
        <v-card>
          <v-container style="min-height:430px;">
            <v-card-title>관심 키워드 등록</v-card-title>
            <v-divider></v-divider>

            <v-card style="min-height:120px;">
              <span>선택된 아해들</span>
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
              <span>선택되지 못한 아해들</span>
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
              style="margin-top:16px;"
              label="추가하고싶은 키워드"
              v-model="userInputKeyword"
              append-icon="mdi-plus"
              @click:append="userInputKeywordToList"
              @keyup.enter.stop="userInputKeywordToList"
              :error="error"
              ₩
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
import info from "../services/getInfo";
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
        axios
          .post("http://192.168.31.85:8080/member/signup", {
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
          axios
            .post("http://192.168.31.85:8080/member/socialtoken", {
              email: localStorage.getItem("member_email"),
              name: localStorage.getItem("member_email"),
              type: localStorage.getItem("member_type")
            })
            .then(res => {
              localStorage.setItem("login-token", res.headers["login-token"]);
              resolve("ㄲ");
            });
        });
      };
      _promise().then(() => {
        localStorage.setItem("loginStatus", parentFunc.name);
        const payload = {
          token: localStorage.getItem("login-token"),
          member_id: "",
          member_name: localStorage.getItem("member_name")
        };
        this.$store.dispatch("login", payload);
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
        };
      this.userInputKeyword = "";
    }
  },
  mounted() {
    this.keywordSetting();
  },
  beforeMount() {
    info();
  }
};
</script>
<style>
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