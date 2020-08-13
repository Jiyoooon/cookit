<template>
  <div id="acc_wrapper">
    <h2>회원가입</h2>
    <hr>
    <signupForm/>
  </div>
</template>

<script>
  import SignupForm from "@/components/accounts/SignupForm.vue"
  export default {
    name: 'SignupView',
    components:{
      SignupForm
    },
  }
</script>

<style>
</style>