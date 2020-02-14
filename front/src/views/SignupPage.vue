<template>
  <v-container>
    <v-row>
      <v-col cols="12" sm="6">
        <v-card>
          <v-container style="min-height:430px;">
            <v-card-title>회원가입</v-card-title>
            <v-form ref="form" v-model="valid" @submit.prevent="Signup">
              <v-text-field :rules="nameRules" label="이름" v-model="name" type="text" />
              <v-text-field :rules="emailRules" label="이메일" v-model="email" type="text" />
              <v-text-field :rules="passwordRules" label="비밀번호" v-model="password" type="password" />
              <v-text-field :rules="passwordCheckRules" label="비밀번호 확인" v-model="passwordCheck" type="password" />
              <v-btn id="signupBtn" :disabled="!valid" color="blue lighten-2" class="mt-3 white--text" type="submit">가입하기</v-btn>
            </v-form>
          </v-container>
        </v-card>
      </v-col>
      <v-col cols="12" sm="8">
        <v-card mb-5>
          <v-container style="min-height:430px;">
            <v-card-title>관심 키워드 등록</v-card-title>
            <v-card-subtitle>
              0. 클릭하여 키워드를 추가하거나 삭제할 수 있습니다. <br>
              1. 키워드와 일치하는 뉴스를 이메일로 전송해드립니다 (이메일 인증 필요) <br>
              2. 로그인시 해당 키워드와 일치하는 뉴스를 따로 보여드립니다.
            </v-card-subtitle>

            <v-divider></v-divider>
            
            <v-card style="min-height:120px;">
              <span class="mx-3">선택된 키워드</span>
              <v-divider></v-divider>
              <v-container>
                <ul @click.stop="unselectKeyword">
                  <transition-group name="list">
                    <li v-for="keyword in selectedKeywords" :key="keyword" class="text--darken-3">{{keyword}}</li>
                  </transition-group>
                </ul>
              </v-container>
            </v-card>
            <hr>
            <v-card style="min-height:120px;" class="mt-3" >
              <span class="mx-3">추천 키워드</span> <br>
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
              :error-messages="errorMessages"
              @input="inputKeyword"
            />
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import http from "../services/http-common";
import axios from "axios";

export default {
  name: "SignupPage",
  data() {
    return {
      // 회원가입 폼 확인 
      valid: false, 
      name: "",
      email: "",
      password: "",
      passwordCheck: "",
      nameRules:[v => !!v || "이름을 입력하세요."],
      emailRules: [
                    v => !!v || "이메일을 입력하세요.",
                    v => /.+@+./.test(v) || "이메일 형식이 아닙니다."
                  ],
      passwordRules: [v => !!v || "비밀번호를 입력하세요."],
      passwordCheckRules: [
                            v => !!v || "비밀번호 확인을 입력하세요.",
                            v => v === this.password || "비밀번호가 일치하지 않습니다."],
      userInputKeyword: '', // 가입자가 입력한 키워드
      selectedKeywords: [],
      unselectedKeywords: [],
      textColorList: ["red--text", "purple--text", "blue--text", "teal--text", "lime--text", "yellow--text", "brown--text", "grey--text"], 
      error: false,
      errorMessages: '',
      trySignup: false,
    };
  },
  mounted() {
    this.keywordSetting()
  },
  methods: {
    Signup() {
      if (this.$refs.form.validate()) { // 회원가입 폼 확인
        if (this.selectedKeywords.length > 0 || this.trySignup) { // 관심 키워드 빈칸인지 확인
          // axios
          //   .post("http://52.79.249.4:8080/member/signup", {
          axios
            .post("http://192.168.31.85:8080/member/signup", {
              email: this.email,
              password: this.password,
              name: this.name,
              inputkeyword: this.selectedKeywords,
              type: "nomal"
            })
            .then(res => {
              if (res.data == 0) {
                alert("아이디가 중복됩니다.");
              } else {
                alert("가입 성공!!");
                this.$router.push("/login");
              }
            })
            .catch(e => {
              console.log(e);
            })
        } else {
          alert('관심 키워드를 등록하시면 더 많은 정보를 받아보실 수 있습니다.')
          this.trySignup = true
        }
      }
    },
    // 관심 키워드 등록
    keywordSetting() {
      http.get("/getUserKeyword")
        .then(response => {
          this.unselectedKeywords = response.data
        })
    },
    selectKeyword(event) {
      if (event.target.tagName === "LI") {
        const keyword = event.target.innerText
        const index = this.unselectedKeywords.findIndex(v => v === keyword)
        if (this.selectedKeywords.findIndex(v => v === keyword) < 0) {
          this.selectedKeywords.push(keyword)
        }
        this.unselectedKeywords.splice(index, 1)
      }
    },
    unselectKeyword(event) {
      if (event.target.tagName === "LI") {
        const keyword = event.target.innerText
        const index = this.selectedKeywords.findIndex(v => v === keyword)
        if (this.unselectedKeywords.findIndex(v => v === keyword) < 0) {
          this.unselectedKeywords.push(keyword)
        }
        this.selectedKeywords.splice(index, 1)
      }
    },
    inputKeyword(value) {
      if (value.length) {
        this.error = false
        this.errorMessages = ''
      } 
    },
    userInputKeywordToList() {
      let errorKeyword = ''
      if (this.userInputKeyword.length < 2) {
        this.error = true
        this.errorMessages = "두글자 이상을 입력해주세요"
      } else {
          this.userInputKeyword.split(' ').forEach( value => {
            if (this.selectedKeywords.findIndex(v => v === value) < 0 && value.length > 1 ) {
              this.selectedKeywords.push(value)
            } else {
              errorKeyword = errorKeyword.concat(` ${value}`)
              console.log(errorKeyword)
            }
          })
          errorKeyword = errorKeyword.trim()
          if (errorKeyword) {
            this.error = true
            this.errorMessages = `${errorKeyword}은(는) 이미 추가되었거나 너무 짧은 단어입니다.`
          }
        }
      this.userInputKeyword = ''

    }
  },
};
</script>

<style scoped>
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
  .list-enter-active{
    transition: all 1s;
  }
  .list-enter {
    opacity: 0;
    transform: translateY(30px);
  }
</style>