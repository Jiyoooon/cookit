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
    created() {
        console.log('22222')
        this.logout
    },
}
</script>

<style>

</style>