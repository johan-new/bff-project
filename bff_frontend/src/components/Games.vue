<template>
    <div>
      <b-card md="6" class="shadow">
        <b-card class="shadow-sm mb-4">
    <h4 class="font-weight-bold">Matcher</h4>
    <p>Här kan du se dina kommande och spelade padelmatcher.</p>
        </b-card>
        <div class="table-responsive">
        <b-card class="shadow-sm mb-4">
        <h5 class="font-weight-bold">Kommande matcher</h5>
        <div v-if="comingGames.length !== 0">
        <b-table stacked="sm" hover :items="comingGames" :fields="fields"></b-table>
        </div>
        <div v-else>Du har inga kommande matcher</div>
        </b-card>
        <b-card class="shadow-sm mb-4">
        <h5 class="font-weight-bold">Spelade matcher</h5>
        <div v-if="playedGames.length !== 0">
        <b-table stacked="sm" hover :items="playedGames" :fields="fields"></b-table>
        </div>
        <div v-else>Du har inga kommande matcher</div>
        </b-card>
        </div>
        </b-card>
    </div>
</template>

<script>
export default {
  name: 'Games',
  data () {
    return {
      fields: [
        {
          key: 'venue',
          label: 'Stad'
        }, {
          key: 'players',
          label: 'Spelare'
        }, {
          key: 'when',
          label: 'Tid'
        }
      ]
    }
  },
  computed: {
    games () {
      const m = this.$store.getters['matchStore/previousGames']
      return Object.values(m)
    },
    dateToday: {
      get () {
        const today = new Date().toISOString()
        console.log(today)
        return today
      }
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
