<template>
    <div class="wrapper">
        <h1>Friends component</h1>
         <!-- <div v-for="friend of friends" :key="friend">
            {{ friend }}
         </div> -->
         <div v-for="friend of friends" :key="friend">
            <router-link :to="{ name: 'Userprofile', params: { username: friend }}">{{ friend }}</router-link>
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
