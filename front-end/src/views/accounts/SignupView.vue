<template>
  <div>
   
    <button>signup</button>
    
    <b-container fluid>
      <b-row class="my-1">
        <b-col sm="3"><!--길이-->
          <label for="input-userid">아이디</label>
        </b-col>
        <b-col sm="9">
          <b-form-input 
            id="input-userid" 
            v-model="userid"
            :state="useridState"
            aria-describedby="input-userid-help input-userid-feedback"
            placeholder=""
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="userid-feedback">
            아이디를 입력하세요(영어만 가능, 최소8글자)
          </b-form-invalid-feedback>
        </b-col>
      </b-row>

      <b-row class="my-1">

        

        <b-col sm="3"><!--길이-->
          <label for="input-none">비밀번호</label>
        </b-col>
        <b-col sm="9">
          <b-form-input type="password" id="password" aria-describedby="password-feedback" v-model="password" :state="passwordValid"></b-form-input>
          <b-form-invalid-feedback id="password-feedback">
          영문자와 숫자를 포함해 8글자 이상으로 입력하세요. </b-form-invalid-feedback>
        </b-col>
      </b-row>

      <b-row class="my-1">
        <b-col sm="3"><!--길이-->
          <label for="input-none">비밀번호 재확인</label>
        </b-col>
        <b-col sm="9">
          <b-form-input type="password" id="password" aria-describedby="password-again-feedback" v-model="passwordAgain" :state="passwordAgainValid"></b-form-input>
          <b-form-invalid-feedback id="password-again-feedback">
          비밀번호가 다릅니다.</b-form-invalid-feedback>
        </b-col>
      </b-row>
      
      <b-row class="my-1">
        <b-col sm="3"><!--길이-->
          <label for="input-userid">닉네임</label>
        </b-col>
        <b-col sm="9">
          <b-form-input 
            id="input-userid" 
            v-model="nickName"
            :state="NickNameState"
            aria-describedby="input-userid-help input-userid-feedback"
            placeholder=""
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback>
            닉네임을 입력하세요(최소2글자)
          </b-form-invalid-feedback>
        </b-col>

      </b-row>
    </b-container>
    <!--이미지 입력Form-->
    <!-- Plain mode -->
    <b-row>
      <b-col>
        프로필사진
      </b-col>
      <b-col sm="9">
        <b-form-file v-model="file2" class="mt-3" plain></b-form-file>
        <div class="mt-3">Selected file: {{ file2 ? file2.name : '' }}</div>
      </b-col>
    </b-row>
    
    
    <div>
      <b-button variant="danger">취소</b-button>
      <b-button variant="primary">확인</b-button>
    </div>
  </div>

  
</template>

<script>
  export default {
    data() {
      return {
        userid: '',
        password: '',
        passwordAgain:'',
        nickName:'',
        file:null,
        file2:null,
      }
    },
    computed: {
      useridState() {
        if(this.userid.length==0){
          return null
        }
        return this.userid.length > 2 ? true : false
      },
      NickNameState() {
        if(this.nickName.length==0){
          return null
        }
        return this.nickName.length > 1 ? true : false
      },
      passwordValid() {
        if(this.password.length==0){
          return null
        }
        const len = this.password.length
        if (len < 8) return false;
        var numFlag = false, alphaFlag = false
        for (var i = 0; i < len; i++) {
          const ch = this.password[i]
          if ('0' <= ch && ch <= '9') numFlag = true
          else if ('a' <= ch && ch <= 'z') alphaFlag = true
        }
        return numFlag && alphaFlag
      },
      passwordAgainValid() {
        if(this.passwordAgain.length==0){
          return null
        }
        if(this.passwordAgain.length > 0 && this.password == this.passwordAgain) return true;
        return false;
      },
    },
    
    
  }
</script>

<style>

</style>