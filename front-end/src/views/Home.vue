<template>
  <div>
      <h1>Welcome to Home</h1>
      {{ isLoggedIn }}
<<<<<<< HEAD
      {{ authUser }}
=======
      <b-icon icon="chevron-up" shift-v="16" class="bg-info"></b-icon>
>>>>>>> develop
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'

export default {
    name: 'Home',
    computed: {
        ...mapGetters('accounts', ['isLoggedIn']),
        ...mapState('accounts', ['authUser']),
    },
    methods: {
    },
    // mounted() {
    //     const image = this.authUser.profile_image
    //     const reader = new FileReader();
    //     reader.onload = (event) => {
    //         this.imageURL = event.target.result
    //       console.log(this.imageURL)
    //     }
    //     reader.readAsDataURL(image);
    // }
}
</script>

<style>

</style>