<template>
  <div>

  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'LogoutView',
    methods: {
        ...mapActions('accounts', ['logout'])
    },
    mounted() {
        this.logout()
    },
}
</script>

<style>

</style>