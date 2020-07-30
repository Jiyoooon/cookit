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
import Home from '../views/Home.vue'
import LookAroundRecipeView from '../views/lookaroundrecipe/LookAroundRecipeView.vue'
import MyBlogListView from '../views/myblog/MyBlogListView.vue'
import RecipeDetailView from '../views/myblog/RecipeDetailView.vue'


Vue.use(VueRouter)

  const routes = [
  {
    path: '',
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
    path: '/myBlog/:recipe_id',
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

  const IsLoggedIn = Vue.$cookies.isKey('auth-token')
  const IsAuthorized = Vue.$cookies.isKey('user-email')
  const IsPasswordAuth = Vue.$cookies.isKey('password-check')

  const LoggedInRequired = RequiredLoggedInPages.includes(to.name)
  const LoggedOutRequired = RequiredLoggedOutPages.includes(to.name)
  const AuthorizedRequired = RequiredAuthorized.includes(to.name)
  const AuthPasswordRequired = RequiredPasswordAuth.includes(to.name)

  if (!IsLoggedIn && LoggedInRequired) {
    next({ name: 'Login' })
  } else {
    next()
  }

  if (IsLoggedIn && LoggedOutRequired) {
    next({ name: 'Home' })
  } else {
    next()
  }

  if (!IsAuthorized && AuthorizedRequired) {
    next({ name: 'EmailAuthView' })
  } else {
    next()
  }

  if (!IsPasswordAuth && AuthPasswordRequired) {
    next({ name: 'PasswordAuthView' })
  } else {
    next()
  }
})

export default router
