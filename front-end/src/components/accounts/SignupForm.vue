<template>
  <div>
    <b-container fluid="lg">
      <b-row align-v="center">
        <b-col sm="3">아이디(이메일)<span style="color: red">*</span>
        </b-col>
        <!-- 아이디 대신 이메일~--> 
        <b-col sm="9">
          {{ userEmail }}
        </b-col>
      </b-row>

      <!-- 최대 12 -->
      <b-row>
        <b-col sm="3" class="mt-2">비밀번호<span style="color: red">*</span>
        </b-col>
        <b-col sm="9">
          <b-form-input type="password" aria-describedby="password-feedback" v-model="signupData.config.password" :state="passwordValid" ></b-form-input>
          <b-form-invalid-feedback id="password-feedback">
          영문과 숫자를 포함해 8글자 이상으로 입력하세요. </b-form-invalid-feedback>
        </b-col>
      </b-row>

      <b-row>
        <b-col sm="3" class="mt-2"><!--길이-->
          비밀번호 재입력<span style="color: red">*</span>
        </b-col>
        <b-col sm="9">
          <b-form-input type="password" aria-describedby="password-again-feedback" v-model="passwordAgain" :state="passwordAgainValid" ></b-form-input>
          <b-form-invalid-feedback id="password-again-feedback">
          비밀번호가 다릅니다.</b-form-invalid-feedback>
        </b-col>
      </b-row>
      
      <!--중복 불가능, 3글자 이상, 특수문자 x 최대 7글자 , -->
      <b-row>
        <b-col sm="3" class="mt-2">
          <label for="input-userid">닉네임<span style="color: red">*</span></label>
        </b-col>
        <b-col sm="6">
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
        <b-col sm="3">
          <div v-if="!NickNameinValid" class="block-btn btn-style3" block>중복 확인</div>
          <div v-else class="block-btn btn-style1" @click='nicknameCheck(updateData.config.nickname)'>중복 확인</div>
        </b-col>
      </b-row>

      <b-row align-v="center">
      <b-col sm="3">프로필 사진</b-col>
      <b-col sm="6">
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
            <b-col sm="2"></b-col>
            <b-col sm="4">
                <div class="block-btn btn-style1" @click="signup(signupData)" block>가입하기</div>
            </b-col>
            <b-col sm="4">
                <div class="block-btn btn-style2" @click="$router.go(-1)">취소</div>
            </b-col>
            <b-col sm="2"></b-col>
        </b-row>
    </b-container>
  </div>

  
</template>

<script>
import { mapState, mapActions } from 'vuex'
import SERVER from '../../api/url.js'
import axios from 'axios'
import cookies from 'vue-cookies'

  export default {
    name:'signup',
    data() {
      return {
        signupData: {
          valid: {
            password: false,
            nickname: false,
          },
          config: {
            email: cookies.get('user-email'),
            password: null,
            nickname: null,
            profile: null,
            image_name: '',
            intro: '',
          }
        },
        file:null,
        passwordAgain: null,
        imageURL: null,
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
      
        if (!this.signupData.config.password) {
          return null
        }
        
        if (!(alphapattern.test(this.signupData.config.password)&&numberpattern.test(this.signupData.config.password)&&availablepassword.test(this.signupData.config.password))) IsAvailable = false
        return IsAvailable
      },
      passwordAgainValid() {
        if(!this.passwordAgain) return null
        if(this.passwordAgain.length > 0 && this.signupData.config.password == this.passwordAgain) return true;
        return false;
      },
      NickNameinValid(){
        if(!this.signupData.config.nickname) return null
        const len = this.signupData.config.nickname.length
        var NickNamelen = 0
        var nameflag = true
        for (var i = 0; i < len; i++) {
          const ch = this.signupData.config.nickname[i]
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
        if(!this.signupData.config.intro) return null
        if(this.signupData.config.intro.length >100)
          commentState = false
        return commentState
      },
    },
    
    methods:{
      clearFiles() {
        this.$refs['file-input'].reset()
      },
      nicknameCheck(nickname) {
        axios.get(SERVER.ROUTES.accounts.checknickname + String(nickname))
        .then((res) => {
          if (res.data.result == 'success') {
            this.signupData.valid.nickname = true
            this.$bvModal.msgBoxOk('확인되었습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
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
            centered: true
            })
            this.signupData.valid.nickname = false
          }
        })
        .catch((err) => {
          console.log(err.response)
          alert('!!!')
        })
      },
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
    }
  }
</script>

<style>
</style>