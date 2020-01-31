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
import info from "../services/getInfo";
export default {
  name: "SignupPage",
  data() {
    return {
      keyword: []
    };
  },
  methods: {
    Signup() {
      http
        .post("/member/signup", {
          email: localStorage.getItem("member_email"),
          name: localStorage.getItem("member_name"),
          inputkeyword: this.keyword,
          type: localStorage.getItem("member_type")
        })
        .then(() => {
          alert("가입성공!!");
          this.logintoken();
        })
        .catch(e => {
          console.log(e);
        });
    },
    logintoken() {
      const parentFunc = this;
      var _promise = function() {
        return new Promise(function(resolve) {
          http
            .post("/member/socialtoken", {
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
    }
  },
  beforeMount() {
    info();
  }
};
</script>