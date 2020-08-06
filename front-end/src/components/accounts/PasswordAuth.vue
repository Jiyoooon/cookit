<template>
  <div>
    <b-container fluid="lg" id="userinfo-container">
    <b-row align-v="center">
        <b-col sm="2"></b-col>
      <b-col sm="6">
        <b-form-input @keydown.enter = "passwordCheck(passwordData)" v-model='passwordData.password' type="password" id="password" aria-describedby="password-feedback" placeholder="비밀번호를 입력하세요."></b-form-input>
      </b-col>
      <b-col sm="2">
        <b-button @click="passwordCheck(passwordData)" type="submit" variant="primary" id="okButton"  block>확인</b-button>
      </b-col>
      <b-col sm="2"></b-col>
    </b-row>
  </b-container>
  </div>
</template>

<script>

import { mapActions } from 'vuex'

export default {
  name: 'PasswordAuth',
  data(){
    return{
      passwordData:{
        password: null,
      }
    }
  },
  methods:{
    // 버튼을 누르면 api이용 비밀번호일치여부를 받아야함 => action
    ...mapActions('accounts',[
      'passwordCheck'
    ])
  }
}
</script>

<style>

</style>