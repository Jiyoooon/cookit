import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'
import cookies from 'vue-cookies'


Vue.use(Vuex)
axios.defaults.baseURL = 'http://6d04d76f2aaa.ngrok.io';
const moduleAccounts = {
  namespaced: true,
  state: {
    authToken: cookies.get('auth-token'),
    
  },
  getters: {
    IsLoggedIn(state) {
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
    RedirectAfterUserUpdate() {
      console.log('!!!')
      router.push({ name: 'UserInfoView' })
    },

    RedirectAfterUserDelete() {
      router.push({ name: 'UserInfoView' })
    },

    login({ commit }, loginData) {
      axios.post('loginurl', loginData)
        .then((res) => {
          console.log(res.data)
          commit('SET_TOKEN', res.data)
          router.push({ name: '첫화면'})
        })
        .catch( err => console.log(err.response))
    },

    // logout() {
    //   axios.post('logouturl', null, state.config)
    //     .then(res => {
    //       console.log(res.data)
    //       router.push({ name: '첫화면'})
    //     })
    //     .catch(err => console.log(err.response))
    // },

    emailAuthCodeSend(context, email) {
      console.log(email)
      axios.get(`/user/verification/send/${String(email)}`)
      .then(res => {
        console.log(`Code send: ${String(res.data.result)}`)
      })
      .catch(err => console.log(err.response))
    },

    emailAuthCodeCheck(context, code) {
      console.log(code)
      axios.get(`/user/verification/check/${String(code)}`)
      .then(res => {
        console.log(`Code check: ${String(res.data.result)}`)
        if (res.data.result == 'success') router.push({name: 'SignUp'})
        else if (res.data.result == 'fail') alert("코드가 맞지 않습니다.")
      })
      .catch(err => console.log(err.response))
    }
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
