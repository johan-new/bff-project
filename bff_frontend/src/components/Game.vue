<template>
    <div class="wrapper">
        <h1>Padel/Game component</h1>
        <h2>Spela padel: Ange location</h2>
                <form @submit.prevent="submitMatchRequest">
            <input type="location" placeholder="Stad" v-model="location" /><br>
            <input type="date" placeholder="Datum" v-model="date" /><br>
            <input type="time" placeholder="Tid" v-model="time" /><br>
            <input type="reservation" placeholder="Reserverat" v-model="reservation" /><br>
            <input type="venue" placeholder="Padelhall" v-model="venue" /><br>
            <input type="participants" placeholder="Spelare" v-model="participants" /><br>
          <button>Play padel</button>
        </form>

        <form @submit.prevent="cancelMatchRequest">
          <button v-if="inQueue">Cancel match request</button>
        </form>
        <div class="mmQueue" style="border:1px solid black"><h2>MM queue:</h2>
      <div v-for="(item, name) of getQueue" :key="name" > <h3>Location: {{ name }} </h3>
      <!--Kanske lägga till (value,name) nedan för att få med namnet på varje rubrik, username etc -->
        <div v-for="(value) in item" :key="value.username">
          <p>{{value.username}}, {{value.date}}, {{value.time}}, {{value.reservation}}, {{value.venue}}, {{value.participants}}</p>
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
    inQueue () { return this.$store.getters['matchStore/inQueue'] }
  },
  data () {
    return {
      location: '',
      date: '',
      time: '',
      reservation: false,
      venue: '',
      participants: 0
    }
  },
  methods: {
    submitMatchRequest () {
      const payload = {
        location: this.location,
        date: this.date,
        time: this.time,
        reservation: this.reservation,
        venue: this.venue,
        participants: this.participants
      }
      console.log(payload)
      this.$store.dispatch('matchStore/submitMatchRequest', payload)
      // .then(() => this.$store.dispatch('matchStore/matchingQueue'))
    },
    cancelMatchRequest () {
      this.$store.dispatch('matchStore/cancelMatchRequest')
      //  .then(() => this.$store.dispatch('matchStore/matchingQueue'))
    }
  }
  // created () {
  //   this.$store.dispatch('matchStore/matchingQueue')
  // }
}
</script>
