<template>
  <div>
      <recipe />
      <hr>
      <ingredient />
      <hr>
      <cookingstep />
      <div  id= "button-bottom">
            <b-icon icon="book" v-b-modal="'my-modal'" scale="1" v-b-tooltip.hover title="가로보기"></b-icon>
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
        >
            <b-carousel-slide
            v-for="cookingstep in selectedRecipe.cookingStep"
            :key="cookingstep.cooking_steps_id"
            :caption="cookingstep.steps"
            :img-src="cookingstep.step_image"
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
        ...mapState('recipes', ['selectedRecipe'])
    },
    methods: {
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser'])
    },
    created() {
        // this.fetchRecipe(),
        this.fetchRecipeUser()
    },
}
</script>

<style>
    #button {
        position: fixed;
        bottom: 10vw;
        right: 10vh;
    }
</style>