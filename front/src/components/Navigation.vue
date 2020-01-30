<template>
  <nav>
    <v-app-bar color="blue lighten-2" dark app>
      <v-btn to="/" text v-if="$vuetify.breakpoint.smAndUp">
        <span>New space</span>
      </v-btn>
      <v-btn to="/" icon v-else>
        <v-icon>mdi-home</v-icon>
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

    <v-navigation-drawer v-model="drawer" app right temporary>
      <!-- 로그인시 보여지는 사이드바 타이틀 -->
      <div v-if="this.$store.state.token">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>
              <v-btn text v-on:click="aboutme">{{username}}</v-btn>
              <v-btn text v-on:click="logout">로그아웃</v-btn>
            </v-list-item-title>
          </v-list-item-content>
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
                <v-list-item-title>{{ item.text }}</v-list-item-title>
              </v-list-item-content>
              <v-badge inline color="red" :content="item.children.length"></v-badge>
            </template>
            <v-list-item v-for="(child, i) in item.children" :key="i" link>
              <v-list-item-action v-if="child.icon">
                <v-icon>{{ child.icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>{{ child.text }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
          <v-list-item v-else :key="item.text" link>
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title @click.stop="logout()">{{ item.text }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>
// import store, { CHANGE_DRAWER } from "../store";
import store from '../store'
import http from "../http-common";
const storage = localStorage;
export default {
  name: "Navigation",
  props: {},
  data() {
    return {
      searchValue: "",
      drawer: false,
      dialog: false,
      username: "",
      userimg: "",
      loginStatus: false,
      items: [
        // { icon: "mdi-contacts", text: "로그아웃", click: "" },
        { icon: "mdi-history", text: "혹시 게시판을 만들게 된다면" },
        {
          icon: "mdi-chevron-up",
          "icon-alt": "mdi-chevron-down",
          text: "2020.01.10",
          model: false,
          children: [{ icon: "mdi-plus", text: "Create label" }]
        },
        {
          icon: "mdi-chevron-up",
          "icon-alt": "mdi-chevron-down",
          text: "2020.01.23",
          model: false,
          children: [
            { text: "Import" },
            { text: "Export" },
            { text: "Print" },
            { text: "Undo changes" },
            { text: "Other contacts" }
          ]
        }
      ]
    };
  },
  methods: {
    changeDrawer() {
      this.drawer = !this.drawer;
      console.log(this.drawer);
    },
    onSubmit(searchValue) {
      this.$router
        .push({
          name: "search",
          params: { searchValue: searchValue }
        })
        .catch(err => {});
      this.searchValue = "";
    },
    getInfo() {
      console.log("login-token: " + storage.getItem("login-token"));
      var token = storage.getItem("login-token");
      if (token != null) {
        http
          .post(
            "/info",
            {},
            {
              headers: {
                "login-token": storage.getItem("login-token")
              }
            }
          )
          .then(res => {
            console.log(JSON.stringify(res.data.data));
            this.username = res.data.data.name;
            this.userimg =
              "../../images/profile.png";
            this.loginStatus = true;
            console.log("res.data.data.member_id : " + res.data.data.member_id)
            storage.setItem("member_id", res.data.data.member_id);
            // console.log(storage.getItem("login-token"));
            // $store.state.token = storage.getItem("login-token");
          })
      } else {
        storage.removeItem("login-token");
        storage.setItem("member_id", "");
        console.log("토큰정보가 없습니다.");
        this.loginStatus = false;
      }
    },
    logout() {
      alert("로그아웃");
      this.loginStatus = false;
      storage.removeItem("login-token");
      storage.setItem("member_id", "");
      this.$store.dispatch('logout')
    },
    aboutme() {
      alert("이거만 구현하면 마지막일까?");
    }
  },
  beforeMount() {
    this.getInfo();
  },
};
</script>