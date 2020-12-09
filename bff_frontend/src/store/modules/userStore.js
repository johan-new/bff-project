import axios from 'axios'

const state = {
  user: '',
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
    username = username.user
    axios.get('http://localhost:8080/user', {
      params: { username }
    })
      .then(data => {
        context.commit('set_user', data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  }
}

const mutations = {
  set_user (state, user) {
    state.user = user
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
