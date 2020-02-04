<template>
  <div>
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
</template>

<script>
export default {

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