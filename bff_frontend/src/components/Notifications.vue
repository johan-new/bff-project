<template>
    <div class="wrapper">
        <h1>Notification component</h1>
        <div v-for="(item, name) of getNotifications" :key="name">
          Name/ID: {{ name }} <br>
          {{ item.type }}
          {{ item.content}}
          {{ item.timestamp }}<br><br>
          <!-- <div v-for="(value) in item" :key="value.type"></div>
          item : {{ value }}<br><br> -->

          <!-- {{ item.item }}
          {{ item.content }}
          {{ item.timestamp }} -->
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
