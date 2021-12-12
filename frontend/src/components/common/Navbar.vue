<template>
  <div>
    <div class="navbar">
      <v-app-bar
        app
        color="#55B96E"
        height=50
      >
        <v-layout class="d-flex justify-space-between align-center">
          <!-- <div>
            <v-icon @click="toggleNavbar">mdi-chevron-up</v-icon>
          </div> -->
          <div @click="clickHome" class="HomeBtn">
            <button>
              Oii
            </button>
          </div>
          <div>
            <!-- <v-btn
              @click="clickLogin"
              elevation="1"
              small
            ><p class="login-text">Login</p>
            </v-btn> -->
          </div>
        </v-layout>
      </v-app-bar>
    </div>
    <div>
      <v-icon 
        @click="toggleNavbar"
        class="down-icon navbar-down__icon"
      >
      mdi-chevron-down
      </v-icon>
    </div>
  </div>
</template>

<script>
import "@/css/common/Navbar.scss"

export default {
  name: 'Navbar',
  data: function () {
    return {
    }
  },
  methods: {
    toggleNavbar: function () {
      const navbar = document.querySelector('.navbar')
      if (navbar.style.display == "none") {
        navbar.style.display = "block"
      } else {
        navbar.style.display = "none"
      }
    },
    clickHome: function () {
      this.$router.push({ name: 'MainPage'})
    },
    clickLogin: function () {
      
    },
  },
}
</script>