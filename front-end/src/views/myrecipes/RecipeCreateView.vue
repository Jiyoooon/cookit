<template>
  <div id="recipePage">
    <div class="container">
      <h2>레시피 정보</h2>
      <hr>
      <recipeInfo></recipeInfo>
    </div>
    <div class="container">
      <h2>재료</h2>
      <hr>
      <b-container fluid="lg">
        <b-row>
          <b-col class="mt-3">주재료</b-col>
          <b-col sm="5"><ingredient :ingredients="mainIngr" :essential=1></ingredient></b-col>
          <b-col class="mt-3">부재료</b-col>
          <b-col sm="5"><ingredient :ingredients="subIngr" :essential=0></ingredient></b-col>
        </b-row>
      </b-container>
    </div>
    <div class="container">
      <h2>조리 과정</h2>
      <hr>
      <cooking-step></cooking-step>
    </div>
    <div>
      <b-container>
        <b-row>
          <b-col sm="3"></b-col>
          <b-col sm="3"><b-button variant="primary" @click="onSubmitButton" block>완료</b-button></b-col>
          <b-col sm="3"><b-button variant="danger" block>취소</b-button></b-col>
          <b-col sm="3"></b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import RecipeInfo from '@/components/editor/RecipeInfo.vue'
import Ingredient from '@/components/editor/Ingredient.vue'
import CookingStep from '@/components/editor/CookingStep.vue'
import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name: 'RecipeCreateView',
    components: {
      RecipeInfo, Ingredient, CookingStep
    },
    computed: {
      ...mapState('editor', ['mainIngr', 'subIngr'])
    },
    methods: {
      ...mapActions('editor', ['onSubmitButton']),
      ...mapMutations('editor', ['SET_RECIPE', 'SET_COOKINGSTEP', 'SET_MAININGR', 'SET_SUBINGR'])
    },
    created() {
      this.SET_RECIPE({
      title: null,
      description: null,
      category_id: 0,
      servings: 0,
      cooking_time: 0,
      level: 0,
      main_image_file: null,
      tag: [ ]
      })
      this.SET_COOKINGSTEP([
      {
        steps: 1,
        description: null,
        tip: null,
        step_image_file: null },
      {
        steps: 2,
        description: null,
        tip: null,
        step_image_file: null },
      ])
      this.SET_MAININGR([
      { name: null, quantity: null, is_essential: 1 },
      { name: null, quantity: null, is_essential: 1 },
      ])
      this.SET_SUBINGR([
      { name: null, quantity: null, is_essential: 0 },
      { name: null, quantity: null, is_essential: 0 },
      ])

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