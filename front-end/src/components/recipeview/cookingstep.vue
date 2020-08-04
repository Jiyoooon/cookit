<template>
  <div>
      <h2>조리순서</h2>
      <b-container>
          <b-row>
        <cookingstepitem :cookingstep="cookingstep" v-for="cookingstep in selectedRecipe.cookingStep" :key="cookingstep.cooking_steps_id"/>
          </b-row>
      </b-container>
  </div>
</template>

<script>
import cookingstepitem from './cookingstepitem.vue'
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

</style>