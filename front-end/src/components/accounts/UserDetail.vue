<template>
  <div>
    <h1>개인정보 조회</h1>
    <h3>아이디 {{ user.username }}</h3>
    <h3>이메일 {{ user.email }} </h3>
    <h3>비밀번호</h3>
    <h3>비밀번호 재입력</h3>
    <h3>닉네임</h3>
    <h3>프로필 사진</h3>
    <h3>소개글</h3>
    <button>수정</button>
    <button>탈퇴</button>
  </div>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
export default {
    name: 'UserDetail',
    props: {
      user: Object
    }
}
</script>

<style>

</style>