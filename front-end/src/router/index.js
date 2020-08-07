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
import RecipeDetailView from '../views/recipeview/RecipeDetailView.vue'
import store from '../store'




Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignupView
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/logout',
    name: 'Logout',
    component: LogoutView
  },
  {
    path: '/userDelete',
    name: 'UserDelete',
    component: UserDeleteView
  },
  {
    path: '/userInfo',
    name: 'UserInfoView',
    component: UserInfoView
  },
  {
    path: '/passwordAuth',
    name: 'PasswordAuthView',
    component: PasswordAuthView
  },
  {
    path: '/passwordFind',
    name: 'PasswordFindView',
    component: PasswordFindView,
  },
  {
    path: '/emailAuth',
    name: 'EmailAuthView',
    component: EmailAuthView
  },
  {
    path: '/recipeCreate',
    name: 'RecipeCreateView',
    component: RecipeCreateView
  },
  {
    path: '/recipeUpdate',
    name: 'RecipeUpdateView',
    component: RecipeUpdateView
  },
  {
    path: '/lookAroundRecipe',
    name: 'LookAroundRecipeView',
    component: LookAroundRecipeView
  },
  {
    path: '/myBlog',
    name: 'MyBlogListView',
    component: MyBlogListView
  },
  {
    path: '/recipe/:recipe_id',
    name: 'SelectedRecipe',
    component: RecipeDetailView
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const RequiredLoggedInPages = ['Logout', 'PasswordAuthView', 'UserInfoView', 'UserDelete', 'MyBlogListView'] 
  const RequiredLoggedOutPages = ['Login', 'Signup', 'EmailAuthView', 'PasswordFindView']
  const RequiredAuthorized = ['Signup']
  const RequiredPasswordAuth = ['UserInfoView']
  const BeforeUpdated = ['Logout', 'MyBlogListView', 'Login', 'Signup', 'EmailAuthView', 'PasswordFindView', 'LookAroundRecipeView']

  const IsLoggedIn = Vue.$cookies.isKey('auth-token')
  const IsAuthorized = Vue.$cookies.isKey('user-email')
  const IsPasswordAuth = Vue.$cookies.isKey('password-check')

  const LoggedInRequired = RequiredLoggedInPages.includes(to.name)
  const LoggedOutRequired = RequiredLoggedOutPages.includes(to.name)
  const AuthorizedRequired = RequiredAuthorized.includes(to.name)
  const AuthPasswordRequired = RequiredPasswordAuth.includes(to.name)

  const FromUserInfo = RequiredPasswordAuth.includes(from.name)
  const FromUserInfoTo = BeforeUpdated.includes(to.name)


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
  console.log(store.state.accounts.updateTF)
  if (!IsLoggedIn && LoggedInRequired) {
    next({ name: 'Login' })
  } else if (IsLoggedIn && LoggedOutRequired) {
    next({ name: 'LookAroundRecipeView' })
  } else if (!IsAuthorized && AuthorizedRequired) {
    next({ name: 'EmailAuthView' })
  } else if (!IsPasswordAuth && AuthPasswordRequired) {
    next({ name: 'PasswordAuthView' })
  } else if (FromUserInfo && FromUserInfoTo && store.state.accounts.updateTF) {
    store._vm.$root.$bvModal.msgBoxConfirm('수정한 내용이 저장되지 않습니다.', {
      title: '정말로 나가시겠습니까?',
      size: 'md',
      buttonSize: 'sm',
      okVariant: 'danger',
      okTitle: 'YES',
      cancelTitle: 'NO',
      footerClass: 'p-2',
      hideHeaderClose: false,
      centered: true
    })
      .then((ans) => {
        if (ans) {
          next() 
        } else {
          next(false)
        }
      }) 
    } else {
      next()
    }   

})

export default router
