<template>
  <div id="editor">
    <div class="editor-container">
      <h2>레시피 정보</h2>
      <hr>
      <recipeInfo></recipeInfo>
    </div>
    <div class="editor-container">
      <h2>재료</h2>
      <hr>
      <b-container fluid>
        <b-row>
          <b-col class="mt-3">주재료</b-col>
          <b-col md="5"><ingredient :ingredients="mainIngr" :essential=1></ingredient></b-col>
          <b-col class="mt-3">부재료</b-col>
          <b-col md="5"><ingredient :ingredients="subIngr" :essential=0></ingredient></b-col>
        </b-row>
      </b-container>
    </div>
    <div class="editor-container">
      <h2>조리 과정</h2>
      <hr>
      <cooking-step></cooking-step>
    </div>
    <div>
      <b-container fluid>
        <b-row>
          <b-col cols="2" sm="3" lg="4"></b-col>
          <b-col cols="4" sm="3" lg="2"><div class="block-btn btn-style1" @click="onSubmitButton" block>완료</div></b-col>
          <b-col cols="4" sm="3" lg="2"><div class="block-btn btn-style4" @click="cancel">취소</div></b-col>
          <b-col cols="2" sm="3" lg="4"></b-col>
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
      ...mapMutations('editor', ['SET_RECIPE', 'SET_COOKINGSTEP', 'SET_MAININGR', 'SET_SUBINGR']),
      cancel() {
        this.$router.go(-1)
      }
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

.inline-block-btn {
  display: inline-block;
  justify-content: space-around;
  align-items: center;
  margin: 1em;
  cursor: pointer;
  padding: 6px 10px 6px 10px;
}

.block-btn {
  display: block;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  cursor: pointer;
  padding: 6px 10px 6px 10px;
}

/* 기본 청록색 버튼 */
.btn-style1 {
  background-color: #53AAA1;
  color: white;
}

/* 흰색에 청록 라인 버튼 */
.btn-style2 {
  border: 1px solid #53AAA1;
  color: #53AAA1;
}

/* disabled 청록색 버튼 */
.btn-style3 {
  background-color: #4B9A91;
  color: #74BCB4;
  cursor: default;
}

/* 완전 경고 빨간색 버튼 */
.btn-style4 {
  background-color: darkred;
  color: white;
}
</style>