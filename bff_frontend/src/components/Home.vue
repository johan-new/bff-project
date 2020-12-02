<template>
    <div class="wrapper">
        <h1>Home component</h1>
        <h2>{{ this.$store.state.authStore.test }}</h2>
    </div>
</template>

<script>
export default {
  name: 'Home'
}
</script>
