<template>
  <div>
      <h1>Welcome to Home</h1>
      {{ isLoggedIn }}
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
    name: 'Home',
    computed: {
        ...mapGetters('accounts', ['isLoggedIn'])
    }
}
</script>

<style>

</style>