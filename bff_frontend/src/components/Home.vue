<template>
    <div class="wrapper">
        <h1>Home component</h1>
        <p>Logged in user: {{ this.$store.state.authStore.email }}</p>
    </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {

    }
  }
}
</script>
