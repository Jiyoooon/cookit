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
        <b-form-input type="password" placeholder="비밀번호 변경 시 입력하세요." aria-describedby="password-feedback" v-model="updateData.config.password" :state="passwordValid"></b-form-input>
        <b-form-invalid-feedback id="password-feedback">
          영문자와 숫자를 포함해 8글자 이상으로 입력하세요. </b-form-invalid-feedback>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">비밀번호 재입력</b-col>
      <b-col sm="9">
        <b-form-input type="password" aria-describedby="password-again-feedback" v-model="updateData.passwordAgain" :state="passwordAgainValid"></b-form-input>
        <b-form-invalid-feedback id="password-again-feedback">
          비밀번호가 다릅니다.</b-form-invalid-feedback>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="3" class="mt-2">닉네임</b-col>
      <b-col sm="6">
        <b-form-input 
            id="input-userid" 
            v-model="updateData.config.nickname"
            :state="NickNameinValid"
            aria-describedby="input-userid-help input-userid-feedback"
            toLowerCase
            @change="checkInitialNickname"
            >
          </b-form-input>
          <b-form-invalid-feedback>
            닉네임이 올바르지 않습니다.<br>(영문/숫자 4~12자, 한글 2~6자 & 특수문자 불가능)
          </b-form-invalid-feedback>
      </b-col>
      <b-col sm="3">
          <div v-if="!NickNameinValid || !IsNicknameChanged" class="block-btn btn-style3" block>중복 확인</div>
          <div v-else class="block-btn btn-style1" @click='nicknameCheck(updateData.config.nickname)'>중복 확인</div>
        </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">프로필 사진</b-col>
      <b-col sm="6">
        <b-form-file ref="file-input" v-model="updateData.config.profile" accept="image/*"
        :placeholder="updateData.config.image_name" @change="imageUpload"></b-form-file>
      </b-col>
			<b-col sm="3">
        <div class="block-btn btn-style1" @click="selectBasicImage">사진 삭제</div>
      </b-col>
    </b-row>
    <b-row v-if="imageURL"> 
      <b-col sm="3"></b-col>
      <b-col sm="9">
        <img :src="imageURL" height="100px">
      </b-col>
    </b-row>
    <b-row v-else> 
      <b-col sm="3"></b-col>
      <b-col sm="9">
        <img :src="imageURL2" height="100px">
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="3" class="mt-2">소개글</b-col>
      <b-col sm="9"><b-form-textarea
       v-model="updateData.config.intro" id="textarea" rows="3" max-rows="6"></b-form-textarea>
      </b-col>
    </b-row>
    <b-row v-for="(sns, index) in updateData.sns_list" :key="index">
      <b-col sm="3">{{ sns.sns_name }} : </b-col>
      <b-col sm="9">
        <b-form-input v-model="updateData.sns_list[index].sns_url" placeholder="https://www.example.com"></b-form-input>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">메인화면 선택</b-col>
      <b-col sm="9">
        <b-form-radio-group v-model="updateData.config.start_page" :options="options" name="radio-options-slots">
          <!-- <b-form-radio value="true">내 블로그</b-form-radio>
          <b-form-radio value="false">둘러보기</b-form-radio> -->
        </b-form-radio-group>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
    </b-row>
    <b-row align-v="center" align-h="center">
      <b-col sm="2"></b-col>
      <b-col sm="4">
        <div class="block-btn btn-style1" @click="updateUser(updateData)" block>수정</div>
      </b-col>
      <b-col sm="4">
        <div class="block-btn btn-style4" @click="DeleteUser" block>탈퇴</div>
      </b-col>
      <b-col sm="2"></b-col>
    </b-row>
  </b-container>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapState, mapMutations } from 'vuex'
import SERVER from '../../api/url.js'

export default {
    name: 'UserDetail',
    data() {
      return {
        updateData: {
          valid: {
            password: true,
            nickname: false,
            filesize: null,
          },
          config: {
            email: null,
            password: '',
            nickname: null,
            profile: null,
            image_name: '',
            intro: '',
            start_page: false,
          },
          sns_list: [
            { sns_name: 'facebook', sns_url: ''},
            { sns_name: 'instagram', sns_url: ''},
            { sns_name: 'twitter', sns_url: ''},
            { sns_name: 'youtube', sns_url: ''},
          ]
        },
        file:null,
        passwordAgain: null,
        options: [
          { text: '내 블로그', value: true },
          { text: '둘러보기', value: false },
        ],
        imageURL: null,
        imageURL2: 'http://i3a201.p.ssafy.io:8080/images/profile/default_image.png',
      }
    },
    computed: {
      ...mapState('accounts', ['authUser', 'updateTF']),
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
      IsNicknameChanged() {
        if (this.updateData.config.nickname == this.authUser.nickname) {
          return false
        } else {
          return true
        }
      },
    },
    methods: {
      ...mapActions('accounts', ['DeleteUser', 'updateUser']),
      ...mapMutations('accounts', ['SET_UPDATETF']),
      nicknameCheck(nickname) {
        axios.get(SERVER.ROUTES.accounts.checknickname + String(nickname))
        .then((res) => {
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
      checkPasswordValidValue() {
        if (!this.updateData.config.password && !this.passwordAgain) {
          this.updateData.valid.password = true
        } else if(this.passwordAgainValid) {
          this.updateData.valid.password = true
        } else {
          this.updateData.valid.password = false
        }
      },
      insertInitialValue() {
        this.updateData.config.nickname = this.authUser.nickname
        this.updateData.config.intro = this.authUser.intro
        this.updateData.config.start_page = this.authUser.start_page
        this.updateData.config.profile = this.authUser.profile
        this.updateData.config.image_name = this.authUser.image_name
        this.updateData.config.email = this.authUser.email
        this.imageURL = this.authUser.image_url
        for (let i=0; i<4; i++) {
          this.updateData.sns_list[i].sns_url = this.authUser.sns_list[i].sns_url
        }
      },
      checkInitialNickname() {
        if (this.authUser.nickname == this.updateData.config.nickname) {
          this.updateData.valid.nickname = true
        } else if (!this.NickNameinValid) {
          this.updateData.valid.nickname = false
        } else {
          this.updateData.valid.nickname = false
        }
      },
      imageUpload(event) {
        const image = event.target.files[0];
        this.updateData.config.image_name = image.name
        this.updateData.valid.filesize = image.size
        const reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = (event) => {
          this.imageURL = event.target.result
        }
      },
      selectBasicImage() {
        this.updateData.config.image_name = ''
        this.imageURL = ''
        this.updateData.valid.filesize = null
        this.updateData.config.profile = null
      },
    },
    updated() {
      this.checkPasswordValidValue()
      this.checkInitialNickname()
      // this.SET_UPDATETF(true)
      console.log(this.updateTF)
    },
    created() {
      this.insertInitialValue()
      this.checkInitialNickname()
    },
    watch: {
      updateData: {
        deep:true,
        handler() {
          if (!(this.updateData.config.intro == this.authUser.intro) || 
          !(this.updateData.config.nickname == this.authUser.nickname) ||
          !(this.updateData.config.profile == this.authUser.profile) ||
          !(this.updateData.config.image_name == this.authUser.image_name) ||
          !(this.updateData.config.start_page == this.authUser.start_page)
          )
            this.SET_UPDATETF(true)
            console.log('??????????')
        }
      }
    },
}
</script>

<style>
.custom-file-input:lang(en) ~ .custom-file-label::after {
  content: '파일찾기';
}
</style>