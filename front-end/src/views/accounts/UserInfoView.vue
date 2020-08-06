<template>
  <div id="wrapper">
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

</style>