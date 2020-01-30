<template>
  <v-container>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="12" md="4">
        <v-card>
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>관심 키워드를 선택해주세요</v-toolbar-title>
            <v-spacer />
          </v-toolbar>
          <v-card-text>
            <v-form>
              <p>{{ keyword }}</p>
              <v-checkbox v-model="keyword" label="AI" value="AI"></v-checkbox>
              <v-checkbox v-model="keyword" label="삼성" value="삼성"></v-checkbox>
              <v-checkbox v-model="keyword" label="LG" value="LG"></v-checkbox>
              <v-checkbox v-model="keyword" label="SK" value="SK"></v-checkbox>
              <v-checkbox v-model="keyword" label="갤럭시" value="갤럭시"></v-checkbox>
              <v-checkbox v-model="keyword" label="빅스비" value="빅스비"></v-checkbox>
              <v-checkbox v-model="keyword" label="가전" value="가전"></v-checkbox>
              <v-checkbox v-model="keyword" label="스마트폰" value="스마트폰"></v-checkbox>
            </v-form>
          </v-card-text>
          <v-btn rounded color="success" dark v-on:click="Signup">선택완료</v-btn>
          <v-card-actions></v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import http from "../http-common";
const storage = localStorage;
export default {
  name: "SignupPage",
  data() {
    return {
      keyword: [],
      email: "",
      name: "",
      type: ""
    };
  },
  methods: {
    Signup() {
      http
        .post("/member/signup", {
          email: this.email,
          name: this.name,
          inputkeyword: this.keyword,
          type: this.type
        })
        .then(() => {
          alert("가입성공!!");
          this.logintoken();
        })
        .catch(e => {
          console.log(e);
        });
    },
    getInfo() {
      console.log("login-token: " + storage.getItem("login-token"));
      var token = storage.getItem("login-token");
      if (token != null) {
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
            console.log(JSON.stringify(res.data.data));
            this.email = res.data.data.email;
            this.name = res.data.data.name;
            this.type = res.data.data.type;
          })
          .catch(e => {
            console.log(e);
          });
      } else {
        console.log("토큰정보가 없습니다.");
        this.loginStatus = false;
      }
    },
    logintoken() {
      console.log(this.email);
      const parentFunc = this;
      storage.setItem("login-token", "");
      var _promise = function() {
        return new Promise(function(resolve) {
          http
            .post("/member/socialtoken", {
              email: parentFunc.email,
              name: parentFunc.name,
              type: parentFunc.type
            })
            .then(res => {
              storage.setItem("login-token", res.headers["login-token"]);
              resolve("ㄲ");
            });
        });
      };
      _promise().then(() => {
        this.$router.push("/");
        location.reload();
      });
    }
  },
  mounted() {
    this.getInfo();
  }
};
</script>