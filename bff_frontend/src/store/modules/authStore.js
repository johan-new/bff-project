import axios from 'axios'

const state = {
  email: 'email',
  password: 'password',
  test: 'My state!'
}
const getters = {

}
const proxyurl = 'https://cors-anywhere.herokuapp.com/'

const actions = {
  addUser (context, email, password) {
    axios.post(proxyurl + 'http://localhost:8080/user', {
      name: email,
      password: password
    })
      .then(data => {
        console.log(data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
      //  context.commit('addUser', email, password)
  }
}
const mutations = {
  addUser (state, email, password) {
    state.email = email
    state.password = password
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
