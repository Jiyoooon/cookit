<template>
  <div id="acc_wrapper">
    <h2>회원정보 조회</h2>
    <hr>
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
</style>