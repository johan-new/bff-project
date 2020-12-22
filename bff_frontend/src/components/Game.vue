<template>
    <div class="wrapper">
        <h1>Padel/Game component</h1>
        <h2>Spela padel: Ange location</h2>
                <form @submit.prevent="submitMatchRequest">
            <input type="location" placeholder="Stad" v-model="form.location" /><br>
            <input type="date" placeholder="Datum" v-model="form.date" /><br>
            <input type="time" placeholder="Tid" v-model="form.time" /><br>
            <input type="checkbox" v-model="form.reservation" /><span>Bokat?</span><br>
            <input type="venue" placeholder="Padelhall" v-model="form.venue" /><br>
            <input type="participants" placeholder="Spelare" v-model="form.participants" /><br>
          <button>Play padel</button>
        </form>
        <div class="mmQueue" style="border:1px solid black"><h2>MM queue:</h2>
      <div v-for="(item, name) of getQueue" :key="name" > <h3>Location: {{ name }} </h3>
      <!--Kanske lägga till (value,name) nedan för att få med namnet på varje rubrik, username etc -->
        <div v-for="(value) in item" :key="value.location">
          <p>{{value.username}}, {{value.date}}, {{value.time}}, {{value.reservation}}, {{value.venue}}, {{value.participants}}
            <button v-if="value.username === loggedInUser" @click="cancelMatchRequest(name)" >Cancel match request</button>
          </p>
        </div>
        </div>
</div>

    </div>
</template>

<script>
export default {
  name: 'Game',
  computed: {
    getQueue () { return this.$store.getters['matchStore/getQueue'] },
    inQueue () { return this.$store.getters['matchStore/inQueue'] },
    loggedInUser () { return this.$store.getters['authStore/loggedInUser'] }
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
    }
  },
  created () {
    this.$store.dispatch('matchStore/matchingQueue')
  }
}
</script>
