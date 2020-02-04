<template>
  <div v-if="user !== null">
		<v-container>
			<div class= "header_box">
					<div class="header_name">
						"{{user.member.name}}"님 환영합니다.
					</div>
					<div class="header_email">
						{{user.member.email}}
					</div>
					<button v-on:click="emailcheck()"></button>
			</div>
			<div class="comment_font">
				<div>
					<h1>
						{{user.count}}
					</h1>
				</div>
				<div>
					<p>comments</p>
				</div>
			</div>
			<v-card>
				<v-card-title>
					"{{user.member.name}}"님이 선택한 키워드
				</v-card-title>
				<v-card-text>
					<span v-for="k in user.member.keyword.split(' ')" :key="k">
						#{{ k }}
					</span>
				</v-card-text>
			</v-card>
		</v-container>
		<v-container>
			<div class="body_box">
				<div class ="newsbody">
						{{user.member.name}}'s NEWS ROOM
				</div>
				<v-card v-for="i in user.list.length" :key="i">
					[{{user.list[i-1].brand}}] {{user.list[i-1].title}}
				</v-card>
			</div>
		</v-container>
  </div>
	<div v-else>
	</div>
</template>
<script>
import http from "../services/http-common.js";

export default {
  name: 'Profile',
  data(){
      return{
          user: null,
      }
  },
  methods: {
    get_user() {
      const token = {
        headers: {
          "login-token": localStorage.getItem("login-token")
		}
	  }
        http.post("/profile/",{
            member_id : localStorage.getItem("member_id")
        }, token)
            .then((response) => {
                this.user = response.data
                console.log(this.user)
            })
            .catch((error) => {
                console.log(error)
            })
	  },
	  emailcheck(){
		const token = {
			headers: {
			"login-token": localStorage.getItem("login-token")
			}
		};
		http
			.get(
			"/sendmail/",
			token
			)
			.then(response => {
			console.log(response.data)
			})
			.catch(error => {
			console.log(error);
			});
	  }
  },
  mounted(){
	this.get_user()
  }
}
</script>
<style scoped>
.header_box {
  margin: 50px 0px;
}
.header_name {
  font-size: 30px;
  text-align: center;
}
.header_email {
  font-size: 20px;
  text-align: center;
  color: grey;
}
.body_box {
  margin: 50px 0px;
}
.newsbody {
  font-size: 30px;
}
.comment_font{
	text-align: center;
	margin: 30px
}
</style>
