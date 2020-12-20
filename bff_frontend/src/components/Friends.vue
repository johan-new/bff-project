<template>
    <div class="wrapper">
        <h1>Friends component</h1>
    </div>
</template>

<script>
export default {
  name: 'Friends',
  data () {
    return {
      user: '',
      oldPassword: '',
      newPassword: ''
    }
  },
  created () {
    this.$store.dispatch('userStore/fetchUserprofile')
  },
  methods: {
    
  }
}
</script>
