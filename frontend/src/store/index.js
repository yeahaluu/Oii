import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
    userInfo: [],
    trackInfo: [],
    centerStation: [],
    visualizedData: [],
    clickVisual: 1,
    onClickCafeteria: false,
  },

  getters: {
    userInfo(state) {
      return state.userInfo
    },
    trackInfo(state) {
      return state.trackInfo
    },
    centerStation(state) {
      return state.centerStation
    },
    visualizedData(state) {
      return state.visualizedData
    },
    clickVisual(state) {
      return state.clickVisual
    },
    cafeteriaInfo(state) {
      return state.cafeteriaInfo
    },
    onClickCafeteria(state) {
      return state.onClickCafeteria
    }
  },

  mutations: {
    SET_USER_INFO: function(state, data) {
      state.userInfo = data
    },
    ADD_TRACK_INFO: function(state, data) {
      state.trackInfo = (data)
    },
    FETCH_CENTER_STATION: function(state, data) {
      state.centerStation = data
    },
    SET_VISUALIZED_DATA: function(state, data) {
      state.visualizedData = data
    },
    SET_CLICK_VISUAL: function(state, data) {
      state.clickVisual = data
    },
    SET_CLICK_CAFETERIA: function(state, data) {
      state.onClickCafeteria = data
    }
  },

  actions: {
    setUserInfo: function({ commit }, data) {
      commit('SET_USER_INFO', data)
    },
    addTrackInfo: function({commit}, data) {
      commit('ADD_TRACK_INFO', data)
    },
    fetchCenterStation: function({commit}, data) {
      commit('FETCH_CENTER_STATION', data)
    },
    setVisualizedData: function({commit}, data) {
      commit('SET_VISUALIZED_DATA', data)
    },
    setClickVisual: function({commit}, data) {
      commit('SET_CLICK_VISUAL', data)
    },
    setClickCafeteria: function({commit}, data) {
      commit('SET_CLICK_CAFETERIA', data)
    }
  },

  modules: {
  },
})
