<template>
  <div>
    <b-container fluid>
      <b-row align-v="center">
        <b-col class="text-center mr-3 ml-3">
          <h3 style="font-weight:700;">재료</h3>
        </b-col>
      </b-row>
      <hr style="margin-top: 0.5em">
      <b-row>
        <b-col sm="6">
          <b-row align-h="center" class="mt-2 mb-1">
            <h5 style="font-weight:620; color:#53AAA1">
              <font-awesome-icon :icon="['fas', 'caret-right']" style="color: #53AAA1; margin-right: 0.8em;"/>
              주재료
              <font-awesome-icon :icon="['fas', 'caret-left']" style="color: #53AAA1; margin-left: 0.8em;"/>
            </h5>
          </b-row>
          <b-row>
            <b-col cols="2"></b-col>
            <b-col cols="9">
              <b-table fixed borderless :items="essentialIngre"></b-table></b-col>
            <b-col cols="1"></b-col>
          </b-row>
        </b-col>
        <b-col sm="6">
          <b-row align-h="center" class="mt-2 mb-1">
            <h5 style="font-weight:620; color:#53AAA1">
              <font-awesome-icon :icon="['fas', 'caret-right']" style="color: #53AAA1; margin-right: 0.8em;"/>
              부재료
              <font-awesome-icon :icon="['fas', 'caret-left']" style="color: #53AAA1; margin-left: 0.8em;"/>
            </h5>
          </b-row>
          <b-row>
            <b-col cols="2"></b-col>
            <b-col cols="9">
              <b-table fixed borderless :items="inessentialIngre"></b-table></b-col>
            <b-col cols="1"></b-col>
          </b-row>
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
                    '재료명': this.selectedRecipe.ingredients[i].food_ingredient_small_name,
                    '재료양': this.selectedRecipe.ingredients[i].quantity,
                }
                if (this.selectedRecipe.ingredients[i].is_essential) {
                    this.essentialIngre.push(ingre)
                } else {
                    this.inessentialIngre.push(ingre)
                }
            }
        },
    },
    created() {
      this.sortingredient()
    },
}
</script>

<style>

</style>