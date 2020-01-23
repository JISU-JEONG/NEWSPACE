import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export const CHANGE_DRAWER = 'CHANGE_DRAWER'


export default new Vuex.Store({
  state: {
		accessToken: '',
    user: '',
    drawer: null
  },
  mutations: {
    [CHANGE_DRAWER](state) {
      state.drawer = !state.drawer
    }
  }
})
