<template>
    <div>
       <b-card class="shadow-sm rounded-0">
        <h6>Vänner:</h6>
         <div v-for="friend of friends" :key="friend">
            <b-avatar size="sm"></b-avatar><router-link :to="{ name: 'Userprofile', params: { username: friend }}" class="smaller-text text-secondary">{{ friend }}</router-link>
         </div>
         </b-card>
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
