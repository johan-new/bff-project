<template>
    <div>
      <b-card md="6" class="shadow">
      <b-card class="shadow-sm mb-4">
        <h4 class="font-weight-bold">Spela padel</h4>
        <ValidationObserver v-slot="{ handleSubmit }">
        <b-form @submit.prevent="handleSubmit(submitMatch)" novalidate>
          <div class="form-row">
            <div class="form-group col-md-5">
              <ValidationProvider name="date" rules="required" v-slot="{ errors }">
              <label for="inputDate">Datum:</label>
              <b-form-datepicker placeholder="Välj datum" class="form-control" id="inputDate" v-model="form.date"></b-form-datepicker>
              <!-- <b-form-datepicker placeholder="Välj datum" id="inputDate" v-model="form.date"></b-form-datepicker> -->
              <span class="error">{{ errors[0] }}.</span>
            </ValidationProvider>
            </div>
            <div class="form-group col-md-3">
              <ValidationProvider name="time" rules="required" v-slot="{ errors }">
              <label for="inputTime">Tidpunkt:</label>
              <b-form-timepicker label-no-time-selected="Välj tid" class="form-control" id="inputTime" v-model="form.time"></b-form-timepicker>
              <span class="error">{{ errors[0] }}.</span>
              </ValidationProvider>
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
      <ValidationProvider name="participants" rules="required" v-slot="{ errors }">
      <label for="participants">Antal Spelare:</label>
      <select class="custom-select" id="participants" v-model="form.participants">
        <option selected disabled value="">Antal...</option>
            <option :value="1" selected>1</option>
    <option :value="2">2</option>
    <option :value="3">3</option>
      </select>
      <span class="error">{{ errors[0] }}.</span>
      </ValidationProvider>
    </div>
    <div class="col-md-4 mb-3">
      <ValidationProvider name="location" rules="required" v-slot="{ errors }">
      <label for="location">Stad:</label>
      <input type="text" class="form-control" id="location" v-model="form.location">
      <span class="error">{{ errors[0] }}.</span>
      </ValidationProvider>
    </div>
    </div>
          <b-button variant="outline-secondary" type="submit">Spela!</b-button>
        </b-form>
        </ValidationObserver>
        </b-card>

        <b-card class="p-2 shadow-sm mb-1" :key="componentKey">
                  <div class="mb-4 d-flex">
        <div class="mr-auto">
          <h4 class="font-weight-bold">Hitta din match</h4>
        </div>
          <b-button size="sm" variant="outline-secondary" @click="updateQueue(); updateKey()">Uppdatera</b-button>
                  </div>

      <div v-for="(item, name, index) of getQueue" :key="index" > <h5>{{ name }} </h5>
      <div class="table-responsive">
      <b-table stacked="sm" hover :items="item" :fields="fields">
        <template #cell(info)="row">
        <div v-for="(value, index) in item" :key="index">
          <div v-if="value.username === row.item.username">
            <b-button variant="outline-secondary" @click="row.toggleDetails">{{ row.detailsShowing ? 'Dölj' : 'Visa mer'}}</b-button>
            </div>
        </div>
        </template>
              <template #row-details="row">
        <b-card bg-variant="light">
        <div v-for="(value, index) in item" :key="index">
          <!-- {{ value }} -->
          <!-- <div v-if="value.username === row.item.username"> -->
          <div v-if="row.item.confirmedParticipants.length !== 0">
          <div v-if="value.username === row.item.username">
          <h5 class="mb-3">Accepterade spelare:</h5>
          <div v-for="confirmedParticipants in row.item.confirmedParticipants" :key="confirmedParticipants">
            <b-card no-body class="my-2 shadow-sm" align-v="center">
            <div class="m-2">
              <div v-if="confirmedParticipants === loggedInUser">
            <div>{{ confirmedParticipants }}</div>
            </div>
          <div v-else>
            <div class="text-secondary">{{ confirmedParticipants }}</div>
            </div>
            </div>
            </b-card>
            </div>
          <hr>
          </div>
          </div>
            <div :key="componentKey">
          <div v-if="Object.keys(row.item.joinRequests).length !== 0">
          <div v-if="value.username === row.item.username">
          <h5 class="my-3">Spelare som vill gå med:</h5>
        <div v-for="(joinRequest, name, index) in row.item.joinRequests" :key="index">
          <div v-if="joinRequest.status === 'PENDING'">
          <b-card no-body class="my-2 shadow-sm">
        <div class="m-2 d-flex">
        <div class="mr-auto">
          <div v-if="joinRequest.sender === loggedInUser">
            <div>{{ joinRequest.sender }}</div>
            </div>
          <div v-else>
            <div class="text-secondary">{{ joinRequest.sender }}</div>
            </div>
          </div>
        <div v-if="value.username === loggedInUser">
          <b-button @click="acceptMatchRequest(name, row.item.id); updateKey()" variant="outline-secondary" size="sm" class="mx-1">Acceptera</b-button>
          <b-button variant="outline-secondary" size="sm" class="mx-1" @click="rejectMatchRequest(name, item[index].id); updateKey()">Neka</b-button>
          </div>
        </div>
        </b-card>
        </div>
        </div>
        <hr>
          </div>
        </div>
          <div v-if="row.item.username === loggedInUser && row.item.username === value.username">
            <b-button variant="outline-secondary" @click="cancelMatch(name)" >Avbryt</b-button>
          </div>
