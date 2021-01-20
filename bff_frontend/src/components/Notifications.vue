<template>
      <div>
        <div v-for="(item, index) of getNotifications" :key="index">
        <div v-for="(notification, name, index) of item" :key="index">
          <b-card>
          Item.id {{ name }}<br><hr>
          Item.type: {{ notification.type }} <br><hr>
          Item.date {{ notification.date }}<br><hr>
          Item.time {{ notification.time }}<br><hr>
          Item.content: {{ notification.content }}<br><hr>
          </b-card>
        </div>
        </div>
    </div>

</template>

<script>
export default {
  name: 'Notifications',
  computed: {
    getNotifications () {
      console.log(this.$store.getters['userStore/getNotifications'])
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
  }
}
</script>
