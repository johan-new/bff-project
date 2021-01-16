<template>
  <div>
      <b-container fluid="md">
      <b-row class="justify-content-md-center mt-n4">
        <b-col md="8">
    <b-card no-body :key="componentKey" class="shadow-sm" md="8" img-src="https://assets.entrepreneur.com/content/3x2/2000/20150312184504-cool-awesome.jpeg?width=700&crop=2:1" fluid-grow img-alt="Card image" img-top img-height="120">
    <b-avatar size="6rem" class="profileImage"></b-avatar>
      <b-card id="raiseProfile" border-variant="light">
      <h5 class="font-weight-bold mt-n4">{{ username }}</h5>
    <b-button size="sm" class="changeProfile mt-n5" variant="outline-secondary" v-b-modal.modal-1>Ändra profil</b-button>
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
      placeholder="Skriv en kort presentation..."
      rows="2"
      max-rows="3"
      v-model="form.presentation"
    ></b-form-textarea>
      </b-form>
  </b-modal>
    <div class="smaller-text text-secondary font-italic">
      <b-icon icon="geo-alt" aria-hidden="true"></b-icon> {{ data.city }}
    </div>
    <div class="smaller-text text-secondary">Kön: <span>{{ formatGender }},</span> Ålder: <span>{{ data.age }}</span></div>
    <div class="mt-2">{{ data.presentation }}</div>
    {{ componentKey }}
    </b-card>
    <div v-if="!loggedInUser">
      <div v-if="!friendStatus" :key="friends.length">
        <button @click="addFriend">Add friend</button>
        </div>
      <div v-if="friendStatus" :key="friends.length">
        <button @click="removeFriend">Remove friend</button>
      </div>
    </div>
    <Friends v-if="loggedInUser" :friends=friends />
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
      myGender: this.data.gender,
      form: {
        gender: '',
        age: '',
        presentation: '',
        city: ''
      },
      componentKey: 1337
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
      let normal = ''
      if (typeof this.myGender !== 'undefined') {
        const caps = this.myGender
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
    updateProfile () {
      console.log(this.form)
      axios.put('http://localhost:8080/user', {
        presentation: this.form.presentation,
        age: this.form.age,
        gender: this.form.gender,
        city: this.form.city
      })
        .then((data) => this.$emit('update-user', data.data)
        )
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
    updateKey () {
      this.componentKey += 1
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
