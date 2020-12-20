<template>
    <div class="wrapper">
        <h1>Friends component</h1>
         <div>
            {{ friends }}
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
