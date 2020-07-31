import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/url.js'
import createPersistedState from 'vuex-persistedstate'


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

//둘러보기 부분

const moduleLookAround = {
  namespaced: true,
  state: {
    //레시피를 불러올때 전달할 쿼리
    numberofgetrecipes:0,
    recipequery:{
      category:'',//어디서설정?
      filter:'',//어디서설정?
      id:'',//어디서설정?
      p:0,//무한 스크롤에서 설정
      query:'',// 서치바에서 설정
      user:'',// 해당
    },
    recipes:[],
    ingredients:[],
  },
  getters: {
  },

  mutations: {
    initializing(state){
      //alert("이니셜라이징!")
      state.recipequery={
        category:'',
        filter:'',
        id:'',
        p:0,
        query:'',
        user:'',
      }
      state.recipes = []
    },
    setRecipequery(state,querydata){
      state.recipequery.query=querydata
      state.recipes = []
      state.recipequery.p = 0;
    },
    setRecipes(state,recipes){
      state.recipes = [...state.recipes, ...recipes]
      console.log(state.recipes)
    },
    setRecipequeryPage(state,payload){
      state.recipequery.p+=payload
    },
    setNumberOfGetRecipes(state,payload){
      state.numberofgetrecipes = payload
      //alert("가져온 레시피 갯수 : " + payload)
    },
    setIngredients(state,payload){
      state.ingredients = payload
    },
  },

  actions: {
    setRecipequery({commit,dispatch},payload){
      commit('setRecipequery',payload)
      dispatch('getFilteredRecipes')
    },
    getFilteredRecipes({commit,state}){
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
    getIngredients({state,commit}){
      console.log("__불러오기전__")
      console.log(state.ingredients)
      if(state.ingredients.length == 0){
        axios.get(SERVER.ROUTES.lookaroundrecipe.getingredients)
        .then((res) => {
          //alert("모든 재료 로드 완료!")
          commit('setIngredients',res.data)
          console.log(res)
          console.log(state.ingredients)
        })
        .catch((err) => {
          alert(err)
        })
      }
    },
    GoLookAroundRecipesView() {
      router.push({ name: 'LookAroundRecipeView',})
    },
  },
}


export default new Vuex.Store({
  plugins: [createPersistedState(
    { path: ['accounts','lookaround'] }
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
    editor: moduleEditor,
    lookaround: moduleLookAround,
  }
})
