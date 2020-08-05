<template>
  <b-container>
      <b-row>
      <b-col>
      <img :src="selectedRecipe.main_image" alt="mainimage">
      <h2 style="text-align:center">{{ selectedRecipe.title }}</h2>
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
      <hr>
      <b-row>
          <b-col lg-3 style="text-align:center">
            <b-img :src="recipeUser.image_url" rounded="circle" alt="Circle image" id="userimg"></b-img>
            {{ selectedRecipe.recipe_user_name }}
          </b-col>
          <b-col lg-9>
          <div class="arrow_box">
              "{{ selectedRecipe.description }}"
          </div>
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
    .arrow_box {
        font-size: 2vw;
        position: relative;
        background: #c2c2c2;
        border: 4px solid #6e7070;
    }
    .arrow_box:after, .arrow_box:before {
        right: 100%;
        top: 50%;
        border: solid transparent;
        content: " ";
        height: 0;
        width: 0;
        position: absolute;
        pointer-events: none;
    }

    .arrow_box:after {
        border-color: rgba(194, 194, 194, 0);
        border-right-color: #c2c2c2;
        border-width: 15px;
        margin-top: -15px;
    }
    .arrow_box:before {
        border-color: rgba(194, 225, 245, 0);
        border-right-color: #6e7070;
        border-width: 21px;
        margin-top: -21px;
    }
</style>