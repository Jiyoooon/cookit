import 'mutationobserver-shim'
import '@babel/polyfill'
import Vue from 'vue'
import './plugins/fontawesome'
import './plugins/axios'
import './plugins/bootstrap-vue'


import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueCookies from 'vue-cookies'
import viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
var infiniteScroll =  require('vue-infinite-scroll');
Vue.use(infiniteScroll)
Vue.use(VueCookies)
Vue.use(viewer)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  viewer,
  vuetify,
  render: h => h(App)
}).$mount('#app')
