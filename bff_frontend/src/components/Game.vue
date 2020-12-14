<template>
    <div class="wrapper">
        <h1>Padel/Game component</h1>
        <h2>Spela padel: Ange location</h2>
                <form @submit.prevent="submitMatchRequest">
            <input type="location" placeholder="location" v-model="location" /><br>
          <button>Play padel</button>
        </form>

    </div>
</template>

<script>
export default {
  name: 'Game',
  data () {
    return {
      location: ''
    }
  },
  methods: {
    submitMatchRequest () {
      const payload = {
        location: this.location
      }
      this.$store.dispatch('submitMatchRequest', payload)
    }
  }
}
</script>
