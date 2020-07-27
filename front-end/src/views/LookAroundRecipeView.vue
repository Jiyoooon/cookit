<template>
    <div>
        <RecipeSearchBar />
        <div class="RecipeArray">
        <v-container fluid grid-list-md>
            <v-layout row wrap>
                <v-flex xs12 sm6 md4 lg3 xl2 v-for="x in 10" :key=x >
                    <RecipeCard >
                    </RecipeCard>
                </v-flex>
            </v-layout>


        <v-btn @click="scrollToTop" id="button-bottom">
            ^
        </v-btn>

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
import RecipeSearchBar from "@/components/RecipeSearchBar.vue"
import RecipeCard from "@/components/RecipeCard.vue"
export default {
    data(){
        return{
            
        }
    },
    components:{
        RecipeSearchBar,
        RecipeCard,
    },
    methods:{
        getRecipes (){
            console.log("레시피가져오기")
        },
        scrollToTop(){
            window.scroll(0,0)//==scroll(0,0)과 같다 => 0,0위치로 이동하는 메소드
        },
    },
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