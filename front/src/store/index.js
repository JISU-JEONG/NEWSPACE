import Vue from 'vue'
import Vuex from 'vuex'
// import news from './modules/news'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token : null,
        member_id: null,
        member_name: null
    },
    mutations : {
        // 첫번째 인자는 무조건 state
        // 이후 인자는 payload(즉, 임의의 매개변수)
        setToken(state, payload) {
            state.token = payload.token
            state.member_id = payload.member_id
            state.member_name = payload.member_name
        }
    },
    actions : {
        // 첫번째 인자는 context (다양한)
        // 이후 인자는 payload(임의의 매개변수)
        login(context, payload) {
            // mutation 호출 -> commit
            context.commit('setToken', payload)
        },
        logout(context) {
            context.commit('setToken', {token : null, member_id: null, member_name: null})
        },
    },
    modules: {

    },
})