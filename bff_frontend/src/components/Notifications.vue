<template>
      <div>
        <!-- <p>Dina senaste notifikationer</p> -->
        <div v-for="(item, name) of getNotifications" :key="name">
          Name/ID: {{ name }} <br>
          Item.type: {{ item.type }} <br>
          Item.content: {{ item.content}} <br>
          Item.timestamp: {{ item.timestamp }}<br><br>
        </div>
    </div>

</template>

<script>
export default {
  name: 'Notifications',
  computed: {
    getNotifications () {
      const yolo = this.$store.getters['userStore/getNotifications']
      console.log(yolo)
      console.log(yolo[0])
      return yolo[0]
    }
  },
  created () {
    this.$store.dispatch('userStore/notifications')
  }
}
</script>
