<template>
  <div>
    <b-card class="p-5 shadow-lg p-3 mb-5">
      <h3 class="mb-4">Logga in</h3>
      <ValidationObserver v-slot="{ handleSubmit }">
      <b-form @submit.prevent="handleSubmit(login)">
        <ValidationProvider name="email" rules="required|email" v-slot="{ errors }">
        <b-form-group class="mb-0"
          id="loginInputMail"
          label="E-postadress:"
          label-for="emailInput"
        >
          <b-form-input
            id="emailInput"
            type="email"
            v-model="userData.email"
            required
            placeholder="Ange din e-postadress"
          >
          </b-form-input>
        </b-form-group>
        <span class="error">{{ errors[0] }}.</span>
        </ValidationProvider>
        <ValidationProvider name="password" rules="required" v-slot="{ errors }">
        <b-form-group class="mb-0"
          id="loginInputPassword"
          label="Lösenord:"
          label-for="passwordInput"
        >
          <b-form-input
            id="passwordInput"
            type="password"
            v-model="userData.password"
            required
            placeholder="Ange lösenord"
          >
          </b-form-input>
        </b-form-group>
        <span class="error">{{ errors[0] }}.</span>
      </ValidationProvider>
        <div class="d-flex justify-content-between mt-2">
          <b-button type="submit">Logga in</b-button>
          <div class="forgottenPassword">
            <a href="#" v-b-modal.modal1>Glömt lösenordet?</a>
          </div>
        </div>
        <div class="mt-4">
          <p class="font-weight-light">
            Saknar du ett konto? Skapa ett <a href="/register">här</a>
          </p>
        </div>
      </b-form>
    </ValidationObserver>
    </b-card>
    <b-modal id="modal1" title="Glömt lösenord">
      <b-img
        src="https://www.jhspecialty.com/Data/c316596e7dca31e2444cbf68ec480438.jpg?Revision=cSQ&Timestamp=X0B84V"
        fluid
        alt="Responsive image"
      />
    </b-modal>
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
      }
    }
  },
  methods: {
    login () {
      const payload = {
        name: this.userData.email,
        password: this.userData.password
      }
      this.$store
        .dispatch('authStore/login', payload)
        .then(() => this.$router.push('/padel'))
        .catch((err) => console.log(err))
    }
  }
}
</script>

<style>
.forgottenPassword {
  font-size: small;
}
.card {
  background-color: rgba(255, 255, 255, 0.9) !important;
}
</style>
