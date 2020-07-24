<template>
  <div class="container">
      <MyRecipeListItem v-for="recipe in myrecipes" :key="recipe.id" :recipe="recipe"/>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import MyRecipeListItem from './MyRecipeListItem.vue'

export default {
    name: 'MyRecipeList',
    components: {
        MyRecipeListItem,
    },
    computed: {
        ...mapState('myblog', ['myrecipes'])
    },
}
</script>

<style>

</style>