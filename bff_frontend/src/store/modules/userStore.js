import axios from 'axios'

const state = {
  user: '',
  friendlist: {},
  gamelist: {},
  statuscode: '',
  notifications: {}
}

const getters = {
  username: state => state.username,
  friendlist: state => state.friendlist,
  gamelist: state => state.gamelist,
  getNotifications: state => state.notifications
}

const actions = {
  fetchUser (context, username) {
    username = username.user
    axios.get('http://localhost:8080/user', {
      params: { username }
    })
      .then(data => {
        console.log(data.data)
        context.commit('userprofile', data.data)
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
    })
      .then(data => {
        console.log(data.status)
        context.commit('set_status', data.status)
      })
      .catch(error => {
        console.log(error.response)
      })
  },
  notifications (context) {
    axios.get('http://localhost:8080/notifications')
      .then(data => {
        context.commit('set_notifications', data.data)
        console.log(data.data)
      })
      .catch(error => {
        console.log(error)
      })
  }
}

const mutations = {
  userprofile (state, user) {
    state.user = user
  },
  set_status (state, status) {
    state.statuscode = status
  },
  set_notifications (state, data) {
    state.notifications = data
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
