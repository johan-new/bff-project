<template>
    <div>
        <b-card md="6" class="shadow">
    <h4>Kommande matcher:</h4>
        <b-table stacked="sm" hover :items="games">
        </b-table>
        {{ games[0].when }}
        <!-- {{ compareDates }} -->
        <div v-if="dateToday > games[0].when">Idag är {{ dateToday }}</div>
        <div v-else>Hejdå</div>
        </b-card>
    </div>
</template>

<script>
export default {
  name: 'Games',
  data () {
    return {
      date: ''
    }
  },
  computed: {
    games () {
      const m = this.$store.getters['matchStore/previousGames']
      return Object.values(m)
    },
    // compareDates () {
    //   const when = this.$store.getters['matchStore/previousGames']
    //   if (this.date > Object.values(when)[0].when) {
    //     console.log('date är större')
    //   } else {
    //     console.log('when är större')
    //   }
    //   return when
    // },
    dateToday: {
      get () {
        const today = new Date().toISOString()
        console.log(today)
        return today
      }
      // set (newDate) {
      //   this.date = newDate
      // }
    }
  },
  methods: {
    previousGames () {
      this.$store.dispatch('matchStore/previousGames')
    }
  },
  created () {
    this.previousGames()
    this.gimmeDate()
  }
}
</script>
