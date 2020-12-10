<template>
    <div class="wrapper">
        <h1>Padel/Game component</h1>
        <p>Sök användare</p>
        <form @submit.prevent="fetchUser">
            <input type="username" placeholder="Username" v-model="user" /><br>
          <button>Search</button>
        </form>
        <p>Username: {{ this.$store.state.userStore.user.username }}</p>
        <p>User as an object: {{ this.$store.state.userStore.user }}</p>

    </div>
</template>

<script>
export default {
  name: 'Userprofile',
  data () {
    return {
      user: ''
    }
  },
  methods: {
    fetchUser () {
      const payload = {
        user: this.user
      }
      this.$store.dispatch('fetchUser', payload)
    }
  }
}
</script>
