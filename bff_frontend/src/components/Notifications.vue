<template>
      <div>
        <b-list-group>
        <div v-for="(item, index) of getNotifications" :key="index">
        <div v-for="(notification, name, index) of item" :key="index">
          <b-list-group-item button @click="notificationRoute(notification.type)" class="flex-column align-items-start overflowNo">
          <!-- <b-row fluid align-h="between" class="mb-2 smaller-text text-secondary"> -->
                <div class="d-flex w-100 justify-content-between">
      <div class="mb-1">{{ notification.time }}</div>
      <small>{{ notification.date }}</small>
    </div>
          <!-- <b-row class="mb-2"> -->
            <!-- <b-col>{{ notification.time }}</b-col>
            <b-col class="text-right">{{ notification.date }}</b-col>
          </b-row> -->
          <div>{{ notification.content }}</div>
          </b-list-group-item>
        </div>
        </div>
        </b-list-group>
    </div>
</template>

<script>
export default {
  name: 'Notifications',
  computed: {
    getNotifications () {
      return this.$store.getters['userStore/getNotifications']
    }
  },
  methods: {
    notifications () {
      this.$store.dispatch('userStore/notifications')
    },
    notificationRoute (route) {
      let destination = ''
      if (route.includes('REQUEST')) {
        destination = 'Padel'
      } else if (route.includes('GAME')) {
        destination = 'Games'
      } else {
        destination = 'Home'
      }
      this.$router.push({
        name: destination
      }).catch(error => {
        if (error.name !== 'NavigationDuplicated' &&
            !error.message.includes('Avoided redundant navigation to current location')) {
          console.log(error)
        }
      })
    }
  },
  created () {
    this.notifications()
  },
  watch: {
    $route (to, from) {
      this.notifications()
    }
  },
  data () {
    return {
    }
  }
}
</script>

<style>
  .overflowNo {
    word-wrap: break-word !important;
  }
</style>
