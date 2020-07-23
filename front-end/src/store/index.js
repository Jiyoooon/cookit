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
    userEmail: cookies.get('user-email'),
  },
  getters: {
    isLoggedIn(state) {
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

     SET_EMAIL(state, email) {
       state.userEmail = email
       cookies.set('user-email', email)
     },
  },

  actions: {
    DeleteUser({ commit }) {
      console.log('aaaa')
      axios.delete(SERVER.ROUTES.accounts.baseuser)
        .then((res) => {
          console.log(res.data)
          console.log('성ㅇ공성공')
          commit('SET_TOKEN', null)
          cookies.remove('auth-token')
          router.push({ name: 'Home' })
        })
        .catch(err => console.log(err.response))
    },

    GoHome({ getters, state }) {
      router.push({ name: 'Home'})
      console.log(getters.IsLoggedIn)
      console.log(state.authToken)
    },

    GoSignup() {
      router.push({ name: 'Signup'})
    },

    GoLogin() {
      router.push({ name: 'Login'})
    },

    GoLogout() {
      router.push({ name: 'Logout'})
    },

    GoUserInfo() {
      router.push({ name: 'UserInfoView'})
    },

    GoEmailAuth() {
      router.push({ name: 'EmailAuthView'})
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
          commit('SET_TOKEN', res.data.uid)
          router.push({ name: 'Home'})
        })
        .catch((err) => {
          console.log(err.response)
          alert(err.response.result)
        })
    },

    logout({ commit }) {
      axios.get(SERVER.ROUTES.accounts.logout)
        .then(res => {
          console.log(res.data)
          commit('SET_TOKEN', null)
          cookies.remove('auth-token')
          router.push({ name: 'Home'})
        })
        .catch(err => console.log(err.response))
    },

    emailAuthCodeSend({ commit }, email) {
      console.log(email)
      alert('인증코드가 발송되었습니다')
      axios.get(`/user/verification/send/${String(email)}`)
      .then(res => {
        console.log(`Code send: ${String(res.data.result)}`)
        commit('SET_EMAIL', email)
      })
      .catch(err => console.log(err.response))
    },

    emailAuthCodeCheck({ commit }, code) {
      console.log(code)
      axios.get(`/user/verification/check/${String(code)}`)
      .then(res => {
        console.log(`Code check: ${String(res.data.result)}`)
        if (res.data.result == 'success') router.push({name: 'SignUp'})
        else if (res.data.result == 'fail') alert("코드가 맞지 않습니다.")
      })
      .catch(err => {
        console.log(err.response)
        commit('SET_EMAIL', null)
        alert('!!!!!!')
       })
    },
    signup({ commit }, signupData) {
      axios.post(SERVER.ROUTES.accounts.signup, signupData)
        .then((res) => {
          console.log(res.data)
          commit('SET_TOKEN', res.data.uid)
          router.push({ name: 'Home'})
        })
        .catch((err) => {
          console.log(err.response)
          alert('!!!!!!')
        })
    },
    nicknameCheck(context, nickname) {
      axios.get(SERVER.ROUTES.accounts.nicknameCheck + String(nickname))
        .then((res) => {
          console.log(res)
        })
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
