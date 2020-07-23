<template>
  <div id="signform">
    <b-container fluid="lg">
      <b-row align-v="center">
        <b-col sm="3">
          <label for="input-userid">아이디<span style="color: red">*</span></label>
        </b-col>
        <!-- 아이디 대신 이메일~--> 
        <b-col sm="9">
          이메일
        </b-col>
      </b-row>

      <!-- 최대 12 -->
      <b-row align-v="center">
        <b-col sm="3">
          <label for="input-none">비밀번호<span style="color: red">*</span></label>
        </b-col>
        <b-col sm="9">
          <b-form-input type="password" id="password" aria-describedby="password-feedback" v-model="signupData.Password" :state="passwordValid" ></b-form-input>
          <b-form-invalid-feedback id="password-feedback">
          영문과 숫자를 포함해 8글자 이상으로 입력하세요. </b-form-invalid-feedback>
        </b-col>
      </b-row>

      <b-row align-v="center">
        <b-col sm="3"><!--길이-->
          <label for="input-none">비밀번호 재입력<span style="color: red">*</span></label>
        </b-col>
        <b-col sm="9">
          <b-form-input type="password" id="password" aria-describedby="password-again-feedback" v-model="signupData.PasswordAgain" :state="passwordAgainValid" ></b-form-input>
          <b-form-invalid-feedback id="password-again-feedback">
          비밀번호가 다릅니다.</b-form-invalid-feedback>
        </b-col>
      </b-row>
      
      <!--중복 불가능, 3글자 이상, 특수문자 x 최대 7글자 , -->
      <b-row align-v="center">
        <b-col sm="3">
          <label for="input-userid">닉네임<span style="color: red">*</span></label>
        </b-col>
        <b-col sm="9">
          <b-form-input 
            id="input-userid" 
            v-model="signupData.NickName"
            :state="NickNameinValid"
            aria-describedby="input-userid-help input-userid-feedback"
            toLowerCase
            >
          </b-form-input>
          <b-form-invalid-feedback>
            닉네임이 올바르지 않습니다.(4~12글자 한글은 최대 6글자)
          </b-form-invalid-feedback>
        </b-col>
      </b-row>


      <b-row align-v="center">
        <b-col>
          프로필사진
        </b-col>
        <b-col sm="9">
          <b-form-file v-model="signupData.File2" class="mt-3" plain></b-form-file>
          <div class="mt-3">Selected file: {{ signupData.File2 ? signupData.File2.name : '' }}</div>
        </b-col>
      </b-row>
      <b-row align-v="center">
        <b-col sm="3">소개글</b-col>
        <b-col sm="9">
            <b-form-textarea
            placeholder="소개글을 적어주세요"
            id="textarea" rows="3" max-rows="6">
            </b-form-textarea>
        </b-col>
      </b-row>
        <b-row align-v="center" align-h="center">
            <b-col sm="2"></b-col>
            <b-col sm="4">
                <b-button type="submit" variant="primary" id="signButton" to="/SignupForm" block>확인</b-button>
            </b-col>
            <b-col sm="4">
                <b-button variant="danger" id="cancelButton" to="/SignupView" block>취소</b-button>
            </b-col>
            <b-col sm="2"></b-col>
        </b-row>
      
    </b-container>
    <!--이미지 입력Form-->
    <!-- Plain mode -->
    
  </div>

  
</template>

<script>
  export default {
    name:'signup',
    data() {
      return {
        signupData:{
          Id: '',
          Password: '',
          PasswordAgain:'',
          NickName:'',
          File:null,
          File2:null,
        }
      }
    },
    computed: {
      //패스워드 유효성
      //3~12 영어와 숫자만가능
      passwordValid() {
        const len = this.signupData.Password.length
        if(len==0){
          return null
        }
        if (len < 8 || len >12) return false;
        var numFlag = false, alphaFlag = false , otherFlag =true
        for (var i = 0; i < len; i++) {
          const ch = this.signupData.Password[i]
          if ('0' <= ch && ch <= '9') numFlag = true
          else if ('a' <= ch && ch <= 'z') {
            alphaFlag = true
          }
          else otherFlag = false
        }
        return numFlag && alphaFlag &&otherFlag
      },
      passwordAgainValid() {
        if(this.signupData.PasswordAgain.length==0){
          return null
        }
        if(this.signupData.PasswordAgain.length > 0 && this.signupData.Password == this.signupData.PasswordAgain) return true;
        return false;
      },
      NickNameinValid(){
        const len = this.signupData.NickName.length
        if(len==0){
          return null
        }
        var NickNamelen = 0
        var nameflag = true
        for (var i = 0; i < len; i++) {
          const ch = this.signupData.NickName[i]
          console.log("ch: " + ch)
          if(('0' <= ch && ch <= '9') || ('a' <= ch && ch <= 'z')||('A' <= ch && ch <= 'Z')){
            NickNamelen++
          }
          else if(('가' <= ch && ch <= '힣')){
            NickNamelen+=2
          }
          else{
            nameflag = false
          }
        }
        if(NickNamelen <4 || NickNamelen >12){
          return false
        }
        return nameflag
      },
    },
  }
</script>

<style>

</style>