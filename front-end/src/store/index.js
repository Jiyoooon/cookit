import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
// import axios from 'axios'
import cookies from 'vue-cookies'



Vue.use(Vuex)

const moduleAccounts = {
  namespaced: true,
  state: {
    authToken: cookies.get('auth-token'),
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
    DeleteUser() {
      console.log('aaaa')
      const ans = Vue.bvModal.msgBoxConfirm('데이터 복구 안댐', {
          title: '정말로 탈퇴하시겠습니까?',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          okTitle: 'YES',
          cancelTitle: 'NO',
          footerClass: 'p-2',
          hideHeaderClose: false,
          centered: true
      })
      if (ans) {
        // axios.delete('deleteurl')
        //   .then((res) => {
        //     console.log(res.data)
        //     commit('SET_TOKEN', null)
        //     cookies.remove('auth-token')
            router.push({ name: 'UserDelete' })
      }
    },

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

    // login({ commit }, loginData) {
    //   axios.post('loginurl', loginData)
    //     .then((res) => {
    //       console.log(res.data)
    //       commit('SET_TOKEN', res.data)
    //       router.push({ name: '첫화면'})
    //     })
    //     .catch( err => console.log(err.response))
    // },

    // logout() {
    //   axios.post('logouturl', null, state.config)
    //     .then(res => {
    //       console.log(res.data)
    //       router.push({ name: '첫화면'})
    //     })
    //     .catch(err => console.log(err.response))
    // },
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
