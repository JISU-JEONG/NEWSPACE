<template>
  <div v-if="$store.state.auth==='1'">
    <div class="admin_header">
      <h1 class="admin_font">NEWSPACE ADMIN PAGE</h1>
    </div>
    <div>
      <div class="adminbody">
        <div class="bodyfont">User 관리</div>
        <v-card>
          여기에는 유저관리 목록
        </v-card>
        <div class="bodyfont">comment 관리</div>
        <v-card>
          여기에는 코멘트 목록
        </v-card>
      </div>
      <div class="subbody">
        czxvzx
        asdasddas
        <v-card>
          나는 뭐쓸래?
        </v-card>
      </div>
    </div>
  </div>
  <div v-else></div>
</template>

<script>
import router from '../router'

export default {
  name :'admin',
  methods: {
    getout(){
      if(this.$store.state.auth !=='1')
      {
        alert("잘못된 접근입니다. 꺼져 주세요.")
        router.push('/')
      }
    }
  },
  mounted(){
    this.getout()
  }

}
</script>

<style scoped>
.admin_header{
  width: 100%;
  background-color: grey;
  height: 150px;
}
.admin_font{
  display: block;
  color : white;
  text-align-last: center;
  line-height: 150px;
}
.adminbody{
  display: inline-block;
  width: 70%;
  padding: 30px;
}
.subbody{
  display: inline-block;
  width: 30%;
  padding: 10px;

}
.bodyfont{
  width: 70%;
}
</style>