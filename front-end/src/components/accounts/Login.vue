<template>
  <div>
    <b-container fluid="lg" id ="login-container">
      <b-row align-v = "center">
        <b-col sm="2">
          <label for="login-email">Email:</label>
        </b-col>
        <b-col sm="7">
          <b-form-input 
            id="login-email" 
            v-model="loginData.email"
            aria-describedby="input-email-feedback"
            placeholder="email"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="input-email-feedback">
            영어와 숫자를 혼합하여 입력하여 주세요.(3자이상 8자이하)
          </b-form-invalid-feedback>
        </b-col>
      </b-row>  

      <b-row align-v = "center">
        <b-col sm="2">
          <label for="login-password">Password:</label>
        </b-col>
        <b-col sm="7">
          <b-form-input
            type="password" 
            id="login-passwod" 
            v-model="loginData.password"
            aria-describedby="input-password-feedback"
            placeholder="Password"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="input-password-feedback">
            영문자와 숫자를 포함해 8글자 이상으로 입력하세요.
          </b-form-invalid-feedback>
        </b-col>
      </b-row>  

      <b-row align-v="center">
          <b-col sm="2"></b-col>
          <b-col sm="2">
            <b-button variant="info" @click="login(loginData)" block>로그인</b-button>
          </b-col>
          <b-col sm="2">
            <b-button variant="info" @click="GoEmailAuth" block>회원가입</b-button>
          </b-col>
          <b-col sm="3">
            <b-button variant="info" @click="GoPasswordFind" block>비밀번호 찾기</b-button>
          </b-col>
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
        ...mapActions('accounts', ['login', 'GoEmailAuth','GoPasswordFind'])
    }
  }

</script>

<style>
#login-container{
  margin-left: auto;
  margin-right: auto;
}
</style>