<template>
  <div id="recipePage">
    <div id="recipeInfo">
      <h2>레시피 정보</h2>
      <hr>
      <recipeInfo></recipeInfo>
    </div>
    <div id="ingredients">
      <h2>재료</h2>
      <hr>
      <b-container fluid="lg">
        <b-row>
          <b-col class="mt-3">주재료</b-col>
          <b-col sm="5"><ingredient></ingredient></b-col>
          <b-col class="mt-3">부재료</b-col>
          <b-col sm="5"><ingredient></ingredient></b-col>
        </b-row>
      </b-container>
    </div>
    <div id="steps">
      <h2>조리 과정</h2>
      <hr>
      <cooking-step></cooking-step>
    </div>
  </div>
</template>

<script>
import RecipeInfo from '@/components/editor/RecipeInfo.vue'
import Ingredient from '@/components/editor/Ingredient.vue'
import CookingStep from '@/components/editor/CookingStep.vue'

export default {
    name: 'RecipeCreateView',
    components: {
      RecipeInfo, Ingredient, CookingStep
    },
    data() {
      return {
        items: [],
      }
    },
    methods: {
      addItem() {

      },
      deleteItem() {

      }
    }
}
</script>

<style>
#recipePage {
  width: 80%;
  display: block;
  margin: 0px auto;
}
</style>