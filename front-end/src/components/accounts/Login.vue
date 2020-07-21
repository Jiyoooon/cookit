<template>
  <div>
      <div>
      <label for="login-id">Id : </label>
      <input v-model="LoginData.Id" type="text" id="login-id">
    </div> 
    <div>
      <label for="login-password">password : </label>
      <input v-model="LoginData.password" type="password" id="login-password">
    </div>
    <button>login</button>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'Login',
    data() {
        return {
            LoginData: {
                Id: null,
                password: null,
            },
        }
    },

}
</script>

<style>

</style>