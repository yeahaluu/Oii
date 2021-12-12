<template>
  <div>
      <v-card
        class="input-info"
        elevation="2"
        color="#E3F0E1"
      >
      <div class="info-flex">
        <v-text-field
          class="name-field"
          label="이름"
          v-model="name"
          hide-details="auto"
          counter="5"
          maxlength="5"
          color="#41A30D"
          :rules="rules"
        ></v-text-field>

        <v-text-field
          id="sample6_address"
          class="location-field"
          v-model="location"
          hide-details="auto"
          color="#41A30D"
          :rules="rules"
          disabled
        ></v-text-field>
        <!-- <input v-model="location" type="text" id="sample6_address" placeholder="주소"><br> -->
        <v-btn
          class="search-btn"
          elevation="1"
          small
          color="#41A30D"
          @click="sample6_execDaumPostcode()"
        >위치검색</v-btn>
      </div>
      <p class="select-text mb-0">나에게 딱 맞는 식당을 찾고 싶다면? (선택)</p>
      <div class="info-flex__second">
        <div class="age-selector">
          <v-select
            :items="ages"
            :label="selectedAge"
            v-model="selectedAge"
            dense
            solo
            height="30"
            width="100"
            background-color="#BDDDAC"
          ></v-select>
        </div>
        <div class="gender-selector">
          <v-radio-group 
            row
            v-model="gender"
          >
            <v-radio
              label="남자"
              value="남자"
              color="#41A30D"
            ></v-radio>
            <v-radio
              label="여자"
              value="여자"
              color="#41A30D"
          ></v-radio>
          </v-radio-group>
        </div>
      </div>
      </v-card>
    </div>
</template>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import "@/css/main/CreateUserForm.scss"

export default {
  name: 'CreateUserForm',
  props: {
    info: {
      type: Object
    }
  },
  data: function() {
    return {
      ages: ['10대', '20대', '30대', '40대', '50대', '60대이상'],
      selectedAge: '나이',
      name: '',
      location: '',
      gender: '',
      rules: [
        value => !!value || 'Required.', // 빈 값이면 Required 발생
      ],
    }
  },

  methods: {
    sample6_execDaumPostcode() {
      new daum.Postcode({
            oncomplete: (data) => {  // arrow 사용시 this는 oncomplete 내부가 아닌 외부의 this를 사용 (function 사용시 내부 this 사용하기 때문에 uncaught error발생)
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 주소 정보를 해당 필드에 넣는다.
                this.location = addr;
            }
        }).open();
    },

  },

  // 변경 감시 => 변경되면 해당 함수 실행
  watch: {
    name: function() {
      this.info.name = this.name
    },
    location: function() {
      this.info.location = this.location
    },
    selectedAge: function() {
      this.info.age = this.selectedAge
    },
    gender: function() {
      this.info.gender = this.gender
    },
  },

}
</script>
