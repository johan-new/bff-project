<template>
  <div>
    <b-navbar class="p-1" toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand to="/">Padel Pals</b-navbar-brand>
      <b-navbar-nav class="ml-auto">
        <b-nav-item to="/padel" v-if="isLoggedIn" exact exact-active-class="active">Spela padel</b-nav-item>
      </b-navbar-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-item to="/games" v-if="isLoggedIn" exact exact-active-class="active">Matcher</b-nav-item>
      </b-navbar-nav>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <!-- <b-navbar-nav> -->
        <b-navbar-nav class="ml-md-auto">
          <b-navbar-nav>
            <b-nav-item to="/login" v-if="!isLoggedIn">Logga in</b-nav-item>
            <b-nav-item to="/register" v-if="!isLoggedIn"
              >Registrera dig</b-nav-item
            >
          </b-navbar-nav>
          <b-nav-form @submit.prevent="fetchUser" v-if="isLoggedIn">
            <b-form-input
              size="sm"
              class="mr-sm-2"
              placeholder="Sök användare..."
              v-model="user"
            ></b-form-input>
            <b-button
              size="sm"
              variant="dark"
              class="my-2 my-sm-0"
              type="submit"
            >
              <b-icon icon="search" aria-hidden="true"></b-icon>
            </b-button>
          </b-nav-form>

          <b-nav-item-dropdown right v-if="isLoggedIn">
            <template #button-content>
              <b-icon
                icon="person-fill"
                aria-hidden="true"
                variant="light"
              ></b-icon>
            </template>
            <b-dropdown-item @click="fetchUserprofile"
              >Min profil</b-dropdown-item
            >
            <b-dropdown-divider></b-dropdown-divider>
            <b-dropdown-item @click="logout">Logga ut</b-dropdown-item>
          </b-nav-item-dropdown>
          <b-dropdown
            right
            text="Dropdown with text"
            variant="dark"
            toggle-class="text-decoration-none"
            no-caret
            v-if="isLoggedIn"
          >
            <template #button-content>
              &nbsp;<b-icon icon="bell-fill" aria-hidden="true"></b-icon>
            </template>
            <b-dropdown-text style="width: 240px">
              <p>Dina senaste notifikationer</p>
              <b-dropdown-divider></b-dropdown-divider>
              <Notifications />
            </b-dropdown-text>
          </b-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import axios from 'axios'
import Notifications from './Notifications.vue'

export default {
  components: { Notifications },
  name: 'Header-component',
  data () {
    return {
      user: ''
    }
  },
  computed: {
    isLoggedIn () {
      return this.$store.getters['authStore/isLoggedIn']
    },
    loggedInUser () {
      return this.$store.getters['authStore/loggedInUser']
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
      axios
        .get('http://localhost:8080/user', {
          params: { username }
        })
        .then((data) => {
          this.$router.push({
            name: 'Userprofile',
            params: { username: data.data.username, data: data.data }
          }).catch(error => {
            if (error.name !== 'NavigationDuplicated' &&
            !error.message.includes('Avoided redundant navigation to current location')) {
              console.log(error)
            }
          })
        })
        .catch((error) => {
          console.log(error.response)
        })
    },
    fetchUserprofile () {
      this.$router.push({
        name: 'Userprofile',
        params: { username: this.loggedInUser }
      }).catch(error => {
        if (error.name !== 'NavigationDuplicated' &&
            !error.message.includes('Avoided redundant navigation to current location')) {
          console.log(error)
        }
      })
    }
  }
}
</script>
