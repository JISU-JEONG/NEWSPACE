<template>
  <nav v-if="$route.name !== 'Admin' && $route.name !== 'AboutUs'">
    <chat ref="chat"/>
    <v-app-bar color="blue lighten-2" dark app :clipped-right="$vuetify.breakpoint.smAndUp" short  id="navbar">
      <v-btn to="/" text v-if="$vuetify.breakpoint.smAndUp">
        <span>New space</span>
      </v-btn>
      <v-btn to="/" icon v-else>
        <v-icon>mdi-home</v-icon>
      </v-btn>
      <v-btn to="/Admin" text v-if="this.$store.state.auth==='1'">
        <span>Admin</span>
      </v-btn>
      <v-spacer />
      <v-form style="width:350px;" @submit.prevent="onSubmit(searchValue)">
        <v-text-field
          v-model="searchValue"
          label="검색"
          hide-details
          clearable
          append-outer-icon="mdi-magnify"
          style="border-radius:5px;"
          @click:append-outer.stop="onSubmit(searchValue)"
          autocomplete="off"
        />
      </v-form>
      <v-spacer />
      <v-btn text @click.stop="changeDrawer()" class="ml-3">
        <v-icon>mdi-menu</v-icon>
      </v-btn>
    </v-app-bar>
    <!-- 사이드바 -->
    <v-navigation-drawer
      id="sidenav"
      v-model="drawer"
      app
      right
      :disable-resize-watcher="true"
      :clipped="$vuetify.breakpoint.smAndUp"
    >
      <!-- 로그인시 보여지는 사이드바 타이틀 -->
      <div v-if="this.$store.state.token">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>
              <v-btn text to="/Profile">{{username}}</v-btn>
            </v-list-item-title>
          </v-list-item-content>
          <v-btn text v-on:click="logout">로그아웃</v-btn>
        </v-list-item>
      </div>
      <!-- 비로그인시 보여지는 사이드바 타이틀 -->
      <div v-else>
        <v-list-item>
          <v-btn text to="/login">로그인</v-btn>
          <v-spacer />
          <v-btn text to="/signup">회원가입</v-btn>
        </v-list-item>
      </div>

      <v-divider></v-divider>

      <v-list dense>
        <v-container v-if="!member_keyword && !username">
          회원가입, 로그인하여 더 많은 정보를 받아보세요!
        </v-container>
        <v-container v-else-if="!member_keyword && username">
          프로필페이지에서 관심 키워드를 등록하면 더 많은 정보를 받아볼 수 있습니다.
        </v-container>
        <span v-else>
          <template v-for="item in member_news">
            <v-list-group
              v-if="item.children"
              :key="item.text"
              v-model="item.model"
              :prepend-icon="item.model ? item.icon : item['icon-alt']"
              append-icon
            >
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>{{ item.date }}</v-list-item-title>
                </v-list-item-content>
                <v-badge
                  inline
                  color="red"
                  v-show="item.children.length"
                  :content="item.children.length"
                ></v-badge>
              </template>
              <v-list-item
                v-for="(child, i) in item.children"
                :key="i"
                link
                :to="{ name: 'detail', params: { id: child.news_id, keyword: ' '} }"
                :class="child.news_brand"
              >
                <v-list-item-content>
                  <v-tooltip top>
                    <template v-slot:activator="{ on }">
                      <v-list-item-title v-on="on">{{ child.news_title }}</v-list-item-title>
                    </template>
                    <span>{{child.news_title}}</span>
                  </v-tooltip>
                </v-list-item-content>
              </v-list-item>
            </v-list-group>
          </template>
        </span>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>
import store from "../store";
import http from "../services/http-common";
import info from "../services/getInfo";
import router from "../router";
import Chat from "../components/Chat";
import axios from "axios";
export default {
  name: "Navigation",
  components: {
    Chat
  },
  props: {},
  computed: {
    username() {
      return this.$store.state.member_name;
    },
    member_keyword() {
      return this.$store.state.member_keyword;
    },
    member_news() {
      return this.$store.state.member_news;
    }
  },
  data() {
    return {
      searchValue: "",
      drawer: false,
      dialog: false
    };
  },
  methods: {
    changeDrawer() {
      // 사이드바
      this.drawer = !this.drawer;
    },
    onSubmit(searchValue) {
      // 검색
      this.$router
        .push({
          name: "search",
          params: { searchValue: searchValue }
        })
        .catch(err => {});
      this.searchValue = "";
    },
    logout() {
      axios
        .post("http://192.168.31.85:8080/member/logout", {
          email: localStorage.getItem("member_email")
        })
        .then(res => {
        })
        .catch(e => {
          console.log(e);
        });
      localStorage.removeItem("login-token");
      localStorage.removeItem("member_id");
      localStorage.removeItem("member_name");
      localStorage.removeItem("member_keyword");
      localStorage.removeItem("loginStatus");
      localStorage.removeItem("auth");
      localStorage.removeItem("member_news");
      localStorage.setItem("loginStatus", false);
      this.auth = 0;
      this.$store.dispatch("logout");
      this.$refs.chat.chatdistconnet();
      if (this.$route.name === "Profile") {
        router.push("/");
      }
    }
  },
  beforeMount() {
    info();
  }
};
</script>
<style scoped> 
.SAMSUNG {
  background-color: rgba(20, 40, 160, 0.1);
}

.LG {
  background-color: rgba(165, 0, 52, 0.1);
}

.SK {
  background-color: rgba(219, 16, 38, 0.1);
}

.SAMSUNG .v-list-item__title {
  color: rgb(20, 40, 160);
  font-weight: 600;
}

.LG .v-list-item__title {
  color: rgb(165, 0, 52);
  font-weight: 600;
}

.SK .v-list-item__title {
  color: rgba(219, 16, 38);
  font-weight: 600;
}
</style>