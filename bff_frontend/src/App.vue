<template>
  <div id="app">
    <Header />
    <router-view></router-view>
    <router-link to="/about">About</router-link><span v-if="isLoggedIn"> | <a @click="logout">Logout</a></span>
  </div>
</template>

<script>
import Header from './components/Header'
export default {
  name: 'App',
  components: {
    Header
  },
  computed: {
    isLoggedIn () { return this.$store.getters.isLoggedIn }
  },
  methods: {
    logout () {
      this.$store.dispatch('logout')
        .then(() => {
          this.$router.push('/login')
        })
    },
    created () {
      this.$http.interceptors.response.use(undefined, function (err) {
        return new Promise(function (resolve, reject) {
          if (err.status === 401 && err.config && !err.config.__isRetryRequest) {
            this.$store.dispatch('logout')
          }
        //  throw err
        })
      })
    }
  }
}
</script>
