<template>
  <v-container>
    <v-card>
      <v-card-title>회원가입</v-card-title>
      <v-row>
        <v-col cols="7">
          <v-form>
            <v-container>
              <v-text-field label="Email" v-model="email" type="text" />
              <v-text-field label="Password" v-model="password" type="password" />
              <v-text-field label="Name" v-model="name" type="text" />
            </v-container>
          </v-form>
        </v-col>
        <v-col cols="5">
          <v-checkbox v-for="keyword in keywords" v-model="selectedKeywords" label="keyword" value="keyword" :key="keyword">{{keyword}}</v-checkbox>
        </v-col>
      </v-row>
    </v-card>
    <v-row justify="center">
      <v-col cols="10">
        <v-card>
          <v-row>
            <v-col cols="7">

              안녕
            </v-col>
            <v-col cols="5">
              하세요
            </v-col>
          </v-row>
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
</template>

<script>
import http from "../http-common";

export default {
  name: "SignupPage",
  data() {
    return {
      email: "",
      password: "",
      name: "",
      selectedKeywords: [],
      keywords: ['AI', 'TV', '빅스비', '스마트폰', '가전', '냉장고', 'SSD'],
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