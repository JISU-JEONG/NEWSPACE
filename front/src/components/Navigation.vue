<template>
  <nav>
    <v-app-bar color="blue lighten-2" dark app >
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

    <v-navigation-drawer
      v-model="drawer"
      app
      right
    >
     <v-list-item>
        <v-list-item-avatar>
          <v-img :src="userimg"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>{{username}}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

        <v-list dense>
          <template v-for="item in items">
            <v-list-group
              v-if="item.children"
              :key="item.text"
              v-model="item.model"
              :prepend-icon="item.model ? item.icon : item['icon-alt']"
              append-icon=""
            >
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>
                    {{ item.text }}
                  </v-list-item-title>
                </v-list-item-content>
                <v-badge inline color="red" :content="item.children.length"></v-badge>
              </template>
              <v-list-item
                v-for="(child, i) in item.children"
                :key="i"
                link
              >
                <v-list-item-action v-if="child.icon">
                  <v-icon>{{ child.icon }}</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                  <v-list-item-title>
                    {{ child.text }}
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-group>
            <v-list-item
              v-else
              :key="item.text"
              link
            >
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title v-on:click="logout()">
                {{ item.text }}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>

import {CHANGE_DRAWER} from '../store'

import http from "../http-common"
const storage = localStorage;
export default {
  name: "Navigation",
  data() {
    return {
      searchValue: '',
      drawer: false,
      dialog: false,
      username : "",
      userimg : "",
      items: [
        { icon: 'mdi-contacts', text: '로그아웃', click : ''},
        { icon: 'mdi-history', text: '혹시 게시판을 만들게 된다면'},
        {
          icon: 'mdi-chevron-up',
          'icon-alt': 'mdi-chevron-down',
          text: '2020.01.10',
          model: false,
          children: [
            { icon: 'mdi-plus', text: 'Create label' },
          ],
        },
        {
          icon: 'mdi-chevron-up',
          'icon-alt': 'mdi-chevron-down',
          text: '2020.01.23',
          model: false,
          children: [
            { text: 'Import' },
            { text: 'Export' },
            { text: 'Print' },
            { text: 'Undo changes' },
            { text: 'Other contacts' },
          ],
        },
      ],

    }
  },
  methods: {
    changeDrawer() {
      this.drawer = !this.drawer 
    },
    onSubmit(searchValue) {
      this.$router.push({ 
        name: 'search',
        params: { searchValue: searchValue }
      }).catch(err =>{})
      this.searchValue = ''
    },
    logout(){
      storage.removeItem("login-token");
      this.username = "";
    },
    getInfo() {
      
      console.log("login-token: " + storage.getItem("login-token"));
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
          this.username = res.data.data.name;
          this.userimg = "";
        })
        .catch(e => {
          this.username = "비로그인 상태"
          this.userimg = "https://www.momjobgo.com/momjobgo3ct/wp-content/themes/hello-momjobgo/images/default-profile.jpg";
          console.log("정보 조회 실패 : " + e.response.data.msg);

        });
    },
  },

  mounted(){
    this.getInfo();
  }
}
</script>

<style>

</style>