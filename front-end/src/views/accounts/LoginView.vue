<template>
  <div>
    <h1>로그인</h1>
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
  #login-vue{
    width: 50%;
    display: block;
    margin: 0px auto;
  }
</style>