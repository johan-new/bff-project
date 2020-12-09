import axios from 'axios'

const state = {
  username: '',
  friendlist: {},
  gamelist: {}
}

const getters = {
  username: state => state.username,
  friendlist: state => state.friendlist,
  gamelist: state => state.gamelist
}

const actions = {
  fetchUser (context, username) {
    axios.get('http://localhost:8080/user', {
      username: username
    })
      .then(data => {
        console.log(data.data)
        context.commit('set_user', data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  }
}

const mutations = {
  set_user (state, user) {
    state.username = user
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
