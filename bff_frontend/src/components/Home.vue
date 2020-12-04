<template>
    <div class="wrapper">
        <h1>Home component(logged in user)</h1>
        <h2>{{ this.$store.state.authStore.test }}</h2>
        <form @submit.prevent="matchUser">
          <input type="location" placeholder="Choose a location" v-model="userData.location" /><br>
          <h2>{{ this.$store.state.authStore.test }}</h2>
          <button>Find match</button>
        </form>
        <p>This will show if user is logged in smileyface</p>
        <p>{{ this.$store.state.authStore.email }}</p>
        <p>{{ this.$store.state.authStore.location }}</p>
    </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {
      userData: {
        email: 'user@name.se',
        location: ''
      },
      successMessage: '',
      errorMessage: ''
    }
  },
  methods: {
    matchUser () {
      console.log('matchUser code is ready to go...')
      console.log(this.userData)
      const payload = {
        name: this.userData.email,
        location: this.userData.location
      }
      this.$store.dispatch('matchUser', payload)
    }
  }
}
</script>
