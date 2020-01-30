<template>
  <v-container>
    <v-card>
      <v-card-title>회원가입</v-card-title>
      <v-form ref="form" v-model="valid" @submit.prevent="Signup">
        <v-container>
          <v-row>
            <v-col cols="6">
              <v-text-field :rules="nameRules" label="이름" v-model="name" type="text" />
              <v-text-field :rules="emailRules" label="이메일" v-model="email" type="text" />
              <v-text-field :rules="passwordRules" label="비밀번호" v-model="password" type="password" />
              <v-text-field :rules="passwordCheckRules" label="비밀번호 확인" v-model="passwordCheck" type="password" />
            </v-col>
            <v-col cols="6">
              <div style="height:50%;">
                <span>선택된 아해들</span>
                <ul @click.stop="unselectKeyword">
                  <transition-group name="list">
                    <li v-for="keyword in selectedKeywords" :key="keyword">{{keyword}}</li>
                  </transition-group>
                </ul>
              </div>               
              <hr>
              <div>
                <span>선택되지 못한 아해들</span> <br>
                <ul @click.stop="selectKeyword">
                  <transition-group name="list">
                    <li v-for="keyword in unselectedKeywords" :key="keyword">{{keyword}}</li>
                  </transition-group>
                </ul>
              </div>
              <!-- 한글자, 특수문자, 숫자 같이 쓰레기 넣었을 때 못하게 하고싶다. -->
              <v-text-field 
                :label="label"
                v-model="userInputKeyword"
                append-icon="mdi-plus"
                @click:append="userInputKeywordToList"
                @keyup.enter.stop="userInputKeywordToList"
                :error="error"
                :error-messages="errorMessages"
                @input="inputKeyword"
              />
            </v-col>
          </v-row>
          <v-btn :disabled="!valid" color="blue lighten-2" class="mt-3 white--text" type="submit">가입하기</v-btn>
        </v-container>
      </v-form>
    </v-card>
  </v-container>
</template>

<script>
import http from "../http-common";

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
      unselectedKeywords: ['123','44', '333'],
      label: "추가하고싶은 키워드",
      error: false,
      errorMessages: ''
    };
  },
  mounted() {
    this.keywordSetting()
  },
  methods: {
    Signup() {
      if (this.$refs.form.validate()) {
        http
          .post("/member/signup", {
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
      }
    },
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
      if (this.userInputKeyword.length < 2) {
        this.error = true
        this.errorMessages = "두글자 이상을 입력해주세요"
      } else {
          if (this.selectedKeywords.findIndex(v => v === this.userInputKeyword) < 0 ) {
            this.selectedKeywords.push(this.userInputKeyword)
          } else {
            this.error = true
            this.errorMessages = '이미 추가된 키워드입니다.'
          }
        }
      this.userInputKeyword = ''
    }
  },
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
  .list-enter-active{
    transition: all 1s;
  }
  .list-enter {
    opacity: 0;
    transform: translateY(30px);
  }
</style>