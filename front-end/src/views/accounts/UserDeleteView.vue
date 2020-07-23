<template>
  <div>
    <UserDelete id="userdelete-vue"/>
  </div>
</template>

<script>
import UserDelete from '../../components/accounts/UserDelete.vue'
export default {
    name: 'UserDeleteView',
    components: {
      UserDelete,
    },
}
</script>

<style>
  #userdelete-vue{
    width: 40%;
    display: block;
    margin: 0px auto;
  }
</style>