<template>
  <div>
   
    <b-container fluid>
      <b-row class="my-1">
        <b-col sm="3"><!--길이-->
          <label for="input-userid">E-mail</label>
        </b-col>
        <b-col sm="6">
          <b-form-input 
            id="input-userid" 
            v-model="userid"
            :state="useridState"
            aria-describedby="input-userid-help input-userid-feedback"
            placeholder=""
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="userid-feedback">
            아이디를 입력하세요(영어만 가능, 최소8글자)
          </b-form-invalid-feedback>
        </b-col>
        <b-col sm="3">
          <b-button variant="primary">확인</b-button>
        </b-col>
      </b-row>  

      <b-row class="my-1">
        <b-col sm="3"><!--길이-->
          <label for="input-none">인증코드</label>
        </b-col>
        <b-col sm="6">
          <b-form-input type="password" id="password" aria-describedby="password-feedback" v-model="password" :state="passwordValid"></b-form-input>
          <b-form-invalid-feedback id="password-feedback">
          영문자와 숫자를 포함해 8글자 이상으로 입력하세요. </b-form-invalid-feedback>
        </b-col>
        <b-col sm="3">
          <b-button variant="primary">확인</b-button>
        </b-col>
      </b-row>
    </b-container>
    <b-button variant="primary">확인</b-button>

  </div>
</template>

<script>
  export default {
    
  }
</script>

<style>

</style>