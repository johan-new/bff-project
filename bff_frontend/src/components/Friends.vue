<template>
    <div>
       <b-card class="rounded-0">
        <!-- <h6 class="text-center">Vänner</h6> -->
            <b-list-group class="m-n3">
               <b-list-group-item class="font-weight-bold">Vänner</b-list-group-item>
         <div v-for="friend of friends" :key="friend">
               <b-list-group-item button @click="fetchUser(friend)" class="smaller-text text-secondary">
            <b-avatar size="sm" class="mr-2"></b-avatar>{{ friend }}
               </b-list-group-item>
         </div>
            </b-list-group>
         </b-card>
    </div>
</template>

<script>
export default {
  name: 'Friends',
  props: ['friends'],
  methods: {
    fetchUser (friend) {
      this.$store.dispatch('userStore/fetchUser', friend)
        .then(() => {
          this.$router.push({
            name: 'Userprofile',
            params: { username: friend }
          })
        })
        .catch((error) => {
          console.log(error.response)
        })
    }
  }
}
</script>
