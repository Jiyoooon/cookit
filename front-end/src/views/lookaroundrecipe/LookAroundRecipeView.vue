<template>
    <div id="lookaround">
        <RecipeSearchBar :order="order"/>
        
        <!-- 레시피 정렬 -->
        <div class="sort-list">
          <div v-for="(item, index) in sort" @click="ordering(index + 1)" 
          :class="(index+1==order)?'sort-item selected':'sort-item default'" :key="String(item)">
          {{ item }}</div>
        </div>

        <hr id="divider">
        <div class="RecipeArray">
        <v-container fluid grid-list-md>
            <v-layout row wrap >
                <v-flex xs12 sm6 md4 lg3 xl2 v-for="(recipeinfo, index) in recipes" :key="index" style="margin:auto%; padding:12px 12px;" >
                    <recipe-card :recipe="recipeinfo" ></recipe-card>
                </v-flex>
            </v-layout>
            <font-awesome-icon id="top-btn" @click="scrollToTop" :icon="['fas', 'angle-up']" />
        
        <div v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="0"
            style="text-align:center; font-size:0.9em; margin-top:3em;">
            <b-spinner v-if="numberofgetrecipes != 0" label="Spinning" ></b-spinner>
            <div v-if="numberofgetrecipes == 0">더 이상 불러올 레시피가 없어요. 다시 검색해볼까요?</div>
        </div>
        <!-- <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading> -->
        </v-container>
        <v-btn icon>
            <v-icon>mdi-share-variant</v-icon>
        </v-btn>
        <div id="bottomSensor"></div>
        </div>
    </div>
</template>

<script src="https://cdnjs.cloudflare.com/ajax/libs/scrollmonitor/1.2.0/scrollMonitor.js"></script>
<script>
import InfiniteLoading from 'vue-infinite-loading'
import RecipeSearchBar from "@/components/lookaroundrecipe/RecipeSearchBar.vue"
import recipeCard from "@/components/lookaroundrecipe/RecipeCard.vue"
import { mapState, mapActions, mapMutations } from 'vuex'
import $ from 'jquery'
export default {
  data(){
    return{
      busy: false,
            
      // 레시피 정렬
      sort: ['최신순', '조회순', '추천순'],
      order: 1,
      }
  },
    components:{
        RecipeSearchBar,
        recipeCard,
        InfiniteLoading,
    },
  methods:{
    ordering(value){
      this.order = value;
      this.$route.meta.scrollToTop = true;
    },
        setBusy(){
            this.busy = false
        },
        loadMore (){
            // console.log("더 불러오기")
            this.busy = true;
            setTimeout(() => {
                this.getFilteredRecipes()
            },1000);
        },
        scrollToTop(){
            window.scroll({top:0,left:0,behavior:'smooth'})//==scroll(0,0)과 같다 => 0,0위치로 이동하는 메소드
        },
        ...mapActions('lookaround',['getFilteredRecipes','alertfortest']),
        ...mapMutations('lookaround',['initializing']),
    },
    computed:{
        ...mapState('lookaround',['recipes','numberofgetrecipes','ingredients']),
    },
    watch: {
      order() {
        sessionStorage.setItem('ordering', Number(this.order))
      }
    },
    updated(){
        if(this.numberofgetrecipes != 0){//가져온 데이터수가 0이 아니면 동작
            this.setBusy()
        }
    },
    created() {
      if(sessionStorage.getItem('ordering')) this.order = Number(sessionStorage.getItem('ordering'))
      else this.order = 1;
    },
    mounted() {
      $("#myblog").removeClass("active");
      $("#browsing").addClass("active");
    }
}
</script>

<style>
#lookaround {
  width: 90%;
  min-height: 200vh;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 4em 1em 8em 1em;
}

@media (max-width: 496px) {
  #lookaround {
    width: 100%
  }
}

.sort-list {
  display: block;
  text-align: right;
  margin-bottom: -0.5em;
}

.sort-item {
  display: inline;
  padding-right: 18px;
  font-size: 0.9em;
  cursor: pointer;
}

.sort-item.selected {
  font-weight: 700;
  color: #53AAA1;
}

.RecipeArray{
    margin: 0px auto;
}

#top-btn {
    position: fixed;
    bottom: 1em;
    right: 3.5%;
    background-color: white;
    border-radius: 10%;
    box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.2);
    font-size: 2.8rem;
    width: 45px;
    height: 45px;
    padding: 2px;
    z-index: 10;
    cursor: pointer;
    font-size: 2.8rem;
}

@media (max-width: 768px) {
  #top-btn {
    font-size: 2rem;
    width: 35px;
    height: 35px;
  }
}

@media (max-width: 496px) {
  #top-btn {
    bottom: 1.6em;
  }
}
</style>