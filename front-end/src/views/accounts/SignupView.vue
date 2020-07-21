<template>
  <div id="signupForm">
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
  #signupForm{
    width: 50%;
    display: block;
    margin: 0px auto;
  }
</style>