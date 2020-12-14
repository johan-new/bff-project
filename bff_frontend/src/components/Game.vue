<template>
    <div class="wrapper">
        <h1>Padel/Game component</h1>
        <h2>Spela padel: Ange location</h2>
                <form @submit.prevent="submitMatchRequest">
            <input type="location" placeholder="location" v-model="location" /><br>
          <button>Play padel</button>
        </form>

        <form @submit.prevent="cancelMatchRequest">
          <button v-if="inQueue">Cancel match request</button>
        </form>
        <div class="mmQueue" style="border:1px solid black"><h2>MM queue:</h2>
        <div v-for="(item, mmQueue) in getQueue" :key="mmQueue" >Username: {{ item.username }} Location: {{ item.location }}</div>
</div>
    </div>
</template>

<script>
export default {
  name: 'Game',
  computed: {
    inQueue () { return this.$store.getters.inQueue },
    getQueue () { return this.$store.getters.getQueue }
  //  newQueueItem () { return this.$store.state.queue }
  },
  data () {
    return {
      location: ''
    }
  },
  created () {
    this.$store.dispatch('matchingQueue')
  },
  methods: {
    submitMatchRequest () {
      const payload = {
        location: this.location
      }
      this.$store.dispatch('submitMatchRequest', payload)
    },
    cancelMatchRequest () {
      this.$store.dispatch('cancelMatchRequest')
    }
  }
}
</script>
