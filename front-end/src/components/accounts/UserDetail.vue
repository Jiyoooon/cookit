<template>
  <div id="userinfo">
    <b-container fluid="lg" id="userinfo-container">
    <!-- <b-row align-v="center">
      <b-col sm="3">아이디</b-col>
      <b-col sm="9">
        user.username
      </b-col>
    </b-row> -->
    <b-row align-v="center">
      <b-col sm="3">이메일</b-col>
      <b-col sm="9">
        {{ authUser.email }}
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3"><label for="input-none">비밀번호</label></b-col>
      <b-col sm="9">
        <b-form-input type="password" id="password" placeholder="비밀번호 변경 시 입력하세요." aria-describedby="password-feedback" v-model="updateData.config.password" :state="passwordValid"></b-form-input>
        <b-form-invalid-feedback id="password-feedback">
          영문자와 숫자를 포함해 8글자 이상으로 입력하세요. </b-form-invalid-feedback>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">비밀번호 재입력</b-col>
      <b-col sm="9">
        <b-form-input type="password" id="password" aria-describedby="password-again-feedback" v-model="updateData.passwordAgain" :state="passwordAgainValid"></b-form-input>
        <b-form-invalid-feedback id="password-again-feedback">
          비밀번호가 다릅니다.</b-form-invalid-feedback>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">닉네임</b-col>
      <b-col sm="8">
        <b-form-input 
            id="input-userid" 
            v-model="updateData.config.nickname"
            :state="NickNameinValid"
            aria-describedby="input-userid-help input-userid-feedback"
            toLowerCase
            >
          </b-form-input>
          <b-form-invalid-feedback>
            닉네임이 올바르지 않습니다.(4~12글자 한글은 최대 6글자)
          </b-form-invalid-feedback>
      </b-col>
      <b-col sm="1">
          <b-button v-if="!NickNameinValid" disabled variant="primary" block>중복확인</b-button>
          <b-button v-else variant="primary" @click='nicknameCheck(updateData.config.nickname)'>중복확인</b-button>
        </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">프로필 사진</b-col>
      <b-col sm="9">
        <b-form-file v-model="updateData.config.profile_image" class="mt-3" accept="image/*" plain></b-form-file>
        <div class="mt-3">Selected file: {{ updateData.config.profile_image ? updateData.config.profile_image : '' }}</div>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">소개글</b-col>
      <b-col sm="9"><b-form-textarea
       v-model="updateData.config.intro" id="textarea" rows="3" max-rows="6"></b-form-textarea>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">메인화면 선택</b-col>
      <b-col sm="9">
        <b-form-radio-group v-model="updateData.config.start_page" name="radio-options-slots">
          <b-form-radio value="true">내 블로그</b-form-radio>
          <b-form-radio value="false">둘러보기</b-form-radio>
        </b-form-radio-group>
      </b-col>
    </b-row>
    <b-row align-v="center" align-h="center">
      <b-col sm="2"></b-col>
      <b-col sm="4">
        <b-button type="submit" variant="primary" id="updateButton" @click="updateUser(updateData)" block>수정</b-button>
      </b-col>
      <b-col sm="4">
        <b-button variant="danger" id="deleteButton" @click="DeleteUser" block>탈퇴</b-button>
      </b-col>
      <b-col sm="2"></b-col>
    </b-row>
  </b-container>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapState } from 'vuex'
import SERVER from '../../api/url.js'

export default {
    name: 'UserDetail',
    data() {
      return {
        updateData: {
          valid: {
            password: true,
            nickname: true,
          },
          config: {
            email: null,
            password: null,
            nickname: null,
            profile_image: null,
            intro: null,
            start_page: false,
          }
        },
        file:null,
        passwordAgain: null,
      }
    },
    computed: {
      ...mapState('accounts', ['authUser']),
      passwordValid() {
        if (!this.updateData.config.password) return null;
        const len = this.updateData.config.password.length
        if (len < 8) return false;
        var numFlag = false, alphaFlag = false
        for (var i = 0; i < len; i++) {
          const ch = this.updateData.config.password[i]
          if ('0' <= ch && ch <= '9') numFlag = true
          else if ('a' <= ch && ch <= 'z') alphaFlag = true
        }
        return numFlag && alphaFlag
      },
      passwordAgainValid() {
        if (!this.updateData.passwordAgain) return null;
        const len = this.updateData.passwordAgain.length
      
        if(len > 0 && this.updateData.config.password == this.updateData.passwordAgain) return true;
        return false;
      },
      NickNameinValid(){
        if(!this.updateData.config.nickname) return null
        const len = this.updateData.config.nickname.length
        var NickNamelen = 0
        var nameflag = true
        for (var i = 0; i < len; i++) {
          const ch = this.updateData.config.nickname[i]
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
      CommentLimit(){
        var commentState = null
        if(!this.updateData.config.intro) return null
        if(this.updasteData.config.intro.length >100)
          commentState = false
        return commentState
      },
    },
    methods: {
      ...mapActions('accounts', ['DeleteUser', 'updateUser']),
      nicknameCheck(nickname) {
        axios.get(SERVER.ROUTES.accounts.checknickname + String(nickname))
        .then((res) => {
          console.log(res)
          if (res.data.result == 'success') {
            this.updateData.valid.nickname = true
            this.$bvModal.msgBoxOk('확인되었습니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'success',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
            })
          } else {
            this.updateData.valid.nickname = false
            this.$bvModal.msgBoxOk('이미 존재하는 닉네임 입니다.', {
            title: 'Confirmation',
            size: 'sm',
            buttonSize: 'sm',
            okVariant: 'danger',
            headerClass: 'p-2 border-bottom-0',
            footerClass: 'p-2 border-top-0',
            centered: true
            })
          }
        })
        .catch((err) => {
          console.log(err.response)
          alert('!!!')
        })
      },
    },
    update() {
      if(this.passwordAgainValid) {
        this.updateData.valid.password = true
      } else {
        this.updateData.valid.password = false
      }
    },
    created() {
      this.updateData.config.nickname = this.authUser.nickname
      this.updateData.config.intro = this.authUser.intro
      this.updateData.config.start_page = this.authUser.start_page
      this.updateData.config.profile_image = this.authUser.profile_image
    }
}
</script>

<style>
</style>