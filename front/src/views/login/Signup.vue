<template>
  <div>
    <h1>This is an Signup page</h1>
    <SignupPage></SignupPage>
  </div>
</template>

<script>
// @ is an alias to /src
import SignupPage from "@/components/login/SignupPage.vue";

export default {
  name: "Signup",
  components: {
    SignupPage
  }
};
</script>