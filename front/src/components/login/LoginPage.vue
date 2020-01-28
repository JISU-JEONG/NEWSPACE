<template>
  <v-app>
    <v-container>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card>
            <v-toolbar color="primary" dark flat>
              <v-toolbar-title>AMT</v-toolbar-title>
              <v-spacer />
            </v-toolbar>
            <v-card-text>
              <v-form>
                <v-text-field label="Email" v-model="email" type="text" />
                <v-text-field label="Password" v-model="password" type="password" />
              </v-form>
            </v-card-text>
            <v-btn rounded color="success" dark v-on:click="login">Login</v-btn>
            <br />
            <br />
            <v-btn rounded color="primary" dark v-on:click="FacebookLogin">FaceBook Login</v-btn>
            <br />
            <br />
            <v-btn rounded color="info" dark v-on:click="GoogleLogin">Google Login</v-btn>

            <v-card-actions></v-card-actions>
          </v-card>
          <br />
          <br />
          <v-card>
            <v-toolbar color="primary" dark flat>
              <v-toolbar-title>정보확인</v-toolbar-title>
              <v-spacer />
              <v-tooltip right>
                <input tpye="text" value="aaa" />
              </v-tooltip>
            </v-toolbar>
            <v-card-text>
              <!-- <v-text-field label="유저" v-model="LoiginStatus" type="text" /> -->
              <v-text-field v-model="username"></v-text-field>
              <v-text-field label="상태" v-model="status" type="text" />
              <v-text-field label="토큰" v-model="token" type="text" />
              <v-text-field label="정보" v-model="info" type="text" />
            </v-card-text>
            <v-btn rounded color="success" dark v-on:click="getInfo">정보확인</v-btn>
            <br />
            <br />
            <v-btn rounded color="primary" dark v-on:click="logout">logout</v-btn>

            <v-card-actions></v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import firebase from "firebase";
import http from "../../http-common"
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

      //로그인 정보 확인부분
      status: "",
      token: "",
      info: ""
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
    },

    //토큰정보 획득
    getInfo() {
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