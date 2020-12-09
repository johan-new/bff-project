<template>
    <header>
        <div class="logo">
            <router-link to="/" class="logo">Client</router-link>
        </div>
        <div class="main-navigation">
            <router-link to="/login">Login</router-link>
            <router-link to="/register">Register</router-link>
            <router-link to="/secure">Secure</router-link>
        </div>
    </header>
</template>

<script>
export default {
  name: 'Header-component'
}
</script>
