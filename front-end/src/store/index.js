import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/url.js'
import createPersistedState from 'vuex-persistedstate'


Vue.use(Vuex)
axios.defaults.baseURL = 'http://i3a201.p.ssafy.io:8080/cooking-0.0.1-SNAPSHOT';

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
            commit('SET_TOKEN', res.headers.token)
            dispatch('fetchUser')
            dispatch('myblog/fetchMyRecipes', null, { root: true })
            dispatch('GoHome')
          } else {
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
          alert(err.response.data.cause)
        })
    },

    emailDupCheck(context, email) {
      axios.get(`/user/dup/email/${String(email)}`)
      .then(res => {
        if (res.data.result == 'success') return true
        else if (res.data.result == 'fail') return false
      })
    },

    emailAuthCodeSend({ commit }, email) {
      axios.get(SERVER.ROUTES.accounts.requestkey + String(email))
      .then(res => {
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
    passwordCheck({ dispatch, getters } ,password) {
      axios.post(SERVER.ROUTES.accounts.checkpassword, password, getters.config)
      .then((res) => {
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
              commit('SET_TOKEN', res.headers.token)
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
    }
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
      axios.get(SERVER.ROUTES.lookaroundrecipe.getfilteredrecipes, filter)
        .then((res) => {
          commit('SET_RECIPES', res.data)
        })
        // .then(() => {
        //   router.push({ name: 'Home' })
        // })
        // .then(() => {
        //   router.push({ name: 'MyBlogListView'})
        // })
        .catch((err) => {
          console.err(err.response)
          alert(err.response.data.cause)
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
  },
}

const moduleEditor = {
  namespaced: true,
  state: {
    recipe: {
      title: null,
      description: null,
      category_id: 0,
      servings: 0,
      cooking_time: 0,
      level: 0,
      main_image_file: null,
      tag: []
    },
    cookingStep: [
      {
        steps: 1,
        description: null,
        tip: null,
        step_image_file: null },
      {
        steps: 2,
        description: null,
        tip: null,
        step_image_file: null },
    ],
    mainIngr: [
      { name: "", quantity: "", is_essential: 1 },
      { name: "", quantity: "", is_essential: 1 },
    ],
    subIngr: [
      { name: "", quantity: "", is_essential: 0 },
      { name: "", quantity: "", is_essential: 0 },
    ],
    ingrQuery: [],
  },
  getters: {

  },
  mutations: {
    SET_RECIPE(state, data) {
      state.recipe = data
    },
    SET_COOKINGSTEP(state, data) {
      state.cookingStep = data
    },
    SET_MAININGR(state, data) {
      state.mainIngr = data
    },
    SET_SUBINGR(state, data) {
      state.subIngr = data
    },
    SET_INGRQUERY(state, data) {
      state.ingrQuery = data
    },
    addIngredient(state, essential) {
      const ref = essential ? state.mainIngr : state.subIngr;
      ref.push({
        name: "",
        quantity: "",
        is_essential: essential
      })
    },
    deleteIngredient(state, data) {
      const ref = data.essential ? state.mainIngr : state.subIngr;
      ref.splice(data.index, 1);
    },
    addCookingStep(state) {
      state.cookingStep.push({
        steps: state.cookingStep.length + 1,
        description: "",
        tip: "",
        step_image: null },)
    },
    deleteCookingStep(state, id) {
      state.cookingStep.splice(id, 1);
      for (var i = id; i < state.cookingStep.length; i++) {
				state.cookingStep[i].steps -= 1;
			}
    },
  }, 
  actions: {
    getRecipe({commit}, data) {
      commit('SET_RECIPE', data);
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
    loadIngredients({commit}) {
      axios.get('/recipe/ingredients/small')
      .then((res) => {
        commit('SET_INGRQUERY', res.data);
      })
      .catch((err) => {
        console.log(err);
      })
    },
    onSubmitButton({state, rootState}) {
      
      console.log(state.mainIngr)
      console.log(state.subIngr)
      if(state.recipe.title == "") {
        alert("레시피 제목을 입력하세요.")
        return;
      }

      if(state.recipe.description == "") {
        alert("레시피 소개말을 입력하세요.")
        return;
      }

      var ingrDone = false;
      for (let i = 0; i < state.mainIngr.length; i++) {
        if(state.mainIngr[i].name != "" && state.mainIngr[i].quantity != "") {
          ingrDone = true;
        }
        if (state.mainIngr[i].name != "" && state.mainIngr[i].quantity == "") {
          alert("재료의 양을 입력하세요.")
          return;
        }
        if (state.mainIngr[i].name == "" && state.mainIngr[i].quantity != "") {
          alert("재료의 이름을 입력하세요.")
          return;
        }
      }
      for (let i = 0; i < state.subIngr.length; i++) {
        if(state.subIngr[i].name != "" && state.subIngr[i].quantity == "") {
          ingrDone = true;
        }
        if (state.subIngr[i].name != "" && state.subIngr[i].quantity == "") {
          alert("재료의 양을 입력하세요.")
          return;
        }
        if (state.subIngr[i].name == "" && state.subIngr[i].quantity != "") {
          alert("재료의 이름을 입력하세요.")
          return;
        }
      }
      if (!ingrDone) {
        alert("재료를 입력하세요.")
        return;
      }

      var stepDone = false;
      for (let i = 0; i < state.cookingStep.length; i++) {
        if (state.cookingStep[i].description != null) {
          stepDone = true;
          break;
        }
      }
      if (!stepDone) {
        alert("조리 과정을 한 개 이상 입력하세요.")
        return;
      }

      const recipeData = new FormData();
      for (let [key, value] of Object.entries(state.recipe)) {
        if (key == "main_image_file" && value == null) continue;
        console.log(`recipe.${key}: ${value}`)
        recipeData.append(key, value);
      }
      
      const ingredients = [...state.mainIngr, ...state.subIngr];
      for (let i = 0; i < ingredients.length; i++) {
        if (ingredients[i].name == null && ingredients[i].quantity == null) continue;
        for (let [key, value] of Object.entries(ingredients[i])) {
          console.log(`ingredients[${i}].${key}: ${value}`)
          recipeData.append(`ingredients[${i}].${key}`, value)
        }
      }

      for (let i = 0; i < state.cookingStep.length; i++) {
        if (state.cookingStep[i].description == null && state.cookingStep[i].tip == null
          && state.cookingStep[i].step_image_file == null) continue;
        for (let [key, value] of Object.entries(state.cookingStep[i])) {
          if (key == "step_image_file" && value == null) continue;
          console.log(`cookingStep[${i}].${key}: ${value}`)
          recipeData.append(`cookingStep[${i}].${key}`, value)
        }
      }

      const headerConfig = { headers: {
        'Authorization': `token ${rootState['accounts/authToken']}`,
        'Content-Type': 'multipart/form-data'
      }}

      axios.post('/recipe/token/save', recipeData, headerConfig)
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
      p:0,//무한 스크롤에서 설정
      query:null,// 서치바에서 설정
      user:null,// 해당
    },
    recipes:[],
    ingredients:null,//모든재료를 저장
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
        p:0,
        query:null,
        user:null,
      }
      state.recipes = []
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
    },
    setRecipes(state,recipes){
      state.recipes = [...state.recipes, ...recipes]
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
      commit('initializing')
      commit('setRecipequery',payload)
      dispatch('getFilteredRecipes')
    },
    setRecipequery2({commit,state},payload){
      commit('initializing')
      commit('setRecipequery',payload)
      // dispatch('myblog/fetchMyRecipes', null, { root: true })
      const filter = {
        params:state.recipequery
      }
      axios.get(SERVER.ROUTES.lookaroundrecipe.getfilteredrecipes,filter)
      .then((res) => {
        commit('myblog/SET_RECIPES', null, { root: true })
        commit('myblog/SET_RECIPES', res.data, { root: true })
      })
      .then(() => {
        router.push({ name: 'Home'})
      })
      .then(() => {
        router.push({ name: 'MyBlogListView'})
      })
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
    getIngredients({commit}){
      //if(state.ingredients.length == 0){
        axios.get(SERVER.ROUTES.lookaroundrecipe.getingredients)
        .then((res) => {
          commit('setIngredients',res.data)
        })
        .catch((err) => {
          alert(err)
        })
      //}
    },
    GoLookAroundRecipesView() {
      router.push({ name: 'LookAroundRecipeView',})
    },
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
    editor: moduleEditor,
    lookaround: moduleLookAround,
  },
  plugins: [
    createPersistedState({
      paths: ['lookaround'],
    })
  ]
})
