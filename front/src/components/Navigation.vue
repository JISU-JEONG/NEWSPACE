<template>
  <nav v-if="$route.name !== 'Admin'">
    <v-app-bar color="blue lighten-2" dark app :clipped-right="$vuetify.breakpoint.smAndUp">
      <v-btn to="/" text v-if="$vuetify.breakpoint.smAndUp">
        <span>New space</span>
      </v-btn>
      <v-btn to="/" icon v-else>
        <v-icon>mdi-home</v-icon>
      </v-btn>
      <v-btn to="/Admin" text v-if="this.$store.state.auth">
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
          @click:append-outer.stop="onSubmit(searchValue)"
        />
      </v-form>
      <v-spacer />
      <v-btn text @click.stop="changeDrawer()" class="ml-3">
        <v-icon>mdi-menu</v-icon>
      </v-btn>
    </v-app-bar>
    <!-- 사이드바 -->
    <v-navigation-drawer 
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
              <v-btn text to="/Profile">{{usernmae}}</v-btn>
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
        <v-container v-if="!member_keyword">
          회원가입하여 더 많은 정보를 받아보든가
        </v-container>
        <span v-else>
          <template v-for="item in items">
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
                <v-badge inline color="red" :content="item.children.length || '0'"></v-badge>
              </template>
              <v-list-item dark  v-for="(child, i) in item.children" :key="i" link :class="child.news_brand" @click="moveToDetail(child.news_id)">
                <v-list-item-content >
                  <v-tooltip top>
                    <template v-slot:activator="{ on }">
                      <v-list-item-title v-on=on>{{ child.news_title }}</v-list-item-title>
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
import http from "../services/http-common"
import Info from "../services/getInfo";
import router from '../router'

export default {
  name: "Navigation",
  props: {},
  computed: {
    usernmae() {
      return this.$store.state.member_name
    },
    member_keyword() {
      return this.$store.state.member_keyword
    }
  },
  data() {
    return {
      searchValue: "",
      drawer: false,
      dialog: false,
      items: [
      ]
    };
  },
  methods: {
    changeDrawer() { // 사이드바 
      this.drawer = !this.drawer;
    },
    onSubmit(searchValue) { // 검색
      this.$router
        .push({
          name: "search",
          params: { searchValue: searchValue }
        })
        .catch(err => {});
      this.searchValue = "";
    },
    logout() {
      localStorage.removeItem("login-token");
      localStorage.removeItem("member_id");
      localStorage.removeItem("member_name");
      localStorage.removeItem("member_keyword")
      localStorage.removeItem("loginStatus");
      localStorage.removeItem("auth");
      this.auth = 0
      this.$store.dispatch("logout");
    },
    moveToDetail(news_id) {
      router.push({ name: 'detail', params: { id: news_id }})
    },
    init() { // 이거 여기 왜 있나요 영길아?
      if (
        localStorage.getItem("loginStatus") != null &&
        localStorage.getItem("login-token") != null
      ) {
        const payload = {
          token: localStorage.getItem("login-token"),
          member_id: localStorage.getItem("member_id"),
          member_name: localStorage.getItem("loginStatus"),
          auth : localStorage.getItem("auth")
        };
        this.$store.dispatch("login", payload);
      }
    }
  },
  beforeMount() {
    Info();
  },
  watch: {
    member_keyword: function() {
      this.items = []
      if (this.member_keyword) {
        http
          .get(`/getUserKeywordNews/${this.member_keyword}`)
          .then((response) => {
            response.data.forEach(newsMain => {
              let addObject = {
                icon: "mdi-chevron-up",
                "icon-alt": "mdi-chevron-down", 
                model: false,
                date: newsMain.date,
                children: [],
              }
              newsMain.list.forEach(news => {
                addObject.children = addObject.children.concat([
                  {
                    news_id: news.news_id,
                    news_brand: news.brand,
                    news_title: news.title,
                  },
                ])
              })
              this.items.push(addObject)
            })
          })
      }
    }
  }
};
</script>
<style scoped> 
  .SAMSUNG {
    background-color: rgba(20, 40, 160, 0.7)
  }
  .LG {
    background-color: rgba(165, 0, 52, 0.7)
  }
  .SK {
    background-color: rgba(219, 16, 38, 0.7)
  }
</style>