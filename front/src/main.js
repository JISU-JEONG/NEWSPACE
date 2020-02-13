import Vue from "vue";
import Vuetify from "vuetify/lib";
import "vuetify/dist/vuetify.min.css";
import VueSimplemde from "vue-simplemde";
import "simplemde/dist/simplemde.min.css";
import "font-awesome/css/font-awesome.min.css";

import App from "./App.vue";
import router from "./router";
import store from "./store";
import VModal from "vue-js-modal";
import VueMinesweeper from "vue-minesweeper";
import lineClamp from 'vue-line-clamp'

Vue.config.productionTip = false;


Vue.use(VueSimplemde);
Vue.use(Vuetify);
Vue.use(VModal, { dynamic: true });
Vue.use(VueMinesweeper);
Vue.use(lineClamp, {})
new Vue({
  router,
  store,
  vuetify: new Vuetify({
    theme: {
      primary: "#3f51b5",
      secondary: "#b0bec5",
      accent: "#8c9eff",
      error: "#b71c1c"
    }
  }),
  render: h => h(App)
}).$mount("#app");