<template>
  <div class="wrapper">
    <h1>Register component</h1>
    <form @submit.prevent="registerUser">
        <input type="email" placeholder="Email" v-model="userData.email" /><br>
        <input type="password" placeholder="Choose a password" v-model="userData.password" /><br>
        <h2>{{ this.$store.state.authStore.test }}</h2>
        <button>Create account</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      userData: {
        email: '',
        password: ''
      },
      successMessage: '',
      errorMessage: ''
    }
  },
  methods: {
    registerUser () {
      console.log('Register code is ready to go...')
      console.log(this.userData)
      const payload = {
        name: this.userData.email,
        password: this.userData.password
      }
      console.log(payload)
      this.$store.dispatch('addUser', payload)
    }
  }
}
</script>
