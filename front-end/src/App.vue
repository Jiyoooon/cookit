<template>
  <div>
    <router-link to="/userInfo">userInfo</router-link>
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',

  components: {
  },

  data: () => ({
    //
  }),
};
</script>
