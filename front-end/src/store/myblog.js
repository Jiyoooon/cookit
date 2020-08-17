import router from '../router'
import axios from 'axios'
import SERVER from '../api/url.js'



export default {
  flag: false,
  namespaced: true,
  state: {
    myrecipes: [],
    likerecipes: null,
    currentshow: 1,
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
      sns_list: null
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
    },
    SET_LIKERECIPES(state, recipes) {
      state.likerecipes = recipes
    },
    SET_CURRENTSHOW(state, data) {
      state.currentshow = data
    }
  },

  actions: {
    GoMyBlog() {
      router.push({ name: 'MyBlogListView' })
    },
    fetchMyRecipes({ rootState, commit }, user) {
      commit('lookaround/initializing',null,{root:true})
      let recipequery = rootState.lookaround.recipequery
      // recipequery.user = state.selecteduserinfo.nickname
      recipequery.user = user.user_id
      recipequery.p = null
      recipequery.likeUser = null
      const filter = {
        params: recipequery
      }
      axios.get(SERVER.ROUTES.lookaroundrecipe.getfilteredrecipes, filter)
        // .then(()=>{
        //   commit('SET_RECIPES',null)
        // })
        .then((res) => {
          commit('SET_RECIPES', res.data)
          // commit('SET_FLAG', true)
          // console.log(state.flag)
        })
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
    fetchLikeRecipes({ rootState, commit, state }) {
      let recipequery = rootState.lookaround.recipequery
      recipequery.user = null
      recipequery.p = null
      recipequery.likeUser = state.selecteduserinfo.user_id
      const filter = {
        params: recipequery
      }
      axios.get(SERVER.ROUTES.lookaroundrecipe.getfilteredrecipes, filter)
        .then((res) => {
          commit('SET_LIKERECIPES', res.data)
        })
    },
    getUserInfo({commit,dispatch,rootState, state},user_id){
      axios.get(SERVER.ROUTES.info.getuserinfo + String(user_id))
        .then((res) => {
          commit('lookaround/initializing',null,{root:true})
          commit('SET_USERINFO', res.data.data)
          commit('lookaround/setRecipequeryUserId',res.data.data.user_id,{root:true})

          console.log("선택 : "+state.selecteduserinfo.user_id);

          if (rootState.accounts.authUser != null && rootState.accounts.authUser.user_id == state.selecteduserinfo.user_id) {
            dispatch('GoMyBlog')
          } else {
            router.push({ name: 'UserBlogListView', params: { user_id: user_id }})
            // location.reload()
          }
        })
        .catch((err) => {
          console.log(err.response)
        })
    },
    getUserInfo2({dispatch,rootState},user_id){
      if (rootState.accounts.authUser != null && rootState.accounts.authUser.user_id == user_id) {
        dispatch('GoMyBlog')
      } else {
        router.push({ name: 'UserBlogListView', params: { user_id: user_id }})
        location.reload()
        console.log(router)
        console.log(location)
      }
    },
  },
}