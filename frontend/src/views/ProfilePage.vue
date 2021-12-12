<template>
  <div>
    <article class="article">
      <p class="article-head">Profile</p>
      <div class="article-content">
        <div class="section1">
          <img class="profile-img" src="https://dimg.donga.com/ugc/CDB/WEEKLY/Article/5b/b3/22/85/5bb32285000ed2738de6.jpg" alt="profile_image">
          <div class="name-position">
            <label>닉네임 : </label>
            <input type="text" placeholder="사용자 닉네임" class="d_form">
            <br>
            <div class="address">
              <label>위치 : </label>
              <!-- <p id="sample6_address">위치 : 서울특별시 서초구 방배3동 예성그린캐슬3차아파트 805호</p> -->
              <input type="text" id="sample6_address" placeholder="주소" class="d_form"><br>
            </div>
              <v-btn color="#41A30D" dark class="btn" @click="searchAddress">검색</v-btn>
              <!-- <button class="btn" @click="addressManageModal">주소지 관리</button> -->
              <v-dialog
                v-model="dialog"
                scrollable
                max-width="600px"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-btn color="#41A30D" dark v-bind="attrs" v-on="on" class="btn">
                    주소지 관리
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title>주소지 관리</v-card-title>
                  <v-divider></v-divider>
                  <v-card-text style="height: 400px;">
                    <v-data-table
    :headers="headers"
    :items="desserts"
    sort-by="calories"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar
        flat
      >
        <v-toolbar-title>My CRUD</v-toolbar-title>
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
        <v-spacer></v-spacer>
        <v-dialog
          v-model="dialog"
          max-width="500px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="primary"
              dark
              class="mb-2"
              v-bind="attrs"
              v-on="on"
            >
              New Item
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.name"
                      label="Dessert name"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.calories"
                      label="Calories"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.fat"
                      label="Fat (g)"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.carbs"
                      label="Carbs (g)"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.protein"
                      label="Protein (g)"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="blue darken-1"
                text
                @click="close"
              >
                Cancel
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click="save"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <!-- <template v-slot:item.actions="{ item }">
      <v-icon
        small
        class="mr-2"
        @click="editItem(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        small
        @click="deleteItem(item)"
      >
        mdi-delete
      </v-icon>
    </template> -->
  </v-data-table>
                  </v-card-text>
                  <v-divider></v-divider>
                  <v-card-actions>
                    <v-btn
                      color="grey darken-1"
                      text
                      @click="dialog = false"
                    >
                      Close
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>

          </div>
        </div>
        <div class="section2">    
          <br>

          <label>나에게 딱 맞는 식당을 찾고 싶다면?(선택)</label>
          <v-container fluid>
            <v-row justify="space-around" align="center">
              <v-col cols="6">
                <v-select
                  :items="ages"
                  :label="selectedAge"
                  v-model="selectedAge"
                  dense
                  solo
                  background-color="#DCD8D7"
                  item-color="green"
                  color="green"
                ></v-select>
              </v-col>
              <v-radio-group
                row
                color="#41A30D"
              >
                <v-radio
                  label="여자"
                  value="radio-1"
                  color="#41A30D"
                ></v-radio>
                <v-radio
                  label="남자"
                  value="radio-2"
                  color="#41A30D"
                ></v-radio>
              </v-radio-group>
            </v-row>
          </v-container>
        </div>
      </div>
      <button class="complete-btn">완료</button>
    </article>
  </div>
