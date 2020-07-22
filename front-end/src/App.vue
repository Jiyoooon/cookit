<template>
  <div id="app-header">
      <b-navbar toggleable="sm" type="light" variant="">
    <b-navbar-brand href="#">
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQDLFnxu6sy4oQgCw4yuNZeNq1p604iMXTq-Q&usqp=CAU" style="height: 5em">
    </b-navbar-brand>
    <b-navbar-nav>
      <b-nav-item href="#">내 블로그</b-nav-item>
      <b-nav-item href="#">둘러보기</b-nav-item>
    </b-navbar-nav>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <i v-if="!isActive">
          <span @click="toggle"><router-link to="/login">로그인</router-link></span> | <router-link to="emailAuth">회원가입</router-link>
        </i>
        <i v-else>
          <b-nav-item-dropdown right>
          <!-- Using 'button-content' slot -->
          <template v-slot:button-content>
             User
          </template>
          <b-dropdown-item><router-link to="/passwordAuth">회원정보</router-link></b-dropdown-item>
          <b-dropdown-item @click="toggle"><router-link to="/logout">로그아웃</router-link></b-dropdown-item>
        </b-nav-item-dropdown>
        </i>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
  <router-view></router-view>
  </div>
</template>

<script>
// import { mapState, mapGetters, mapActions } from 'vuex'
export default {
    name: 'AppHeader',
    data() {
        return {
            isActive: false
        }
    },
    // computed: {
    //     ...mapState([ 'me' ]),
    //     ...mapGetters([ 'isLoggedIn' ])
    // },
    methods: {
        toggle() {
            this.isActive = !this.isActive
            console.log(this.isActive)
        },
        // ...mapActions([ 'logout' ])
    }
}
</script>

<style scoped>
* {
    margin: 0px;
    padding: 0px;
}

#app-header {
    width: 100%;
    height: 100%;
}
</style>