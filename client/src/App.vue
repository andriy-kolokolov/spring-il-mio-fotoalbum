<script>
import MsHeader from "./components/MsHeader.vue";

export default {
  name: 'App',
  components: {
    MsHeader
  }
}
</script>

<template>

  <ms-header/>

  <main>
    <router-view/>
  </main>


</template>

<style>

</style>
