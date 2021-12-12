<template>
  <div class="datavisual">
    <h1>이 지역의 긍정 키워드 3개는<br>{{ 맛집 }} {{ 데이트 }} {{ 분위기 }} 입니다.</h1>
    <d3-pie
        :data="pieValue"
        :cornerRadius="100"
        width="100%"
        height="320px">
    </d3-pie>
    <div id="word-cloud"></div>
  </div>
</template>

<script>

export default {
  name: 'DataVisual',
  data(){
      return {
        pieValue: [
          {key : '한식', value : '31'},
          {key : '중식', value : '22'},
          {key : '일식', value : '41'},
        ],
        words: [
        { text: '짜장면', size: 20 },
        { text: '짬뽕', size: 30 },
        { text: '좋아', size: 10 },
        { text: '싫어', size: 25 },
        { text: '맛있어', size: 50 },
        { text: '맛없어', size: 40 },
        { text: '청결', size: 30 },
        { text: '뭐지', size: 24 },
        { text: '비싸', size: 10 },
        { text: '싸', size: 34 },
      ],
      name: '오이식당', 
      category: '한식',
      rate: 3,
      rate_count: 5,
      review: "밥 좋아요 짱 ",
      // words: "안녕 하이 짱 좋아 뭐가 음 롤로롤 오이식당 한식 라라랄랄"
      }
  },
  mounted() {
    this.genLayout();
  },
  methods: {
    genLayout() {
      const cloud = require('d3-cloud');
      cloud()
        .words(this.words)
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
      const width = 400;
      const height = 300;
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


<style scoped>
h1 {
  margin: 30px 10% 30px 10% ;
}
#word-cloud {
  margin: 40px;
  text-align: center;
}
d3-pie {
  margin-top: 60px;
}
</style>