</template>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
// import axios from 'axios'
import AddrMngModal from '@/components/AddrMngModal'
export default {
  name: 'ProfilePage',
  data: function() {
    return {
      ages: ['10대', '20대', '30대', '40대', '50대', '60대이상'],
      selectedAge: '나이',
      dialog: false,
      dialog: false,
      dialogDelete: false,
      headers: [
        {
          text: '주소지',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        { text: '주소', value: 'calories' },
        // { text: 'Fat (g)', value: 'fat' },
        // { text: 'Carbs (g)', value: 'carbs' },
        // { text: 'Protein (g)', value: 'protein' },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      desserts: [],
      editedIndex: -1,
      editedItem: {
        name: '',
        calories: 0,
        fat: 0,
        carbs: 0,
        protein: 0,
      },
      defaultItem: {
        name: '',
        calories: 0,
        fat: 0,
        carbs: 0,
        protein: 0,
      },
    }
  },
  computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
      },
    },
    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogDelete (val) {
        val || this.closeDelete()
      },
    },
  components: {
    AddrMngModal
  },
  methods: {
    searchAddress() {
      // axios.get(`https://www.juso.go.kr/addrlink/addrLinkUrl.do`)
      //   .then(res => window.open(res,'window_name','fullscreen=yes'))
      //   .catch(err => console.log(err))
      new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            } 

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("sample6_address").value = addr;
        }
      }).open();
    },
    initialize () {
        this.desserts = [
          {
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
          },
          {
            name: 'Ice cream sandwich',
            calories: 237,
            fat: 9.0,
            carbs: 37,
            protein: 4.3,
          },
        ]
      },

      editItem (item) {
        this.editedIndex = this.desserts.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      deleteItem (item) {
        this.editedIndex = this.desserts.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialogDelete = true
      },

      deleteItemConfirm () {
        this.desserts.splice(this.editedIndex, 1)
        this.closeDelete()
      },

      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      closeDelete () {
        this.dialogDelete = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      save () {
        if (this.editedIndex > -1) {
          Object.assign(this.desserts[this.editedIndex], this.editedItem)
        } else {
          this.desserts.push(this.editedItem)
        }
        this.close()
      },
  },
  created () {
      this.initialize()
    },
}
</script>

<style scoped>
.name-position {
  display: inline-block;
  position: relative;
  bottom: 30px;
  left: 20px;
}
.article-head {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  border-radius: 10px 10px 0 0;
  font-size: 50px;
  text-align: start;
  margin: 0;
  color: #41A30D;
  background-color: #BEE2C2;
}
.article {
  background-color: #E3F0E1;
  border-radius: 10px;
  width: 700px;
  height: fit-content;
  text-align: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.article-content {
  color: #41A30D;
  padding: 10px 0 0 10px;
}
.section1 {
  text-align: start;
}
.address {
  display: inline-block;
  padding-top: 10px;
}
.address input {
  margin: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 230px;
  /* height: 20px; */
}
.profile-img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 70%;
}
.btn {
  font-size: 1rem;
  font-weight: 900;
  background-color: #41A30D;
  color: aliceblue;
  padding: 2px 10px;
  margin-left: 10px;
  cursor: pointer;
  border-radius: 5px;
  border: 1px black;
  box-shadow:  2px 2px 3px black;
}
.complete-btn {
  font-size: 1rem;
  background-color: white;
  color: #41A30D;
  font-weight: 900;
  padding: 2px 10px;
  margin-bottom: 10px;
  border-radius: 5px;
  box-shadow:  2px 2px 3px black;
}
.v-input--selection-controls {
  margin-top: 0;
  padding-top: 0;
}
.d_form {
  /* margin: .2em 0; */
  font-size: 1em;
  margin-left: 10px;
  padding-left: 5px;
  /* padding: .5em; */
  /* border: 1px solid #ccc; */
  border-bottom: 3px solid #41A30D;
  color: #41A30D;
  /* border-color: #dbdbdb #d2d2d2 #d0d0d0 #d2d2d3; */
  /* box-shadow: inset 0.1em 0.1em 0.15em rgb(0 0 0 / 10%); */
  /* vertical-align: middle; */
  /* line-height: 1.25em; */
  /* outline: 0; */
  /* width: 20em; */
}
label {
  color: #2F6914;
  font-weight: 900;
}
.v-btn:not(.v-btn--round).v-size--default {
    /* height: 36px;
    min-width: 50px; */
    padding: 0 10px;
}
</style>