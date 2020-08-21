<template>
  <div id="acc_wrapper">
    <h2>로그인</h2>
    <hr>
    <Login id='login-vue'/>
  </div>
</template>

<script>
import Login from '../../components/accounts/Login.vue'

export default {
    name: 'LoginView',
    components: {
      Login,
    },
}
</script>

<style>
</style>