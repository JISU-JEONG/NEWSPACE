<template>
<div>
  <div class="header_box">
      <div class="header_name">"{{user.member.name}}"님 환영합니다.</div>
      <div class="header_email">{{user.member.email}}</div>
      <div class="header_email" >
        <v-btn text v-if="isemailcheck !== 'true'" @click="emailcheck()">이메일 인증하기</v-btn>
        <span v-else>이메일 인증 완료</span>
      </div>
    <div class="comment_font">
      <div>
        <h1>{{user.count}}</h1>
      </div>
      <div>
        <p>comments</p>
      </div>
    </div>
  </div>
  <v-container class="px-1" style="min-height:850px">
      <v-tabs
      vertical
      >
        <v-tab>
          관심 뉴스
        </v-tab>
        <v-tab>
          최근 본 뉴스
        </v-tab>
        <v-tab>
          관심 키워드
        </v-tab>
  
        <v-tab-item class="px-2">
          <v-container fluid v-if="like_list.length !== 0">
            <div style="min-height:800px">

            <div class="likeheader">
              <strong>{{user.member.name}}'s NEWS ROOM</strong>
              <hr>
            </div>
            <v-row>
              <v-col
                v-for="i in like_list[page-1].length"
                :key="i"
                @click="goDetail(like_list[page-1][i-1].news_id)"
                xs=12
                sm=5
                md=4
                lg=3>
                <v-card
                  height=350px
                >
                  <v-img
                    :src="user.list[i-1].thumb"
                    height=150px
                  ></v-img>

                  <v-card-title>
                  <h4  v-line-clamp="2">
                    {{user.list[i-1].title}}
                  </h4>
                  </v-card-title>

                  <v-card-subtitle>
                  <p  v-line-clamp="5">
                    {{user.list[i-1].bodytext}}
                  </p>
                  </v-card-subtitle>
                </v-card>
              </v-col>
            </v-row>
            </div>
            <div class="text-center">
            <v-pagination
              v-model="page"
              :length=pagetotal
              circle
              total-visible="6"
            >
            </v-pagination>
          </div>
          </v-container>
          <v-container v-else
          >
            관심 뉴스를 등록해 보세요~
          </v-container>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <v-card-text>
              <p>
                Morbi nec metus. Suspendisse faucibus, nunc et pellentesque egestas, lacus ante convallis tellus, vitae iaculis lacus elit id tortor. Sed mollis, eros et ultrices tempus, mauris ipsum aliquam libero, non adipiscing dolor urna a orci. Curabitur ligula sapien, tincidunt non, euismod vitae, posuere imperdiet, leo. Nunc sed turpis.
              </p>
  
              <p>
                Suspendisse feugiat. Suspendisse faucibus, nunc et pellentesque egestas, lacus ante convallis tellus, vitae iaculis lacus elit id tortor. Proin viverra, ligula sit amet ultrices semper, ligula arcu tristique sapien, a accumsan nisi mauris ac eros. In hac habitasse platea dictumst. Fusce ac felis sit amet ligula pharetra condimentum.
              </p>
  
              <p>
                Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Nam commodo suscipit quam. In consectetuer turpis ut velit. Sed cursus turpis vitae tortor. Aliquam eu nunc.
              </p>
  
              <p>
                Etiam ut purus mattis mauris sodales aliquam. Ut varius tincidunt libero. Aenean viverra rhoncus pede. Duis leo. Fusce fermentum odio nec arcu.
              </p>
  
              <p class="mb-0">
                Donec venenatis vulputate lorem. Aenean viverra rhoncus pede. In dui magna, posuere eget, vestibulum et, tempor auctor, justo. Fusce commodo aliquam arcu. Suspendisse enim turpis, dictum sed, iaculis a, condimentum nec, nisi.
              </p>
            </v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <v-card-text>
              <p>
                Fusce a quam. Phasellus nec sem in justo pellentesque facilisis. Nam eget dui. Proin viverra, ligula sit amet ultrices semper, ligula arcu tristique sapien, a accumsan nisi mauris ac eros. In dui magna, posuere eget, vestibulum et, tempor auctor, justo.
              </p>
  
              <p class="mb-0">
                Cras sagittis. Phasellus nec sem in justo pellentesque facilisis. Proin sapien ipsum, porta a, auctor quis, euismod ut, mi. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nam at tortor in tellus interdum sagittis.
              </p>
            </v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs>
    <!-- <v-card>
      <v-card-title>
        "{{user.member.name}}"님이 선택한 키워드
        <v-btn text class="updatedfont" @click.stop="dialog = true">수정</v-btn>
      </v-card-title>
      <v-card-text>
        <span v-for="k in user.member.keyword.split(' ')" :key="k">#{{ k }}</span>
      </v-card-text>
    </v-card>

    <div class="body_box">
      <div class="newsbody">{{user.member.name}}'s NEWS ROOM</div>
      <v-card
        v-for="i in user.list.length"
        :key="i"
        @click="goDetail(user.list[i-1].news_id)"
      >
        <v-container>
          [{{user.list[i-1].brand}}] {{user.list[i-1].title}}
        </v-container>
      </v-card>
    </div>

    <v-dialog v-model="dialog" max-width="400">
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
                  <li
                    v-for="keyword in selectedKeywords"
                    :key="keyword"
                    class="text--darken-3"
                  >{{keyword}}</li>
                </transition-group>
              </ul>
            </v-container>
          </v-card>
          <hr />
          <v-card style="min-height:120px;" class="mt-3">
            <span>선택되지 못한 아해들</span>
            <br />
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
          /> -->
        <!-- </v-container>
        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="green darken-1" text @click.stop="OkClick()">ok</v-btn>

          <v-btn color="green darken-1" text @click.stop="cancleClick()">Cancle</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog> -->
  </v-container>
