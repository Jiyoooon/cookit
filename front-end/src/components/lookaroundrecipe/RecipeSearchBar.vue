<template>
  <div>
    <b-container id="searchbar">
      <!-- 카테고리 필터링 -->
      <b-row align-v="center" style="padding-bottom: 1em;">
        <b-col sm="2" class="text-center cate-title">카테고리</b-col>
        <b-col sm="10" style="padding-bottom: 0px;">
          <div class="cate-list">
            <div v-for="(item, index) in categories" @click="changeCategory(index)" 
            :class="(index==selectedcategory)?'cate-item selected':'cate-item default'" :key="item.text">
            {{ item.text }}</div>
          </div>
        </b-col>
      </b-row>

      <!-- 제목 검색 -->
      <b-row align-v="center">
        <b-col sm="10">
          <b-form-input
            type="text"
            v-model="searchtextT"
            @keydown.enter="searchRecipe"
            placeholder="레시피 제목 검색"
            single-line
          />
        </b-col>
        <b-col sm="2">
          <div class="block-btn btn-style1" @click="searchRecipe">검색</div>
        </b-col>
      </b-row>
      <b-row class="mt-4 mb-3 mr-3 ml-3">
        <b-form-text id="tags-remove-on-delete-help">
          <b-icon class="mr-1" icon="exclamation-circle-fill" variant="secondary"></b-icon> 넣고 싶은 재료와 빼고 싶은 재료를
          <kbd>Enter</kbd>로 등록해서 검색해보세요.
        </b-form-text>
      </b-row>

      <b-row style="padding-top: 0px; padding-bottom: 0px; margin-bottom: -10px; margin-left: 3px;">

      <!-- 추가할 재료 -->
        <b-col md="6" style="padding-top: 0px; padding-bottom: 0px;">
          <b-row align-v="center" style="margin-top: -10px;">
            <b-col cols="1" class="text-center" style="padding-top: 8px;">
              <font-awesome-icon color="#3F51B5" :icon="['fas', 'plus']" />
            </b-col>
            <b-col cols="11" class="ml-n3" style="padding-top: 0px; padding-bottom: 0px;">
              <v-autocomplete
                class="b-flex"
                :selecteditems="selecteditems"
                :search-input.sync="searchtextS"
                append-icon
                single-line
                @keydown.enter="selectSource(selecteditems[0])"
                placeholder=" 포함할 재료 추가"
                color="#3F51B5"
              />
              <!-- 추가할 재료 자동 완성 -->
              <v-list
                autofocus
                ref="Slist"
                v-if="searchtextS && this.selecteditems.length"
                style="position: absolute; margin-top: -10px; z-index: 10; width: 95.6%; overflow-y: scroll; height: auto; max-height: 300px"
              >
                <v-list-item
                  v-for="selecteditem in selecteditems"
                  @click="selectSource(selecteditem)"
                  onmouseover="this.style.backgroundColor='#eee'"
                  onmouseout="this.style.backgroundColor='white'"
                  :key="String(selecteditem.name)+ String(selecteditem.id)"
                  style="cursor: pointer;"
                >
                  <v-list-item-title v-text="selecteditem.name"></v-list-item-title>
                </v-list-item>
              </v-list>
            </b-col>
          </b-row>
        </b-col>

        <!-- 제외할 재료 -->
        <b-col md="6" style="padding-top: 0px; padding-bottom: 0px;">
          <b-row align-v="center" style="margin-top: -10px;">
            <b-col cols="1" class="text-center" style="padding-top: 8px;">
              <font-awesome-icon color="#B71C1C" :icon="['fas', 'minus']" />
            </b-col>
            <b-col cols="11" class="ml-n3" style="padding-top: 0px; padding-bottom: 0px;">
              <v-autocomplete
                class="b-flex"
                :selecteditems="excludeditems"
                :search-input.sync="searchtextE"
                append-icon
                single-line
                @keydown.enter="excludedSource(excludeditems[0])"
                placeholder=" 제외할 재료 추가"
                color="#B71C1C"
              />
              <!-- 제외할 재료 자동 완성 -->
              <v-list
                ref="Slist"
                v-if="searchtextE && this.excludeditems.length"
                style="position: absolute; margin-top: -10px; z-index: 10; width: 92%; overflow-y: scroll; height: auto; max-height: 300px"
              >
                <v-list-item
                  v-for="excludeditem in excludeditems"
                  @click="selectSource(excludeditem)"
                  onmouseover="this.style.backgroundColor='#eee'"
                  onmouseout="this.style.backgroundColor='white'"
                  :key="String(excludeditem.name)+ String(excludeditem.id)"
                  style="cursor: pointer;"
                >
                  <v-list-item-content>
                    <v-list-item-title v-text="excludeditem.name"></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <!-- 재료 입력시 생성되는 칩 -->
      <v-row class="pl-3 pr-3" id="selected_chips">
        <v-col>
          <SelectedSource
          v-for="select in selected"
          :key="select.ingredientdata.name"
          :id="select.ingredientdata.name"
          :Sourceinfo="select"
          style="display:inline"
          @delete-source="deleteSource"
        />
        </v-col>
      </v-row>
      <!-- 레시피 정렬 -->
      <v-row>
          <span @click="ordering(1)">최신순</span>
          <span @click="ordering(2)">조회순</span>
          <span @click="ordering(3)">추천순</span>
      </v-row>
    </b-container>
  </div>
</template>

