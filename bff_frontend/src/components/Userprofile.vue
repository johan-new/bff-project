<template>
  <div class="wrapper">
    <h1>Userprofile component</h1>
    <div>Användarprofil: {{ username }}</div>
    <h3>
      Spelade och kommande matcher:
    </h3>
    <div v-for="(item, name) in data.games" :key="name">
      {{ name }}:
      {{ item.venue }}, {{ item.players }}, {{ item.id }},
      {{ item.when }}
    </div>
    <div v-if="loggedInUser">
      <h2>Ändra lösenord:</h2>
      <form @submit.prevent="changePassword">
        <input type="password" placeholder="Gammalt lösenord" v-model="oldPassword"/><br>
        <input type="password" placeholder="Nytt lösenord" v-model="newPassword"/><br>
        <button>Ändra lösenord</button>
      </form>
    </div>
    <div v-if="!loggedInUser">
      <div v-if="!friendStatus" :key="friends.length">
        <button @click="addFriend">Add friend</button>
        </div>
      <div v-if="friendStatus" :key="friends.length">
        <button @click="removeFriend">Remove friend</button>
      </div>
    </div>
    <Friends v-if="loggedInUser" :friends=friends />
  </div>
</template>

<script>
import Friends from './Friends'
import axios from 'axios'
export default {
  name: 'Userprofile',
  components: {
    Friends
  },
  data () {
    return {
      oldPassword: '',
      newPassword: '',
      friends: []
    }
  },
  created () {
    this.fetchFriends()
  },
  computed: {
    loggedInUser () {
      const loggedIn = this.$store.getters['authStore/loggedInUser']
      if (loggedIn === this.username) {
        return true
      } else {
        return false
      }
    },
    friendStatus () {
      if (this.friends.includes(this.username)) {
        return true
      } else {
        return false
      }
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
    },
    addFriend () {
      axios.post('http://localhost:8080/friend', {
        username: this.username
      })
        .then(this.fetchFriends)
        .catch(error => {
          console.log(error.response)
        })
    },
    removeFriend () {
      axios.delete('http://localhost:8080/friend', {
        data: { username: this.username }
      })
        .then(this.fetchFriends)
        .catch(error => {
          console.log(error.response)
        })
    },
    fetchFriends () {
      axios.get('http://localhost:8080/friend/all')
        .then(data => {
          this.friends = data.data
        })
        .catch((error) => {
          console.log(error.response)
        })
    }
  }
}
</script>
