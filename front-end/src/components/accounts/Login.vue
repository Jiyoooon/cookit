<template>
  <div>
      <div>
      <label for="login-id">Id : </label>
      <input v-model="loginData.Id" type="text" id="login-id">
    </div> 
    <div>
      <label for="login-password">password : </label>
      <input v-model="loginData.password" type="password" id="login-password">
    </div>
    <button @click="login(loginData)">login</button>
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
    methods: {
        ...mapActions('accounts', ['login'])
    }

}
</script>

<style>

</style>