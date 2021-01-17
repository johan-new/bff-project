<template>
    <div>
        <b-card md="6" class="shadow">
    <h4>Kommande matcher:</h4>
        <!-- <b-table stacked="sm" hover :items="games">
        </b-table> -->
        {{ games[0].when }}
        <!-- {{ compareDates }} -->
        <div v-if="dateToday > games[0].when">Idag är {{ dateToday }}</div>
        <div v-else>Hejdå</div>
        <h5>Spelade</h5>
        <b-table :items="playedGames"></b-table>
        <h5>Kommande</h5>
        <b-table :items="comingGames"></b-table>
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
    },
    playedGames () {
      const gamez = this.$store.getters['matchStore/previousGames']
      let item = {}
      const playedGames = []
      for (item in gamez) {
        if (gamez[item].when < new Date().toISOString()) {
          playedGames.push(gamez[item])
        }
      }
      console.log(playedGames)
      return Object.values(playedGames)
    },
    comingGames () {
      const gamez = this.$store.getters['matchStore/previousGames']
      let item = {}
      const comingGames = []
      for (item in gamez) {
        if (gamez[item].when > new Date().toISOString()) {
          comingGames.push(gamez[item])
        }
      }
      console.log(comingGames)
      return comingGames
    }
  },
  methods: {
    previousGames () {
      this.$store.dispatch('matchStore/previousGames')
    }
  },
  created () {
    this.previousGames()
  }
}
</script>
