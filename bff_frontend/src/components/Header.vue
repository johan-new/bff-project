<template>
<div>
  <b-navbar toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="/">Padel pals</b-navbar-brand>
          <b-navbar-nav class="ml-auto">
    <b-nav-item  to="/padel" v-if="isLoggedIn">Spela padel</b-nav-item>
          </b-navbar-nav>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-navbar-nav>
        <b-nav-item href="/login" v-if="!isLoggedIn">Logga in</b-nav-item>
        <b-nav-item href="/register" v-if="!isLoggedIn">Registrera dig</b-nav-item>
      </b-navbar-nav>
        <b-nav-form v-if="isLoggedIn">
          <b-form-input size="sm" class="mr-sm-2" placeholder="Användarnamn..."></b-form-input>
          <b-button size="sm" class="my-2 my-sm-0" type="submit">Sök användare</b-button>
        </b-nav-form>

        <b-nav-item-dropdown right v-if="isLoggedIn">
          <!-- Using 'button-content' slot -->
          <template #button-content>
            <em>Profil</em>
          </template>
          <b-dropdown-item @click="fetchUserprofile">Min profil</b-dropdown-item>
          <b-dropdown-item @click="logout">Logga ut</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item href="/notifications">Notiser</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
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
