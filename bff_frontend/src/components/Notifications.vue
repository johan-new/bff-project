<!-- <template>
    <div class="wrapper">
        <h1>Notification component</h1>
        <div v-for="(item, name) of getNotifications" :key="name">
          Name/ID: {{ name }} <br>
          Item.type: {{ item.type }} <br>
          Item.content: {{ item.content}} <br>
          Item.timestamp: {{ item.timestamp }}<br><br>
        </div>
    </div>
</template> -->

<template>
  <div>
        <b-toast id="example-toast" title="BootstrapVue" static no-auto-hide>
      Hello, world! This is a toast message.
    </b-toast>
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
