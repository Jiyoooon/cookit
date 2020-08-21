<template>
  <div>
    <b-container fluid="lg">
      <b-row align-v="center">
        <b-col cols="4" sm="3">아이디<span style="color: red">*</span>
        </b-col>
        <!-- 아이디 대신 이메일~--> 
        <b-col cols="8" sm="9">
          {{ userEmail }}
        </b-col>
      </b-row>

      <!-- 최대 12 -->
      <b-row>
        <b-col cols="4" sm="3" class="mt-2">비밀번호<span style="color: red">*</span>
        </b-col>
        <b-col cols="8" sm="9">
          <b-form-input type="password" aria-describedby="password-feedback" v-model="signupData.config.password" :state="passwordValid" ></b-form-input>
          <b-form-invalid-feedback id="password-feedback">
          영문과 숫자를 포함해 8~12자로 입력하세요. </b-form-invalid-feedback>
        </b-col>
      </b-row>

      <b-row>
        <b-col cols="4" sm="3" class="mt-2 reinput"><!--길이-->
          비밀번호 재입력<span style="color: red">*</span>
        </b-col>
        <b-col cols="8" sm="9">
          <b-form-input type="password" aria-describedby="password-again-feedback" v-model="passwordAgain" :state="passwordAgainValid" ></b-form-input>
          <b-form-invalid-feedback id="password-again-feedback">
          비밀번호가 다릅니다.</b-form-invalid-feedback>
        </b-col>
      </b-row>
      
      <!--중복 불가능, 3글자 이상, 특수문자 x 최대 7글자 , -->
      <b-row>
        <b-col cols="4" sm="3" class="mt-2">
          <label for="input-userid">닉네임<span style="color: red">*</span></label>
        </b-col>
<<<<<<< HEAD
        <b-col sm="8">
=======
        <b-col cols="8" sm="6">
>>>>>>> develop
          <b-form-input 
            v-model="signupData.config.nickname"
            :state="NickNameinValid"
            aria-describedby="input-userid-feedback"
            toLowerCase
            >
          </b-form-input>
          <b-form-invalid-feedback>
            닉네임이 올바르지 않습니다.<br>(영문/숫자 4~12자, 한글 2~6자 & 특수문자 불가능)
          </b-form-invalid-feedback>
        </b-col>
<<<<<<< HEAD
        <b-col sm="1">
          <b-button v-if="!NickNameinValid" disabled variant="primary" block>중복확인</b-button>
          <b-button v-else variant="primary" @click='nicknameCheck(signupData.config.nickname)'>중복확인</b-button>
        </b-col>
      </b-row>


      <b-row align-v="center">
        <b-col>
          프로필사진
        </b-col>
        <b-col sm="9">
          <b-form-file ref = "file-input" v-model="signupData.config.profile_image" class="mt-3" accept=".jpg, .png, .jpeg" plain></b-form-file>
          
          <div class="mt-3"><b-button variant="primary" @click="clearFiles" plain>파일 제거</b-button> 선택된사진: {{ signupData.config.profile_image ?signupData.config.profile_image.name : '' }}</div>
=======
        <b-col sm="3">
          <div v-if="!NickNameinValid" class="block-btn btn-style3" block>중복 확인</div>
          <div v-else class="block-btn btn-style1" @click='nicknameCheck(signupData.config.nickname)'>중복 확인</div>
>>>>>>> develop
        </b-col>
      </b-row>

      <b-row align-v="center">
      <b-col cols="4" sm="3" style="padding-right: 0;">프로필 사진</b-col>
      <b-col cols="8" sm="6">
        <b-form-file ref="file-input" v-model="signupData.config.profile" accept="image/*"
        placeholder="사진 선택" @change="imageUpload"></b-form-file>
      </b-col>
			<b-col sm="3">
        <div class="block-btn btn-style1" @click="clearFiles">사진 삭제</div>
      </b-col>
    </b-row>
    <b-row v-if="signupData.config.profile"> 
      <b-col sm="3"></b-col>
      <b-col sm="9">
        <img :src="imageURL" height="100px">
      </b-col>
    </b-row>
      <b-row>
        <b-col sm="3" class="mt-2">소개글</b-col>
        <b-col sm="9">
            <b-form-textarea
            id="textarea" rows="3" max-rows="6" v-model="signupData.config.intro" :state="CommentLimit" aria-describedby="comment-len-feedback">
            
            </b-form-textarea>
            <b-form-invalid-feedback id="comment-len-feedback">
            최대 100자 까지 가능합니다.</b-form-invalid-feedback>
        </b-col>
      </b-row>
        <b-row><b-col> </b-col></b-row>
        <b-row align-v="center" align-h="center">
