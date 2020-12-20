<template>
    <div class="wrapper">
        <h1>Userprofile component</h1>
        <div>Användarnamn: {{ username }}</div>
        <p>Användarobjekt visar tidigare spelare matcher samt framtida,
          vilka var med, när va detta, samt användarnamn</p>
        <div>user-objekt: {{ data }}</div><br>
        <div>{{ data.games }}</div>
        <div>
          <h2>Ändra lösenord:</h2>
            <form @submit.prevent="changePassword">
            <input type="password" placeholder="Gammalt lösenord" v-model="oldPassword" />
            <input type="password" placeholder="Nytt lösenord" v-model="newPassword" />
          <button>Ändra lösenord</button>
        </form>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Userprofile',
  data () {
    return {
      oldPassword: '',
      newPassword: ''
    }
  },
  props: ['username', 'data'],
  methods: {
    changePassword () {
      const payload = {
        oldPassword: this.oldPassword,
        newPassword: this.newPassword
      }
      this.$store.dispatch('userStore/changePassword', payload)
    }
  }
}
</script>
