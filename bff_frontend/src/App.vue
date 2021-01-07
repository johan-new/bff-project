<template>
  <div id="app">
    <Header />
    <b-container fluid>
  <b-row align-h="center" class="mt-5">
    <b-col></b-col>
    <b-col md="4"><router-view></router-view></b-col>
    <b-col></b-col>
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
  @import url('https://fonts.googleapis.com/css2?family=Play&display=swap');
  @import url('https://fonts.googleapis.com/css2?family=ABeeZee&display=swap');
  /* @import url('https://fonts.googleapis.com/css2?family=Lato:wght@700&family=Roboto:wght@700&display=swap'); */

  body {
    /* background: #EEF1F4 !important; */
    background-image: url('http://www.allwhitebackground.com/images/7/Padel-Wallpaper-1920x1080-07118.jpg');
    font-family: 'Lato', sans-serif !important;
    background-repeat: no-repeat;
    background-size: cover;
    background-attachment: fixed;
  }
</style>
