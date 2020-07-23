import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/url.js'



Vue.use(Vuex)
axios.defaults.baseURL = SERVER.URL;
const moduleAccounts = {
  namespaced: true,
  state: {
    authToken: cookies.get('auth-token'),
    // authUser: cookies.get('auth-user')
  },
  getters: {
    IsLoggined(state) {
      if (state.authToken) {
        return true
      } else {
        return false
      }
    },
    config(state) {
      return {
        headers: {
          Authoriaztion: `Token ${state.authToken}`
        }
      }
    },
  },

  mutations: {
     SET_TOKEN(state, token) {
       state.authToken = token
       cookies.set('auth-token', token)
     },
  },

  actions: {
    // DeleteUser({ commit, getters }) {
    //   console.log('aaaa')
    //   axios.delete(SERVER.ROUTES.accounts.baseuser + )
    //     .then((res) => {
    //       console.log(res.data)
    //       commit('SET_TOKEN', null)
    //       cookies.remove('auth-token')
    //       router.push({ name: 'UserDelete' })
    //     })
    //     .catch(err => console.log(err.response))
    // },

    GoSignup() {
      router.push({ name: 'Signup'})
    },

    RedirectAfterUserUpdate() {
      console.log('!!!')
      router.push({ name: 'UserInfoView' })
    },

    RedirectAfterUserDelete() {
      router.push({ name: 'UserInfoView' })
    },

    login({ commit }, loginData) {
      axios.post(SERVER.ROUTES.accounts.login, loginData)
        .then((res) => {
          console.log(res.data)
          commit('SET_TOKEN', res.data)
          router.push({ name: '첫화면'})
        })
        .catch( err => console.log(err.response))
    },

    logout({ getters }) {
      axios.post(SERVER.ROUTES.accounts.logout, null, getters.config)
        .then(res => {
          console.log(res.data)
          router.push({ name: '첫화면'})
        })
        .catch(err => console.log(err.response))
    },
  },
}

const moduleRecipes = {
  namespaced: true,
  state: {

  },

  getters: {

  },

  mutations: {

  },

  actions: {

  },
}

const moduleMyRecipes = {
  namespaced: true,
  state: {

  },

  getters: {

  },

  mutations: {

  },

  actions: {

  },
}

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    accounts: moduleAccounts,
    recipes: moduleRecipes,
    myrecipes: moduleMyRecipes,
  }
})
