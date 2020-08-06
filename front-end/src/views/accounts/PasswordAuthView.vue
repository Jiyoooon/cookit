<template>
  <div id="wrapper">
    <h2>비밀번호 확인</h2>
    <hr>
    <div class="inform">회원 정보 조회를 위해서는 인증이 필요합니다.<br>
    비밀번호 입력 후 확인 버튼을 클릭해 주세요.</div>
    <passwordAuth />
  </div>
</template>

<script>
import PasswordAuth from "@/components/accounts/PasswordAuth.vue"
export default {
    name: 'PasswordAuthView',
    components: {
      PasswordAuth
    }
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