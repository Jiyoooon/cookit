<template>
  <div>
    <b-container fluid="lg" id ="passwordfind-container">
      <b-row align-v = "center">
        <b-col sm="1"></b-col>
        <b-col sm="2">
          <label for="input-email">E-mail</label>
        </b-col>
        <b-col sm="5">
          <b-form-input 
            id="input-email" 
            v-model="userEmail"
            :state="userEmailValid"
            aria-describedby="email-feedback"
            placeholder="이메일"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="email-feedback">
            이메일을 확인해주세요.
          </b-form-invalid-feedback>
        </b-col>
        <b-col sm="3">
          <b-button v-if="!userEmailValid" disabled variant="primary" block>새 비밀번호 전송</b-button>
          <b-button v-else variant="primary" @click='sendNewPassword(userEmail)'>새 비밀번호 전송</b-button>
        </b-col>
        <b-col sm="1"></b-col>
      </b-row> 
  </b-container>
  </div>
</template>

<script>

import { mapActions } from 'vuex'

export default {
  name: 'PasswordFind',
  data(){
      return{
        userEmail:null,
      }
  },
  methods:{
    // 버튼을 누르면 api이용 비밀번호일치여부를 받아야함 => action
    ...mapActions('accounts',[
      'sendNewPassword'
    ])
  },
  computed:{
    userEmailValid() {
        if (this.userEmail == null || this.userEmail.length == 0) return null
        // 중복 여부 확인

        // 정규식으로 이메일 양식 확인
        var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
        if (this.userEmail.match(regExp) != null) return true
        else return false
    },
  },
}
</script>

<style>

</style>