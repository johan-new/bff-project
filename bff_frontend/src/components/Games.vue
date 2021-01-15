<template>
    <div>
        <b-card md="6" class="shadow">
    <!-- <div v-for="(item, name) of getPreviousGames" :key="name">{{ item }} {{ name }}</div> -->
    <!-- <div v-for="(item, name) of previous" :key="name">{{ item }} {{ name }}</div> -->
    <!-- <div v-for="(item) of test" :key="item.venue">{{ item }}</div> -->
    <h4>Kommande matcher:</h4>
        <b-table stacked="sm" hover :items="test">
        </b-table>
        </b-card>
    </div>
</template>
            <!-- <div v-for="(value) in item" :key="value.id"></div> -->

<script>
export default {
  name: 'Games',
  data () {
    return {

    }
  },
  computed: {
    getPreviousGames () {
      return this.$store.getters['matchStore/previousGames']
    },
    previous () {
      const s = this.$store.getters['matchStore/previousGames']
      const formatted = Object.values(s)
      console.log(formatted)
      return formatted
    },
    test () {
      const m = this.$store.getters['matchStore/previousGames']
      return Object.values(m)
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
