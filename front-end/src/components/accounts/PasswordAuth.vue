<template>
  <div>
    <b-container fluid="lg" id="userinfo-container">
    <b-row align-v="center">
        <b-col sm="2"></b-col>
      <b-col sm="6">
        <b-form-input v-model='passwordData.password' type="password" @keyup.enter="passwordCheck(passwordData)" placeholder="비밀번호를 입력하세요."></b-form-input>
      </b-col>
      <b-col sm="2">
        <div class="block-btn btn-style1" @click="passwordCheck(passwordData)" block>확인</div>
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