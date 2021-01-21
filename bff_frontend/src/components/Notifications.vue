<template>
      <div>
        <b-list-group>
        <div v-for="(item, index) of getNotifications" :key="index">
        <div v-for="(notification, name, index) of item" :key="index">
          <b-list-group-item :to="routerPath" class="flex-column align-items-start overflowNo">
          <b-row align-h="between smaller-text text-secondary" class="mb-2">
            <b-col>{{ notification.time }}</b-col>
            <b-col class="text-right">{{ notification.date }}</b-col>
          </b-row>
          <div>{{ notification.type }}</div>
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
    },
    routerPath () {
      const notifications = this.$store.getters['userStore/getNotifications']
      let items = ''
      let item = ''
      for (items in Object.values(notifications)) {
        console.log(notifications[items])
        for (item of Object.values(notifications[items])) {
          console.log(typeof item.type)
          if (item.type.includes('JOIN')) {
            return 'padel'
          } else if (item.type === 'GAME_CREATED') {
            return 'games'
          } else {
            return '/'
          }
        }
      }
      return this.$store.getters['userStore/getNotifications']
    }
  },
  methods: {
    notifications () {
      this.$store.dispatch('userStore/notifications')
    }
  },
  created () {
    this.notifications()
  },
  watch: {
    $route (to, from) {
      console.log('Hämtar notiser!')
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
