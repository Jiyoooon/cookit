<template>
  <div class="wrapper">
    <header id="app-header">
      <b-navbar toggleable="sm" type="light" variant="">
    <b-navbar-brand href="#">
        <img id="mainlogo" @click="mainLogoClick" src="./assets/logo.jpg" style="height: 5em; padding: 0px 2em">
    </b-navbar-brand>
    <nav class="nav-menu">
      <div id="myblog" @click="myBlogClick">내 블로그</div>
      <div id="browsing" @click="browsingClick">둘러보기</div>
    </nav>
    <nav class="nav-side">
      <div v-if="!isLoggedIn">
        <div class="nav-side-item btn-style1" id="signup" @click="joinClick">Join</div>
        <div class="nav-side-item btn-style2" id="login" @click="signinClick">Sign In</div>
      </div>
      <div v-else>
        <div class="nav-side-item btn-style1" id="userInfo" @click="myinfoClick">My Info</div>
        <div class="nav-side-item btn-style2" id="logout" @click="logoutClick">Log out</div>
      </div>
    </nav>
  </b-navbar>
  </header>
  <hr id="divider">
  <div id="app-router">
    <transition name="fade" mode="out-in" @after-leave="afterLeave" :key="$route.fullPath">
      <router-view></router-view>
    </transition>
  </div>
  <footer id="app-footer">
    <ul>
      <li><strong>Made by. </strong></li>
      <li>곽은정</li>
      <li>김지윤</li>
      <li>김태형</li>
      <li>이건수</li>
      <li>차보람</li>
      <div class="copyright">
        Copyright ©
        <a href="http://www.ssafy.com"><strong>Samsung SW Academy For Youth</strong></a>
        All Rights Reserved.
      </div>
    </ul>
  </footer>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapState, mapMutations } from 'vuex'
import $ from 'jquery'

export default {
    name: 'AppHeader',
    computed: {
        // ...mapState([ 'me' ]),
        ...mapGetters('accounts', ['isLoggedIn']),
        ...mapState('accounts',['authUser']),
    },
    methods: {
        afterLeave() {
          this.$root.$emit('triggerScroll')
        },
        removeActiveClass() {
          $("#myblog").removeClass("active");
          $("#browsing").removeClass("active");
        },
        mainLogoClick() {
          this.removeActiveClass();
          this.GoHome();
        },
        joinClick(){
          this.removeActiveClass();
          this.GoEmailAuth();
        },
        signinClick(){
          this.removeActiveClass();
          this.GoLogin();
        },
        myinfoClick(){
          this.removeActiveClass();
          this.GoPasswordAuth();
        },
        logoutClick(){
          this.removeActiveClass();
          this.GoLogout();
        },
        myBlogClick() {
          if(!this.isLoggedIn) {
            this.removeActiveClass();
            this.GoLogin();
          }
          else {
            $("#myblog").addClass("active");
            $("#browsing").removeClass("active");
            this.SET_USERINFO(this.authUser)
            this.setRecipequeryUserId(this.authUser.nickname)
            this.GoMyBlog();
          }
        },
        browsingClick() {
          this.removeActiveClass();
          $("#browsing").addClass("active");
          this.GoLookAroundRecipesView();
        },
        ...mapActions('accounts', ['GoLogin', 'GoSignup', 'GoHome', 'GoPasswordAuth', 'GoLogout', 'GoEmailAuth', 'logout']),
        ...mapActions('myblog',['GoMyBlog']),
        ...mapActions('lookaround', ['GoLookAroundRecipesView','getIngredients']),
        ...mapMutations('myblog',['SET_USERINFO']),
        ...mapMutations('lookaround',['setRecipequeryUserId']),
        gorecipeupdate() {
          console.log('nnnn')
          this.$router.push({ name: 'RecipeUpdateView'})
        }
    },
    created(){
      this.getIngredients()
    }
}
</script>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

.no-drag {
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity .3s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}

.wrapper {
  background-color: #FFC79A;
  margin: 0px;
}

#app-header {
  background-color: white;
  width: 100%;
  height: 100%;
}

#mainlogo {
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

.nav-side {
  position: absolute;
  top: 1em;
  right: 1em;
}

.nav-side-item {
  display: inline-block;
  justify-content: space-around;
  align-items: center;
  margin: 1em;
  font-size: 12px;
  cursor: pointer;
  padding: 5px 8px 5px 8px;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

.btn-style1 {
  background-color: #53AAA1;
  color: white;
}

.btn-style2 {
  border: 1px solid #53AAA1;
  color: #53AAA1;
}

.nav-menu {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.nav-menu #myblog, .nav-menu #browsing {
  color: #888888;
  font-size: 18px;
  text-decoration: none;
  cursor: pointer;
  margin-top: 40px;
  margin-left: 20px;
  margin-right: 20px;
  height: 1em;
  position: relative;
  padding: 6px 12px;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

@media (max-width: 506px) {
  .nav-menu {
    margin-left: auto;
    margin-right: auto;
  }
  .nav-menu #myblog, .nav-menu #browsing {
    margin-top: 0px;
    margin-bottom: 20px;
  }
}

.nav-menu #myblog:hover, .nav-menu #browsing:hover {
  color: #53AAA1;
}

.nav-menu #myblog::after, .nav-menu #browsing::after {
  content: "";
  position: absolute;
  bottom: -130%;
  left: 50%;
  transform: translateX(-50%);
  width: 0%;
  height: 5px;
  background: #53AAA1;
  transition: all .5s ease-out;
  z-index: 1;
}

.nav-menu #myblog:hover::after, .nav-menu #browsing:hover::after {
  width: 100%;
}

.nav-menu #myblog.active, .nav-menu #browsing.active {
  color: #53AAA1;
}

.nav-menu #myblog.active::after, .nav-menu #browsing.active::after {
  content: "";
  position: absolute;
  bottom: -130%;
  left: 50%;
  transform: translateX(-50%);
  width: 100%;
  height: 5px;
  background: #53AAA1;
}

#divider {
  background-color: #efefef;
}

#app-router {
  display: block;
  width: 90%;
  margin: 0px auto;
  background-color: white;
  min-height: 482px;
}

@media (max-width: 768px) {
  #app-router {
    display: block;
    width: 100%;
    margin: 0px auto;
    background-color: white;
  }
}

#app-footer {
  text-align: center;
  padding: 30px 50px;
  color: white;
  background-color: #24282C;
  position: relative;
  width: 100%;
  margin: 0 auto;
  font-size: 12px;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
} 

#app-footer li {
  position: relative;
  display: inline-block;
  padding: 2px 12px;
  white-space: nowrap;
  text-decoration: none;
  color: white;
}

#app-footer .copyright {
  padding: 25px 0 15px 0;
}

#app-footer a {
  text-decoration: none;
  color: white;
}

/* 버튼 */

.inline-block-btn {
  display: inline-block;
  justify-content: space-around;
  align-items: center;
  margin: 1em;
  cursor: pointer;
  padding: 6px 10px 6px 10px;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

.block-btn {
  display: block;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  cursor: pointer;
  padding: 6px 10px 6px 10px;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
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