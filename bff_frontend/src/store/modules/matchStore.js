import axios from 'axios'

const state = {
  queue: {
    Göteborg: [
      {
        username: 'e@e.e',
        date: '2020-12-24',
        time: '18:30',
        reservation: false,
        venue: 'GLTK',
        participants: 3
      },
      {
        username: 's@s.s',
        date: '2020-12-24',
        time: '16:30',
        reservation: false,
        venue: 'MBB',
        participants: 2
      },
      {
        username: 'b@b.b',
        date: '2020-12-31',
        time: '23:30',
        reservation: true,
        venue: 'Hallen',
        participants: 1
      }
    ],
    Stockholm: [
      {
        username: 'asda@as.s',
        date: '2020-12-22',
        time: '13:00',
        reservation: false,
        venue: 'Tråkhallen',
        participants: 3
      },
      {
        username: 'dtf@b.b',
        date: '2020-12-25',
        time: '15:30',
        reservation: true,
        venue: 'DTF-hallen',
        participants: 2
      },
      {
        username: 'gote@b.b',
        date: '2020-12-21',
        time: '22:30',
        reservation: false,
        venue: 'Logan',
        participants: 1
      }
    ],
    Uppsala: [
      {
        username: 'hejhej@b.b',
        date: '2020-12-30',
        time: '21:30',
        reservation: false,
        venue: 'Logan logansson-hallen',
        participants: 3
      },
      {
        username: 'logan@logansson.b',
        date: '2020-12-19',
        time: '25:30',
        reservation: false,
        venue: 'Hallen',
        participants: 1
      }
    ]
  },
  inQueue: false
}

const getters = {
  inQueue: state => !!state.inQueue,
  getQueue: state => state.queue,
  getLoggedInUser (state, getters, rootState, rootGetters) {
    return rootGetters.loggedInUser
  }
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
  matchingQueue ({ commit, getters, rootGetters }) {
    axios.get('http://localhost:8080/match/queue')
      .then(data => {
        commit('matching_queue', data.data)
        var x
        for (x of getters.getQueue) {
          if (x.username === rootGetters['authStore/loggedInUser']) {
            commit('queue_status', true)
            break
          } else {
            commit('queue_status', false)
          }
        }
      })
      .catch(error => {
        console.log(error.response)
        commit('queue_status', false)
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
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
