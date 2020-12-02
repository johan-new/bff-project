const state = {
  email: 'email',
  password: 'password',
  test: 'My state!'
}
const getters = {

}
const actions = {
  addUser (context, email, password) {
    context.commit('addUser', email, password)
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
