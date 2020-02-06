<template>
  <div v-if="user !== null" class="test">
		<v-container>
			<div class= "header_box">
					<div class="header_name">
						"{{user.member.name}}"님 환영합니다.
					</div>
					<div class="header_email">
						{{user.member.email}}
					</div>
					<button v-on:click="emailcheck()">이메일체크</button>
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
					<v-btn text class="updatedfont"
  	      @click.stop="dialog = true">
						수정
					</v-btn>
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
				<v-card v-for="i in user.list.length" :key="i" @click="goDetail(user.list[i-1].news_id)">
					[{{user.list[i-1].brand}}] {{user.list[i-1].title}}
				</v-card>
			</div>

      <v-dialog
        v-model="dialog"
        max-width="400"
      >
        <v-card>
					<v-container style="min-height:430px;">
					<v-card-title>관심 키워드 수정</v-card-title>
            <v-divider></v-divider>
					<v-card style="min-height:120px;">
              <span px-3>선택된 아해들</span>
              <v-divider></v-divider>
              <v-container>
                <ul @click.stop="unselectKeyword">
                  <transition-group name="list">
                    <li v-for="keyword in selectedKeywords" :key="keyword" class="text--darken-3">{{keyword}}</li>
                  </transition-group>
                </ul>
              </v-container>
          </v-card>
          <hr>
					<v-card style="min-height:120px;" class="mt-3" >
            <span>선택되지 못한 아해들</span> <br>
            <v-divider></v-divider>
            <v-container>
              <ul @click.stop="selectKeyword">
                <transition-group name="list" id="selectedSpan">
                  <li v-for="keyword in unselectedKeywords" :key="keyword">{{keyword}}</li>
                </transition-group>
              </ul>
            </v-container>
          </v-card>
					<v-text-field 
              style="margin-top:16px;"
              label="추가하고싶은 키워드"
              v-model="userInputKeyword"
              append-icon="mdi-plus"
              @click:append="userInputKeywordToList"
              @keyup.enter.stop="userInputKeywordToList"
              :error="error"
              :error-messages="errorMessages"
              @input="inputKeyword"
            />
						</v-container>
          <v-card-actions>
            <v-spacer></v-spacer>
  
            <v-btn
              color="green darken-1"
              text
              @click.stop="OkClick()"
            >
              ok
            </v-btn>
  
            <v-btn
              color="green darken-1"
              text
              @click.stop="cancleClick()"
            >
              Cancle
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
		</v-container>
  </div>
	<div v-else>
	</div>
</template>
<script>
import http from "../services/http-common.js";
import router from '../router'

export default {
  name: 'Profile',
  data(){
      return{
					user: null,
					dialog: false,
					userInputKeyword: '',
					selectedKeywords: [],
					unselectedKeywords: [],
					error: false,
      		errorMessages: '',
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
								this.selectedKeywords = this.user.member.keyword.split(' ')
								this.keywordSetting()
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
	  },
		goDetail(id) {
			router.push({ name: 'detail', params: { id: id }})
			},
			onClickKeyword(key){
				this.$router.push({ 
				name: 'search',
				params: { searchValue: key }
			})
			.catch(err =>{})
		},
		userInputKeywordToList() {
      let errorKeyword = ''
      if (this.userInputKeyword.length < 2) {
        this.error = true
        this.errorMessages = "두글자 이상을 입력해주세요"
      } else {
          this.userInputKeyword.split(' ').forEach( value => {
            if (this.selectedKeywords.findIndex(v => v === value) < 0 && value.length > 1 ) {
              this.selectedKeywords.push(value)
            } else {
              errorKeyword = errorKeyword.concat(` ${value}`)
              console.log(errorKeyword)
            }
          })
          errorKeyword = errorKeyword.trim()
          if (errorKeyword) {
            this.error = true
            this.errorMessages = `${errorKeyword}은(는) 이미 추가되었거나 너무 짧은 단어입니다.`
          }
        }
      this.userInputKeyword = ''

		},
		inputKeyword(value) {
      if (value.length) {
        this.error = false
        this.errorMessages = ''
      } 
		},
		unselectKeyword(event) {
      if (event.target.tagName === "LI") {
        const keyword = event.target.innerText
        const index = this.selectedKeywords.findIndex(v => v === keyword)
        if (this.unselectedKeywords.findIndex(v => v === keyword) < 0) {
          this.unselectedKeywords.push(keyword)
        }
        this.selectedKeywords.splice(index, 1)
      }
		},
		selectKeyword(event) {
      if (event.target.tagName === "LI") {
        const keyword = event.target.innerText
        const index = this.unselectedKeywords.findIndex(v => v === keyword)
        if (this.selectedKeywords.findIndex(v => v === keyword) < 0) {
          this.selectedKeywords.push(keyword)
        }
        this.unselectedKeywords.splice(index, 1)
      }
		},
		keywordSetting() {
      http.get("/getUserKeyword")
        .then(response => {
					this.unselectedKeywords = response.data
					for(let i=0; this.selectedKeywords.length;i++){
						const keyword = this.selectedKeywords[i]
						const index = this.unselectedKeywords.findIndex(v => v === keyword)
						if (index < 0) {
							return
						}
						this.unselectedKeywords.splice(index, 1)
					}
        })
		},
		OkClick(){
			const token = {
        headers: {
          "login-token": localStorage.getItem("login-token")
					}
	  		}
			const options = {
				name : this.user.member.name,
				inputkeyword : this.selectedKeywords
			}
			http.post('/profileupdate', options, token)
				.then((response) => {
								this.userInputKeyword = ''
								this.dialog = false
								this.user.member = response.data
							})
				.catch((error) => {
					console.log(error)
			})
		},
		cancleClick(){
			this.userInputKeyword = ''
			this.dialog = false
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
.updatedfont{
	font-size:10px;
	color:gray;
	margin-left: 10px;
}
.updatedfont:hover{
	font-size:14px;
	color:black;
	margin-left: 7px;
}
</style>
