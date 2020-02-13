<template>
  <div v-if="$store.state.auth==='1'">
    <div class="admin_header">
      <h1 class="admin_font">NEWSPACE ADMIN PAGE</h1>
    </div>
    <div>
      <div class="adminbody">
        <div class="bodyfont">Server 상태</div>
        <v-card>
          <table>
            <tr>SAMSUNG CRAWLING STATUS
              <td>
                {{status[0]}}
              </td>
            </tr>
            <tr>LG CRAWLING STATUS
              <td>
                {{status[1]}}
              </td>
            </tr>
            <tr>SK CRAWLING STATUS
              <td>
                {{status[2]}}
              </td>
            </tr>
            <tr>NEWS KEYWORD SETTING STATUS
              <td>
                {{status[3]}}
              </td>
            </tr>
          </table>
        </v-card>
        <br>
        <div class="bodyfont">User 관리</div>
        <v-card>
          <div class="box" v-for="i in users.length" :key="i">
            <div>{{users[i-1].name}} {{users[i-1].email}}</div>
          </div>
        </v-card>
      </div>
    </div>
  </div>
  <div v-else></div>
</template>

<script>
import router from '../router'
import axios from 'axios'

export default {
  data() {
    return {
      status: [],
      users: [],
    };
  },
  name :'admin',
  methods: {
    getout(){
      if(this.$store.state.auth !=='1')
      {
        alert("잘못된 접근입니다. 꺼져 주세요.")
        router.push('/')
      }
    },
    read(){
      axios
        .post("http://192.168.31.85:8080/member/adminManage/", {},
        {
          headers: {
            "login-token": localStorage.getItem("login-token")
          }
        })
        .then(response => {
            this.users = response.data.memberList;
            this.status = response.data.checkCrawling;
            console.log(this.users);
            console.log(this.status);
        })
        .catch((error) => {
          console.log(error)
        })
    },
  },

  mounted(){
    alert("mount test");
    this.getout();
    this.read();
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