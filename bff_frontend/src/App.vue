<template>
  <div id="app">
    <Header />
    <b-container class="bv-example-row">
  <b-row align-h="center" class="mt-5">
    <b-col md="5"><router-view></router-view></b-col>
  </b-row>
</b-container>
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
    created () {
      this.$http.interceptors.response.use(undefined, function (err) {
        return new Promise(function (resolve, reject) {
          if (err.status === 401 && err.config && !err.config.__isRetryRequest) {
            this.$store.dispatch('logout')
          }
          throw err // Skapar error i consolen när man försöker nå reqAuth-sidor
        })
      })
    }
  }
}
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Lato:400,700');
  body {
    background: #EEF1F4 !important;
    font-family: 'Lato', sans-serif !important;
  }
</style>
