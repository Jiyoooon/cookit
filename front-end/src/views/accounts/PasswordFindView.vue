<template>
  <div id="password-find">
    <h2>비밀번호 찾기</h2>
    <hr>
    <div class="inner-center">비밀번호를 찾고 싶은 이메일을 입력해 주세요.<br>
    새로운 비밀번호를 이메일로 발송합니다.</div>
    <passwordFind />
  </div>
</template>

<script>
import PasswordFind from "@/components/accounts/PasswordFind.vue"
export default {
    name: 'PasswordFindView',
    components: {
      PasswordFind,
    },
    mathods:{

    },
}
</script>

<style>
  #password-find {
    width: 50%;
    display: block;
    margin: 0px auto;
  }
  .inner-center {
    text-align: center;
    margin: 2em;
  }
</style>