<template>
  <div id="acc_wrapper">
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
</style>