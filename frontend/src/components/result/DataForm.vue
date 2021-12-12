<template>
  <div>
    <!-- <v-sheet
      height="400"
      class="overflow-hidden"
      style="position: relative;"
    > -->
    <!-- <v-sheet>
      <v-container class="fill-height">
        <v-row
          align="center"
          justify="center"
        >
          <v-btn
            color="#BEE2C2"
            dark
            @click.stop="drawer = !drawer"
          >
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </v-row>
      </v-container> -->

      <v-btn
        color="#BEE2C2"
        dark
        @click.stop="drawer = !drawer"
        class="arrow-btn"
        small
      >
        <v-icon
        >mdi-chevron-left</v-icon>
      </v-btn>

      <v-navigation-drawer
        v-model="drawer"
        absolute
        temporary
        right
        overlay-color="white"
        overlay-opacity="0"
        width="400"
      >
      <div v-show="this.clickVisual===1">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>이 약속장소의 정보</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-divider></v-divider>
        <v-list dense >
          <div class="datavisual" style="margin: 0 5% 0 5%;">
            <h1>이 약속장소의 키워드 TOP3는<br>{{ data.이수역[0].items[0].title }} {{ data.이수역[0].items[1].title }} {{ data.이수역[0].items[2].title }} 입니다.</h1>
            <p>카테고리별 분포</p>
            <d3-pie
                :data="data.이수역[0].pievalue"
                :cornerRadius="100"
                width="100%"
                height="320px">
            </d3-pie>
            <!-- <h3>이 약속장소의 맛집들은 어떤 키워드를 가지고 있을까요?</h3> -->
            <h2>워드클라우드</h2>
            <p style="text-align: center;">이 약속장소의 맛집들은 어떤 키워드를 가지고 있을까요? 약속장소 식당들의 리뷰를 워드클라우드로 확인해보세요 </p>
            <div id="word-cloud"></div>
            <h2>핵심 키워드 분포</h2>
            <d3-vertical-bar
              :data="data.이수역[0].words"
              :options="options"
              :margin="margin"
              width="100%"
              @range-updated="(dateTimeStart, dateTimeEnd) => fetchDataWithCurrentInterval(dateTimeStart, dateTimeEnd)"
              height="400px">
            </d3-vertical-bar>
            <v-list v-for="(item, index) in data.이수역[0].items" :key="index">
              <p style="magin-bottom:0px;">{{ index+1 }}순위: {{item.title}}</p>
            </v-list>
          </div>
        </v-list>
      </div>

      <div v-show="this.clickVisual===2">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>식당 정보</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-divider></v-divider>
        <v-list dense>
          <div class="detailVisual">
            <img id="category-icon" :src="require('../../assets/category/' + data.이수역[0].cafeteria[1].category + '.png')" alt="" />
            <h1>{{ data.이수역[0].cafeteria[1].name }}</h1>
            <a :href="data.이수역[0].cafeteria[1].url" style="text-align: center;">
              <p style="font-size:13px; color:black;">이 식당에 대해 더 자세히 알고 싶다면?</p>
            </a>
            <p>{{ data.이수역[0].cafeteria[1].category }}</p>
            <div class="good_score">
              <!-- <v-list v-for="index in score" :key="index">
                <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
              </v-list>
              <v-list v-for="index in 5-score" :key="index">
                <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
              </v-list> -->
              <div v-if="data.이수역[0].cafeteria[1].rate<4.55">
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
              </div>
              <div v-else-if="data.이수역[0].cafeteria[1].rate<4.65" >
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px; "/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
              </div>
              <div v-else-if="data.이수역[0].cafeteria[1].rate<4.75" >
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
              </div>
              <div v-else-if="data.이수역[0].cafeteria[1].rate<4.85" >
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
              </div>
              <div v-else-if="data.이수역[0].cafeteria[1].rate<4.95" >
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v2.png" style="width: 48px; margin:0px;"/>
              </div>
              <div v-else-if="data.이수역[0].cafeteria[1].rate>=4.95" >
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
                  <img src="https://img.icons8.com/color/48/000000/like--v3.png" style="width: 48px; margin:0px;"/>
              </div>
            </div>
            <p>맛집점수: {{ data.이수역[0].cafeteria[1].rate }} ({{ data.이수역[0].cafeteria[1].rate_count }})</p>
            <p style="font-size:27px;">{{ data.이수역[0].cafeteria[1].name }}의 review</p>
            <div class="cloud"></div>
          </div>
        </v-list>
      </div>
      </v-navigation-drawer>
    <!-- </v-sheet> -->
  </div>
