<template>
  <div id="wrapper">
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

.inform {
  text-align: center;
  margin: 2em;
}
</style>