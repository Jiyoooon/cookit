<template>
  <div>
    <b-container fluid="lg" id ="userinfo-container">
      <b-row align-v = "center">
        <b-col sm="3">
          <label for="input-email">E-mail</label>
        </b-col>
        <b-col sm="6">
          <b-form-input 
            id="input-email" 
            v-model="userid"
            :state="useridState"
            aria-describedby="input-email-help input-email-feedback"
            placeholder="이메일"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="email-feedback">
            아이디를 입력하세요(영어만 가능, 최소8글자)
          </b-form-invalid-feedback>
        </b-col>
        <b-col sm="3">
          <b-button variant="primary">인증번호 전송</b-button>
        </b-col>
      </b-row>  

      <b-row align-v="center" align-h="center">
        <b-col sm="3">
          <label for="emailcode">인증코드</label>
        </b-col>
        <b-col sm="6">
          <b-form-input id="emailcode" aria-describedby="code-feedback" v-model="password" :state="passwordValid" placeholder="인증코드"></b-form-input>
          <b-form-invalid-feedback id="code-feedback">
          인증코드가 올바르지 않습니다. </b-form-invalid-feedback>
        </b-col>
        <b-col sm="3">
          <b-button variant="primary">확인</b-button>
        </b-col>
      </b-row>
      <b-row align-v="center" align-h="center" >
          <b-col sm="2"></b-col>
          <b-col sm="4">
            <b-button variant="primary" to="/Signup" block>확인</b-button>
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
  export default {
    name: 'EmailAuth',
  }
</script>

<style>

</style>
