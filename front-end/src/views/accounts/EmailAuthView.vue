<template>
  <div id="acc_wrapper">
    <h2>회원가입</h2>
    <hr>
     <div class="inform">회원 가입을 위해서는 이메일 인증이 필요합니다.<br>
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
#acc_wrapper {
  width: 690px;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 5em 1em 8em 1em;
}

@media (max-width: 768px) {
  #acc_wrapper {
    width: 90%;
  }
}

.inform {
  text-align: center;
  margin: 2em;
}
</style>