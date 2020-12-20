<template>
  <div class="wrapper">
    <h1>Userprofile component</h1>
    <div>Användarnamn: {{ username }}</div>
    <h3>
      Spelade och kommande matcher:
    </h3>
    <div v-for="(item, name) in data.games" :key="name">
      {{ name }}:
      {{ item.venue }}, {{ item.players }}, {{ item.id }},
      {{ item.when }}
    </div>
    <div v-if="loggedInUser">
      <h2>Ändra lösenord:</h2>
      <form @submit.prevent="changePassword">
        <input
          type="password"
          placeholder="Gammalt lösenord"
          v-model="oldPassword"
        /><br>
        <input
          type="password"
          placeholder="Nytt lösenord"
          v-model="newPassword"
        /><br>
        <button>Ändra lösenord</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Userprofile',
  data () {
    return {
      oldPassword: '',
      newPassword: ''
    }
  },
  computed: {
    loggedInUser () {
      const loggedIn = this.$store.getters['authStore/loggedInUser']
      if (loggedIn === this.username) {
        return true
      } else {
        return false
      }
    }
  },
  props: ['username', 'data'],
  methods: {
    changePassword () {
      const payload = {
        oldPassword: this.oldPassword,
        newPassword: this.newPassword
      }
      this.$store.dispatch('userStore/changePassword', payload)
    }
  }
}
</script>
