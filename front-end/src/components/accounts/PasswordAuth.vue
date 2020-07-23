<template>
  <div>
    <b-container fluid="lg" id="userinfo-container">
    <b-row align-v="center">
        <b-col sm="2"></b-col>
      <b-col sm="6">
        <b-form-input type="password" id="password" aria-describedby="password-feedback" placeholder="비밀번호를 입력하세요."></b-form-input>
      </b-col>
      <b-col sm="2">
        <b-button type="submit" variant="primary" id="okButton" to="/userInfo" block>확인</b-button>
      </b-col>
      <b-col sm="2"></b-col>
    </b-row>
  </b-container>
  </div>
</template>

<script>
export default {
    name: 'PasswordAuth',
}
</script>

<style>

</style>