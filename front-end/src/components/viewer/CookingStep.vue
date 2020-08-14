<template>
  <div>
    <b-container fluid>
      <b-row align-v="center">
        <b-col class="text-center mr-3 ml-3">
          <h3 style="font-weight:700;">조리순서</h3>
        </b-col>
      </b-row>
      <hr style="margin-top: 0.5em">
      <b-row>
        <cooking-step-item :cookingstep="cookingstep" v-for="cookingstep in selectedRecipe.cookingStep"
        :key="cookingstep.cooking_steps_id"/>
      </b-row>
    </b-container>
  </div>
</template>
<script>
<<<<<<< HEAD
import cookingstepitem from './CookingStepItem.vue'
=======
import cookingStepItem from '@/components/viewer/CookingStepItem.vue'
>>>>>>> develop
import { mapState } from 'vuex'

export default {
    name: 'CookingStep',
    components: {
        cookingStepItem,
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe'])
    },
}
</script>

<style>
</style>