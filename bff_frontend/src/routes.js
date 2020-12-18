import Home from './components/Home'
import Login from './components/Login'
import Register from './components/Register'
import Game from './components/Game.vue'
import Userprofile from './components/Userprofile.vue'
import Notifications from './components/Notifications.vue'

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
    name: 'Game',
    component: Game,
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
    meta: {
      requiresAuth: true
    }
  }
]