<!-- <div v-for="(value, index) in item" :key="index">
          <div v-if="value.username === row.item.username"> -->
        <!-- <div v-if="value.username !== loggedInUser"> -->
          <div v-if="value.username === row.item.username">
            <div v-for="(joinRequest, index) in row.item.joinRequests" :key="index">
              <div v-if="joinRequest.status === 'PENDING' && joinRequest.sender === loggedInUser">
                <div>Du är placerad i kö. Invänta matcharrangörens beslut.</div>
              </div>
              <div v-else-if="joinRequest.status === 'ACCEPTED' && joinRequest.sender === loggedInUser">
                <div>Du är med i spelet</div>
              </div>
            </div>
            <div v-if="!testing(row.item.joinRequests) && row.item.username !== loggedInUser">
              <b-button @click="joinMatch(item[index].id); updateKey()">Gå med</b-button>
            </div>
            </div>
        </div>
        <!-- </div> -->
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
export default {
  name: 'Padel',
  computed: {
    inQueue () { return this.$store.getters['matchStore/inQueue'] },
    loggedInUser () { return this.$store.getters['authStore/loggedInUser'] },
    trueOrFalse () { return this.form.reservation },
    getQueue () {
      const queue = this.$store.getters['matchStore/getQueue']
      let q = {}
      for (q in queue) {
        if (queue[q][0].courtIsBooked === true) {
          queue[q][0].courtIsBooked = 'Ja'
        } else {
          queue[q][0].courtIsBooked = 'Nej'
        }
      }
      return queue
    }
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
      fields: [
        {
          key: 'username',
          label: 'Användare'
        }, {
          key: 'date',
          label: 'Datum'
        }, {
          key: 'time',
          label: 'Tid'
        }, {
          key: 'courtIsBooked',
          label: 'Bokat'
        }, {
          key: 'venue',
          label: 'Padelhall'
        }, {
          key: 'requestedParticipants',
          label: 'Önskat spelarantal'
        }, {
          key: 'info',
          label: 'Info'
        }
      ],
      componentKey: 1337,
      compkey: 0
    }
  },
  methods: {
    submitMatch () {
      this.$store.dispatch('matchStore/submitMatch', this.form)
        .then(() => this.$store.dispatch('matchStore/matchingQueue'))
      this.$bvToast.toast('Din match har blivit tillagd!', {
        title: 'Succé!',
        autoHideDelay: 5000,
        toaster: 'b-toaster-bottom-center',
        variant: 'success'
      })
        .catch(error => {
          this.$bvToast.toast('Det gick EJ bra!', error.response)
          console.log(error)
        })
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
    },
    testing (param) {
      let s = {}
      let value = false
      for (s in param) {
        if (param[s].sender === this.$store.getters['authStore/loggedInUser']) {
          value = true
          break
        }
      }
      return value
    },
    updateKey () {
      this.componentKey += 1
    },
    updateQueue () {
      this.$store.dispatch('matchStore/matchingQueue')
    }
  },
  created () {
    this.updateQueue()
  }
}
</script>

<style>
.card {
  background-color: rgba(255, 255, 255) !important;
}
.error {
  color: red;
  font-size: small;
  font-style: italic;
}
</style>
