<template>
  <div class="login">
    <br><br><br>
    <input type="email" v-model="email" /><br/>
    <input type="password" v-model="password"/><br/>
    <button v-on:click="login">로그인</button><br/>
    <button @click="googleLogin">Google 로그인</button><br/>
    <button @click="githubLogin">Github 로그인</button><br/>
    <button @click="logout">로그아웃</button><br>
    <input v-model="user" />
  </div>
</template>
 
<script>
import FirebaseService from "@/services/FirebaseService";
import firebase from "firebase";
import router from "../router";

export default {
  name: "login",
  data() {
    return {
      email: "",
      password: "",
      user: ""
    };
  },
  methods: {
    githubLogin(){
      const provider = new firebase.auth.GithubAuthProvider();
      firebase
        .auth()
        .signInWithPopup(provider)
        .then(result => {
          console.log(result.dh.message.email);
          this.user = result.dh.email;
        })
        .catch(error => {
          console.log(error);
          this.errorMessage = error.message;
          this.showError = true;
        });
    },
    googleLogin() {
      const provider = new firebase.auth.GoogleAuthProvider();
      firebase
        .auth()
        .signInWithPopup(provider)
        .then(result => {
          console.log(result.user);
          this.user = result.user.displayName;
        })
        .catch(error => {
          console.log(error);
          this.errorMessage = error.message;
          this.showError = true;
        });
    },
    login() {
      firebase
        .auth()
        .signInWithEmailAndPassword(this.email, this.password)
        .then(
          function(user) {
            alert("로그인 완료!");
          },
          function(err) {
            alert("에러 : " + err.message);
          }
        );
    },
    logout() {
      firebase
        .auth()
        .signOut()
        .then(function() {
          console.log("로그아웃");
          router.push("/post");
        })
        .catch(error => {
          console.log(error.message);
        });
    }
  }
};
</script>