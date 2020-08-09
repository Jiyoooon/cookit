<template>
  <div id="wrapper">
    <h2>비밀번호 찾기</h2>
    <hr>
    <div class="inform">가입한 이메일을 입력해 주세요.<br>
    이메일로 새로운 비밀번호를 발송합니다.</div>
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