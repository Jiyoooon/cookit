import router from '../router'
import axios from 'axios'
import SERVER from '../api/url.js'

export default {
  namespaced: true,
  state: {
    //레시피를 불러올때 전달할 쿼리
    numberofgetrecipes:0,
    recipequery:{
      category:null,//어디서설정?
      create_date:null,
      filtering_id:null,
      filtering_name:null,
      filtering_user_id:null,
      hate_large:null,
      hate_medium:null,
      hate_small:null,
      id:null,//어디서설정?
      like_large:null,
      like_medium:null,
      like_small:null,
      order:0,
      p:0,//무한 스크롤에서 설정
      query:null,// 서치바에서 설정
      user:null,
      likeUser:null,// 해당
    },
    recipes:[],
    ingredients:null,//모든재료를 저장
    ingrName: [],
  },
  getters: {
  },

  mutations: {
    initializing(state){
      state.recipequery={
        category:null,//어디서설정?
        create_date:null,
        filtering_id:null,
        filtering_name:null,
        filtering_user_id:null,
        hate_large:null,
        hate_medium:null,
        hate_small:null,
        id:null,
        like_large:null,
        like_medium:null,
        like_small:null,
        order:null,
        p:0,
        query:null,
        user:null,
        likeUser:null,
      }
      state.recipes = []
    },
    setRecipequeryUserId(state,payload){
      state.recipequery.user =  payload
    },
    setRecipequeryCategory(state,payload){
      state.recipequery.category = payload
    },
    setRecipequeryOrder(state,payload){
      console.log(payload)
      console.log(typeof(payload))
      state.recipequery.order = payload
    },
    setRecipequery(state,payload){
      state.recipequery.query=payload.querydata
      for (var i in payload.selectedarray){
        if(payload.selectedarray[i].state == true){
          switch(payload.selectedarray[i].ingredientdata.kind){
            case 'small':
              if(state.recipequery.like_small == null)
                state.recipequery.like_small = payload.selectedarray[i].ingredientdata.name+','
              else
                state.recipequery.like_small += payload.selectedarray[i].ingredientdata.name+','
            break;
            case 'medium':
              if(state.recipequery.like_medium == null)
                state.recipequery.like_medium = payload.selectedarray[i].ingredientdata.name+','
              else
                state.recipequery.like_medium += payload.selectedarray[i].ingredientdata.name+','
            break;
            case 'large':
              if(state.recipequery.like_large == null)
                state.recipequery.like_large = payload.selectedarray[i].ingredientdata.name+','
              else
                state.recipequery.like_large += payload.selectedarray[i].ingredientdata.name+','
            break;
          }
        }
        else{
          switch(payload.selectedarray[i].ingredientdata.kind){
            case 'small':
              if(state.recipequery.hate_small == null)
                state.recipequery.hate_small = payload.selectedarray[i].ingredientdata.name+','
              else
                state.recipequery.hate_small += payload.selectedarray[i].ingredientdata.name+','
            break;
            case 'medium':
              if(state.recipequery.hate_medium == null)
                state.recipequery.hate_medium = payload.selectedarray[i].ingredientdata.name+','
              else
                state.recipequery.hate_medium += payload.selectedarray[i].ingredientdata.name+','
            break;
            case 'large':
              if(state.recipequery.hate_large == null)
                state.recipequery.hate_large = payload.selectedarray[i].ingredientdata.name+','
              else
                state.recipequery.hate_large += payload.selectedarray[i].ingredientdata.name+','
            break;
          }
        }
      }
      state.recipequery.category = payload.category
      state.recipequery.order = payload.order
    },
    setRecipes(state,recipes){
      state.recipes = [...state.recipes, ...recipes]
    },
    setRecipequeryPage(state,payload){
      state.recipequery.p+=payload
    },
    setNumberOfGetRecipes(state,payload){
      state.numberofgetrecipes = payload
    },
    setIngredients(state,payload){
      state.ingredients = payload
    },
    setIngrName(state, payload) {
      state.ingrName = payload;
    },
    initRecipes(state){
      state.recipes = []
    },
    initPage(state){
      state.recipequery.p=0
    },

  },

  actions: {
    loadIngrName({state, commit}) {
      this.getIngredients();
      commit('setIngrName', 
      state.ingredients.sort(function(a, b) {
        return a.length < b.length ? -1 : a.length > b.length ? 1 : 0;
      }));
    },
    setRecipequery({commit,dispatch},payload){
      commit('initializing')
      commit('setRecipequery',payload)
      dispatch('getFilteredRecipes')
    },
    setRecipequery2({commit,state,rootState},payload){
      console.log(payload)
      console.log(rootState.myblog.selecteduserinfo)
      commit('setRecipequery',payload)
      let trecipequery = state.recipequery
      const current = payload.current
      if (rootState.myblog.selecteduserinfo) {
        trecipequery.user = rootState.myblog.selecteduserinfo.user_id
      } else if (payload.current == 2) {
        trecipequery.likeUser = rootState.myblog.selecteduserinfo.user_id
        trecipequery.user = null
      } else {
        trecipequery.likeUser = null
        trecipequery.user = rootState.accounts.authUser.user_id
      }
      trecipequery.query = payload.query
      const filter = {
        params: trecipequery
      }
      console.log(trecipequery)
      axios.get(SERVER.ROUTES.lookaroundrecipe.getfilteredrecipes,filter)
      .then((res) => {
        commit('initializing')
        if (current == 2) {
          commit('myblog/SET_LIKERECIPES', res.data, { root: true })
        } else {
          commit('myblog/SET_RECIPES', res.data, { root: true })
        }
      })
    },
    getFilteredRecipes({commit,state}){
      // console.log(state.recipequery)
      const filter = {
        params:state.recipequery
      }
      axios.get(SERVER.ROUTES.lookaroundrecipe.getfilteredrecipes,filter)
      .then((res) => {
        commit('setRecipes',res.data)
        commit('setNumberOfGetRecipes',res.data.length)
        commit('setRecipequeryPage',res.data.length)
      })
      .catch((err) => {
        alert(err)
      })
    },
    getIngredients({commit}){
      axios.get(SERVER.ROUTES.lookaroundrecipe.getingredients)
      .then((res) => {
        commit('setIngredients', res.data)
      })
      .catch((err) => {
        alert(err)
      })
    },
    GoLookAroundRecipesView() {
      router.push({ name: 'LookAroundRecipeView',})
    },
  },
}