import axios from 'axios'

const state = {
  email: '',
  password: 'password',
  test: 'My state!',
  location: 'location',
  token: localStorage.getItem('user-token') || '',
  status: ''
}
const getters = {
  isAthenticated: state => !!state.token,
  authStatus: state => state.status
}
var headers = {
  'Content-Type': 'application/json'
}

const actions = {
  addUser (context, { name, password }) {
    axios.post('http://localhost:8080/user', {
      username: name,
      password: password
    }, { headers: headers })
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
    }, { headers: headers })
      .then(data => {
        console.log(data.data)
        context.commit('SET_LOCATION', location)
      })
      .catch(error => {
        console.log(error.response)
      })
  },
  loginUser (context, { name, password }) {
    axios.post('http://localhost:8080/login', {
      username: name,
      password: password
    }, { headers: headers })
      .then(data => {
        console.log(data.data)
        context.commit('SET_USERINFO', name, password)
        console.log(data.headers)
      })
      .catch(error => {
        console.log(error.response)
      })
      //  context.commit('addUser', email, password)
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
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
