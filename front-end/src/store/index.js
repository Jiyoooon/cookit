import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/url.js'



Vue.use(Vuex)
axios.defaults.baseURL = 'http://i3a201.p.ssafy.io:8080/cooking-0.0.2-SNAPSHOT';

const moduleAccounts = {
  namespaced: true,
  state: {
    authToken: cookies.get('auth-token'),
    authUser: cookies.get('auth-user'),
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
          Authoriaztion: `Bearer ${state.authToken}`
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
     SET_USER(state, user) {
       state.authUser = user
       cookies.set('auth-user', user)
     },
  },

  actions: {
    DeleteUser({ commit }) {
      console.log('aaaa')
      axios.delete(SERVER.ROUTES.accounts.baseuser)
        .then((res) => {
          console.log(res.data)
          commit('SET_TOKEN', null)
          commit('SET_USER', null)
          commit('SET_EMAIL', null)
          cookies.remove('auth-token')
          cookies.remove('auth-user')
          cookies.remove('user-email')
          router.push({ name: 'Home' })
        })
        .catch(err => console.log(err.response))
    },

    GoHome() {
      router.push({ name: 'MyBlogListView'})
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
    GoPasswordAuth(){
      router.push({ name: 'PasswordAuthView'})
    },
    GoPasswordFind(){
      router.push({ name: 'PasswordFindView'})
    },

    RedirectAfterUserUpdate() {
      router.push({ name: 'UserInfoView' })
    },

    RedirectAfterUserDelete() {
      router.push({ name: 'UserInfoView' })
    },

    login({ commit, dispatch }, loginData) {
      axios.post(SERVER.ROUTES.accounts.login, loginData)
        .then((res) => {
          console.log(res.data)
          console.log(res.headers)
          console.log(res)
          if (res.data.result == 'success') {
            commit('SET_TOKEN', res.data.uid)
            dispatch('fetchUser')
            router.push({ name: 'Home'})
          } else {
            alert('이메일과 비밀번호를 확인해주세요.')
          }
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
          commit('SET_USER', null)
          commit('SET_EMAIL', null)
          cookies.remove('auth-token')
          cookies.remove('auth-user')
          cookies.remove('user-email')
          router.push({ name: 'Home'})
        })
        .catch(err => console.log(err.response))
    },

    emailDupCheck(context, email) {
      console.log("email dup check")
      axios.get(`/user/dup/email/${String(email)}`)
      .then(res => {
        if (res.data.result == 'success') return true
        else if (res.data.result == 'fail') return false
      })
    },

    emailAuthCodeSend({ commit }, email) {
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
        else if (res.data.result == 'fail') {
          alert("코드가 맞지 않습니다.")
          commit('SET_EMAIL', null)
        }
      })
      .catch(err => {
        console.log(err.response)
        alert('!!!!!!')
       })
    },
    signup({ commit, dispatch }, signupData) {
      if (!signupData.valid.password) {
        alert('비밀번호가 일치하지 않습니다.')
      } else if (!signupData.valid.nickname) {
        alert('닉네임 중복체크를 해주세요.')
      } else {
        axios.post(SERVER.ROUTES.accounts.signup, signupData.config)
          .then((res) => {
            console.log(res.data)
            commit('SET_TOKEN', res.data.uid)
            dispatch('fetchUser')
            router.push({ name: 'Home'})
          })
          .catch((err) => {
            console.log(err.response)
            alert('!!!!!!')
          })
        }
    },
    fetchUser({ getters, commit }) {
      axios.get(SERVER.ROUTES.accounts.baseuser, null, getters.config)
        .then((res) => {
          console.log(res)
          commit('SET_USER', res.data)
        })
        .catch((err) => {
          console.err(err.response)
          alert('!!!!')
        })
    },
    nicknameCheck(context, nickname) {
      axios.get(SERVER.ROUTES.accounts.nicknameCheck + String(nickname))
        .then((res) => {
          console.log(res)
        })
    },
    passwordCheck({dispatch},password){
      console.log('토큰 :' + this.authToken)
      axios.post(SERVER.ROUTES.accounts.checkpassword,password,{
        headers:{
          'Authoriaztion': 'jwt-auth-token' + this.authToken
        } 
      })
      .then((res) => {
        console.log(res)
        if(res){
          alert("일치합니다!")
          dispatch('GoUserInfo')
        }
      })
      .catch((err) => {
        console.log("에러!!!!")
        alert(err.response)
      })
    },
    sendNewPassword({dispatch},email){
      axios.get(SERVER.ROUTES.accounts.sendnewpassword+String(email))
      .then((res) => {
        if(res){
          alert(email+"로 새 비밀번호를 전송했습니다!")
          dispatch('GoLogin')
        }
      })
      .catch((err) => {
        console.log("에러!!!!")
        alert(err.response)
      })
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

const moduleMyBlog = {
  namespaced: true,
  state: {
    myrecipes: [{id: 1, title: '1111111111', content: 'fsdfsfsdfsdf'},
                {id: 2, title: 'dfsfsfsdfsd', content: 'dfssdfadfad'},
                {id: 3, title: 'fasdfsdf', content: 'dfsgasdgs'}],
    selectedrecipe: null,
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
  },

  actions: {
    fetchMyRecipes({ commit }, user_id) {
      axios.get(SERVER.ROUTES.myrecipes.myrecipes + String(user_id))
        .then((res) => {
          console.log(res)
          commit('SET_RECIPES', res.data)
        })
        .catch((err) => {
          console.err(err.response)
          alert('!!!!!')
        })
    },
    selectedRecipe({ commit }, recipe_id) {
      axios.get(SERVER.ROUTES.myrecipe.selectedrecipe + String(recipe_id))
        .then((res) => {
          console.log(res.data)
          commit('SET_SELECTEDRECIPE', res.data)
          router.push({ name: 'SelectedRecipe'})
        })
        .catch((err) => {
          console.err(err.response)
          alert('!!!!!')
        })
    },
    // fetchRecipesByTitle() {
    //   axios.get(SERVER.ROUTES.myrecipe.fetchrecipesbytitle, )
    // }
  },
}

const moduleEditor = {
  namespaced: true,
  state: {
    recipeData: {
    },
    recipe: {
      title: null,
      description: null,
      category_id: 0,
      servings: 0,
      cooking_time: 0,
      level: 0,
      main_image: null,
      tag: [ ]
    },
    cookingSteps: [
      {
        steps: 1,
        description: null,
        tip: null,
        step_image: null },
      {
        steps: 2,
        description: null,
        tip: null,
        step_image: null },
    ],
    mainIngr: [
      { name: null, quantity: null, is_essential: 1 },
      { name: null, quantity: null, is_essential: 1 },
    ],
    subIngr: [
      { name: null, quantity: null, is_essential: 0 },
      { name: null, quantity: null, is_essential: 0 },
    ]
  },
  getters: {

  },
  mutations: {
    SET_RECIPEDATA(state) {
      state.recipeData = {
        recipe: state.recipeinfo,
        ingredients: [...state.mainIngr, ...state.subIngr],
        cookingSteps: state.cookingSteps
      }
    },
    SET_RECIPEINFO(state, data) {
      state.recipeinfo = data
    },
    SET_COOKINGSTEPS(state, data) {
      state.cookingSteps = data
    },
    SET_MAININGR(state, data) {
      state.mainIngr = data
    },
    SET_SUBINGR(state, data) {
      state.subIngr = data
    },
    addIngredient(state, essential) {
      const ref = essential ? state.mainIngr : state.subIngr;
      ref.push({
        name: null,
        quantity: null,
        is_essential: essential
      })
    },
    deleteIngredient(state, data) {
      const ref = data.essential ? state.mainIngr : state.subIngr;
      ref.splice(data.index, 1);
    },
    addCookingStep(state) {
      state.cookingSteps.push({
        steps: state.cookingSteps.length + 1,
        description: null,
        tip: null,
        step_image: null },)
    },
    deleteCookingStep(state, id) {
      state.cookingSteps.splice(id, 1);
      for (var i = id; i < state.cookingSteps.length; i++) {
				state.cookingSteps[i].steps -= 1;
			}
    },
  }, 
  actions: {
    getRecipeInfo({commit}, data) {
      commit('SET_RECIPEINFO', data);
    },
    getMainIngr({commit}, data) {
      commit('SET_MAININGR', data);
    },
    getSubIngr({commit}, data) {
      commit('SET_SUBINGR', data);
    },
    getCookingSteps({commit}, data) {
      commit('SET_COOKINGSTEPS', data);
    },
    onSubmitButton({state, commit}) {//, rootState) {
      commit('SET_RECIPEDATA')
      const recipe = new FormData(), ingredients = new FormData(), cookingSteps = new FormData();
      for (let [key, value] of Object.entries(state.recipe)) {
        recipe.append(key, value);
      }
      for (let [key, value] of Object.entries([...state.mainIngr, ...state.subIngr])) {
        ingredients.append(key, value);
      }
      for (let [key, value] of Object.entries(state.cookingSteps)) {
        cookingSteps.append(key, value);
      }
      const recipeData = {
        recipe: recipe,
        ingredients: ingredients,
        cookingSteps: cookingSteps
      }
      // const headerconfig = { headers: {
      //   'Authorization': `token ${rootState['accounts/authToken']}`,
      //   'Content-Type': 'multipart/form-data'
      // }}
      for (let [key, value] of recipe.entries()) {
        console.log(`${key}: ${value}`)
      }
      for (let [key, value] of ingredients.entries()) {
        console.log(key)
        console.log(value)
      }
      for (let [key, value] of cookingSteps.entries()) {
        console.log(key)
        console.log(value)
      }
      axios.post('/recipe/save2', recipeData)
      .then((res) => {
        console.log(res)
        // 레시피 화면으로 redirect 필요
      })
      .catch((err) => {
        console.log(err)
      })
    }
  }
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
    myblog: moduleMyBlog,
    editor: moduleEditor,
  }
})
