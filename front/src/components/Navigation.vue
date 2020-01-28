<template>
  <nav>
    <v-app-bar color="blue lighten-2" dark app >
      <v-btn to="/" text>
        <span>New space</span>
      </v-btn>
      <v-btn to="/" icon>
        <v-icon>mdi-home</v-icon>
      </v-btn>
      <v-spacer />
      <v-form style="width:350px;" @submit.prevent="onSubmit">
        <v-text-field 
          v-model="searchValue"
          label="검색"
          hide-details
          clearable
          append-outer-icon="mdi-magnify"
          @click:append-outer.stop="onSubmit"
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
          <v-img src="http://edu.ssafy.com/edu/comm/imgDownload.do?userId=lAenTIrEs1O1LdGdfpnDrw%3D%3D"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>우리 서비스으</v-list-item-title>
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
              <v-list-item-title>
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

export default {
  name: "Navigation",
  data() {
    return {
      searchValue: '',
      drawer: false,
      dialog: false,
      items: [
        { icon: 'mdi-contacts', text: '로그아웃' },
        { icon: 'mdi-history', text: '혹시 게시판을 만들게 된다면' },
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
    onSubmit() {
      this.searchValue = ''
      this.$router.push({ 
        path: '/search'
      })
      console.log('asd')
    }
  },
}
</script>

<style>

</style>