</template>

<script src="https://d3js.org/d3.v3.min.js"></script>
<script>
import { mapGetters } from 'vuex'
import seo from '../../assets/seo.json'
const data=seo
export default {
  name: 'DataForm',
  data () {
    return {
      data,
      // 역 데이터
      // subway: keys[0],
      // pieValue: [
      //   {key : '한식', value : '31'},
      //   {key : '중식', value : '22'},
      //   {key : '일식', value : '41'},
      // ],
      // verticalValue: [
      //   {key : '좋아요', value : '31'},
      //   {key : '분위기', value : '22'},
      //   {key : '데이트', value : '41'},
      //   {key : '맛집', value : '21'},
      //   {key : '술집', value : '16'},
      //   {key : '즐겁다', value : '2'},
      // ],
      // words: [
      //   { text: '짜장면', size: 20 },
      //   { text: '짬뽕', size: 30 },
      //   { text: '좋아', size: 10 },
      //   { text: '싫어', size: 25 },
      //   { text: '맛있어', size: 50 },
      //   { text: '맛없어', size: 40 },
      //   { text: '청결', size: 30 },
      //   { text: '뭐지', size: 24 },
      //   { text: '비싸', size: 10 },
      //   { text: '싸', size: 28 },
      //   { text: '즐겁다', size: 34 },
      //   { text: '뭐지', size: 58 },
      //   { text: '무슨말', size: 54 },
      //   { text: '중국집', size: 26 },
      //   { text: '탕수육', size: 34 },
      //   { text: '그냥그래', size: 46 },
      // ],
      // // verticalValue에서 vlaue큰 것 3개
      // items: [
      //   {title: '좋아요'},
      //   {title: '분위기'},
      //   {title: '맛집'},
      // ],
      // // 맛집 데이터
      // name: '오이식당',
      // category: '한식',
      // url: "https://www.multicampus.com/kr/index.html",
      // rating: 4.82,
      // rate_count: 5,
      // review: "안녕 언제 언제 언제 하이 짱 좋아 뭐가 음 롤로롤 오이식당 한식 라라랄랄 하이 좋아 안녕 뭐가 랄라 머야 아 진짜 뭐 적을지 전혀 모르겠다 진짜 진짜 음 맛있어 저녁 언제 먹지 언제먹을까",
      drawer: null,
    }
  },
  computed: {
    ...mapGetters(['trackInfo', 'userInfo', 'centerStation', 'visualizedData', 'clickVisual']),
  },
  mounted() {
    this.genLayout();

    // num for score
    // const score = Math.floor((this.rating*10)-45);
    // console.log(score)

    // key 출력 - 문제: 이 역의 인덱스는 어떻게 가져옴?
    // var keys = Object.keys(this.data)
    // console.log('key출력', keys[0])

    // detailVisual word-cloud
    var text2 = this.data.이수역[0].cafeteria[1].review,
      review = sortByFrequency( text2 )
        .map(function(d,i) {
          //console.log(d);
              return {text: d, size: -i};
            });

    var fontName = "Impact",
      cWidth = 380,
      cHeight = 270,
      svg,
      wCloud,
      bbox,
      // ctm,
      bScale,
      bWidth,
      bHeight,
      bMidX,
      bMidY,
      bDeltaX,
      bDeltaY;

    var cTemp = document.createElement('canvas'),
      ctx = cTemp.getContext('2d');
      ctx.font = "100px " + fontName;

    var fRatio = Math.min(cWidth, cHeight) / ctx.measureText(review[0].text).width,
      fontScale = d3.scale.linear()
        .domain([
          d3.min(review, function(d) { return d.size; }), 
          d3.max(review, function(d) { return d.size; })
        ])
        //.range([20,120]),
        .range([20,100*fRatio/2]), // tbc
      fill = d3.scale.category20();

    d3.layout.cloud()
      .size([cWidth, cHeight])
      .words(review)
      //.padding(2) // controls
      .rotate(function() { return ~~(Math.random() * 2) * 90; })
      .font(fontName)
      .fontSize(function(d) { return fontScale(d.size) })
      .on("end", draw)
      .start();

    function draw(review, bounds) {
      // move and scale cloud bounds to canvas
      // bounds = [{x0, y0}, {x1, y1}]
      bWidth = bounds[1].x - bounds[0].x;
      bHeight = bounds[1].y - bounds[0].y;
      bMidX = bounds[0].x + bWidth/2;
      bMidY = bounds[0].y + bHeight/2;
      bDeltaX = cWidth/2 - bounds[0].x + bWidth/2;
      bDeltaY = cHeight/2 - bounds[0].y + bHeight/2;
      bScale = bounds ? Math.min( cWidth / bWidth, cHeight / bHeight) : 1;
      
      console.log(
        "bounds (" + bounds[0].x + 
        ", " + bounds[0].y + 
        ", " + bounds[1].x + 
        ", " + bounds[1].y + 
        "), width " + bWidth +
        ", height " + bHeight +
        ", mid (" + bMidX +
        ", " + bMidY +
        "), delta (" + bDeltaX +
        ", " + bDeltaY +
        "), scale " + bScale
      );
      
      // the library's bounds seem not to correspond to reality?
      // try using .getBBox() instead?
      
      svg = d3.select(".cloud").append("svg")
        .attr("width", cWidth)
        .attr("height", cHeight);
      
      wCloud = svg.append("g")
        //.attr("transform", "translate(" + [bDeltaX, bDeltaY] + ") scale(" + 1 + ")") // nah!
        .attr("transform", "translate(" + [bWidth>>1, bHeight>>1] + ") scale(" + bScale + ")") // nah!
        .selectAll("text")
        .data(review)
        .enter().append("text")
        .style("font-size", function(d) { return d.size -3 + "px"; })
        .style("font-family", fontName)
        .style("fill", function(d, i) { return fill(i); })
        .attr("text-anchor", "middle")
        .transition()
        .duration(500)
        .attr("transform", function(d) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.text; });
      
      // TO DO: function to find min and max x,y of all words
      // and use it as the group's bbox
      // then do the transformation
      bbox = wCloud.node(0).getBBox();
      //ctm = wCloud.node().getCTM();
      console.log(
        "bbox (x: " + bbox.x + 
        ", y: " + bbox.y + 
        ", w: " + bbox.width + 
        ", h: " + bbox.height + 
        ")"
      );
      
    }

    function sortByFrequency(arr) {
      var f = {};
      arr.forEach(function(i) { f[i] = 0; });
      var u = arr.filter(function(i) { return ++f[i] == 1; });
      return u.sort(function(a, b) { return f[b] - f[a]; });
    }
  },
  methods: {
    genLayout() {
      const cloud = require('d3-cloud');
      cloud()
        .words(this.data.이수역[0].review)
        .padding(1)
        .font('Impact')
        .fontSize(function (d) {
          return d.size;
        })
        .on('end', this.end)
        .spiral('archimedean')
        .start()
        .stop();
    },
    end(words) {
      const d3 = require('d3');
      const width = 350;
      const height = 280;
      d3.select('#word-cloud')
        .append('svg')
        .attr('width', width)
        .attr('height', height)
        .style('background', 'white')
        .append('g')
        .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')') // g를 중심에서 단어들을 그리기 때문에 g를 svg 중심으로 이동
        .selectAll('text')
        .data(words)
        .enter()
        .append('text')
        .style('font-size', (d) => {
          return d.size + 5 + 'px';
        })
        .style('font-family', 'Impact')
        .attr('text-anchor', 'middle')
        .attr('transform', (d) => {
          return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')';
        })
        .text((d) => d.text);
    },
    
  }
}
</script>

<style>
.good_score {
  text-align: center;
}
.arrow-btn {
  position: absolute;
  bottom: 50%;
  right: 0px;
  z-index: 1;
}
#word-cloud {
  text-align: center;
}
.d3-pie {
  margin-bottom: 40px;
}
.detaVisual h1 {
  margin: 10px 0 30px 0 ;
}
.detaVisual h2 {
  text-align: center;
}
.detaVisual p {
  margin: 0;
}
.detailVisual h1 {
  text-align: center;
  font-size: 3em;
  margin-bottom: 0;
}
.detailVisual h2 {
  text-align: center;
  margin-bottom: 4px;
}
.detailVisual p {
  text-align: center;
  margin-bottom: 0x;
}
.detailVisual img {
  width: 200px;
  margin:0 25% 0 25%;
}
</style>