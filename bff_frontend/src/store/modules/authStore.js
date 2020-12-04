import axios from 'axios'

const state = {
  email: 'email',
  password: 'password',
  test: 'My state!'
}
const getters = {

}
var headers = {
  'Content-Type': 'application/json'
}

/*
DETTA NEDAN FUNKAR, MEN EJ ATT SKICKA SOM JSON, NGT ÄR FEL PÅ SERVERN!!!
*/

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
  }
}
const mutations = {
  addUser (state, payload) {
    state.email = payload.email
    state.password = payload.password
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
