import 'mutationobserver-shim'
import '@babel/polyfill'
import Vue from 'vue'
import './plugins/axios'
import './plugins/bootstrap-vue'

import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueCookies from 'vue-cookies'
var infiniteScroll =  require('vue-infinite-scroll');
Vue.use(infiniteScroll)
Vue.use(VueCookies)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
