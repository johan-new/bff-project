import axios from 'axios'

const state = {
  queue: {},
  inQueue: false
}

const getters = {
  inQueue: state => !!state.inQueue,
  getQueue: state => state.queue
}

const actions = {
  submitMatchRequest (context, location) {
    location = location.location
    axios.post('http://localhost:8080/match', {
      location: location
    })
      .then(data => {
        console.log(data.data)
        context.commit('queue_status', true)
      })
      .catch(error => {
        console.log(error.response)
      })
  },
  matchingQueue (context) {
    axios.get('http://localhost:8080/match/queue')
      .then(data => {
        console.log(data.data)
        context.commit('matching_queue', data.data)
      //  context.commit('queue_status', true)
      })
      .catch(error => {
        console.log(error.response)
        context.commit('queue_status', false)
      })
  },
  cancelMatchRequest (context) {
    axios.delete('http://localhost:8080/match')
      .then(data => {
        console.log(data.data)
        context.commit('queue_status', false)
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
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
