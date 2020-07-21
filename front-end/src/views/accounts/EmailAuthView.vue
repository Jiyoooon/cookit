<template>
  <div id="email-auth">
    <h2>이메일 확인</h2>
    <hr>
     <div class="inner-center">회원 가입을 위해서는 E-mail 인증이 필요합니다.<br>
    입력한 이메일로 인증코드를 보냅니다.</div>
    <EmailAuth/>
  </div>
</template>

<script>
import EmailAuth from "@/components/accounts/EmailAuth.vue"
export default {
    name: 'EmailAuthView',
    components: {
      EmailAuth,
    }
}
</script>

<style>
  #email-auth {
    width: 50%;
    display: block;
    margin: 0px auto;
  }
  .inner-center {
    text-align: center;
    margin: 2em;
  }
</style>