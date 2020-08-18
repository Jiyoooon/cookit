import router from '../router'
import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '../api/url.js'
import $ from 'jquery'

export default {
  namespaced: true,
  state: {
    authToken: cookies.get('auth-token'),
    authUser: cookies.get('auth-user'),
    userEmail: cookies.get('user-email'),
    validEmail: false,
    updateTF: false,
    followers: null,
    followings: null,
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
    isValidEmail(state) {
      return state.validEmail
    }
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
     SET_VALID(state, data) {
       state.validEmail = data
     },
     SET_UPDATETF(state, value) {
       console.log('fsdfsf')
       state.updateTF = value
     },
     SET_FOLLOWERS(state, followers) {
       state.followers = followers
     },
     SET_FOLLOWINGS(state, followings) {
       state.followings = followings
     }
  },

  actions: {
    DeleteUser({ commit, getters }) {
      let that = this
      window.addEventListener('keypress', function(event) {
        if (event.keyCode == 13) that._vm.$root.$bvModal.hide('modal')
      })
      this._vm.$root.$bvModal.msgBoxConfirm('한번 삭제된 데이터는 복구되지 않습니다.', {
        title: '정말로 탈퇴하시겠습니까?',
        size: 'lg',
        buttonSize: 'sm',
        okVariant: 'danger',
        okTitle: 'YES',
        cancelTitle: 'NO',
        footerClass: 'p-2',
        hideHeaderClose: false,
        centered: true,
        id: 'modal'
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
                    centered: true,
                    id: 'modal'
                  })
                  commit('SET_TOKEN', null)
                  commit('SET_USER', null)
                  commit('SET_EMAIL', null)
                  commit('myblog/SET_RECIPES', null, { root: true })
                  cookies.remove('auth-token')
                  cookies.remove('auth-user')
                  cookies.remove('user-email')
                  router.push({ name: 'LookAroundRecipeView' })
                } else {
                  this._vm.$root.$bvModal.msgBoxOk('실패하였습니다.', {
                    title: 'Confirmation',
                    size: 'sm',
                    buttonSize: 'sm',
                    okVariant: 'danger',
                    headerClass: 'p-2 border-bottom-0',
                    footerClass: 'p-2 border-top-0',
                    centered: true,
                    id: 'modal'
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
    clearSearchHistory() {
      sessionStorage.removeItem('category');
      sessionStorage.removeItem('searching');
      sessionStorage.removeItem('selecting');
      sessionStorage.removeItem('ordering');
    },
    GoHome({dispatch}) {
      dispatch('clearSearchHistory')
      router.push({ name: 'Home'}).catch(() => {});
    },

    GoSignup({dispatch}) {
      dispatch('clearSearchHistory')
      router.push({ name: 'Signup'}).catch(() => {});
    },

    GoLogin({dispatch}) {
      dispatch('clearSearchHistory')
      router.push({ name: 'Login'}).catch(() => {});
    },

    GoLogout({dispatch}) {
      dispatch('clearSearchHistory')
      router.push({ name: 'Logout'}).catch(() => {});
    },

    GoUserInfo({ commit, dispatch }) {
      dispatch('clearSearchHistory')
      commit('SET_UPDATETF', false)
      router.push({ name: 'UserInfoView'}).catch(() => {});
    },

    GoEmailAuth({dispatch}) {
      dispatch('clearSearchHistory')
      router.push({ name: 'EmailAuthView'}).catch(() => {});
    },
    GoPasswordAuth({dispatch}){
      dispatch('clearSearchHistory')
      router.push({ name: 'PasswordAuthView'}).catch(() => {});
    },
    GoPasswordFind({dispatch}){
      dispatch('clearSearchHistory')
      router.push({ name: 'PasswordFindView'}).catch(() => {});
    },
    GoRecipeCreate(){
      router.push({ name: 'RecipeCreateView'}).catch(() => {});
    },
    RedirectAfterUserUpdate() {
      router.push({ name: 'UserInfoView' }).catch(() => {});
    },

    RedirectAfterUserDelete() {
      router.push({ name: 'UserInfoView' }).catch(() => {});
    },

    login({ commit, dispatch, state }, loginData) {
      dispatch('clearSearchHistory')
      axios.post(SERVER.ROUTES.accounts.login, loginData)
        .then((res) => {
          if (res.data.result == 'success') {
            commit('SET_TOKEN', res.headers.token)
            dispatch('fetchUser')
            return new Promise(function() {
              window.setTimeout(function() {
                dispatch('myblog/fetchMyRecipes', state.authUser, { root: true })
                dispatch('myblog/fetchLikeRecipes', null, { root: true })
                if (state.authUser.start_page) {
                  $("#myblog").addClass("active");
                  $("#browsing").removeClass("active");
                  router.push({ name: 'MyBlogListView'})
                }
                else {
                  $("#myblog").removeClass("active");
                  $("#browsing").addClass("active");
                  router.push({ name: 'LookAroundRecipeView'})
                }
              }, 500);
            })
            // dispatch('GoHome')
          } else {
            let that = this
            window.addEventListener('keypress', function(event) {
              if (event.keyCode == 13) that._vm.$root.$bvModal.hide('modal')
            })
            this._vm.$root.$bvModal.msgBoxOk('이메일과 비밀번호를 확인하여 주십시오.', {
              title: 'Confirmation',
              size: 'sm',
              buttonSize: 'sm',
              okVariant: 'danger',
              headerClass: 'p-2 border-bottom-0',
              footerClass: 'p-2 border-top-0',
              centered: true,
              id: 'modal'
            })
          }
        })
        .catch((err) => {
          console.log(err)
          commit('SET_TOKEN', null)
          commit('SET_USER', null)
          commit('SET_EMAIL', null)
          commit('myblog/SET_RECIPES', null, { root: true })
          cookies.remove('auth-token')
          cookies.remove('auth-user')
          cookies.remove('user-email')
          router.push({ name: 'Login'})
          alert(err.response)
        })
    },

    logout({ commit, getters, dispatch }) {
      dispatch('clearSearchHistory')
      axios.get(SERVER.ROUTES.accounts.logout, getters.config)
        .then(() => {
          commit('SET_TOKEN', null)
          commit('SET_USER', null)
          commit('SET_EMAIL', null)
          commit('myblog/SET_RECIPES', null, { root: true })
          cookies.remove('auth-token')
          cookies.remove('auth-user')
          cookies.remove('user-email')
          router.push({ name: 'LookAroundRecipeView'})
        })
        .catch(err => {
          console.log(err.response)
          alert(err.response.data.cause)
        })
    },

    emailDupCheck({commit}, email) {
      axios.get(`/user/dup/email/${String(email)}`)
      .then(res => {
        if (res.data.result == 'success')
          commit('SET_VALID', true)
        else if (res.data.result == 'fail')
          commit('SET_VALID', false)
      })
    },

    emailAuthCodeSend({ commit }, email) {
      axios.get(SERVER.ROUTES.accounts.requestkey + String(email))
      .then(res => {
        let that = this
            window.addEventListener('keypress', function(event) {
              if (event.keyCode == 13) that._vm.$root.$bvModal.hide('modal')
            })
        if(res.data.result == 'success') {
          commit('SET_EMAIL', email)
          this._vm.$root.$bvModal.msgBoxOk('인증 코드가 발송되었습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true,
            id: 'modal'
          })
        } else {
          this._vm.$root.$bvModal.msgBoxOk('인증 코드 발송에 실패하였습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true,
            id: 'modal'
          })
        }
      })
      .catch(err => {
        console.log(err.response)
        alert(err.response)
      })
    },

    emailAuthCodeCheck({ commit, state, dispatch }, code) {
      const emailConfirm = {
        email: state.userEmail,
        code: code,
      }
      axios.post(SERVER.ROUTES.accounts.checkkey, emailConfirm)
      .then(res => {
        if (res.data.result == 'success') dispatch('GoSignup')
        else if (res.data.result == 'fail') {
          let that = this
            window.addEventListener('keypress', function(event) {
              if (event.keyCode == 13) that._vm.$root.$bvModal.hide('modal')
            })
          this._vm.$root.$bvModal.msgBoxOk('코드가 맞지 않습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true,
            id: 'modal'
          })
          commit('SET_EMAIL', null)
        }
      })
      .catch(err => {
        console.log(err.response)
        alert(err.response)
       })
    },
    fetchUser({ state, getters, commit, dispatch}) {
      axios.get(SERVER.ROUTES.accounts.baseuser, getters.config)
        .then((res) => {
          commit('SET_USER', res.data.data)
          dispatch('storage/getfollowings',state.authUser.user_id,{root : true})
        })
        .catch((err) => {
          alert(err.response)
        })
    },
    updateUser({ dispatch, state, commit }, updateData) {
      let that = this
      window.addEventListener('keypress', function(event) {
        if (event.keyCode == 13) that._vm.$root.$bvModal.hide('modal')
      })
      if (!updateData.valid.password) {
        this._vm.$root.$bvModal.msgBoxOk('비밀번호가 일치하지 않습니다.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true,
          id: 'modal'
        })
      } else if (!updateData.valid.nickname) {
        this._vm.$root.$bvModal.msgBoxOk('닉네임 중복체크를 해주세요.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true,
          id: 'modal'
        })
      } else if (updateData.valid.filesize > 10000000) {
        this._vm.$root.$bvModal.msgBoxOk('이미지 크기가 너무 큽니다.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true,
          id: 'modal'
        })
      } else {
        const formData = new FormData()
        formData.append('email', updateData.config.email)
        formData.append('password', updateData.config.password)
        formData.append('nickname', updateData.config.nickname)
        formData.append('profile', updateData.config.profile)
        formData.append('image_name', updateData.config.image_name)
        formData.append('intro', updateData.config.intro)
        formData.append('start_page', updateData.config.start_page)
        for (let i=0; i<4; i++) {
          formData.append(`sns_list[${i}].sns_name`, updateData.sns_list[i].sns_name)
          formData.append(`sns_list[${i}].sns_url`, updateData.sns_list[i].sns_url)
        }

        // for (let [key, value] of formData.entries()) {
        //   console.log(`${key} : ${value}`)
        //   if (key == 'profile') {
        //     console.log(value)
        //   }
        // }

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
                centered: true,
                id: 'modal'
              })
            dispatch('fetchUser')
            commit('SET_UPDATETF', false)
            }
          })
          .catch((err) => {
            console.log(err.response)
            alert(err.response.data.error)
          })
      }
    },
    passwordCheck({ dispatch, getters } ,password) {
      axios.post(SERVER.ROUTES.accounts.checkpassword, password, getters.config)
      .then((res) => {
        if(res.data.result == 'success') {
          cookies.set('password-check', 1)
          dispatch('GoUserInfo')
          cookies.remove('password-check')
        } else {
          // alert("비밀번호가 일치하지 않습니다.")
          let that = this
            window.addEventListener('keypress', function(event) {
              if (event.keyCode == 13) that._vm.$root.$bvModal.hide('modal')
            })
          console.log(this)
          this._vm.$root.$bvModal.msgBoxOk('비밀번호가 일치하지 않습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true,
            id: 'modal',
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
        let that = this
            window.addEventListener('keypress', function(event) {
              if (event.keyCode == 13) that._vm.$root.$bvModal.hide('modal')
            })
        if(res.data.result == 'success'){
          this._vm.$root.$bvModal.msgBoxOk('새 비밀번호가 전송되었습니다', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true,
            id: 'modal'
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
            centered: true,
            id: 'modal'
          }) 
        }
      })
      .catch((err) => {
        console.log(err.response)
        alert(err.response)
      })
    },
    signup({ commit, dispatch }, signupData) {
      let that = this
            window.addEventListener('keypress', function(event) {
              if (event.keyCode == 13) that._vm.$root.$bvModal.hide('modal')
            })
      if (!signupData.valid.password) {
        this._vm.$root.$bvModal.msgBoxOk('비밀번호를 확인해주세요.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true,
          id: 'modal'
        })
      } else if (!signupData.valid.nickname) {
        this._vm.$root.$bvModal.msgBoxOk('닉네임 중복체크를 해주세요.', {
          title: 'Confirmation',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          headerClass: 'p-2 border-bottom-0',
          footerClass: 'p-2 border-top-0',
          centered: true,
          id: 'modal'
        })
      } else {
        
        const formData = new FormData()
        formData.append('email', signupData.config.email)
        formData.append('password', signupData.config.password)
        formData.append('nickname', signupData.config.nickname)
        formData.append('profile', signupData.config.profile)
        formData.append('intro', signupData.config.intro)
        formData.append('image_name', signupData.config.image_name)

        // for (let key of formData.entries()) {
        //   console.log(`${key}`)
        // }
        axios.post(SERVER.ROUTES.accounts.signup, formData, { headers: { 'Content-Type': 'multipart/form-data' }})
          .then((res) => {
            if (res.data.result == 'success') {
              commit('SET_TOKEN', res.headers.token)
              dispatch('fetchUser')
              // dispatch('myblog/fetchMyRecipes', state.authUser, { root: true })
              this._vm.$root.$bvModal.msgBoxOk('가입되었습니다.', {
                title: 'Confirmation',
                size: 'sm',
                buttonSize: 'sm',
                okVariant: 'success',
                headerClass: 'p-2 border-bottom-0',
                footerClass: 'p-2 border-top-0',
                centered: true,
                id: 'modal'
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
                centered: true,
                id: 'modal'
              })
            }
          })
          .catch((err) => {
            console.log(err.response)
            alert(err.response)
          })
        }
    },
    fetchFollowers({ state, commit }) {
      axios.get(SERVER.ROUTES.accounts.follower + String(state.authUser.user_id))
        .then(res => {
          commit('SET_FOLLOWERS', res.data)
        })
    },
    fetchFollowings({ state, commit }) {
      axios.get(SERVER.ROUTES.accounts.following + String(state.authUser.user_id))
        .then(res => {
          commit('SET_FOLLOWINGS', res.data)
        })
    },
    hituser(state,payload){
      // console.log(state)
      // console.log("유저히트!"+payload)
      axios.put(SERVER.ROUTES.accounts.hituser+String(payload))
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        alert(err.response)
        console.log(err)
      })
    }
  },
}