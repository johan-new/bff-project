<template>
    <div class="wrapper">
        <h1>User component</h1>
        <form @submit.prevent="fetchUser">
            <input type="username" placeholder="Username" v-model="username" /><br>
          <button>Search</button>
        </form>
        <p>Username: {{ this.$store.state.userStore.username }}</p>

    </div>
</template>

<script>
export default {
  name: 'User',
  data () {
    return {
      username: ''
    }
  },
  methods: {
    fetchUser () {
      const payload = {
        username: this.username
      }
      this.$store.dispatch('fetchUser', payload)
    }
  }
}
</script>
