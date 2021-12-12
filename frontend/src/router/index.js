import Vue from 'vue'
import VueRouter from 'vue-router'

import MainPage from '@/views/MainPage.vue'
import ResultPage from '@/views/ResultPage.vue'
import ProfilePage from '../views/ProfilePage.vue'
import DataPage from '../views/DataPage.vue'
import DetailPage from '../views/DetailPage.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'MainPage', component: MainPage },
  { path: '/result', name: 'ResultPage', component: ResultPage },
  { path: '/data', name: 'DataPage', component: DataPage },
  { path: '/detail', name: 'DetailPage', component: DetailPage },
  
  {
    path: '/profile',
    name: 'ProfilePage',
    component: ProfilePage
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
