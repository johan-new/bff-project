<template>
    <header>
        <div class="main-navigation">
            <router-link to="/">Hem</router-link>
            <router-link to="/login" v-if="!isLoggedIn">Logga in</router-link>
            <router-link to="/register" v-if="!isLoggedIn">Registrera dig</router-link>
            <router-link to="/padel" v-if="isLoggedIn">Spela padel</router-link>
            <router-link to="/userprofile" v-if="isLoggedIn">Profil</router-link>
            <a v-if="isLoggedIn" @click="logout">Logga ut</a>

        </div>
    </header>
</template>

<script>
export default {
  name: 'Header-component',
  computed: {
    isLoggedIn () { return this.$store.getters['authStore/isLoggedIn'] }
  },
  methods: {
    logout () {
      this.$store.dispatch('authStore/logout')
        .then(() => {
          this.$router.push('/login')
        })
    }
  }
}
</script>
