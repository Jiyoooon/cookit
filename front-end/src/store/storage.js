import axios from 'axios'
import SERVER from '../api/url.js'

export default {//로컬스토리지에 저장할 필요가 있는 정보들은 여기 저장
    namespaced:true,
    state: {
      myfollowings:[],//나
      myfollowers:[],
      followings:[],//다른사람
      followers:[],
    },
    mutations: {
      SET_FOLLOWERS(state, payload){
        if(payload.user == payload.Me){
          state.myfollowers = payload.array
          state.followers = payload.array
        }
        else {
          state.followers = payload.array
        }
      },
      SET_FOLLOWINGS(state, payload){
        if(payload.user == payload.Me){
          state.myfollowings = payload.array
          state.followings = payload.array
        }
        else {
          state.followings = payload.array
        }
      },
    },
  
    actions: {
      getfollowings({commit,rootState},user){
        axios.get(SERVER.ROUTES.accounts.following + String(user))
        .then(res => {
          commit('SET_FOLLOWINGS',{ user : user, array : res.data, Me : rootState.accounts.authUser.user_id})
        })
        .catch((err) => {
          console.log(err)
        })
      },

      getfollowers({commit,rootState},user){
        axios.get(SERVER.ROUTES.accounts.follower + String(user))
        .then(res => {
          commit('SET_FOLLOWERS',{user : user, array : res.data, Me : rootState.accounts.authUser.user_id})
        })
        .catch((err) => {
          console.log(err)
        })
      },

      follow({rootState,rootGetters,dispatch},payload){
        const targetid = {
          id : String(payload) 
        }
        
        axios.post(SERVER.ROUTES.accounts.follow+String(payload),targetid,rootGetters['accounts/config'])
          .then(() => {
            dispatch('getfollowings',rootState.accounts.authUser.user_id)
          })
          .catch((err) => {
            console.log(err)
            alert("팔로우실패")
          })
      },
      unfollow({rootState,dispatch},payload){
        const headerConfig = {
          headers: {
            Authorization: `token ${rootState.accounts.authToken}`,
          },
          data:{
            id : String(payload)
          }
        }
        axios.delete(SERVER.ROUTES.accounts.unfollow+String(payload),headerConfig)
          .then(() => {
            dispatch('getfollowings',rootState.accounts.authUser.user_id)
          })
          .catch((err) => {
            console.log(err)
            alert("언팔로우실패")
          })
      },
    },
  }