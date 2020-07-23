<template>
  <div>
    <b-container fluid="lg" id ="login-container">
      <b-row align-v = "center">
        <b-col sm="2">
          <label for="login-id">Id : </label>
        </b-col>
        <b-col sm="6">
          <b-form-input 
            id="login-id" 
            v-model="loginData.Id"
            :state="idValid"
            aria-describedby="input-id-feedback"
            placeholder="Id"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="input-id-feedback">
            영어와 숫자를 혼합하여 입력하여 주세요.(3자이상 8자이하)
          </b-form-invalid-feedback>
        </b-col>
      </b-row>  

      <b-row align-v = "center">
        <b-col sm="2">
          <label for="login-password">Password : </label>
        </b-col>
        <b-col sm="6">
          <b-form-input
            type="password" 
            id="login-passwod" 
            v-model="loginData.password"
            :state="passwordValid"
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
            <b-button variant="primary" @click="login(loginData)" block>로그인</b-button>
          </b-col>
          <b-col sm="2">
            <b-button variant="primary" to="/signup" block>회원가입</b-button>
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
                Id: null,
                password: null,
            },
        }
    },
    computed: {
      idValid() {
        if (this.loginData.Id == null) return null;
        const idLength = this.loginData.Id.length
        if (idLength < 3 || idLength > 12) return false;
        return true

        // var alphaFlag = false, numFlag = false

        // for (var i=0; i < idLength; i++){
        //   const ch = this.loginData.Id[i]
        //   if ('0' <= ch && ch <= '9') numFlag = true;
        //   else if ('a' <= ch && ch <= 'z') alphaFlag = true;
        // }
        // return numFlag && alphaFlag
      },
      passwordValid() {
        if (this.loginData.password == null) return null;
        const len = this.loginData.password.length
        if (len < 8) return false;
        var numFlag = false, alphaFlag = false
        for (var i = 0; i < len; i++) {
          const ch = this.loginData.password[i]
          if ('0' <= ch && ch <= '9') numFlag = true
          else if ('a' <= ch && ch <= 'z') alphaFlag= true
        }
        return numFlag && alphaFlag
      },
    },
    methods: {
        ...mapActions('accounts', ['login'])
    }
  }

</script>

<style>

</style>