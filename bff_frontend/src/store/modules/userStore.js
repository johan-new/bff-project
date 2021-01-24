import axios from 'axios'

const state = {
  user: '',
  friendlist: {},
  gamelist: {},
  statuscode: '',
  notifications: [],
  myFriends: []
}

const getters = {
  user: state => state.user,
  friendlist: state => state.friendlist,
  gamelist: state => state.gamelist,
  getNotifications: state => state.notifications,
  getMyFriends: state => state.myFriends
}

const actions = {
  fetchUser (context, username) {
    axios.get('http://localhost:8080/user', {
      params: { username }
    })
      .then(data => {
        context.commit('userprofile', data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  },
  fetchUserprofile (context) {
    axios.get('http://localhost:8080/loggedinuser')
      .then(data => {
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
        if (data.data !== '') {
          context.commit('set_notifications', data.data)
        }
      })
      .catch(error => {
        console.log(error)
      })
  },
  updateProfile (commit, payload) {
    axios.put('http://localhost:8080/user', {
      presentation: payload.presentation,
      age: payload.age,
      gender: payload.gender,
      city: payload.city
    })
      .then(data => {
        commit('userprofile', data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  },
  myFriends (context) {
    axios.get('http://localhost:8080/friend/all')
      .then(data => {
        context.commit('my_friends', data.data)
      })
      .catch(error => {
        console.log(error.response)
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
    state.notifications.push(data)
  },
  delete_notification (state) {
    state.notifications = []
  },
  my_friends (state, data) {
    state.myFriends = data
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
