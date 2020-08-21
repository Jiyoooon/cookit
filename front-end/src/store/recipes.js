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
    overlay:false,// 오버레이 띄울지 말지
    timestring:"00:00"// 띄울 시간
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
    // 타이머 관련 설정
    SET_OVERLAY(state,payload){
      state.overlay = payload
    },
    SET_TIMESTRING(state ,payload){
        let mm,ss
        mm = parseInt(payload / 60)
        ss = payload%60+1
        state.timestring = mm+":"+ss
    },
    SET_TIMER_INIT(state){
      state.overlay = false
      state.timestring = ''
    }
  },
  actions: {
    startTimer({commit},payload){
      commit('SET_TIMESTRING',payload)
    },
    hitupRecipe({ state }) {
      axios.get(SERVER.ROUTES.recipeview.fetchrecipe + String(`hits/${state.selectedRecipe.recipe_id}`))
    },
    goRecipe(context, recipe){
      context.dispatch('fetchRecipe', recipe.recipe_id)
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
      .then(() => {
        dispatch('fetchComments')
        // router.push({ name: 'SelectedRecipe', params: { recipe_id: state.selectedRecipe.recipe_id } })
      })
    },
    fetchComments({ commit,state }) {
      axios.get(SERVER.ROUTES.myrecipe.fetchcomments + String(state.selectedRecipe.recipe_id) + '/comments')
      .then(res => {
        commit('SET_COMMENTS', res.data)
      })
    },
    deleteComment({ rootGetters, state, dispatch }, comment_id) {
      axios.delete(SERVER.ROUTES.myrecipe.deletecomment + String(state.selectedRecipe.recipe_id) + '/comments/' + String(comment_id), rootGetters['accounts/config'])
      .then(() => {
        dispatch('fetchComments')
      })
    },
    updateComment({ rootGetters, state, dispatch, commit }, commentData) {
      axios.put(SERVER.ROUTES.myrecipe.updatecomment + String(state.selectedRecipe.recipe_id) + '/comments/' + String(commentData.comment_id), commentData, rootGetters['accounts/config'])
      .then(() => {
        dispatch('fetchComments')
        commit('SET_SELECTEDCOMMENT', null)
      })
      .catch(err => {
        console.err(err.response)
      })
    }
  },
}