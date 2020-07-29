<template>
  <div>
      <h1>Welcome to Home</h1>
      {{ isLoggedIn }}
      <b-icon icon="chevron-up" shift-v="16" class="bg-info"></b-icon>
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