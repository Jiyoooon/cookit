<template>
  <div id="editor">
    <div class="editor-container">
      <h2>레시피 정보</h2>
      <hr>
      <recipe-info-for-update />
    </div>
    <div class="editor-container">
      <h2>재료</h2>
      <hr>
      <b-container fluid>
        <b-row>
          <b-col class="mt-3">주재료</b-col>
          <b-col sm="5"><ingredient-for-update :ingredients="mainIngr" :essential=1 /></b-col>
          <b-col class="mt-3">부재료</b-col>
          <b-col sm="5"><ingredient-for-update :ingredients="subIngr" :essential=0 /></b-col>
        </b-row>
      </b-container>
    </div>
    <div class="editor-container">
      <h2>조리 과정</h2>
      <hr>
      <cooking-step-for-update />
    </div>
    <div>
      <b-container fluid>
        <b-row>
          <b-col cols="2" sm="3" lg="4"></b-col>
          <b-col cols="4" sm="3" lg="2"><div class="block-btn btn-style1" @click="onSubmitButtonforUpdate" block>완료</div></b-col>
          <b-col cols="4" sm="3" lg="2"><div class="block-btn btn-style4" @click="cancel">취소</div></b-col>
          <b-col cols="2" sm="3" lg="4"></b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import recipeInfoForUpdate from '@/components/editor/RecipeInfoforUpdate.vue'
import ingredientForUpdate from '@/components/editor/IngredientforUpdate.vue'
import cookingStepForUpdate from '@/components/editor/CookingStepforUpdate.vue'
import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name: 'RecipeUpdateView',
    components: {
      recipeInfoForUpdate, ingredientForUpdate, cookingStepForUpdate
    },
    computed: {
      ...mapState('editor', ['mainIngr', 'subIngr']),
      ...mapState('recipes', ['selectedRecipe'])
    },
    methods: {
      ...mapActions('editor', ['onSubmitButtonforUpdate']),
      ...mapMutations('editor', ['SET_MAININGR', 'SET_SUBINGR']),
      cancel() {
        this.$router.go(-1)
      }
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

#editor {
  width: 90%;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 5em 1em 8em 1em;
}

.editor-container {
  margin-bottom: 2em
}

@media (max-width: 496px) {
  #editor {
    width: 100%;
  }
}
</style>