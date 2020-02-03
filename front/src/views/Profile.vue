<template>
  <div>
		<v-container>
			<div class= "header_box">
					<div class="header_name">
						"{{user.name}}"님 환영합니다.
					</div>
					<div class="header_email">
						{{user.email}}
					</div>
			</div>
			<v-card>
				<v-card-title>
					"{{user.name}}"님이 선택한 키워드
				</v-card-title>
				<v-card-text>
					<span v-for="k in user.keyword.split(' ')" :key="k">
						#{{ k }}
					</span>
				</v-card-text>
			</v-card>
		</v-container>
		<v-container>
			<div class="body_box">
				<div class ="newsbody">
						{{user.name}}'s NEWS ROOM
				</div>
			</div>
		</v-container>
  </div>
</template>
<script>
import http from '../services/http-common.js'

export default {
  name: 'Profile',
  data(){
      return{
          user: {
						keyword:'',
					},
      }
  },
  methods: {
      get_user(){
         const token ={
          headers: {
            "login-token": localStorage.getItem("login-token")
          }
        }
        http.post("/profile/",{
            member_id : this.$store.state.member_id
        }, token)
            .then((response) => {
                this.user = response.data
                console.log(this.user)
            })
            .catch((error) => {
                console.log(error)
            })
      }
  },
  mounted(){
      this.get_user()
  },
};
</script>
<style scoped>
.header_box{
	margin: 50px 0px;
}
.header_name{
    font-size: 30px;
    text-align: center;
}
.header_email{
	  font-size: 20px;
    text-align: center;
		color: grey;
}
.body_box{
	margin: 50px 0px;
}
.newsbody{
	font-size: 30px;
}
</style>
