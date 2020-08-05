<template>
  <b-container>
      <h2>{{ selectedRecipe.title }}</h2>
      <b-row>
      <b-col>
      <img :src="selectedRecipe.main_image" alt="mainimage">
      </b-col>
      </b-row>
      <b-row>
          <b-col lg-3>
            <img id="userimg" :src="recipeUser.image_url">        
            {{ selectedRecipe.recipe_user_name }}
            
          </b-col>
          <b-col lg-9>
            {{ selectedRecipe.description }}
          </b-col>
      </b-row>
      <b-row>
          <b-col>
            {{ selectedRecipe.servings }}인분
          </b-col>
          <b-col>
            난이도 : {{ selectedRecipe.level }}
          </b-col>
          <b-col>
            조리시간 : {{ selectedRecipe.cooking_time }}
          </b-col>
      </b-row>
       <!-- {{ recipeUser }} -->
       <br>
  </b-container>
</template>

<script>
import { mapState } from 'vuex'

export default {
    name: 'recipe',
    computed: {
        ...mapState('recipes', ['selectedRecipe', 'recipeUser']),
    },
    methods: {
        // ...mapActions('recipes', ['fetchRecipeUser'])
    },
    created() {
        // this.fetchRecipeUser()
    }
}
</script>

<style>
    #userimg {
        height: 20vw;
        weight: 20vw;
    }
</style>