<template>
  <div>
      <b-container fluid="md">
      <b-row class="justify-content-md-center mt-n4">
        <b-col md="8">
    <b-card no-body :key="componentKey" class="shadow-sm" md="8" img-src="https://assets.entrepreneur.com/content/3x2/2000/20150312184504-cool-awesome.jpeg?width=700&crop=2:1" fluid-grow img-alt="Card image" img-top img-height="120">
    <b-avatar size="6rem" class="profileImage"></b-avatar>
      <b-card id="raiseProfile" border-variant="light">
      <h5 class="font-weight-bold mt-n4">
        {{ userprofile.username }}</h5>
    <b-button pill size="sm" class="changeProfile mt-n5" variant="outline-secondary" v-if="loggedInUser" v-b-modal.modal-1>Ändra profil</b-button>
      <b-modal id="modal-1" title="Ändra profil" @ok="updateProfile">
            <b-form @submit="updateProfile">
              <div class="form-row">
              <div class="form-group">
            <b-form-group
          id="profileCity"
          label="Stad:"
          label-for="cityInput"
        >
          <b-form-input
            id="cityInput"
            type="text"
            required
            v-model="form.city"
          >
          </b-form-input>
        </b-form-group>
              </div></div>
                        <div class="form-row">
            <div class="form-group">
        <b-form-group
          id="profileAge"
          label="Ålder:"
          label-for="ageInput"
        >
          <b-form-input
            id="ageInput"
            type="number"
            required
            v-model.number="form.age"
          >
          </b-form-input>
        </b-form-group>
            </div>
    <div class="ml-3">
      <label for="gender">Ange kön</label>
      <select class="custom-select" id="gender" required v-model="form.gender">
        <option selected disabled value="">Kön...</option>
            <option :value="'male'">Man</option>
    <option :value="'female'">Kvinna</option>
    <option :value="'nonbinary'">Annat</option>
      </select>
      </div>
      </div>
            <b-form-textarea class="form-group ml-n1"
      id="textarea"
      placeholder=""
      rows="2"
      max-rows="3"
      v-model="form.presentation"
    ></b-form-textarea>
      </b-form>
  </b-modal>
    <div class="smaller-text text-secondary font-italic">
      <b-icon icon="geo-alt" aria-hidden="true"></b-icon>
      {{ userprofile.city }}
    </div>
    <div class="smaller-text text-secondary">Kön: <span>
      {{ formatGender }},
      </span> Ålder: <span>
      {{ userprofile.age }}
      </span></div>
    <div class="mt-2">
      {{ userprofile.presentation }}
      </div>
      <div>
        {{ $route.params }}
      </div>
    </b-card>
    <div v-if="!loggedInUser">
    <b-card>
      <div v-if="!friendStatus" :key="friends.length">
        <b-button @click="addFriend" variant="outline-secondary" size="sm">Lägg till vän</b-button>
        </div>
      <div v-if="friendStatus" :key="friends.length">
        <b-button @click="removeFriend" variant="outline-secondary" size="sm">Ta bort vän</b-button>
      </div>
      </b-card>
    </div>
    <Friends v-if="loggedInUser" :friends=friends @em="doThis"/>
    <div v-if="loggedInUser" class="my-3">
        <b-button v-b-toggle.collapse-1337 variant="link" size="sm">
          Ändra lösenord <b-icon icon="arrow-down-short" aria-hidden="true"></b-icon></b-button>
  <b-collapse id="collapse-1337" class="mt-2">
      <b-card class="rounded-0">
      <h6>Byt lösenord</h6>
      <b-form @submit.prevent="changePassword">
        <b-form-group
          id="oldPassword"
          label="Gammalt lösenord"
          label-for="oldPasswordInput"
        >
          <b-form-input
            id="oldPasswordInput"
            type="text"
            required
            v-model="oldPassword"
          >
          </b-form-input>
          </b-form-group>
                  <b-form-group
          id="newPassword"
          label="Nytt lösenord"
          label-for="newPasswordInput"
        >
          <b-form-input
            id="newPasswordInput"
            type="text"
            required
            v-model="newPassword"
          >
          </b-form-input>
          </b-form-group>
        <b-button variant="outline-secondary" size="sm">Spara ändringar</b-button>
      </b-form>
      </b-card>
      </b-collapse>
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
      form: {
        gender: '',
        age: '',
        presentation: '',
        city: ''
      },
      userprofile: {},
      componentKey: 1337
    }
  },
  created () {
    this.fetchFriends()
    this.fetchUser()
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
      let normal = ''
      if (typeof this.userprofile.gender !== 'undefined') {
        const caps = this.userprofile.gender
        const lower = caps.toLowerCase()
        normal = lower.charAt(0).toUpperCase() + lower.slice(1)
        if (normal === 'Female') {
          normal = 'Kvinna'
        } else if (normal === 'Male') {
          normal = 'Man'
        } else {
          normal = 'Varken eller'
        }
      } else {
        normal = ''
      }
      return normal
    }
  },
  props: ['username', 'data'],
  methods: {
    doThis (friend) {
      const username = friend
      axios.get('http://localhost:8080/user', {
        params: { username }
      })
        .then((data) => {
          this.userprofile = data.data
        })
        .catch((error) => {
          console.log(error.response)
        })
    },
    updateProfile () {
      axios.put('http://localhost:8080/user', {
        presentation: this.form.presentation,
        age: this.form.age,
        gender: this.form.gender,
        city: this.form.city
      })
        .then(() => this.$store.dispatch('userStore/fetchUserprofile'))
    },
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
    },
    fetchUser () {
      const username = this.username
      axios.get('http://localhost:8080/user', {
        params: { username }
      })
        .then((data) => {
          this.userprofile = data.data
        })
        .catch((error) => {
          console.log(error.response)
        })
    }
  },
  watch: {
    $route (to, from) {
      this.doThis(to.params.username)
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
  top: -45px;
  right: 0;
  left: 3%;
  width: auto;
  height: auto;
  border: 4px solid white;
}
 .profileImage:hover {
   border: 3px solid white;
 }
 .changeProfile {
   position: absolute;
   top: 10px;
   right: 0px;
   /* left: 350px; */
   left: 75%;
   width: auto;
   height: auto;
 }
 #raiseProfile {
  margin-top: -2.5rem !important;
}
</style>
