<template>
  <div>
    <b-container fluid id ="userinfo-container">
      <b-row>
        <b-col md="2" class="mt-2">이메일</b-col>
        <b-col md="7">
          <b-form-input 
            id="input-email" 
            v-model="userEmail"
            :state="userEmailValid"
            aria-describedby="email-feedback"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="email-feedback">
            이미 가입된 이메일이거나, 형식이 잘못되었습니다.
          </b-form-invalid-feedback>
        </b-col>
        <b-col md="3">
          <div class="block-btn btn-style3" v-if="!userEmailValid">인증번호 전송</div>
          <div class="block-btn btn-style1" v-else @click='emailAuthCodeSend(userEmail)' block>인증번호 전송</div>
        </b-col>
      </b-row>  

      <b-row align-v="center" align-h="center">
        <b-col md="2">
          <label for="emailcode">인증코드</label>
        </b-col>
        <b-col md="10">
          <b-form-input id="auth-code" aria-describedby="code-feedback" v-model="authCode"></b-form-input>
        </b-col>
      </b-row>
      <b-row><b-col></b-col></b-row>
      <b-row align-v="center" align-h="center" >
          <b-col cols="3"></b-col>
          <b-col cols="3">
            <div class="block-btn btn-style1" @click='emailAuthCodeCheck(authCode)' block>확인</div>
          </b-col>
          <b-col cols="3">
            <div class="block-btn btn-style2" @click="$router.go(-1)" block>취소</div>
          </b-col>
          <b-col cols="3"></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
  export default {
    name: 'EmailAuth',
    data() {
      return {
        userEmail: null,
        authCode: null,
        sendBtnActive: false,
      }
    },
    methods: {
      ...mapActions('accounts', ['emailDupCheck', 'emailAuthCodeSend', 'emailAuthCodeCheck'])
    },
    computed: {
      ...mapGetters('accounts', ['isValidEmail']),
      userEmailValid() {
        if (this.userEmail == null || this.userEmail.length == 0) return null
        // 정규식으로 이메일 양식 확인
        var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
        if (this.userEmail.match(regExp) == null) return false

        // 이메일 중복 체크
        this.emailDupCheck(this.userEmail);
        return this.isValidEmail;
      },
    }
  }
</script>

<style>
.inline-block-btn {
  display: inline-block;
  justify-content: space-around;
  align-items: center;
  margin: 1em;
  cursor: pointer;
  padding: 6px 10px 6px 10px;
}

.block-btn {
  display: block;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  cursor: pointer;
  padding: 6px 10px 6px 10px;
}

/* 기본 청록색 버튼 */
.btn-style1 {
  background-color: #53AAA1;
  color: white;
}

/* 흰색에 청록 라인 버튼 */
.btn-style2 {
  border: 1px solid #53AAA1;
  color: #53AAA1;
}

/* disabled 청록색 버튼 */
.btn-style3 {
  background-color: #4B9A91;
  color: #74BCB4;
  cursor: default;
}

/* 완전 경고 빨간색 버튼 */
.btn-style4 {
  background-color: darkred;
  color: white;
}
</style>
