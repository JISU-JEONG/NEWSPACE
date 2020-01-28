<template>
<!-- 곧 지워질 컴포넌트입니다. -->
  <v-navigation-drawer
    :value="drawer"
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
</template>

<script>
  export default {
    props: {
      source: String,
    },
    computed: {
      drawer() {
        return this.$store.state.drawer
      }
    },
    data: () => ({
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
    }),
  }
</script>
