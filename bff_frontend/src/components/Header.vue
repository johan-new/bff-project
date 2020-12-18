<template>
    <header>
        <div class="main-navigation">
            <router-link to="/">Hem</router-link>
            <router-link to="/login" v-if="!isLoggedIn">Logga in</router-link>
            <router-link to="/register" v-if="!isLoggedIn">Registrera dig</router-link>
            <router-link to="/padel" v-if="isLoggedIn">Spela padel</router-link>
            <router-link to="/userprofile" v-if="isLoggedIn">Profil</router-link>
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
export default {
  name: 'Header-component',
  data () {
    return {
      user: ''
    }
  },
  computed: {
    isLoggedIn () { return this.$store.getters['authStore/isLoggedIn'] }
  },
  methods: {
    logout () {
      this.$store.dispatch('authStore/logout')
        .then(() => {
          this.$router.push('/login')
        })
    },
    fetchUser () {
      const payload = {
        user: this.user
      }
      this.$store.dispatch('userStore/fetchUser', payload)
        .then(() => this.$router.push({ name: 'Userprofile', params: { username: payload.user } }))
        .catch(err => console.log(err))
      this.user = ''
    }
  }
}
</script>
