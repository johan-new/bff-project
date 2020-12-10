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
  },
  changePassword ({ context, state }, { oldPassword, newPassword }) {
    axios.put('http://localhost:8080/user', {
      username: state.user.username,
      oldPassword,
      newPassword
    }, {
      headers: {
        Authorization: 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFcmlrQGEuYSIsImV4cCI6MTYwODQzNDc1Nn0.nbklpynT0yeSYk4cUaHQjuz873WiRrav_yrwlnFH4hqXWNs0sq3YsX_tOze4FLzqfxYRQyuTldjrAllSMFmPcQ'
      }
    })
      .then(data => {
        console.log(data.data)
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
