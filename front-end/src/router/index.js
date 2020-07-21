import Vue from 'vue'
import VueRouter from 'vue-router'
import SignupView from '../views/accounts/SignupView.vue'
import LoginView from '../views/accounts/LoginView.vue'
import LogoutView from '../views/accounts/LogoutView.vue'
import UserDeleteView from '../views/accounts/UserDeleteView.vue'
import UserUpdateView from '../views/accounts/UserUpdateView.vue'
import UserInfoView from '../views/accounts/UserInfoView.vue'
import PasswordAuthView from '../views/accounts/PasswordAuthView.vue'

Vue.use(VueRouter)

  const routes = [
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
    path: '/userDelete/:userId',
    name: 'UserDelete',
    component: UserDeleteView
  },
  {
    path: '/userUpdate',
    name: 'UserUpdate',
    component: UserUpdateView
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
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
