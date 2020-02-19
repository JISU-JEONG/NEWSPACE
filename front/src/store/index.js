import Vue from "vue";
import Vuex from "vuex";
import http from '../services/http-common'
// import news from './modules/news'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: null,
    member_id: null,
    member_name: null,
    auth: null,
    member_keyword: '',
    member_news: [],
    error: false,
    preRouter:'', // 로그인시 이전 페이지로 이동위한 변수
  },
  mutations: {
    // 첫번째 인자는 무조건 state
    // 이후 인자는 payload(즉, 임의의 매개변수)
    setToken(state, payload) {
      state.token = payload.token;
      state.auth = payload.auth;
      state.member_id = payload.member_id;
      state.member_name = payload.member_name;
      state.member_keyword = payload.member_keyword
      state.member_news = payload.member_news
    },
    setError(state) {
      state.error = true;
    },
    setMemberNews(state, payload) {
      state.member_news = payload.member_news
    }

  },
  actions: {
    // 첫번째 인자는 context (다양한)
    // 이후 인자는 payload(임의의 매개변수)
    login(context, payload) {
      // mutation 호출 -> commit
      context.commit("setToken", payload);
    },
    logout(context) {
      console.log("logout");
      context.commit("setToken", {
        token: null,
        member_id: null,
        member_name: '',
        auth: null,
        member_keyword: '',
        member_news: [],
      });
      console.log(context.state.member_name)
    },
    error(context) {
      context.commit("setError");
    },
    setMemberNews(context) {
      context.state.member_news = []
      const settingArray = []
        if (context.state.member_keyword) {
          http
            .get(`/getUserKeywordNews/${context.state.member_keyword}`)
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
                        news_keyword: news.keyword,
                      },
                    ])
                  })
                  settingArray.push(addObject)
                })
                localStorage.setItem('member_news', JSON.stringify(settingArray))
                context.commit('setMemberNews', {
                  member_news: settingArray
                })
              })
          }

        }
      }    
});