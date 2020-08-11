//import router from '../router'
import axios from 'axios'
import SERVER from '../api/url.js'

export default {//로컬스토리지에 저장할 필요가 있는 정보들은 여기 저장
    namespaced:true,
    state: {
      followings:[],
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
      },
      DEL_FOLLOWINGS(state,payload){
        state.followings.splice(state.followings.map(x => x.user_id).indexOf(payload), 1)
        state.followings = [...state.followings]
      }
    },
  
    actions: {
      getfollowings({commit,rootState}){
        console.log("내가 팔로우중인사람가져오기")
        console.log(rootState.accounts.authUser)
        axios.get(SERVER.ROUTES.accounts.following + String(rootState.accounts.authUser.user_id))
        .then(res => {
          console.log("__내가 팔로우중인사람__")
          console.log(res.data)
          commit('SET_FOLLOWINGS',res.data)
        })
        .catch((err) => {
          console.log(err.response)
        })
      },
      getfollowers({commit,rootState}){
        console.log("나를 팔로우중인사람가져오기")
        axios.get(SERVER.ROUTES.accounts.follower + String(rootState.accounts.authUser.user_id))
        .then(res => {
          console.log("__나를 팔로우중인사람__")
          console.log(res.data)
          commit('SET_FOLLOWERS',res.data)
        })
        .catch((err) => {
          console.log(err.response)
        })
      },

      follow({commit,rootGetters},payload){
        // const headerConfig = { headers: {
        //   'Authorization': `token ${rootState['accounts/authToken']}`,
        // }}
        console.log((payload))
        // const targetid = {
        //   id : String(payload) 
        // }
        //rootGetters['accounts/config']
        axios.post(SERVER.ROUTES.accounts.follow,String(payload),rootGetters['accounts/config'])
          .then((res) => {
            console.log("성공")
            console.log(res)
            commit('ADD_FOLLOWINGS',payload)
          })
          .catch((err) => {
            console.log(err.response)
            alert("팔로우실패")
          })
      },
      unfollow({commit,rootGetters},payload){
        axios.post(SERVER.ROUTES.accounts.unfollow,String(payload),rootGetters['accounts/config'])
          .then((res) => {
            console.log("언팔성공")
            console.log(res)
            commit('DEL_FOLLOWINGS',payload)
          })
          .catch((err) => {
            console.log(err.response)
            alert("언팔로우실패")
          })
      },
    },
  }