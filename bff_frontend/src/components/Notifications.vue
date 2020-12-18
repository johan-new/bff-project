<template>
    <div class="wrapper">
        <h1>Notification component</h1>
        <button @click="getNotification">Notiser</button>
        <div v-for="(item, name) of getNotifications" :key="name">
          {{ name }} <br>
          {{ item.type }}
          {{ item.content }}
          {{ item.timestamp }}
        </div>
    </div>
</template>

<script>
export default {
  name: 'Notifications',
  computed: {
    getNotifications () { return this.$store.getters['userStore/getNotifications'] }
  },
  methods: {
    getNotification () {
      this.$store.dispatch('userStore/notifications')
    }
  }
}
</script>
