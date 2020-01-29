<template>
  <v-container>
    <v-row>
      <v-col cols="12" sm="6">
        <v-card raised>
          <v-card-title>로그인</v-card-title>
          <v-container>
            <v-form ref="loginForm" @submit.prevent="login" v-model="loginValid" >
              <v-container>
                <v-text-field 
                  label="이메일"
                  type="email"
                  v-model="email"
                  :rules="emailRules"
                />
                <v-text-field 
                  label="비밀번호"
                  type="password"
                  v-model="password"
                  :rules="passwordRules"
                />
                <v-btn :disabled="!loginValid" color="blue lighten-2" type="submit" class="white--text mt-3">로그인</v-btn>
              </v-container>
            </v-form>
            <v-divider></v-divider>
          </v-container>
          <v-subheader>소셜 로그인</v-subheader>
          <v-container d-flex justify-space-around>
            <v-btn dark color="indigo darken-2" @click="FacebookLogin">Facebook 로그인</v-btn>
            <v-btn dark color="red darken-2" @click="GoogleLogin">Google 로그인</v-btn>
          </v-container>
        </v-card>
      </v-col>
      <v-col cols="12" sm="6">
        <v-card raised>
          <v-card-title>정보확인</v-card-title>
          <v-container>
            <v-form ref="infoForm" @submit.prevent="getInfo" v-model="infoValid" >
              <v-container>
                <!-- <v-text-field :rules="emptyRules" label="유저" v-model="LoiginStatus" type="text" /> -->
                <v-text-field :rules="emptyRules" v-model="username"></v-text-field>
                <v-text-field :rules="emptyRules" label="상태" v-model="status" type="text" />
                <v-text-field :rules="emptyRules" label="토큰" v-model="token" type="text" />
                <v-text-field :rules="emptyRules" label="정보" v-model="info" type="text" />
                <v-btn :disabled="!infoValid" type="submit" color="success" style="while--text">정보확인</v-btn>
              </v-container>
            </v-form>
            <v-divider></v-divider>
          </v-container>
          <v-btn rounded color="primary" dark v-on:click="logout"> 사라지게 될 logout</v-btn>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import firebase from "firebase";
import http from "../http-common"
const storage = localStorage;
// const storage = sessionStorage;
export default {
  name: "login",
  data() {
    return {
      email: "",
      password: "",
      username: "",
      duplicationflag: 0,
      type: "",
      //로그인 폼 양식 확인
      loginValid: false,
      infoValid: false,
      emailRules: [
        v => !!v || '이메일을 입력하세요',
        v => !!/.+@+./.test(v) || '이메일 형식이 아닙니다.'
      ],
      passwordRules: [v => !!v || '비밀번호를 입력하세요'],
      emptyRules: [v => !!v || '값을 입력해주세요'],
      //로그인 정보 확인부분
      status: "",
      token: "",
      info: "",
    };
  },
  methods: {
    ////////////////////////// 일반로그인 /////////////////////////////
    // 정보확인용
    setInfo(status, token, info) {
      this.status = status;
      this.token = token;
      this.info = info;
    },

    //일반 로그인 로직
    login() {
      if (this.$refs.loginForm.validate()){ // 로그인 폼이 유효한지 확인
        http
          .post("/member/signin", {
            email: this.email,
            password: this.password,
            type : "nomal"
          })
          .then(res => {
            if (res.data.status) {
              storage.setItem("login-token", res.headers["login-token"]);
              this.setInfo("로그인성공", "", "");
              this.getInfo();
            } else {
              this.setInfo("", "", "");
              this.username = "";
              alert("입력 정보를 확인하세요.");
            }
          })
          .catch(e => {
            this.setInfo("실패", "", JSON.stringify(e.response || e.message));
        });
      }
    },
    //토큰정보 획득
    
    getInfo() {
      if (this.$refs.infoForm.validate()) {   // 정보확인 폼 유효한지 확인
        console.log("login-token: " + storage.getItem("login-token"));
        http
          .post(
            "/info",
            {},
            {
              headers: {
                "login-token": storage.getItem("login-token")
              }
            }
          )
          .then(res => {
            this.username = res.data.data.name;
            this.$router.push("/");
            // this.setInfo(
            //   "정보 조회 성공",
            //   JSON.stringify(res.data),
            //   JSON.stringify(res.data.data)
            // );
          })
          .catch(e => {
            this.setInfo("정보 조회 실패", "", e.response.data.msg);
          });
      }
    },
    logout() {
      storage.removeItem("login-token");
      this.username = "";
      this.setInfo("로그아웃 성공", "", "");
    },
    ////////////////////////// 일반로그인 /////////////////////////////

    ////////////////////////// 소셜로그인 /////////////////////////////
    FacebookLogin() {
      const provider = new firebase.auth.FacebookAuthProvider();
      const parentFunc = this;
      var _promise = function() {
        return new Promise(function(resolve, reject) {
          firebase
            .auth()
            .signInWithPopup(provider)
            .then(res => {
              console.log(res.user);
              parentFunc.username = res.user.displayName;
              parentFunc.email = res.user.uid;
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
              parentFunc.email = res.user.uid;
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
      storage.setItem("login-token", "");

      //아이디 중복체크
      var _promise = function() {
        return new Promise(function(resolve) {
          http
            .post("/member/signupcheck", {
              email: parentFunc.email
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
              http
                .post("/member/socialtoken", {
                  email: parentFunc.email,
                  name: parentFunc.username,
                  type: parentFunc.type
                })
                .then(res => {
                  storage.setItem("login-token", res.headers["login-token"]);
                  resolve("ㄲ");
                });
          });
        };
        _promise2().then(() => {
          if (parentFunc.duplicationflag == 1) {
            //처음 로그인시 키워드 선택
            this.$router.push("/SocialSignup");
          } else {
            //이미 로그인한적이 있을시 홈으루
            this.getInfo();
          }
        });
      });
    }
    ////////////////////////// 소셜로그인 /////////////////////////////
  },
  mounted() {
    this.getInfo();
  }
};
</script>