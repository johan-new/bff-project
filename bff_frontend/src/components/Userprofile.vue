<template>
  <div>
    <b-container fluid="md" class="px-0">
      <b-row class="justify-content-md-center mt-n4">
        <b-col md="8" class="px-2">
    <b-card md="6" img-src="https://assets.entrepreneur.com/content/3x2/2000/20150312184504-cool-awesome.jpeg?width=700&crop=2:1" fluid-grow img-alt="Card image" img-top img-height="150">
    <b-avatar size="6rem" class="profileImage"></b-avatar>
    <h5 class="mt-n5 font-weight-bold">{{ username }}</h5>
    <div class="smaller-text text-secondary">
      <b-icon icon="geo-alt" aria-hidden="true"></b-icon> {{ data.city }}
    </div>
    <div class="smaller-text text-secondary">Kön: <span>{{ formatGender }},</span> Ålder: <span>{{ data.age }}</span></div>
    <div class="my-3">{{ data.presentation }}</div>
    <hr>
    <div v-if="!loggedInUser">
      <div v-if="!friendStatus" :key="friends.length">
        <button @click="addFriend">Add friend</button>
        </div>
      <div v-if="friendStatus" :key="friends.length">
        <button @click="removeFriend">Remove friend</button>
      </div>
    </div>
    <Friends v-if="loggedInUser" :friends=friends />
    <!-- <hr> -->
    <div v-if="loggedInUser">
      <h6 class="mt-5">Redigera profil</h6>
      <form @submit.prevent="changePassword">
        <input type="password" placeholder="Gammalt lösenord" v-model="oldPassword"/><br>
        <input type="password" placeholder="Nytt lösenord" v-model="newPassword"/><br>
        <button>Ändra lösenord</button>
      </form>
    </div>
    </b-card>
    </b-col>
    </b-row>
    </b-container>
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
      friends: [],
      gender: this.data.gender
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
    },
    formatGender () {
      const caps = this.gender
      const lower = caps.toLowerCase()
      let normal = ''
      normal = lower.charAt(0).toUpperCase() + lower.slice(1)
      if (normal === 'Female') {
        normal = 'Kvinna'
      } else if (normal === 'Male') {
        normal = 'Man'
      } else {
        normal = 'ICKE CIS'
      }
      return normal
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

<style>
 .smaller-text {
   font-size: small;
 }
 .profileImage {
  position: absolute;
  top: -70px;
  right: 0;
  left: 0px;
  width: auto;
  height: auto;
  border: 4px solid white;
}
 .profileImage:hover {
   border: 3px solid white;
 }
 .parent {
  position: relative;
 }
</style>