</div>
</template>
<script>
import http from "../services/http-common.js";
import router from "../router";
import info from "../services/getInfo";

export default {
  name: "Profile",
  data() {
    return {
      user: null,
      dialog: false,
      userInputKeyword: "",
      selectedKeywords: [],
      unselectedKeywords: [],
      error: false,
      errorMessages: "",
      isemailcheck: localStorage.getItem("certifiedkey"),
      page: 1,
      like_list : [],
      pagetotal : 0,
    };
  },
  methods: {
    get_user() {
      const token = {
        headers: {
          "login-token": localStorage.getItem("login-token")
        }
      };
      http
        .post(
          "/profile/",
          {
            member_id: localStorage.getItem("member_id")
          },
          token
        )
        .then(response => {
          this.user = response.data;
          this.selectedKeywords = this.user.member.keyword.split(" ");
          this.isemailcheck = localStorage.getItem("certifiedkey");
          this.keywordSetting();
          let newslist = []
          if( this.user.list !==[]){
            for(let i=0;i<this.user.list.length;i++)
            {
              newslist.push(this.user.list[i])
              if (newslist.length===8)
              {
                this.like_list.push(newslist)
                newslist = []
              }
            }
            if (newslist.length!==0)
            {
              this.like_list.push(newslist)
            }
            this.pagetotal = this.like_list.length
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    emailcheck() {
      const token = {
        headers: {
          "login-token": localStorage.getItem("login-token")
        }
      };
      http
        .get("/sendmail/", token)
        .then(response => {
        })
        .catch(error => {
          console.log(error);
        });
    },
    goDetail(id) {
      router.push({ name: "detail", params: { id: id } });
    },
    onClickKeyword(key) {
      this.$router
        .push({
          name: "search",
          params: { searchValue: key }
        })
        .catch(err => {});
    },
    userInputKeywordToList() {
      let errorKeyword = "";
      if (this.userInputKeyword.length < 2) {
        this.error = true;
        this.errorMessages = "두글자 이상을 입력해주세요";
      } else {
        this.userInputKeyword.split(" ").forEach(value => {
          if (
            this.selectedKeywords.findIndex(v => v === value) < 0 &&
            value.length > 1
          ) {
            this.selectedKeywords.push(value);
          } else {
            errorKeyword = errorKeyword.concat(` ${value}`);
          }
        });
        errorKeyword = errorKeyword.trim();
        if (errorKeyword) {
          this.error = true;
          this.errorMessages = `${errorKeyword}은(는) 이미 추가되었거나 너무 짧은 단어입니다.`;
        }
      }
      this.userInputKeyword = "";
    },
    inputKeyword(value) {
      if (value.length) {
        this.error = false;
        this.errorMessages = "";
      }
    },
    unselectKeyword(event) {
      if (event.target.tagName === "LI") {
        const keyword = event.target.innerText;
        const index = this.selectedKeywords.findIndex(v => v === keyword);
        if (this.unselectedKeywords.findIndex(v => v === keyword) < 0) {
          this.unselectedKeywords.push(keyword);
        }
        this.selectedKeywords.splice(index, 1);
      }
    },
    selectKeyword(event) {
      if (event.target.tagName === "LI") {
        const keyword = event.target.innerText;
        const index = this.unselectedKeywords.findIndex(v => v === keyword);
        if (this.selectedKeywords.findIndex(v => v === keyword) < 0) {
          this.selectedKeywords.push(keyword);
        }
        this.unselectedKeywords.splice(index, 1);
      }
    },
    keywordSetting() {
      http.get("/getUserKeyword").then(response => {
        this.unselectedKeywords = response.data;
        for (let i = 0; this.selectedKeywords.length; i++) {
          const keyword = this.selectedKeywords[i];
          const index = this.unselectedKeywords.findIndex(v => v === keyword);
          if (index < 0) {
            return;
          }
          this.unselectedKeywords.splice(index, 1);
        }
      });
    },
    OkClick() {
      const token = {
        headers: {
          "login-token": localStorage.getItem("login-token")
        }
      };
      const options = {
        name: this.user.member.name,
        inputkeyword: this.selectedKeywords
      };
      http
        .post("/profileupdate", options, token)
        .then(response => {
          this.userInputKeyword = "";
          this.dialog = false;
          this.user.member = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    cancleClick() {
      this.userInputKeyword = "";
      this.dialog = false;
    }
  },
  beforeMount() {
    info();
  },
  mounted() {
    this.get_user();
  },
};
</script>
<style scoped>
.header_box {
  width: 100%;
  height: 360px;
  background-color : rgba(130, 215, 255, 0.863);
  padding-top:100px;
  margin-bottom: 30px;
}
.header_name {
  font-size: 30px;
  text-align: center;
  color : white;
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
.comment_font {
  text-align: center;
  margin: 30px;
}
.updatedfont {
  font-size: 10px;
  color: gray;
  margin-left: 10px;
}
.updatedfont:hover {
  font-size: 14px;
  color: black;
  margin-left: 7px;
}
.likeheader{
  font-size : 20px;
  margin-bottom: 20px;
}
ul {
  margin: 0;
  padding: 0;
}
li {
  list-style: none;
  display: inline-block;
  margin: 0 0 0 8px;
  cursor: pointer;
}
li:hover {
  transform: scale(1.2);
}
.list-enter-active{
  transition: all 1s;
}
.list-enter {
  opacity: 0;
  transform: translateY(30px);
}
</style>
