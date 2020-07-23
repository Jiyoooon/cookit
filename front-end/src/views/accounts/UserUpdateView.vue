<template>
  <div>
    <UserUpdate id="userupdate-vue"/>
  </div>
</template>

<script>
import UserUpdate from '../../components/accounts/UserUpdate.vue'

export default {

    name: 'UserUpdateView',
    components: {
      UserUpdate,
    },
}
</script>

<style>
  #userupdate-vue{
    width: 40%;
    margin-left: auto;
    margin-right: auto;
  }
</style>