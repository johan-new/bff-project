<template>
    <div class="wrapper">
        <h1>Friends component</h1>
         <div v-for="friend of friends" :key="friend">
            {{ friend }}
         </div>
    </div>
</template>

<script>
export default {
  name: 'Friends',
  props: ['friends'],
  methods: {
  }
}
</script>
