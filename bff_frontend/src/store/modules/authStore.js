import axios from 'axios'

const state = {
  email: '',
  password: 'password',
  location: '',
  token: localStorage.getItem('token') || '',
  status: ''
}
const getters = {
  isLoggedIn: state => !!state.token,
  authStatus: state => state.status,
  loggedInUser: state => state.email
}

const actions = {
  addUser (context, { name, password }) {
    axios.post('http://localhost:8080/user', {
      username: name,
      password: password
    })
      .catch(error => {
        console.log(error.response)
      })
  },
  login (context, user) {
    return new Promise((resolve, reject) => {
      context.commit('auth_request')
      axios.post('http://localhost:8080/login', {
        username: user.name,
        password: user.password
      })
        .then(resp => {
          const token = resp.headers.authorization
          localStorage.setItem('token', token)
          axios.defaults.headers.common.Authorization = token
          context.commit('auth_success', token, user)
          context.commit('SET_USERINFO', user)
          resolve(resp)
        })
        .catch(err => {
          context.commit('authStore/auth_error')
          localStorage.removeItem('token')
          reject(err)
        })
    })
  },
  logout (context) {
    return new Promise((resolve, reject) => {
      context.commit('logout')
      localStorage.removeItem('token')
      delete axios.defaults.headers.common.Authorization
      context.commit('userStore/delete_notification', null, { root: true })
      resolve()
    })
  }
}
const mutations = {
  SET_LOCATION (state, payload) {
    state.location = payload
  },
  SET_USERINFO (state, user) {
    state.email = user.name
  },
  auth_request (state) {
    state.status = 'loading'
  },
  auth_success (state, token) {
    state.status = 'success'
    state.token = token
  },
  auth_error (state) {
    state.status = 'error'
  },
  logout (state) {
    state.status = ''
    state.token = ''
    state.email = ''
    state.password = ''
  }
}
export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