<<<<<<< HEAD
            <b-col sm="2"></b-col>
            <b-col sm="4">
                <b-button type="submit" variant="primary" id="signButton" @click="signup(signupData)" block>확인</b-button>
=======
            <b-col cols="1" sm="2"></b-col>
            <b-col cols="5" sm="4">
                <div class="block-btn btn-style1" @click="signup(signupData)" block>가입하기</div>
>>>>>>> develop
            </b-col>
            <b-col cols="5" sm="4">
                <div class="block-btn btn-style2" @click="$router.go(-1)">취소</div>
            </b-col>
            <b-col cols="1" sm="2"></b-col>
        </b-row>
    </b-container>
<<<<<<< HEAD
    <!--이미지 입력Form-->
    <!-- Plain mode -->
    
=======
>>>>>>> develop
  </div>

  
</template>

<script>
import { mapState, mapActions } from 'vuex'
import SERVER from '../../api/url.js'
import axios from 'axios'

  export default {
    name:'signup',
    data() {
      return {
        signupData: {
          valid: {
            password: this.passwordAgainValid,
            nickname: false,
          },
          config: {
            email: null,
            password: null,
            nickname: null,
<<<<<<< HEAD
            profile_image: null,
            intro: null,
=======
            profile: null,
            image_name: '',
            intro: '',
>>>>>>> develop
          }
        },
        file:null,
        passwordAgain: null,
      }
    },
    computed: {
      //패스워드 유효성
      //3~12 영어와 숫자만가능
      ...mapState('accounts', ['userEmail']),
      passwordValid() {
        var availablepassword = /^[0-9a-zA-Z~`!@#$%\\^&*()-]{8,12}$/
        var numberpattern = /[0-9]/
        var alphapattern = /[a-zA-Z]/
        var IsAvailable = true
        //console.log(this.signupData.Password)
        if (!this.signupData.password) {
          return null
        }
        
        if (!(alphapattern.test(this.signupData.password)&&numberpattern.test(this.signupData.password)&&availablepassword.test(this.signupData.password))) IsAvailable = false
        return IsAvailable
      },
      passwordAgainValid() {
        if(!this.passwordAgain) return null
<<<<<<< HEAD
        if(this.passwordAgain.length > 0 && this.signupData.password == this.passwordAgain) return true;
=======
        if(this.passwordAgain.length > 0 && this.signupData.config.password == this.passwordAgain) return true;
>>>>>>> develop
        return false;
      },
      NickNameinValid(){
        if(!this.signupData.nickname) return null
        const len = this.signupData.nickname.length
        var NickNamelen = 0
        var nameflag = true
        for (var i = 0; i < len; i++) {
<<<<<<< HEAD
          const ch = this.signupData.nickname[i]
          console.log("ch: " + ch)
=======
          const ch = this.signupData.config.nickname[i]
>>>>>>> develop
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
      CommentLimit(){
        var commentState = null
        if(!this.signupData.intro) return null
        if(this.signupData.intro.length >100)
          commentState = false
        return commentState
      },
    },
    
    methods:{
      clearFiles() {
        this.$refs['file-input'].reset()
      },
      nicknameCheck(nickname) {
        axios.get(SERVER.ROUTES.accounts.nicknameCheck + String(nickname))
        .then((res) => {
          let that = this
          window.addEventListener('keypress', function(event) {
              if (event.keyCode == 13) that.$bvModal.hide('modal')
            })
          if (res.data.result == 'success') {
            this.signupData.valid.nickname = true
<<<<<<< HEAD
=======
            this.$bvModal.msgBoxOk('확인되었습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true,
            id: 'modal'
            })
            this.signupData.valid.nickname = true
          } else {
            this.$bvModal.msgBoxOk('이미 존재하는 닉네임 입니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true,
            id: 'modal'
            })
            this.signupData.valid.nickname = false
>>>>>>> develop
          }
        })
        .catch((err) => {
          console.log(err.response)
          // alert('!!!')
        })
      },
<<<<<<< HEAD
      ...mapActions('accounts', ['signup']),
=======
      ...mapActions('accounts', ['signup', 'signup2']),
      checkPasswordValidValue() {
        if(this.passwordAgainValid) {
          this.signupData.valid.password = true
        } else {
          this.signupData.valid.password = false
        }
      },
      imageUpload(event) {
        const image = event.target.files[0];
        this.signupData.config.image_name = image.name
        this.imageURL = URL.createObjectURL(image)
      },
    },
    updated() {
      this.checkPasswordValidValue()
>>>>>>> develop
    }
  }
</script>

<style>
<<<<<<< HEAD

=======
@media (max-width: 496px) {
  .reinput {
    padding-right: 0;
    font-size: 0.92em;
    margin-top: 0 !important;
  }
}
>>>>>>> develop
</style>