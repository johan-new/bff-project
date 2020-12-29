<template>
  <div>
    <b-card class="p-3">
        <h3 class="mb-4">Logga in</h3>
        <b-form @submit.prevent="login">
                <b-form-group id="loginInputMail"
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
                <b-form-group id="loginInputPassword"
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
          <b-button type="submit">Logga in</b-button>
        <div class="forgottenPassword">
          <a href="#" v-b-modal.modal1>Glömt lösenordet?</a>
        </div>
          </div>
          <div class="mt-4">
            <p class="font-weight-light">Saknar du ett konto? Skapa ett <a href="/register">här</a></p>
            </div>
          </b-form>
    </b-card>
      <b-modal id="modal1" title="Glömt lösenord">
      <b-img src="https://www.jhspecialty.com/Data/c316596e7dca31e2444cbf68ec480438.jpg?Revision=cSQ&Timestamp=X0B84V" fluid alt="Responsive image" />
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
      console.log('hej')
      const payload = {
        name: this.userData.email,
        password: this.userData.password
      }
      console.log('hej')
      this.$store.dispatch('authStore/login', payload)
        .then(() => this.$router.push('/'))
        .catch(err => console.log(err))
    }
  }
}
</script>

<style>
  .forgottenPassword {
    font-size: small;
  }
</style>
