<template>
  <div>
    <b-jumbotron class="test"
      style="background: transparent !important"
      header-level="1"
      header="Padel Pals."
    >
    <p class="othertest">Sidan för att matcha med andra padelspelare</p>
      <b-button size="sm" variant="outline-light" href="/match">Spela padel
              <b-icon icon="arrow-right-short"></b-icon>
      </b-button>
    </b-jumbotron>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {}
  }
}
</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap');
  @import url('https://fonts.googleapis.com/css?family=Lato:400,700');

.test {
  color: rgb(255, 255, 255);
  font-family: 'Roboto', sans-serif !important;
}
.othertest {
  color: white;
  font-family: 'Lato', sans-serif !important;
}
</style>
