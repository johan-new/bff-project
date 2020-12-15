import axios from 'axios'

const state = {

}

const getters = {

}

const actions = {
  submitMatchRequest (context, location) {
    location = location.location
    axios.post('http://localhost:8080/match', {
      location: location
    })
      .then(data => {
        console.log(data)
      })
      .catch(error => {
        console.log(error.response)
      })
  }
}
const mutations = {

}

export default {
  state,
  getters,
  actions,
  mutations
}
