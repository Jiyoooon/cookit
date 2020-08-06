<template>
  <div>
    <UserDelete id="wrapper"/>
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