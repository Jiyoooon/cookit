import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/url.js'
import createPersistedState from 'vuex-persistedstate'


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
          Authorization: `token ${state.authToken}`
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
       console.log(email)
       console.log(state.userEmail)
       cookies.set('user-email', email)
     },
     SET_USER(state, user) {
       state.authUser = user
       cookies.set('auth-user', user)
     },
  },

  actions: {
    DeleteUser({ commit, getters }) {
      this._vm.$root.$bvModal.msgBoxConfirm('한번 삭제된 데이터는 복구되지 않습니다.', {
        title: '정말로 탈퇴하시겠습니까?',
        size: 'lg',
        buttonSize: 'sm',
        okVariant: 'danger',
        okTitle: 'YES',
        cancelTitle: 'NO',
        footerClass: 'p-2',
        hideHeaderClose: false,
        centered: true
      })
        .then((ans) => {
          if (ans) {
            axios.delete(SERVER.ROUTES.accounts.baseuser, getters.config)
              .then((res) => {
                if(res.data.result == 'success') {
                  this._vm.$root.$bvModal.msgBoxOk('회원탈퇴 되었습니다.', {
                    title: 'Confirmation',
                    size: 'sm',
                    buttonSize: 'sm',
                    okVariant: 'success',
                    headerClass: 'p-2 border-bottom-0',
                    footerClass: 'p-2 border-top-0',
                    centered: true
                  })
                  commit('SET_TOKEN', null)
                  commit('SET_USER', null)
                  commit('SET_EMAIL', null)
                  commit('myblog/SET_RECIPES', null, { root: true })
                  cookies.remove('auth-token')
                  cookies.remove('auth-user')
                  cookies.remove('user-email')
                  router.push({ name: 'Home' })
                } else {
                  this._vm.$root.$bvModal.msgBoxOk('실패하였습니다.', {
                    title: 'Confirmation',
                    size: 'sm',
                    buttonSize: 'sm',
                    okVariant: 'danger',
                    headerClass: 'p-2 border-bottom-0',
                    footerClass: 'p-2 border-top-0',
                    centered: true
                  })
                }
              })
              .catch((err) => {
                console.log(err.response)
                alert(err.response)
              })
          }
        })
        .catch(err => {
          console.log(err.response)
          alert(err.response)
        })
    },

    GoHome() {
      router.push({ name: 'Home'})
    },

    GoMyBlog() {
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
          if (res.data.result == 'success') {
            commit('SET_TOKEN', res.data.token)
            dispatch('fetchUser')
            dispatch('myblog/fetchMyRecipes', null, { root: true })
            dispatch('GoHome')
          } else {
            console.log(res.data)
            this._vm.$root.$bvModal.msgBoxOk('이메일과 비밀번호를 확인하여 주십시오.', {
              title: 'Confirmation',
              size: 'sm',
              buttonSize: 'sm',
              okVariant: 'danger',
              headerClass: 'p-2 border-bottom-0',
              footerClass: 'p-2 border-top-0',
              centered: true
            })
          }
        })
        .catch((err) => {
          console.log(err.response)
          alert(err.response)
        })
    },

    logout({ commit, getters }) {
      axios.get(SERVER.ROUTES.accounts.logout, getters.config)
        .then(() => {
          commit('SET_TOKEN', null)
          commit('SET_USER', null)
          commit('SET_EMAIL', null)
          commit('myblog/SET_RECIPES', null, { root: true })
          cookies.remove('auth-token')
          cookies.remove('auth-user')
          cookies.remove('user-email')
          router.push({ name: 'Home'})
        })
        .catch(err => {
          console.log(err.response)
          alert(err.response)
        })
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
      axios.get(SERVER.ROUTES.accounts.requestkey + String(email))
      .then(res => {
        console.log(`Code send: ${String(res.data.result)}`)
        if(res.data.result == 'success') {
          commit('SET_EMAIL', email)
          this._vm.$root.$bvModal.msgBoxOk('인증 코드가 발송되었습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
          })
        } else {
          this._vm.$root.$bvModal.msgBoxOk('인증 코드 발송에 실패하였습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
          })
        }
      })
      .catch(err => {
        console.log(err.response)
        alert(err.response)
      })
    },

    emailAuthCodeCheck({ commit, state, dispatch }, code) {
      console.log(code)
      const emailConfirm = {
        email: state.userEmail,
        code: code,
      }
      console.log(emailConfirm)
      axios.post(SERVER.ROUTES.accounts.checkkey, emailConfirm)
      .then(res => {
        console.log(`Code check: ${String(res.data.result)}`)
        if (res.data.result == 'success') dispatch('GoSignup')
        else if (res.data.result == 'fail') {
          this._vm.$root.$bvModal.msgBoxOk('코드가 맞지 않습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
          })
          commit('SET_EMAIL', null)
        }
      })
      .catch(err => {
        console.log(err.response)
        alert('!!!!!!')
       })
    },
    fetchUser({ getters, commit }) {
      axios.get(SERVER.ROUTES.accounts.baseuser, getters.config)
        .then((res) => {
          commit('SET_USER', res.data.data)
          console.log(res.data.data)
          console.log('!!!!')
        })
        .catch((err) => {
          console.err(err.response)
          alert(err.response)
        })
    },
    updateUser({ dispatch, state }, updateData) {
      if (!updateData.valid.password) {
        this._vm.$root.$bvModal.msgBoxOk('비밀번호가 일치하지 않습니다.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true
        })
      } else if (!updateData.valid.nickname) {
        this._vm.$root.$bvModal.msgBoxOk('닉네임 중복체크를 해주세요.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true
        })
      } else {
        console.log(updateData.config)

        const formData = new FormData()
        formData.append('email', updateData.config.email)
        formData.append('password', updateData.config.password)
        formData.append('nickname', updateData.config.nickname)
        formData.append('profile', updateData.config.profile)
        formData.append('image_name', updateData.config.image_name)
        formData.append('intro', updateData.config.intro)
        formData.append('start_page', updateData.config.start_page)

        for (let key of formData.entries()) {
          console.log(`${key}`)
        }

        const headerconfig = { headers: {
          'Authorization': `token ${state.authToken}`,
          'Content-Type': 'multipart/form-data'
        }}

        axios.put(SERVER.ROUTES.accounts.baseuser, formData, headerconfig)
          .then((res) => {
            if (res.data.result == 'success') {
              this._vm.$root.$bvModal.msgBoxOk('수정되었습니다.', {
                title: 'Confirmation',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'success',
                headerClass: 'p-2 border-bottom-0',
                footerClass: 'p-2 border-top-0',
                centered: true
              })
            dispatch('fetchUser')
            }
          })
          .catch((err) => {
            console.log(err.response)
            console.log(err)
            alert(err.response.data.error)
          })
      }
    },
    // nicknameCheck(context, nickname) {
    //   axios.get(SERVER.ROUTES.accounts.nicknameCheck + String(nickname))
    //     .then((res) => {
    //       console.log(res)
    //     })
    // },
    passwordCheck({ dispatch, getters, state } ,password) {
      console.log('토큰 :' + state.authToken)
      axios.post(SERVER.ROUTES.accounts.checkpassword, password, getters.config)
      .then((res) => {
        console.log(res)
        if(res.data.result == 'success') {
          cookies.set('password-check', 1)
          this._vm.$root.$bvModal.msgBoxOk('확인되었습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
          })
          .then((ans) => {
            if (ans) {
              dispatch('GoUserInfo')
            }
            console.log(111111)
            cookies.remove('password-check')
          })
        } else {
          this._vm.$root.$bvModal.msgBoxOk('비밀번호가 일치하지 않습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
          })
        }
      })
      .catch((err) => {
        console.log(err.response)
        alert(err.response)
      })
    },
    sendNewPassword({ dispatch }, email) {
      axios.get(SERVER.ROUTES.accounts.sendnewpassword + String(email))
      .then((res) => {
        console.log(res)
        if(res.data.result == 'success'){
          this._vm.$root.$bvModal.msgBoxOk('새 비밀번호가 전송되었습니다', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
          }) 
          dispatch('GoLogin')
        } else {
          this._vm.$root.$bvModal.msgBoxOk('존재하지 않는 이메일입니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
          }) 
        }
      })
      .catch((err) => {
        console.log(err.response)
        alert(err.response)
      })
    },
    signup({ commit, dispatch }, signupData) {
      if (!signupData.valid.password) {
        this._vm.$root.$bvModal.msgBoxOk('비밀번호가 일치하지 않습니다.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true
        })
      } else if (!signupData.valid.nickname) {
        this._vm.$root.$bvModal.msgBoxOk('닉네임 중복체크를 해주세요.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true
        })
      } else {
        
        const formData = new FormData()
        formData.append('email', signupData.config.email)
        formData.append('password', signupData.config.password)
        formData.append('nickname', signupData.config.nickname)
        formData.append('profile', signupData.config.profile)
        formData.append('intro', signupData.config.intro)
        formData.append('image_name', signupData.config.image_name)

        for (let key of formData.entries()) {
          console.log(`${key}`)
        }
       
        console.log(signupData.config.profile)
        
        axios.post(SERVER.ROUTES.accounts.signup, formData, { headers: { 'Content-Type': 'multipart/form-data' }})
          .then((res) => {
            if (res.data.result == 'success') {
              commit('SET_TOKEN', res.data.token)
              dispatch('fetchUser')
              dispatch('myblog/fetchMyRecipes', null, { root: true })
              this._vm.$root.$bvModal.msgBoxOk('가입되었습니다.', {
                title: 'Confirmation',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'success',
                headerClass: 'p-2 border-bottom-0',
                footerClass: 'p-2 border-top-0',
                centered: true
              })
                .then(() => {
                  router.push({ name: 'Home'})
                })
            } else {
              console.log(res)
              console.log(res.data)
              this._vm.$root.$bvModal.msgBoxOk('이미지 파일이 올바르지 않습니다.', {
                title: 'Confirmation',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'danger',
                headerClass: 'p-2 border-bottom-0',
                footerClass: 'p-2 border-top-0',
                centered: true
              })
            }
          })
          .catch((err) => {
            console.log(err.response)
            alert(err.response)
          })
        }
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
    myrecipes: null,
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
    fetchMyRecipes({ rootState, commit }) {
      let recipequery = rootState['lookaround/recipequery']
      // recipequery.user = user_id
      const filter = {
        params: recipequery
      }
      axios.get('http://i3a201.p.ssafy.io:8080/cooking-0.0.2-SNAPSHOT/recipe/recipes', filter)
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
  },
}

