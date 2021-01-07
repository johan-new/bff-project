<template>
    <div>
      <b-card md="6" class="shadow">
      <b-card class="shadow-sm mb-4">
        <h1>Padel/Game component</h1>
        <h2>Spela padel: Ange location</h2>
<b-form-timepicker placeholder="Välj tidpunkt" label-no-time-selected="Tid"></b-form-timepicker>
        <b-form @submit.prevent="submitMatchRequest" class="needs-validation" validated novalidate>
          <div class="form-row">
            <div class="form-group col-md-4">
              <label for="inputDate">Datum:</label>
              <b-form-datepicker placeholder="Välj datum" class="form-control" id="inputDate" required v-model="form.date"></b-form-datepicker>
              <!-- <input type="date" class="form-control" id="inputDate" required v-model="form.date"> -->
            </div>
            <div class="form-group col-md-2">
              <label for="inputTime">Tidpunkt:</label>
              <input type="time" class="form-control" id="inputTime" required v-model="form.time" />
            </div>
          </div>

           <b-form-group label="Bokat hall:">
  <b-form-radio-group id="radio-group-2" v-model="form.reservation" :value="false"
    name="radio-sub-component">
    <b-form-radio :value="false">Nej</b-form-radio>
    <b-form-radio :value="true">Ja</b-form-radio>
              <input v-if="trueOrFalse" type="venue" placeholder="Ange hall..." v-model="form.venue" />
  </b-form-radio-group>
</b-form-group>
    <div class="form-row">
    <div class="col-md-2 mb-3">
      <label for="participants">Antal Spelare:</label>
      <select class="custom-select" id="participants" required v-model="form.participants">
        <option selected disabled value="">Choose...</option>
            <option :value="1" selected>1</option>
    <option :value="2">2</option>
    <option :value="3">3</option>

      </select>
    </div>
    <div class="col-md-4 mb-3">
      <label for="location">Stad:</label>
      <input type="text" class="form-control" id="location" v-model="form.location" required>
            <!-- <div class="invalid-tooltip">
        Please provide a valid city.
      </div> -->
    </div>
    </div>

          <b-button type="submit">Spela!</b-button>
        </b-form>
        </b-card>

        <b-card class="p-2 shadow-sm mb-1" style="background-color: white" ><h3 class="mb-4">Hitta din match</h3>
      <div v-for="(item, name) of getQueue" :key="name" > <h4>{{ name }} </h4>
      <b-table striped hover :items="item"></b-table>
        <div v-for="(value) in item" :key="value.location">
            <b-button v-if="value.username === loggedInUser" @click="cancelMatchRequest(name)" >Cancel match request</b-button>
        </div>
        </div>
</b-card>
</b-card>
    </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Game',
  computed: {
    getQueue () { return this.$store.getters['matchStore/getQueue'] },
    inQueue () { return this.$store.getters['matchStore/inQueue'] },
    loggedInUser () { return this.$store.getters['authStore/loggedInUser'] },
    trueOrFalse () { return this.form.reservation }
  },
  data () {
    return {
      form: {
        location: '',
        date: '',
        time: '',
        reservation: false,
        venue: '',
        participants: ''
      }
    }
  },
  methods: {
    submitMatchRequest () {
      this.$store.dispatch('matchStore/submitMatchRequest', this.form)
        .then(() => this.$store.dispatch('matchStore/matchingQueue'))
    },
    cancelMatchRequest (location) {
      this.$store.dispatch('matchStore/cancelMatchRequest', location)
        .then(() => this.$store.dispatch('matchStore/matchingQueue'))
    },
    fetchMatchingQueue () {
      axios.get('http://localhost:8080/match/queue')
        .then(data => {
          var item
          for (item in data.data) {
            console.log(item)
            this.items.push(item)
          }
          console.log(this.items)
        })
        .catch((error) => {
          console.log(error.response)
        })
    }
  },
  created () {
    this.$store.dispatch('matchStore/matchingQueue')
  }
}
</script>

<style>
.card {
  background-color: rgba(255, 255, 255) !important;
}
</style>
