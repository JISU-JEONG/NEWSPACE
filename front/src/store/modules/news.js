// 아직 죽어있는 파일입니다... 
import http from '../../http-common'

const state = {
    samsungNews: [],
    lgNews: [],
    skNews: [],
}

const mutations = {
}

const actions = {
  searchNews({ commit, state }, payload) {
    http.get(`http://192.168.31.85:8080/findNews/${payload.searchValue}`)
      .then((response) => {
          state.samsungNews = response.data[0]
          state.lgNews = response.data[1]
          state.skNews = response.data[2]
        console.log(response.data)
      }) 
  }
}


export default {
    state,
    mutations,
    actions
}