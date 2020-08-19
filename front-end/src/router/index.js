import Vue from 'vue'
import VueRouter from 'vue-router'
import SignupView from '../views/accounts/SignupView.vue'
import LoginView from '../views/accounts/LoginView.vue'
import LogoutView from '../views/accounts/LogoutView.vue'
import UserDeleteView from '../views/accounts/UserDeleteView.vue'
import UserInfoView from '../views/accounts/UserInfoView.vue'
import PasswordAuthView from '../views/accounts/PasswordAuthView.vue'
import PasswordFindView from '../views/accounts/PasswordFindView.vue'
import EmailAuthView from '../views/accounts/EmailAuthView.vue'
import RecipeCreateView from '@/views/myrecipes/RecipeCreateView.vue'
import RecipeUpdateView from '@/views/myrecipes/RecipeUpdateView.vue'
import Home from '../views/Home.vue'
import LookAroundRecipeView from '../views/lookaroundrecipe/LookAroundRecipeView.vue'
import MyBlogListView from '../views/myblog/MyBlogListView.vue'
import UserBlogListView from '../views/myblog/UserBlogListView.vue'
import RecipeDetailView from '../views/recipeview/RecipeDetailView.vue'
import store from '../store'
import $ from 'jquery'



Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { scrollToTop: true }
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignupView,
    meta: { scrollToTop: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: { scrollToTop: true }
  },
  {
    path: '/logout',
    name: 'Logout',
    component: LogoutView,
    meta: { scrollToTop: true }
  },
  {
    path: '/userDelete',
    name: 'UserDelete',
    component: UserDeleteView
  },
  {
    path: '/userInfo',
    name: 'UserInfoView',
    component: UserInfoView,
    meta: { scrollToTop: true }
  },
  {
    path: '/passwordAuth',
    name: 'PasswordAuthView',
    component: PasswordAuthView,
    meta: { scrollToTop: true }
  },
  {
    path: '/passwordFind',
    name: 'PasswordFindView',
    component: PasswordFindView,
    meta: { scrollToTop: true }
  },
  {
    path: '/emailAuth',
    name: 'EmailAuthView',
    component: EmailAuthView,
    meta: { scrollToTop: true }
  },
  {
    path: '/recipeCreate',
    name: 'RecipeCreateView',
    component: RecipeCreateView,
    meta: { scrollToTop: true }
  },
  {
    path: '/recipeUpdate/:recipe_id',
    name: 'RecipeUpdateView',
    component: RecipeUpdateView,
    meta: { scrollToTop: true }
  },
  {
    path: '/lookAroundRecipe',
    name: 'LookAroundRecipeView',
    component: LookAroundRecipeView
  },
  {
    path: '/myblog',
    name: 'MyBlogListView',
    component: MyBlogListView,
    meta: { scrollToTop: true }
  },
  {
    path: '/userblog/:user_id',
    name: 'UserBlogListView',
    component: UserBlogListView,
    meta: { scrollToTop: true}
  },
  {
    path: '/recipe/:recipe_id',
    name: 'SelectedRecipe',
    component: RecipeDetailView,
    meta: { 
      scrollToTop: true,
      metaTags: [
        {
          property: 'og:title',
          content: 'COOKIT!!!!!'
        },
        {
          property: 'og:description',
          content: '쿠킷 레시피 공유하기'
        },
        {
          property: 'og:image',
          content: 'http://i3a201.p.ssafy.io:8080/images/logo/logo.png'
        }
      ]
    }
  },
]

// scrollBehavior:
// - only available in html5 history mode
// - defaults to no scroll behavior
// - return false to prevent scroll
const scrollBehavior = function (to, from, savedPosition) {
  if (savedPosition) {
    // savedPosition is only available for popstate navigations.
    return savedPosition
  } else {
    const position = {}
    return new Promise(resolve => {
      // check if any matched route config has meta that requires scrolling to top
      if (to.matched.some(m => m.meta.scrollToTop)) {
        // coords will be used if no selector is provided,
        // or if the selector didn't match any element.
        position.x = 0
        position.y = 0
      }

      // wait for the out transition to complete (if necessary)
      this.app.$root.$once('triggerScroll', () => {
        // if the resolved position is falsy or an empty object,
        // will retain current scroll position.
        resolve(position)
      })
    })
  }
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior
})

