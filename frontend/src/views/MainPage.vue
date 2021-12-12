<template>
  <v-container>
    <div class="main-text">
      <h1 class="title">Oii</h1>
      <h2 class="subTitle">오이! 오이갈까?</h2>
      <p class="content">드디어 밥 한번 먹기로 했는데 어디가지? 친구와 중간에서 만나보세요!</p>
    </div>
    <CreateUserForm
      v-for="(info, idx) in infos"
      :key="idx"
      :info="info"
    />
    <div>
      <v-btn 
        icon 
        color="#0FA958"
        class="plus-btn"
        @click="clickPlusBtn"
      >
        <v-icon>mdi-plus-circle-outline</v-icon>
      </v-btn>
    </div>
    <div class="find-btn">
      <v-btn
        @click="clickFind"
        elevation="1"
      ><p class="find-text">찾기</p>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import "@/css/MainPage.scss"
import CreateUserForm from "@/components/main/CreateUserForm"

export default {
  name: 'MainPage',
  components: {
    CreateUserForm
  },

  data: function(){
    return {
      infos: [],
    }
  },
  methods: {
    clickPlusBtn() {
      var info = new Object();
      info = {
        name: '',
        location: '',
        age: '',
        gender: '',
      }
      this.infos.push(info)
    },
    clickFind() {
      this.$store.dispatch('setUserInfo', this.infos)
      this.$router.push({name: 'ResultPage'})
    }
    
  },

  mounted() {
    var info = new Object();
      info = {
        name: '',
        location: '',
        age: '',
        gender: '',
      }
    this.infos.push(info)
  }
}
</script>
