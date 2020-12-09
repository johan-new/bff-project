import Home from './components/Home'
import Login from './components/Login'
import Register from './components/Register'
import Secure from './components/Secure.vue'
import Userprofile from './components/Userprofile.vue'

export const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  }, {
    path: '/login',
    name: 'Login',
    component: Login
  }, {
    path: '/register',
    name: 'Register',
    component: Register
  }, {
    path: '/secure',
    name: 'Secure',
    component: Secure,
    meta: {
      requiresAuth: true
    }
  }, {
    path: '/userprofile',
    name: 'userprofile',
    component: Userprofile,
    meta: {
      requiresAuth: true
    }
  }
]
