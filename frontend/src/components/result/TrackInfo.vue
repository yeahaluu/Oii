<template>
  <div>
    <!-- <div class="carousel-item">
      <h3>Location</h3>
      <h4>user1</h4>
      <h5>user2</h5>
      <h6>{{ Info }}</h6>
    </div> -->
    <!-- <div style="width:700px; text:center; justify-content:center; position:relative; left:300px; bottom 1000px;">
      {{ trackInfo }}

    </div> -->
  <v-sheet
    class="mx-auto"
    elevation="0"
    max-width="1300"
  >
    <v-slide-group
      v-model="model"
      class="pa-4"
      active-class="green lighten-5"
      show-arrows
    >
      <v-slide-item
        v-for="n in userInfo.length"
        :key="n"
        v-slot="{ active }"
        class="carousel-item"
      >
        <v-card
          :color="active ? undefined : 'green lighten-5'"
          class="ma-4"
          height="200"
          width="320"
          @click="showTrackDetail(n)"
        >
        <div style="padding-top: 10px; font-size: 20px;">
          <v-icon
          :color="getcolor(n-1)"
          x-large
          left
          right>
            mdi-map-marker-path
          </v-icon>
          {{userInfo[n-1].name}}
        </div>
          <!-- {{trackInfo[n-1]}} -->
          <v-row
            class="fill-height"
            align="center"
            justify="center"
          >
          <p>출발: {{ userInfo[userInfo.length - n].location }}</p>
          <p v-if="trackInfo[userInfo.length - n].lastEndStation">목적지: {{ trackInfo[userInfo.length - n].lastEndStation }}</p>
          <p v-if="trackInfo[userInfo.length - n].totalTime">소요시간 :  {{ trackInfo[userInfo.length - n].totalTime }}분</p>
          <p v-if="trackInfo[userInfo.length - n].totalDistance">거리 :  {{ trackInfo[userInfo.length - n].totalDistance }}m</p>
          <!-- <p v-if="trackInfo[n-1].totalTime">{{userInfo[n-1].name}}님은 {{trackInfo[n-1].lastEndStation}} 도착까지 {{ trackInfo[n-1].totalTime }}분 소요됩니다.</p> -->
          <p v-else>{{trackInfo[n-1].msg}}</p>
          <!-- {{ trackInfo[n].totalTime }} -->
          <!-- {{ Info[n] }} -->
            <!-- <v-scale-transition>
              <v-icon
                v-if="active"
                color="white"
                size="48"
                v-text="'mdi-close-circle-outline'"
              ></v-icon>
            </v-scale-transition> -->
          </v-row>
        </v-card>
      </v-slide-item>
    </v-slide-group>
  </v-sheet>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'TrackInfo',
  props: {
  },
  data() {
    return {
      model: null,
      colors: ['#6478FF', '#48DAD2', '#FF7E9D', '#50508C', '#FF5A5A']
    }
  },
  computed: {
    ...mapGetters(['trackInfo', 'userInfo', 'centerStation']),
  },
  methods: {
    getcolor(n) {
      return this.colors[n]
    },
    showTrackDetail(n) {
      // console.log('showTrackDetail', n-1)
      this.$emit('show-track-detail', n-1)
    }
  },
  mounted() {
    // console.log('ddddddddddddddd', this.centerStation)
    console.log('현재유저', this.userInfo)
    console.log('트랙정보', this.trackInfo)
  }
}
</script>

<style scoped>
/* .carousel-item {
  width: 320px;
  height: 200px;
  background-color: rgba(41, 177, 36, 0.4);
  color: black;
  padding-left: 10px;
  border-radius: 10px;
} */
.v-application p {
  margin-bottom: 10px;
  font-family: 'Jua', sans-serif;
}
.theme--light.v-sheet {
  background-color: rgba(255, 255, 255, 0);
  border-color: #FFFFFF;
  color: rgba(0, 0, 0, 0.87);
}
.v-application .light-green.accent-3 {
  background-color: none !important;
  border-color: blue !important;
  border: 2px solid green !important;
}
.v-sheet.v-card {
  border-radius: 15px;
}
/* .carousel-item {
  overflow-y: scroll;
  overflow-x: hidden;
} */
.row {
  /* align-content: center; */
  flex-direction: column;
  margin: -25px;
}
</style>