<template>
  <div id="wrapper">
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
#wrapper {
  width: 690px;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 5em 1em 8em 1em;
}

@media (max-width: 768px) {
  #wrapper {
    width: 90%;
    display: block;
    margin: 0px auto;
    background-color: #fff;
    padding: 5em 1em 8em 1em;
  }
}
</style>