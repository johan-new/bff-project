<template>
  <header>
    <div class="main-navigation">
      <router-link to="/">Hem</router-link>
      <router-link to="/login" v-if="!isLoggedIn">Logga in</router-link>
      <router-link to="/register" v-if="!isLoggedIn"
        >Registrera dig</router-link
      >
      <router-link to="/padel" v-if="isLoggedIn">Spela padel</router-link>
      <!--  <router-link to="/userprofile" v-if="isLoggedIn">Profil</router-link> -->
      <a v-if="isLoggedIn" @click="fetchUserprofile">Profil</a>
      <router-link to="/notifications" v-if="isLoggedIn">Notiser</router-link>
      <a v-if="isLoggedIn" @click="logout">Logga ut</a>
      <form v-if="isLoggedIn" @submit.prevent="fetchUser">
        <input type="username" placeholder="Användarnamn..." v-model="user" />
        <button>Sök användare</button>
      </form>
    </div>
  </header>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Header-component',
  data () {
    return {
      user: ''
    }
  },
  computed: {
    isLoggedIn () {
      return this.$store.getters['authStore/isLoggedIn']
    }
  },
  methods: {
    logout () {
      this.$store.dispatch('authStore/logout').then(() => {
        this.$router.push('/login')
      })
    },
    fetchUser () {
      const username = this.user
      console.log(username)
      axios
        .get('http://localhost:8080/user', {
          params: { username }
        })
        .then((data) => {
          console.log(data.data)
          this.$router.push({
            name: 'Userprofile',
            params: { username: data.data.username, data: data.data }
          })
        })
        .catch((error) => {
          console.log(error.response)
        })
    },
    fetchUserprofile () {
      axios
        .get('http://localhost:8080/loggedinuser')
        .then((data) => {
          this.$router.push({
            name: 'Userprofile',
            params: { username: data.data.username, data: data.data }
          })
        })
        .catch((error) => {
          console.log(error.response)
        })
    }
  }
}
</script>
