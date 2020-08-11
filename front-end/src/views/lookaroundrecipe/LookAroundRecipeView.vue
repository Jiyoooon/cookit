<template>
    <div id="list">
        <RecipeSearchBar/>
        <div class="RecipeArray">
        <v-container fluid grid-list-md >
            <v-layout row wrap >
                <v-flex xs12 sm6 md4 lg3 xl2 v-for="recipeinfo in recipes" :key="recipeinfo.recipe_id" style="margin:auto%" >
                    <RecipeCard :recipe="recipeinfo" >
                    </RecipeCard>
                </v-flex>
            </v-layout>
        <div @click="scrollToTop" id="button-bottom">
            <b-icon icon="arrow-up-circle" scale="1" v-b-tooltip.hover title="가장위로"></b-icon>
        </div>
        
        <div v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="0" style="text-align:center">
            <b-spinner v-if="numberofgetrecipes != 0" label="Spinning" ></b-spinner>
            <span v-if="numberofgetrecipes == 0">더 이상 불러올 레시피가 없어요...</span>
        </div>
        
        <!-- <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading> -->
        </v-container>
        </div>
    </div>
</template>

<script src="https://cdnjs.cloudflare.com/ajax/libs/scrollmonitor/1.2.0/scrollMonitor.js"></script>
<script>
import InfiniteLoading from 'vue-infinite-loading'
import RecipeSearchBar from "@/components/lookaroundrecipe/RecipeSearchBar.vue"
import RecipeCard from "@/components/lookaroundrecipe/RecipeCard.vue"
import { mapState, mapActions, mapMutations } from 'vuex'
export default {
    data(){
        return{
            busy: false
        }
    },
    components:{
        RecipeSearchBar,
        RecipeCard,
        InfiniteLoading,
    },
    methods:{
        setBusy(){
            this.busy = false
        },
        loadMore (){
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
    updated(){
        if(this.numberofgetrecipes != 0){//가져온 데이터수가 0이 아니면 동작
            this.setBusy()
        }
    },
    created(){
        this.initializing()
    }
}
</script>

<style>
    #button-bottom{
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 10vh;
        cursor: pointer;
    }

    .RecipeArray{
        margin-left: auto;
        margin-right: auto;
    }

#list {
  width: 90%;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 5em 1em 8em 1em;
}

</style>