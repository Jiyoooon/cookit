import Vue from 'vue'
import Vuex from 'vuex'

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
    accounts: modulelAccounts,
    recipes: moduleRecipes,
    myrecipes: moduleMyRecipes,
  }
})
