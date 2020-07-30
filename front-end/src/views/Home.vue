<template>
  <div>
      <h1>Welcome to Home</h1>
      {{ isLoggedIn }}
      <hr>
      {{ authUser }}
      <hr>
      <!-- {{ myrecipes }} -->
      <b-img id="image" v-bind="mainProps" src="http://i3a201.p.ssafy.io:8080/images/profile/profile_image_14.png" rounded="circle" alt="Circle image"></b-img>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'

export default {
    name: 'Home',
    data() {
      return {
         mainProps: { blank: false, blankColor: '#777', width: 75, height: 75, class: 'm1' }
      }
    },
    computed: {
        ...mapGetters('accounts', ['isLoggedIn']),
        ...mapState('accounts', ['authUser']),
        // ...mapState('myblog', ['myrecipes'])
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
  /* #image {
    width: 10px;
    height: 10px;
  } */
</style>