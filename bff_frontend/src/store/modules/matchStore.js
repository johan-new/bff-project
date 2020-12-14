import axios from 'axios'

const state = {
  queue: [],
  inQueue: false
}

const getters = {
  inQueue: state => !!state.inQueue,
  getQueue: state => state.queue
}

const actions = {
  submitMatchRequest (context, location) {
    return new Promise((resolve, reject) => {
      location = location.location
      axios.post('http://localhost:8080/match', {
        location: location
      })
        .then(data => {
          console.log(data)
          context.commit('queue_status', true)
          resolve(data)
        })
        .catch(error => {
          console.log(error.response)
          reject(error)
        })
    })
  },
  matchingQueue (context) {
    axios.get('http://localhost:8080/match/queue')
      .then(data => {
        console.log(data.data)
        context.commit('matching_queue', data.data)
      })
      .catch(error => {
        console.log(error.response)
        context.commit('queue_status', false)
      })
  },
  cancelMatchRequest (context) {
    return new Promise((resolve, reject) => {
      axios.delete('http://localhost:8080/match')
        .then(data => {
          console.log(data.data)
          context.commit('queue_status', false)
          resolve(data)
        })
        .catch(error => {
          console.log(error.response)
          reject(error)
        })
    })
  }
}
const mutations = {
  matching_queue (state, data) {
    state.queue = data
  },
  queue_status (state, data) {
    state.inQueue = data
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
