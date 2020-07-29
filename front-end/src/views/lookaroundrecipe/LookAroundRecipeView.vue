<template>
    <div>
        <RecipeSearchBar />
        <div class="RecipeArray">
        <v-container fluid grid-list-md>
            <button @click="getFilteredRecipes"> 누르지마시오 </button>
            <v-layout row wrap>
                <v-flex xs12 sm6 md4 lg3 xl2 v-for="recipeinfo in recipes" :key="recipeinfo.recipe_id" style="margin:1%" >
                    <RecipeCard :recipe="recipeinfo">
                    </RecipeCard>
                </v-flex>
            </v-layout>
        <v-btn @click="scrollToTop" id="button-bottom"  rounded="circle">
            <b-icon icon="chevron-up" shift-v="16"></b-icon>
        </v-btn>

        <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
        </v-container>
        </div>
    </div>
</template>

<script src="https://cdnjs.cloudflare.com/ajax/libs/scrollmonitor/1.2.0/scrollMonitor.js"></script>
<script>
import InfiniteLoading from 'vue-infinite-loading';
import RecipeSearchBar from "@/components/lookaroundrecipe/RecipeSearchBar.vue"
import RecipeCard from "@/components/lookaroundrecipe/RecipeCard.vue"
import { mapState, mapActions } from 'vuex'
export default {
    data(){
        return{
            list: ['감자','고구마','딸기'],
        }
    },
    components:{
        RecipeSearchBar,
        RecipeCard,
        InfiniteLoading,
    },
    methods:{
        getRecipes (){
            console.log(this.recipes[0].cooking_time)
        },
        ...mapActions('lookaround',['getFilteredRecipes']),
        
        scrollToTop(){
            window.scroll(0,0)//==scroll(0,0)과 같다 => 0,0위치로 이동하는 메소드
        },
        infiniteHandler($state) {
            setTimeout(() => {//1초마다 갱신
                this.getFilteredRecipes()
                // getrecipe받아오기 => input을 뭘로 줄지 생각(이전에 받아왔던 레시피 이후로 받아와야함)

                // 레시피를 받아오면 Vuex에 저장 => v-for="객체이름 $store.state.객체이름s" 으로 넘겨준다 :props이름= 객체이름 
                // 으로 넘겨준다.
                // selected안에서는 받은 객체 이용 카드만듬
                setTimeout(1000)
                $state.loaded();
            }, 2000);
        },
    },
    computed:{
        ...mapState('lookaround',['recipes']),
    },
    created(){
        this.getFilteredRecipes()
    }
}
</script>

<style>
    #button-bottom{
        position: fixed;
        right: 5vw;
        bottom: 10vh;
        border: 0.1vh solid rgb(226, 151, 64);
    }

    .RecipeArray{
        width: 80%;
        margin-left: auto;
        margin-right: auto;
    }
</style>