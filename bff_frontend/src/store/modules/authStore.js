import axios from 'axios'

const state = {
  email: '',
  password: 'password',
  test: 'My state!',
  location: 'location'
}
const getters = {

}
var headers = {
  'Content-Type': 'application/json'
}

const actions = {
  addUser (context, { name, password }) {
    axios.post('http://localhost:8080/user', {
      userName: name,
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
  }
}
const mutations = {
  SET_LOCATION (state, payload) {
    state.location = payload
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
