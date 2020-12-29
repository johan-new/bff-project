<template>
  <div>
    <b-card class="p-3">
        <h3 class="mb-4">Registrera konto</h3>
        <b-form @submit.prevent="registerUser">
                <b-form-group id="registerInputMail"
                    label="E-postadress:"
                    label-for="emailInput"
                    >
        <b-form-input id="emailInput"
                      type="email"
                      v-model="userData.email"
                      required
                      placeholder="Ange din e-postadress">
        </b-form-input>
      </b-form-group>
                <b-form-group id="registerInputPassword"
                    label="Lösenord:"
                    label-for="passwordInput">
        <b-form-input id="passwordInput"
                      type="password"
                      v-model="userData.password"
                      required
                      placeholder="Ange lösenord">
        </b-form-input>
      </b-form-group>
        <div class="d-flex justify-content-between">
          <b-button type="submit">Registrera dig</b-button>
          </div>
          </b-form>
    </b-card>
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
