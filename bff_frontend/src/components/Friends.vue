<template>
    <div class="wrapper">
        <h1>Friends component</h1>
        <form @submit.prevent="fetchUser">
            <input type="username" placeholder="Username" v-model="user" /><br>
          <button>Search</button>
        </form>
        <p>Användarnamn: {{ this.$store.state.userStore.user.username }}</p>
        <p>Användarobjekt: {{ this.$store.state.userStore.user }}</p>

        <div>
          <h2>Ändra lösenord:</h2>
            <form @submit.prevent="changePassword">
            <input type="password" placeholder="Gammalt lösenord" v-model="oldPassword" />
            <input type="password" placeholder="Nytt lösenord" v-model="newPassword" />
          <button>Ändra lösenord</button>
        </form>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Friends',
  data () {
    return {
      user: '',
      oldPassword: '',
      newPassword: ''
    }
  },
  created () {
    this.$store.dispatch('userStore/fetchUserprofile')
  },
  methods: {
    fetchUser () {
      const payload = {
        user: this.user
      }
      this.$store.dispatch('userStore/fetchUser', payload)
    },
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
