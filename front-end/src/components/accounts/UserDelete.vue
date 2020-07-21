<template>
  <div>
      <div id="app">
  <v-app id="inspire">
    <div>
      <v-alert
        text
        color="info"
      >
        <h3 class="headline">회원 탈퇴</h3>
        <div>탈퇴되었습니다.</div>
        <v-col class="shrink">
            <v-btn
              color="info"
              outlined
              @click="RedirectAfterUserUpdate"
            >
              확인
            </v-btn>
          </v-col>
      </v-alert>
    </div>
  </v-app>
</div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'UserDelete',
    methods: {
        ...mapActions('accounts', ['RedirectAfterUserDelete'])
    },
}
</script>

<style>

</style>