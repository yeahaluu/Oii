<template>
  <v-app>
    <Navbar/>
    <v-main style="padding: 25px 0px 0px;">
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from '@/components/common/Navbar'
import "@/css/App.scss"

export default {
  name: 'App',
  components: {
    Navbar
  },
  data: () => ({
    //
  }),
};
</script>
<style>
</style>