router.beforeEach((to, from, next) => {
  const RequiredLoggedInPages = ['Logout', 'PasswordAuthView', 'UserInfoView', 'UserDelete', 'MyBlogListView'] 
  const RequiredLoggedOutPages = ['Login', 'Signup', 'EmailAuthView', 'PasswordFindView']
  const RequiredAuthorized = ['Signup']
  const RequiredPasswordAuth = ['UserInfoView']
  const RecipeUpdate = ['RecipeUpdateView', 'RecipeCreateView']
  const BeforeUpdated = ['Logout', 'MyBlogListView', 'Login', 'Signup', 'EmailAuthView', 'PasswordFindView', 
  'LookAroundRecipeView', 'Home', 'RecipeCreateView', 'RecipeUpdateView', 'UserBlogListView', 'SelectedRecipe',]

  const IsLoggedIn = Vue.$cookies.isKey('auth-token')
  const IsAuthorized = Vue.$cookies.isKey('user-email')
  const IsPasswordAuth = Vue.$cookies.isKey('password-check')

  const LoggedInRequired = RequiredLoggedInPages.includes(to.name)
  const LoggedOutRequired = RequiredLoggedOutPages.includes(to.name)
  const AuthorizedRequired = RequiredAuthorized.includes(to.name)
  const AuthPasswordRequired = RequiredPasswordAuth.includes(to.name)

  const FromUserInfo = RequiredPasswordAuth.includes(from.name)
  const FromUserInfoTo = BeforeUpdated.includes(to.name)
  const FromRecipeUpdate = RecipeUpdate.includes(from.name)


  // if (!IsLoggedIn && LoggedInRequired) {
  //   next({ name: 'Login' })
  // } else {
  //   next()
  // }

  // if (IsLoggedIn && LoggedOutRequired) {
  //   next({ name: 'Home' })
  // } else {
  //   next()
  // }

  // if (!IsAuthorized && AuthorizedRequired) {
  //   next({ name: 'EmailAuthView' })
  // } else {
  //   next()
  // }

  // if (!IsPasswordAuth && AuthPasswordRequired) {
  //   next({ name: 'PasswordAuthView' })
  // } else {
  //   next()
  // }

  // if (FromUserInfo && FromUserInfoTo) {
  //   store._vm.$root.$bvModal.msgBoxConfirm('수정한 내용이 저장되지 않습니다.', {
  //     title: '정말로 나가시겠습니까?',
  //     size: 'md',
  //     buttonSize: 'sm',
  //     okVariant: 'danger',
  //     okTitle: 'YES',
  //     cancelTitle: 'NO',
  //     footerClass: 'p-2',
  //     hideHeaderClose: false,
  //     centered: true
  //   })
  //     .then((ans) => {
  //       if (ans) {
  //         next() 
  //       } else {
  //         next(false)
  //       }
  //     }) 
  //   }    
  
  if (!(from.name == 'SelectedRecipe') && (to.name == 'LookAroundRecipeView')) {
    store.commit('lookaround/initializing')
    next()
    // console.log(store.state)
  // } else if ((from.name == 'SelectedRecipe') && (to.name == 'LookAroundRecipeView') && (store.state.accounts.authUser.user_id == store.state.recipes.recipeUser.user_id)) {
  //   store.commit('lookaround/initializing')
  //   next()
  } else {
    next()
  }

  if (!IsLoggedIn && LoggedInRequired) {
    next({ name: 'Login' })
  } else if (IsLoggedIn && LoggedOutRequired) {
    next({name: 'Home'})
  } else if (!IsAuthorized && AuthorizedRequired) {
    next({ name: 'EmailAuthView' })
  } else if (!IsPasswordAuth && AuthPasswordRequired) {
    next({ name: 'PasswordAuthView' })
  } else if (FromUserInfo && FromUserInfoTo && store.state.accounts.updateTF) {
      window.addEventListener('keypress', function(event) {
        if (event.keyCode == 13) store._vm.$root.$bvModal.hide('modal')
      })
    store._vm.$root.$bvModal.msgBoxConfirm('수정된 내용이 저장되지 않습니다.', {
      title: '정말로 나가시겠습니까?',
      size: 'md',
      buttonSize: 'sm',
      okVariant: 'danger',
      okTitle: 'YES',
      cancelTitle: 'NO',
      footerClass: 'p-2',
      hideHeaderClose: false,
      centered: true,
      id: 'modal'
    })
      .then((ans) => {
        if (ans) {
          next()
          store.commit('editor/SET_UPDATETF', false)
        } else {
          next(false)
        }
      }) 
  } else if (FromRecipeUpdate && FromUserInfoTo && store.state.editor.updateTF) {
      window.addEventListener('keypress', function(event) {
        if (event.keyCode == 13) store._vm.$root.$bvModal.hide('modal')
      })
    store._vm.$root.$bvModal.msgBoxConfirm('작성한 내용이 저장되지 않습니다.', {
      title: '정말로 나가시겠습니까?',
      size: 'md',
      buttonSize: 'sm',
      okVariant: 'danger',
      okTitle: 'YES',
      cancelTitle: 'NO',
      footerClass: 'p-2',
      hideHeaderClose: false,
      centered: true,
      id: 'modal'
    })
      .then((ans) => {
        if (ans) {
          next()
          store.commit('editor/SET_UPDATETF', false)
          // router.go(-1);
        } else {
          next(false)
        }
      }) 
    } else {
      next()
    } 


    // 상단바 클래스 지정
    if (to.name == 'MyBlogListView') {
      $("#myblog").addClass("active");
      $("#browsing").removeClass("active");
    }
    else next();

    if (to.name == 'LookAroundRecipeView') {
      $("#myblog").removeClass("active");
      $("#browsing").addClass("active");
    }
    else next();

    if (to.name == 'logout') {
      $("#myblog").removeClass("active");
      $("#browsing").addClass("active");
    }
    else next();

    const setDefaultClass = ['Home', 'Signup', 'Login', 'PasswordAuthView', 'EmailAuthView' ]
    if (setDefaultClass.includes(to.name)) {
      $("#myblog").removeClass("active");
      $("#browsing").removeClass("active");
    }
    else next();
})

export default router
