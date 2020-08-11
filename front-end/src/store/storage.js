//import router from '../router'
import axios from 'axios'
import SERVER from '../api/url.js'

export default {//로컬스토리지에 저장할 필요가 있는 정보들은 여기 저장
    namespaced:true,
    state: {
      followings:['asdf'],
      followers:[],
    },
  
    getters: {
  
    },
  
    mutations: {
      SET_FOLLOWERS(state, payload){
        state.followers = payload
      },
      SET_FOLLOWINGS(state, payload){
         state.followings = payload
     },
     ADD_FOLLOWINGS(state,payload){
      state.followings.push(payload)
      console.log(state.followings)
      }
    },
  
    actions: {
      getfollowings({commit},payload){
        console.log("로그인! 팔로우중인사람가져오기")
        console.log(payload)
        axios.get(SERVER.ROUTES.accounts.following + String(payload.user_id))
        .then(res => {
          console.log("__팔로우중인사람__")
          console.log(res.data)
          commit('SET_FOLLOWINGS',res.data)
        })
        .catch((err) => {
          console.log(err.response)
        })
      },
      follow({commit,rootGetters},payload){
        //여기 수정 필
        console.log(rootGetters.config)
        axios.post(SERVER.ROUTES.accounts.follow, payload,rootGetters['moduleAccounts/config'])
          .then((res) => {
            console.log("성공")
            console.log(res)
            commit('ADD_FOLLOWINGS',payload)
          })
          .catch((err) => {
            console.log(err.response)
            alert("비밀번호를 입력해주세요!")
          })
      }
    },
  }