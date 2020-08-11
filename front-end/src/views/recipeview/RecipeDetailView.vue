<template>
  <div>
      <div v-if="checkdeleteauth">
      <button @click="deleteRecipe(selectedRecipe.recipe_id)">삭제삭제</button>
      </div>
      <button @click="gorecipeupdate">고고</button>
      <recipe />
      <hr>
      <ingredient />
      <hr>
      <cookingstep />
      <div  id= "button">
            <b-icon icon="book" v-b-modal="'my-modal'" scale="1" v-b-tooltip.hover title="가로보기"></b-icon>
      </div>
      <div @click="scrollToTop" id= "button-bottom">
            <b-icon icon="arrow-up-circle" scale="1" v-b-tooltip.hover title="가장위로" ></b-icon>
    </div>
      <!-- <b-button id="button" v-b-modal="'my-modal'">가로보기</b-button> -->

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

export default {
    name: 'recipeDetailView',
    components: {
        cookingstep,
        ingredient,
        recipe,
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
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser']),
        ...mapActions('editor', ['deleteRecipe']),
        gorecipeupdate() {
          console.log('nnnn')
          this.$router.push({ name: 'RecipeUpdateView'})
        }
    },
    created() {
        // this.fetchRecipe(),
        this.fetchRecipeUser()
    },
}
</script>

<style>
    #button {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 18vh;
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
</style>