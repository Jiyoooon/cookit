<template>
  <div>
      <b-container>
      <h2>재료 준비</h2>
          <b-row>
              <b-col lg-6>
                  <strong>주재료</strong>
      <b-table striped hover :items="essentialIngre"></b-table>
              </b-col>
              <b-col lg-6>
                  <strong>보조재료</strong>
      <b-table striped hover :items="inessentialIngre"></b-table>
        </b-col>
        </b-row>
      </b-container>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
    name: 'ingredient',
    data() {
        return {
            essentialIngre: [],
            inessentialIngre: [],
        }
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe'])
    },
    methods: {
        sortingredient() {
            const length = this.selectedRecipe.ingredients.length;
            for (var i=0; i < length; i++) {
                const ingre = {
                    'no': i+1,
                    'name': this.selectedRecipe.ingredients[i].food_ingredient_small_name,
                    'quantity': this.selectedRecipe.ingredients[i].quantity,
                }
                if (this.selectedRecipe.ingredients[i].is_essential) {
                    this.essentialIngre.push(ingre)
                } else {
                    this.inessentialIngre.push(ingre)
                }
            }
        },
    },
    mounted() {
        this.sortingredient()
    }
}
</script>

<style>

</style>