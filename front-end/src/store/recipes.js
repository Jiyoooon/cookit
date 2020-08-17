import router from '../router'
import axios from 'axios'
import SERVER from '../api/url.js'

export default {
  namespaced: true,
  state: {
    selectedRecipe: null,
    recipeUser: null,
    comments: null,
    selectedcomment: {
      id: null,
      tf: false,
    },
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
    SET_COMMENTS(state, comments) {
      state.comments = comments
    },
    SET_SELECTEDCOMMENT(state, id) {
      state.selectedcomment.id = id
    },
  },

  actions: {
    goRecipe({ dispatch },  recipe){
      console.log(recipe)
      // commit('SET_RECIPE', recipe)
      dispatch('fetchRecipe', recipe.recipe_id)
      router.push({ name: 'SelectedRecipe', params: { recipe_id: recipe.recipe_id }})
    },
    fetchRecipe({ commit }, recipe_id) {
      axios.get(SERVER.ROUTES.recipeview.fetchrecipe + String(recipe_id))
        .then(res => {
          commit('SET_RECIPE', res.data)
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
    },
    createComment({ state, rootGetters, dispatch }, commentData) {
      if(!commentData.description) return;
      axios.post(SERVER.ROUTES.myrecipe.commentcreate + String(state.selectedRecipe.recipe_id) + '/comments', commentData, rootGetters['accounts/config'])
      .then(res => {
        console.log(res.data)
        dispatch('fetchComments')
        // router.push({ name: 'SelectedRecipe', params: { recipe_id: state.selectedRecipe.recipe_id } })
      })
    },
    fetchComments({ state, commit }) {
      axios.get(SERVER.ROUTES.myrecipe.fetchcomments + String(state.selectedRecipe.recipe_id) + '/comments')
      .then(res => {
        commit('SET_COMMENTS', res.data)
        console.log(res.data)
      })
    },
    deleteComment({ rootGetters, state, dispatch }, comment_id) {
      axios.delete(SERVER.ROUTES.myrecipe.deletecomment + String(state.selectedRecipe.recipe_id) + '/comments/' + String(comment_id), rootGetters['accounts/config'])
      .then(res => {
        console.log(res)
        dispatch('fetchComments')
      })
    },
    updateComment({ rootGetters, state, dispatch, commit }, commentData) {
      axios.put(SERVER.ROUTES.myrecipe.updatecomment + String(state.selectedRecipe.recipe_id) + '/comments/' + String(commentData.comment_id), commentData, rootGetters['accounts/config'])
      .then(res => {
        console.log(res)
        dispatch('fetchComments')
        commit('SET_SELECTEDCOMMENT', null)
      })
      .catch(err => {
        console.err(err.response)
      })
    }
  },
}