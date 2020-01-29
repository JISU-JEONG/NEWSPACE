<template>
<!-- 삭제될 컴포넌트 -->
  <v-app>
    <v-container>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card>
            <v-toolbar color="primary" dark flat>
              <v-toolbar-title>회원가입</v-toolbar-title>
              <v-spacer />
            </v-toolbar>
            <v-card-text>
              <v-form>
                <v-text-field label="Email" v-model="email" type="text" />
                <v-text-field label="Password" v-model="password" type="password" />
                <v-text-field label="Name" v-model="name" type="text" />
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
            <v-btn rounded color="success" dark v-on:click="Signup">가입하기</v-btn>
            <v-card-actions></v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import http from "../../http-common";

export default {
  name: "SignupPage",
  data() {
    return {
      email: "",
      password: "",
      name: "",
      keyword: []
    };
  },
  methods: {
    Signup() {
      http
        .post("/member/signup", {
          email: this.email,
          password: this.password,
          name: this.name,
          inputkeyword: this.keyword,
          type: "nomal"
        })
        .then(res => {
          if (res.data == 0) {
            alert("아이디가 중복됩니다.");
          } else {
            alert("가입 성공!!");
            this.$router.push("/Login");
          }
        })
        .catch(e => {
          console.log(e);
        });
    }
  }
};
</script>