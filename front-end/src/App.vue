<template>
  <div id="app-header">
      <b-navbar toggleable="sm" type="light" variant="">
    <b-navbar-brand href="#">
        <img @click="GoHome" src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQDLFnxu6sy4oQgCw4yuNZeNq1p604iMXTq-Q&usqp=CAU" style="height: 5em">
    </b-navbar-brand>
    <nav class="nav-menu">
      <nav-item id="myblog" @click="GoMyBlog">내 블로그</nav-item>
      <nav-item id="browsing" @click="GoLookAroundRecipesView">둘러보기</nav-item>
    </nav>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <i v-if="!isLoggedIn">
          <b-nav-item @click="GoLogin">로그인</b-nav-item>
          <b-nav-item @click="GoEmailAuth">회원가입</b-nav-item>
          <!-- <span @click="toggle"><router-link to="/login">로그인</router-link></span> | <router-link to="emailAuth">회원가입</router-link> -->
        </i>
        <i v-else>
          <!-- <b-nav-item-dropdown right> -->
          <!-- Using 'button-content' slot -->
          <!-- <template v-slot:button-content>
             User
          </template>
          <b-dropdown-item @click="GoUserInfo">회원정보</b-dropdown-item>
          <b-dropdown-item @click="toggle">로그아웃</b-dropdown-item>
        </b-nav-item-dropdown> -->
          <b-nav-item @click="GoPasswordAuth">회원정보</b-nav-item>
          <b-nav-item @click="GoLogout">로그아웃</b-nav-item>
        </i>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
  <hr style="margin-top: -8px">
  <div id="app-router">
    <router-view></router-view>
  </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import $ from 'jquery'
$(document).ready(function(){
  $('nav-item').click(function() {
    $("#myblog").removeClass("active");
    $("#browsing").removeClass("active");
    $(this).toggleClass("active");
  });
});

export default {
    name: 'AppHeader',
    data() {
        return {
            isActive: false
        }
    },
    computed: {
        // ...mapState([ 'me' ]),
        ...mapGetters('accounts', ['isLoggedIn'])
    },
    methods: {
        toggle() {
            this.isActive = !this.isActive
            console.log(this.isActive)
        },
        ...mapActions('accounts', ['GoLogin', 'GoSignup', 'GoHome', 'GoPasswordAuth', 'GoLogout', 'GoEmailAuth', 'GoMyBlog']),
        ...mapActions('lookaround', ['GoLookAroundRecipesView','getIngredients'])
    },
    created(){
      this.getIngredients()
    }
}
</script>

<style scoped>
* {
    margin: 5px;
    padding: 0px;
}

#app-header {
    width: 100%;
    height: 100%;
}

.nav-menu {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.nav-menu nav-item {
  color: #888888;
  font-size: 18px;
  text-decoration: none;
  cursor: pointer;
  margin-top: 70px;
  margin-left: 20px;
  margin-right: 20px;
  height: 1em;
  position: relative;
  padding: 6px 12px;
}

.nav-menu nav-item:hover {
  color: black;
}

.nav-menu nav-item::after {
  content: "";
  position: absolute;
  bottom: -100%;
  left: 50%;
  transform: translateX(-50%);
  width: 0%;
  height: 4px;
  background: #53AAA1;
  transition: all .5s ease-out;
}

.nav-menu nav-item:hover::after {
  width: 100%;
}

nav-item.active {
  color: black;
}

nav-item.active::after {
  content: "";
  position: absolute;
  bottom: -100%;
  left: 50%;
  transform: translateX(-50%);
  width: 100%;
  height: 4px;
  background: #53AAA1;
  transition: all .5s ease-out;
}

#app-router {
  display: block;
  width: 100%;
  margin: 3em auto;
}

</style>