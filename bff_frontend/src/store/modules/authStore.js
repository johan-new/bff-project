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
  isAthenticated: state => !!state.token,
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
      //  context.commit('addUser', email, password)
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
  login ({ commit }, user) {
    return new Promise((resolve, reject) => {
      commit('auth_request')
      console.log(user.name)
      //  axios({ url: 'http://localhost:8080/login', data: { payload }, method: 'POST' })
      axios.post('http://localhost:8080/login', {
        username: user.name,
        password: user.password
      })
        .then(resp => {
          console.log(resp.headers)
          const token = resp.headers.authorization
          const user = resp.data.user
          console.log(token)
          console.log(user)
          localStorage.setItem('token', token)
          axios.defaults.headers.common.Authorization = token
          commit('auth_success', token, user)
          resolve(resp)
        })
        .catch(err => {
          console.log('Nope!')
          commit('auth_error')
          localStorage.removeItem('token')
          reject(err)
        })
    })
  }
}
const mutations = {
  SET_LOCATION (state, payload) {
    state.location = payload
  },
  SET_USERINFO (state, name, password) {
    state.email = name
    state.password = password
  },
  addUser (state, payload) {
    state.email = payload
  },
  auth_request (state) {
    state.status = 'loading'
  },
  auth_success (state, token, user) {
    state.status = 'success'
    state.token = token
    state.user = user
  },
  auth_error (state) {
    state.status = 'error'
  },
  logout (state) {
    state.status = ''
    state.token = ''
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
