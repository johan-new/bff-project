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
        console.log(data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  },
  fetchUserprofile (context) {
    axios.get('http://localhost:8080/loggedinuser')
      .then(data => {
        console.log(data.data)
        context.commit('userprofile', data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  }
}

const mutations = {
  userprofile (state, user) {
    state.user = user
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
