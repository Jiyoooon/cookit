<template>
    <div style="width:100%; height:100%">
            
        <div v-if="checkdeleteauth">
            <button @click="deleteRecipe(selectedRecipe.recipe_id)">삭제삭제</button>
        </div>
        <div v-if="checkdeleteauth">
            <button @click="gorecipeupdate">고고</button>
        </div>
        <recipe />
        <hr>
        <ingredient />
        <hr>
        <cookingstep />
        <v-overlay :value="this.overlay" >
            <div style="cursor:pointer; width:100%; height:100%" @click="overlay = false; console.log('꺼져랏') ">
                <v-btn
                    icon
                    id="button1"
                >
                <v-icon>mdi-close</v-icon>
                </v-btn>
                <div class="timerpos" v-if="timerstate != -1">
                    <timervue @set-timerstate ="setTimerState" :t="timervalue"/>
                </div>
            </div>
        </v-overlay>
    
    <div class="text-center">
        <v-btn
        id = "button1"
        @click="setTimerValue('00:04'); (overlay = !overlay);"
        >
        3초
        </v-btn>
    </div>    
      <div id = "button2" @click="setTimerValue('00:21')">
        20초
      </div>
      <div  id= "button">
            <b-icon icon="book" v-b-modal="'my-modal'" scale="1" v-b-tooltip.hover title="가로보기"></b-icon>
      </div>
      <div @click="scrollToTop" id= "button-bottom">
            <b-icon icon="arrow-up-circle" scale="1" v-b-tooltip.hover title="가장위로" ></b-icon>
    </div>
      <!-- <b-button id="button" v-b-modal="'my-modal'">가로보기</b-button> -->
    <commentCreate />
    <commentList />
        <!-- The modal -->
        <b-modal size="xl" id="my-modal" title="쿠킹스텝">
            <b-carousel
            id="carousel-fade"
            style="text-shadow: 1px 1px 2px #000"
            fade
            indicators
            controls
            :interval="0"
            
            img-width=100
            img-height=100
        >
            <b-carousel-slide
            v-for="cookingstep in selectedRecipe.cookingStep"
            :key="cookingstep.cooking_steps_id"
            :caption="(String)(cookingstep.steps)"
            :img-src="cookingstep.step_image"
            style="height:70vh"
            
            text="ddd"
            ><h1>{{ cookingstep.description }}</h1></b-carousel-slide>
            </b-carousel>
        </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import cookingstep from '../../components/recipeview/cookingstep.vue'
import ingredient from '../../components/recipeview/ingredient.vue'
import recipe from '../../components/recipeview/recipe.vue'
import commentCreate from '../../components/recipeview/commentCreate.vue'
import commentList from '../../components/recipeview/commentList.vue'
import timervue from "@/components/recipeview/Timer.vue";

export default {
    name: 'recipeDetailView',
    data(){
        return{
            timervalue: "99:00",
            timerstate:-1,
            overlay:false,
        }
    },
    components: {
        cookingstep,
        ingredient,
        recipe,
        commentCreate,
        commentList,
        timervue,
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe']),
        ...mapState('accounts', ['authUser']),
        checkdeleteauth() {
            if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
                return true
            } else {
                return false
            }
        },
    },
    methods: {
        scrollToTop(){
            window.scroll({top:0,left:0,behavior:'smooth'})//==scroll(0,0)과 같다 => 0,0위치로 이동하는 메소드
        },
        setTimerValue(t){
            console.log("타이머값을" + "00:00" + "로")
            this.timervalue = t
            this.setTimerState(1)
            console.log(this.timervalue)
        },
        setTimerState(s){
            console.log(s)
            this.timerstate = s
            console.log(this.timerstate)
        },
        gorecipeupdate() {
          console.log('nnnn')
          if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
              this.$router.push({ name: 'RecipeUpdateView', params: { recipe_id: this.selectedRecipe.recipe_id }})
          }
        },
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser', 'fetchComments']),
        ...mapActions('editor', ['deleteRecipe']),
    },
    created() {
        this.fetchRecipe(this.$route.params.recipe_id),
        this.fetchRecipeUser()
        this.fetchComments()
    },
}
</script>

<style>
    #button1 {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 40vh;
        cursor: pointer;
    }
    #button2 {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 30vh;
        cursor: pointer;
    }
    #button {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 20vh;
        cursor: pointer;
    }
    #button-bottom{
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 10vh;
        cursor: pointer;
    }
    .timerpos {
        background-color: #eee;
        z-index: 1;
        position: absolute;
        width: 100%;
        height: 100%;
    }
</style>