<template>
  <div>
    <h1>로그인</h1>
    <hr>
    <Login />
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