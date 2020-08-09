<template>
  <div id="recipePage">
    <div class="container">
      <h2>레시피 정보</h2>
      <hr>
      <RecipeInfoforUpdate />
    </div>
    <div class="container">
      <h2>재료</h2>
      <hr>
      <b-container fluid="lg">
        <b-row>
          <b-col class="mt-3">주재료</b-col>
          <b-col sm="5"><IngredientforUpdate :ingredients="mainIngr" :essential=1 /></b-col>
          <b-col class="mt-3">부재료</b-col>
          <b-col sm="5"><IngredientforUpdate :ingredients="subIngr" :essential=0 /></b-col>
        </b-row>
      </b-container>
    </div>
    <div class="container">
      <h2>조리 과정</h2>
      <hr>
      <CookingStepforUpdate />
    </div>
    <div>
      <b-container>
        <b-row>
          <b-col sm="3"></b-col>
          <b-col sm="3"><b-button variant="primary" @click="onSubmitButtonforUpdate" block>완료</b-button></b-col>
          <b-col sm="3"><b-button variant="danger" block>취소</b-button></b-col>
          <b-col sm="3"></b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import RecipeInfoforUpdate from '@/components/editor/RecipeInfoforUpdate.vue'
import IngredientforUpdate from '@/components/editor/IngredientforUpdate.vue'
import CookingStepforUpdate from '@/components/editor/CookingStepforUpdate.vue'
import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name: 'RecipeUpdateView',
    components: {
      RecipeInfoforUpdate, IngredientforUpdate, CookingStepforUpdate
    },
    computed: {
      ...mapState('editor', ['mainIngr', 'subIngr']),
      ...mapState('recipes', ['selectedRecipe'])
    },
    methods: {
      ...mapActions('editor', ['onSubmitButtonforUpdate']),
      ...mapMutations('editor', ['SET_MAININGR', 'SET_SUBINGR'])
    },
    created() {
      const main = []
      const sub = []
      for (var i=0; i < this.selectedRecipe.ingredients.length; i++) {
        const ingre = {
          name: this.selectedRecipe.ingredients[i].food_ingredient_small_name,
          quantity: this.selectedRecipe.ingredients[i].quantity,
          is_essential: this.selectedRecipe.ingredients[i].is_essential
        }
        if (this.selectedRecipe.ingredients[i].is_essential) {
          main.push(ingre)
        } else {
          sub.push(ingre)
        }
      }
      this.SET_MAININGR(main)
      this.SET_SUBINGR(sub)
    }
}
</script>

<style>
#recipePage {
  width: 80%;
  display: block;
  margin: 0px auto;
}
.container {
  margin: 2em
}
</style>