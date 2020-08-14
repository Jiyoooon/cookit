<template>
  <div>
      <b-container fluid="lg">
            <h2>조리순서</h2>
            <cookingstepitem class="cookingstepitem" :cookingstep="cookingstep" v-for="cookingstep in selectedRecipe.cookingStep" :key="cookingstep.cooking_steps_id"/>
      </b-container>
  </div>
</template>
<script>
import cookingstepitem from './CookingStepItem.vue'
import { mapState } from 'vuex'

export default {
    name: 'cookingstep',
    components: {
        cookingstepitem,
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe'])
    },
}
</script>

<style>
    .cookingstepitem{
        min-height: 50px;
    }
</style>