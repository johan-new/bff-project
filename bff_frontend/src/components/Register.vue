<template>
  <div class="wrapper">
    <h1>Register component</h1>
    <form @submit.prevent="registerUser">
        <input type="email" placeholder="Email" v-model="userData.email" /><br>
        <input type="password" placeholder="Choose a password" v-model="userData.password" /><br>
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
      const payload = {
        name: this.userData.email,
        password: this.userData.password
      }
      this.$store.dispatch('authStore/addUser', payload)
    }
  }
}
</script>
