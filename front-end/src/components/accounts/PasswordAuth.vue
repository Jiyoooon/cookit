<template>
  <div>
    <b-container fluid="lg" id="userinfo-container">
    <b-row align-v="center">
      <b-col sm="3">비밀번호</b-col>
      <b-col sm="9">
        <b-form-input type="password" id="password" aria-describedby="password-feedback"></b-form-input>
      </b-col>
    </b-row>
    <b-row align-v="center" align-h="center">
      <b-col sm="4"></b-col>
      <b-col sm="4">
        <b-button type="submit" variant="primary" id="okButton" to="/userInfo" block>확인</b-button>
      </b-col>
      <b-col sm="4"></b-col>
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