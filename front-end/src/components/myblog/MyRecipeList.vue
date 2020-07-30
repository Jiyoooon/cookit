<template>
  <b-container>
      <b-row>
      <MyRecipeListItem id="abc" @click="selectedRecipe(recipe.id)" v-for="recipe in myrecipes" :key="recipe.id" :recipe="recipe"/>
      </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'

import MyRecipeListItem from './MyRecipeListItem.vue'

export default {
    name: 'MyRecipeList',
    components: {
        MyRecipeListItem,
    },
    computed: {
        ...mapState('myblog', ['myrecipes'])
    },
    methods: {
        ...mapActions('myblog', ['fetchMyRecipes', 'selectedRecipe'])
    },
    created() {
        this.fetchMyRecipes()
    }
    
}
</script>

<style>
    #abc {
        margin: 0;
        padding: 0;
    }
    #abc:hover {
        cursor: pointer;
        color: orange;
    }
</style>