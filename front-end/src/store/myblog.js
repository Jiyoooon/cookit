import router from '../router'
import axios from 'axios'
import SERVER from '../api/url.js'

export default {
  flag: false,
  namespaced: true,
  state: {
    myrecipes: null,
    selectedrecipe: null,
    selecteduserinfo:{//들어가는 블로그의 유저정보
      email: null,
      hits: null,
      image_name: null,
      image_url: null,
      nickname: null,
      password: null,
      start_page: null,
      user_id: null,
    },
  },

  getters: {

  },

  mutations: {
    SET_RECIPES(state, recipes) {
      state.myrecipes = recipes
    },
    SET_SELETCERECIPE(state, recipe) {
      state.selectedrecipe = recipe
    },
    SET_USERINFO(state, payload){
      state.selecteduserinfo = payload
    },
    SET_FLAG(state, payload){
      state.flag = payload
    }
  },

  actions: {
    GoMyBlog() {
      router.push({ name: 'MyBlogListView' })
    },
    fetchMyRecipes({ rootState, commit, state }) {
      let recipequery = rootState.lookaround.recipequery
      recipequery.user = state.selecteduserinfo.nickname
      recipequery.p = null
      const filter = {
        params: recipequery
      }
      axios.get(SERVER.ROUTES.lookaroundrecipe.getfilteredrecipes, filter)
        // .then(()=>{
        //   commit('SET_RECIPES',null)
        // })
        .then((res) => {
          commit('SET_RECIPES', res.data)
        })
        // .then(()=>{
        //   if(state.flag == false){
        //     router.push({name : 'Home'})
        //     router.push({name : 'MyBlogListView'})
        //     commit('SET_FLAG',true)
        //   }
        // })
        // .catch((err) => {
        //   console.err(err.response)
        //   alert(err.response.data.cause)
        // })
    },
    selectedRecipe({ commit }, recipe_id) {
      axios.get(SERVER.ROUTES.myrecipe.selectedrecipe + String(recipe_id))
        .then((res) => {
          commit('SET_SELECTEDRECIPE', res.data)
          router.push({ name: 'SelectedRecipe'})
        })
        .catch((err) => {
          console.err(err.response)
          alert(err.response.data.cause)
        })
    },
    getUserInfo({commit,dispatch,rootState, state},user_id){
      axios.get(SERVER.ROUTES.info.getuserinfo + String(user_id))
        .then((res) => {
          commit('lookaround/initializing',null,{root:true})
          commit('SET_USERINFO', res.data.data)
          commit('lookaround/setRecipequeryUserId',res.data.data.nickname,{root:true})
          if (rootState.accounts.authUser.user_id == state.selecteduserinfo.user_id) {
            dispatch('GoMyBlog')
          } else {
            router.push({ name: 'UserBlogListView', params: { user_id: user_id }})
          }
        })
        .catch((err) => {
          console.err(err.response)
        })
    },
  },
}