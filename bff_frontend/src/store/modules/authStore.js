import axios from 'axios'

const state = {
  email: '',
  password: 'password',
  test: 'My state!',
  location: 'location',
  token: localStorage.getItem('token') || '',
  status: '',
  user: {}
}
const getters = {
  isLoggedIn: state => !!state.token,
  authStatus: state => state.status
}

const actions = {
  addUser (context, { name, password }) {
    axios.post('http://localhost:8080/user', {
      username: name,
      password: password
    })
      .then(data => {
        console.log(data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  },
  matchUser (context, { location }) {
    axios.post('http://localhost:8080//match', {
      location: location
    })
      .then(data => {
        console.log(data.data)
        context.commit('SET_LOCATION', location)
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
          context.commit('auth_error')
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
    state.password = user.password
  },
  addUser (state, payload) {
    state.email = payload
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
  state,
  getters,
  actions,
  mutations
}
