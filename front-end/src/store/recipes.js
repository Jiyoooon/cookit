import router from '../router'
import axios from 'axios'
import SERVER from '../api/url.js'

export default {
  namespaced: true,
  state: {
    selectedRecipe: null,
    recipeUser: null,
  },

  getters: {

  },

  mutations: {
    SET_RECIPE(state, recipe) {
      state.selectedRecipe = recipe
    },
    SET_USER(state, userinfo) {
      state.recipeUser = userinfo
    },
  },

  actions: {
    fetchRecipe({ commit }, recipe_id) {
      axios.get(SERVER.ROUTES.recipeview.fetchrecipe + String(recipe_id))
        .then(res => {
          commit('SET_RECIPE', res.data)
          router.push({ name: 'SelectedRecipe'})
        })
    },
    fetchRecipeUser({ commit, state }) {
      axios.get(SERVER.ROUTES.accounts.fetchuserinfo + String(state.selectedRecipe.recipe_user))
        .then(res => {
          commit('SET_USER', res.data.data)
        })
    },
    recipeLike({ rootGetters, dispatch }, recipe_id) {
      axios.get(SERVER.ROUTES.myrecipe.recipelike + String(recipe_id) +'/like', rootGetters['accounts/config'])
      .then(() => {
        dispatch('fetchMyRecipes', null, { root: true })
      })
    }
  },
}