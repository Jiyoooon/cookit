<template>
  <div id="wrapper">
      <h1>Welcome to Home</h1>
      <hr>
      <div v-if="!isLoggedIn" class="inform">
        임시 홈화면입니다.<br>
        내 블로그 기능을 이용하려면 로그인을 해주세요.
      </div>
      <div v-else class="inform">
        {{ authUser.nickname }} 님, 환영합니다!
      </div>
      <!-- {{ myrecipes }} -->
      <b-img v-if="isLoggedIn" id="image" v-bind="mainProps" :src="authUser.image_url" rounded="circle" alt="Circle image"></b-img>
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