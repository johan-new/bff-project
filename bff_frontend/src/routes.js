import Home from './components/Home'
import Login from './components/Login'
import Register from './components/Register'
import Padel from './components/Padel.vue'
import Userprofile from './components/Userprofile.vue'
import Notifications from './components/Notifications.vue'
import Games from './components/Games.vue'

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
    path: '/padel',
    name: 'Padel',
    component: Padel,
    meta: {
      requiresAuth: true
    }
  }, {
    path: '/games',
    name: 'Games',
    component: Games,
    meta: {
      requiresAuth: true
    }
  }, {
    path: '/notifications',
    name: 'Notificatons',
    component: Notifications,
    meta: {
      requiresAuth: true
    }
  }, {
    //  path: '/userprofile',
    path: '/:username',
    name: 'Userprofile',
    component: Userprofile,
    props: true,
    meta: {
      requiresAuth: true
    }
  }
]
