<template>
  <v-container>
    <v-row class="justify-center">
      <v-col cols="7">
        <v-card raised>
          <v-alert color="error" icon="warning" v-show="this.$store.state.error">로그인 정보를 확인해주세요</v-alert>
          <v-card-title>로그인</v-card-title>
          <v-container>
            <v-form ref="loginForm" @submit.prevent="login" v-model="loginValid">
              <v-container>
                <v-text-field label="이메일" type="email" v-model="email" :rules="emailRules" />
                <v-text-field
                  label="비밀번호"
                  type="password"
                  v-model="password"
                  :rules="passwordRules"
                />
                <v-btn
                  :disabled="!loginValid"
                  color="blue lighten-2"
                  type="submit"
                  class="white--text mt-3"
                  v-on:click="login"
                >로그인</v-btn>
              </v-container>
            </v-form>
            <v-divider></v-divider>
          </v-container>
          <v-subheader>소셜 로그인</v-subheader>
          <v-container d-flex justify-space-around>
            <input type="hidden" v-model="socialemail" />
            <v-btn dark color="indigo darken-2" @click="FacebookLogin">Facebook 로그인</v-btn>
            <v-btn dark color="red darken-2" @click="GoogleLogin">Google 로그인</v-btn>
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import firebase from "firebase/app";
import firebaseservice from "../services/FirebaseService";
import http from "../services/http-common";
import axios from "axios";

export default {
  name: "login",
  computed: {
    error() {
      return this.$store.state.error;
    }
  },
  data() {
    return {
      type: "",
      email: "",
      password: "",
      username: "",
      socialemail: "",
      duplicationflag: 0,
      //로그인 폼 양식 확인
      loginValid: false,
      infoValid: false,
      emailRules: [
        v => !!v || "이메일을 입력하세요",
        v => !!/.+@+./.test(v) || "이메일 형식이 아닙니다."
      ],
      passwordRules: [v => !!v || "비밀번호를 입력하세요"],
      emptyRules: [v => !!v || "값을 입력해주세요"]
    };
  },
  methods: {
    login() {
      if (this.$refs.loginForm.validate()) {
        // 로그인 폼이 유효한지 확인
        axios
          .post("http://192.168.31.85:8080/member/signin", {
            email: this.email,
            password: this.password,
            type: "nomal",
            tokenname: "login-token"
          })
          .then(res => {
            if (res.data.status) {
              
              localStorage.setItem("login-token", res.headers["login-token"]);
              localStorage.setItem("loginStatus", res.data.name);
              const payload = {
                token: localStorage.getItem("login-token"),
                member_id: "",
                member_name: res.data.member_name,
                auth : localStorage.removeItem("auth"),
                member_keyword: res.data.member_keyword,
              };
              console.log(payload)
              this.$store.dispatch("login", payload);
              this.$router.push("/", () => {});
            } else {
              this.$store.dispatch("error");
              // alert("입력 정보를 확인하세요.");
            }
          })
          .catch(e => {
            this.$store.dispatch("error");
            console.log(e);
          });
      }
    },
    FacebookLogin() {
      const provider = new firebase.auth.FacebookAuthProvider();
      const parentFunc = this;
      var _promise = function() {
        return new Promise(function(resolve, reject) {
          firebase
            .auth()
            .signInWithPopup(provider)
            .then(res => {
              // console.log(res.user);
              parentFunc.username = res.user.displayName;
              parentFunc.socialemail = res.user.uid;
              parentFunc.type = "facebook";
              resolve("ㄲ");
            })
            .catch(err => {
              console.log(err);
              reject(err);
            });
        });
      };
      _promise().then(() => {
        this.duplicationCheck();
      });
    },
    GoogleLogin() {
      const provider = new firebase.auth.GoogleAuthProvider();
      const parentFunc = this;
      var _promise = function() {
        return new Promise(function(resolve, reject) {
          firebase
            .auth()
            .signInWithPopup(provider)
            .then(res => {
              console.log(res.user);
              parentFunc.username = res.user.displayName;
              parentFunc.socialemail = res.user.uid;
              parentFunc.type = "google";
              resolve("ㄲ");
            })
            .catch(error => {
              console.log(error);
              reject(error);
            });
        });
      };
      _promise().then(() => {
        this.duplicationCheck();
      });
    },
    duplicationCheck() {
      const parentFunc = this;
      //아이디 중복체크
      var _promise = function() {
        return new Promise(function(resolve) {
          axios
            .post("http://192.168.31.85:8080/member/signupcheck", {
              email: parentFunc.socialemail
            })
            .then(res => {
              console.log(res.data);
              if (res.data == "Notexist") {
                //아이디 중복 없다.
                parentFunc.duplicationflag = 1;
              } else {
                //아이디 중복있다.
                parentFunc.duplicationflag = 0;
              }
              resolve("ㄲ");
            });
        });
      };
      _promise().then(() => {
        var _promise2 = function() {
          return new Promise(function(resolve) {
            axios
              .post("http://192.168.31.85:8080/member/socialtoken", {
                email: parentFunc.socialemail,
                name: parentFunc.username,
                type: parentFunc.type
              })
              .then(res => {
                localStorage.setItem("login-token", res.headers["login-token"]);
                localStorage.setItem("loginStatus", parentFunc.username);
                resolve("ㄲ");
              });
          });
        };
        _promise2().then(() => {
          if (parentFunc.duplicationflag == 1) {
            //처음 로그인시 키워드 선택
            this.$router.push("/SocialSignup", () => {});
          } else {
            //이미 로그인한적이 있을시 홈으루
            const payload = {
              token: localStorage.getItem("login-token"),
              member_id: "",
              member_name: localStorage.getItem("loginStatus"),
              auth : localStorage.removeItem("auth")
            };
            this.$store.dispatch("login", payload);
            this.$router.push("/", () => {});
          }
        });
      });
    },
  },
};
</script>