<template>
    <div class="wrapper">
        <h1>Login component</h1>
        <form @submit.prevent="login">
            <input type="email" placeholder="Email" v-model="userData.email" /><br>
            <input type="password" placeholder="Choose a password" v-model="userData.password" /><br>
          <button>Log in</button>
        </form>
        <p>This will show if user is logged in smileyface</p>
        <p>Username: {{ this.$store.state.authStore.email }}</p>

    </div>
</template>

<script>
export default {
  name: 'Login',
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
    login () {
      console.log('Login code is ready to go...')
      console.log(this.userData)
      const payload = {
        name: this.userData.email,
        password: this.userData.password
      }
      this.$store.dispatch('loginUser', payload).then(() => {
        this.$router.push('/')
      })
    }
  }
}
</script>
