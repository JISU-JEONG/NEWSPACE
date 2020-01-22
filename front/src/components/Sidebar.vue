<template>
  <div>
    <v-menu
      left
      bottom
    >
      <template v-slot:activator="{ on }">
        <v-btn icon v-on="on">
          <v-app-bar-nav-icon></v-app-bar-nav-icon>
          </v-btn>
      </template>

      <v-list>
        <v-list-item v-for="item in itemMenus" :key="item.title" @click="() => {}" :to="item.path">
          <v-list-item-title>{{item.title}}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
</template>

<script>
//import HelloWorld from "./components/HelloWorld";

export default {
  data(){
    return {
      itemMenus: [
          { title: 'Home', path: '/'},
          { title: 'Portfolio', path: '/portfolio'},
          { title: 'Post', path: '/post'}
     ]
    }
  },
};
</script>