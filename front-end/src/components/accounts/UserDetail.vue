<template>
  <div id="userinfo">
    <b-container fluid="lg" id="userinfo-container">
    <b-row align-v="center">
      <b-col sm="3">아이디</b-col>
      <b-col sm="9">
        user.username
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">이메일</b-col>
      <b-col sm="9">
        user.email
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">비밀번호</b-col>
      <b-col sm="9">
        <b-form-input type="password" id="password" placeholder="비밀번호 변경 시 입력하세요." aria-describedby="password-feedback" v-model="password" :state="passwordValid"></b-form-input>
        <b-form-invalid-feedback id="password-feedback">
          영문자와 숫자를 포함해 8글자 이상으로 입력하세요. </b-form-invalid-feedback>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">비밀번호 재입력</b-col>
      <b-col sm="9">
        <b-form-input type="password" id="password" aria-describedby="password-again-feedback" v-model="passwordAgain" :state="passwordAgainValid"></b-form-input>
        <b-form-invalid-feedback id="password-again-feedback">
          비밀번호가 다릅니다.</b-form-invalid-feedback>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">닉네임</b-col>
      <b-col sm="9">
        user.nickname
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">프로필 사진</b-col>
      <b-col sm="9">
        <b-form-file v-model="profileImage" class="mt-3" accept="image/*" plain></b-form-file>
        <div class="mt-3">Selected file: {{ profileImage ? profileImage.name : '' }}</div>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">소개글</b-col>
      <b-col sm="9"><b-form-textarea
      id="textarea" rows="3" max-rows="6"></b-form-textarea>
      </b-col>
    </b-row>
    <b-row align-v="center">
      <b-col sm="3">메인화면 선택</b-col>
      <b-col sm="9">
        <b-form-radio-group v-model="mainSelected" name="radio-options-slots">
          <b-form-radio value="blog">내 블로그</b-form-radio>
          <b-form-radio value="browsing">둘러보기</b-form-radio>
        </b-form-radio-group>
      </b-col>
    </b-row>
    <b-row align-v="center" align-h="center">
      <b-col sm="2"></b-col>
      <b-col sm="4">
        <b-button type="submit" variant="primary" id="updateButton" to="/userUpdate" block>수정</b-button>
      </b-col>
      <b-col sm="4">
        <b-button variant="danger" id="deleteButton" to="/userDelete" block>탈퇴</b-button>
      </b-col>
      <b-col sm="2"></b-col>
    </b-row>
  </b-container>
  </div>
</template>

<script>
export default {
    name: 'UserDetail',
    data() {
      return {
        password: '',
        passwordAgain: '',
        profileImage: '',
        mainSelected: '',
      }
    },
    computed: {
      passwordValid() {
        const len = this.password.length
        if (len == 0) return null;
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
        const len = this.passwordAgain.length
        if (len == 0) return null;
        if(len > 0 && this.password == this.passwordAgain) return true;
        return false;
      }
    },
}
</script>

<style>
</style>