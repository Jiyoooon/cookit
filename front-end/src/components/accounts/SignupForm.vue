<template>
  <div id="signform">
    <b-container fluid="lg">
      <b-row align-v="center">
        <b-col sm="3">
          <label for="input-userid">아이디<span style="color: red">*</span></label>
        </b-col>
        <b-col sm="9">
          <!-- <b-form-input 
            id="input-userid" 
            v-model="userid"
            :state="useridState"
            aria-describedby="input-userid-help input-userid-feedback"
            placeholder="아이디 입력 (최소 8글자, 영어만 사용가능)"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback id="userid-feedback" >
            아이디가 올바르지 않습니다.(최소 8글자, 영어만 사용가능)
          </b-form-invalid-feedback> -->
          {{ userEmail }}
        </b-col>
      </b-row>

      <b-row align-v="center">
        <b-col sm="3">
          <label for="input-none">비밀번호<span style="color: red">*</span></label>
        </b-col>
        <b-col sm="9">
          <b-form-input type="password" id="password" aria-describedby="password-feedback" v-model="signupData.password" :state="passwordValid" placeholder="비밀번호 (최소 8글자,영문과 숫자를 포함)"></b-form-input>
          <b-form-invalid-feedback id="password-feedback">
          영문자와 숫자를 포함해 8글자 이상으로 입력하세요. </b-form-invalid-feedback>
        </b-col>
      </b-row>

      <b-row align-v="center">
        <b-col sm="3"><!--길이-->
          <label for="input-none">비밀번호 재입력<span style="color: red">*</span></label>
        </b-col>
        <b-col sm="9">
          <b-form-input type="password" id="password" aria-describedby="password-again-feedback" v-model="passwordAgain" :state="passwordAgainValid" placeholder="비밀번호 확인"></b-form-input>
          <b-form-invalid-feedback id="password-again-feedback">
          비밀번호가 다릅니다.</b-form-invalid-feedback>
        </b-col>
      </b-row>
      
      <b-row align-v="center">
        <b-col sm="3">
          <label for="input-userid">닉네임<span style="color: red">*</span></label>
        </b-col>
        <b-col sm="8">
          <b-form-input 
            id="input-userid" 
            v-model="signupData.nickname"
            :state="NickNameState"
            aria-describedby="input-userid-help input-userid-feedback"
            placeholder="사용할 별명을 적어주세요 (최소2글자, 특수문자, 공백 사용불가)"
            trim
            >
          </b-form-input>
          <b-form-invalid-feedback>
            닉네임이 올바르지 않습니다.(최소2글자, 특수문자, 공백 사용불가)
          </b-form-invalid-feedback>
        </b-col>
        <b-col sm="1">
          <b-button v-if="!NickNameState" disabled variant="primary" block>중복확인</b-button>
          <b-button v-else variant="primary" @click='nicknameCheck(signupData.nickname)'>중복확인</b-button>
        </b-col>
      </b-row>


      <b-row align-v="center">
        <b-col>
          프로필사진
        </b-col>
        <b-col sm="9">
          <b-form-file v-model="signupData.profile_image" class="mt-3" plain></b-form-file>
          <div class="mt-3">Selected file: {{ signupData.profile_image ? signupData.profile_image.name : '' }}</div>
        </b-col>
      </b-row>
      <b-row align-v="center">
        <b-col sm="3">소개글</b-col>
        <b-col sm="9">
            <b-form-textarea
            placeholder="소개글을 적어주세요"
            id="textarea" rows="3" max-rows="6"
            v-model="signupData.intro">
            </b-form-textarea>
        </b-col>
      </b-row>
        <b-row align-v="center" align-h="center">
            <b-col sm="2"></b-col>
            <b-col sm="4">
                <b-button type="submit" variant="primary" id="signButton" @click="signup(signupData)" block>확인</b-button>
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
import { mapState, mapActions } from 'vuex'

  export default {
    data() {
      return {
        signupData: {
          email: null,
          password: null,
          nickname: null,
          profile_image: null,
          intro: null,
        },
        file:null,
        passwordAgain: null,
      }
    },
    computed: {
      ...mapState('accounts', ['userEmail']),
      // useridState() {
      //   if(this.userid.length==0){
      //     return null
      //   }
      //   return this.userid.length > 7 ? true : false
      // },
      NickNameState() {
        if(!this.signupData.nickName){
          return null
        }
        return this.signupData.nickName.length > 1 ? true : false
      },
      passwordValid() {
        if(!this.signupData.password){
          return null
        }
        const len = this.signupData.password.length
        if (len < 8) return false;
        var numFlag = false, alphaFlag = false
        for (var i = 0; i < len; i++) {
          const ch = this.signupData.password[i]
          if ('0' <= ch && ch <= '9') numFlag = true
          else if ('a' <= ch && ch <= 'z') alphaFlag = true
        }
        return numFlag && alphaFlag
      },
      passwordAgainValid() {
        if(!this.passwordAgain){
          return null
        }
        if(this.passwordAgain.length > 0 && this.signupData.password == this.passwordAgain) return true;
        return false;
      },
    },
    methods: {
      ...mapActions('accounts', ['signup']),
    }
    
    
  }
</script>

<style>

</style>