//둘러보기 부분

const moduleLookAround = {
  namespaced: true,
  state: {
    //레시피를 불러올때 전달할 쿼리
    recipequery:{
      category:'',//어디서설정?
      filter:'',//어디서설정?
      id:'',//어디서설정?
      p:21,//무한 스크롤에서 설정
      query:'',// 서치바에서 설정
      user:'',//
    },
    recipes:[
      {
        recipe_id: 9999,
        recipe_user: 3,
        recipe_user_name: null,
        category_id: 1,
        title: "일본식 꽁치통조림 간장조림",
        description: "집밥백선생에 나온 꽁치통조림 요리중에 밥반찬으로 최고인 일식 꽁치간장조림이에요",
        main_image: "user11595982936459",
        serving: 0,
        cooking_time: 15,
        hits: 0,
        tag: null,
        create_date: "2020-07-29 00:35:36",
        update_date: null,
        delete_date: null
      },
    ],
  },
  getters: {
  },

  mutations: {
    setRecipequery(state,filter){
      state.recipequery=filter
    },
    setRecipes(state,recipes){
      state.recipes = [...state.recipes, ...recipes]
      console.log(state.recipes)
    },
    setRecipequeryPage(state){
      state.recipequery.p+=20
    }
  },

  actions: {
    getFilteredRecipes({commit,state}){
      const filter = {
        params:state.recipequery
      }
      axios.get(SERVER.ROUTES.lookaroundrecipe.getfilteredrecipes,filter)
      .then((res) => {
        commit('setRecipes',res.data)
        commit('setRecipequeryPage')
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


export default new Vuex.Store({
  plugins: [createPersistedState(
    { path: ['accounts'] }
  )],
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
    lookaround: moduleLookAround,

  }
})