<script>
import SelectedSource from "@/components/lookaroundrecipe/SelectedSource.vue";
import $ from 'jquery'
import { mapActions, mapState, mapMutations } from "vuex";
export default {
  name: "RecipeSearchBar",
  data() {
    return {
      categories: [
        { text: "전체", value: "0" },
        { text: "밥", value: "1" },
        { text: "면", value: "2" },
        { text: "탕/찌개", value: "3" },
        { text: "메인", value: "4" },
        { text: "반찬", value: "5" },
        { text: "샐러드", value: "6" },
        { text: "디저트", value: "7" },
        { text: "기타", value: "8" },
      ],
      select: null,
      selecteditems: [],
      excludeditems: [],

      selected: [], //{ingredientdata:{name,kind,id },state }
      searchtextT: "",
      searchtextS: "",
      searchtextE: "",
      selectedcategory: 0,
      order: null, // 레시피 정렬
    };
  },
  components: {
    SelectedSource,
  },
  methods: {
    searchRecipe() {
      //버튼을 눌렀을때,
      this.setRecipequery({
        querydata: this.searchtextT,
        selectedarray: this.selected,
        category: this.selectedcategory,
        order: this.order,
      });
    },
    blinkChip(index) {
      var item = $('#'+this.selected[index].ingredientdata.name)
      item.css('opacity', '30%')
      setTimeout(() => {
        item.css('opacity', '100%')
      }, 300)
      setTimeout(() => {
        item.css('opacity', '30%')
        setTimeout(() => {
          item.css('opacity', '100%')
        }, 300)
      }, 600)
    },
    changeCategory(index) {
      this.selectedcategory = index;
    },
    selectSource(payload) {
      let index = this.selected.map((x) => x.ingredientdata.name).indexOf(payload.name)
      if (index == -1) {
        this.selected.push({ ingredientdata: payload, state: true });
        this.searchtextS = "";
        this.searchRecipe();
      }
      else {
        this.searchtextS = "";
        this.blinkChip(index);
      }
    },
    excludedSource(payload) {
      let index = this.selected.map((x) => x.ingredientdata.name).indexOf(payload.name)
      if (index == -1) {
        this.selected.push({ ingredientdata: payload, state: false });
        this.searchtextE = "";
        this.searchRecipe();
      }
      else {
        this.searchtextE = "";
        this.blinkChip(index);
      }
    },
    deleteSource(item) {
      this.selected.splice(
        this.selected.map((x) => x.ingredientdata.name).indexOf(item), 1);
      this.selected = [...this.selected];
      this.searchRecipe();
    },
    // 넣고싶은재료
    querySelectionsS(v) {
      if (v !== "") {
        setTimeout(() => {
          this.selecteditems = this.ingredients.filter((e) => {
            //e : 필터링되기전 모든 데이터
            //alert((e || '').toLowerCase().indexOf((v || '').toLowerCase()) > -1)
            //해당 문자열이 있으면 true 없으면 false반환
            // || => 왼쪽의 값이없으면 ''로 대체된다.
            return (e.name || "").indexOf(v || "") > -1;
          });
        }, 500);
      }
    },
    selectCategory(index) {
      this.selectedcategory = index;
    },
    ordering(kind){
        this.order = kind
        this.searchRecipe();
    },
    ...mapActions("lookaround", [ "setRecipequery", "getFilteredRecipes" ]),
    ...mapMutations("lookaround", [ "initPage", "setRecipequeryCategory", "initRecipes","setRecipequeryOrder" ]),
  },
  computed: {
    ...mapState("lookaround", ["ingredients", "recipequery"]),
  },
  watch: {
    // 넣을 재료 자동 완성 리스트
    searchtextS(val) {
      if (val == "" || val == null) this.selecteditems = [];
      else {
        this.selecteditems = this.ingredients
          .sort(function (a, b) {
            return a.name.length < b.name.length ? -1
              : a.name.length > b.name.length ? 1 : 0;
          })
          .filter((e) => {
            return (e.name || "").indexOf(val || "") > -1;
          });
      }
    },
    // 뺄 재료 자동 완성 리스트
    searchtextE(val) {
      if (val == "" || val == null) this.excludeditems = [];
      else {
        this.excludeditems = this.ingredients
          .sort(function (a, b) {
            return a.name.length < b.name.length ? -1
              : a.name.length > b.name.length ? 1 : 0;
          })
          .filter((e) => {
          return (e.name || "").indexOf(val || "") > -1;
        });
      }
    },
    selectedcategory() {
      this.setRecipequeryCategory(this.selectedcategory);
      this.initPage();
      this.initRecipes();
      this.getFilteredRecipes();
    },
  },
};
</script>
    
<style scoped>
#searchbar {
  width: 900px;
  display: block;
  margin: 0px auto;
  background-color: #fff;
}

@media (max-width: 1225px) {
  #searchbar {
    width: 100%;
  }
}

.cate-title {
  color: #53AAA1;
  font-weight: 700;
  padding-left: 0px;
  padding-right: 0px;
  padding-bottom: 0px;
}

.cate-list {
  display: flex;
  justify-content: space-around;
  align-items: center;
  font-size: 0.9em;
}

.cate-list::after {
  content: "";
  position: absolute;
  background-color: #53AAA1;
  height: 0.1em;
  margin-top: 1.6em;
  width: 95%;
  left: 50%;
  transform: translateX(-50%);
}

.cate-item {
  cursor: pointer;
  transition: all .2s ease-out;
  padding: 3px 10px 0px 10px;
}

.cate-item:hover {
  color:#53AAA1;
}

.cate-item.selected {
  font-weight:700;
  background-color: #53AAA1;
  color: white;
  transition: all .2s ease-out;
}

@media (max-width: 772px) {
  .cate-item {
  cursor: pointer;
  transition: all .2s ease-out;
  padding: 0px;
  }

  .cate-item.selected {
  font-weight:700;
  background-color: transparent;
  color: #53AAA1;
  transition: all .2s ease-out;
}
.cate-list::after {
  content:"";
}
}

.cate-item.defalut {
  content:"";
}

</style>