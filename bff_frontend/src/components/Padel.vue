<template>
    <div>
      <b-card md="6" class="shadow">
      <b-card class="shadow-sm mb-4">
        <h4 class="font-weight-bold">Spela padel</h4>
        <b-form @submit.prevent="submitMatch" class="needs-validation" validated novalidate>
          <div class="form-row">
            <div class="form-group col-md-4">
              <label for="inputDate">Datum:</label>
              <b-form-datepicker placeholder="Välj datum" class="form-control" id="inputDate" required v-model="form.date"></b-form-datepicker>
            </div>
            <div class="form-group col-md-3">
              <label for="inputTime">Tidpunkt:</label>
<b-form-timepicker label-no-time-selected="Välj tid" class="form-control" id="inputTime" required v-model="form.time"></b-form-timepicker>
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
    <div class="col-md-3 mb-3">
      <label for="participants">Antal Spelare:</label>
      <select class="custom-select" id="participants" required v-model="form.participants">
        <option selected disabled value="">Antal...</option>
            <option :value="1" selected>1</option>
    <option :value="2">2</option>
    <option :value="3">3</option>
      </select>
    </div>
    <div class="col-md-4 mb-3">
      <label for="location">Stad:</label>
      <input type="text" class="form-control" id="location" v-model="form.location" required>
    </div>
    </div>
          <b-button variant="outline-secondary" type="submit">Spela!</b-button>
        </b-form>
        </b-card>

        <b-card class="p-2 shadow-sm mb-1">
          <h4 class="mb-4 font-weight-bold">Hitta din match</h4>
      <div v-for="(item, name) of getQueue" :key="name" > <h5>{{ name }} </h5>
      <div class="table-responsive">
      <b-table stacked="sm" hover :items="item" :fields="fields">
        <template #cell(info)="row">
        <div v-for="(value) in item" :key="value.location">
            <b-button variant="outline-secondary" @click="row.toggleDetails">{{ row.detailsShowing ? 'Dölj' : 'Visa mer'}}</b-button>
        </div>
        </template>
              <template #row-details="row">
        <b-card bg-variant="light">
        <div v-for="(value) in item" :key="value.location">
          <div v-if="row.item.confirmedParticipants.length !== 0">
          <h5 class="mb-3">Accepterade spelare:</h5>
          <div v-for="confirmedParticipants in row.item.confirmedParticipants" :key="confirmedParticipants">
            <b-card no-body class="my-2 shadow-sm" align-v="center">
            <div class="m-2">{{ confirmedParticipants }}</div>
            </b-card>
            </div>
          <hr>
          </div>
          <div v-if="value.username === loggedInUser">
          <div v-if="Object.keys(row.item.joinRequests).length !== 0">
          <h5 class="my-3">Spelare som vill gå med:</h5>
        <div v-for="(joinRequest, name) in row.item.joinRequests" :key="joinRequest.id">
          <b-card v-if="joinRequest.status === 'PENDING'" no-body class="my-2 shadow-sm">
        <div class="m-2 d-flex">
        <div class="mr-auto">{{ joinRequest.sender }}</div>
        <div>
          <b-button @click="acceptMatchRequest(name, item[0].id)" variant="outline-secondary" size="sm" class="mx-1">Acceptera</b-button>
          <b-button variant="outline-secondary" size="sm" class="mx-1">Neka</b-button></div>
        </div>
        </b-card>
          </div>
        <hr>
        </div>
          <div>
            <b-button variant="outline-secondary" @click="cancelMatch(name)" >Avbryt</b-button>
          </div>
        </div>
        <div v-if="value.username !== loggedInUser">
          <!-- <div v-for="(joinRequest) in row.item.joinRequests" :key="joinRequest.id"> -->
              <!-- <div v-if="Object.keys(row.item.joinRequests).length === 0"> -->
            <div v-for="joinRequest in row.item.joinRequests" :key="joinRequest.id">
              <div v-if="joinRequest.status === 'PENDING' && joinRequest.sender === loggedInUser">
                <div>Du är i kö, chilla, {{ joinRequest.sender }}</div>
              </div>
              <div v-else-if="joinRequest.status === 'ACCEPTED' && joinRequest.sender === loggedInUser">
                <div>Du är med i spelet</div>
              </div>
              <div v-else-if="joinRequest.sender === loggedInUser && joinRequest.status === 'REJECTED'">
              <b-button @click="joinMatch(item[0].id)">Gå med</b-button>
              {{ joinRequest.sender }}
              </div>
              <div v-else-if="joinRequest.sender !== loggedInUser">
                <b-button @click="joinMatch(item[0].id)">Gå med</b-button>
              </div>
            </div>
            <div v-if="Object.keys(row.item.joinRequests).length === 0">
          <p>!loggedInUser</p>
          <b-button @click="joinMatch(item[0].id)">Gå med2</b-button>
            </div>
          <!-- </div>
            </div> -->
        <!-- </div> -->
        </div>
        </div>
        </b-card>
      </template>
      </b-table>
      </div>
        </div>
</b-card>
</b-card>
    </div>
</template>

<script>
// import axios from 'axios'
export default {
  name: 'Padel',
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
      },
      fields: ['username', 'date', 'time', 'courtIsBooked', 'venue', 'requestedParticipants', 'info']
    }
  },
  methods: {
    submitMatch () {
      this.$store.dispatch('matchStore/submitMatch', this.form)
        .then(() => this.$store.dispatch('matchStore/matchingQueue'))
    },
    cancelMatch (location) {
      this.$store.dispatch('matchStore/cancelMatch', location)
        .then(() => this.$store.dispatch('matchStore/matchingQueue'))
    },
    acceptMatchRequest (name, id) {
      const payload = {
        matchingRequestId: id,
        joinRequestId: name,
        action: 'accept'
      }
      console.log(payload)
      this.$store.dispatch('matchStore/matchRequest', payload)
        .then(() => this.$store.dispatch('matchStore/matchingQueue'))
    },
    rejectMatchRequest (name, id) {
      const payload = {
        matchingRequestId: id,
        joinRequestId: name,
        action: 'reject'
      }
      this.$store.dispatch('matchStore/matchRequest', payload)
        .then(() => this.$store.dispatch('matchStore/matchingQueue'))
    },
    joinMatch (id) {
      this.$store.dispatch('matchStore/joinMatch', id)
        .then(() => this.$store.dispatch('matchStore/matchingQueue'))
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
