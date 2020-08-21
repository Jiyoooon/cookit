<template>
  <div>
    <b-container fluid>
      <b-row align-v="center">
        <b-col sm="12" md="2">이메일</b-col>
        <b-col sm="7" md="6">
          <b-form-input 
            id="input-email" 
            v-model="userEmail"
            :state="userEmailValid"
            aria-describedby="email-feedback"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="email-feedback">
            이메일을 확인해주세요.
          </b-form-invalid-feedback>
        </b-col>
        <b-col sm="5" md="4">
          <div v-if="!userEmailValid" class="block-btn btn-style3">새 비밀번호 전송</div>
          <div v-else class="block-btn btn-style1" @click='sendNewPassword(userEmail)'>새 비밀번호 전송</div>
        </b-col>
      </b-row>
      <b-row><b-col></b-col></b-row>
      <b-row align-v="center" align-h="center">
        <b-col cols="4"></b-col>
        <b-col cols="4"><div class="block-btn btn-style2"  @click="$router.go(-1)" block>취소</div></b-col>
        <b-col cols="4"></b-col>
      </b-row>
  </b-container>
  </div>
</template>

<script>

import { mapActions } from 'vuex'

export default {
  name: 'PasswordFind',
  data(){
      return{
        userEmail:'',
      }
  },
  methods:{
    // 버튼을 누르면 api이용 비밀번호일치여부를 받아야함 => action
    ...mapActions('accounts',[
      'sendNewPassword'
    ])
  },
  computed:{
    userEmailValid() {
        if (this.userEmail == null || this.userEmail.length == 0) return null
        // 중복 여부 확인

        // 정규식으로 이메일 양식 확인
        var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
        if (this.userEmail.match(regExp) != null) return true
        else return false
    },
  },
}
</script>

<style>

</style>