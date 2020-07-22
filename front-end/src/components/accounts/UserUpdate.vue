<template>
  <div>
      <div id="app">
  <v-app id="inspire">
    <div>
      <v-alert
        text
        color="info"
        width="50vw"
        type="success"
        
      >
        <h3 class="headline">회원정보 수정</h3>
        <div>수정되었습니다.</div>
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
<b-button v-b-modal.modal-1>Launch demo modal</b-button>
<b-modal id="modal-1" title="BootstrapVue">
    <p class="my-4">Hello from modal!</p>
  </b-modal>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {

    name: 'UserUpdate',
    methods: {
      ...mapActions('accounts', ['RedirectAfterUserUpdate'])
    }
}
</script>

<style>

</style>