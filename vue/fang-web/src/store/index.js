import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    userInfo: ''
  },
  mutations: {
    setUserInfo (state, userInfo) {
      state.userInfo = userInfo
    },
    clearUserInfo (state) {
      state.userInfo = ''
    },
    setToken (state, token) {
      state.token = token
    },
    clearToken (state) {
      state.token = ''
    }
  },
  actions: {
  },
  modules: {
  }
})
