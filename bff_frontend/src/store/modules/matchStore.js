import axios from 'axios'

const state = {
  queue: [],
  inQueue: false,
  previous_games: [],
  test: {}
}

const getters = {
  inQueue: state => !!state.inQueue,
  getQueue: state => state.queue,
  getLoggedInUser (state, getters, rootState, rootGetters) {
    return rootGetters.loggedInUser
  },
  previousGames: state => state.previous_games
}

const actions = {
  submitMatch (context, payload) {
    return new Promise((resolve, reject) => {
      axios.post('http://localhost:8080/match/submit', {
        location: payload.location,
        date: payload.date,
        time: payload.time,
        reservation: payload.reservation,
        venue: payload.venue,
        participants: payload.participants
      })
        .then(data => {
          context.commit('queue_status', true)
          resolve(data)
        })
        .catch(error => {
          reject(error)
          console.log(error.response.data)
        })
    })
  },
  matchingQueue ({ commit, getters, rootGetters }) {
    axios.get('http://localhost:8080/match/queue')
      .then(data => {
        commit('matching_queue', data.data)
        commit('queue_status', false)
        var item
        for (item in state.queue) {
          for (var i = 0; i < state.queue[item].length; i++) {
            if (state.queue[item][i].username === rootGetters['authStore/loggedInUser']) {
              commit('queue_status', true)
            }
          }
        }
      })
      .catch(error => {
        console.log(error)
      })
  },
  cancelMatch (context, payload) {
    return new Promise((resolve, reject) => {
      axios.delete('http://localhost:8080/match/cancel', {
        data: { location: payload }
      })
        .then(data => {
          context.commit('queue_status', false)
          resolve(data)
        })
        .catch(error => {
          console.log(error.response)
          reject(error)
        })
    })
  },
  previousGames (context) {
    axios.get('http://localhost:8080/user/previousgames')
      .then(data => {
        context.commit('previous_games', data.data)
      })
      .catch(error => {
        console.log(error)
      })
  },
  matchRequest (context, payload) {
    console.log(payload)
    axios.post('http://localhost:8080/match/request', {
      matchingRequestId: payload.matchingRequestId,
      joinRequestId: payload.joinRequestId,
      action: payload.action
    })
      .then(data => {
        context.commit('test', data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  },
  joinMatch (context, payload) {
    axios.post('http://localhost:8080/match/join', {
      requestId: payload
    })
      .then(data => {
        context.commit('test', data.data)
      })
      .catch(error => {
        console.log(error.response)
      })
  }
}
const mutations = {
  matching_queue (state, data) {
    state.queue = data
  },
  queue_status (state, data) {
    state.inQueue = data
  },
  previous_games (state, data) {
    state.previous_games = data
  },
  test (state, data) {
    state.test = data
  }
}
export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
