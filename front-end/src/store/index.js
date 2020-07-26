import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/url.js'



Vue.use(Vuex)
axios.defaults.baseURL = SERVER.URL;
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

    emailAuthCodeSend({ commit }, email) {
      console.log(email)
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
    // updateUser(context, ) {
    //   axios.put(SERVER.ROUTES.accounts.baseuser, )
    // }
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
  }
})
