<template>
  <div>
    <b-card class="p-5 shadow-lg p-3 mb-5">
        <h3 class="mb-4">Registrera konto</h3>
      <ValidationObserver v-slot="{ handleSubmit }">
        <b-form @submit.prevent="handleSubmit(registerUser)">
          <ValidationProvider name="email" rules="required|email" v-slot="{ errors }">
                <b-form-group id="registerInputMail" class="mb-0"
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
      <span class="error">{{ errors[0] }}.</span>
        </ValidationProvider>
        <ValidationProvider name="password" rules="required" v-slot="{ errors }">
                <b-form-group id="registerInputPassword" class="mb-0"
                    label="Lösenord:"
                    label-for="passwordInput">
        <b-form-input id="passwordInput"
                      type="password"
                      v-model="userData.password"
                      required
                      placeholder="Ange lösenord">
        </b-form-input>
      </b-form-group>
      <span class="error">{{ errors[0] }}.</span>
      </ValidationProvider>
        <div class="d-flex justify-content-between mt-2">
          <b-button type="submit">Registrera dig</b-button>
          </div>
          </b-form>
          </ValidationObserver>
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
        .then(() => this.$router.push({
          name: 'Login'
        }).catch(error => {
          if (error.name !== 'NavigationDuplicated' &&
            !error.message.includes('Avoided redundant navigation to current location')) {
            console.log(error.response)
          }
        }))
    }
  }
}
</script>

<style>
  .forgottenPassword {
    font-size: small;
  }
  .card { background-color: rgba(255, 255, 255, 0.9) !important; }
</style>
