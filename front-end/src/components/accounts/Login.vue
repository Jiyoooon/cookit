<template>
  <div>
    <b-container fluid="lg" id ="login-container">
      <b-row align-v="center" align-h="center">
        <b-col sm="2">이메일</b-col>
        <b-col sm="8">
          <b-form-input id="login-email" v-model="loginData.email">
          </b-form-input>
        </b-col>
      </b-row>  

      <b-row align-v="center" align-h="center">
        <b-col sm="2">비밀번호</b-col>
        <b-col sm="8">
          <b-form-input type="password" id="login-password" v-model="loginData.password" @keyup.enter="login(loginData)">
          </b-form-input>
        </b-col>
<<<<<<< HEAD
      </b-row>  

      <b-row align-v="center">
          <b-col sm="2"></b-col>
          <b-col sm="2">
            <b-button variant="info" @click="login(loginData)" block>로그인</b-button>
          </b-col>
          <b-col sm="2">
            <b-button variant="info" @click="GoSignup" block>회원가입</b-button>
          </b-col>
          <b-col sm="3">
            <b-button variant="info" @click="GoPasswordFind" block>비밀번호 찾기</b-button>
          </b-col>
=======
      </b-row>
      <b-row><b-col></b-col></b-row>
      <b-row align-v="center" align-h="center">
        <div class="inline-block-btn btn-style1" @click="login(loginData)"><strong>로그인</strong></div>
        <div class="inline-block-btn btn-style2" @click="GoEmailAuth">회원가입</div>
        <div class="inline-block-btn btn-style2" @click="GoPasswordFind">비밀번호 찾기</div>
>>>>>>> develop
      </b-row>    
    </b-container>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'Login',
    data() {
        return {
            loginData: {
                email: null,
                password: null,
            },
        }
    },
    computed: {
    },
    methods: {
        ...mapActions('accounts', ['login', 'GoSignup','GoPasswordFind'])
    }
  }

</script>

<style>
#login-container{
  width: 90%;
  margin: 0px auto;
}
</style>