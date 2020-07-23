<template>
  <div>
    <b-container fluid="lg" id ="userinfo-container">
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
          <b-button v-if="!userEmailValid" disabled variant="primary" block>인증번호 전송</b-button>
          <b-button v-else variant="primary" @click='emailAuthCodeSend(userEmail)'>인증번호 전송</b-button>
        </b-col>
        <b-col sm="1"></b-col>
      </b-row>  

      <b-row align-v="center" align-h="center">
        <b-col sm="1"></b-col>
        <b-col sm="2">
          <label for="emailcode">인증코드</label>
        </b-col>
        <b-col sm="8">
          <b-form-input id="auth-code" aria-describedby="code-feedback" v-model="authCode" placeholder="인증코드"></b-form-input>
        </b-col>
        <b-col sm="1"></b-col>
      </b-row>
      <b-row><b-col></b-col></b-row>
      <b-row align-v="center" align-h="center" >
          <b-col sm="2"></b-col>
          <b-col sm="4">
            <b-button variant="primary" @click='emailAuthCodeCheck(authCode)' block>확인</b-button>
          </b-col>
          <b-col sm="4">
            <b-button variant="danger" to="/" block>취소</b-button>
          </b-col>
          <b-col sm="2"></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
  export default {
    name: 'EmailAuth',
    data() {
      return {
        userEmail: null,
        authCode: null,
        sendBtnActive: false
      }
    },
    computed: {
      userEmailValid() {
        if (this.userEmail == null || this.userEmail.length == 0) return null
        // 중복 여부 확인

        // 정규식으로 이메일 양식 확인
        var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
        if (this.userEmail.match(regExp) != null) return true
        else return false
      },
    },
    methods: {
      ...mapActions('accounts', ['emailAuthCodeSend', 'emailAuthCodeCheck'])
    }
  }
</script>

<style>

</style>
