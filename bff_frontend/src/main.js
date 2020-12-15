import Vue from 'vue'
import App from './App.vue'
import store from './store/store'
import VueRouter from 'vue-router'
import { routes } from './routes'

import Axios from 'axios'

Vue.use(VueRouter)

Vue.config.productionTip = false

Vue.prototype.$http = Axios
const token = localStorage.getItem('token')
if (token) {
  Vue.prototype.$http.defaults.headers.common.Authorization = token
}

const router = new VueRouter({
  mode: 'history',
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (store.getters['authStore/isLoggedIn']) {
      next()
      return
    }
    next('/login')
  } else {
    next()
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
