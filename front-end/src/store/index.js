import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'

Vue.use(Vuex)

const moduleAccounts = {
  namespaced: true,
  state: {

  },
  getters: {

  },

  mutations: {

  },

  actions: {
    RedirectAfterUserUpdate() {
      console.log('!!!')
      router.push({ name: 'UserInfoView' })
    },
    RedirectAfterUserDelete() {
      router.push({ name: 'UserInfoView' })
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
