<template>
  <div>
    <router-link to="/Signup">회원가입</router-link>&nbsp;
    <router-link to="/Login">로그인</router-link>&nbsp;
    <router-link to="/PasswordAuth">회원정보 수정</router-link>&nbsp;
    <router-link to="/Logout">로그아웃</router-link>&nbsp;
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',

  components: {
  },

  data: () => ({
    //
  }),
};
</script>
