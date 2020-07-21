<template>
  <div id="userDetail">
    <h2>회원정보 조회</h2>
    <userDetail />
  </div>
</template>

<script>
import UserDetail from "@/components/accounts/UserDetail.vue"
export default {
    name: 'UserInfoView',
    components: {
      UserDetail
    },
}
</script>

<style>
  #userDetail {
    width: 50%;
    display: block;
    margin: 0px auto;
  }
</style>