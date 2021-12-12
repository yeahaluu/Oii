<template>
  <div id="result-page">
    <div id="map"></div>
    <div id="carousel" v-if="onclick">
      <TrackInfo @show-track-detail="showTrackDetail"/>
    </div>
    <DataForm v-if="onclick" />
    <v-btn v-if="onclick" class="reload-bound" @click="setBounds()">지도 범위 재설정 하기</v-btn> 
  </div>
  
</template>
<!--카카오 지도 API-->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b5e1900554a89d3c0a959cad71852fc6&libraries=services"></script>

<script>
import "@/css/ResultPage.scss"
import { mapGetters } from 'vuex'
import TrackInfo from '@/components/result/TrackInfo.vue'
import DataForm from '@/components/result/DataForm.vue'
import axios from 'axios'
import cafeInfo from '@/assets/seo.json'
export default {
  name: 'ResultPage',
  data() {
    return {
      map: null,
      latlng: [],
      userPosition: [],
      centerPosition: {
        lat: '',
        lng: '',
      },
      onclick: false,
      polylineLst: [[],[],[],[],[]],
      trackInfoLst: [],
      timeLst: [],
      responseData: null,
      cafeteriaMarker: [],
      cafeInformation: cafeInfo,
      pickedCafereria: ["금요일","마감","일정","주","좀","맘","수","이번","더위","좀","수","몸","컨디션","영","별루","오늘","포스트","동네","맛집","프로젝트","케케케","지금","동네","맛집","프로젝트","현재","개","업","소개","숫자","소개","곳","하나","스시","서울","서초구","서초대로","길","층","매일","점심","매일","저녁","제일","스시","주말","저녁","하나","스시","내방역","가까이","위치","근처","처음","마카","세로","회","초밥","내방역","오픈","하나","스시","곳","전화","먼저","예약","시간","도착","나름","내방역","맛집","하나","스시","내방역","방배동","스시","하나","스시","쿠팡","츠","배달","런치","마카","세","근처","곳","곳","스시","집","걸","발견","오","퀄리티","날씨","또","맨","얼마","전","외식","제","입","꼬리","아주","귀","디","내방역","하나","스시","곳","갔숩니","동네","일본","얼마","전","앞","스시","집","얼마","전","친구","릴드당스","놀러와","방문","볼","수","생각","코앞","곳","자주","심리","랄","왜","이제","방","고지","저","주로","혼자","밥","사실","마음","신나요","오늘","친구","사치","위치","내방역","번","번","출구","방배동","맛","객인","친구","덕분","런치","마카","세","맛","가성","비도","오픈","한지","얼마","후기","것","하나","스시","매일","점심","매일","저녁","사진","출처","지토","규모","요즘","내방역","맛집","포스팅","자주","짓","내방역","일본","하나","스시","오늘","소개","맛집","내방역","바로","근처","하나","스시","일식","직","큰길","골목","일본","듯"],
      isPicked: false,
    }
  },
  components: {
    TrackInfo,
    DataForm
  },
  computed: {
    ...mapGetters(['userInfo', 'centerStation', 'trackInfo']),

  },
  methods: {
    // 카카오 지도 생성
    loadMap: function(){
      
      // 주소-좌표 변환 객체
      var geocoder = new kakao.maps.services.Geocoder();
      // 마커 이미지/크기 설정 및 생성
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
      var imageSize = new kakao.maps.Size(24, 35);
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
      // 유저의 주소로 좌표를 검색합니다
      var cnt = this.userInfo.length-1
      for(var i=this.userInfo.length-1; i>=0; i--){
        // this.userInfo[i].location : 유저의 주소
        geocoder.addressSearch(this.userInfo[i].location, (result) => {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          // console.log('dddd', result[0].y, result[0].x)
          // 해당 유저좌표 저장
          this.userPosition.push({lat: result[0].y, lng: result[0].x})
          this.latlng.push(coords)
          // 좌표위치에 마커를 생성
          var marker = new kakao.maps.Marker({
              map: this.map, // 마커를 표시할 지도
              position: coords, // 마커를 표시할 위치
              image : markerImage // 마커 이미지 
          });
          var iwContent = '<div style="padding:5px; height: 75px; border: 1px solid rgb(118, 129, 168);">' + this.userInfo[cnt].name + '<br>' + this.userInfo[cnt].location +'</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
              iwPosition = new kakao.maps.LatLng(result[0].y, result[0].x), //인포윈도우 표시 위치입니다
              iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
          
          if (cnt >= 0) {
            cnt -= 1
          }

          // 인포윈도우를 생성하고 지도에 표시합니다
          
          kakao.maps.event.addListener(marker, 'click', () => {
            var infowindow = new kakao.maps.InfoWindow({
              position : iwPosition, 
              content : iwContent,
              removable : iwRemoveable,
              zIndex : 2,
            });
            infowindow.open(this.map, marker); 
          })
        });
      }
    },
    // 중심지점찾기
    searchCenter: function() {
      // 주소->좌표변환 객체
      var geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(this.userInfo[this.userInfo.length-1].location, (result) => {
        // 가운데 지점(무게중심)
        var x_loc = 0.0
        var y_loc = 0.0
        for(var j=0; j<this.latlng.length; j++) {
          y_loc += this.latlng[j].La
          x_loc += this.latlng[j].Ma
        }
        x_loc /= j
        y_loc /= j
        // 중간지점 갱신
        this.centerPosition.lat = x_loc
        this.centerPosition.lng = y_loc
        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        var loc = new kakao.maps.LatLng(x_loc, y_loc);
        this.map.setCenter(loc);
        // 무게중심 좌표 서버로 보내기
        const url = 'http://placekitten.com//200/300'
        // const url = 'http://localhost:8080/map'
        // axios({
        //   url: url,
        //   method: 'get',
        //   headers: {
        //     "Content-Type": "application/json",
        //   },
        //   data: {
        //     userPositionLst: [this.centerPosition.lat, this.centerPosition.lng]
        //   }
        // })
        axios.get(url)
          // 중간지점에 해당하는 지하철 역 좌표 데이터
          .then(res => {
            // 지하철역 좌표 데이터
            this.$store.dispatch('fetchCenterStation', [
              // {lat:this.centerPosition.lat, lng:this.centerPosition.lng},
              {name: '사당역', lat: 37.4767992, lng: 126.9816245},
              {name: '이수역', lat: 37.48658247, lng: 126.981745},
              {name: '방배역', lat: 37.48130245, lng: 126.997636},
              {name: '내방역', lat: 37.4875118, lng: 126.9932223},
            ])
          })
          .catch(err => console.log(err))
        // 1 지하철역
        // for (let i=0; i<this.centerStation.length; i++) {
        //   // 유저위치
        //   for (let j=0; j<this.latlng.length; j++) {
        //     // 시작좌표(유저)
        //     var sx = this.latlng[j].La
        //     var sy = this.latlng[j].Ma
        //     // 도착좌표(지하철역)
        //     var ex = this.centerStation[i].lng
        //     var ey = this.centerStation[i].lat
        //     // 각 지하철역까지 걸리는 시간을 확인하기 위한 함수
        //     this.setCenter(sx, sy, ex, ey, i, j)
        //   }
        // }

        // 2 소요시간 리스트 내용 추가하기
        // for (let k=0; k<this.timeLst.length; k++) {
        //   console.log(k)
        //   console.log(this.timeLst[k])
        //   // const userCnt = this.userInfo.length
        //   // this.timeLst[i] /= userCnt
        //   this.centerStation[k].totalTime = this.timeLst[k] // this.timeLst[i] => undefined
        // }
        // const list = this.centerStation
        // list.sort((f, s) => f.totalTime - s.totalTime)

        // 중간지점에 마커만들기
        var imageSrc = "https://www.pngarts.com/files/3/Map-Marker-Pin-PNG-Transparent-Image.png";
        // for (let i=0; i<this.centerStation.length; i++) {
        for (let i=0; i<this.centerStation.length; i++) {
          var loc = new kakao.maps.LatLng(this.centerStation[i].lat, this.centerStation[i].lng);
          
          this.map.setCenter(loc);
          var imageSize2 = new kakao.maps.Size(40, 45); 
          var markerImage2 = new kakao.maps.MarkerImage(imageSrc, imageSize2); 
          var marker = new kakao.maps.Marker({
              map: this.map, // 마커를 표시할 지도
              position: loc, // 마커를 표시할 위치
              image : markerImage2, // 마커 이미지
          });
          
          // 중간지점마커를 클릭했을 경우의 이벤트
          kakao.maps.event.addListener(marker, 'click', () => {
            const url = 'http://placekitten.com//200/300'
            axios.get(url)
            // axios.get({
            //   url: url,
            //   headers: {
            //     "Content-Type": "application/json",
            //   },
            //   data: {
            //     stationName: this.centerStation[i],
            //     lat: this.centerStation[i].lat,
            //     lng: this.centerStation[i].lng
            //   }
            // })
              .then(res => {
                console.log(i, '번째 중심지점 부근의 음식점 정보 받아오기')
                if (this.onclick === true && i === 0) {
                  this.responseData = this.cafeInformation.사당역
                  
                  // 지하철 역 주변에 존재하는 음식점 마커 생성
                  var markers = []
                  // var imageSrc = "https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/pin_sample(xxxs).png";
                  var imageSrc = "https://cdn-icons-png.flaticon.com/128/1946/1946401.png";
                  // console.log(this.responseData[0].cafeteria.length)
                  for (let i=0; i<15; i++) {
                    // console.log(i, responseData[0].cafeteriaLst[i].position.lat, responseData[0].cafeteriaLst[i].position.lng)
                    var loc = new kakao.maps.LatLng(this.responseData[0].cafeteria[i].lon, this.responseData[0].cafeteria[i].lat);
                    
                    var imageSize2 = new kakao.maps.Size(30, 35); 
                    var markerImage2 = new kakao.maps.MarkerImage(imageSrc, imageSize2); 
                    var cafemarker = new kakao.maps.Marker({
                        map: this.map, // 마커를 표시할 지도
                        position: loc, // 마커를 표시할 위치
                        image : markerImage2, // 마커 이미지
                    });
                    markers.push(cafemarker)
                    kakao.maps.event.addListener(cafemarker, 'click', () => {
                      this.$store.dispatch('setVisualizedData', this.responseData[0].cafeteria[i])
                      this.pickedCafereria = this.responseData[0].cafeteria[i]
                      this.isPicked = true
                      this.$store.dispatch('setClickVisual', 2)
                      this.$store.dispatch('setClickCafeteria', true)
                      // this.pickedCafeteria = this.responseData[0].cafeteria[i]
                    })
                    // var infowindow = new kakao.maps.InfoWindow();
                    // kakao.maps.event.addListener(cafemarker, 'mouseover', () => {
                    //   console.log(this.responseData[0].cafeteria[i].lon, this.responseData[0].cafeteria[i].lat)
                    //   const position = new kakao.maps.LatLng(this.responseData[0].cafeteria[i].lon, this.responseData[0].cafeteria[i].lat);
                    //   console.log(position)
                    //   var content = '<div style="padding:5px;z-index:2;">' + this.responseData[0].cafeteria[i].name + '</div>';
                    //   infowindow.setContent(content);
                    //   infowindow.setPosition(position);
                    //   infowindow.open(this.map, cafemarker);
                    // });

                    // kakao.maps.event.addListener(cafemarker, 'mouseout', () => {
                    //     infowindow.close();
                    // });

                    // var content = '<div style="padding:5px;z-index:2;">' + this.responseData[0].cafeteria[i].name + '</div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                    //      //인포윈도우 표시 위치입니다
                    // var iwPosition = new kakao.maps.LatLng(this.responseData[0].cafeteria[i].lon, this.responseData[0].cafeteria[i].lat)
                    // var infowindow = new kakao.maps.InfoWindow({
                    //   position : iwPosition,
                    //   content : content,
                    // });
                    // // 인포윈도우를 생성하고 지도에 표시합니다
                    
                    // kakao.maps.event.addListener(cafemarker, 'mouseover', () => {
                    //   console.log('ddddddddddd')              
                    //   infowindow.open(this.map, cafemarker); 
                    // })
                    // kakao.maps.event.addListener(cafemarker, 'mouseout', () => {
                    //   infowindow.close()
                    // })
                  }
                  this.cafeteriaMarker = markers
                } else if (this.onclick === true && i === 1) {
                  this.responseData = this.cafeInformation.이수역
                  
                  // 지하철 역 주변에 존재하는 음식점 마커 생성
                  var markers = []
                  var imageSrc = "https://cdn-icons-png.flaticon.com/128/1946/1946401.png";
                  console.log(this.responseData[0].cafeteria.length)
                  for (let i=0; i<15; i++) {
                    // console.log(i, responseData[0].cafeteriaLst[i].position.lat, responseData[0].cafeteriaLst[i].position.lng)
                    var loc = new kakao.maps.LatLng(this.responseData[0].cafeteria[i].lon, this.responseData[0].cafeteria[i].lat);
                    
                    var imageSize2 = new kakao.maps.Size(30, 35); 
                    var markerImage2 = new kakao.maps.MarkerImage(imageSrc, imageSize2); 
                    var cafemarker = new kakao.maps.Marker({
                        map: this.map, // 마커를 표시할 지도
                        position: loc, // 마커를 표시할 위치
                        image : markerImage2, // 마커 이미지
                    });
                    markers.push(cafemarker)
                    kakao.maps.event.addListener(cafemarker, 'click', () => {
                      // console.log('음식점 마커 클릭')
                      this.$store.dispatch('setVisualizedData', this.responseData[0].cafeteria[i])
                      this.pickedCafereria = this.responseData[0].cafeteria[i]
                      this.isPicked = true
                      this.$store.dispatch('setClickVisual', 2)
                      this.$store.dispatch('setClickCafeteria', true)
                      // this.pickedCafeteria = this.responseData[0].cafeteria[i]
                    })
                  }
                  this.cafeteriaMarker = markers
                } else if (this.onclick === true && i === 2) {
                  this.responseData = this.cafeInformation.방배역
                  
                  // 지하철 역 주변에 존재하는 음식점 마커 생성
                  var markers = []
                  var imageSrc = "https://cdn-icons-png.flaticon.com/128/1946/1946401.png";
                  console.log(this.responseData[0].cafeteria.length)
                  for (let i=0; i<15; i++) {
                    // console.log(i, responseData[0].cafeteriaLst[i].position.lat, responseData[0].cafeteriaLst[i].position.lng)
                    var loc = new kakao.maps.LatLng(this.responseData[0].cafeteria[i].lon, this.responseData[0].cafeteria[i].lat);
                    
                    var imageSize2 = new kakao.maps.Size(30, 35); 
                    var markerImage2 = new kakao.maps.MarkerImage(imageSrc, imageSize2); 
                    var cafemarker = new kakao.maps.Marker({
                        map: this.map, // 마커를 표시할 지도
                        position: loc, // 마커를 표시할 위치
                        image : markerImage2, // 마커 이미지
                    });
                    markers.push(cafemarker)
                    kakao.maps.event.addListener(cafemarker, 'click', () => {
                      // console.log('음식점 마커 클릭')
                      this.$store.dispatch('setVisualizedData', this.responseData[0].cafeteria[i])
                      this.pickedCafereria = this.responseData[0].cafeteria[i]
                      this.isPicked = true
                      this.$store.dispatch('setClickVisual', 2)
                      this.$store.dispatch('setClickCafeteria', true)
                      // this.pickedCafeteria = this.responseData[0].cafeteria[i]
                    })
                  }
                  this.cafeteriaMarker = markers
                } else if (this.onclick === true && i === 3) {
                  this.responseData = this.cafeInformation.내방역
                  // console.log(this.responseData[0].cafeteria.lon)
                  
                  // 지하철 역 주변에 존재하는 음식점 마커 생성
                  var markers = []
                  var imageSrc = "https://cdn-icons-png.flaticon.com/128/1946/1946401.png";
                  for (let i=0; i<15; i++) {
                    // console.log(i, responseData[0].cafeteriaLst[i].position.lat, responseData[0].cafeteriaLst[i].position.lng)
                    var loc = new kakao.maps.LatLng(this.responseData[0].cafeteria[i].lon, this.responseData[0].cafeteria[i].lat);
                    
                    var imageSize2 = new kakao.maps.Size(30, 35); 
                    var markerImage2 = new kakao.maps.MarkerImage(imageSrc, imageSize2); 
                    var cafemarker = new kakao.maps.Marker({
                        map: this.map, // 마커를 표시할 지도
                        position: loc, // 마커를 표시할 위치
                        image : markerImage2, // 마커 이미지
                    });
                    markers.push(cafemarker)
                    kakao.maps.event.addListener(cafemarker, 'click', () => {
                      // console.log('음식점 마커 클릭')
                      this.$store.dispatch('setVisualizedData', this.responseData[0].cafeteria[i])
                      this.pickedCafereria = this.responseData[0].cafeteria[i]
                      this.isPicked = true
                      this.$store.dispatch('setClickCafeteria', true)
                      this.$store.dispatch('setClickVisual', 2)
                    })
                  }
                  this.cafeteriaMarker = markers
                } 
                this.$store.dispatch('setVisualizedData', this.responseData[0])
              })
              .catch(err => console.log(err))
              
            // console.log(`${i}번째 마커를 클릭하였습니다.`)
            // 클릭이 되어있는 경우 => 경로가 꺼지도록
            if (this.onclick === true) {
              this.onclick = false
              this.trackInfoLst = []
              this.$store.dispatch('addTrackInfo', this.trackInfoLst)
              // 해당 역 주변 음식점 마커지우기
              for (let k=0; k<this.cafeteriaMarker.length; k++) {
                this.cafeteriaMarker[k].setMap(null);
              }
              // 현재 지도위의 경로지우기
              for (let i = 0; i<this.polylineLst.length; i++) {
                for (let j = 0; j<this.polylineLst[i].length; j++) {
                  var polyline = this.polylineLst[i][j]
                  polyline.setMap(null);
                }
              }
              this.polylineLst = [[],[],[],[],[]]
            } else if (this.onclick === false) {
              this.onclick = true
              // this.$store.dispatch('setVisualizedData', this.responseData[0])
              this.$store.dispatch('setClickVisual', 1)
              this.$store.dispatch('setClickCafeteria', false)
              var loc = new kakao.maps.LatLng(this.centerStation[i].lat, this.centerStation[i].lng);
              this.map.setCenter(loc)
              this.map.setLevel(4)
              // console.log('현재사용자: ', this.latlng)
              // 유저마다 지하철역까지 가는 경로 생성
              for (let j=0; j<this.userPosition.length; j++) {
                // console.log('j:', j, 'i:' , i)
                var sx = this.userPosition[j].lng
                var sy = this.userPosition[j].lat
                var ex = this.centerStation[i].lng
                var ey = this.centerStation[i].lat
                this.showTrack(sx, sy, ex, ey, j)
              }
            }
          })
        }
      });
    },
    // 지하철역까지 걸리는 소요시간 확인
    setCenter(sx, sy, ex, ey, i, j) {
      // console.log('setCenter')
      var xhr = new XMLHttpRequest();
      //ODsay apiKey 입력
      var url = `https://api.odsay.com/v1/api/searchPubTransPath?SX=${sx}&SY=${sy}&EX=${ex}&EY=${ey}&apiKey=${process.env.VUE_APP_TEST}`;
      xhr.open("GET", url, true);
      xhr.send();
      xhr.onreadystatechange = () => {
        if (xhr.readyState == 4 && xhr.status == 200) {
          const totalTime = (JSON.parse(xhr.responseText))["result"]["path"][0].info.totalTime
          // console.log(`${j}번째 유저=> ${i}번째 지하철까지 소요시간 ${totalTime}분`)
          if (this.timeLst[i] === undefined) {
            this.timeLst.push(totalTime)
          } else {
            this.timeLst[i] += totalTime
          }
        }
      }
    },

    showTrack(sx, sy, ex, ey, idx) {
      const searchPubTransPathAJAX = () => {
        // console.log(sx, sy, ex, ey)
        var xhr = new XMLHttpRequest();
        //ODsay apiKey 입력
        var url = `https://api.odsay.com/v1/api/searchPubTransPath?SX=${sx}&SY=${sy}&EX=${ex}&EY=${ey}&apiKey=${process.env.VUE_APP_TEST}`;
        xhr.open("GET", url, true);
        xhr.send();
        xhr.onreadystatechange = () => {
          if (xhr.readyState == 4 && xhr.status == 200 && xhr.response.indexOf('error') == -1) {           
            //노선그래픽 데이터 호출
            // console.log(xhr.response)
            // callMapObjApiAJAX((JSON.parse(xhr.responseText))["result"]["path"][0].info.mapObj);
            // xhr.responseText 로 결과를 가져올 수 있음
            const aa = (JSON.parse(xhr.responseText))["result"]["path"][0].info.mapObj
            // var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
            const trackInfo = (JSON.parse(xhr.responseText))["result"]["path"][0].info
            // const trackInfo = {
            //   totalTime: (JSON.parse(xhr.responseText))["result"]["path"][0].info.totalTime,
            //   totalDistance: (JSON.parse(xhr.responseText))["result"]["path"][0].info.totalDistance,
            //   userIdx: idx,
            // }
            //ODsay apiKey 입력
            var url = "https://api.odsay.com/v1/api/loadLane?mapObject=0:0@"+aa+"&apiKey="+process.env.VUE_APP_TEST;
            xhr.open("GET", url, true);
            xhr.send();
            xhr.onreadystatechange = () => {
              if (xhr.readyState == 4 && xhr.status == 200) {
                // console.log('error확인: ', xhr.response.indexOf('error'), xhr.responseText)
                var resultJsonData = JSON.parse(xhr.responseText);
                console.log('push전 trackInfoLst:', this.trackInfoLst)
                this.trackInfoLst.push(trackInfo)
                // drawNaverPolyLine(resultJsonData);      // 노선그래픽데이터 지도위 표시
                var lineArray;

                for(var i = 0 ; i < resultJsonData.result.lane.length; i++){
                  for(var j=0 ; j <resultJsonData.result.lane[i].section.length; j++){
                    lineArray = null;
                    lineArray = new Array();
                    for(var k=0 ; k < resultJsonData.result.lane[i].section[j].graphPos.length; k++){
                      lineArray.push(new kakao.maps.LatLng(resultJsonData.result.lane[i].section[j].graphPos[k].y, resultJsonData.result.lane[i].section[j].graphPos[k].x));
                    }
                    if(idx === 0){
                      var polyline = new kakao.maps.Polyline({
                        map: this.map,
                        path: lineArray,
                        strokeWeight: 7,
                        strokeColor: '#6478FF'
                      });
                      if (this.polylineLst[idx] === []) {
                        this.polylineLst.push(polyline)
                      } else {
                        this.polylineLst[idx].push(polyline)
                      }
                      // console.log(this.polylineLst)
                    } else if(idx == 1){
                      var polyline = new kakao.maps.Polyline({
                        map: this.map,
                        path: lineArray,
                        strokeWeight: 7,
                        strokeColor: '#48DAD2'
                      });
                      if (this.polylineLst[idx] === []) {
                        this.polylineLst.push(polyline)
                      } else {
                        this.polylineLst[idx].push(polyline)
                      }
                    } else if(idx == 2){
                      var polyline = new kakao.maps.Polyline({
                        map: this.map,
                        path: lineArray,
                        strokeWeight: 7,
                        strokeColor: '#FF7E9D'
                      });
                      if (this.polylineLst[idx] === []) {
                        this.polylineLst.push(polyline)
                      } else {
                        this.polylineLst[idx].push(polyline)
                      }
                    } else if(idx == 3){
                      var polyline = new kakao.maps.Polyline({
                        map: this.map,
                        path: lineArray,
                        strokeWeight: 7,
                        strokeColor: '#50508C'
                      });
                      if (this.polylineLst[idx] === []) {
                        this.polylineLst.push(polyline)
                      } else {
                        this.polylineLst[idx].push(polyline)
                      }
                    } else {
                      var polyline = new kakao.maps.Polyline({
                        map: this.map,
                        path: lineArray,
                        strokeWeight: 7,
                        strokeColor: '#FF5A5A'
                      });
                      if (this.polylineLst[idx] === []) {
                        this.polylineLst.push(polyline)
                      } else {
                        this.polylineLst[idx].push(polyline)
                      }
                    }
                  }
                }
                // boundary 데이터가 있을경우, 해당 boundary로 지도이동
                // if(resultJsonData.result.boundary){
                //     var boundary = new kakao.maps.LatLngBounds(
                //       new kakao.maps.LatLng(resultJsonData.result.boundary.top, resultJsonData.result.boundary.left),
                //       new kakao.maps.LatLng(resultJsonData.result.boundary.bottom, resultJsonData.result.boundary.right)
                //       );
                //     // this.map.panToBounds(boundary);
                // }
              } else if (xhr.readyState == 4 && xhr.status == 200 && xhr.response.indexOf('error') != -1) {
                // console.log(xhr.response)
                if (xhr.response.indexOf('-98')) {
                  const trackInfo = {
                    msg: '도착지가 700m이내입니다.',
                    userIdx: idx
                  }
                  this.trackInfoLst.push(trackInfo)
                }
                // console.log(xhr)
              }
              // console.log('trackInfo확인', this.trackInfoLst)
              this.$store.dispatch('addTrackInfo', this.trackInfoLst)
            }
          } else if (xhr.readyState == 4 && xhr.status == 200 && xhr.response.indexOf('error') != -1) {
            // console.log(xhr.response)
            if (xhr.response.indexOf('-98')) {
              const trackInfo = {
                msg: '도착지가 700m이내입니다.',
                userIdx: idx
              }
              this.trackInfoLst.push(trackInfo)
            }
            // console.log(xhr)
          }
        }

      }
      searchPubTransPathAJAX();
    },
    setBounds() {
      this.onclick = false
      this.trackInfoLst = []
      this.$store.dispatch('addTrackInfo', this.trackInfoLst)
      // 해당 역 주변 음식점 마커지우기
      for (let k=0; k<this.cafeteriaMarker.length; k++) {
        this.cafeteriaMarker[k].setMap(null);
      }
      for (let i = 0; i<this.polylineLst.length; i++) {
        for (let j = 0; j<this.polylineLst[i].length; j++) {
          var polyline = this.polylineLst[i][j]
          polyline.setMap(null);
        }
      }
      this.polylineLst = [[],[],[],[],[]]
      console.log('지도 범위 재설정')
      var bounds = new kakao.maps.LatLngBounds();
      for (let i = 0; i < this.latlng.length; i++) {
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(this.latlng[i]);
      }
      this.map.setBounds(bounds);
    },
    showTrackDetail(idx) {
      // console.log('경로상세보이기: ', this.polylineLst, idx)
      for (let i = 0; i<this.polylineLst.length; i++) {
        for (let j = 0; j<this.polylineLst[i].length; j++) {
          var polyline = this.polylineLst[i][j]
          polyline.setMap(null);
        }
      }
      for (let i = 0; i<this.polylineLst.length; i++) {
        if (i === idx) {
          for (let j = 0; j<this.polylineLst[i].length; j++) {
            var polyline = this.polylineLst[i][j]
            polyline.setMap(this.map);
          }
        }
      }

    },
  },
  // watch: {
  //   centerStationLst: function() {
  //     console.log('바꼇다리')
  //     // console.log(this.centerStationLst)
  //   }
  // },
  async mounted(){
    // var mapScript = document.getElementById('mapScript')
    // mapScript.type = "text/javascript"
    // mapScript.src="//dapi.kakao.com/v2/maps/sdk.js?appkey="+process.env.VUE_APP_KAKAO_API_KEY+"&libraries=services"
    // document.body.appendChild(mapScript)
    // window.google && window.google.maps
    //   ? this.initMap(false)
    //   : this.addKaKaoMapScript();

    var container = document.getElementById('map');
      var options = {
         center: new kakao.maps.LatLng(33.450701, 126.570667),
         level: 7
      };
    
    this.map = new kakao.maps.Map(container, options);
    await this.loadMap()
    await this.searchCenter()
  }
}
</script>

<style>
#result-page {
  height: 100%;
}
#map {
  overflow: hidden;
  width:100%;
  height: 100%;
}
#carousel {
  position: absolute;
  z-index: 1;
  width: 100%;
  height: 250px;
  /* background-color: rgba( 255, 255, 255, 0.3); */
  color: black;
  bottom: 1rem;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.reload-bound {
  position: absolute;
  right: 10px;
  bottom: 10px;
  z-index: 1;
}
</style>